package org.ckr.msdemo.exception;



/**
 * The application exception that is used for re-throwning when the exception is transferred through remote
 * call.
 */
public class ReThrownApplicationException extends ApplicationException {

    private static final long serialVersionUID = -389413773965151203L;

    public ReThrownApplicationException(String shortDescription, String exceptionId) {
        super(shortDescription, false);
        this.exceptionId = exceptionId;
    }

    /**
     * Add message with message code and message.
     *
     * @param msgCode message code
     * @param message message
     * @return ReThrownApplicationException
     */
    public ReThrownApplicationException addMessage(String msgCode, String message) {

        ExceptionMessage expMsg = new ExceptionMessage(msgCode, message);

        messageList.add(expMsg);
        return this;
    }
}
