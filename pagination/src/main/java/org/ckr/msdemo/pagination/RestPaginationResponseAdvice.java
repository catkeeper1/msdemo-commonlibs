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
 * Custom ResponseBodyAdvice to modify response header before body is written.
 * See: {@link PaginationContext#setRestPaginationResponse(ServerHttpResponse)}
 */
@RestControllerAdvice()
public class RestPaginationResponseAdvice implements ResponseBodyAdvice<Object> {

    private static Logger LOG = LoggerFactory.getLogger(RestPaginationResponseAdvice.class);


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return PaginationContext.getQueryResponse() != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        PaginationContext.setRestPaginationResponse(response);

        return body;
    }
}
