package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Euchre {
	private Suits DIAMOND = Suits.Diamonds;
	private Suits HEART = Suits.Hearts;
	private Suits SPADE = Suits.Spades;
	private Suits CLUB = Suits.Clubs;
	private static Team TEAM1 = Team.Team1;
	private static Team TEAM2 = Team.Team2;
	private static Suits trump, dead;
	public static Card a[] = new Card[5];
	public static Card b[] = new Card[5];
	public static Card c[] = new Card[5];
	public static Card d[] = new Card[5];
	public ArrayList<Card> deck = new ArrayList<Card>();
	static int t1Score;
	static int t2Score;
	public int cardCount;
	public static Player p1;
	public static Player p2;
	public static Player p3;
	public static Player p4;
	public Card played[] = new Card[4];
	public Card play[] = new Card[3];
	Card turnUp;
	public boolean t1CallSuit, alone;
	int n;
	public Card highCard;
	Scanner reader = new Scanner(System.in); // Reading from System.in
	public static int t1Trick;
	public static int t2Trick;
	public Card Blank = new Card();
	char yorn, cSuit;
	public int call, caller;

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

		p1 = new Player(TEAM1, a);
		p2 = new Player(TEAM2, b);
		p3 = new Player(TEAM1, c);
		p4 = new Player(TEAM2, d);

		game.shuffle(gameDeck);
		game.printCards(gameDeck);
		//game.deal();
		// printHand(p1.getHand());
		// printHand(p2.getHand());
		// printHand(p3.getHand());
		// printHand(p4.getHand());
		game.playTrick(p1, p2, p3, p4, trump);
		System.out.println(t1Trick);
		System.out.println(t2Trick);

	}
	
	public void swapDealer()
	{
		if(p1.isDealer())
		{
			p1.setDealer(false);
			p2.setDealer(true);
		}
		else if(p2.isDealer())
		{
			p2.setDealer(false);
			p3.setDealer(true);
		}
		else if(p3.isDealer())
		{
			p3.setDealer(false);
			p4.setDealer(true);
		}
		else if(p4.isDealer())
		{
			p4.setDealer(false);
			p1.setDealer(true);
		}
	}

	public void T1Point() {
		t1Score++;
	}

	public void T2Point() {
		t2Score++;
	}

	public boolean GameStatus() {
		if (t1Score >= 10) {
			System.out.println("Team 1 wins");
			return true;
		} else if (t2Score >= 10) {
			System.out.println("Team 2 wins");
			return true;
		} else {
			System.out.println("Team one score: " + t1Score + "\nTeam two score: " + t2Score);
			return false;
		}
	}

	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
		deal();
	}

	public boolean playable(Card Lead, Card Played, Card hand[]) { // also need
																	// users
		if (Played.getSuit() == null)
			return false; // hand
		if (Lead.getSuit() == Played.getSuit())
			return true;
		else if (Lead.getSuit() != Played.getSuit()) {
			for (int i = 0; i < hand.length; i++) {
				if (Lead.getSuit() == hand[i].getSuit())
					return false;
			}
		}
		return true;
	}

	public void setTrump(Suits t) {
		trump = t;
	}

	public Card takeTrick(Card active[], Suits t) { // order played
		Card High;
		High = active[0];
		for (int i = 1; i < active.length; i++) {
			if (rightBower(High, t))
				return High;
			else if (rightBower(active[i], t))
				return active[i];
			else if (leftBower(High, t)) {

			}
			else if (High.getSuit() == t && active[i].getSuit() == t) {
				if (active[i].getCardName() > High.getCardName() || leftBower(active[i], t))
					High = active[i];
			}
			else if (High.getSuit() != t && (active[i].getSuit() == t || leftBower(active[i], t))) {
				High = active[i];
			}
			else if (High.getSuit() == active[i].getSuit())
				if (High.getCardName() < active[i].getCardName())
					High = active[i];
		// if (High.getSuit() != active[i].getSuit()) {
		// 		if (active[i].getSuit() == t) {
		// 			High = active[i];
		// 		}
		// 	} else if (High.getCardName() < active[i].getCardName())
		// 		High = active[i];
		}
		return High;
	}

private boolean rightBower(Card c, Suits t) {
	if (c.getCardName() == 2 && c.getSuit() == t)
		return true;
	return false;
}
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
//	public Card takeTrick(Card active[], Suits t) { // order played
//		Card High;
//		High = active[0];
//		for (int i = 1; i < active.length; i++) {
//			if (High.getSuit() != active[i].getSuit()) {
//				if (active[i].getSuit() == t) {
//					High = active[i];
//				}
//			} else if (High.getCardName() < active[i].getCardName())
//				High = active[i];
//		}
//		return High;
//	}

	public void deal() {
		cardCount = 0;
		t1Trick = 0;
		t2Trick = 0;
		for (int i = 0; i < 5; i++) 
		{
			if(p1.isDealer())
			{
				p2.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p3.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p4.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p1.setCard(i, this.deck.get(cardCount));
				cardCount++;
			}
			else if(p2.isDealer())
			{
				p3.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p4.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p1.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p2.setCard(i, this.deck.get(cardCount));
				cardCount++;
			}
			else if(p3.isDealer())
			{
				p4.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p1.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p2.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p3.setCard(i, this.deck.get(cardCount));
				cardCount++;
			}
			else
			{
				p1.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p2.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p3.setCard(i, this.deck.get(cardCount));
				cardCount++;
				p4.setCard(i, this.deck.get(cardCount));
				cardCount++;
			}
		}
		turnUp = deck.get(cardCount);
		organizeHand(p1.getHand());
		organizeHand(p2.getHand());
		organizeHand(p3.getHand());
		organizeHand(p4.getHand());
		callTrump(turnUp);
	}

	public void organizeHand(Card hand[]) { // still need to move cards down
											// were just swapping cards right
											// now
		Card change;
		for (int i = 2; i < hand.length; i++) {
			for (int j = 0; j < i; j++) {
				if (hand[i].getSuit() == hand[j].getSuit()) {
					if (j + 1 < 5) {
						if (hand[i].getSuit() == hand[j + 1].getSuit()) {
							if (j + 2 < 5) {
								if (hand[i].getSuit() == hand[j + 2].getSuit()) {
									if (j + 3 < 5) {
										if (hand[i].getSuit() == hand[j + 3].getSuit()) {
											if (j + 4 < 5) {
												if (hand[i].getSuit() == hand[j + 3].getSuit()) {
													i = hand.length;
													j = i;
												} else if (hand[i].getSuit() != hand[j + 4].getSuit()) {
													change = hand[i];
													hand[i] = hand[j + 4];
													hand[j + 4] = change;
												}
											}
										} else if (hand[i].getSuit() != hand[j + 3].getSuit()) {
											change = hand[i];
											hand[i] = hand[j + 3];
											hand[j + 3] = change;
										}
									}
								} else if (hand[i].getSuit() != hand[j + 2].getSuit()) {
									change = hand[i];
									hand[i] = hand[j + 2];
									hand[j + 2] = change;
								}
							}
						} else if (hand[i].getSuit() != hand[j + 1].getSuit()) {
							change = hand[i];
							hand[i] = hand[j + 1];
							hand[j + 1] = change;
						}
					}
				}

			}
		}
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

	public void playTrick(Player pl1, Player pl2, Player pl3, Player pl4, Suits t) {

		printHand(pl1.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		played[0] = pl1.getCard(n);
		pl1.setCard(n, Blank);

		printHand(pl2.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl2.getCard(n), pl2.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl2.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[1] = pl2.getCard(n);
		pl2.setCard(n, Blank);

		printHand(pl3.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl3.getCard(n), pl3.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl3.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[2] = pl3.getCard(n);
		pl3.setCard(n, Blank);

		printHand(pl4.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl4.getCard(n), pl4.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl4.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[3] = pl4.getCard(n);
		pl4.setCard(n, Blank);

		highCard = takeTrick(played, t);
		if (highCard == played[0] || highCard == played[2]) {
			if (pl1.getTeam() == TEAM1 || pl3.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		} else {
			if (pl2.getTeam() == TEAM1 || pl4.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		}
		System.out.println("Team 1 Tricks: " + t1Trick);
		System.out.println("Team 2 Tricks: " + t2Trick);
		if (t1Trick + t2Trick < 5) {
			if (highCard == played[0])
				playTrick(pl1, pl2, pl3, pl4, t);
			else if (highCard == played[1])
				playTrick(pl2, pl3, pl4, pl1, t);
			else if (highCard == played[2])
				playTrick(pl3, pl4, pl1, pl2, t);
			else
				playTrick(pl4, pl1, pl2, pl3, t);
		} 
		
		if (t1CallSuit) 
		{
			if (t1Trick >= 3 && t1Trick < 5)
				T1Point();
			else if (t1Trick == 5) 
			{
				T1Point();
				T1Point();
			}
			else
			{
				T2Point();
				T2Point();
			}
		} 
		else 
		{
			if (t2Trick >= 3 && t2Trick < 5)
				T2Point();
			else if (t2Trick == 5) 
			{
				T2Point();
				T2Point();
			} 
			else {
				T1Point();
				T1Point();
			}
		}
		if (GameStatus()) 
		{
			System.out.println("Would you like to play again? (y) or (n)");
			yorn = reader.next().charAt(0);
			if (yorn == 'y' || yorn == 'Y') 
			{
				t1Score = 0;
				t2Score = 0;
				swapDealer();
				shuffle(deck);
			}
		} 
		else 
		{
			t1Trick = 0;
			t2Trick = 0;
			swapDealer();
			shuffle(deck);
		}
	}
	
	public void playTrick(Player pl1, Player pl2, Player pl3, Suits t) {

		printHand(pl1.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		play[0] = pl1.getCard(n);
		pl1.setCard(n, Blank);

		printHand(pl2.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(play[0], pl2.getCard(n), pl2.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl2.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		play[1] = pl2.getCard(n);
		pl2.setCard(n, Blank);

		printHand(pl3.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(play[0], pl3.getCard(n), pl3.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl3.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		play[2] = pl3.getCard(n);
		pl3.setCard(n, Blank);


		highCard = takeTrick(play, t);
		if (highCard == play[0]) {
			if (pl1.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		} else if (highCard == play[1]) {
			if (pl2.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		}
		else
		{
			if (pl3.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		}
		System.out.println("Team 1 Tricks: " + t1Trick);
		System.out.println("Team 2 Tricks: " + t2Trick);
		if (t1Trick + t2Trick < 5) {
			if (highCard == play[0])
				playTrick(pl1, pl2, pl3, t);
			else if (highCard == play[1])
				playTrick(pl2, pl3, pl1, t);
			else
				playTrick(pl3, pl1, pl2, t);
		} 
		
		if (t1CallSuit) 
		{
			if (t1Trick >= 3 && t1Trick < 5)
				T1Point();
			else if (t1Trick == 5) 
			{
				T1Point();
				T1Point();
				T1Point();
				T1Point();
			}
			else
			{
				T2Point();
				T2Point();
			}
		} 
		else 
		{
			if (t2Trick >= 3 && t2Trick < 5)
				T2Point();
			else if (t2Trick == 5) 
			{
				T2Point();
				T2Point();
				T2Point();
				T2Point();
			} 
			else {
				T1Point();
				T1Point();
			}
		}
		if (GameStatus()) 
		{
			System.out.println("Would you like to play again? (y) or (n)");
			yorn = reader.next().charAt(0);
			if (yorn == 'y' || yorn == 'Y') 
			{
				t1Score = 0;
				t2Score = 0;
				swapDealer();
				shuffle(deck);
			}
		} 
		else 
		{
			t1Trick = 0;
			t2Trick = 0;
			swapDealer();
			shuffle(deck);
		}
	}

	public static void printHand(Card hand[]) 
	{
		System.out.println("\nYour hand is:");
		for (int i = 0; i < hand.length; i++) {
			System.out.println(i + ": " + hand[i]);
		}				
	}
	
	public static void printCard(Card c) 
	{
		System.out.println("\nThe card turned up is: " + c);
	}
	
	public void callTrump(Card tUp)
	{
		call = 0;
		trump = null;
		while(call<4 && trump==null)
		{
			if(p1.isDealer())
			{
				if(call==0)
				{
					printHand(p2.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p1,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 2;
					}
					else
						call++;
				}
				else if(call==2)
				{
					printHand(p4.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p1,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 4;
					}
					else
						call++;
				}
				else if(call==1)
				{
					printHand(p3.getHand());
					printCard(tUp);
					System.out.println("Would you like to order it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p1,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 1;
					}
					else
						call++;
				}
				else if(call==3)
				{
					printHand(p1.getHand());
					printCard(tUp);
					System.out.println("Would you like to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p1,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 3;
					}
					else
						call++;
				}
			}
			
			else if(p2.isDealer())
			{
				if(call==0)
				{
					printHand(p3.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p2,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 3;
					}
					else
						call++;
				}
				else if(call==2)
				{
					printHand(p1.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p2,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 1;
					}
					else
						call++;
				}
				else if(call==1)
				{
					printHand(p4.getHand());
					printCard(tUp);
					System.out.println("Would you like to order it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p2,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 4;
					}
					else
						call++;
				}
				else if(call==3)
				{
					printHand(p2.getHand());
					printCard(tUp);
					System.out.println("Would you like to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p2,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 2;
					}
					else
						call++;
				}
			}
			
			else if(p3.isDealer())
			{
				if(call==0)
				{	
					printHand(p4.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p3,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 4;
					}
					else
						call++;
				}
				else if(call==2)
				{
					printHand(p2.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p3,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 2;
					}
					else
						call++;
				}
				else if(call==1)
				{
					printHand(p1.getHand());
					printCard(tUp);
					System.out.println("Would you like to order it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p3,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 1;
					}
					else
						call++;
				}
				else if(call==3)
				{
					printHand(p3.getHand());
					printCard(tUp);
					System.out.println("Would you like to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p3,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 3;
					}
					else
						call++;
				}
			}
			
			else
			{
				if(call==0)
				{
					printHand(p1.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p4,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 1;
					}
					else
						call++;
				}
				else if(call==2)
				{
					printHand(p3.getHand());
					printCard(tUp);
					printHand(p1.getHand());
					printCard(tUp);
					System.out.println("Would you like the dealer to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p4,tUp);
						trump = tUp.getSuit();
						t1CallSuit = true;
						caller = 3;
					}
					else
						call++;
				}
				else if(call==1)
				{
					printHand(p2.getHand());
					printCard(tUp);
					System.out.println("Would you like to order it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p4,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 2;
					}
					else
						call++;
				}
				if(call==3)
				{
					printHand(p4.getHand());
					printCard(tUp);
					System.out.println("Would you like to pick it up: (y or n)");
					yorn = reader.next().charAt(0);
					if(yorn == 'y' || yorn == 'Y')
					{
						goAlone();
						pickUp(p4,tUp);
						trump = tUp.getSuit();
						t1CallSuit = false;
						caller = 4;
					}
					else
						call++;
				}
			}
		}
		dead = tUp.getSuit();
		while(call<7&&trump == null)
		{
			if(p1.isDealer())
			{
				if(call==4)
				{
					printHand(p2.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 2;
				}
				else if(call == 5)
				{
					printHand(p3.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 3;
				}
				else
				{
					printHand(p4.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 4;
				}
			}
			else if(p2.isDealer())
			{
				if(call==4)
				{
					printHand(p3.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 3;
				}
				else if(call == 5)
				{
					printHand(p4.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 4;
				}
				else
				{
					printHand(p1.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 1;
				}
			}
			else if(p3.isDealer())
			{
				if(call==4)
				{
					printHand(p4.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 4;
				}
				else if(call == 5)
				{
					printHand(p1.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 1;
				}
				else
				{
					printHand(p2.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 2;
				}
			}
			else 
			{
				if(call==4)
				{
					printHand(p1.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 1;
				}
				else if(call == 5)
				{
					printHand(p2.getHand());
					trump = secondround(dead);
					t1CallSuit = false;
					caller = 2;
				}
				else
				{
					printHand(p3.getHand());
					trump = secondround(dead);
					t1CallSuit = true;
					caller = 3;
				}
			}
		if(trump == null)	
			call++;
		}
		while(trump==null)
		{
			if(p1.isDealer())
			{
				printHand(p1.getHand());
				t1CallSuit = true;
				caller = 1;
			}
			else if(p2.isDealer())
			{
				printHand(p2.getHand());
				t1CallSuit = false;
				caller = 2;
			}
			else if(p3.isDealer())
			{
				printHand(p3.getHand());
				t1CallSuit = true;
				caller = 3;
			}
			else
			{
				printHand(p4.getHand());
				t1CallSuit = false;
				caller = 4;
			}
			if(dead==DIAMOND)
			{
				System.out.println("Stick the dealer. Call Trump (h = Hearts, s = spades, c=clubs, n=no): ");
				cSuit = reader.next().charAt(0);
				if(cSuit == 'h' || cSuit == 'H')
				{
					goAlone();
					trump = HEART;
				}
				else if(cSuit == 's' || cSuit == 'S')
				{
					goAlone();
					trump = SPADE;
				}
				else if(cSuit == 'c' || cSuit == 'C')
				{
					goAlone();
					trump = CLUB;
				}
			}
			
			if(dead==HEART)
			{
				System.out.println("Stick the dealer. Call Trump (s = spades, c=clubs, d=diamonds, n=no): ");
				cSuit = reader.next().charAt(0);
				if(cSuit == 's' || cSuit == 'S')
				{
					goAlone();
					trump = SPADE;
				}
				else if(cSuit == 'c' || cSuit == 'C')
				{
					goAlone();
					trump = CLUB;
				}
				else if(cSuit == 'd' || cSuit == 'D')
				{
					goAlone();
					trump = DIAMOND;
				}
			}
			if(dead==SPADE)
			{
				System.out.println("Stick the dealer. Call Trump (h = Hearts, c=clubs, d=diamonds, n=no): ");
				cSuit = reader.next().charAt(0);
				if(cSuit == 'h' || cSuit == 'H')
				{
					goAlone();
					trump = HEART;
				}
				else if(cSuit == 'c' || cSuit == 'C')
				{
					goAlone();
					trump = CLUB;
				}
				else if(cSuit == 'd' || cSuit == 'D')
				{
					goAlone();
					trump = DIAMOND;
				}	
			}
			else
			{
				System.out.println("Stick the dealer. Call Trump (h = Hearts, s = spades, d=diamonds, n=no): ");
				cSuit = reader.next().charAt(0);
				if(cSuit == 'h' || cSuit == 'H')
				{
					goAlone();
					trump = HEART;
				}
				else if(cSuit == 's' || cSuit == 'S')
				{
					goAlone();
					trump = SPADE;
				}
				else if(cSuit == 'd' || cSuit == 'D')
				{
					goAlone();
					trump = DIAMOND;
				}		
			}
		}
		if(!alone)
		{
			if(p1.isDealer())
			{
				playTrick(p2,p3,p4,p1,trump);
			}
			else if(p2.isDealer())
			{
				playTrick(p3,p4,p1,p2,trump);
			}	
			else if(p3.isDealer())
			{
				playTrick(p4,p1,p2,p3,trump);
			}	
			else
			{
				playTrick(p1,p2,p3,p4,trump);
			}
		}
		else
		{
			if(p1.isDealer())
			{
				if(caller == 1)
					playTrick(p2,p4,p1,trump);
				else if(caller == 2)
					playTrick(p2,p3,p1,trump);
				else if(caller == 3)
					playTrick(p2,p3,p4,trump);	
				else
					playTrick(p3,p4,p1,trump);
			}
			else if(p2.isDealer())
			{
				if(caller == 1)
					playTrick(p4,p1,p2,trump);
				else if(caller == 2)
					playTrick(p3,p1,p2,trump);
				else if(caller == 3)
					playTrick(p3,p4,p2,trump);	
				else
					playTrick(p3,p4,p1,trump);
			}	
			else if(p3.isDealer())
			{
				if(caller == 1)
					playTrick(p4,p1,p2,trump);
				else if(caller == 2)
					playTrick(p1,p2,p3,trump);
				else if(caller == 3)
					playTrick(p4,p2,p3,trump);	
				else
					playTrick(p4,p1,p3,trump);
			}	
			else
			{
				if(caller == 1)
					playTrick(p1,p2,p4,trump);
				else if(caller == 2)
					playTrick(p1,p2,p3,trump);
				else if(caller == 3)
					playTrick(p2,p3,p4,trump);	
				else
					playTrick(p1,p3,p4,trump);
			}
		}
	}
	
	public void goAlone()
	{
		System.out.println("would you like to go alone? (y = yes, n = no): ");
		yorn = reader.next().charAt(0);
		if(yorn == 'y' || yorn == 'Y')
			alone = true;
		else
			alone = false;
	}
	
	public void pickUp(Player p, Card c)
	{
		System.out.println("Pick a card to replace: ");
		printHand(p.getHand());
		n = reader.nextInt();
		p.setCard(n,c);
		organizeHand(p.getHand());
	}


	public Suits secondround(Suits dead)
	{
		if(dead==DIAMOND)
		{
			System.out.println("Would you like to call Trump? (h = Hearts, s = spades, c=clubs, n=no): ");
			cSuit = reader.next().charAt(0);
			if(cSuit == 'h' || cSuit == 'H')
			{
				goAlone();
				return HEART;
			}
			else if(cSuit == 's' || cSuit == 'S')
			{
				goAlone();
				return SPADE;
			}
			else if(cSuit == 'c' || cSuit == 'C')
			{
				goAlone();
				return CLUB;
			}
			else
			{
				return null;
			}		
		}
		
		else if(dead==HEART)
		{
			System.out.println("Would you like to call Trump? (s = spades, c=clubs, d=diamonds, n=no): ");
			cSuit = reader.next().charAt(0);
			if(cSuit == 's' || cSuit == 'S')
			{
				goAlone();
				return SPADE;
			}
			else if(cSuit == 'c' || cSuit == 'C')
			{
				goAlone();
				return CLUB;
			}
			else if(cSuit == 'd' || cSuit == 'D')
			{
				goAlone();
				return DIAMOND;
			}
			else
			{
				return null;
			}		
		}
		
		else if(dead==SPADE)
		{
			System.out.println("Would you like to call Trump? (h = Hearts, c=clubs, d=diamonds, n=no): ");
			cSuit = reader.next().charAt(0);
			if(cSuit == 'h' || cSuit == 'H')
			{
				goAlone();
				return HEART;
			}
			else if(cSuit == 'c' || cSuit == 'C')
			{
				goAlone();
				return CLUB;
			}
			else if(cSuit == 'd' || cSuit == 'D')
			{
				goAlone();
				return DIAMOND;
			}
			else
			{
				return null;
			}		
		}
		else
		{
			System.out.println("Would you like to call Trump? (h = Hearts, s = spades, d=diamonds, n=no): ");
			cSuit = reader.next().charAt(0);
			if(cSuit == 'h' || cSuit == 'H')
			{
				goAlone();
				return HEART;
			}
			else if(cSuit == 's' || cSuit == 'S')
			{
				goAlone();
				return SPADE;
			}
			else if(cSuit == 'd' || cSuit == 'D')
			{
				goAlone();
				return DIAMOND;
			}
			else
			{
				return null;
			}		
		}
	}
}