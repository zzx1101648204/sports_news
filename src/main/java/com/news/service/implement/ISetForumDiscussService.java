package com.news.service.implement;

/**
 * Created by Vivian on 2018/03/30.
 */
public interface ISetForumDiscussService {
    /**
     * 对某个论坛发表评论
     * @param forumId
     * @param discuss
     * @return
     */
    int setForumDiscuss(int forumId, String discuss) ;
}
