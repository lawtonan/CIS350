package game;

public class Player {
	public Card hand[] = new Card[5];
	public Team team;
	public boolean dealer;
	
	public Player(Team team, Card hand[]) {
		this.team = team;
		this.hand = hand;
		dealer = false;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card hand[]) {
		this.hand = hand;
	}
	
	public void setCard(int cnum,Card c) {
		this.hand[cnum]=c;
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
