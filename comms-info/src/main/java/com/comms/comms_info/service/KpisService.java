package com.comms.comms_info.service;

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

		kpis1.setProcessedJSONFiles(loadDataService.getProcessedJsonFilesNumber());

		kpis1.setTotalRows(loadDataService.getTotalRowsRead());
		
		kpis1.setTotalCalls(metricsService.getCallsCounter());
		
		kpis1.setTotalMessages(metricsService.getMessagesCounter());
		
		kpis1.setTotalOriginCountryCodes(metricsService.getOriginCountryCodesSet().size());
		
		kpis1.setTotalDestinationCountryCodes(metricsService.getDestinCountryCodesSet().size());
		
//		kpis1.setDurationJSONProcess(loadDataService.getDurationOfJsonProcess());
		
		return kpis1;

	}



}
