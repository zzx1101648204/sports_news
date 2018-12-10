package com.news.controller;

import com.news.service.implement.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Created by:  Ju Harry
 * Date: 2018/4/13 16:20
 */

@RestController
@RequestMapping("/file")
public class FileUpload {
    @Autowired
    INewsService newsService;

    /**
     * 新闻上传
     * @param news
     */

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
  //  @Permission(byOder = false,identities = {1})
    public void fileUpload(String news){

    }
}
