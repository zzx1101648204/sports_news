package com.news.service.implement;

import com.news.entitys.UserEntity;

/**
 * Created by zhl on 2018/3/28.
 */
public interface IUserService  {


    /**
     * 创建用户
     * @param
     * @return
     */
     int createUser(String phone,String userName,String password);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int userModify(UserEntity user);

    /**
     * 查找用户
     * @param userName
     * @return
     */
    UserEntity getUser(String userName);
    /**
     * 修改用户权限
     * @param userName
     * @param userPower
     * @return
     */
    int modifyUserAuth(String userName, Byte userPower);
}
