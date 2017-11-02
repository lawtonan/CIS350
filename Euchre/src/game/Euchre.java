package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**********************************************************************
 * The Euchre class is our main class that controls the functionality of the
 * game to be played in the console window. It can create a deck and keep track
 * of score as the game is played.
 * 
 * @author Andrew Lawton
 * @author Dan Schroeder
 * @author Aron Ockerse
 * @version 1.0
 *********************************************************************/
public class Euchre {
	/** A suit created by an enumerated type for the deck. */
	private Suits DIAMOND = Suits.Diamonds;

	/** A suit created by an enumerated type for the deck. */
	private Suits HEART = Suits.Hearts;

	/** A suit created by an enumerated type for the deck. */
	private Suits SPADE = Suits.Spades;

	/** A suit created by an enumerated type for the deck. */
	private Suits CLUB = Suits.Clubs;

	/** A type to assign Team 1. */
	private static Team TEAM1 = Team.Team1;

	/** A type to assign Team 2. */
	private static Team TEAM2 = Team.Team2;

	/** A suit used to keep track of trump. */
	private static Suits trump;

	/** A suit used to keep track of the dead suit. */
	private static Suits dead;

	/** An ArrayList of cards for the deck. */
	public ArrayList<Card> deck = new ArrayList<Card>();
	
	/** An ArrayList of players */
	ArrayList<Player> players = new ArrayList<Player>();

	/** An integer to keep track of team 1's score. */
	private static int t1Score;

	/** An integer to keep track of team 2's score. */
	private static int t2Score;

//	/** A player object declared in main. */
//	private static Player p1;
//
//	/** A player object declared in main. */
//	private static Player p2;
//
//	/** A player object declared in main. */
//	private static Player p3;
//
//	/** A player object declared in main. */
//	private static Player p4;

	/** A card used to keep track of the turn up. */
	public Card turnUp;

	/** A boolean used to see who called the trump. */
	public boolean t1CallSuit;

	/** A boolean used to see who goes alone. */
	public boolean alone;

	/** An integer to be used for the scanner. */
	public int input;

	/** A scanner used to read user input. */
	private Scanner reader = new Scanner(System.in);

	/** An integer to keep track of team 1's trick count. */
	private static int t1Trick;

	/** An integer to keep track of team 2's trick count. */
	private static int t2Trick;

	/** A character to read if the user calls yes or no. */
	public char yorn;

	/** A character to read what the user calls for trump. */
	public char cSuit;

	/******************************************************************
	 * A main method used to start the game after setting a few 
	 * variables to then create the game itself. This will change in 
	 * release 2 as we will move to a GUI instead of command terminal.
	 * 
	 * @param args
	 *            An array of strings for arguments
	 *****************************************************************/
	//public static void main(String[] args) {
	public Euchre() {
		//Euchre game = new Euchre();
		createDeck();
		setT1Score(0);
		setT2Score(0);

		ArrayList<Card> a = new ArrayList<Card>();
		ArrayList<Card> b = new ArrayList<Card>();
		ArrayList<Card> c = new ArrayList<Card>();
		ArrayList<Card> d = new ArrayList<Card>();

		
		Player p1 = new Player(TEAM1, a);
		Player p2 = new Player(TEAM2, b);
		Player p3 = new Player(TEAM1, c);
		Player p4 = new Player(TEAM2, d);
		p1.setDealer(true);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);


		shuffle(deck);
	}
	
	public void playGame() {
		shuffle(deck);
		deal();
		//callTrump();
		//playTrick(players);
		
	}
	/******************************************************************
	 * A method to return the players
	 * 
	 * @return returns an ArrayList of player objects (the players)
	 *****************************************************************/
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/******************************************************************
	 * A helper method used to create the deck in main.
	 * 
	 * @return returns an ArrayList of card objects (the deck)
	 *****************************************************************/
	public ArrayList<Card> createDeck() {
		for (int i = 0; i <= 5; i++) {
			deck.add(new Card(i, DIAMOND));
			deck.add(new Card(i, HEART));
			deck.add(new Card(i, CLUB));
			deck.add(new Card(i, SPADE));
		}
		return deck;
	}

	/******************************************************************
	 * A method used to swap the dealer. Changes dealer to the next player.
	 * 
	 *****************************************************************/
	public void swapDealer() {
		if (players.get(0).isDealer()) {
			players.get(0).setDealer(false);
			players.get(1).setDealer(true);
		} else if (players.get(1).isDealer()) {
			players.get(1).setDealer(false);
			players.get(2).setDealer(true);
		} else if (players.get(2).isDealer()) {
			players.get(2).setDealer(false);
			players.get(3).setDealer(true);
		} else if (players.get(3).isDealer()) {
			players.get(3).setDealer(false);
			players.get(0).setDealer(true);
		}
	}

	/******************************************************************
	 * A accessor used to get team 1s' score.
	 * 
	 * @return returns t1Score
	 *****************************************************************/
	public int getT1Score() {
		return t1Score;
	}

	/******************************************************************
	 * A mutator used to change team 1s' score.
	 * 
	 * @param t1
	 *            a desired score
	 *****************************************************************/
	public static void setT1Score(int t1) {
		t1Score = t1;
	}

	/******************************************************************
	 * An incrementor that increments team 1's score.
	 * 
	 *****************************************************************/
	public void T1Point() {
		setT1Score(getT1Score() + 1);
	}

	/******************************************************************
	 * A accessor used to get team 2s' score.
	 * 
	 * @return returns t2Score
	 *****************************************************************/
	public int getT2Score() {
		return t2Score;
	}

	/******************************************************************
	 * A mutator used to change team 2s' score.
	 * 
	 * @param t2
	 *            a desired score
	 *****************************************************************/
	public static void setT2Score(int t2) {
		t2Score = t2;
	}

	/******************************************************************
	 * An incrementor that increments team 2's score.
	 * 
	 *****************************************************************/
	public void T2Point() {
		setT2Score(getT2Score() + 1);
	}

	/******************************************************************
	 * A method that returns the games status.
	 * 
	 * @return returns a boolean whether the game is complete
	 *****************************************************************/
	public boolean gameStatus() {
		if (t1Score >= 10) {
			System.out.println("Team one score: " + t1Score 
					+ "\nTeam two score: " + t2Score);
			System.out.println("Team 1 wins");
			return true;
		} else if (t2Score >= 10) {
			System.out.println("Team one score: " + t1Score
					+ "\nTeam two score: " + t2Score);
			System.out.println("Team 2 wins");
			return true;
		} else {
			System.out.println("Team one score: " + t1Score
					+ "\nTeam two score: " + t2Score);
			return false;
		}
	}

	/******************************************************************
	 * A method that shuffles the deck ArrayList then deals.
	 * 
	 * @param deck the games deck
	 *****************************************************************/
	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}

	/******************************************************************
	 * A method that shuffles the deck ArrayList then deals.
	 * 
	 * @param deck
	 *            the games deck
	 *****************************************************************/
	public void shuffleTest(ArrayList<Card> deck) {
		Collections.shuffle(deck);

	}

	/******************************************************************
	 * A method that checks if the card played is playable or not.
	 * 
	 * @param lead
	 *            The lead card
	 * @param played
	 *            The card to check if valid
	 * @param hand
	 * 			  The players hand
	 * @param t
	 *            Trump
	 * @return returns True if played is a valid card
	 *****************************************************************/
	public boolean playable(Card lead, Card played, ArrayList<Card> hand, 
			Suits t) {

		if (lead.getSuit() == t || leftBower(lead, t)) {
			if (played.getSuit() == t || leftBower(played, t)) {
				return true;
			} else {
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getSuit() == t 
						|| leftBower(hand.get(i), t)) 
						return false;
				}
			}
		} else {
			if (lead.getSuit() == played.getSuit() 
				&& !leftBower(played, t))
				return true;
			for (int i = 0; i < hand.size(); i++) {
				if (lead.getSuit() == hand.get(i).getSuit() 
					&& !leftBower(hand.get(i), t))
					return false;
			}
		}
		return true;
	}

	/******************************************************************
	 * A mutator that sets trump.
	 * 
	 * @param t
	 *            the suit to set trump
	 *****************************************************************/
	public static void setTrump(Suits t) {
		trump = t;
	}

	/******************************************************************
	 * A method that determines which card is the highest out of those 
	 * played, and will return that card.
	 * 
	 * @param active
	 *            an array list of cards that were played
	 * @param t
	 *            trump
	 * @return returns the highest value card
	 *****************************************************************/
	public Card takeTrick(ArrayList<Card> active, Suits t) {
		Card high;
		high = active.get(0);
		for (int i = 1; i < active.size(); i++) {
			if (rightBower(high, t))
				return high;
			else if (rightBower(active.get(i), t))
				return active.get(i);
			else if (leftBower(high, t)) {
				continue;
			} else if (high.getSuit() == t && active.get(i).getSuit() == t) {
				if (active.get(i).getCardName() > high.getCardName() || leftBower(active.get(i), t))
					high = active.get(i);
			} else if (high.getSuit() != t && (active.get(i).getSuit() == t || leftBower(active.get(i), t))) {
				high = active.get(i);
			} else if (high.getSuit() == active.get(i).getSuit())
				if (high.getCardName() < active.get(i).getCardName())
					high = active.get(i);
		}
		return high;
	}

	/******************************************************************
	 * A helper method to determine if a card is the right bower.
	 * 
	 * @param c
	 *            The card in question
	 * @param t
	 *            trump
	 * @return returns a boolean of if the card is the right bower
	 *****************************************************************/
	private boolean rightBower(Card c, Suits t) {
		if (c.getCardName() == 2 && c.getSuit() == t)
			return true;
		return false;
	}

	/******************************************************************
	 * A helper method to determine if a card is the left bower.
	 * 
	 * @param c
	 *            The card in question
	 * @param t
	 *            trump
	 * @return return a boolean of if the card is tje left bower
	 *****************************************************************/
	private boolean leftBower(Card c, Suits t) {
		if (t == SPADE) {
			if (c.getCardName() == 2 && c.getSuit() == CLUB)
				return true;
			return false;
		}
		if (t == CLUB) {
			if (c.getCardName() == 2 && c.getSuit() == SPADE)
				return true;
			return false;
		}
		if (t == HEART) {
			if (c.getCardName() == 2 && c.getSuit() == DIAMOND)
				return true;
			return false;
		}
		if (t == DIAMOND) {
			if (c.getCardName() == 2 && c.getSuit() == HEART)
				return true;
			return false;
		}
		return false;

	}

	/******************************************************************
	 * A accessor to return team 1's trick count.
	 * 
	 * @return returns t1Trick
	 *****************************************************************/
	public int getT1Trick() {
		return t1Trick;
	}

	/******************************************************************
	 * A mutator to set team 1's trick count.
	 * 
	 * @param t1
	 *            What you want to set the trick to
	 *****************************************************************/
	public static void setT1Trick(int t1) {
		t1Trick = t1;
	}

	/******************************************************************
	 * A accessor to return team 2's trick count.
	 * 
	 * @return returns t2Trick
	 *****************************************************************/
	public int getT2Trick() {
		return t2Trick;
	}

	/******************************************************************
	 * A mutator to set team 2's trick count.
	 * 
	 * @param t2
	 *            What you want to set the trick to
	 *****************************************************************/
	public static void setT2Trick(int t2) {
		t2Trick = t2;
	}

	/******************************************************************
	 * A method that give each player 5 cards from the shuffled deck.
	 * 
	 *****************************************************************/
	public void deal() {
		int cardCount = 0;
		setT1Trick(0);
		setT2Trick(0);
		for (int i = 0; i < 5; i++) {
			players.get(1).setCard(this.deck.get(cardCount));
			cardCount++;
			players.get(2).setCard(this.deck.get(cardCount));
			cardCount++;
			players.get(3).setCard(this.deck.get(cardCount));
			cardCount++;
			players.get(0).setCard(this.deck.get(cardCount));
			cardCount++;
		}
		turnUp = deck.get(cardCount);
		players.get(0).setHand(organizeHand(players.get(0).getHand()));
		players.get(1).setHand(organizeHand(players.get(1).getHand()));
		players.get(2).setHand(organizeHand(players.get(2).getHand()));
		players.get(3).setHand(organizeHand(players.get(3).getHand()));
		//callTrump(turnUp);
	}

	/******************************************************************
	 * A method used to organize the hand.
	 * 
	 * @param hand
	 *            an ArrayList of cards that represents the hand
	 * @return returns the new organized hand
	 *****************************************************************/
	public ArrayList<Card> organizeHand(ArrayList<Card> hand) {

		Card change;
		ArrayList<Card> temp = new ArrayList<Card>();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit() == HEART)
				temp.add(hand.get(i));
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit() == CLUB)
				temp.add(hand.get(i));
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit() == DIAMOND)
				temp.add(hand.get(i));
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit() == SPADE)
				temp.add(hand.get(i));
		}
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < i; j++) {
				if (temp.get(i).getSuit() == temp.get(j).getSuit()) {
					if (temp.get(i).getCardName() > temp.get(j).getCardName()) {
						change = temp.get(i);
						temp.set(i, temp.get(j));
						temp.set(j, change);
					}
				}
			}
		}
		return temp;
		
	}

	/******************************************************************
	 * A method that allows each player to play a card and handles the 
	 * scoring for each team. This method is recursive to play the entire 
	 * game.
	 * 
	 * @param pl1
	 *            Player 1
	 * @param pl2
	 *            Player 2
	 * @param pl3
	 *            Player 3
	 * @param pl4
	 *            Player 4
	 * @param t
	 *            Trump
	 *****************************************************************/
	public void playTrick(Player pl1, Player pl2, Player pl3, Player pl4, Suits t) {

		Card highCard;
		ArrayList<Card> played = new ArrayList<Card>();

		printHand(pl1.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		played.add(pl1.getCard(input));
		pl1.getHand().remove(input);

		printHand(pl2.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		while (playable(played.get(0), pl2.getCard(input), pl2.getHand(), t)) {
			System.out.println("not a valid card");
			printHand(pl2.getHand());
			System.out.println("Play a Card: ");
			input = reader.nextInt();
		}
		played.add(pl2.getCard(input));
		pl2.getHand().remove(input);

		printHand(pl3.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		while (playable(played.get(0), pl3.getCard(input), pl3.getHand(), t)) {
			System.out.println("not a valid card");
			printHand(pl3.getHand());
			System.out.println("Play a Card: ");
			input = reader.nextInt();
		}
		played.add(pl3.getCard(input));
		pl3.getHand().remove(input);

		printHand(pl4.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		while (playable(played.get(0), pl4.getCard(input), pl4.getHand(), t)) {
			System.out.println("not a valid card");
			printHand(pl4.getHand());
			System.out.println("Play a Card: ");
			input = reader.nextInt();
		}
		played.add(pl4.getCard(input));
		pl4.getHand().remove(input);

		highCard = takeTrick(played, t);
		if (highCard == played.get(0) || highCard == played.get(2)) {
			if (pl1.getTeam() == TEAM1 || pl3.getTeam() == TEAM1)
				setT1Trick(getT1Trick() + 1);
			else
				setT2Trick(getT2Trick() + 1);
		} else {
			if (pl2.getTeam() == TEAM1 || pl4.getTeam() == TEAM1)
				setT1Trick(getT1Trick() + 1);
			else
				setT2Trick(getT2Trick() + 1);
		}
		System.out.println("Team 1 Tricks: " + t1Trick);
		System.out.println("Team 2 Tricks: " + t2Trick);
		if (t1Trick + t2Trick < 5) {
			if (highCard == played.get(0))
				playTrick(pl1, pl2, pl3, pl4, t);
			else if (highCard == played.get(1))
				playTrick(pl2, pl3, pl4, pl1, t);
			else if (highCard == played.get(2))
				playTrick(pl3, pl4, pl1, pl2, t);
			else
				playTrick(pl4, pl1, pl2, pl3, t);
		}

		if (t1CallSuit) {
			if (t1Trick >= 3 && t1Trick < 5)
				T1Point();
			else if (t1Trick == 5) {
				T1Point();
				T1Point();
			} else {
				T2Point();
				T2Point();
			}
		} else {
			if (t2Trick >= 3 && t2Trick < 5)
				T2Point();
			else if (t2Trick == 5) {
				T2Point();
				T2Point();
			} else {
				T1Point();
				T1Point();
			}
		}
		if (gameStatus()) {
			System.out.println("Would you like to play again? (y) or (n)");
			yorn = reader.next().charAt(0);
			if (yorn == 'y' || yorn == 'Y') {
				setT1Score(0);
				setT2Score(0);
				swapDealer();
				shuffle(deck);
			}
		} else {
			setT1Trick(0);
			setT2Trick(0);
			swapDealer();
			shuffle(deck);
		}
	}

	/******************************************************************
	 * A method that allows each player to play a card when a player 
	 * goes alone and handles the scoring for each team. This method is 
	 * recursive to play the entire game. This method is used when a 
	 * player chooses to play alone.
	 * 
	 * @param pl1
	 *            Player 1
	 * @param pl2
	 *            Player 2
	 * @param pl3
	 *            Player 3
	 * @param t
	 *            Trump
	 *****************************************************************/
	public void playTrick(Player pl1, Player pl2, Player pl3, Suits t) {

		Card highCard;
		ArrayList<Card> play = new ArrayList<Card>();

		printHand(pl1.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		play.add(pl1.getCard(input));
		pl1.getHand().remove(input);

		printHand(pl2.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		while (playable(play.get(0), pl2.getCard(input), pl2.getHand(), t)) {
			System.out.println("not a valid card");
			printHand(pl2.getHand());
			System.out.println("Play a Card: ");
			input = reader.nextInt();
		}
		play.add(pl2.getCard(input));
		pl2.getHand().remove(input);

		printHand(pl3.getHand());
		System.out.println("Play a Card: ");
		input = reader.nextInt();
		while (playable(play.get(0), pl3.getCard(input), pl3.getHand(), t)) {
			System.out.println("not a valid card");
			printHand(pl3.getHand());
			System.out.println("Play a Card: ");
			input = reader.nextInt();
		}
		play.add(pl3.getCard(input));
		pl3.getHand().remove(input);

		highCard = takeTrick(play, t);
		if (highCard == play.get(0)) {
			if (pl1.getTeam() == TEAM1)
				setT1Trick(getT1Trick() + 1);
			else
				setT2Trick(getT2Trick() + 1);
		} else if (highCard == play.get(1)) {
			if (pl2.getTeam() == TEAM1)
				setT1Trick(getT1Trick() + 1);
			else
				setT2Trick(getT2Trick() + 1);
		} else {
			if (pl3.getTeam() == TEAM1)
				setT1Trick(getT1Trick() + 1);
			else
				setT2Trick(getT2Trick() + 1);
		}
		System.out.println("Team 1 Tricks: " + t1Trick);
		System.out.println("Team 2 Tricks: " + t2Trick);
		if (t1Trick + t2Trick < 5) {
			if (highCard == play.get(0))
				playTrick(pl1, pl2, pl3, t);
			else if (highCard == play.get(1))
				playTrick(pl2, pl3, pl1, t);
			else
				playTrick(pl3, pl1, pl2, t);
		}

		if (t1CallSuit) {
			if (t1Trick >= 3 && t1Trick < 5)
				T1Point();
			else if (t1Trick == 5) {
				T1Point();
				T1Point();
				T1Point();
				T1Point();
			} else {
				T2Point();
				T2Point();
			}
		} else {
			if (t2Trick >= 3 && t2Trick < 5)
				T2Point();
			else if (t2Trick == 5) {
				T2Point();
				T2Point();
				T2Point();
				T2Point();
			} else {
				T1Point();
				T1Point();
			}
		}
		if (gameStatus()) {
			System.out.println("Would you like to play again? (y) or (n)");
			yorn = reader.next().charAt(0);
			if (yorn == 'y' || yorn == 'Y') {
				setT1Score(0);
				setT2Score(0);
				swapDealer();
				shuffle(deck);
			}
		} else {
			setT1Trick(0);
			setT2Trick(0);
			swapDealer();
			shuffle(deck);
		}
	}
	
	/******************************************************************
	 * A method that returns next player.
	 * 
	 * @param current 
	 * 			  The current player
	 * @return player
	 * 			  Returns the next player
	 *****************************************************************/
	public Player nextPlayer(Player current) {
		if (current == players.get(0)) 
			return players.get(1);
		else if (current == players.get(1)) 
			return players.get(2);
		else if (current == players.get(2)) 
			return players.get(3);
		else
			return players.get(0);
	}

	/******************************************************************
	 * A method that prints the hand.
	 * 
	 * @param hand
	 *            An array list of a cards to be printed
	 *****************************************************************/
	public static void printHand(ArrayList<Card> hand) {
		System.out.println("\nYour hand is:");
		for (int i = 0; i < hand.size(); i++) {
			System.out.println(i + ": " + hand.get(i));
		}
	}

	/******************************************************************
	 * A method that prints a card for turn up.
	 * 
	 * @param c
	 *            A card to be printed
	 *****************************************************************/
	public static void printCard(Card c) {
		System.out.println("\nThe card turned up is: " + c);
	}

	/******************************************************************
	 * A method that is used to ask each player to determine trump at 
	 * the beginning of each hand.
	 * 
	 * @param tUp
	 *            the turned up card
	 *****************************************************************/
//	public void callTrump(Card tUp) {
//		int call = 0;
//		int caller = 0;
//		trump = null;
//		while (call < 4 && trump == null) {
//			if (p1.isDealer()) {
//				if (call == 0) {
//					printHand(p2.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p1, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 2;
//					} else
//						call++;
//				} else if (call == 2) {
//					printHand(p4.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p1, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 4;
//					} else
//						call++;
//				} else if (call == 1) {
//					printHand(p3.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to order it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p1, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 1;
//					} else
//						call++;
//				} else if (call == 3) {
//					printHand(p1.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p1, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 3;
//					} else
//						call++;
//				}
//			} else if (p2.isDealer()) {
//				if (call == 0) {
//					printHand(p3.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p2, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 3;
//					} else
//						call++;
//				} else if (call == 2) {
//					printHand(p1.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p2, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 1;
//					} else
//						call++;
//				} else if (call == 1) {
//					printHand(p4.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to order it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p2, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 4;
//					} else
//						call++;
//				} else if (call == 3) {
//					printHand(p2.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p2, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 2;
//					} else
//						call++;
//				}
//			} else if (p3.isDealer()) {
//				if (call == 0) {
//					printHand(p4.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p3, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 4;
//					} else
//						call++;
//				} else if (call == 2) {
//					printHand(p2.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p3, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 2;
//					} else
//						call++;
//				} else if (call == 1) {
//					printHand(p1.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to order it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p3, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 1;
//					} else
//						call++;
//				} else if (call == 3) {
//					printHand(p3.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p3, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 3;
//					} else
//						call++;
//				}
//			} else {
//				if (call == 0) {
//					printHand(p1.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p4, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 1;
//					} else
//						call++;
//				} else if (call == 2) {
//					printHand(p3.getHand());
//					printCard(tUp);
//					printHand(p1.getHand());
//					printCard(tUp);
//					System.out.println("Would you like the dealer to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p4, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = true;
//						caller = 3;
//					} else
//						call++;
//				} else if (call == 1) {
//					printHand(p2.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to order it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p4, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 2;
//					} else
//						call++;
//				}
//				if (call == 3) {
//					printHand(p4.getHand());
//					printCard(tUp);
//					System.out.println("Would you like to pick it up: (y or n)");
//					yorn = reader.next().charAt(0);
//					if (yorn == 'y' || yorn == 'Y') {
//						goAlone();
//						pickUp(p4, tUp);
//						trump = tUp.getSuit();
//						t1CallSuit = false;
//						caller = 4;
//					} else
//						call++;
//				}
//			}
//		}
//		dead = tUp.getSuit();
//		while (call < 7 && trump == null) {
//			if (p1.isDealer()) {
//				if (call == 4) {
//					printHand(p2.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 2;
//				} else if (call == 5) {
//					printHand(p3.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 3;
//				} else {
//					printHand(p4.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 4;
//				}
//			} else if (p2.isDealer()) {
//				if (call == 4) {
//					printHand(p3.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 3;
//				} else if (call == 5) {
//					printHand(p4.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 4;
//				} else {
//					printHand(p1.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 1;
//				}
//			} else if (p3.isDealer()) {
//				if (call == 4) {
//					printHand(p4.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 4;
//				} else if (call == 5) {
//					printHand(p1.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 1;
//				} else {
//					printHand(p2.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 2;
//				}
//			} else {
//				if (call == 4) {
//					printHand(p1.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 1;
//				} else if (call == 5) {
//					printHand(p2.getHand());
//					trump = secondround(dead);
//					t1CallSuit = false;
//					caller = 2;
//				} else {
//					printHand(p3.getHand());
//					trump = secondround(dead);
//					t1CallSuit = true;
//					caller = 3;
//				}
//			}
//			if (trump == null)
//				call++;
//		}
//		while (trump == null) {
//			if (p1.isDealer()) {
//				printHand(p1.getHand());
//				t1CallSuit = true;
//				caller = 1;
//			} else if (p2.isDealer()) {
//				printHand(p2.getHand());
//				t1CallSuit = false;
//				caller = 2;
//			} else if (p3.isDealer()) {
//				printHand(p3.getHand());
//				t1CallSuit = true;
//				caller = 3;
//			} else {
//				printHand(p4.getHand());
//				t1CallSuit = false;
//				caller = 4;
//			}
//			if (dead == DIAMOND) {
//				System.out.println("Stick the dealer. Call Trump (h = Hearts, s = spades, c = clubs): ");
//				cSuit = reader.next().charAt(0);
//				if (cSuit == 'h' || cSuit == 'H') {
//					goAlone();
//					trump = HEART;
//				} else if (cSuit == 's' || cSuit == 'S') {
//					goAlone();
//					trump = SPADE;
//				} else if (cSuit == 'c' || cSuit == 'C') {
//					goAlone();
//					trump = CLUB;
//				}
//			}
//
//			if (dead == HEART) {
//				System.out.println("Stick the dealer. Call Trump (s = spades, c = clubs, d = diamonds): ");
//				cSuit = reader.next().charAt(0);
//				if (cSuit == 's' || cSuit == 'S') {
//					goAlone();
//					trump = SPADE;
//				} else if (cSuit == 'c' || cSuit == 'C') {
//					goAlone();
//					trump = CLUB;
//				} else if (cSuit == 'd' || cSuit == 'D') {
//					goAlone();
//					trump = DIAMOND;
//				}
//			}
//			if (dead == SPADE) {
//				System.out.println("Stick the dealer. Call Trump (h = Hearts, c = clubs, d = diamonds): ");
//				cSuit = reader.next().charAt(0);
//				if (cSuit == 'h' || cSuit == 'H') {
//					goAlone();
//					trump = HEART;
//				} else if (cSuit == 'c' || cSuit == 'C') {
//					goAlone();
//					trump = CLUB;
//				} else if (cSuit == 'd' || cSuit == 'D') {
//					goAlone();
//					trump = DIAMOND;
//				}
//			} else {
//				System.out.println("Stick the dealer. Call Trump (h = Hearts, s = spades, d = diamonds): ");
//				cSuit = reader.next().charAt(0);
//				if (cSuit == 'h' || cSuit == 'H') {
//					goAlone();
//					trump = HEART;
//				} else if (cSuit == 's' || cSuit == 'S') {
//					goAlone();
//					trump = SPADE;
//				} else if (cSuit == 'd' || cSuit == 'D') {
//					goAlone();
//					trump = DIAMOND;
//				}
//			}
//		}
//		if (!alone) {
//			if (p1.isDealer()) {
//				playTrick(p2, p3, p4, p1, trump);
//			} else if (p2.isDealer()) {
//				playTrick(p3, p4, p1, p2, trump);
//			} else if (p3.isDealer()) {
//				playTrick(p4, p1, p2, p3, trump);
//			} else {
//				playTrick(p1, p2, p3, p4, trump);
//			}
//		} else {
//			if (p1.isDealer()) {
//				if (caller == 1)
//					playTrick(p2, p4, p1, trump);
//				else if (caller == 2)
//					playTrick(p2, p3, p1, trump);
//				else if (caller == 3)
//					playTrick(p2, p3, p4, trump);
//				else
//					playTrick(p3, p4, p1, trump);
//			} else if (p2.isDealer()) {
//				if (caller == 1)
//					playTrick(p4, p1, p2, trump);
//				else if (caller == 2)
//					playTrick(p3, p1, p2, trump);
//				else if (caller == 3)
//					playTrick(p3, p4, p2, trump);
//				else
//					playTrick(p3, p4, p1, trump);
//			} else if (p3.isDealer()) {
//				if (caller == 1)
//					playTrick(p4, p1, p2, trump);
//				else if (caller == 2)
//					playTrick(p1, p2, p3, trump);
//				else if (caller == 3)
//					playTrick(p4, p2, p3, trump);
//				else
//					playTrick(p4, p1, p3, trump);
//			} else {
//				if (caller == 1)
//					playTrick(p1, p2, p4, trump);
//				else if (caller == 2)
//					playTrick(p1, p2, p3, trump);
//				else if (caller == 3)
//					playTrick(p2, p3, p4, trump);
//				else
//					playTrick(p1, p3, p4, trump);
//			}
//		}
//	}

	/******************************************************************
	 * A method that reads a character from the user on whether or not 
	 * they want to go alone. It sets a boolean accordingly.
	 *
	 *****************************************************************/
//	public boolean goAlone(Player p) {
//		
//	}

	/******************************************************************
	 * A method that asks which card the player will remove from their 
	 * hand to pickup the new card.
	 * 
	 * @param p
	 *            the player that is picking up a card
	 * @param c
	 *            the card the player is picking up
	 *****************************************************************/
	public void pickUp(Player p, Card c) {
		System.out.println("Pick a card to replace: ");
		printHand(p.getHand());
		input = reader.nextInt();
		p.getHand().remove(input);
		p.getHand().add(c);
		p.setHand(organizeHand(p.getHand()));
	}

	/******************************************************************
	 * A method that runs when the turned up card is turned down and 
	 * the users get to choose trump.
	 * 
	 * @param dead
	 *            The suit of the turned over card
	 * @return returns the suit chosen
	 *****************************************************************/
//	public Suits secondround(Suits dead) {
//		if (dead == DIAMOND) {
//			System.out.println("Would you like to call Trump? (h = Hearts, s = spades, c = clubs, n = no): ");
//			cSuit = reader.next().charAt(0);
//			if (cSuit == 'h' || cSuit == 'H') {
//				goAlone();
//				return HEART;
//			} else if (cSuit == 's' || cSuit == 'S') {
//				goAlone();
//				return SPADE;
//			} else if (cSuit == 'c' || cSuit == 'C') {
//				goAlone();
//				return CLUB;
//			} else {
//				return null;
//			}
//		} else if (dead == HEART) {
//			System.out.println("Would you like to call Trump? (s = spades, c = clubs, d = diamonds, n = no): ");
//			cSuit = reader.next().charAt(0);
//			if (cSuit == 's' || cSuit == 'S') {
//				goAlone();
//				return SPADE;
//			} else if (cSuit == 'c' || cSuit == 'C') {
//				goAlone();
//				return CLUB;
//			} else if (cSuit == 'd' || cSuit == 'D') {
//				goAlone();
//				return DIAMOND;
//			} else {
//				return null;
//			}
//		} else if (dead == SPADE) {
//			System.out.println("Would you like to call Trump? (h = Hearts, c = clubs, d = diamonds, n = no): ");
//			cSuit = reader.next().charAt(0);
//			if (cSuit == 'h' || cSuit == 'H') {
//				goAlone();
//				return HEART;
//			} else if (cSuit == 'c' || cSuit == 'C') {
//				goAlone();
//				return CLUB;
//			} else if (cSuit == 'd' || cSuit == 'D') {
//				goAlone();
//				return DIAMOND;
//			} else {
//				return null;
//			}
//		} else {
//			System.out.println("Would you like to call Trump? (h = Hearts, s = spades, d = diamonds, n = no): ");
//			cSuit = reader.next().charAt(0);
//			if (cSuit == 'h' || cSuit == 'H') {
//				goAlone();
//				return HEART;
//			} else if (cSuit == 's' || cSuit == 'S') {
//				goAlone();
//				return SPADE;
//			} else if (cSuit == 'd' || cSuit == 'D') {
//				goAlone();
//				return DIAMOND;
//			} else {
//				return null;
//			}
//		}
//	}
//}
}