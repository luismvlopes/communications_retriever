package com.comms.comms_info.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String home() {

		return "Welcome to the Communications info retriever! Let's check those calls... "
				+ "\nInsert here the date of the calls you want info" + "\n FORM to add date YYYYMMDD"
				+ "\n BUTTON to submit" + "\n BUTTON to check Metrics" + "\n BUTTON to check KPIs";
	}

}
