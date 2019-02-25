package org.txlcn.demo;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Date: 19-2-25 下午1:53
 *
 * @author ujued
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    @Transactional
    public String index() {
        return "index";
    }
}
