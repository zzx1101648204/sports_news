package com.news.service.implement;

/**
 * Created by Vivian on 2018/03/30.
 */
public interface ISetNewsDiscussService {
   /**
    * 对某个新闻发表评论
    * @param newsId
    * @param discuss
    * @return
    */
   int setNewsDiscuss(int newsId,String discuss);
}
