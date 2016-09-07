package Logic;


/*
 * A ter em conta na decisao do vencedor (so contam as 5 cartas que fazem a melhor mao):
 * 	- se ambos os jogadores tiverem flush, ganha o que tiver o maior flush
 *  - se ambos os jogadores tiverem dois pares entao, primeira verifica - se o maior par de cada jogador,
 *  em caso de empate passa se ao segundo par. Caso o empate persista, tem de ver qual e a 5ª carta mais alta
 *  - 
 */


import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import common.Card;
import common.PokerHand;
import common.Rank;
import common.Suit;
 

public class HandAnalyzer {

	private static ArrayList<Card> bestFiveCards ;
	private static ArrayList<Card> cardsOfTheMove = new ArrayList<Card>();
	
	
	private static Card getHigherCard(ArrayList<Card> allCards){
		Collections.sort(allCards);
		int size = allCards.size()-1;
		return allCards.get(size);
	}
	
	/**
	 *  calculates the five best cards considering  allcards of the table and the player hand the cards tha form a poker hand
	 * @param allCards cards of the table and the player hand
	 * @param cardsOnMove the cards that form a poker hand 
	 * 
	 * @return the five best cards 
	 */
	public static ArrayList<Card> getBestCards(ArrayList<Card> allCards, ArrayList<Card> cardsOnMove){
		
		ArrayList<Card> aux = new ArrayList<Card>();
		aux.addAll(allCards);
		
		// caso de a jogada ser constituida por 5 cartas
		if(cardsOnMove.size() == 5){
			return cardsOnMove;
		}
		//caso nao seja
		bestFiveCards = new ArrayList<Card>();
		
		bestFiveCards.addAll(cardsOnMove);
		
		// fico apenas com aquelas que ainda nao estao nas 5 melhores
		aux.removeAll(cardsOnMove);
		Collections.sort(aux);
		int i = aux.size() - 1 ;

		while(bestFiveCards.size() < 5){
			bestFiveCards.add(aux.get(i));
			i--;	
		}
		Collections.sort(bestFiveCards);
		return bestFiveCards;		
	}
	 
	public static void setBestCards(ArrayList<Card> bestCards){
		bestFiveCards  = bestCards;
	}
	
	public static ArrayList<Card> getCardsOfTheMove(){
		Collections.sort(cardsOfTheMove);
		return cardsOfTheMove;		
	}
	
	public static void setCardsOfTheMove(ArrayList<Card> best){
		cardsOfTheMove = best;
	}
	
	/**
	 *  Analyzes the cards, and returns the corresponding Poker Hand
	 *  @param cards to analyze
	 *  @return The Poker Hand corresponding to the cards passed by argument
	 */
	public static PokerHand getPokerHand(ArrayList<Card> cards){

		if(isRoyalFlush(cards))
			return PokerHand.ROYAL_FLUSH;
		else if(isStraghtFlush(cards).size()== 5)
			return PokerHand.STRAIGHT_FLUSH;
		else if(isFourOfAKind(cards))
			return PokerHand.FOUR_OF_A_KIND;
		else if(isFullHouse(cards))
			return PokerHand.FULL_HOUSE;
		else if(isFlush(cards))
			return PokerHand.FLUSH;
		else if(isStraight(cards).size() == 5)
			return PokerHand.STRAIGHT;
		else if(isThreeOfAKind(cards))
			return PokerHand.THREE_OF_A_KIND;
		else if(isAPair(cards).size()!= 0){
			if(isAPair(cards).size() == 2)
				return PokerHand.ONE_PAIR;
			else if(isAPair(cards).size() == 4)
				return PokerHand.TWO_PAIRS;
		}
		
		ArrayList<Card> d = new ArrayList<Card>();
		d.add(getHigherCard(cards));
		setCardsOfTheMove(d);
		return PokerHand.HIGH_CARD;
	}

	private static ArrayList<Card> isAPair(ArrayList<Card> cards){

		ArrayList<Card> result = new ArrayList<Card>();
		Rank aux = Rank.NONE;

		for (int i = cards.size() - 1; i > -1; i--) {
			for (int j = i ; j  > -1; j--) {
				if(cards.get(i).getRank() == cards.get(j).getRank() && cards.get(i).getRank()!= aux)
					if(cards.get(i).getSuit() != cards.get(j).getSuit()){
						aux = cards.get(i).getRank();
						result.add(cards.get(i));
						result.add(cards.get(j));
					}
			}
			
			if(result.size() == 4){
				break;
			}
		}
	
		setCardsOfTheMove(result);
		
		return result;
	}

	private static boolean isThreeOfAKind(ArrayList<Card> cards){

		ArrayList<Card> d = isAPair(cards);

		if(d.size() == 0)
			return false;
		else{
			for (Card card : d) {
				if(getSameRank(cards,card.getRank()).size() == 3){
					setCardsOfTheMove(getSameRank(cards,card.getRank()));
					return true;
				}
			}
		}

		return false;
	}

	private static ArrayList<Card> isStraight(ArrayList<Card> cards){
		ArrayList<Card> aux = new ArrayList<Card>();

		Collections.sort(cards);
		int counter = 1;
		ArrayList<Card> pairs = getPairsToBeIgnored(cards);

		for (int i = cards.size() - 1; i > 0; i--) {
			if(pairs.contains(cards.get(i))== true){
				continue;
			}
			else if(cards.get(i).getRank().getValue() == (cards.get(i-1).getRank().getValue() + 1 )){
				aux.add(cards.get(i));
			}
			else {
				aux.removeAll(aux);
				counter = 1;
			}
			
			if(aux.size() == 4){
				int j;
				for (j = i - 1; j >= 0; j--) {
					if(pairs.contains(cards.get(j))== true){
						counter++;
						continue;
					}
					else if(cards.get(j).getRank().getValue() + 1  == cards.get(j+counter).getRank().getValue() ){
						aux.add(cards.get(j));
						break;
					}
					else {
						aux.removeAll(aux);
						break;
					}
				}
				setCardsOfTheMove(aux);
				return aux;
			}
			
		}
		
		aux.removeAll(aux);
		return aux;
	}

	private static boolean isFlush(ArrayList<Card> cards){

		if(getOfSameSuit(cards, Suit.DIAMONDS).size() == 5){
			setCardsOfTheMove(getOfSameSuit(cards, Suit.DIAMONDS));
			return true;
		}
		else if(getOfSameSuit(cards, Suit.CLUBS).size() == 5){
			setCardsOfTheMove(getOfSameSuit(cards, Suit.CLUBS));
			return true;
		}
		else if(getOfSameSuit(cards, Suit.HEARTS).size() == 5){
			setCardsOfTheMove(getOfSameSuit(cards, Suit.HEARTS));
			return true;
		}
		else if(getOfSameSuit(cards, Suit.SPADES).size() == 5){
			setCardsOfTheMove(getOfSameSuit(cards, Suit.SPADES));
			return true;
		}
		else return false;
	}


	private static boolean isFullHouse(ArrayList<Card> cards){

		ArrayList<Card> d = isAPair(cards);
		if(d.size() == 0 || d.size() == 2)
			return false;
		else {
			for (Card card : d) {
				if(getSameRank(cards,card.getRank()).size() == 3){
					ArrayList<Card> full = d;
					for(int i = 0; i < getSameRank(cards,card.getRank()).size() ; i++){
						if(!full.contains(getSameRank(cards,card.getRank()).get(i))){
							full.add(getSameRank(cards,card.getRank()).get(i));
						}
					}
					setCardsOfTheMove(full);
					return true;
				}
			}
		}

		return false;
	}


	private static boolean isFourOfAKind(ArrayList<Card> cards){

		ArrayList<Card> d = isAPair(cards);

		if(d.size() == 0)
			return false;
		else{
			for (Card card : d) {
				ArrayList<Card>bestHand = getSameRank(cards,card.getRank());
				if(bestHand.size() == 4){
					setCardsOfTheMove(bestHand);
					return true;
				}
			}
		}


		return false;
	}


	private static ArrayList<Card> getSameRank(ArrayList<Card> cards, Rank r){

		ArrayList<Card> d = new ArrayList<Card>();	
		for (Card card : cards) {
			if(card.getRank() == r)
				d.add(card);
		}

		return d;
	}

	private static ArrayList<Card> getOfSameSuit(ArrayList<Card> cards, Suit s){

		ArrayList<Card> d = new ArrayList<Card>();
		for (Card card : cards) {
			if(card.getSuit() == s)
				d.add(card);
		}

		return d;
	}

	private static ArrayList<Card> isStraghtFlush(ArrayList<Card> cards){
		ArrayList<Card> d = isStraight(cards);

		if(d.size()> 0){
			if(isFlush(d)){
				setCardsOfTheMove(d);
				return d;
			}
		}
		return new ArrayList<Card>();
	}
	
	private static boolean isRoyalFlush(ArrayList<Card> cards){
		
		ArrayList<Card> d = isStraghtFlush(cards);
		
		if(d.size() == 0)
			return false;
		else{
			for (Card card : d) {
				if(card.getRank()!= Rank.ACE && card.getRank()!= Rank.KING &&  card.getRank()!= Rank.JACK &&  card.getRank()!= Rank.QUEEN &&  card.getRank()!= Rank.TEN )
					return false;
			}
		}
		setCardsOfTheMove(d);
		return true;
	}

	private static ArrayList<Card> getPairsToBeIgnored(ArrayList<Card> cards){

		ArrayList<Card> aux = new ArrayList<Card>();

		for (int i = 0 ; i < cards.size(); i++) {
			if(getSameRank(cards, cards.get(i).getRank()).size() > 1){
				for (int j = i+1; j < cards.size()-1; j++) {
					if(cards.get(i).getRank() == cards.get(j).getRank()){
						if(getOfSameSuit(cards, cards.get(i).getSuit()).size() > getOfSameSuit(cards, cards.get(j).getSuit()).size()){
							aux.add(cards.get(j));
						}
						else {
							aux.add(cards.get(i));
							break;
						}
					}
				}
			}
		}
		return aux;
	}
	
	

	

}