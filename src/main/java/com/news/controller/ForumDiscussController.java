package com.news.controller;

import com.news.common.ResultWrapper;
import com.news.dao.comments.CommentDao;
import com.news.entitys.ForumDiscussEntity;
import com.news.entitys.ForumEntity;
import com.news.service.implement.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */

@RestController
@RequestMapping("/forum")
public class ForumDiscussController {

    @Autowired
    ICommentService commentDao;
    @Autowired
    CommentDao comment;



    /**
     * 获取某个论坛内容
     * @param forumId
     * @return
     */
    @PostMapping("/discuss")
    public Object getSomeForum(@RequestParam(value = "id")int forumId){
        ForumEntity forumEntity=commentDao.getForum(forumId);
        if(forumEntity!=null){
            return forumEntity;
        }
        return 0;
    }

    /**
     * 获取某个论坛的所有评论
     * @param id
     * @param pn
     * @return
     */
    @PostMapping("/discusspn")
    public ResultWrapper getForumDiscuss(@RequestParam(value = "id")int id,@RequestParam(value = "pn")int pn){
        ResultWrapper resultWrapper=new ResultWrapper();
        List<ForumDiscussEntity> forumDiscussEntities=commentDao.getForumDiscuss(id,pn);
        if(forumDiscussEntities.size()>0){
            int num=comment.forumDiscussNum(id);
            int page=num/10;
            int pages=num%10;
            if(page==0){
                return resultWrapper.build(1,"获取成功",forumDiscussEntities);
            }
            if(page!=0&&pages==0){
                return resultWrapper.build(page,"获取成功",forumDiscussEntities);
            }
            return resultWrapper.build(page+1,"获取成功",forumDiscussEntities);
        }
        return resultWrapper.build(0,"获取失败");
    }

}
