package com.news.service.implement.comments;

import com.news.dao.comments.CommentDao;
import com.news.entitys.ForumDiscussEntity;
import com.news.service.implement.ISetForumDiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vivian on 2018/03/30.
 */
@Service
public class SetForumDiscussService implements ISetForumDiscussService {

    @Autowired
    CommentDao commentDao;


    /**
     * 发表论坛平论
     * @param forumId
     * @param discuss
     * @return
     */
        public int setForumDiscuss(int forumId, String discuss) {
          String userName="Vivian";
      return   commentDao.setForumDiscuss(forumId,userName,discuss);


    }
}
