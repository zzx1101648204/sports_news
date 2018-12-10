package com.news.controller;

import com.news.service.implement.ISetForumDiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vivian on 2018/04/17.
 */
@RestController
@RequestMapping("/forum")
public class SetForumDiscussController {
    @Autowired
    ISetForumDiscussService setForumDiscussService;

    /**
     * 对某个论坛进行评论
     * @param forumId
     * @param discuss
     * @return
     */
    @RequestMapping(value = "/setDiscuss",method = RequestMethod.POST)
    public Object setForumDiscuss(@RequestParam(value = "id")int forumId, @RequestParam(value = "discuss")String discuss){
        int setND=setForumDiscussService.setForumDiscuss(forumId,discuss);
        if(setND==1){
            return 1;
        }
        return 0;

    }
}
