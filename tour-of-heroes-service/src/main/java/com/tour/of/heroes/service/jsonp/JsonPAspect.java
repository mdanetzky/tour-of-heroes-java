package com.tour.of.heroes.service.jsonp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by matthias on 04/03/2017.
 */
@Aspect
@Component
public class JsonPAspect {

    @Around("execution(@com.tour.of.heroes.service.jsonp.JsonP * *(..))")
    public Object handleJsonPCallback(ProceedingJoinPoint joinPoint) throws Throwable {
        String callback = getCallback();
        Object result = joinPoint.proceed();
        if (!(result instanceof String)) {
            throw new Exception("JsonP annotated method must return String");
        }
        return packAsJsonpResponse(callback, (String) result);
    }

    private String getCallback() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = (servletRequestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        return request.getParameter("callback");
    }

    private static String packAsJsonpResponse(String callback, String response) {
        if (callback != null) {
            return callback + "(" + response + ");";
        }
        return response;
    }
}