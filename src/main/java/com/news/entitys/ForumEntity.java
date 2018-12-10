package com.news.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Vivian on 2018/03/29.
 */
@Data
@ToString
public class ForumEntity {
    private int id;
    private int userId;
    private String forum;
    private String userName;
    private String forumTime;

}
