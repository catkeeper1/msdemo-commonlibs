package org.ckr.msdemo.exception;

import com.google.common.base.Joiner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This exception should be used when an user message should be shown to end users to explain what action should be
 * taken. For example if an mandatory is not input, program can throw an instance of this class and include an message
 * to prompt use to key in that mandatory field.
 */
public class ApplicationException extends BaseException {

    private static final long serialVersionUID = 1799296168836812569L;

    protected List<ExceptionMessage> messageList = new ArrayList<>();


    protected StackTraceElement[] stackWhenThrown = null;

    protected ApplicationException() {
        super();
    }

    protected ApplicationException(String shortDescription, boolean generateExpId) {
        super(shortDescription, generateExpId);
    }

    public ApplicationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ApplicationException(String arg0) {
        super(arg0);
    }

    /*public ApplicationException(String arg0, Object[] params, Throwable arg1) {
        super(arg0, arg1);
        addMessage(arg0, params);
    }

    public ApplicationException(String arg0, Object[] params) {
        super(arg0);
        addMessage(arg0, params);
    }*/


    public List<ExceptionMessage> getMessageList() {
        return messageList;
    }

    /*
    private String printParams(ExceptionMessage expMsg) {

        if (expMsg == null || expMsg.getMessageParams() == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(50);

        for (Object parm : expMsg.getMessageParams()) {

            if (parm == null) {
                builder.append("null");
            } else {
                builder.append(parm.toString());
            }

            builder.append(" ");

        }

        return builder.toString();
    }
    */
    /*
    private String getShortDescription() {
        StringBuilder result = new StringBuilder(100);

        result.append("exception ID:")
                .append(getExceptionId())
                .append("\r\n");

        for (ExceptionMessage expMsg : messageList) {
            result.append("message code:" + expMsg.getMessageCode() + "  ")
                    .append("message params:" + printParams(expMsg) + " \r\n");
        }

        if (messageList.isEmpty()) {
            result.append("\r\n");
        }

        return result.toString();

    }*/

    /**
     * Add one more user message to this exception.
     * @param msgCode The message code which is used to retrive message from messageSource for i18n. It is also can
     *                be used to indicate the error.
     * @param params  The values for parameters inside the message template.
     */
    public final ApplicationException addMessage(String msgCode, Object... params) {

        ExceptionMessage expMsg = new ExceptionMessage(msgCode, params);

        this.messageList.add(expMsg);

        return this;
    }

    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder( 1024);

        for (ExceptionMessage msg : messageList) {
            result.append("message code: ");
            result.append(msg.getMessageCode());
            result.append(".");

            if (msg.getMessageParams() != null) {
                result.append(" message parameters: [");

                Joiner.on(',').appendTo(result, msg.getMessageParams());
                result.append("]");
            }

            result.append("\r\n");

        }

        if (stackWhenThrown != null) {
            result.append("exception is thrown at:\r\n");

            //print 5 line only to tell where this exception is thrown.
            for (int ind = 0 ; ind < 5 && ind < stackWhenThrown.length; ind++) {
                result.append("\tat ");
                result.append(stackWhenThrown[ind]);
                result.append("\r\n");
            }
        }

        result.append(super.getMessage());

        return result.toString();
    }

    /**
     * Throw this exception if {@link #getMessageList()} return an non empty list.
     * This method is used for below scenario:
     * <ul>
     *     <li>There are multple validation rules.
     *     <li>For each rule, program will do one checking. If the checking failed, addMessage() will be called to
     *     add one message to the message list.
     *     <li>Call this method. If the message list is empty, that means all validations passed. If no, this
     *     exception will be thrown and all error messages will be shown to user.
     * </ul>
     */
    public void throwThisIfValid() {

        Thread.currentThread().getStackTrace();

        if(!this.messageList.isEmpty()) {
            this.stackWhenThrown = Thread.currentThread().getStackTrace();
            throw this;
        }
    }

    /**
     * This is an internal class used by {@link ApplicationException} to store user message data.
     * Every instance of this class represent one user message.
     *
     * @see ApplicationException
     */
    public static class ExceptionMessage implements Serializable {


        private static final long serialVersionUID = 887496710964984427L;

        private String messageCode = null;

        private Object[] messageParams = null;

        private String message = null;

        /*public ExceptionMessage(){
            super();
        }*/

        /**
         *
         * @param code The message code that will be used to retrieve user message from messageSource object.
         * @param params  The values of parameters of the message template.
         */
        public ExceptionMessage(String code, Object[] params) {
            this.messageCode = code;
            if (params != null) {
                this.messageParams = new Object[params.length];
                System.arraycopy(params, 0, this.messageParams, 0, params.length);
            }
            this.message = null;
        }

        /**
         *
         * @param code The code indicate the error
         * @param message The message that should be shown to end user. It is expected that the micro service that
         *                throw exception provide this message.
         */
        public ExceptionMessage(String code, String message) {
            this.messageCode = code;
            this.messageParams = null;
            this.message = message;
        }

        /**
         * The get method for message code.
         * @return The message code that will be used to retrieve user message from messageSource object.
         */
        public String getMessageCode() {
            return messageCode;
        }

        /**
         * The get method for message params.
         * @return the values of parameters of message template.
         */
        public Object[] getMessageParams() {

            if (this.messageParams != null) {
                int arrayLen = this.messageParams.length;

                Object[] result = new Object[arrayLen];
                Object[] srcParams = this.messageParams;

                System.arraycopy(srcParams, 0, result, 0, arrayLen);
                return result;
            }

            return null;
        }

        /**
         * Return he error message that will be shown to end user.
         * @return The error message that will be shown to end user.
         */
        public String getMessage() {
            return message;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExceptionMessage that = (ExceptionMessage) o;
            return Objects.equals(messageCode, that.messageCode) &&
                    Arrays.equals(messageParams, that.messageParams) &&
                    Objects.equals(message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(messageCode, messageParams, message);
        }
    }
}
