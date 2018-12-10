package com.news.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Vivian on 2018/03/29.
 */
@Data
@ToString
public class NewsDiscussEntity {
    private int id;
    private String userName;
    private int NewsId;
    private String discuss;
    private int isLike;
    private String discussTime;
}
