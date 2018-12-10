package com.news.service.implement.comments;

import com.news.dao.comments.CommentDao;
import com.news.entitys.NewsDiscussEntity;
import com.news.service.implement.ISetNewsDiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vivian on 2018/03/30.
 */
@Service
public class SetNewsDiscussService implements ISetNewsDiscussService {
    @Autowired
    CommentDao commentDao;

    /**
     * 发表新闻平论
     * @param newsId
     * @param discuss
     * @return
     */
    public int setNewsDiscuss(int newsId,String discuss){

        return  commentDao.setNewsDiscuss(newsId,"Vivian",discuss);
    }


}
