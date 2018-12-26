package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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

import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Call;
import com.comms.comms_info.model.Comms;
import com.comms.comms_info.model.Metrics;
import com.comms.comms_info.model.Msg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MetricsService {

	private List<Comms> commsData;
	private List<Comms> referenceCommsData;

	private String tempFileAddress = "tempJson.json";

	private int processedJSONFilesNumber;
	private int totalRowsCounter = 0;
	private int callsCounter = 0;
	private int messagesCounter = 0;

	private Set<String> originCountryCodesSet = new HashSet<>();
	private Set<String> destinCountryCodesSet = new HashSet<>();

	private Map<Integer, Long> durationJsonProcessesMap = new HashMap<>();
	private long timeElapsedMeasuring;

	public Metrics getMetrics(List<Comms> commsData) {

		Instant start = Instant.now();
		Metrics metrics1 = new Metrics();

		// commsData = accessDataFile();

		metrics1.setMissingFields(getNumberRowsWithMissingFields(commsData));
		metrics1.setBlankContentMessages(getNumberOfMsgsWithBlankContent(commsData));
		metrics1.setFieldErrors(getNumberRowsWithFieldErrors(commsData));
		metrics1.setCallsByCountry(getNumberOfCallsByCC(commsData));
		metrics1.setOkKoRelationship(getRelationshipBetweenOKKOCalls(commsData));
		metrics1.setAvgCallDurationByCountry(getAvgCallDurationByCC(commsData));
		metrics1.setWordHierarqchy(getWordOccurrenceRanking(commsData));

		Instant finish = Instant.now();

		timeElapsedMeasuring = Duration.between(start, finish).toMillis();
		System.out.println("Time elapsed measuring: " + timeElapsedMeasuring);

//		updateProcessedJsonFilesNumber(commsData);

		return metrics1;
	}

	//Method used in Kpis...
	public void updateProcessedJsonFilesNumber(List<Comms> commsData) {

		if (referenceCommsData == null) {
			processedJSONFilesNumber = 1;
			referenceCommsData = commsData;
			addRowsToCounter(commsData);
			return;
		}
		// TODO Improve more this comparing instruction...
		if (referenceCommsData.size() == commsData.size()) {
			System.out.println("processed Json file number should not update... Testing 'equals' method");
			return;
		}

		referenceCommsData = commsData;
		processedJSONFilesNumber++;
		addRowsToCounter(commsData);

	}

	private List<Comms> accessDataFile() {

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

			System.out.println("Communication type: " + communication.getMessageType());

			if (communication instanceof Call) {

				// Create method for counting Calls
				this.callsCounter++;

				if (communication.getMessageType() == "" || communication.getTimestamp() == null
						|| communication.getOrigin() == null || communication.getDestination() == null
						|| ((Call) communication).getDuration() == null || ((Call) communication).getStatusCode() == ""
						|| ((Call) communication).getStatusDescription() == "") {
					rowsWithMissingFields++;
				}
			}

			if (communication instanceof Msg) {

				msgCounter++;

				this.messagesCounter++;
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

		/**
		 * Field errors are considered: Type of message different from CALL or MSG
		 * Message Type: OK or KO Message Status: DELIVERED or SEEN
		 */

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
				originCountryCodesSet.add(originCC);

				String destinCC = call1.getDestination().toString().substring(0, 3);
				destinCountryCodesSet.add(destinCC);

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

	public int getProcessedJsonFilesNumber() {
		return processedJSONFilesNumber;
	}

	private void addRowsToCounter(List<Comms> commsData) {
		this.totalRowsCounter += commsData.size();
	}

	public Map<Integer, Long> recordDurationEachJSONProcess() {
		return durationJsonProcessesMap;
	}

	public int getCallsCounter() {
		return callsCounter;
	}

	public int getMessagesCounter() {
		return messagesCounter;
	}

	public Set<String> getOriginCountryCodesSet() {
		return originCountryCodesSet;
	}

	public Set<String> getDestinCountryCodesSet() {
		return destinCountryCodesSet;
	}

	public Map<Integer, Long> getDurationOfJsonProcess() {
		return durationJsonProcessesMap;
	}

	public long getTimeElapsedMeasuring() {
		return timeElapsedMeasuring;
	}

	public List<Comms> getCommsData() {
		return commsData;
	}

	public int getTotalRowsCounter() {
		return totalRowsCounter;
	}

}
