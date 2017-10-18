package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class EuchreTest {

	@Test
	void testCreateDeck() {
		Euchre junit = new Euchre();
		ArrayList<Card> result = junit.createDeck();
		ArrayList<Card> test = new ArrayList<Card>();
		for (int i = 0; i <= 5; i++) {
			test.add(new Card(i, Suits.Diamonds));
			test.add(new Card(i, Suits.Hearts));
			test.add(new Card(i, Suits.Clubs));
			test.add(new Card(i, Suits.Spades));
		}
		test = junit.createDeck();

		assertArrayEquals(result.toArray(), test.toArray());

	}

	@Test
	void testPrintCards() {
		Euchre junit = new Euchre();
		ArrayList<Card> deck = junit.createDeck();

		assertNotNull(deck);

	}

	@Test
	void testGameStatus() {
		Euchre junit = new Euchre();
		junit.T1Point();

		assertFalse(junit.ameStatus());
	}

	@Test
	void testShuffle() {
		Euchre junit = new Euchre();

		ArrayList<Card> deck = junit.createDeck();
		ArrayList<Card> deck2 = junit.createDeck();

		junit.shuffleTest(deck);

		assertNotSame(deck.toArray(), deck2.toArray());
	}


}
