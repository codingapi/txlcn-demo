package com.codingapi.txlcn.demo.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Date: 2018/12/25
 *
 * @author ujued
 */
@RestController
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @RequestMapping("/txlcn")
    public String execute(@RequestParam("value") String value) {
        String result1 = demoService.transactionA();
        return "DTX A:" + result1 + " | DTX B:" + demoService.transactionB(value);
    }

    @RequestMapping("/call-by-e")
    public String callByE(@RequestParam("value") String value) {
        return demoService.transactionC();
    }
}
