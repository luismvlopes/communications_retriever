package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.comms.comms_info.model.Comms;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoadDataService {

	private int processedJsonFilesCounter = 0;
	private int totalRowsRead = 0;
	private String tempFileAddress;

//	Instant start = null;
//	Instant finish = null;
//	Long timeElapsed = Duration.between(start, finish).toMillis();
//	Map<Integer, Long> durationJsonProcess = new HashMap<>();

//	public void countInitialTime() {
//		this.start = Instant.now();
//	}
//
//	public void countFinishTime() {
//		this.finish = Instant.now();
//	}
//
//	public Map<Integer, Long> getDurationOfJsonProcess() {
//		durationJsonProcess.put(processedJsonFilesCounter, timeElapsed);
//		return durationJsonProcess;
//	}

	public void extractJsonFile(String date, String destinAdress) {

		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";
		URL url;

		try {

			url = new URL(fileURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			File tempFile = new File(destinAdress);
			System.out.println("tempJson.json file created in project root directory");

			FileWriter writer = new FileWriter(tempFile);

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				this.totalRowsRead++;

				writer.write(inputLine);
			}
			writer.close();
			in.close();

			tempFile.deleteOnExit();

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.processedJsonFilesCounter++;
	}

	public void modifyJsonFile(String fileAddress) {

		File fileToBeModified = new File(fileAddress);

		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {

			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();
			String newContent = "[" + line.replaceAll("\\}\\{", "\\},\\{") + "]";

			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public List<Comms> accessDataFile() {

		BufferedReader reader = null;
		;
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

	public int getProcessedJsonFilesNumber() {
		return processedJsonFilesCounter;
	}

	public int getTotalRowsRead() {
		return totalRowsRead;
	}

	public void setTempFileAddress(String tempFileAddress) {
		this.tempFileAddress = tempFileAddress;
	}

}
