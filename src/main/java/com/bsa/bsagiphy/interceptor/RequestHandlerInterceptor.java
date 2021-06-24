package com.bsa.bsagiphy.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class RequestHandlerInterceptor implements HandlerInterceptor {

    @Value("${api.validation-header.name}")
    private String validationHeaderName;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {
        if (request.getHeader(validationHeaderName) == null) {
            response.sendError(HttpStatus.FORBIDDEN.value(), String.format("Missing %s header", validationHeaderName));
            return false;
        }
        return true;
    }

}
