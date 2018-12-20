package com.example.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author kalhara
 * @version 1.0
 * @since 2018-12-21
 */
@Component
public class DeleteImageInterceptor implements HandlerInterceptor {

    @Value("${am.qr.file.path}")
    private String path;

    @Autowired
    DeleteFile deleteFile;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        deleteFile.delete(path + response.getHeader("fileName"));
    }
}
