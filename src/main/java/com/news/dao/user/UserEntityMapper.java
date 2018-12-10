package com.news.dao.user;

import com.news.entitys.UserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    @Insert("INSERT INTO user(phone,user_name,password,user_ico,sign,power) VALUES (#{phone},#{userName},#{password},#{userIco},#{sign},#{power})")
    int createUser(@Param("phone") String phone,
                   @Param("userName") String userName,
                   @Param("password") String password,
                   @Param("userIco") String ico,
                   @Param("sign") String sign,
                   @Param("power") Byte power);

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    UserEntity getUserByName(@Param("userName") String userName);

    @Update("UPDATE user SET user_name=#{userName},password=pass#{password},user_ico=#{userIco},sign=#{sign},power=#{power}")
    int modifyUserByName(@Param("userName") String userName,
                         @Param("password") String password,
                         @Param("userIco") String ico,
                         @Param("sign") String sign,
                         @Param("power") Byte power);
}