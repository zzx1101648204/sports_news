package com.news.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Created by:  Ju Harry
 * Date: 2018/4/13 16:15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试类
     * @return
     */
    @RequestMapping("/*")
    public ModelAndView test(){

        return new ModelAndView("Log");
    }

}
