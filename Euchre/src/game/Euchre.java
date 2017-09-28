package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Euchre {
	private Suits DIAMOND = Suits.Diamonds;
	private Suits HEART = Suits.Hearts;
	private Suits SPADE = Suits.Spades;
	private Suits CLUB = Suits.Clubs;
	Suits trump;
	private ArrayList<Card> deck = new ArrayList<Card>();
	static int t1Score;
	static int t2Score;
	private int cardCount;
	Card p1[] = new Card[5];
	Card p2[] = new Card[5];
	Card p3[] = new Card[5];
	Card p4[] = new Card[5];
	Card played[] = new Card[5];
	Card turnUp;
	int n;
	Scanner reader = new Scanner(System.in);  // Reading from System.in

	public ArrayList<Card> createDeck() {
		for (int i = 0; i <= 5; i++) {
			deck.add(new Card(i, DIAMOND));
			deck.add(new Card(i, HEART));
			deck.add(new Card(i, CLUB));
			deck.add(new Card(i, SPADE));
		}
		return deck;
	}

	public void printCards(ArrayList<Card> deck) {
		System.out.println(deck);
	}

	public static void main(String[] args) {
		Euchre game = new Euchre();
		ArrayList<Card> gameDeck = game.createDeck();
		game.printCards(gameDeck);
		t1Score = 0;
		t2Score = 0;
		game.shuffle(gameDeck);
		game.printCards(gameDeck);
		
		//deal(deck,p1,p2,p3,p4);
		
		//printHand(p1);
	}

	public void T1Point() {
		t1Score++;
	}

	public void T2Point() {
		t2Score++;
	}

	public void GameStatus() {
		if (t1Score >= 10)
			System.out.println("Team 1 wins");
		else if (t2Score >= 10)
			System.out.println("Team 2 wins");
		else
			System.out.println("Team one score: " + t1Score + "\nTeam two score: " + t2Score);
	}

	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}

	public boolean playable(Card Lead, Card Played, Card hand[]) { // also need
																	// users
																	// hand
		if (Lead.getSuit() == Played.getSuit())
			return true;
		else if (Lead.getSuit() != Played.getSuit()) {
			for (int i = 0; i < hand.length; i++) {
				if (Lead.getSuit() == hand[i].getSuit())
					return false;
			}
		}
		return false;
	}

	public void setTrump(Suits t) {
		trump = t;
	}

	public void takeTrick(Card active[], Suits t){ // order played
		Card High;
		High = active[0];
		for(int i=1;i<4;i++)
		{
			if(High.getSuit()!=active[i].getSuit()){
				if(active[i].getSuit()==t){
					High=active[i];
				}
			}
			else if(High.getCardName()<active[i].getCardName())
				High=active[i];
		}
	}

	public void deal(ArrayList<Card> Deck, Card p1[], Card p2[], Card p3[], Card p4[]) {
		shuffle(Deck);
		cardCount = 0;
		for (int i = 0; i < 5; i++) {
			this.p1[i] = Deck.get(cardCount);
			cardCount++;
			this.p2[i] = Deck.get(cardCount);
			cardCount++;
			this.p3[i] = Deck.get(cardCount);
			cardCount++;
			this.p4[i] = Deck.get(cardCount);
			cardCount++;
		}
		turnUp=Deck.get(cardCount);
		organizeHand(p1);
		organizeHand(p2);
		organizeHand(p3);
		organizeHand(p4);
	}

	public void organizeHand(Card hand[]) { // still need to move cards down
											// were just swapping cards right
											// now
		Card change;
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < i; j++) {
				if (hand[i].getSuit() == hand[j].getSuit()) {
					if (hand[i].getCardName() > hand[j].getCardName()) {
						change = hand[i];
						hand[i] = hand[j];
						hand[j] = change;
					}
				}
			}
		}
	}

	
	public void playTrick(Card h1[], Card h2[], Card h3[], Card h4[], Suits t)
	{
	
		printHand(h1);
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		played[0]=h1[n];
	
		printHand(h2);
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while( playable(played[0],h2[n],h2) == false){
			System.out.println("not a valid card");
			printHand(h2);
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[1]=h2[n];
	
		printHand(h3);
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while( playable(played[0],h3[n],h3) == false){
			System.out.println("not a valid card");
			printHand(h3);
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[2]=h3[n];
		
		printHand(h4);
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while( playable(played[0],h4[n],h4) == false){
			System.out.println("not a valid card");
			printHand(h4);
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[3]=h4[n];
	}
	
	public void printHand(Card hand[]){
		for(int i=0;i<hand.length;i++){
			switch (hand[i].getCardName()) {
			case 0:
				System.out.println("9 of " + hand[i].getSuit());
				break;
			case 1:
				System.out.println("10 of " + hand[i].getSuit());
				break;
			case 2:
				System.out.println("Jack of " + hand[i].getSuit());
				break;
			case 3:
				System.out.println("Queen of " + hand[i].getSuit());
				break;
			case 4: 
				System.out.println("King of " + hand[i].getSuit());
				break;
			case 5:
				System.out.println("Ace of " + hand[i].getSuit());
				break;
			}
		}
	}
	
	
}