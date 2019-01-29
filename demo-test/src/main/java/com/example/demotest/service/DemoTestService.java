package com.example.demotest.service;

import com.example.demotest.dao.ResultDao;
import com.example.demotest.dao.TestDao;
import com.example.demotest.model.Item;
import com.example.demotest.model.Result;
import com.example.demotest.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Date;
import java.util.List;

@Component
public class DemoTestService {


    @Autowired
    private TestDao testDao;

    @Autowired
    private ResultDao resultDao;

    String path = "d://developer/idea/txlcn-demo/";


    public void dubbo(){
        String type = "dubbo";
        String url = "http://localhost:12004/txlcn?value=bob";
        start(type,url);
    }

    public void spring(){
        String type = "spring";
        String url = "http://localhost:12001/txlcn?value=bob";
        start(type,url);
    }

    @PostConstruct
    public void init(){
        //清理jmeter文件夹
        JmeterUtils.delete(path+"/jmeter");

        resultDao.truncate();
    }

    public void start(String type,String url){
        File file = new File(path+type);

        for(String item:file.list()){
            File testFile = new File(path+type+"/"+item+"/run.bat");
            System.out.println(testFile.getPath());
            if(testFile.exists()){

                //杀死服务
                List<Item> items =  WindowJavaUtils.javaAll(item+"-"+type);
                WindowJavaUtils.kill(items);


                //启动服务
                WindowCmdUtils.runBat(testFile.getPath());

                //检查服务状态
                int tryCount = 0;
                while (!WindowWgetUtils.state(url)){
                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tryCount++;
                    if(tryCount==100){
                        System.out.println("服务启动有问题.");
                        System.exit(1);
                    }
                }

                //清理数据
                testDao.truncateDemo();

                //执行Jmeter测试
                String report = JmeterUtils.run(type,item);

                boolean isOk = testDao.isOk();
                System.out.println("isOk:"+isOk);

                //获取报告结果并保存数据
                Result result =  HtmlParserUtils.parser(path+"//"+report);
                result.setCreate_time(new Date());
                result.setIs_ok(isOk?"success":"error");
                result.setType(type);
                result.setModel(item);
                resultDao.save(result);

                //杀死服务
                items =  WindowJavaUtils.javaAll(item+"-"+type);
                WindowJavaUtils.kill(items);

            }
        }
    }
}
