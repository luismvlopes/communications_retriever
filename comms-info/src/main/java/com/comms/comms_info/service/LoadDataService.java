package com.comms.comms_info.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LoadDataService {

	private String destinAddress = "tempJson.json";

	public void loadAndModifyJson(String date) {

		downloadJsonFile(date, destinAddress);
//		modifyJsonFile(destinAddress);

	}

	private void downloadJsonFile(String date, String fileAddress) {

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

	public void cleanJsonFile(String fileAddress) {

		File fileToBeModified = new File(fileAddress);

		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {

			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();
			String correctedCurlyBraces = "[" + line.replaceAll("\\}\\{", "\\},\\{") + "]";

			String flawsCheckedString = clearFlaws(correctedCurlyBraces);

			writer = new FileWriter(fileToBeModified);
			writer.write(flawsCheckedString);

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

	private String clearFlaws(String textFile) {

		String textFile1 = textFile.replaceAll("\\{\\}", "\\}");

		Map<String, String> jsonFlaws = new HashMap<String, String>();

		/*
		 * Insert flaws and corrections here
		 */
		jsonFlaws.put("\"status\": \"OK\"", "\"status_code\": \"OK\"");
		jsonFlaws.put("\": OK", "\": \"OK");

		String correctedText = textFile1;

		for (Map.Entry<String, String> entry : jsonFlaws.entrySet()) {

			if (correctedText.contains(entry.getKey())) {
				correctedText = correctedText.replaceAll(entry.getKey(), entry.getValue());
			}
		}

		return correctedText;
	}

}
