package com.comms.comms_info.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Call;
import com.comms.comms_info.model.Comms;
import com.comms.comms_info.model.Msg;

@Service
public class MetricsService {

	public int getNumberRowsWithMissingFields(List<Comms> commsData) {

		int rowsWithMissingFields = 0;

		for (Comms communication : commsData) {

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
					break;
				}
			}
		}
		return rowsWithMissingFields;
	}

	public int getNumberOfMsgsWithBlankContent(List<Comms> commsData) {

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

	public int getNumberRowsWithFieldErrors(List<Comms> commsData) {

		int rowsWithFieldErrors = 0;

		for (Comms communication : commsData) {

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

	public Map<String, Integer> getNumberOfCallsByCC(List<Comms> commsData) {

		Map<String, Integer> callsByCC = new HashMap<String, Integer>();

		for (Comms communications : commsData) {

			if (communications instanceof Call) {

				Call call1 = (Call) communications;

				if (call1.getOrigin() == null || call1.getDestination() == null) {
					continue;
				}

				String originCC = call1.getOrigin().toString().substring(0, 2);
				String destinCC = call1.getDestination().toString().substring(0, 2);
				String originDestinCall = "Orig: " + originCC + ", Dest: " + destinCC;

				Integer count = callsByCC.get(originDestinCall);

				if (count == null) {
					callsByCC.put(originDestinCall, 1);
				} else {
					callsByCC.put(originDestinCall, count + 1);
				}
			}
		}

		// TODO Verify if this sorting works
//		Map<String, Integer> sortedCalls = callsByCC.entrySet().stream()
//				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return sortMapByValue(callsByCC, true);
	}

	public String getRelationshipBetweenOKKOCalls(List<Comms> commsData) {

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

	public Map<String, Integer> getAvgCallDurationByCC(List<Comms> commsData) {

		Map<String, LinkedList<Integer>> countSumDuration = new HashMap<String, LinkedList<Integer>>();

		for (Comms communication : commsData) {
			if (communication instanceof Call) {

				if (communication.getOrigin() == null) {
					continue;
				}

				String countryCode = communication.getOrigin().toString().substring(0, 2);

				if (countSumDuration.containsKey(countryCode)) {

					LinkedList<Integer> callsDuration = countSumDuration.get(countryCode);
					callsDuration.add(((Call) communication).getDuration());
					countSumDuration.replace(countryCode, callsDuration);

				} else {

					LinkedList<Integer> newCallDurationList = new LinkedList<Integer>();
					newCallDurationList.add(((Call) communication).getDuration());
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

	public Map<String, Integer> getWordOccurrenceRanking(List<Comms> commsData) {

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

		System.out.println("Sorted map by keys: " + tempMap);

		return tempMap;
	}

}
