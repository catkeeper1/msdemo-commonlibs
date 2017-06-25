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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/6/11.
 */
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    public static final String EXCEPTION_ID = "EXCEPTION_ID";
    public static final String EXCEPTION_MSG_CODE = "EXCEPTION_CODE";
    public static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";

    public static ResponseEntity<ErrorResponse> handleException(Throwable exp,
                                                 AbstractMessageSource messageSource) {

        LOG.debug("start to handle exception");

        BaseException be = null;

        if (exp instanceof BaseException) {

            be = (BaseException) exp;

        } else {
            be = (BaseException) getCause(exp, BaseException.class);
        }

        if (be == null) {
            be = new SystemException("System exception.", exp);
        }

        LOG.error("exception was caught:", be);

        HttpHeaders headers = new HttpHeaders();

        headers.add(EXCEPTION_ID, be.getExceptionId());

        HttpServletRequest httpRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Locale locale = RequestContextUtils.getLocale(httpRequest);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setExceptionId(be.getExceptionId());

        if (be instanceof ApplicationException) {
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

    public static Throwable getCause(Throwable exp, Class<?> clazz) {

        Throwable tmp = exp;

        while (tmp != null) {
            if (clazz.isInstance(tmp)) {
                return tmp;
            }

            tmp = tmp.getCause();
        }

        return null;
    }

}
