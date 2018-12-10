package com.news.controller;

import com.news.service.implement.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Created by:  Ju Harry
 * Date: 2018/4/14 20:28
 */
@RestController
public class FileUploader {
    @Autowired
    IFileUploadService fileUploadService;

    /**
     * 上传新闻
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/config")
    public String fileUpload(HttpServletRequest request, HttpServletResponse response){
        return fileUploadService.upload(request);
    }
}
