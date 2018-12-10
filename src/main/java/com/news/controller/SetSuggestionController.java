package com.news.controller;

import com.news.service.implement.ISuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vivian on 2018/04/16.
 */
@RestController
@RequestMapping("/suggestion")
public class SetSuggestionController {

    @Autowired
    ISuggestion suggestion;

    /**
     * 联系我们给我们反馈信息
     * @param content
     * @return
     */
    @PostMapping(value = "/set")
    public Object setSuggestion(@RequestParam(value = "content")String content ){
       int s=suggestion.createSuggestion(content,100);
        if(s==1){
            return 1;
        }
        return 0;
    }
}
