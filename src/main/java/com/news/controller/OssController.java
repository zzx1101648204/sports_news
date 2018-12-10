package com.news.controller;

import com.news.common.ResultWrapper;
import com.news.service.implement.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class OssController {
    @Autowired
    IOssService ossService;

    @PostMapping("/oss")
    public void upload(HttpServletRequest request, HttpServletResponse response){
        ossService.ossUploader(request,response);
    }

    @RequestMapping(value = "/callback",method = RequestMethod.POST)
    public void callBack(HttpServletRequest request, HttpServletResponse response){
        try {
            ossService.callBack(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
