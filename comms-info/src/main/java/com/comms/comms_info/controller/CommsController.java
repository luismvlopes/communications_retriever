package com.comms.comms_info.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class CommsController {

	/*
	 *Define an HTTP endpoint that receives a date parameter (YYYYMMDD) 
	 *This method will be requested to select the JSON file to process 
	 *The URL to get the file will be https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_YYYYMMDD.json 
	 */
	@GetMapping("/")
	public void selectFileToProcess() {
		
	}
	
	/*
	 * The service will have an HTTP endpoint (/metrics) that returns a set of counters related with the processed JSON file
	 * 
	 * 
	 */
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World!";
	}
	
	/*
	 * The service will have an HTTP endpoint (/kpis) that returns a set of counters related with the service
	 * 
	 */
	
	
	
}
