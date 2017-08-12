package org.ckr.msdemo.exception;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public class ReThrownApplicationException extends ApplicationException {

    public ReThrownApplicationException(String shortDescription, String exceptionId) {
        super(shortDescription, false);
        this.exceptionId = exceptionId;
    }


    public ReThrownApplicationException addMessage(String msgCode, String message) {

        ExceptionMessage expMsg = new ExceptionMessage(msgCode, message);

        messageList.add(expMsg);
        return this;
    }
}
