package com.comms.comms_info.model;

import java.util.HashMap;
import java.util.Map;

public class Metrics {

	private int missingFields;
	private int blankContentMessages;
	private int fieldErrors;
	private Map<String, Integer> callsByCountry;
	private String OkKoCallsRelationship;
	private Map<String, Integer> avgCallDurationByCountry;
	private Map<String, Integer> WordHierarqchy;

	public Metrics() {
	};

	public Metrics(int missingFields, int blankContentMessages, int fieldErrors, HashMap<String, Integer> callsByCountry,
			String okKoRelationship, Map<String, Integer> avgCallDurationByCountry, Map<String, Integer> wordHierarqchy) {
		this.missingFields = missingFields;
		this.blankContentMessages = blankContentMessages;
		this.fieldErrors = fieldErrors;
		this.callsByCountry = callsByCountry;
		OkKoCallsRelationship = okKoRelationship;
		this.avgCallDurationByCountry = avgCallDurationByCountry;
		WordHierarqchy = wordHierarqchy;
	}

	public int getMissingFields() {
		return missingFields;
	}

	public void setMissingFields(int missingFields) {
		this.missingFields = missingFields;
	}

	public int getBlankContentMessages() {
		return blankContentMessages;
	}

	public void setBlankContentMessages(int blankContentMessages) {
		this.blankContentMessages = blankContentMessages;
	}

	public int getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(int fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public Map<String, Integer> getCallsByCountry() {
		return callsByCountry;
	}

	public void setCallsByCountry(Map<String, Integer> callsByCountry) {
		this.callsByCountry = callsByCountry;
	}

	public String getOkKoRelationship() {
		return OkKoCallsRelationship;
	}

	public void setOkKoRelationship(String okKoRelationship) {
		OkKoCallsRelationship = okKoRelationship;
	}

	public Map<String, Integer> getAvgCallDurationByCountry() {
		return avgCallDurationByCountry;
	}

	public void setAvgCallDurationByCountry(Map<String, Integer> avgCallDurationByCountry) {
		this.avgCallDurationByCountry = avgCallDurationByCountry;
	}

	public Map<String, Integer> getWordHierarqchy() {
		return WordHierarqchy;
	}

	public void setWordHierarqchy(Map<String, Integer> wordHierarqchy) {
		WordHierarqchy = wordHierarqchy;
	}

}
