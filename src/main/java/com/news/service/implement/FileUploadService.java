package com.news.service.implement;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Created by:  Ju Harry
 * Date: 2018/4/14 20:42
 */
@Service
public class FileUploadService implements IFileUploadService{
    String rootPath = null;


    /**
     * 上传新闻
     * @param request
     * @return
     */
    @Override
    public String upload(HttpServletRequest request){
        ServletContext application =request.getServletContext();
        rootPath = application.getRealPath( "/" );
        ActionEnter actionEnter = new ActionEnter(request,rootPath);

        return actionEnter.invoke();
    }

}
