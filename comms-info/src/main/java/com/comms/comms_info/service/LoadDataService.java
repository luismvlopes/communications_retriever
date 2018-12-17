package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class LoadDataService {

	private int processedJsonFilesCounter = 0;
	private int totalRowsRead = 0;
	
	
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
				System.out.println(inputLine);

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
	
	public int getProcessedJsonFilesNumber() {
		return processedJsonFilesCounter;
	}
	
	public int getTotalRowsRead() {
		return totalRowsRead;
	}

	
	public void modifyJsonFile(String fileAddress) {

		File fileToBeModified = new File(fileAddress);

		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();

			//Add initial brackets
			
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}

			String newContent = oldContent.replaceAll("}{", "},{");

			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				// writer2.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

}
