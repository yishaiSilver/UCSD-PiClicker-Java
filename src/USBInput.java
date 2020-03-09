/**
 * This is the highest level of this program. This interfaces with
 * Raspberry Pi HID device and controls a display, thus allowing
 * the instructor to hide and display the chart of students' responses. 
 * 
 * @author Yishai
 * @version 1.0
 * @since 2019-08-19
 */
import java.util.Arrays;
import java.util.List;

import purejavahidapi.*;

public class USBInput {
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
	
	//Bytes for commands
	public static final byte BYTE_OPEN = (byte)0xAA;
	public static final byte BYTE_CLOSE = (byte)0xBB;
	public static final byte BYTE_NEXT_QUESTION = (byte)0xCC;
	public static final byte BYTE_SCREENSHOT = (byte)0xD1;
	public static final byte BYTE_SAVE_ALL_SCREENSHOTS = (byte)0xD2;
	public static final byte BYTE_RESPONSE_ONE = (byte)0x02;
	public static final byte BYTE_RESPONSE_TWO = (byte)0x30;
	
	//PiClicker VID and PID
	public static final short PICLICKER_VID = (short)0x1881;
	public static final short PICLICKER_PID = (short)0x0150;
	
	//Is the device open?
	private static boolean deviceOpen = false;
	
	/**
	 * Runs a loop to connect to Pi and then control display 
	 * 
	 * @param args none expected
	 */
	public static void main(String[] args) {
		try {
			// Initialize/open display
			Display display = new Display();
			ControlWindow controller = new ControlWindow(display);
			
			//Screenshot screenshotController = new Screenshot();
			while(true) {
				//Scan for device when it's not open
				if(!deviceOpen) {
					System.out.println("Scanning.");
					
					//Get list of all devices
					List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
					HidDeviceInfo devInfo = null;
					
					//Loop through list of devices, look for PiClicker
					for (HidDeviceInfo info : devList) {
						if (info.getVendorId() == PICLICKER_VID && info.getProductId() == PICLICKER_PID) {
							// Save PiClicker's info. Change display to connected
							devInfo = info;
							System.out.println("Device found.");
							deviceOpen = true;
							Display.setConnected(true);
							
							break;
						}
					}
					
					//If the device is open, configure it
					if(deviceOpen) {
						HidDevice dev = PureJavaHidApi.openDevice(devInfo);
						
						//Give it an InputReportListener
						dev.setInputReportListener(new InputReportListener() {
							@Override
							public void onInputReport(HidDevice source, byte Id, byte[] data, int len) {
								//Print out the report
								System.out.printf("onInputReport: id %d len %d data ", Id, len);
								for (int i = 0; i < len; i++) {
									System.out.printf("%02X ", data[i]);
								}
								System.out.println();
								
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
									//screenshotController.newScreenshot();
								}
								else if(data[0] == BYTE_SAVE_ALL_SCREENSHOTS) {
									//screenshotController.saveAllScreenshots(source);
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
						});
						
						dev.setDeviceRemovalListener(new DeviceRemovalListener() {
							@Override
							public void onDeviceRemoval(HidDevice source) {
								System.out.println("Device removed.");
								deviceOpen = false;
								Display.setConnected(false);
							}
						});
						
						System.out.println("Reached");
					}
				}

				Thread.sleep(500);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}