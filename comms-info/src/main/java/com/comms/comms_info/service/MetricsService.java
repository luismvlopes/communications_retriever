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

			if (communication instanceof Call) {

				if (communication.getMessageType() == "" || communication.getTimestamp() == null || 
						communication.getOrigin() == null || communication.getDestination() == null ||						
						((Call) communication).getDuration() == null || ((Call) communication).getStatusCode() == "" ||
						((Call) communication).getStatusDescription() == "") {
					rowsWithMissingFields++;
				}
			}

			if (communication instanceof Msg) {

				if (communication.getMessageType() == "" || communication.getTimestamp() == null || 
						communication.getOrigin() == null || communication.getDestination() == null ||	
						((Msg) communication).getMessageContent() == "" || ((Msg) communication).getMessageStatus() == "") {
					rowsWithMissingFields++;
					break;
				}
			}
		}
		return rowsWithMissingFields;
	}
	
	
	public int getNumberOfMsgsWithBlankContent(List<Comms> commsData) {
		
	int messagesWithBlackContent = 0; 
	
	for(Comms comunication : commsData) {
		
		if(comunication instanceof Msg) {
			
			if(((Msg) comunication).getMessageContent() == "") {
				messagesWithBlackContent++;
			}
		}
	}
	return messagesWithBlackContent;
	}
	
	
}
