package Logic;


import java.util.ArrayList;
import java.util.Collections;

import common.Card;
import common.NotificationType;
import common.PlayerInterface;
import common.PokerHand;

public class Round {

	private Deck deck;
	private ArrayList<PlayerInterface> players;
	private Table table= Table.getInstance(); 
	private int numWinners;
	
	/**
	 * Constructor of the round 
	 * 
	 * @param p players playing the round
	 * 
	 */
	public Round(ArrayList<PlayerInterface> p){
		deck=new Deck();
		
		players=p;
		numWinners=1;
	} 
	 
	
	/**
	 * Draws the cards to the players
	 * 
	 */
	public void preFlop() {//dar as cartas aos jogadores
		table.resetTable();		
		for (int i = 0; i < players.size(); i++) {
			ArrayList<Card> cards = new ArrayList<Card>();
			cards.add(deck.draw());
			cards.add(deck.draw());
			players.get(i).setHand(cards);
			players.get(i).notify(NotificationType.UPDATE, 0);
		}
	}

	/**
	 * Draws the 3 first cards to the table
	 * 
	 */
	public void flop(){
		table.setCard(0, deck.draw());
		table.setCard(1, deck.draw());
		table.setCard(2, deck.draw());
		System.out.println(table);
	}

	/**
	 * Draws the 4 card to the table
	 * 
	 */
	public void turn(){
		table.setCard(3, deck.draw());
		System.out.println(table);
	}
	
	/**
	 * Draws the 5 card to the table
	 * 
	 */
	public void river(){
		table.setCard(4, deck.draw());
		System.out.println(table);
	}


	/**
	 * calculates the hand rank for all the players
	 * 
	 */
public void setPlayersHandRank(){
		
		for (PlayerInterface player : players) {
			ArrayList<Card> cards = new  ArrayList<Card>();
			for (Card card : table.getTableContent()) {
				cards.add(card);
			}
			player.setWin(false);
			cards.addAll(player.getHand());

			player.setHandRank(HandAnalyzer.getPokerHand(cards)); 
			player.setCardsOfMove(HandAnalyzer.getCardsOfTheMove());
			ArrayList<Card> d = HandAnalyzer.getBestCards(cards, player.getCardsOfMove());
			player.setBestFiveCards(d);
		
		}
	}
	
/**
 * finds the winner's of the round
 * 
 */
	public void findWinner(){// calls all the Handanylizer and determines who is the player with the better hand
		
		setPlayersHandRank();
		
		
		
		int bestHand=players.get(0).getHandRank().getValue();;
		int bestPlayer=0;
		
		
		for (int i = 0; i < players.size(); i++) {//encontrar o primeiro jogador em jogo
			if(players.get(i).getInGame() == false)
				continue;
			else{
				bestHand = players.get(i).getHandRank().getValue();
				bestPlayer = i;
				break;
			}
		}
	
		int aux=bestPlayer+1;//começar a comparar com o proximo jogador que estiver em jogo
		
		
		for(int j = aux; j < players.size(); j++){
			
			if(players.get(j).getInGame() == false)
				continue;
			
			 int value = players.get(j).getHandRank().getValue();
			 // se for menor, quer dizer que  a mao é superior
			 if(value > bestHand){
				 bestHand = value;
				 bestPlayer = j;
			 }
			 else if (value == bestHand){
				 int bestPlayerFound;
				 ArrayList<Card> cardsOfMoveBestPlayer = players.get(bestPlayer).getCardsOfMove();
				 ArrayList<Card> cardsOfMovePlayer = players.get(j).getCardsOfMove();
				 Collections.sort(cardsOfMovePlayer);
				 Collections.sort(cardsOfMoveBestPlayer);
				 
				 bestPlayerFound = compareArraysOfPlayers(cardsOfMoveBestPlayer, cardsOfMovePlayer, bestPlayer, j);
				 if(bestPlayerFound == -1){
					 // segunda opcao
					 System.out.print("tem de se verificar pelas 5 melhores cartas");
					 ArrayList<Card> bestFiveCardsOfPlayer = players.get(bestPlayer).getbestFiveCards();
					 ArrayList<Card> bestFiveCards = players.get(j).getbestFiveCards();
					 Collections.sort(bestFiveCards);
					 Collections.sort(bestFiveCardsOfPlayer);
					 
					 bestPlayerFound = compareArraysOfPlayers(bestFiveCardsOfPlayer, bestFiveCards, bestPlayer, j);
					 if(bestPlayerFound == -1){
						 players.get(j).setWin(true);
						 players.get(bestPlayer).setWin(true);
						 numWinners=2;
						 return ; 
					 }
					
						 
					bestPlayer = bestPlayerFound;
					 bestHand = PokerHand.valueOf("" + players.get(bestPlayer).getHandRank()).ordinal();	
					 
				 }
				 else{
					 bestPlayer = bestPlayerFound;
					 bestHand = PokerHand.valueOf("" + players.get(bestPlayer).getHandRank()).ordinal();				
				 }
			 }
		}
		 
		players.get(bestPlayer).setWin(true);
		
	}
	
	  
	private int compareArraysOfPlayers(ArrayList<Card> cardsBestPlayer , ArrayList<Card> cardsPlayer, int bestPlayerId, int playerId){

		 for(int i = cardsBestPlayer.size() - 1; i > -1 ; i--){
			 if(cardsBestPlayer.get(i).getRank().getValue() != cardsPlayer.get(i).getRank().getValue()){
				 // a mao do atual jogador continua a ser a melhor
				 if(cardsBestPlayer.get(i).getRank().getValue() > cardsPlayer.get(i).getRank().getValue()){
					return bestPlayerId;
			
				 }
				 else{
					 // o atual jogador com a melhor mao muda
					 return playerId;
				 }
			 }
		 }
		 // quer dizer que nao foi encontrado um melhor jogador
		return -1;
	}

	
	/**
	 * @return number winners of the round
	 * 
	 */
	public	int getNumWinners() {
		return numWinners;
	}


	/**
	 * set's the players of the round
	 * 
	 * @param p new players of the round
	 * 
	 */
	public void setPlayers(ArrayList<PlayerInterface> p) {
		players=p;
	}

}
