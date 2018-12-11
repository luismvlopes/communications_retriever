package com.comms.comms_info.model;

import java.util.List;

public class KpisDTO {

	private int processedJSONFiles;
	private int totalRows;
	private int totalCalls;
	private int totalMessages;
	private int totalOriginCountryCodes;
	private int totalDestinationCountryCodes;
	private List<Integer> durationJSONProcess;
	
	public KpisDTO() {}

	public KpisDTO(int processedJSONFiles, int totalRows, int totalCalls, int totalMessages, int totalOriginCountryCodes,
			int totalDestinationCountryCodes, List<Integer> durationJSONProcess) {
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

	public List<Integer> getDurationJSONProcess() {
		return durationJSONProcess;
	}

	public void setDurationJSONProcess(List<Integer> durationJSONProcess) {
		this.durationJSONProcess = durationJSONProcess;
	};
	
	
}
