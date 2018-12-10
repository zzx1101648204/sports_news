package com.news.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Vivian on 2018/04/16.
 */
@Data
@ToString
public class SuggestionEntity {
    private int id;
    private String content;
    private int userId;
    private String times;

}
