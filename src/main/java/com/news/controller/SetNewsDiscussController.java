package com.news.controller;

import com.news.service.implement.ISetNewsDiscussService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vivian on 2018/03/30.
 */
@RestController
@RequestMapping("/news")
public class SetNewsDiscussController {

    @Autowired
    ISetNewsDiscussService setNewsDiscussService;

    /**
     * 对某个新闻进行批评论
     * @param newsId
     * @param discuss
     * @return
     */
    @RequestMapping(value = "/setDiscuss",method = RequestMethod.POST)
    public Object setNewsDiscuss(@RequestParam(value = "id")int newsId,@Param(value = "discuss")String discuss){
        int setND=setNewsDiscussService.setNewsDiscuss(newsId,discuss);
        if(setND==1){
            return 1;
        }
        return 0;

    }

}
