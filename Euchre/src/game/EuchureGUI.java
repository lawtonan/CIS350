package game;

import javax.swing.JFrame;
/******************************************************************
 * A GUI Class that is used to play the game as a main.
 *****************************************************************/
public final class EuchureGUI {
	
	/******************************************************************
	 * A private constructor to shut up CheckStyle.
	 *****************************************************************/
	private EuchureGUI() { }
	
	/******************************************************************
	 * A main method to run the game.
	 * 
	 * @param args
	 * 				the arguments to main
	 *****************************************************************/
	public static void main(String[] args) {
		  JFrame frame = new JFrame("Euchre");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  EuchrePanel panel = new EuchrePanel(frame);
		  frame.getContentPane().add(panel);
		  //frame.pack();
		  frame.setVisible(true);
		 }
}
