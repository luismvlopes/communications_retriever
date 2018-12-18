package com.comms.comms_info.model;

import java.util.Map;

public class Kpis {

	private int processedJSONFiles;
	private int totalRows;
	private int totalCalls;
	private int totalMessages;
	private int totalOriginCountryCodes;
	private int totalDestinationCountryCodes;
	private Map<Integer, Long> durationJSONProcess;
	
	public Kpis() {}

	public Kpis(int processedJSONFiles, int totalRows, int totalCalls, int totalMessages, int totalOriginCountryCodes,
			int totalDestinationCountryCodes, Map<Integer, Long> durationJSONProcess) {
		this.processedJSONFiles = processedJSONFiles;
		this.totalRows = totalRows;
		this.totalCalls = totalCalls;
		this.totalMessages = totalMessages;
		this.totalOriginCountryCodes = totalOriginCountryCodes;
		this.totalDestinationCountryCodes = totalDestinationCountryCodes;
		this.durationJSONProcess = durationJSONProcess;
	}

	public int getProcessedJSONFiles() {
		return processedJSONFiles;
	}

	public void setProcessedJSONFiles(int processedJSONFiles) {
		this.processedJSONFiles = processedJSONFiles;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalCalls() {
		return totalCalls;
	}

	public void setTotalCalls(int totalCalls) {
		this.totalCalls = totalCalls;
	}

	public int getTotalMessages() {
		return totalMessages;
	}

	public void setTotalMessages(int totalMessages) {
		this.totalMessages = totalMessages;
	}

	public int getTotalOriginCountryCodes() {
		return totalOriginCountryCodes;
	}

	public void setTotalOriginCountryCodes(int totalOriginCountryCodes) {
		this.totalOriginCountryCodes = totalOriginCountryCodes;
	}

	public int getTotalDestinationCountryCodes() {
		return totalDestinationCountryCodes;
	}

	public void setTotalDestinationCountryCodes(int totalDestinationCountryCodes) {
		this.totalDestinationCountryCodes = totalDestinationCountryCodes;
	}

	public Map<Integer, Long> getDurationJSONProcess() {
		return durationJSONProcess;
	}

	public void setDurationJSONProcess(Map<Integer, Long> durationJSONProcess) {
		this.durationJSONProcess = durationJSONProcess;
	};
	
	
}
