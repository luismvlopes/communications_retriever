package com.comms.comms_info.controller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comms.comms_info.model.Comms;

@RestController
@RequestMapping("/")
public class CommsController {
	
	private List<Comms> commsData;

	/*
	 *Define an HTTP endpoint that receives a date parameter (YYYYMMDD) 
	 *This method will be requested to select the JSON file to process 
	 *The URL to get the file will be https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_YYYYMMDD.json 
	 */
	@PostMapping("/{date}")
	public void loadCommsData(@RequestBody @PathVariable String date) {
		
		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";

		
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("temporaryJSON.json");
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					fileOutputStream.write(dataBuffer, 0, bytesRead);
				}
					fileOutputStream.close();
							
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		commsData = new ArrayList<>();
		
		
		
	}
	
	/*
	 * The service will have an HTTP endpoint (/metrics) that returns a set of counters related with the processed JSON file
	 * 
	 * 
	 */
	@GetMapping("/metrics")
	public List<Comms> returnMetrics() {
		
		return null;
	}
	
	/*
	 * The service will have an HTTP endpoint (/kpis) that returns a set of counters related with the service
	 * 
	 */
	@GetMapping("/kpis")
	public String returnKpis() {
		
		return "Hey There!";
	}
	
	
	
}
