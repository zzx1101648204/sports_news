package com.news.service.implement;

import com.news.entitys.ForumEntity;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */
public interface IForumService {
    List<ForumEntity> getForum(int pn);
    int delForum(int forumId);
    int setForum(String forum,String userName);
}
