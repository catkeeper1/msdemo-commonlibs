package org.ckr.msdemo.pagination;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom HandlerInterceptor for parsing header and clear context data.
 */
public class PaginationInterceptor implements HandlerInterceptor {

    /**
     * This method is used to trigger {@link PaginationContext#parseRestPaginationParameters()} to parse
     * the patination request info from HTTP request.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  handler
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        PaginationContext.parseRestPaginationParameters();

        return true;
    }

    /**
     * This method is <strong>NOT</strong> used to modify response header for @ResponseBody and ResponseEntity methods.
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param handler      handler
     * @param modelAndView modelAndView
     * @throws Exception Exception
     * @see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-handlermapping-interceptor">spring-framework-reference</a>
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * This method will call {@link PaginationContext#clearContextData()} to make sure the data stored in
     * thread local in PaginationContext is cleared before the thread is released back to the pool.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  handler
     * @param ex       Exception
     * @throws Exception Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        PaginationContext.clearContextData();
    }
}
