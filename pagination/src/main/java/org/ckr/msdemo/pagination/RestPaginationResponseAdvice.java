package org.ckr.msdemo.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by Administrator on 2017/7/8.
 */
@RestControllerAdvice()
public class RestPaginationResponseAdvice implements ResponseBodyAdvice<Object> {

    private Logger LOG = LoggerFactory.getLogger(RestPaginationResponseAdvice.class);


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return PaginationContext.getQueryResponse() != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        PaginationContext.setRestPaginationResponse(response);

        return body;
    }
}
