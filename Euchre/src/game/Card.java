package game;

/**********************************************************************
 * The Card class is a class that creates Card objects that are used 
 * in the creation of the deck in Euchre and used to compare in the 
 * played comparison.
 * 
 * @author Andrew Lawton
 * @author Dan Schroeder
 * @author Aron Ockerse
 * @version 1.0
 *********************************************************************/
public class Card {
	
	/** An integer that keeps track of the card name. */
	private int cardName;
	
	/** The suit of the card. */
	private Suits suit;
	
	
	/******************************************************************
	 * A default constructor that sets card name to -1 and the suit to
	 * null.
	 *****************************************************************/
	public Card() {
		cardName = -1;
		this.suit = null;
	}
	
	/******************************************************************
	 * A Constructor that takes an integer for the card name and a suit 
	 * for the card itself.
	 * 
	 * @param card The designated card name
	 * @param suit the suit the card will be
	 *****************************************************************/
	public Card(int card, Suits suit) {
		cardName = card;
		this.suit = suit;
	}

	/******************************************************************
	 * A method that returns card name.
	 * 
	 * @return 		returns cardName
	 *****************************************************************/
	public int getCardName() {
		return cardName;
	}

	/******************************************************************
	 * A method that sets the card name.
	 * 
	 * @param cardName The desired cardName
	 *****************************************************************/
	public void setCardName(int cardName) {
		this.cardName = cardName;
	}

	/******************************************************************
	 * A method that returns the suit.
	 * 
	 * @return 		returns suit
	 *****************************************************************/
	public Suits getSuit() {
		return suit;
	}

	/******************************************************************
	 * A method that sets the suit.
	 * 
	 * @param suit The desired suit
	 *****************************************************************/
	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	
	/******************************************************************
	 * A toString method that returns a string for each card depending
	 * on their set number from cardName.
	 * 
	 * @return 		the Correct string for Card Name
	 *****************************************************************/
	public String toString() {
		if (cardName == 0)
			return "9 of " + suit;
		else if (cardName == 1)
			return "10 of " + suit;
		else if (cardName == 2)
			return "Jack of " + suit;
		else if (cardName == 3)
			return "Queen of " + suit;
		else if (cardName == 4)
			return "King of " + suit;
		else if (cardName == 5)
			return "Ace of " + suit;
		return "";
	}
}
