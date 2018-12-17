package com.comms.comms_info.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comms.comms_info.model.Comms;
import com.comms.comms_info.model.Kpis;
import com.comms.comms_info.model.Metrics;
import com.comms.comms_info.service.KpisService;
import com.comms.comms_info.service.LoadDataService;
import com.comms.comms_info.service.MetricsService;

@RestController
@RequestMapping("/")
public class CommsController {

	@Autowired
	private MetricsService metricsService;

	@Autowired
	private KpisService kpisService;

	@Autowired
	private LoadDataService loadDataService;

	

	/**
	 * TODO: Copy file from url to new file in folder Replace necessary characters
	 * to read as Json and serialize it Convert to Java Object? GSON/Jackson ?
	 * @param date
	 * @throws IOException
	 */
	@GetMapping("/{date}")
	public void loadCommsData(@PathVariable String date) {

		String destinAddress = "tempJson.json";
		
		loadDataService.extractJsonFile(date, destinAddress);
		
		loadDataService.modifyJsonFile(destinAddress);
	
		//Replace characters to make the file a readable Json
		
		//Put this in an additional method and object
		
		
	}

	@GetMapping("/metrics")
	public Metrics returnMetricResults(@RequestBody List<Comms> commsData) {

		/**
		 * TODO change input, make method to go get file Json from data Use @RequestBody
		 * to test metrics methods
		 */

		Metrics metrics1 = new Metrics();

		// 1. Rows with Missing fields - Check for missing fields
		metrics1.setMissingFields(metricsService.getNumberRowsWithMissingFields(commsData));

		// 2. Number of messages with blank content
		metrics1.setBlankContentMessages(metricsService.getNumberOfMsgsWithBlankContent(commsData));

		// 3. Number of rows with fields errors
		metrics1.setFieldErrors(metricsService.getNumberRowsWithFieldErrors(commsData));

		// 4. Number of calls origin/destination grouped by country code
		metrics1.setCallsByCountry(metricsService.getNumberOfCallsByCC(commsData));

		// 5. Relationship between OK/KO calls
		metrics1.setOkKoRelationship(metricsService.getRelationshipBetweenOKKOCalls(commsData));

		// 6. Average call duration grouped by country code
		metrics1.setAvgCallDurationByCountry(metricsService.getAvgCallDurationByCC(commsData));

		// 7. Word occurrence ranking for the given words in message_content field
		metrics1.setWordHierarqchy(metricsService.getWordOccurrenceRanking(commsData));

		return metrics1;
	}

	@GetMapping("/kpis")
	public Kpis returnKpis() {

		Kpis kpis1 = new Kpis();

		kpis1.setProcessedJSONFiles(loadDataService.getProcessedJsonFilesNumber());

		kpis1.setTotalRows(loadDataService.getTotalRowsRead());

		return kpis1;
	}

}
