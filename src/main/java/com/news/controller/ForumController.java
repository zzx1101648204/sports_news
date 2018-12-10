
package com.news.controller;

import com.news.common.ResultWrapper;
import com.news.dao.news.ForumDao;
import com.news.entitys.ForumEntity;
import com.news.service.implement.IForumService;
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
public class ForumController {

    @Autowired
    IForumService forumService;
    @Autowired
    ForumDao forumDao;


    /**
     * 获取论坛所有话题
     * @param pn
     * @return
     */
    @PostMapping("/get")
    public ResultWrapper getForum(@RequestParam(value = "pn")int pn){
        ResultWrapper resultWrapper = new ResultWrapper();
        List<ForumEntity> forumEntity=forumService.getForum(pn);
        if(forumEntity.size()>0){
            int num=forumDao.forumNum();
            int page=num/9;
            int pages=num%9;
            if(page==0){
                return resultWrapper.build(1,"获取成功",forumEntity);
            }
            if(page!=0&&pages==0){
                return resultWrapper.build(page,"获取成功",forumEntity);
            }
            return resultWrapper.build(page+1,"获取成功",forumEntity);
        }
        return resultWrapper.build(0,"获取失败");
    }


    /**
     * 删除论坛某个话题
     * @param forumId
     * @return
     */
    @PostMapping("/del")
    public Object delForum(@RequestParam(value = "id")int forumId){

        int del=forumService.delForum(forumId);
        if(del==1){
            return 1;
        }
        return 0;
    }



    /**
     * 发表论坛
     * @param forum
     * @return
     */
    @PostMapping(value = "/set")
    public int setForum(@RequestParam("forum")String forum){
        int s=forumService.setForum(forum,"Vivian");
        if(s==1){
            return 1;
        }
        return 0;
    }

}

