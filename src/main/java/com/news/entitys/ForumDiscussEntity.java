package com.news.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Vivian on 2018/03/29.
 */
@Data
@ToString
public class ForumDiscussEntity {
    private int id;
    private int forumId;
    private String userName;
    private String discuss;
    private String discussTime;
}
