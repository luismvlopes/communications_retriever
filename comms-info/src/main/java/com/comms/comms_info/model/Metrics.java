package com.comms.comms_info.model;

import java.util.HashMap;
import java.util.Map;

public class Metrics {

	private int missingFields;
	private int blankContentMessages;
	private int fieldErrors;
	private Map<String, Integer> callsByCountry;
	private double OkKoRelationship;
	private double avgCallDurationByCountry;
	private HashMap<Integer, String> WordHierarqchy;

	public Metrics() {
	};

	public Metrics(int missingFields, int blankContentMessages, int fieldErrors, HashMap<String, Integer> callsByCountry,
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

	public Map<String, Integer> getCallsByCountry() {
		return callsByCountry;
	}

	public void setCallsByCountry(Map<String, Integer> callsByCountry) {
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
