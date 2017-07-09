package org.ckr.msdemo.pagination;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/7/8.
 */
@ControllerAdvice(annotations = {PaginationSupported.class})
public class RestControllerAdvice implements ResponseBodyAdvice<Object>, RequestBodyAdvice {

    private Map<Method, Boolean> supportedMethodsMap = new ConcurrentHashMap<>();

    private boolean supported(Method method) {

        Boolean result = supportedMethodsMap.get(method);

        if (result == null) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            result = supported(annotations);

            supportedMethodsMap.put(method, result);

        }
        return result;
    }

    private boolean supported(Annotation[] annotations) {
        if (annotations == null) {
            return false;
        }

        for(Annotation annotation : annotations) {
            if (PaginationSupported.class.isAssignableFrom(annotation.annotationType())) {
               return true;
            }
        }

        return false;
    }

    //request body advice.
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        return supported(methodParameter.getMethod());

    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        PaginationContext.parseRestPaginationParameters();

        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    //response body advice

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return PaginationContext.getQueryRequest() != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {


        PaginationContext.setRestPaginationResponse(response);

        return body;
    }
}
