package game;

import java.util.ArrayList;
import java.util.Collections;

/**********************************************************************
 * The Euchre class is our main class that controls the functionality 
 * of the game to be played in the console window. It can create a deck 
 * and keep track of score as the game is played.
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
	private Team TEAM1 = Team.Team1;

	/** A type to assign Team 2. */
	private Team TEAM2 = Team.Team2;

	/** A suit used to keep track of trump. */
	private Suits trump;

	/** An ArrayList of cards for the deck. */
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	/** An ArrayList of players. */
	private ArrayList<Player> players = new ArrayList<Player>();

	/** An integer to keep track of team 1's score. */
	private int t1Score;

	/** An integer to keep track of team 2's score. */
	private int t2Score;

	/** A card used to keep track of the turn up. */
	private Card turnUp;

	/** A boolean used to see who called the trump. */
	private boolean t1CallSuit;

	/** A boolean used to see who goes alone. */
	private boolean alone;

	/** An integer to keep track of team 1's trick count. */
	private static int t1Trick;

	/** An integer to keep track of team 2's trick count. */
	private static int t2Trick;
	
	/** A Card object used to track the high card in scoring. */
	private Card highCard;
	
	/** An ArrayList of cards to keep track of played cards.  */
	private ArrayList<Card> play = new ArrayList<Card>();

	/******************************************************************
	 * A constructor for Euchre. This constructor instantiates all
	 * values for the game to be played. (Creates the deck and players
	 *****************************************************************/
	public Euchre() {
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
	}
	
	/******************************************************************
	 * A method to return the players.
	 * 
	 * @return returns an ArrayList of player objects (the players)
	 *****************************************************************/
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/******************************************************************
	 * A method to return the trump.
	 * 
	 * @return returns a Suit for trump.
	 *****************************************************************/
	public Suits getTrump() {
		return trump;
	}
	
	/******************************************************************
	 * A method to return the played cards.
	 * 
	 * @return returns an ArrayList of played cards
	 *****************************************************************/
	public ArrayList<Card> getPlay() {
		return play;
	}
	
	/******************************************************************
	 * A method to return the deck.
	 * 
	 * @return returns an ArrayList of cards.
	 *****************************************************************/
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	/******************************************************************
	 * A method to check if alone is true or false..
	 * 
	 * @return returns a boolean.
	 *****************************************************************/
	public boolean alone() {
		return alone;
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
	public void setT1Score(int t1) {
		t1Score = t1;
	}

	/******************************************************************
	 * An adder that increments team 1's score.
	 * 
	 * @param p
	 * 			the point value
	 *****************************************************************/
	public void t1Point(int p) {
		setT1Score(getT1Score() + p);
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
	public void setT2Score(int t2) {
		t2Score = t2;
	}

	/******************************************************************
	 * An incrementor that increments team 2's score.
	 * 
	 * @param p
	 * 			the point value
	 *****************************************************************/
	public void t2Point(int p) {
		setT2Score(getT2Score() + p);
	}

	/******************************************************************
	 * A method that returns the games status.
	 * 
	 * @return returns a boolean whether the game is complete
	 *****************************************************************/
	public boolean gameStatus() {
		return ((t1Score >= 10) || (t2Score >= 10));
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
	 * @return returns True if played is a valid card
	 *****************************************************************/
	public boolean playable(Card lead, Card played, ArrayList<Card> hand) {

		if (lead == null)
			return true;
		
		if (lead.getSuit() == trump || leftBower(lead, trump)) {
			if (played.getSuit() == trump 
					|| leftBower(played, trump)) {
				return true;
			} else {
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getSuit() == trump
						|| leftBower(hand.get(i), trump)) 
						return false;
				}
			}
		} else {
			if (lead.getSuit() == played.getSuit() 
				&& !leftBower(played, trump))
				return true;
			for (int i = 0; i < hand.size(); i++) {
				if (lead.getSuit() == hand.get(i).getSuit() 
					&& !leftBower(hand.get(i), trump))
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
	public void setTrump(Suits t) {
		trump = t;
	}

	/******************************************************************
	 * A method that determines which card is the highest out of those 
	 * played, and will return that card.
	 * 
	 * @param active
	 *            an array list of cards that were played
	 * @return returns the highest value card
	 *****************************************************************/
	public Card takeTrick(ArrayList<Card> active) {
		Card high;
		high = active.get(0);
		for (int i = 1; i < active.size(); i++) {
			if (rightBower(high, trump))
				return high;
			else if (rightBower(active.get(i), trump))
				return active.get(i);
			else if (leftBower(high, trump)) {
				continue;
			} else if (high.getSuit() == trump && active.get(i).getSuit() == trump) {
				if (active.get(i).getCardName() > high.getCardName() || leftBower(active.get(i), trump))
					high = active.get(i);
			} else if (high.getSuit() != trump && (active.get(i).getSuit() == trump || leftBower(active.get(i), trump))) {
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
	 * @return returns the card to be turned up
	 *****************************************************************/
	public Card deal() {
		for (int j = 0; j < 4; j++) {
			players.get(j).getHand().clear();
		}
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
		return turnUp;
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
	 * A method to return the dealer.
	 * 
	 * @return returns an a player that is the dealer.
	 *****************************************************************/
	public Player getDealer() {
		if (players.get(0).isDealer()) {
				return players.get(0);
		} else if (players.get(1).isDealer()) {
				return players.get(1);
		} else if (players.get(2).isDealer()) {
				return players.get(2);
		} else {
				return players.get(3);
		}
	}
	
	/******************************************************************
	 * A method to return the first player.
	 * 
	 * @param dPlayer
	 * 					the dead player
	 * @return returns an player that plays first
	 *****************************************************************/
	public Player getFirstPlayer(int dPlayer) {
		if (players.get(0).isDealer()) {
			if (dPlayer != 1)
				return players.get(1);
			else 
				return players.get(2);
		} else if (players.get(1).isDealer()) {
			if (dPlayer != 2)
				return players.get(2);
			else
				return players.get(3);
		} else if (players.get(2).isDealer()) {
			if (dPlayer != 3)
				return players.get(3);
			else
				return players.get(0);
		} else {
			if (dPlayer != 0)
				return players.get(0);
			else
				return players.get(1);
		}
	}
	/******************************************************************
	 * A method to assign points to teams.
	 *****************************************************************/
	public void assignPoints() {
		if (t1CallSuit) {
			if (t1Trick == 5 && alone) {
				t1Point(4);
			} else if (t1Trick == 5) {
				t1Point(2);
			} else if (t1Trick >= 3) {
				t1Point(1);
			} else {
				t2Point(2);
			}
		} else {
			if (t2Trick == 5 && alone) {
				t2Point(4);
			} else if (t2Trick == 5) {
				t2Point(2);
			} else if (t2Trick >= 3) {
				t2Point(1);
			} else {
				t1Point(2);
			}
		}
		setT1Trick(0);
		setT2Trick(0);
		swapDealer();
	}
	
	/******************************************************************
	 * A method to set the alone boolean.
	 * 
	 * @param b
	 * 			the boolean to be set
	 *****************************************************************/
	public void setAlone(boolean b) {
		alone = b;
	}
	/******************************************************************
	 * A method to assign the trick.
	 * 
	 * @param players
	 * 					a array list of players
	 * @param dead
	 * 					dead player
	 * @param current
	 * 					the current player
	 * 
	 * @return returns a Player that receives the trick.
	 *****************************************************************/
	public Player assignTrick(ArrayList<Player> players, int dead, Player current) {
		highCard = takeTrick(play);
		for (int i = 0; i < play.size(); i++) {	
			if (highCard == play.get(i)) {
				if (current.getTeam() == TEAM1) {
					setT1Trick(getT1Trick() + 1);
					return current;
				} else {
					setT2Trick(getT2Trick() + 1);
					return current;
				}
			}
			current = nextPlayer(players.indexOf(current), dead);
		}
		return null;
	}
	
	
	/******************************************************************
	 * A method that returns next player.
	 * 
	 * @param current 
	 * 			  The current player index 
	 * @param dPlayer
	 * 			  The player not playing if alone.
	 * @return player
	 * 			  Returns the next player
	 *****************************************************************/
	public Player nextPlayer(int current, int dPlayer) {
		if (current == 0) 
			if (dPlayer != 1)
				return players.get(1);
			else 
				return players.get(2);
		else if (current == 1) 
			if (dPlayer != 2)
				return players.get(2);
			else
				return players.get(3);
		else if (current == 2) 
			if (dPlayer != 3)
				return players.get(3);
			else
				return players.get(0);
		else
			if (dPlayer != 0)
				return players.get(0);
			else
				return players.get(1);
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


}
