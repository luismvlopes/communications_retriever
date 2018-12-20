package com.comms.comms_info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comms.comms_info.model.Comms;
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

	private Long timeElapsed = null;

	@GetMapping("/{date}")
	public void loadCommsData(@PathVariable String date) {

//		loadDataService.countInitialTime();

		// Change destination to outside project
		String destinAddress = "tempJson.json";

		loadDataService.extractJsonFile(date, destinAddress);

		loadDataService.modifyJsonFile(destinAddress);

		loadDataService.setTempFileAddress(destinAddress);

//		loadDataService.countFinishTime();

	}

	@GetMapping("/metrics")
	public Metrics returnMetricResults(/* @RequestBody List<Comms> commsData */) {

		List<Comms> commsData = loadDataService.accessDataFile();

		Metrics metrics1 = new Metrics();

		metrics1.setMissingFields(metricsService.getNumberRowsWithMissingFields(commsData));

		metrics1.setBlankContentMessages(metricsService.getNumberOfMsgsWithBlankContent(commsData));

		metrics1.setFieldErrors(metricsService.getNumberRowsWithFieldErrors(commsData));

		metrics1.setCallsByCountry(metricsService.getNumberOfCallsByCC(commsData));

		metrics1.setOkKoRelationship(metricsService.getRelationshipBetweenOKKOCalls(commsData));

		metrics1.setAvgCallDurationByCountry(metricsService.getAvgCallDurationByCC(commsData));

		metrics1.setWordHierarqchy(metricsService.getWordOccurrenceRanking(commsData));

		return metrics1;
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
