package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class EuchrePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public ArrayList<JButton> hand = new ArrayList<JButton>();
	public ArrayList<Player> players;
	private ButtonListener actionL = new ButtonListener();
	JLabel middle = new JLabel();
	JLabel left = new JLabel();
	JLabel right = new JLabel();
	public int aloneCount = 0;
	
	public JFrame frame;

	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}
	
	private void display() {
		Euchre game = new Euchre();
		players = game.getPlayers();
		
		int i=0;
		while (i < 5) {
			hand.add(new JButton());
			hand.get(i).addActionListener(actionL);
			i++;
		}
				
		frame.setSize(800,600);
		frame.setLayout(null);
		frame.setVisible(true);
		
		playGame(game);
		displayHand(players.get(1).getHand());
		//setMiddle(players.get(0).getHand().get(0));
		
		
	}
	
	private void displayHand(ArrayList<Card> cards) {
		//removeHand();
		for (int i = 0; i < cards.size(); i++) {
			hand.get(i).setText(cards.get(i).toString());
			hand.get(i).setBounds(10+(155 *i),500,145, 40);
			frame.add(hand.get(i));
		}
		
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}
	
	public void removeHand() {
		for (int i=0; i<hand.size(); i++) {
			frame.remove(hand.get(i));
		}
		frame.revalidate();
		frame.repaint();
	}
	
	public void playGame(Euchre game) {
		game.shuffle(game.deck);
		Card tUp = game.deal();
		setMiddle(tUp);
		Suits t = pickTrump(tUp);
		game.setTrump(t);
		//System.out.println(t);
		playHand(aloneCount, game);
		
	}
	
	public void playHand(int dead, Euchre game)
	{
		Player nPlayer = game.getFirstPlayer(dead);
		while(game.getT1Trick() + game.getT2Trick() < 5) {
			 displayHand(nPlayer.getHand());
			 //NEED TO WAIT FOR BUTTON PRESS HERE
			 game.playTrick(nPlayer.getHand());
			 if(game.play.size()==4 || !(game.play.size()==3 && game.alone)){
				 	nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
				 	nPlayer = game.assignTrick(players, dead, nPlayer);
				 	game.play.clear();
				}
			 else{
				 nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
			 }
		}
		game.assignPoints();
		game.gameStatus();
	}
	
	private void setMiddle(Card card) {
		middle.setText(card.toString());
		middle.setBounds(320, 250, 145, 40);
		middle.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(middle);
		frame.revalidate();
		frame.repaint();
	}
	
	private void setLeft(Card card) {
		left.setText(card.toString());
		left.setBounds(170, 250, 145, 40);
		left.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(left);
		frame.revalidate();
		frame.repaint();
	}
	
	private void setRight(Card card) {
		right.setText(card.toString());
		right.setBounds(470, 250, 145, 40);
		right.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(right);
		frame.revalidate();
		frame.repaint();
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
		Card c = new Card();
		setMiddle(c);
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
			aloneCount = goAlone(count-1);
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
		aloneCount = goAlone(count-1);
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
	private class ButtonListener implements ActionListener, MouseListener {

		public void actionPerformed(ActionEvent e) {
			if (hand.get(0) == e.getSource()) {
				
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent r) { //Right Mouse Button
			
		}
	}
}

