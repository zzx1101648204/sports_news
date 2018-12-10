package com.news.entitys;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Vivian on 2018/03/28.
 */
@Data
@ToString
public class AdminEntity {
    private int id;
    private String adminName;
    private String adminPassword;
    private int userPower;
    private int newsPower;
    private int adminPower;

}
