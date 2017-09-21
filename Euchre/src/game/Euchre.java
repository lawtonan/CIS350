package game;

import java.util.ArrayList;

public class Euchre {
	private Suits DIAMOND = Suits.Diamonds;
	private Suits HEART = Suits.Hearts;
	private Suits SPADE = Suits.Spades;
	private Suits CLUB = Suits.Clubs;
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	public void createDeck(){
		for(int i = 0; i<=5; i++){
			deck.add(new Card(i, DIAMOND));
			deck.add(new Card(i, HEART));
			deck.add(new Card(i, CLUB));
			deck.add(new Card(i, SPADE));
		}
	}
	public void printCards(){
		System.out.println(deck);
	}
	public static void main(String[] args){
		Euchre game = new Euchre();
		game.createDeck();
		game.printCards();
	}
}