package com.comms.comms_info.service;

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

			if (communication.getMessageType() == "" || communication.getTimestamp() == null
					|| communication.getOrigin() == null || communication.getDestination() == null) {
				rowsWithMissingFields++;
				break;
			}

			if (communication instanceof Call) {

				if (((Call) communication).getDuration() == null || ((Call) communication).getStatusCode() == ""
						|| ((Call) communication).getStatusDescription() == "") {
					rowsWithMissingFields++;
					break;
				}
			}

			if (communication instanceof Msg) {

				if (((Msg) communication).getMessageContent() == "" || ((Msg) communication).getMessageStatus() == "") {
					rowsWithMissingFields++;
					break;
				}
			}
		}
		return rowsWithMissingFields;
	}
	
}
