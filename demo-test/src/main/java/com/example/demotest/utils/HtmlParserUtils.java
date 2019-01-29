package com.example.demotest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demotest.model.Result;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class HtmlParserUtils {

//    public static void main(String[] args) {
//        String path = "C:\\Users\\xp\\Desktop\\TXLCN\\jemter\\report\\content\\js\\dashboard.js";
//        Result result =  parser(path);
//
//    }

    public static Result parser(String path) {
        path +="\\content\\\\js\\\\dashboard.js";
        System.out.println(path);
        try {
            List<String> list =  FileUtils.readLines(new File(path), Charset.forName("utf8"));
            for (String line:list)
                if (line.contains("#statisticsTable")) {

                    line = line.replace("createTable($(\"#statisticsTable\"),", "");
                    line = line.replace(", function(index, item){", "");

                    JSONObject jsonObject = JSON.parseObject(line);
                    JSONObject overall = jsonObject.getJSONObject("overall");
                    JSONArray data = overall.getJSONArray("data");

                    Result result = new Result();
                    result.setSample(data.getString(1));
                    result.setKo(data.getString(2));
                    result.setError(data.getString(3));
                    result.setAverage(data.getString(4));
                    result.setMin(data.getString(5));
                    result.setMax(data.getString(6));
                    result.setPct_90th(data.getString(7));
                    result.setPct_95th(data.getString(8));
                    result.setPct_99th(data.getString(9));
                    result.setThroughput(data.getString(10));
                    result.setReceived(data.getString(11));
                    result.setSent(data.getString(12));
                    return result;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
