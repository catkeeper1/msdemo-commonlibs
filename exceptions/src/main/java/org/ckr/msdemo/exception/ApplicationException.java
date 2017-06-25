package org.ckr.msdemo.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicationException extends BaseException {

    private static final long serialVersionUID = 1799296168836812569L;

    private List<ExceptionMessage> messageList = new ArrayList<>();

    public ApplicationException() {
        super();
    }

    public ApplicationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        addMessage(arg0, null);
    }

    public ApplicationException(String arg0) {
        super(arg0);
        addMessage(arg0, null);
    }

    public ApplicationException(String arg0, Object[] params, Throwable arg1) {
        super(arg0, arg1);
        addMessage(arg0, params);
    }

    public ApplicationException(String arg0, Object[] params) {
        super(arg0);
        addMessage(arg0, params);
    }

    public List<ExceptionMessage> getMessageList() {
        return messageList;
    }

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

//    private String getShortDescription() {
//        StringBuilder result = new StringBuilder(100);
//
//        result.append("exception ID:")
//                .append(getExceptionId())
//                .append("\r\n");
//
//        for (ExceptionMessage expMsg : messageList) {
//            result.append("message code:" + expMsg.getMessageCode() + "  ")
//                    .append("message params:" + printParams(expMsg) + " \r\n");
//        }
//
//        if (messageList.isEmpty()) {
//            result.append("\r\n");
//        }
//
//        return result.toString();
//
//    }

    final public void addMessage(String msgCode, Object[] params) {

        ExceptionMessage expMsg = new ExceptionMessage(msgCode, params);

        messageList.add(expMsg);

    }

    public static class ExceptionMessage implements Serializable {


        private static final long serialVersionUID = 887496710964984427L;

        private String messageCode = null;

        private Object[] messageParams = null;

        /*public ExceptionMessage(){
            super();
        }*/

        public ExceptionMessage(String code, Object[] params) {
            messageCode = code;
            if (params != null) {
                messageParams = new Object[params.length];
                System.arraycopy(params, 0, this.messageParams, 0, params.length);
            }

        }

        public String getMessageCode() {
            return messageCode;
        }

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

    }
}
