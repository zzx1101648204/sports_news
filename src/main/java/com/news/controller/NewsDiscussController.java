package com.news.controller;

import com.news.common.ResultWrapper;
import com.news.dao.comments.CommentDao;
import com.news.entitys.NewsDiscussEntity;
import com.news.entitys.NewsEntity;
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
@RequestMapping("/news")
public class NewsDiscussController {


    @Autowired
    ICommentService commentService;
    @Autowired
    CommentDao commentDao;


    /**
     * 获取某个新闻
     * @param newsId
     * @return
     */
    @PostMapping("/discuss")
    public Object getSomeNew(@RequestParam(value = "id") int newsId){
        NewsEntity newsEntity=commentService.getNews(newsId);
        if(newsEntity!=null){
            return newsEntity;
        }
        return 0;
    }

    /**
     * 获取某个新闻的所有的评论
     * @param id
     * @param pn
     * @return
     */
    @PostMapping("/discusspn")
    public ResultWrapper getNewsDiscuss(@RequestParam(value = "id")int id, @RequestParam(value = "pn")int pn){
        ResultWrapper resultWrapper=new ResultWrapper();
        List<NewsDiscussEntity> newsDiscussEntities=commentService.getNewsDiscuss(id,pn);
        if(newsDiscussEntities.size()>0){
            int num=commentDao.newsDiscussNum(id);
            int page=num/10;
            int pages=num%10;

            if(page==0){
                return resultWrapper.build(1,"获取成功",newsDiscussEntities);
            }
            if(page!=0&&pages==0){
                return resultWrapper.build(page,"获取成功",newsDiscussEntities);
            }
                return resultWrapper.build(page+1,"获取成功",newsDiscussEntities);
        }
        return resultWrapper.build(0,"获取失败");
    }

}
