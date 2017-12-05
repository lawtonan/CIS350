package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**********************************************************************
 * The EuchrePanel class controls our GUI for the Euchre game. It uses
 * the Euchre class in order to play the game and add functionality where 
 * needed.
 * 
 * @author Andrew Lawton
 * @author Dan Schroeder
 * @author Aron Ockerse
 * @version 1.0
 *********************************************************************/
public class EuchrePanel extends JPanel {
	/** The game object used to play the game. */
	private Euchre game;
	
	/** A player object used to track the current player. */
	private Player nPlayer;
	
	/** Added to shut up CheckStyle. */
	private static final long serialVersionUID = 1L;
	
	/** An ArrayList of JButtons used to manage the hand. */
	private ArrayList<JButton> images = new ArrayList<JButton>();
	
	/** An ArrayList of the players paying the game. */
	private ArrayList<Player> players;
	
	/** A ButtonListener used to recognize button presses. */
	private ButtonListener actionL = new ButtonListener();
	
	/** A JLabel for the rest of the deck. */
	private JLabel deck = new JLabel();
	
	/** A JLabel for the middle image. */
	private JLabel middle = new JLabel();
	
	/** A JLabel for the left image. */
	private JLabel left = new JLabel();
	
	/** A JLabel for the right image. */
	private JLabel right = new JLabel();
	
	/** A JLabel used to display the game status. */
	private JLabel gStats = new JLabel();
	
	/** A JLabel used to display the hand status. */
	private JLabel hStats = new JLabel();
	
	/** The count of alone. */
	private int aloneCount = 0;
	
	/** A boolean used to know if pickup was used. */
	private boolean pickup = false;
	
	/** A card used to keep track of the turn up. */
	private Card tUp;
	
	/** A JFrame for the game as a whole. */
	private JFrame frame;

	/******************************************************************
	 * A constructor for Euchre Panel. This is called by the EuchreGUI
	 * class to set up the panel. We use a helper function to display 
	 * the game called here.
	 * 
	 * @param f
	 * 			A JFrame to display on.
	 *****************************************************************/
	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}

	/******************************************************************
	 * A helper method used to set up the display. This instantiates
	 * the values in the GUI and calls the playGame method in oder to
	 * start the game logic.
	 *****************************************************************/
	private void display() {
		game = new Euchre();
		players = game.getPlayers();
		int i = 0;
		while (i < 5) {
			images.add(new JButton());
			images.get(i).addActionListener(actionL);
			i++;
		}
		frame.setSize(800, 800);
		frame.setLayout(null);
		frame.setVisible(true);	
		
		//game.setT1Score(9);
		//game.setT2Score(9);
		
		playGame(game);
	}

	/******************************************************************
	 * A method used to display the hand. Looks at the cards and assigns
	 * images to them based on their card name.
	 * 
	 * @param cards
	 * 			The hand to display
	 *****************************************************************/
	private void displayHand(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			String s = cards.get(i).toString();
			String t = s + ".png";
			ImageIcon card = new ImageIcon("cards/" + t);
			images.get(i).setEnabled(true);
			images.get(i).setIcon(card);					
			images.get(i).setBounds(50 + (130 * i), 500, 100, 145);
			frame.add(images.get(i));
		}
		for (int j = 4; j >= cards.size(); j--) {
			images.get(j).setEnabled(false);	
			ImageIcon card = new ImageIcon("");
			images.get(j).setIcon(card);		
			images.get(j).setBounds(50 + (130 * j), 500, 100, 145);
			frame.add(images.get(j));
		}
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * The playGame method. This method is used to play the game. It 
	 * calls all of the logic necessary for the game to play out in a 
	 * loop to get to the end of the game.
	 * 
	 * @param game
	 * 			The game being played.
	 *****************************************************************/
	public void playGame(Euchre game) {
		gameStats();
		while (!game.gameStatus()) {
			game.setAlone(false);
			aloneCount = 5;
			game.shuffle(game.getDeck());
			tUp = game.deal();
			setDeck(tUp);
			Suits t = pickTrump(tUp);
			game.setTrump(t);
			playHand(aloneCount, game);
			game.assignPoints();
			gameStats();
			handStats();
		}
		if (playAgain()) {
			game.setT1Score(0);
			game.setT2Score(0);
			playGame(game);
		} else
			 System.exit(0);
	}

	/******************************************************************
	 * A helper method to check if the player would like to play again.
	 * 
	 * @return returns a boolean on what the player chose
	 *****************************************************************/
	public boolean playAgain() {
		int n;
		Object[] options = {"Play again", "Close" };
		if (game.getT1Score() >= 10) {
			n = JOptionPane.showOptionDialog(frame, "Would you like to play again?", "Team 1 Wins!", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		} else {
			n = JOptionPane.showOptionDialog(frame, "Would you like to play again?", "Team 2 Wins!", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		return n == 0;
	}

	/******************************************************************
	 * A method used to display the cards being played.
	 *****************************************************************/
	private void printPlayed() {
		if (game.getPlay().size() == 1) {
			setLeft(game.getPlay().get(0));
		} else if (game.getPlay().size() == 2) {
			setMiddle(game.getPlay().get(1));
		} else if (game.getPlay().size() == 4 || (game.getPlay().size() == 3 && game.alone())) {
			Card c = new Card();
			setLeft(c);
			setMiddle(c);
			setRight(c);
		} else if (game.getPlay().size() == 3) {
			setRight(game.getPlay().get(2));
		}
	}

	/******************************************************************
	 * A method used to display the game statistics.
	 *****************************************************************/
	private void gameStats() {
		gStats.setText("<html>T1 Score: " + game.getT1Score() + "<br>T2 Score: " + game.getT2Score() + "</html>");
		gStats.setBounds(100, 75, 145, 50);
		gStats.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(gStats);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used to display the hand statistics.
	 *****************************************************************/
	private void handStats() {
		hStats.setText("<html>Trump: " + game.getTrump() + "<br>T1 Tricks: " + game.getT1Trick() + "<br>T2 Tricks: "
				+ game.getT2Trick() + "<br>Current Player: " + players.indexOf(nPlayer) + "<br>Current Team: " + nPlayer.getTeam() + "</html>");
		hStats.setBounds(500, 75, 145, 100);
		hStats.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(hStats);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used to display the deck position in the GUI.
	 * 
	 * @param card
	 * 				the card being set to the position.
	 *****************************************************************/
	private void setDeck(Card card) {
		ImageIcon d = new ImageIcon("cards/" + card.toString()  + ".png");
		deck.setIcon(d);
		deck.setText(card.toString());
		deck.setBounds(320, 75, 100, 145);
		deck.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(deck);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used to display the middle position in the GUI.
	 * 
	 * @param card
	 * 				the card being set to the position.
	 *****************************************************************/
	private void setMiddle(Card card) {
		ImageIcon m = new ImageIcon("cards/" + card.toString() + ".png");
		middle.setIcon(m);
		middle.setText(card.toString());
		middle.setBounds(320, 250, 100, 145);
		middle.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(middle);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used to display the left position in the GUI.
	 * 
	 * @param card
	 * 				the card being set to the position.
	 *****************************************************************/
	private void setLeft(Card card) {
		ImageIcon l = new ImageIcon("cards/" + card.toString() + ".png");
		left.setIcon(l);
		left.setText(card.toString());
		left.setBounds(170, 250, 100, 145);
		left.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(left);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used to display the right position in the GUI.
	 * 
	 * @param card
	 * 				the card being set to the position.
	 *****************************************************************/
	private void setRight(Card card) {
		ImageIcon r = new ImageIcon("cards/" + card.toString() + ".png");
		right.setIcon(r);
		right.setText(card.toString());
		right.setBounds(470, 250, 100, 145);
		right.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(right);
		frame.revalidate();
		frame.repaint();
	}

	/******************************************************************
	 * A method used go through the trump logic and set trump to what
	 * is called by the player.
	 * 
	 * @param top
	 * 				this is the card that was turned up
	 * @return returns the suit that was picked.
	 *****************************************************************/
	public Suits pickTrump(Card top) {
		Object[] options = {"Pick it up!", "Pass" };
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
			displayHand(nPlayer.getHand());
			handStats();
			n = JOptionPane.showOptionDialog(frame, "Want to call it up?", "Call Trump", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (n == 0) {
				pickup = true;
			} else {
				count++;
				nPlayer = game.nextPlayer(players.indexOf(nPlayer), aloneCount);
			}
		}
		if (count > 3) {
			count = 0;
			ImageIcon backCard = new ImageIcon("cards/" + "yugioh.png");
			deck.setIcon(backCard);
			n = 3;
			while (n == 3 && count < 4) {
				displayHand(nPlayer.getHand());
				handStats();
				if (count == 3) {
					n = JOptionPane.showOptionDialog(frame, "Pick a suit?", "Call Trump", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, last, last[0]);
					count++;
					break;
				}
				n = JOptionPane.showOptionDialog(frame, "Pick a suit?", "Call Trump", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, second, second[0]);
				count++;
				if (n == 3) {
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
		ImageIcon backCard = new ImageIcon("cards/" + "yugioh.png");
		deck.setIcon(backCard);
		aloneCount = goAlone(players.indexOf(nPlayer));
		return top.getSuit();
	}

	/******************************************************************
	 * A method used to manage if the player wishes to go alone.
	 * 
	 * @param count
	 * 				the count of the player
	 * @return returns a value to evaluate in play game.
	 *****************************************************************/
	public int goAlone(int count) {
		displayHand(players.get(count).getHand());
		Object[] options = {"Go alone!", "No thanks!"};
		int n = JOptionPane.showOptionDialog(frame, "Would you like to go alone?", "Go Alone?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			game.setAlone(true);
			return (count + 2) % 4;
		} else {
			game.setAlone(false);
			return 5;
		}
	}

	/******************************************************************
	 * A method used to play a hand via GUI terms. It updates the GUI
	 * as the hand is played.
	 * 
	 * @param dead
	 * 				used to determine the first player.
	 * @param game 
	 * 				the game being played.
	 *****************************************************************/
	public void playHand(int dead, Euchre game) {
		while (pickup) {
			nPlayer = game.getDealer();
			displayHand(nPlayer.getHand());
			handStats();
		}
		nPlayer = game.getFirstPlayer(dead);
		while (game.getT1Trick() + game.getT2Trick() < 5) {
			handStats();
			displayHand(nPlayer.getHand());
			if (game.getPlay().size() == 4 || (game.getPlay().size() == 3 && game.alone())) {
				nPlayer = game.assignTrick(players, dead, nPlayer);
				game.getPlay().clear();
				printPlayed();
			}
		}
	}

	/******************************************************************
	 * A method used to play a hand via GUI terms. It updates the GUI
	 * as the hand is played.
	 * 
	 * @param i
	 * 			the card count
	 *****************************************************************/
	public void playCard(int i) {
		// setCheck(nPlayer.getCard(i));
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

	/******************************************************************
	 * A method for pickup to update the players hand as well as update
	 * the GUI when you pick up a card.
	 * 
	 * @param i
	 * 			the card count
	 *****************************************************************/
	public void pickUp(int i) {
		nPlayer.getHand().set(i, tUp);
		nPlayer = game.getFirstPlayer(aloneCount);
		pickup = false;
		ImageIcon backCard = new ImageIcon("cards/" + "yugioh.png");
		deck.setIcon(backCard);
	}

/**********************************************************************
 * A button listener class that is implemented for button listening
 * capabilities.
 * 
 * @author Andrew Lawton
 * @author Dan Schroeder
 * @author Aron Ockerse
 * @version 1.0
*********************************************************************/	
	private class ButtonListener implements ActionListener {

		/******************************************************************
		 * An action performed method that takes an input from a button
		 * and performs a certain action based on the event.
		 * 
		 * @param e
		 * 			the ActionEvent for each button
		 *****************************************************************/
		public void actionPerformed(ActionEvent e) {
			if (pickup) {
				displayHand(game.getDealer().getHand());
				if (images.get(0) == e.getSource()) {
					pickUp(0);
				} else if (images.get(1) == e.getSource()) {
					pickUp(1);
				} else if (images.get(2) == e.getSource()) {
					pickUp(2);
				} else if (images.get(3) == e.getSource()) {
					pickUp(3);
				} else if (images.get(4) == e.getSource()) {
					pickUp(4);
				}
			} else {
				if (images.get(0) == e.getSource()) {
					// setCheck(nPlayer.getCard(0));
					playCard(0);
				} else if (images.get(1) == e.getSource()) {
					// setCheck(nPlayer.getCard(1));
					playCard(1);
				} else if (images.get(2) == e.getSource()) {
					// setCheck(nPlayer.getCard(2));
					playCard(2);
				} else if (images.get(3) == e.getSource()) {
					// setCheck(nPlayer.getCard(3));
					playCard(3);
				} else if (images.get(4) == e.getSource()) {
					// setCheck(nPlayer.getCard(4));
					playCard(4);
				}
			}
		}
	}
}
