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
	public ArrayList<Player> players;
	private ButtonListener actionL = new ButtonListener();
	JLabel middle = new JLabel();
	JLabel left = new JLabel();
	JLabel right = new JLabel();
	JLabel checkbut = new JLabel();
	JLabel gStats = new JLabel();
	JLabel hStats = new JLabel();
	public int aloneCount = 0;
	
	public JFrame frame;

	public EuchrePanel(JFrame f) {
		frame = f;
		display();
	}
	
	private void display() {
		game = new Euchre();
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
			hand.get(i).setEnabled(true);
			hand.get(i).setBounds(10+(155 *i),500,145, 40);
			frame.add(hand.get(i));
		}
		for (int j = 4; j >= cards.size(); j--)
		{
			hand.get(j).setText("");
			hand.get(j).setEnabled(false);
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
		gameStats();
		game.shuffle(game.deck);
		Card tUp = game.deal();
		setMiddle(tUp);
		Suits t = pickTrump(tUp);
		game.setTrump(t);
		//System.out.println(t);
		playHand(aloneCount, game);
		game.assignPoints();
		game.gameStatus();
		handStats();
		gameStats();
		
	}
	
//	public void playHand(int dead, Euchre game)
//	{
//		Player nPlayer = game.getFirstPlayer(dead);
//		while(game.getT1Trick() + game.getT2Trick() < 5) {
//			 displayHand(nPlayer.getHand());
//			 //NEED TO WAIT FOR BUTTON PRESS HERE
//			 game.playTrick(nPlayer.getHand());
//			 if(game.play.size()==4 || !(game.play.size()==3 && game.alone)){
//				 	nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
//				 	nPlayer = game.assignTrick(players, dead, nPlayer);
//				 	game.play.clear();
//				}
//			 else{
//				 nPlayer = game.nextPlayer(players.indexOf(nPlayer),dead);
//			 }
//		}
//		game.assignPoints();
//		game.gameStatus();
//	}
	private void setCheck(Card card) {
		checkbut.setText(card.toString());
		checkbut.setBounds(320, 100, 145, 40);
		checkbut.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(checkbut);
		frame.revalidate();
		frame.repaint();
	}
	
	private void printPlayed(){
		if( game.play.size() == 1 ){
			setLeft(game.play.get(0));
		}else if( game.play.size() == 2 ){
			setMiddle(game.play.get(1));
		}else if( game.play.size() == 3 ){
			setRight(game.play.get(2));
		}else if( game.play.size() == 4){
			Card c = new Card();
			setLeft(c);
			setMiddle(c);
			setRight(c);
		}
	}
	private void gameStats() {
		gStats.setText("<html>T1 Score: " + game.getT1Score() + "<br>T2 Score: " + game.getT2Score() +"</html>");
		gStats.setBounds(100, 100, 145, 50);
		gStats.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(gStats);
		frame.revalidate();
		frame.repaint();
	}
	
	private void handStats() {
		hStats.setText("<html>Trump: " + game.trump + "<br>T1 Tricks: " + game.getT1Trick() + "<br>T2 Tricks: " + game.getT2Trick() + "<br>Current Player: " + nPlayer.getTeam() + "</html>");
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
	
	public void playHand(int dead, Euchre game)
	{
		nPlayer = game.getFirstPlayer(dead);
		while(game.getT1Trick() + game.getT2Trick() < 5) {
			handStats();
			displayHand(nPlayer.getHand());
			//NEED TO WAIT FOR BUTTON PRESS HERE
			if(game.play.size()==4 || (game.play.size()==3 && game.alone)){
			 	nPlayer = game.assignTrick(players, dead, nPlayer);
			 	game.play.clear();
			}
		}
	}
	
	private class ButtonListener implements ActionListener, MouseListener {

		public void actionPerformed(ActionEvent e) {
			if (hand.get(0) == e.getSource()) {
				setCheck(nPlayer.getCard(0));
				if(game.play.size()==0){
					game.play.add(nPlayer.getCard(0));
					nPlayer.getHand().remove(0);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}else if(game.playable(game.play.get(0), nPlayer.getCard(0), nPlayer.getHand())) {
					game.play.add(nPlayer.getCard(0));
					nPlayer.getHand().remove(0);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}
//				else
//					System.out.println("bad input");
				printPlayed();
			} else if (hand.get(1) == e.getSource()) {
				setCheck(nPlayer.getCard(1));
				if(game.play.size()==0) {
					game.play.add(nPlayer.getCard(1));
					nPlayer.getHand().remove(1);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				} else if(game.playable(game.play.get(0), nPlayer.getCard(1), nPlayer.getHand())) {
					game.play.add(nPlayer.getCard(1));
					nPlayer.getHand().remove(1);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}
				printPlayed();
			} else if (hand.get(2) == e.getSource()) {
				setCheck(nPlayer.getCard(2));
				if(game.play.size()==0) {
					game.play.add(nPlayer.getCard(2));
					nPlayer.getHand().remove(2);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				} else if(game.playable(game.play.get(0), nPlayer.getCard(2), nPlayer.getHand())) {
					game.play.add(nPlayer.getCard(2));
					nPlayer.getHand().remove(2);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}
				printPlayed();
			} else if (hand.get(3) == e.getSource()) {
				setCheck(nPlayer.getCard(3));
				if(game.play.size()==0) {
					game.play.add(nPlayer.getCard(3));
					nPlayer.getHand().remove(3);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}else if(game.playable(game.play.get(0), nPlayer.getCard(3), nPlayer.getHand())) {
					game.play.add(nPlayer.getCard(3));
					nPlayer.getHand().remove(3);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}
				printPlayed();
			} else if (hand.get(4) == e.getSource()) {
				setCheck(nPlayer.getCard(4));
				if(game.play.size()==0) {
					game.play.add(nPlayer.getCard(4));
					nPlayer.getHand().remove(4);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				} else if(game.playable(game.play.get(0), nPlayer.getCard(4), nPlayer.getHand())) {
					game.play.add(nPlayer.getCard(4));
					nPlayer.getHand().remove(4);
					nPlayer = game.nextPlayer(players.indexOf(nPlayer),aloneCount);
				}
				printPlayed();
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
//	private class ButtonListener implements ActionListener, MouseListener {
//
//		public void actionPerformed(ActionEvent e) {
//			if (hand.get(0) == e.getSource()) {
//				
//			}
//		}
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent arg0) {
//		}
//
//		@Override
//		public void mouseExited(MouseEvent arg0) {
//		}
//
//		@Override
//		public void mousePressed(MouseEvent arg0) {
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent r) { //Right Mouse Button
//			
//		}
//	}
}

