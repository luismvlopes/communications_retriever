package com.comms.comms_info.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String home() {

		return "Welcome to the Communications info retriever API!"
				+ "\n insert the date to load a Json file with communications records"
				+ "\n use the /metrics endpoint to return several metrics on the loaded Json file"
				+ "\n use the /kpis endpoint to return key performance indicators on this API";
	}
}
