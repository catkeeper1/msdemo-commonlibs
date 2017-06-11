package org.ckr.msdemo.exception.handler;

import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.exception.BaseException;
import org.ckr.msdemo.exception.SystemException;
import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/11.
 */
public class RestExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    public static String EXCEPTION_ID = "EXCEPTION_ID";
    public static String EXCEPTION_MSG_CODE = "EXCEPTION_CODE";
    public static String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";

    public static ResponseEntity<ErrorResponse> handleException(Throwable e,
                                                 AbstractMessageSource messageSource) {

        LOG.debug("start to handle exception");

        BaseException be = null;

        if (e instanceof BaseException) {

            be = (BaseException) e;

        } else {
            be = (BaseException) getCause(e, BaseException.class);
        }

        if (be == null) {
            be = new SystemException("System exception.", e);
        }

        LOG.error("exception was caught:", be);

        HttpHeaders headers = new HttpHeaders();

        headers.add(EXCEPTION_ID, be.getExceptionID());

        HttpServletRequest httpRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Locale locale = RequestContextUtils.getLocale(httpRequest);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setExceptionId(be.getExceptionID());

        if(be instanceof ApplicationException) {
            ApplicationException appExp = (ApplicationException) be;
            for (int i = 0; i < appExp.getMessageList().size(); i++) {
                ApplicationException.ExceptionMessage expMsg = appExp.getMessageList().get(i);
                LOG.debug("return exception message with msg code {}", expMsg.getMessageCode());

                errorResponse.addMessage(expMsg.getMessageCode(),
                                         messageSource.getMessage(expMsg.getMessageCode(),
                                                                  expMsg.getMessageParams(),
                                                                  locale));


            }
        }

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public static Throwable getCause(Throwable e, Class<?> c) {

        Throwable tmp = e;

        while (tmp != null) {
            if (c.isInstance(tmp)) {
                return tmp;
            }

            tmp = tmp.getCause();
        }

        return null;
    }

}
