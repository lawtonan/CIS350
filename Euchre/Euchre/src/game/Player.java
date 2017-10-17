package game;

/**********************************************************************
 * The Player class is a class that holds each players information. 
 * Such as their hand, their team, and whether or not they are the
 * dealer.
 * 
 * @author Andrew Lawton
 * @author Dan Schroeder
 * @author Aron Ockerse
 * @version 1.0
 *********************************************************************/
public class Player {
	
	/** An array of Cards that designates the hand */
	public Card hand[] = new Card[5];
	
	/** A enumerated type to hold which team the player belongs */
	public Team team;
	
	/** A boolean to determine if the player is the dealer */
	public boolean dealer;
	
	/******************************************************************
	 * A Constructor that sets the players team and hand, while setting
	 * dealer to false.
	 * 
	 * @param Team team The players team
	 * @param Card hand[] an array of the players hand
	 *****************************************************************/
	public Player(Team team, Card hand[]) {
		this.team = team;
		this.hand = hand;
		dealer = false;
	}

	/******************************************************************
	 * A accessor that returns the hand
	 * 
	 * @param none
	 * @return Card[] returns the hand Array
	 *****************************************************************/
	public Card[] getHand() {
		return hand;
	}

	/******************************************************************
	 * A mutator that sets the hand.
	 * 
	 * @param Card hand[] the players hand to be set
	 * @return void
	 *****************************************************************/
	public void setHand(Card hand[]) {
		this.hand = hand;
	}
	
	/******************************************************************
	 * A mutator to set a card to a certain index in the hand
	 * 
	 * @param int cnum the index of the Array
	 * @param Card c the card to set to the index
	 *****************************************************************/
	public void setCard(int cnum, Card c) {
		this.hand[cnum]=c;
	}
	
	/******************************************************************
	 * A accessor that returns the Card given by a certain index.
	 * 
	 * @param int cnum the index in the array
	 * @return Card returns the card from the desired index
	 *****************************************************************/
	public Card getCard(int cnum) {
		return this.hand[cnum];
	}

	/******************************************************************
	 * A accessor that returns the players team
	 * 
	 * @param none
	 * @return Team returns team
	 *****************************************************************/
	public Team getTeam() {
		return team;
	}

	/******************************************************************
	 * A mutator that sets the team of the player
	 * 
	 * @param Team team The desired team to set
	 * @return void
	 *****************************************************************/
	public void setTeam(Team team) {
		this.team = team;
	}

	/******************************************************************
	 * A method that returns if the player is the dealer or not
	 * 
	 * @param none
	 * @return boolean returns dealer
	 *****************************************************************/
	public boolean isDealer() {
		return dealer;
	}

	/******************************************************************
	 * A method that sets the boolean to whether or not the player is
	 * the dealer.
	 * 
	 * @param boolean dealer the desired boolean for this player
	 * @return void
	 *****************************************************************/
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	
}
