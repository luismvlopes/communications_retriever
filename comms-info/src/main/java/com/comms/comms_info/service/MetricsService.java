package com.comms.comms_info.service;

import java.util.HashMap;
import java.util.List;

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

		for (Comms comunication : commsData) {

			if (comunication instanceof Msg) {

				if (((Msg) comunication).getMessageContent() == "") {
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

	public HashMap<String, Integer> getNumberOfCallsByCC(List<Comms> commsData) {

		HashMap<String, Integer> callsByCC = new HashMap<String, Integer>();

		for (Comms communications : commsData) {

			if (communications instanceof Call) {

				Call call1 = (Call) communications;

				String originCC = call1.getOrigin().toString().substring(0, 2);
				String destinCC = call1.getDestination().toString().substring(0, 2);
				String originDestinCall = "Origin: " + originCC + ", Destination" + destinCC;

				if (callsByCC.containsKey(originDestinCall)) {

					Integer value = callsByCC.get(originDestinCall);
					value++;
					callsByCC.replace(originDestinCall, value);

				} else {

					callsByCC.put(originDestinCall, 1);
				}
			}
		}
		
		//TODO: Sort callsByCC map by originCC

		return callsByCC;
	}

}
