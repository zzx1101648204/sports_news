package com.news.dao.news;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Vivian on 2018/04/16.
 */
@Mapper
public interface SuggestionDao {
    @Insert("INSERT INTO suggestion(content,user_id) VALUES (#{content},#{userId})")
    int setSugg(@Param(value = "content")String content,
                @Param(value = "userId")int userId);
}
