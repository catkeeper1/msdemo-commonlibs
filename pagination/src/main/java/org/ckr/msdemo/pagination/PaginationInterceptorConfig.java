package org.ckr.msdemo.pagination;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Add custom HandlerInterceptor {@link PaginationInterceptor} to InterceptorRegistry.
 */
@Configuration
public class PaginationInterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PaginationInterceptor());
    }
}
