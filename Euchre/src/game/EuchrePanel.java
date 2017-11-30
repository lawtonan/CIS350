package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class EuchrePanel extends JPanel {
	Euchre game;
	Player nPlayer;
	private static final long serialVersionUID = 1L;
	public ArrayList<JButton> hand = new ArrayList<JButton>();
	public ArrayList<JButton> images = new ArrayList<JButton>();
	public ArrayList<Player> players;
	private ButtonListener actionL = new ButtonListener();
	JLabel middle = new JLabel();
	JLabel left = new JLabel();
	JLabel right = new JLabel();
	JLabel checkbut = new JLabel();
	JLabel gStats = new JLabel();
	JLabel hStats = new JLabel();
	public int aloneCount = 0;
	public boolean pickup = false;
	public Card tUp;
	Card c = new Card();
	
	public JFrame frame;

	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}

	private void display() {
		game = new Euchre();
		players = game.getPlayers();

		int i = 0;
		while (i < 5) {
			hand.add(new JButton());
			hand.get(i).addActionListener(actionL);
			images.add(new JButton());
			images.get(i).addActionListener(actionL);
			i++;
		}

		frame.setSize(800, 800);
		frame.setLayout(null);
		frame.setVisible(true);

		playGame(game);
		// displayHand(players.get(1).getHand());
		// setMiddle(players.get(0).getHand().get(0));

	}

	private void displayHand(ArrayList<Card> cards) {
		// removeHand();
		for (int i = 0; i < cards.size(); i++) {
			hand.get(i).setText(cards.get(i).toString());
			hand.get(i).setEnabled(true);
			hand.get(i).setBounds(50 + (130 * i), 650, 100, 40);

			String s = hand.get(i).getText();
			String t = s + ".png";

			ImageIcon card = new ImageIcon("cards/" + t);

			images.get(i).setIcon(card);

			images.get(i).setBounds(50 + (130 * i), 500, 100, 145);
			frame.add(images.get(i));

			frame.add(hand.get(i));
		}
		for (int j = 4; j >= cards.size(); j--) {
			hand.get(j).setText("");
			hand.get(j).setEnabled(false);
		}

		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}

//	public void removeHand() {
//		for (int i = 0; i < hand.size(); i++) {
//			frame.remove(hand.get(i));
//		}
//		frame.revalidate();
//		frame.repaint();
//	}

	public void playGame(Euchre game) {
		while(!game.gameStatus())
		{
			gameStats();
			game.setAlone(false);
			aloneCount = 5;
			game.shuffle(game.getDeck());
			tUp = game.deal();
			setMiddle(tUp);
			Suits t = pickTrump(tUp);
			game.setTrump(t);
			// System.out.println(t);
			playHand(aloneCount, game);
			game.assignPoints();
			game.gameStatus();
			handStats();
			
		}
	}

	// public void playHand(int dead, Euchre game)
	// {
	// Player nPlayer = game.getFirstPlayer(dead);
	// while(game.getT1Trick() + game.getT2Trick() < 5) {
	// displayHand(nPlayer.getHand());
	// //NEED TO WAIT FOR BUTTON PRESS HERE
	// game.playTrick(nPlayer.getHand());
	// if(game.getPlay().size()==4 || !(game.getPlay().size()==3 && game.alone)){
	// nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
	// nPlayer = game.assignTrick(players, dead, nPlayer);
	// game.getPlay().clear();
	// }
	// else{
	// nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
	// }
	// }
	// game.assignPoints();
	// game.gameStatus();
	// }
//	private void setCheck(Card card) {
//		checkbut.setText(card.toString());
//		checkbut.setBounds(320, 100, 145, 40);
//		checkbut.setBorder(BorderFactory.createLineBorder(Color.black));
//		frame.add(checkbut);
//		frame.revalidate();
//		frame.repaint();
//	}

	private void printPlayed() {
		if (game.getPlay().size() == 1) {
			setLeft(game.getPlay().get(0));
		} else if (game.getPlay().size() == 2) {
			setMiddle(game.getPlay().get(1));
		} else if (game.getPlay().size() == 4 || (game.getPlay().size() == 3 && game.alone() == true)) {
			Card c = new Card();
			setLeft(c);
			setMiddle(c);
			setRight(c);
		} else if (game.getPlay().size() == 3) {
			setRight(game.getPlay().get(2));
		}
	}

	private void gameStats() {
		gStats.setText("<html>T1 Score: " + game.getT1Score() + "<br>T2 Score: " + game.getT2Score() + "</html>");
		gStats.setBounds(100, 100, 145, 50);
		gStats.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(gStats);
		frame.revalidate();
		frame.repaint();
	}

	private void handStats() {
		hStats.setText("<html>Trump: " + game.getTrump() + "<br>T1 Tricks: " + game.getT1Trick() + "<br>T2 Tricks: "
				+ game.getT2Trick() + "<br>Current Player: " + nPlayer.getTeam() + "</html>");
		hStats.setBounds(500, 100, 145, 100);
		hStats.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(hStats);
		frame.revalidate();
		frame.repaint();
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
		Object[] options = { "Pick it up!", "Pass" };
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
		nPlayer = game.getFirstPlayer(aloneCount);
		while (n == 1 && count < 4) {
			// USE NPLAYER NOT COUNT -------------------------------------------------------------------------------------------------------------------------
			displayHand(nPlayer.getHand());
			n = JOptionPane.showOptionDialog(frame, "Want to call it up?", "Call Trump", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n == 0)
			{
				pickup = true;				
			}
			else
			{
				count++;
				nPlayer = game.nextPlayer(players.indexOf(nPlayer), aloneCount);
			}
		}
		if (count > 3) {
			count = 0;
			setMiddle(c);
			n = 3;
			while (n == 3 && count < 4) {
				displayHand(nPlayer.getHand());
				if (count == 3) {
					n = JOptionPane.showOptionDialog(frame, "Pick a suit?", "Call Trump", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, last, last[0]);
					count++;
					break;
				}
				n = JOptionPane.showOptionDialog(frame, "Pick a suit?", "Call Trump", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, second, second[0]);
				count++;
				if(n==3)
				{
					nPlayer = game.nextPlayer(players.indexOf(nPlayer), aloneCount);
				}
			}
			aloneCount = goAlone(players.indexOf(nPlayer));
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
		aloneCount = goAlone(players.indexOf(nPlayer));
		return top.getSuit();

	}

	public int goAlone(int count) {

		displayHand(players.get(count).getHand());
		Object[] options = { "Go alone!", "No thanks!" };
		int n = JOptionPane.showOptionDialog(frame, "Would you like to go alone?", "Go Alone?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0)
		{
			game.setAlone(true);
			return (count + 2) % 4;
		}
		else
		{
			game.setAlone(false);
			return 5;	
		}
	}

	public void playHand(int dead, Euchre game) {
		while(pickup){
			nPlayer = game.getDealer();
			displayHand(nPlayer.getHand());
		}
		nPlayer = game.getFirstPlayer(dead);
		while (game.getT1Trick() + game.getT2Trick() < 5) {
			handStats();
			displayHand(nPlayer.getHand());
			// NEED TO WAIT FOR BUTTON PRESS HERE
			if (game.getPlay().size() == 4 || (game.getPlay().size() == 3 && game.alone())) {
				nPlayer = game.assignTrick(players, dead, nPlayer);
				game.getPlay().clear();
				printPlayed();
			}
		}
	}

	public void playCard(int i) {
//		setCheck(nPlayer.getCard(i));
		if (game.getPlay().size() == 0) {
			game.getPlay().add(nPlayer.getCard(i));
			nPlayer.getHand().remove(i);
			nPlayer = game.nextPlayer(players.indexOf(nPlayer), aloneCount);
		} else if (game.playable(game.getPlay().get(0), nPlayer.getCard(i), nPlayer.getHand())) {
			game.getPlay().add(nPlayer.getCard(i));
			nPlayer.getHand().remove(i);
			nPlayer = game.nextPlayer(players.indexOf(nPlayer), aloneCount);
		}
		printPlayed();
	}
	
	public void pickUp(int i){
		nPlayer.getHand().set(i, tUp);
		nPlayer = game.getFirstPlayer(aloneCount);
		pickup = false;
		setMiddle(c);
	}
	
	private class ButtonListener implements ActionListener, MouseListener {

		public void actionPerformed(ActionEvent e) {
			if(pickup)
			{
				displayHand(game.getDealer().getHand());
				if (hand.get(0) == e.getSource()) {
					pickUp(0);
				} else if (hand.get(1) == e.getSource()) {
					pickUp(1);
				} else if (hand.get(2) == e.getSource()) {
					pickUp(2);
				} else if (hand.get(3) == e.getSource()) {
					pickUp(3);
				} else if (hand.get(4) == e.getSource()) {
					pickUp(4);
				}
			}
			else{
				if (hand.get(0) == e.getSource()) {
//					setCheck(nPlayer.getCard(0));
					playCard(0);
				} else if (hand.get(1) == e.getSource()) {
//					setCheck(nPlayer.getCard(1));
					playCard(1);
				} else if (hand.get(2) == e.getSource()) {
//					setCheck(nPlayer.getCard(2));
					playCard(2);
				} else if (hand.get(3) == e.getSource()) {
//					setCheck(nPlayer.getCard(3));
					playCard(3);
				} else if (hand.get(4) == e.getSource()) {
//					setCheck(nPlayer.getCard(4));
					playCard(4);
				}
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
		public void mouseReleased(MouseEvent r) { // Right Mouse Button

		}
	}
	// private class ButtonListener implements ActionListener, MouseListener {
	//
	// public void actionPerformed(ActionEvent e) {
	// if (hand.get(0) == e.getSource()) {
	//
	// }
	// }
	//
	// @Override
	// public void mouseClicked(MouseEvent e) {
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent arg0) {
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent arg0) {
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent arg0) {
	// }
	//
	// @Override
	// public void mouseReleased(MouseEvent r) { //Right Mouse Button
	//
	// }
	// }
}
