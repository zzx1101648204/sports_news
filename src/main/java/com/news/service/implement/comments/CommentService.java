package com.news.service.implement.comments;

import com.news.dao.comments.CommentDao;
import com.news.entitys.ForumDiscussEntity;
import com.news.entitys.ForumEntity;
import com.news.entitys.NewsDiscussEntity;
import com.news.entitys.NewsEntity;
import com.news.service.implement.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */
@Service
public class CommentService implements ICommentService {
    @Autowired
    CommentDao commentDao;


    /**
     * 获取某个新闻的内容
     * @param id
     * @return
     */
    public NewsEntity getNews(int id){
        NewsEntity newsDiscussEntity=commentDao.getNews(id);
       return newsDiscussEntity;
    }

    /**
     * 获取某个新闻的所有评论
     * @param id
     * @param pn
     * @return
     */
    public List<NewsDiscussEntity> getNewsDiscuss(int id,int pn){
        List<NewsDiscussEntity> newsDiscussEntities=commentDao.getNewsDiscuss(id,(pn-1)*10);
        return newsDiscussEntities;
    }

    //获取某个论坛和相应的评论

    /**
     * 获取某个论坛的内容
     * @param id
     * @return
     */
    public ForumEntity getForum(int id){
        ForumEntity forum=commentDao.getForum(id);
        return forum;
    }

    /**
     * 获取某个论坛的所有评论
     * @param id
     * @param pn
     * @return
     */
    public List<ForumDiscussEntity> getForumDiscuss(int id,int pn){
        List<ForumDiscussEntity> forumDiscussEntities=commentDao.getForumDiscuss(id,(pn-1)*10);
        return forumDiscussEntities;
    }
}
