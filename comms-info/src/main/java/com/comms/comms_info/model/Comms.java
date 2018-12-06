package com.comms.comms_info.model;

public class Comms {

	private String messageType;
	private String timestamp;
	private String origin;
	private String destination;
	private Integer duration;
	private String status_code;
	private String status_description;
	private String message_content;	
	private String message_status;
	
	
	public Comms(String messageType, String timestamp, String origin, String destination, Integer duration,
			String status_code, String status_description, String message_content, String message_status) {
		super();
		this.messageType = messageType;
		this.timestamp = timestamp;
		this.origin = origin;
		this.destination = destination;
		this.duration = duration;
		this.status_code = status_code;
		this.status_description = status_description;
		this.message_content = message_content;
		this.message_status = message_status;
	}


	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public String getStatus_code() {
		return status_code;
	}


	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}


	public String getStatus_description() {
		return status_description;
	}


	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}


	public String getMessage_content() {
		return message_content;
	}


	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}


	public String getMessage_status() {
		return message_status;
	}


	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}
	
		
}
