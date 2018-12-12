package com.comms.comms_info.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Call extends Comms {

	private Integer durationInt;
	private String statusCode;
	private String statusDescription;

	public Call() {
	}

	@JsonProperty("duration")
	public Integer getDuration() {
		return durationInt;
	}

	@JsonProperty("duration")
	public void setDuration(Integer duration) {
		this.durationInt = duration;
	}

	@JsonProperty("status_code")
	public String getStatusCode() {
		return statusCode;
	}

	@JsonProperty("status_code")
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("status_description")
	public String getStatusDescription() {
		return statusDescription;
	}

	@JsonProperty("status_description")
	public void setStatusDescription(String statuDescription) {
		this.statusDescription = statuDescription;
	}

}
