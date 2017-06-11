package org.ckr.msdemo.exception.valueobject;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ErrorResponse implements Serializable {
	
	
	private static final long serialVersionUID = 1687427709624458223L;
	
	private String exceptionId;
	private List<ErrorMessage> messageList;
	
	
	
	
	public String getExceptionId() {
		return exceptionId;
	}




	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}




	public List<ErrorMessage> getMessageList() {
		return messageList;
	}




	public void addMessage(String msgCode, String msg) {
		if(this.messageList == null){
			this.messageList = new ArrayList<ErrorMessage>();
		}
		
		ErrorMessage expMsg = new ErrorMessage(msgCode, msg);
		
		messageList.add(expMsg);
		
	}


	public Date getServerTime(){
		return new Date(System.currentTimeMillis());
	}
	

	public static class ErrorMessage implements Serializable{

		private static final long serialVersionUID = -8659613208164591992L;
		
		private String messageCode=null;
		private String message=null;
		
		public ErrorMessage(){
			super();
		}
		
		public ErrorMessage(String messageCode, String message){
			this.message = message;
			this.messageCode = messageCode;
		}
		
		
		public String getMessageCode() {
			return messageCode;
		}
		
		public String getMessage() {
			return message;
		}
		
		
		
	}
	
}
