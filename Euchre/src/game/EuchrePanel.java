package game;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class EuchrePanel extends JPanel {
	public ArrayList<JButton> hand = new ArrayList<JButton>();
	public JLabel[] left = new JLabel[5];
	
	public JFrame frame;

	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}
	
	private void display() {
		Euchre game = new Euchre();
		ArrayList<Player> players = game.getPlayers();


		createHand(players.get(1).getHand().size());
		
		
		frame.setSize(600,500);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	private void createHand(int size) {
		for (int i = 0; i < size; i++) {
			hand.add(new JButton("card"));
			hand.get(i).setBounds(20+(110 *i),400,100, 40);
			frame.add(hand.get(i));
		}
	}
}
//	private void createFiller(int size) {
//		for (int i =0; i < size; i++) {
//			left= new JLabel("Card");
//			text.setBorder(new LineBorder(Color.BLACK));
//			text.setBounds(20,200, 40, 40);
//			frame.add(text);
//		}
//	}
//}
