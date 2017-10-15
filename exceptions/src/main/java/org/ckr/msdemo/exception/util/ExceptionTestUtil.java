package org.ckr.msdemo.exception.util;

import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;

import java.util.Locale;

/**
 * Created by Administrator on 2017/10/15.
 */
public class ExceptionTestUtil {

    public static boolean checkMsg(MessageSource messageSource,
                                ApplicationException appExp,
                                String expectedMsgCode,
                                String expectedMessage) {

        ErrorResponse errorResponse = new ErrorResponse();

        RestExceptionHandler.updateMsgForResponse(errorResponse, appExp, messageSource, Locale.ENGLISH);

        for(ErrorResponse.ErrorMessage errorMessage : errorResponse.getMessageList()) {

            if(expectedMsgCode.equals(errorMessage.getMessageCode()) &&
               expectedMessage.equals(errorMessage.getMessage())) {
                return true;
            }
        }
        return false;
    }
}
