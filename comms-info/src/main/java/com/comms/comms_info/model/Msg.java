package com.comms.comms_info.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Msg extends Comms {

	private String messageContent;	
	private String messageStatus;
	
	public Msg(Comms comms) {
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("message_content")
	public String getMessageContent() {
		return messageContent;
	}

	@JsonProperty("message_content")
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	@JsonProperty("message_status")
	public String getMessageStatus() {
		return messageStatus;
	}

	@JsonProperty("message_status")
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	
}
