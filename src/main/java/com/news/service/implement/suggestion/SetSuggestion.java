package com.news.service.implement.suggestion;

import com.news.dao.news.SuggestionDao;
import com.news.entitys.SuggestionEntity;
import com.news.service.implement.ISuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vivian on 2018/04/16.
 */
@Service
public class SetSuggestion implements ISuggestion{

    @Autowired
    SuggestionDao suggestionDao;

    /**
     * 给我们发表反馈
     * @param content
     * @param userId
     * @return
     */
    public int createSuggestion(String content,int userId){
        return suggestionDao.setSugg(content,userId);
    }

}
