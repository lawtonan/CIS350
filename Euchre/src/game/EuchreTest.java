package game;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class EuchreTest {

	@Test
	public void testCreateDeck() {
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
	public void testPrintCards() {
		Euchre junit = new Euchre();
		ArrayList<Card> deck = junit.createDeck();

		assertNotNull(deck);

	}

	@Test
 	public void testGameStatus() {
		Euchre junit = new Euchre();
		junit.t1Point(1);

		assertFalse(junit.gameStatus());
	}

	@Test
	public void testShuffle() {
		Euchre junit = new Euchre();

		ArrayList<Card> deck = junit.createDeck();
		ArrayList<Card> deck2 = junit.createDeck();

		junit.shuffleTest(deck);

		assertNotSame(deck.toArray(), deck2.toArray());
	}

}
