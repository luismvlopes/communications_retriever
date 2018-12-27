package com.comms.comms_info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Kpis;

@Service
public class KpisService {

	@Autowired
	private MetricsService metricsService;

	public Kpis getKpis() {

		Kpis kpis1 = new Kpis();

		metricsService.calcTotalValues();

		kpis1.setProcessedJSONFiles(metricsService.getProcessedJsonFilesNumber());
		kpis1.setTotalRows(metricsService.getTotalRows());
		kpis1.setTotalCalls(metricsService.getTotalCalls());
		kpis1.setTotalMessages(metricsService.getTotalMsgs());
		kpis1.setTotalOriginCountryCodes(metricsService.getDifferentOriginCC());
		kpis1.setTotalDestinationCountryCodes(metricsService.getDifferentDestinationCC());
		kpis1.setDurationJSONProcess(metricsService.calcDurationOfJsonProcess());

		return kpis1;
	}
}
