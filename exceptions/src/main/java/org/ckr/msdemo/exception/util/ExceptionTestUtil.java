package org.ckr.msdemo.exception.util;

import org.ckr.msdemo.exception.ApplicationException;
import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.springframework.context.MessageSource;

import java.util.Locale;


/**
 * Created by Administrator on 2017/10/15.
 */
public class ExceptionTestUtil {

    /**
     * Check whether an {@link ApplicationException} include a error message.
     *
     * @param messageSource  The message source to retrieve message shown to end user.
     * @param appExp  The application exception
     * @param expectedMsgCode    The message code
     * @param expectedMessage    The message
     * @return   Return true if the exception include the error message. Otherwise return false.
     */
    public static boolean checkErrorMsg(MessageSource messageSource,
                                        ApplicationException appExp,
                                        String expectedMsgCode,
                                        String expectedMessage) {

        ErrorResponse errorResponse = new ErrorResponse();

        RestExceptionHandler.updateMsgForResponse(errorResponse, appExp, messageSource, Locale.ENGLISH);

        if (errorResponse.getMessageList() == null) {
            return false;
        }

        for (ErrorResponse.ErrorMessage errorMessage : errorResponse.getMessageList()) {

            if (expectedMsgCode.equals(errorMessage.getMessageCode())
                && expectedMessage.equals(errorMessage.getMessage())) {
                return true;
            }
        }
        return false;
    }
}
