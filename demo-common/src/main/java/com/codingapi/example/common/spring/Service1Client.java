package com.codingapi.example.common.spring;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 * Date: 19-1-29 下午3:20
 *
 * @author ujued
 */
@FeignClient(value = "spring-demo-client")
public interface Service1Client {

    @RequestMapping("/call-by-e")
    String transactionC(@RequestParam("value") String value);
}
