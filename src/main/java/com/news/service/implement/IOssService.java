package com.news.service.implement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IOssService {
    public void ossUploader(HttpServletRequest request, HttpServletResponse response);

    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
