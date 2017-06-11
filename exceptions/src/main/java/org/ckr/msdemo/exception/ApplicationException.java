package org.ckr.msdemo.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicationException extends BaseException {

    private static final long serialVersionUID = 1799296168836812569L;

    List<ExceptionMessage> messageList = new ArrayList<>();

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

        StringBuilder builder = new StringBuilder();

        for(Object parm : expMsg.getMessageParams()) {

            if(parm == null) {
                builder.append("null");
            } else {
                builder.append(parm.toString());
            }

            builder.append(" ");

        }

        return builder.toString();
    }

    private String getShortDescription() {
        StringBuilder result = new StringBuilder();

        result.append("exception ID:")
                .append(getExceptionID())
                .append("\r\n");

        for (ExceptionMessage expMsg : messageList) {
            result.append("message code:" + expMsg.getMessageCode() + "  ")
                    .append("message params:" + printParams(expMsg) + " \r\n");
        }

        if(messageList.isEmpty()) {
            result.append("\r\n");
        }

        return result.toString();

    }

    public void addMessage(String msgCode, Object[] params) {

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
            messageParams = params;
        }

        public String getMessageCode() {
            return messageCode;
        }

        public Object[] getMessageParams() {
            return messageParams;
        }

    }
}
