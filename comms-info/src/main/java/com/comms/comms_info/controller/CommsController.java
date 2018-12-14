package com.comms.comms_info.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import com.comms.comms_info.service.MetricsService;

@RestController
@RequestMapping("/")
public class CommsController {

	@Autowired
	private MetricsService metricsService;

	@Autowired
	private KpisService kpisService;

	private int processedJsonFilesCounter = 0;
	private int totalRowsead = 0;

	/**
	 * TODO: Copy file from url to new file in folder
	 * Replace necessary characters to read as Json
	 * Convert to Java Object?
	 * 
	 * 
	 * @param date
	 */
	@GetMapping("/{date}")
	public void loadCommsData(@PathVariable String date) {

		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";

		processedJsonFilesCounter++;
		
		try {
		URL url = new URL(fileURL);
		InputStream in = url.openStream();
		//Files.copy(in, Paths.get("/test/testJsonFile.txt"), StandardCopyOption.REPLACE_EXISTING);
		
		} catch(IOException e) {
			System.out.println("Exception in IO");
			e.printStackTrace();
		}
		

//			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//
//			String inputLine;
//			while((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
//				totalRowsead++;
//				
//			}
//			in.close();
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("GET");
//			connection.connect();
//			int responseCode = connection.getResponseCode();
//
//			System.out.println("Connection established");
//			
//			connection.disconnect();
//			
//			System.out.println("Connection ended");

	
	}

	@GetMapping("/metrics")
	public Metrics returnMetricResults(@RequestBody List<Comms> commsData) {

		
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

		kpis1.setProcessedJSONFiles(processedJsonFilesCounter);

		kpis1.setTotalRows(totalRowsead);

		return kpis1;
	}

}
