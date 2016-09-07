package Logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import common.Card;
import common.Rank;
import common.Suit;

public class Deck {
	private ArrayList<Card> cards;
	
	/**
	 * Constructor of the deck
	 */
	public Deck(){
		cards=new ArrayList<Card>();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				cards.add(new Card(Suit.values()[i],Rank.values()[j]));
			}
		}
		 
		Collections.shuffle(cards);
	}
	
	/**
	 *  Draws a card from the deck
	 * @return a card from the deck
	 */
	public Card draw(){
		return cards.remove(cards.size() - 1);
	}
	
}
