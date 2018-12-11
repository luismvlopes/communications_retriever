package com.comms.comms_info.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "message_type")
@JsonSubTypes({ @Type(value = Call.class, name = "CALL"), @Type(value = Msg.class, name = "MSG") })
public abstract class Comms {

	private String messageType;
	private Long timestamp;
	private String origin;
	private String destination;

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
	public String getOrigin() {
		return origin;
	}

	//@JsonProperty("origin")
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@JsonProperty("destination")
	public String getDestination() {
		return destination;
	}

	//@JsonProperty("destination")
	public void setDestination(String destination) {
		this.destination = destination;
	}

}
