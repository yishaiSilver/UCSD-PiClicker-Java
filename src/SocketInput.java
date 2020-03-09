import java.net.*;
import java.util.Arrays;
import java.io.*;

public class SocketInput {
	//Ints and their associated answers
	public static final int ANSWER_A = 81;
	public static final int ANSWER_B = 82;
	public static final int ANSWER_C = 83;
	public static final int ANSWER_D = 84;
	public static final int ANSWER_E = 85;
	
	//Answers
	public static final String A = "A";
	public static final String B = "B";
	public static final String C = "C";
	public static final String D = "D";
	public static final String E = "E";
	
	//Bytes for commands custom commands from pi.
	public static final byte BYTE_OPEN = (byte)0xAA;
	public static final byte BYTE_CLOSE = (byte)0xBB;
	public static final byte BYTE_NEXT_QUESTION = (byte)0xCC;
	public static final byte BYTE_SCREENSHOT = (byte)0xD1;
	public static final byte BYTE_SAVE_ALL_SCREENSHOTS = (byte)0xD2;
	public static final byte BYTE_RESPONSE_ONE = (byte)0x02;
	public static final byte BYTE_RESPONSE_TWO = (byte)0x30;
	
	// The ports through which the pi and the computer will communicate
	public static final int SERVER_PORT = 8080;
	public static final int PI_SERVER_PORT = 8080;
	public static final int REPORT_SIZE = 64;
	
	// The possible IPs that can be connected to (192... is for when the user
	// has chosen to configure through settings, whereas raspberrypi.local is for
	// Bojour)
	public static final String PI_ADDRESS_SETTINGS = "192.168.7.2";
	public static final String PI_ADDRESS_BONJOUR = "raspberrypi.local";
	
	// The duration for which the program will try to connect to a certain IP during
	// the notification attempt (in ms)
	public static final int CONNECTION_TIMEOUT = 1000;
	
	private static ServerSocket serverSocket;
	private static Socket client;
	
	private static DataInputStream input;
	
	/*public static void main(String[] args) {
		try {
			Display display = new Display();
			Screenshot.takeFalseScreenshot();
			
			// Loop continuously until the pi is notified / connected to.
			while(true) {
				System.out.println("Attempting to connect via settings.");
				if(notifyPi(PI_ADDRESS_SETTINGS)) {
					break;
				}
				
				System.out.println("Attempting to connect via Bonjour.");
				if(notifyPi(PI_ADDRESS_BONJOUR)) {
					break;
				}
			}
			
			// If you've reached this point, you've connected successfully
			Display.setConnected(true);
			
			// Initialize a server socket
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Initiated socket.");
			
			// Loop continuously
			while(true) {
				// Accept all client requests
				client = serverSocket.accept();
				System.out.println("Accepted client.");
				
				// Get input from the client
				input = new DataInputStream(client.getInputStream());
				byte[] data = new byte[REPORT_SIZE];
				input.readFully(data);
				
				// Print out the input from the client
				System.out.println("Recieved report!");
				for (int i = 0; i < data.length; i++) {
					System.out.printf("%02X ", data[i]);
				}
				
				//Check the first byte of the report and act accordingly
				if(data[0] == BYTE_OPEN) {
					Display.openDisplay();
				}
				else if (data[0] == BYTE_CLOSE) {
					Display.closeDisplay();
				}
				else if (data[0] == BYTE_NEXT_QUESTION) {
					Display.nextQuestion();
				}
				else if (data[0] == BYTE_SCREENSHOT) {
					System.out.println("asdf");
					new Screenshot(client);
				}
				else if(data[0] == BYTE_RESPONSE_ONE && data[1] == BYTE_RESPONSE_TWO) {
					//Get bytes responsible for ID
					byte[] idArr = Arrays.copyOfRange(data, 5, 8);
					String idStr = "";
					//Add each byte to idStr
					for(int i = 0; i < idArr.length; i ++) {
						idStr += String.format("%02X", idArr[i]);
					}

					System.out.println(idStr);
					
					//Get byte responsible for response
					byte responseByte = data[4];
					//Convert byte to string
					String responseStr = String.format("%02X", responseByte);
					//Parse string to int
					int responseInt = Integer.parseInt(responseStr);
					//Get choice equivalent of int
					String responseLetter = "";
					switch (responseInt) {
						case 81:
							responseLetter = A;
							break;
						case 82:
							responseLetter = B;
							break;
						case 83:
							responseLetter = C;
							break;
						case 84:
							responseLetter = D;
							break;
						case 85:
							responseLetter = E;
							break;
					}
					
					//Register the response
					display.newResponse(idStr, responseLetter);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean notifyPi(String pi_address) {
		try {
			// Create a client, connect to the given IP, and then close immediately.
			Socket clientSocket = new Socket();
			clientSocket.connect(new InetSocketAddress(pi_address, PI_SERVER_PORT), CONNECTION_TIMEOUT);
			clientSocket.close();
			return true;
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return false;
	}*/
}