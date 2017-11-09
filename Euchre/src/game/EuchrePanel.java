package game;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class EuchrePanel extends JPanel {
	public ArrayList<JButton> hand = new ArrayList<JButton>();
	public ArrayList<Player> players;
	public JLabel[] left = new JLabel[5];
	public int aloneCount = 0;
	
	public JFrame frame;

	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}
	
	private void display() {
		Euchre game = new Euchre();
		players = game.getPlayers();
		

		displayHand(players.get(1).getHand());
		//setMiddle(players.get(0).getHand().get(0));
		
		frame.setSize(800,600);
		frame.setLayout(null);
		frame.setVisible(true);
		playGame(game);
	}
	
	private void displayHand(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			hand.add(new JButton(cards.get(i).toString()));
			hand.get(i).setBounds(10+(155 *i),500,145, 40);
			frame.add(hand.get(i));
		}
	}
	
	private void setMiddle(Card card) {
		JLabel middle = new JLabel(card.toString());
		middle.setBounds(320, 250, 145, 40);
		middle.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(middle);
	}
	
	public void playGame(Euchre game) {
		game.shuffle(game.deck);
		Card tUp = game.deal();
		Suits t = pickTrump(tUp);
		game.setTrump(t);
		//System.out.println(t);
		game.playHand(players, aloneCount, t);
		
	}
	
	public Suits pickTrump(Card top) {
		Object[] options = {"Pick it up!", "Pass"};
		Object[] second = new Object[4];
		Object[] last = new Object[3];
		
		if (top.getSuit() == Suits.Hearts) {
			second[0] = "Clubs"; 
			second[1] = "Diamonds";
			second[2] = "Spades";
			second[3] = "Pass";
			last[0] = "Clubs"; 
			last[1] = "Diamonds";
			last[2] = "Spades";
			
		}
		
		if (top.getSuit() == Suits.Clubs) {
			second[0] = "Hearts"; 
			second[1] = "Diamonds";
			second[2] = "Spades";
			second[3] = "Pass";
			last[0] = "Hearts"; 
			last[1] = "Diamonds";
			last[2] = "Spades";
		}
		
		if (top.getSuit() == Suits.Diamonds) {
			second[0] = "Hearts"; 
			second[1] = "Clubs";
			second[2] = "Spades";
			second[3] = "Pass";
			last[0] = "Hearts"; 
			last[1] = "Clubs";
			last[2] = "Spades";
		}
		
		if (top.getSuit() == Suits.Spades) {
			second[0] = "Hearts"; 
			second[1] = "Clubs";
			second[2] = "Diamonds";
			second[3] = "Pass";
			last[0] = "Hearts"; 
			last[1] = "Clubs";
			last[2] = "Diamonds";
		}
		
		int n = 1;
		int count = 0;
		while(n==1 && count < 4) {
			displayHand(players.get(count).getHand());
			n = JOptionPane.showOptionDialog(frame, "Want to call it up?",
				"Call Trump", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
			count++;
		}
		if (count > 3) {
			count = 0;
			n = 3;
			while(n==3 && count < 4) {
				displayHand(players.get(count).getHand());
				if (count == 3) {
					n = JOptionPane.showOptionDialog(frame, "Pick a suit?",
							"Call Trump", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
							null, last, last[0]);
					count++;
					break;
				}
				n = JOptionPane.showOptionDialog(frame, "Pick a suit?",
					"Call Trump", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
					null, second, second[0]);
				count++;
			}
			aloneCount = goAlone(count);
			String suit = second[n].toString();
			if (suit.equals("Hearts"))
				return Suits.Hearts;
			if (suit.equals("Clubs"))
				return Suits.Clubs;
			if (suit.equals("Diamonds"))
				return Suits.Diamonds;
			if (suit.equals("Spades"))
				return Suits.Spades;
			
		}
		aloneCount = goAlone(count);
		return top.getSuit();

	}
	public int goAlone(int count) {
		displayHand(players.get(count).getHand());
		Object[] options = {"Go alone!", "No thanks!"};
		int n = JOptionPane.showOptionDialog(frame, "Would you like to go alone?",
				"Go Alone?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (n == 0)
			return (count + 2) % 4;
		else
			return 4;
	}
}

