package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import Logic.HandAnalyzer;
import common.Card;
import common.PokerHand;
import common.Rank;
import common.Suit;

public class TestPokerHandAnaylizer {

	
	@Test
	public void testIsHigherCard() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		cards.add(new Card(Suit.HEARTS, Rank.TWO));
		cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
		cards.add(new Card(Suit.DIAMONDS, Rank.THREE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.HIGH_CARD, HandAnalyzer.getPokerHand(cards));

		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.DIAMONDS, Rank.ACE));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.DIAMONDS, Rank.ACE));
		expectBest.add(new Card(Suit.SPADES, Rank.QUEEN));
		expectBest.add(new Card(Suit.SPADES, Rank.KING));
		expectBest.add(new Card(Suit.CLUBS, Rank.FIVE));
		expectBest.add(new Card(Suit.CLUBS, Rank.FOUR));
		
		Collections.shuffle(expectBest);
		Collections.shuffle(expect);
		assertEquals(1, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	
	
	
	// par de damas
	@Test
	public void testOnePair() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		cards.add(new Card(Suit.HEARTS, Rank.TWO));
		cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.THREE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.ONE_PAIR, HandAnalyzer.getPokerHand(cards));

		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.QUEEN));
		expect.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.QUEEN));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		expectBest.add(new Card(Suit.SPADES, Rank.KING));
		expectBest.add(new Card(Suit.CLUBS, Rank.FIVE));
		expectBest.add(new Card(Suit.CLUBS, Rank.FOUR));
		
		Collections.shuffle(expectBest);
		Collections.shuffle(expect);
		

		assertEquals(2, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());		
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	//par de damas e de dois
	@Test
	public void testTwoPairs() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		cards.add(new Card(Suit.HEARTS, Rank.TWO));
		cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.TWO));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.TWO_PAIRS, HandAnalyzer.getPokerHand(cards));
	
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.QUEEN));
		expect.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		expect.add(new Card(Suit.CLUBS, Rank.TWO));
		expect.add(new Card(Suit.HEARTS, Rank.TWO));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.QUEEN));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		expectBest.add(new Card(Suit.CLUBS, Rank.TWO));
		expectBest.add(new Card(Suit.HEARTS, Rank.TWO));
		expectBest.add(new Card(Suit.SPADES, Rank.KING));
		
		Collections.shuffle(expectBest);
		Collections.shuffle(expect);
		
		assertEquals(4, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	
	}

	// trio de damas
	@Test
	public void testThreeOfAKind() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.TWO));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.THREE_OF_A_KIND, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.QUEEN));
		expect.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		expect.add(new Card(Suit.HEARTS, Rank.QUEEN));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.QUEEN));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
		expectBest.add(new Card(Suit.HEARTS, Rank.QUEEN));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		expectBest.add(new Card(Suit.SPADES, Rank.KING));
		
		
		assertEquals(3, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	@Test
	public void testIsStraight() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.TWO));
		cards.add(new Card(Suit.SPADES, Rank.SIX));
		cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
		cards.add(new Card(Suit.DIAMONDS, Rank.NINE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.STRAIGHT, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.SIX));
		expect.add(new Card(Suit.HEARTS, Rank.SEVEN));
		expect.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
		expect.add(new Card(Suit.DIAMONDS, Rank.NINE));
		expect.add(new Card(Suit.CLUBS, Rank.FIVE));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.SIX));
		expectBest.add(new Card(Suit.HEARTS, Rank.SEVEN));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.NINE));
		expectBest.add(new Card(Suit.CLUBS, Rank.FIVE));
		
		assertEquals(5, expect.size());
		assertEquals(5, expectBest.size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	@Test
	public void testIsFlush() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.SIX));
		cards.add(new Card(Suit.HEARTS, Rank.SEVEN));
		cards.add(new Card(Suit.SPADES, Rank.EIGHT));
		cards.add(new Card(Suit.SPADES, Rank.NINE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.FLUSH, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.FIVE));
		expect.add(new Card(Suit.SPADES, Rank.SIX));
		expect.add(new Card(Suit.SPADES, Rank.EIGHT));
		expect.add(new Card(Suit.SPADES, Rank.NINE));
		expect.add(new Card(Suit.SPADES, Rank.FOUR));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.FIVE));
		expectBest.add(new Card(Suit.SPADES, Rank.SIX));
		expectBest.add(new Card(Suit.SPADES, Rank.EIGHT));
		expectBest.add(new Card(Suit.SPADES, Rank.NINE));
		expectBest.add(new Card(Suit.SPADES, Rank.FOUR));
		
		assertEquals(5, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	//trio de 5's e par de 4's 
	@Test
	public void testIsFullHouse() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.SIX));
		cards.add(new Card(Suit.HEARTS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.EIGHT));
		cards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.FULL_HOUSE, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.FIVE));
		expect.add(new Card(Suit.HEARTS, Rank.FIVE));
		expect.add(new Card(Suit.SPADES, Rank.FOUR));
		expect.add(new Card(Suit.CLUBS, Rank.FIVE));
		expect.add(new Card(Suit.DIAMONDS, Rank.FOUR));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.FIVE));
		expectBest.add(new Card(Suit.HEARTS, Rank.FIVE));
		expectBest.add(new Card(Suit.SPADES, Rank.FOUR));
		expectBest.add(new Card(Suit.CLUBS, Rank.FIVE));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.FOUR));
		
		assertEquals(5, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	// poker de 5's
	@Test
	public void testIsFourOfAKind() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.SIX));
		cards.add(new Card(Suit.HEARTS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.EIGHT));
		cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.FOUR_OF_A_KIND, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.FIVE));
		expect.add(new Card(Suit.HEARTS, Rank.FIVE));
		expect.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		expect.add(new Card(Suit.CLUBS, Rank.FIVE));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.FIVE));
		expectBest.add(new Card(Suit.HEARTS, Rank.FIVE));
		expectBest.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		expectBest.add(new Card(Suit.CLUBS, Rank.FIVE));
		expectBest.add(new Card(Suit.SPADES, Rank.EIGHT));
		
		assertEquals(4, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	 

	@Test
	public void testIsStraightFlush() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.SIX));
		cards.add(new Card(Suit.SPADES, Rank.SEVEN));
		cards.add(new Card(Suit.SPADES, Rank.EIGHT));
		cards.add(new Card(Suit.SPADES, Rank.NINE));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.CLUBS, Rank.FOUR));
		Collections.shuffle(cards);
		assertEquals(PokerHand.STRAIGHT_FLUSH, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.FIVE));
		expect.add(new Card(Suit.SPADES, Rank.SIX));
		expect.add(new Card(Suit.SPADES, Rank.SEVEN));
		expect.add(new Card(Suit.SPADES, Rank.EIGHT));
		expect.add(new Card(Suit.SPADES, Rank.NINE));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.FIVE));
		expectBest.add(new Card(Suit.SPADES, Rank.SIX));
		expectBest.add(new Card(Suit.SPADES, Rank.SEVEN));
		expectBest.add(new Card(Suit.SPADES, Rank.EIGHT));
		expectBest.add(new Card(Suit.SPADES, Rank.NINE));
		
		assertEquals(5, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	@Test
	public void testIsRoyalFlush() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Suit.SPADES, Rank.ACE));
		cards.add(new Card(Suit.SPADES, Rank.JACK));
		cards.add(new Card(Suit.SPADES, Rank.TEN));
		cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
		cards.add(new Card(Suit.SPADES, Rank.QUEEN));
		cards.add(new Card(Suit.CLUBS, Rank.FIVE));
		cards.add(new Card(Suit.SPADES, Rank.KING));
		Collections.shuffle(cards);
		assertEquals(PokerHand.ROYAL_FLUSH, HandAnalyzer.getPokerHand(cards));
		
		ArrayList<Card> expect  = new ArrayList<Card>();
		expect.add(new Card(Suit.SPADES, Rank.ACE));
		expect.add(new Card(Suit.SPADES, Rank.JACK));
		expect.add(new Card(Suit.SPADES, Rank.KING));
		expect.add(new Card(Suit.SPADES, Rank.QUEEN));
		expect.add(new Card(Suit.SPADES, Rank.TEN));
		
		ArrayList<Card> expectBest  = new ArrayList<Card>();
		expectBest.add(new Card(Suit.SPADES, Rank.ACE));
		expectBest.add(new Card(Suit.SPADES, Rank.JACK));
		expectBest.add(new Card(Suit.SPADES, Rank.KING));
		expectBest.add(new Card(Suit.SPADES, Rank.QUEEN));
		expectBest.add(new Card(Suit.SPADES, Rank.TEN));
		
		assertEquals(5, HandAnalyzer.getCardsOfTheMove().size());
		assertEquals(5, HandAnalyzer.getBestCards(cards, expect).size());
		assertTrue(expect.containsAll(HandAnalyzer.getCardsOfTheMove()));
		assertTrue(expectBest.containsAll(HandAnalyzer.getBestCards(cards, expect)));
	}
	
	
}
