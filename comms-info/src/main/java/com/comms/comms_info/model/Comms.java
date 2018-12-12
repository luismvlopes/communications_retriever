package com.comms.comms_info.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

	/**
	 * TODO: See annotation to solve problem where message_type = ""
	 * @author luislopes
	 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY,
		property = "message_type")
@JsonSubTypes({ @Type(value = Call.class, name = "CALL"), @Type(value = Msg.class, name = "MSG")})
public abstract class Comms {

	private String messageType;
	private Long timestamp;
	private Long origin;
	private Long destination;

	public Comms() {
	};

	@JsonProperty("message_type")
	public String getMessageType() {
		return messageType;
	}

	//@JsonProperty("message_type")
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@JsonProperty("timestamp")
	public Long getTimestamp() {
		return timestamp;
	}

	//@JsonProperty("timestamp")
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("origin")
	public Long getOrigin() {
		return origin;
	}

	//@JsonProperty("origin")
	public void setOrigin(Long origin) {
		this.origin = origin;
	}

	@JsonProperty("destination")
	public Long getDestination() {
		return destination;
	}

	//@JsonProperty("destination")
	public void setDestination(Long destination) {
		this.destination = destination;
	}

}
