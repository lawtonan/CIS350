package game;

import javax.swing.JFrame;

public class EuchureGUI {
	
	public static void main(String[] args) {
		  JFrame frame = new JFrame("Euchre");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  EuchrePanel panel = new EuchrePanel(frame);
		  frame.getContentPane().add(panel);
		  //frame.pack();
		  frame.setVisible(true);
		 }
}