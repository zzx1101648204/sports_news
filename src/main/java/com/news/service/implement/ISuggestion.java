package com.news.service.implement;

/**
 * Created by Vivian on 2018/04/16.
 */
public interface ISuggestion {
    /**
     * 发表反馈信息
     * @param content
     * @param userId
     * @return
     */
    int createSuggestion(String content,int userId);
}
