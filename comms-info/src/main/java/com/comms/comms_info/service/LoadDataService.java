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

	private String destinAddress = "tempJson.json";

	public void loadAndModifyJson(String date) {

		extractJsonFile(date, destinAddress);
		modifyJsonFile(destinAddress);

	}

	private void extractJsonFile(String date, String fileAddress) {

		String fileURL = "https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json";
		URL url;

		try {

			url = new URL(fileURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			File tempFile = new File(fileAddress);
			
			FileWriter writer = new FileWriter(tempFile);

			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				writer.write(inputLine);
			}
			writer.close();
			in.close();

			tempFile.deleteOnExit();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void modifyJsonFile(String fileAddress) {

		File fileToBeModified = new File(fileAddress);

		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {

			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();
			
			String correctedCurlyBraces = "[" + line.replaceAll("\\}\\{", "\\},\\{") + "]";
			
			String correctedQuotationMarks = correctedCurlyBraces.replaceAll("\": OK", "\": \"OK");
			
			String correctExtraCurlyB = correctedQuotationMarks.replaceAll("\\{\\}", "\\}");
			
			/*
			 * Improve strings above and methods to be able to read more incomplete json files
			 * 
			 */
			
			writer = new FileWriter(fileToBeModified);
			writer.write(correctExtraCurlyB);

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
}
