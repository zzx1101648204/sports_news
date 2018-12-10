package com.news.dao.news;

import com.news.entitys.ForumEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Vivian on 2018/03/29.
 */
@Mapper
public interface ForumDao {
    @Select("Select * FROM forum order by forum_time desc LIMIT #{pn},9")
    List<ForumEntity> getForum(@Param(value = "pn") int pn);

    @Delete("DELETE FROM forum WHERE id=#{forumId} ")
    int delForum(@Param(value = "forumId") int forumId);

    @Insert("INSERT INTO forum(forum,user_name) VALUES (#{forum},#{userName})")
    int setForum(@Param(value = "forum")String  forum,
                 @Param(value = "userName")String userName);

    @Select("SELECT COUNT(id) FROM forum")
    int forumNum();}
