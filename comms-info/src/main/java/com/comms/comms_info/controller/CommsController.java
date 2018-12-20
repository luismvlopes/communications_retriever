package com.comms.comms_info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comms.comms_info.model.Kpis;
import com.comms.comms_info.model.Metrics;
import com.comms.comms_info.service.LoadDataService;
import com.comms.comms_info.service.MetricsService;

@RestController
@RequestMapping("/")
public class CommsController {

	@Autowired
	private MetricsService metricsService;

	@Autowired
	private LoadDataService loadDataService;

//	private Long timeElapsed = null;

	@GetMapping("/{date}")
	public void loadCommsData(@PathVariable String date) {

		loadDataService.loadAndModifyJson(date);

	}

	@GetMapping("/metrics")
	public Metrics returnMetricResults() {

		return metricsService.getMetrics();
	}

	@GetMapping("/kpis")
	public Kpis returnKpis() {

		
		
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
