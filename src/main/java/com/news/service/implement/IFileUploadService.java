package com.news.service.implement;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Created by:  Ju Harry
 * Date: 2018/4/14 22:18
 */

public interface IFileUploadService {
    /**
     * 上传新闻
     * @param request
     * @return
     */
    String upload(HttpServletRequest request);
}
