package com.sdinga.image.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testWebTest {

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }
}
