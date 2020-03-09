/**
 * This class creates a JFrame and populates it with a JFreeChart 
 * bar chart. 
 * 
 * @author Yishai
 * @version 1.0
 * @since 2019-08-19
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;


public class ControlWindow extends JFrame {

	//Ignore
	private static final long serialVersionUID = -2932614175973594471L;
	
	//private static Question question;
	
	//The JFrame used to display everything and its characteristics
	private static JFrame displayFrame;
	private static final int WINDOW_WIDTH = 250;
	private static final int WINDOW_HEIGHT = 100;
	private static final String WINDOW_TITLE = "PiClicker";

	public static final int BEGIN_POLL_BUTTON = 0;
	public static final int STOP_POLL_BUTTON = 1;
	public static final int DISPLAY_RESULTS = 1;
	public static final int CLOSE_BUTTON = -1;
	
	//Boolean for whether or not USB is connected
	private static boolean connected = false;
	
	private JButton startPollButton;
	private USBInput usbController;
	
	
	private Display display;
	
	/**
	 * Used to initialize the display's variables.
	 */
	public ControlWindow(Display display) {
		this.display = display;
		
		begin();
	}
	
	/**
	 * Used to initialize the JFrame.
	 */
	public void begin() {
		//Initialize displayFrame
		displayFrame = new JFrame();
		displayFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		displayFrame.setTitle(WINDOW_TITLE);
		displayFrame.setAlwaysOnTop(true);
		displayFrame.setFocusableWindowState(false);
		displayFrame.setLayout(null);
		
		//Save session on exit.
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add the chart to displayFrame
		//displayFrame.setContentPane(getChart());

		startPollButton = new JButton("Start");
		startPollButton.setBounds(150, 10, 80, 50);
		startPollButton.addActionListener(StartStop);
		
		displayFrame.add(startPollButton);
		
		
		displayFrame.validate();
		displayFrame.setVisible(true);
		
		//Open the display
		openDisplay();
	}
	
	private ActionListener StartStop = new ActionListener() {
		private boolean shouldStart = true;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(shouldStart) {
				openDisplay();
			}
			else {
				closeDisplay();
			}
			shouldStart = !shouldStart;
		}
	};
	
	/**
	 * Used to open the display
	 */
	public static void openDisplay() {
		displayFrame.setState(Frame.NORMAL);
	}
	
	/**
	 * Used to hide the display
	 */
	public static void closeDisplay() {
		displayFrame.setState(Frame.ICONIFIED);
	}
	
	/**
	 * Used to change the title of the bar chart / notify 
	 * of not being connected
	 * 
	 * @param connectedArg boolean to represent being connected
	 */
	public static void setConnected(boolean connectedArg) {
	}
}

