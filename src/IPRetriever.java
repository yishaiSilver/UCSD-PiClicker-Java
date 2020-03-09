/*
 * Helped from: 
 * https://www.baeldung.com/run-shell-command-in-java
 * https://alvinalexander.com/java/edu/pj/pj010016
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IPRetriever {
	
	private static final String BONJOUR_IP = "raspberrypi.local";
	private static final String IP_BEGINNING = "169";
	private static final int INDEX_OF_PI_IP = 1;
	
	public String getIP() {
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			if(!isWindows) {
				return BONJOUR_IP;
			}
			
			Process process = Runtime.getRuntime().exec("arp -a");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String totalOutput = "";
			String line = null;
			while((line = stdInput.readLine()) != null) {
				totalOutput += line;
			}
			
			System.out.println(totalOutput);
			System.out.println(findIPInString(totalOutput));
			return findIPInString(totalOutput);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String findIPInString(String wholeString){
		// Create list for all IPs that start with 169
		ArrayList<String> possibleIPs = new ArrayList<String>();
		
		// Loop through all words and insert words that start with 169 into possibleIPs
		for(String word : wholeString.split(" ")) {
			if(word.contains(IP_BEGINNING)) {
				possibleIPs.add(word);
			}
		}
		
		return possibleIPs.get(INDEX_OF_PI_IP);
	}
}
