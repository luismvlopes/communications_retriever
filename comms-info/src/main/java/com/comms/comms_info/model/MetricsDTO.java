package com.comms.comms_info.model;

import java.util.HashMap;

public class MetricsDTO {

	private int missingFields;
	private int blankContentMessages;
	private int fieldErrors;
	private int callsByCountry;
	private double OkKoRelationship;
	private double avgCallDurationByCountry;
	private HashMap<Integer, String> WordHierarqchy;
	
	public MetricsDTO() {};
	
	public MetricsDTO(int missingFields, int blankContentMessages, int fieldErrors, int callsByCountry,
			double okKoRelationship, double avgCallDurationByCountry, HashMap<Integer, String> wordHierarqchy) {
		this.missingFields = missingFields;
		this.blankContentMessages = blankContentMessages;
		this.fieldErrors = fieldErrors;
		this.callsByCountry = callsByCountry;
		OkKoRelationship = okKoRelationship;
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

	public int getCallsByCountry() {
		return callsByCountry;
	}

	public void setCallsByCountry(int callsByCountry) {
		this.callsByCountry = callsByCountry;
	}

	public double getOkKoRelationship() {
		return OkKoRelationship;
	}

	public void setOkKoRelationship(double okKoRelationship) {
		OkKoRelationship = okKoRelationship;
	}

	public double getAvgCallDurationByCountry() {
		return avgCallDurationByCountry;
	}

	public void setAvgCallDurationByCountry(double avgCallDurationByCountry) {
		this.avgCallDurationByCountry = avgCallDurationByCountry;
	}

	public HashMap<Integer, String> getWordHierarqchy() {
		return WordHierarqchy;
	}

	public void setWordHierarqchy(HashMap<Integer, String> wordHierarqchy) {
		WordHierarqchy = wordHierarqchy;
	}
	
		
}
