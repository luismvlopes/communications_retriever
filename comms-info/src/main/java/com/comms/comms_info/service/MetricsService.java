package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Call;
import com.comms.comms_info.model.Comms;
import com.comms.comms_info.model.Metrics;
import com.comms.comms_info.model.Msg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MetricsService {

	@Autowired
	private LoadDataService loadDataService;
	
	private List<Comms> commsData;
	private List<List<Comms>> jsonDataFiles = new ArrayList<List<Comms>>();

	private String tempFileAddress = "tempJson.json";

	private int processedJSONFilesCounter = 0;
	private int totalRows = 0;
	private int totalCalls = 0;
	private int totalMsgs = 0;
	private int differentOriginCC = 0;
	private int differentDestinationCC = 0;

	private Map<Integer, Long> processesAndDurations = new HashMap<Integer, Long>();
	private long timeElapsedMeasuring;

	public Metrics getMetrics() {

		Instant start = Instant.now();

		Metrics metrics1 = new Metrics();

		commsData = accessDataFile();

		metrics1.setMissingFields(getNumberRowsWithMissingFields(commsData));
		metrics1.setBlankContentMessages(getNumberOfMsgsWithBlankContent(commsData));
		metrics1.setFieldErrors(getNumberRowsWithFieldErrors(commsData));
		metrics1.setCallsByCountry(getNumberOfCallsByCC(commsData));
		metrics1.setOkKoRelationship(getRelationshipBetweenOKKOCalls(commsData));
		metrics1.setAvgCallDurationByCountry(getAvgCallDurationByCC(commsData));
		metrics1.setWordHierarqchy(getWordOccurrenceRanking(commsData));

		Instant finish = Instant.now();

		timeElapsedMeasuring = Duration.between(start, finish).toMillis();
		
		updateProcessedJsonFilesCounter(commsData);

		return metrics1;
	}
	
	

	private List<Comms> accessDataFile() {

		loadDataService.cleanJsonFile(tempFileAddress);
		
		BufferedReader reader = null;

		String jsonArray = "";
		List<Comms> commsData = null;
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			reader = new BufferedReader(new FileReader(new File(tempFileAddress)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			jsonArray = reader.readLine();
			reader.close();
			commsData = objectMapper.readValue(jsonArray, new TypeReference<List<Comms>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commsData;
	}

	private int getNumberRowsWithMissingFields(List<Comms> commsData) {

		int rowsWithMissingFields = 0;
		int msgCounter = 0;
		int callCounter = 0;
		int numberComms = 0;

		for (Comms communication : commsData) {

			numberComms++;

			if (communication instanceof Call) {

				if (communication.getMessageType() == "" || communication.getTimestamp() == null
						|| communication.getOrigin() == null || communication.getDestination() == null
						|| ((Call) communication).getDuration() == null || ((Call) communication).getStatusCode() == ""
						|| ((Call) communication).getStatusDescription() == "") {
					rowsWithMissingFields++;
				}
			}

			if (communication instanceof Msg) {

				if (communication.getMessageType() == "" || communication.getTimestamp() == null
						|| communication.getOrigin() == null || communication.getDestination() == null
						|| ((Msg) communication).getMessageContent() == ""
						|| ((Msg) communication).getMessageStatus() == "") {
					rowsWithMissingFields++;
				}
			}
		}
		return rowsWithMissingFields;
	}

	private int getNumberOfMsgsWithBlankContent(List<Comms> commsData) {

		int messagesWithBlackContent = 0;

		for (Comms communication : commsData) {

			if (communication instanceof Msg) {

				if (((Msg) communication).getMessageContent() == "") {
					messagesWithBlackContent++;
				}
			}
		}

		return messagesWithBlackContent;
	}

	private int getNumberRowsWithFieldErrors(List<Comms> commsData) {

		int rowsWithFieldErrors = 0;

		for (Comms communication : commsData) {

			if (!communication.getMessageType().equals("CALL") && !communication.getMessageType().equals("MSG")) {
				rowsWithFieldErrors++;
				continue;
			}

			if (communication instanceof Call) {

				if (!((Call) communication).getStatusCode().equals("OK")
						&& !((Call) communication).getStatusCode().equals("KO")) {
					rowsWithFieldErrors++;
				}
			}

			if (communication instanceof Msg) {

				if (!((Msg) communication).getMessageStatus().equals("DELIVERED")
						&& (!((Msg) communication).getMessageStatus().equals("SEEN"))) {
					rowsWithFieldErrors++;
				}
			}
		}

		return rowsWithFieldErrors;
	}

	private Map<String, Integer> getNumberOfCallsByCC(List<Comms> commsData) {

		Map<String, Integer> callsByCC = new HashMap<String, Integer>();

		for (Comms communications : commsData) {

			if (communications instanceof Call) {

				Call call1 = (Call) communications;

				if (call1.getOrigin() == null || call1.getDestination() == null) {
					continue;
				}

				String originCC = call1.getOrigin().toString().substring(0, 3);

				String destinCC = call1.getDestination().toString().substring(0, 3);

				String originDestinCall = "Orig: " + originCC + ", Dest: " + destinCC;

				Integer count = callsByCC.get(originDestinCall);

				if (count == null) {
					callsByCC.put(originDestinCall, 1);
				} else {
					callsByCC.put(originDestinCall, count + 1);
				}
			}
		}

		return sortMapByValue(callsByCC, true);
	}

	private String getRelationshipBetweenOKKOCalls(List<Comms> commsData) {

		long OKcalls = 0;
		long KOcalls = 0;

		for (Comms communications : commsData) {

			if (communications instanceof Call) {

				String messageStatusCode = ((Call) communications).getStatusCode();

				switch (messageStatusCode) {
				case "OK":
					OKcalls++;
					break;
				case "KO":
					KOcalls++;
					break;
				case "K.O.":
					KOcalls++;
				default:
					continue;
				}
			}
		}

		return OKcalls + " OK, " + KOcalls + " KO";
	}

	private Map<String, Integer> getAvgCallDurationByCC(List<Comms> commsData) {

		Map<String, LinkedList<Integer>> countSumDuration = new HashMap<String, LinkedList<Integer>>();

		for (Comms communication : commsData) {
			if (communication instanceof Call) {

				if (communication.getOrigin() == null) {
					continue;
				}

				if (((Call) communication).getDuration() == null) {
					continue;
				}

				String countryCode = communication.getOrigin().toString().substring(0, 3);

				if (countSumDuration.containsKey(countryCode)) {

					LinkedList<Integer> callsDuration = countSumDuration.get(countryCode);
					Integer duration = Integer.valueOf(((Call) communication).getDuration().intValue());
					callsDuration.add(duration);
					countSumDuration.replace(countryCode, callsDuration);

				} else {

					LinkedList<Integer> newCallDurationList = new LinkedList<Integer>();
					Integer duration = Integer.valueOf(((Call) communication).getDuration().intValue());
					newCallDurationList.add(duration);
					countSumDuration.put(countryCode, newCallDurationList);
				}
			}
		}

		Map<String, Integer> avgCallDurationByCC = new HashMap<String, Integer>();

		Iterator<Entry<String, LinkedList<Integer>>> it = countSumDuration.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, LinkedList<Integer>> pair = (Map.Entry<String, LinkedList<Integer>>) it.next();

			int sumDurations = 0;
			for (int i = 0; i < pair.getValue().size(); i++) {
				sumDurations += pair.getValue().get(i);
			}
			int avgDuration = sumDurations / pair.getValue().size();

			avgCallDurationByCC.put(pair.getKey(), avgDuration);

		}

		return avgCallDurationByCC;
	}

	private Map<String, Integer> getWordOccurrenceRanking(List<Comms> commsData) {

		Map<String, Integer> wordRanking = new HashMap<String, Integer>();

		for (Comms communication : commsData) {

			if (communication instanceof Msg) {

				String msgContent = ((Msg) communication).getMessageContent();
				String[] words = msgContent.split(" ");

				for (String word : words) {
					if (word.matches("[a-zA-Z]+")) {

						Integer count = wordRanking.get(word);

						if (count == null) {
							wordRanking.put(word, 1);
						} else {
							wordRanking.put(word, count + 1);
						}
					}
				}
			}
		}

		return sortMapByValue(wordRanking, true);
	}

	public static Map<String, Integer> sortMapByValue(Map<String, Integer> hmap, boolean descendingOrder) {

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hmap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue().compareTo(o2.getValue()));
			}
		});

		Map<String, Integer> tempMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			tempMap.put(aa.getKey(), aa.getValue());
		}

		if (descendingOrder) {

			Map<String, Integer> reversedSortMap = new LinkedHashMap<>();

			tempMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					.forEachOrdered(entry -> reversedSortMap.put(entry.getKey(), entry.getValue()));

			tempMap = reversedSortMap;
		}

		return tempMap;
	}

	public void updateProcessedJsonFilesCounter(List<Comms> commsData) {

		if (jsonDataFiles.isEmpty()) {
			jsonDataFiles.add(commsData);
			processedJSONFilesCounter = jsonDataFiles.size();
			return;
		}

		for (int i = 0; i < jsonDataFiles.size(); i++) {
			if (commsData.size() == jsonDataFiles.get(i).size()) {

				for (int j = 0; j < commsData.size(); j++) {

					if (commsData.get(j).getTimestamp() == null || jsonDataFiles.get(i).get(j).getTimestamp() == null) {
						continue;
					}

					if (!commsData.get(j).getTimestamp().equals(jsonDataFiles.get(i).get(j).getTimestamp())) {
						jsonDataFiles.add(commsData);
						return;
					}
				}
				return;
			}
		}

		jsonDataFiles.add(commsData);
		processedJSONFilesCounter = jsonDataFiles.size();
	}

	public void calcTotalValues() {

		int rowsCounter = 0;
		int callsCounter = 0;
		int msgsCounter = 0;
		int originCCCounter = 0;
		int destinationCCCounter = 0;

		Set<String> originCountryCodesSet = new HashSet<>();
		Set<String> destinCountryCodesSet = new HashSet<>();

		for (int i = 0; i < jsonDataFiles.size(); i++) {
			rowsCounter += jsonDataFiles.get(i).size();

			for (int j = 0; j < jsonDataFiles.get(i).size(); j++) {
				if (jsonDataFiles.get(i).get(j).getMessageType().equals("MSG")) {
					msgsCounter++;
				}

				if (jsonDataFiles.get(i).get(j).getMessageType().equals("CALL")) {
					callsCounter++;
				}

				if (jsonDataFiles.get(i).get(j).getOrigin() == null
						|| jsonDataFiles.get(i).get(j).getDestination() == null) {
					continue;
				}

				originCountryCodesSet.add(jsonDataFiles.get(i).get(j).getOrigin().toString().substring(0, 3));
				destinCountryCodesSet.add(jsonDataFiles.get(i).get(j).getDestination().toString().substring(0, 3));
			}
		}

		this.totalRows = rowsCounter;
		this.totalCalls = callsCounter;
		this.totalMsgs = msgsCounter;
		this.differentOriginCC = originCountryCodesSet.size();
		this.differentDestinationCC = destinCountryCodesSet.size();

	}

	public Map<Integer, Long> calcDurationOfJsonProcess() {

		if (processedJSONFilesCounter == 0) {
			return null;
		}

		if (processesAndDurations.containsKey(processedJSONFilesCounter)) {
			return processesAndDurations;
		}

		processesAndDurations.put(processedJSONFilesCounter, timeElapsedMeasuring);

		return processesAndDurations;
	}

	public int getProcessedJsonFilesNumber() {
		return processedJSONFilesCounter;
	}

	public List<List<Comms>> getJsonDataFiles() {
		return jsonDataFiles;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalCalls() {
		return totalCalls;
	}

	public int getTotalMsgs() {
		return totalMsgs;
	}

	public int getDifferentOriginCC() {
		return differentOriginCC;
	}

	public int getDifferentDestinationCC() {
		return differentDestinationCC;
	}

	public long getTimeElapsedMeasuring() {
		return timeElapsedMeasuring;
	}

	public List<Comms> getCommsData() {
		return commsData;
	}

}
