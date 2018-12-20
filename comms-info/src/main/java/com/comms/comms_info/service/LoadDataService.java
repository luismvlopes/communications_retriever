package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class LoadDataService {

	private String destinAddress = "tempJson.json";

	private int processedJsonFilesCounter = 0;
	private int totalRowsRead = 0;
	private long timeElapsedLoading;

	public void loadAndModifyJson(String date) {

		Instant start = Instant.now();

		extractJsonFile(date, destinAddress);

		modifyJsonFile(destinAddress);

		Instant finish = Instant.now();

		timeElapsedLoading = Duration.between(start, finish).toMillis();
		
		System.out.println("Time elapsed loading file: " + timeElapsedLoading);
	}

	private void extractJsonFile(String date, String fileAddress) {

		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";
		URL url;

		try {

			url = new URL(fileURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			File tempFile = new File(fileAddress);
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

	private void modifyJsonFile(String fileAddress) {

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

	public int getProcessedJsonFilesNumber() {
		return processedJsonFilesCounter;
	}

	public int getTotalRowsRead() {
		return totalRowsRead;
	}

	public long getTimeElapsedLoading() {
		return timeElapsedLoading;
	}

}
