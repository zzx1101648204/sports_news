package com.news.service.implement.news;

import com.news.dao.news.ForumDao;
import com.news.entitys.ForumEntity;
import com.news.service.implement.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */
@Service
public class ForumService implements IForumService {

    @Autowired
    ForumDao forumDao;


    /**
     * 获取所有论坛内容
     * @param pn
     * @return
     */
    public List<ForumEntity> getForum(int pn){
        List<ForumEntity> forumEntities= forumDao.getForum((pn-1)*9);
        return forumEntities;
    }

    /**
     * 删除某个论坛
     * @param forumId
     * @return
     */
    public int delForum(int forumId){
        int dleForum=forumDao.delForum(forumId);
        return dleForum;
    }
    public int setForum(String forum,String userName){
        return forumDao.setForum(forum,userName);
    }

}
