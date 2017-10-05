package game;

import java.util.*;

public class Player {
	public ArrayList<Card> hand = new ArrayList<Card>();
	public Team team;
	public boolean dealer;
	
	public Player(Team team, ArrayList<Card> hand) {
		this.team = team;
		this.hand = hand;
		dealer = false;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean isDealer() {
		return dealer;
	}

	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	
}
