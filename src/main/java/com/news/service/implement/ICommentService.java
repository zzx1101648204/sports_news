package com.news.service.implement;

import com.news.entitys.ForumDiscussEntity;
import com.news.entitys.ForumEntity;
import com.news.entitys.NewsDiscussEntity;
import com.news.entitys.NewsEntity;

import java.util.List;

/**
 * Created by zhl on 2018/3/28.
 */
public interface ICommentService {
    /**
     * 获取某个新闻
     * @param id
     * @return
     */
    NewsEntity getNews(int id);

    /**
     * 获取新闻的所有平论
     * @param id
     * @param pn
     * @return
     */
    List<NewsDiscussEntity> getNewsDiscuss(int id,int pn);

    /**
     * 获取某个论坛
     * @param id
     * @return
     */
    ForumEntity getForum(int id);

    /**
     * 获取某个论坛的所有平论
     * @param id
     * @param pn
     * @return
     */
    List<ForumDiscussEntity> getForumDiscuss(int id,int pn);
}
