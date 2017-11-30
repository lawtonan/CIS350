package game;

import java.util.ArrayList;

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
	
	/** An array of Cards that designates the hand. */
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	/** A enumerated type to hold which team the player belongs. */
	private Team team;
	
	/** A boolean to determine if the player is the dealer. */
	private boolean dealer;
	
	/******************************************************************
	 * A Constructor that sets the players team and hand, while setting
	 * dealer to false.
	 * 
	 * @param team The players team
	 * @param hand an array list of the players hand
	 *****************************************************************/
	public Player(Team team, ArrayList<Card> hand) {
		this.team = team;
		this.hand = hand;
		dealer = false;
	}

	/******************************************************************
	 * A accessor that returns the hand.
	 * 
	 * @return 		returns the hand Array
	 *****************************************************************/
	public ArrayList<Card> getHand() {
		return hand;
	}

	/******************************************************************
	 * A mutator that sets the hand.
	 * 
	 * @param hand the players hand to be set
	 *****************************************************************/
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	/******************************************************************
	 * A mutator to set a card to a certain index in the hand.
	 * 
	 * @param c the card to set to the index
	 *****************************************************************/
	public void setCard(Card c) {
		this.hand.add(c);
	}
	
	/******************************************************************
	 * A accessor that returns the Card given by a certain index.
	 * 
	 * @param cnum the index in the array
	 * @return 		returns the card from the desired index
	 *****************************************************************/
	public Card getCard(int cnum) {
		return this.hand.get(cnum);
	}

	/******************************************************************
	 * A accessor that returns the players team.
	 * 
	 * @return 		returns team
	 *****************************************************************/
	public Team getTeam() {
		return team;
	}

	/******************************************************************
	 * A mutator that sets the team of the player.
	 * 
	 * @param team The desired team to set
	 *****************************************************************/
	public void setTeam(Team team) {
		this.team = team;
	}

	/******************************************************************
	 * A method that returns if the player is the dealer or not.
	 * 
	 * @return 		returns true if dealer
	 *****************************************************************/
	public boolean isDealer() {
		return dealer;
	}

	/******************************************************************
	 * A method that sets the boolean to whether or not the player is
	 * the dealer.
	 * 
	 * @param dealer the desired boolean for this player
	 *****************************************************************/
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	
}
