package com.comms.comms_info.controller;

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
	private LoadDataService loadDataService;

	@Autowired
	private KpisService kpisService;

	@GetMapping("/{date}")
	public void loadCommsData(@PathVariable String date) {

		loadDataService.loadAndModifyJson(date);
	}

	@GetMapping("/metrics")
	public Metrics returnMetricResults(@RequestBody List<Comms> commsData) {

		return metricsService.getMetrics(commsData);
	}

	@GetMapping("/kpis")
	public Kpis returnKpis() {

		return kpisService.getKpis();
	}

}
