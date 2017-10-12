package game;

public class Card {
	private int cardName;
	private Suits suit;
	
	public Card() {
		cardName = -1;
		this.suit = null;
	}
	
	public Card(int card, Suits suit){
		cardName = card;
		this.suit = suit;
	}

	public int getCardName() {
		return cardName;
	}

	public void setCardName(int cardName) {
		this.cardName = cardName;
	}

	public Suits getSuit() {
		return suit;
	}

	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	
	public String toString(){
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
