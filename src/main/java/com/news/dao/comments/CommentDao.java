package com.news.dao.comments;

import com.news.entitys.ForumDiscussEntity;
import com.news.entitys.ForumEntity;
import com.news.entitys.NewsDiscussEntity;
import com.news.entitys.NewsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */
@Mapper
public interface CommentDao {
    @Select("SELECT * FROM news WHERE id=#{newsId}")
    NewsEntity getNews(@Param(value = "newsId")int newsId);

    @Select("SELECT a.news as news,a.admin_name as news_name,a.news_time as admin_time,a.news_title as news_title," +
            " b.user_name as user_name,b.discuss as discuss,b.is_like as is_like,b.discuss_time as discuss_time FROM " +
            "news a INNER JOIN news_discuss b ON a.id=b.news_id WHERE b.news_id=#{newsId} order by discuss_time desc LIMIT #{pn},10 ")
    List<NewsDiscussEntity> getNewsDiscuss(@Param(value = "newsId")int newsId,
                                           @Param(value = "pn") int pn);

    @Select("SELECT * FROM forum WHERE id=#{forumId}")
    ForumEntity getForum(@Param(value = "forumId")int forumId);

    @Select("SELECT a.user_name as user_name,a.forum_time as forum_time,a.forum as forum ,b.user_name as user_name," +
            "b.discuss as discuss,b.discuss_time as discuss_time FROM forum a INNER JOIN forum_discuss b ON a.id=b." +
            "forum_id WHERE b.forum_id=#{forumId} order by discuss_time desc LIMIT #{pn},10")
    List<ForumDiscussEntity> getForumDiscuss(@Param(value = "forumId")int forumId,@Param(value = "pn") int pn);

    @Insert("INSERT INTO news_discuss (news_id,user_name,discuss) VALUES (#{newsId},#{userName},#{discuss}) ")
    int setNewsDiscuss(@Param(value = "newsId") int newsId,
                       @Param(value = "userName") String userName,
                       @Param(value = "discuss") String discuss);
    @Insert("INSERT INTO forum_discuss (forum_id,user_name,discuss) VALUES (#{forumId},#{userName},#{discuss})")
    int setForumDiscuss(@Param(value = "forumId") int forumId,
                        @Param(value = "userName") String userName,
                        @Param(value = "discuss") String discuss);

    @Select("SELECT COUNT(id) FROM news_discuss WHERE news_id=#{newsId}")
    int newsDiscussNum(@Param(value = "newsId")int newsId);

    @Select("SELECT COUNT(id) FROM forum_discuss WHERE forum_id=#{forumId}")
    int forumDiscussNum(@Param(value = "forumId")int forumId);



}
