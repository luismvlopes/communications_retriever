package com.comms.comms_info.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comms.comms_info.model.Call;
import com.comms.comms_info.model.Comms;
import com.comms.comms_info.model.KpisDTO;

@RestController
@RequestMapping("/")
public class CommsController {

	@GetMapping("/{date}")
	public void loadCommsData(@RequestBody @PathVariable String date) {

		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";

		try {

			URL url = new URL(fileURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.connect();
			int responseCode = connection.getResponseCode();

//			if(responseCode != 200) throw new RuntimeException("HttpResponseCode: " + responseCode);
//			else {
//				Scanner sc = new Scanner(url.openStream());
//				String inline = "";
//				while(sc.hasNext()) {
//					 inline += sc.nextLine();
//					
//				}
//			}

			// Extract and save JSON to file

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/metrics")
	public Call returnMetricResults(@RequestBody Comms commsData) {
		Call call = null;
		
		if (commsData instanceof Call) {
			 call = (Call) commsData;

		}
		System.out.println("emntra ");

		// MetricsDTO metrics1 = new MetricsDTO();
		// Insert data from original JSON:
		// 1. Rows with Missing fields - Check for missing fields

		/*
		 * int rowsWithMissingFields = 0; if(commsData.getMessageType() == "") {
		 * rowsWithMissingFields++; }
		 * 
		 * if(commsData.getMessageType() == "CALL") { if(commsData.getTimestamp() == ""
		 * || commsData.getOrigin() == "" || commsData.getDestination() == "" ||
		 * commsData.getDuration() == null || commsData.getStatus_code() == "" ||
		 * commsData.getStatus_description() == "") { rowsWithMissingFields++; } }
		 * 
		 * metrics1.setMissingFields(rowsWithMissingFields);
		 * 
		 * 
		 * //2. Messages with Blank Contents
		 * 
		 * int messagesWithBlackContent = 0; if(commsData.getMessage_content() == "") {
		 * messagesWithBlackContent++; }
		 * 
		 * metrics1.setBlankContentMessages(messagesWithBlackContent);
		 * 
		 * 
		 * //3. field Errors
		 * 
		 * int rowsWithFieldErrors = 0;
		 * 
		 * if(commsData.getMessageType() != "CALL" && commsData.getMessageType() !=
		 * "MSG" || commsData.getMessageType() == "CALL" && commsData.getStatus_code()
		 * != "OK" && commsData.getStatus_code() != "KO" || commsData.getMessageType()
		 * == "MSG" && commsData.getMessage_status() != "SEEN" &&
		 * commsData.getMessage_status() != "DELIVERED") { rowsWithFieldErrors++; }
		 * 
		 * metrics1.setFieldErrors(rowsWithFieldErrors);
		 */

		// 4. Number of calls origin/destination grouped by country code

		// Map with

		// 5. Relationship between OK/KO calls

		// 6. Average call duration by country

		// 7. Word occurrence ranking for the given words in message_content field.

		// metrics1.setOkKoRelationship(123456);

		return call;
	}

	@GetMapping("/kpis")
	public KpisDTO returnKpis() {

		KpisDTO kpis = new KpisDTO();

		/*
		 * Total number of processed JSON files
		 * 
		 * Total number of rows Total number of calls Total number of messages Total
		 * number of different origin country codes
		 * (https://en.wikipedia.org/wiki/MSISDN) Total number of different destination
		 * country codes (https://en.wikipedia.org/wiki/MSISDN) Duration of each JSON
		 * process
		 */

		// Mock Kpis
		kpis.setTotalRows(123);
		kpis.setTotalCalls(234);
		kpis.setTotalMessages(23);
		kpis.setTotalMessages(123);
		kpis.setTotalOriginCountryCodes(123);
		kpis.setTotalDestinationCountryCodes(232);

		List<Integer> duration = new LinkedList<Integer>();
		kpis.setDurationJSONProcess(duration);

		return kpis;
	}

}
