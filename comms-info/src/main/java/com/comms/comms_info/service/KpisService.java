package com.comms.comms_info.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Kpis;

@Service
public class KpisService {

	@Autowired
	private LoadDataService loadDataService;

	@Autowired
	private MetricsService metricsService;

	public Kpis getKpis() {

		Kpis kpis1 = new Kpis();

		kpis1.setProcessedJSONFiles(metricsService.getProcessedJsonFilesNumber());
		kpis1.setTotalRows(metricsService.getCommsData().size());
		kpis1.setTotalCalls(metricsService.getCallsCounter());
		kpis1.setTotalMessages(metricsService.getMessagesCounter());
		kpis1.setTotalOriginCountryCodes(metricsService.getOriginCountryCodesSet().size());
		kpis1.setTotalDestinationCountryCodes(metricsService.getDestinCountryCodesSet().size());
		kpis1.setDurationJSONProcess(getDurationJSONProcesses());

		return kpis1;
	}
	
	

	private Map<Integer, Long> getDurationJSONProcesses() {

		Map<Integer, Long> processesAndDurations = new HashMap<Integer, Long>();

			return processesAndDurations;
	}

}
