package org.ckr.msdemo.exception.util;

import org.ckr.msdemo.exception.ApplicationException;


/**
 * Created by Administrator on 2017/10/15.
 */
public class ExceptionTestUtil {

    /**
     * Check whether an {@link ApplicationException} include a error message.
     *
     * @param appExp  The application exception
     * @param expectedMsgCode    The message code
     * @param expectedMessage    The message
     * @return   Return true if the exception include the error message. Otherwise return false.
     */
    public static boolean checkErrorMsg(ApplicationException appExp,
                                        String expectedMsgCode,
                                        String expectedMessage) {



        if (appExp.getMessageList() == null) {
            return false;
        }

        for (ApplicationException.ExceptionMessage errorMessage : appExp.getMessageList()) {

            if (expectedMsgCode.equals(errorMessage.getMessageCode())
                && expectedMessage.equals(errorMessage.getMessage())) {
                return true;
            }
        }
        return false;
    }
}
