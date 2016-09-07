package common;

import java.util.ArrayList;

public interface PlayerInterface {


	/**
	 *  Method used by the server to notify the player
	 * @param n type of the notification
	 * @param v depends on the notification type, could be the current max bet value or the pot value
	 */
	public void notify(NotificationType n,int v);

	/**
	 *  raises the bet of the player 
	 * @param val values of the new bet
	 */
	public void raiseBet(int val);

	/**
	 *  reset´s the player variables
	 * 
	 */
	public void reset();

	/**
	 *  @return the name of the player 
	 * 
	 */
	public String getName() ;
	/**
	 *  sets the name of the player 
	 * @param val new name
	 */
	public void setName(String val) ;

	/**
	 *  @return the current bet
	 * 
	 */
	public int getCurrentBet() ;
	/**
	 *  @param val value to set the current bet of the player
	 * 
	 */
	public void setCurrentBet(int val) ;
	
	
	/**
	 *  @return the money of the player 
	 * 
	 */
	public int getMoney() ;
	/**
	 *  @param val value to set the money of the player
	 * 
	 */
	public void setMoney(int val) ;

	/**
	 *  @return if the player is playing
	 * 
	 */
	public boolean getInGame() ;
	/**
	 *  @param val value to set the in game variable
	 * 
	 */
	public void setIngame(boolean val) ;

	/**
	 *  @return cards of the player
	 * 
	 */
	public ArrayList<Card> getHand() ;
	/**
	 *  @param c value to set the hand of the player
	 * 
	 */
	public void setHand(ArrayList<Card> c) ;

	/**
	 *  @return hand rank of the player
	 * 
	 */
	public PokerHand getHandRank() ;
	/**
	 *  @param val value to set the hand rank of the player
	 * 
	 */
	public void setHandRank(PokerHand val) ;
	
	
	/**
	 *  @return the cards that form a poker hand 
	 * 
	 */
	public ArrayList<Card> getCardsOfMove();
	/**
	 *  @param cards value to set the cards of move variable
	 * 
	 */
	public void setCardsOfMove(ArrayList<Card> cards);

	/**
	 *  @return the best fiver cards hand of the player
	 * 
	 */
	public ArrayList<Card> getbestFiveCards();
	/**
	 *  @param cards value to set the best five cards variable
	 * 
	 */
	public void setBestFiveCards(ArrayList<Card> cards);

	/**
	 *  @return if the player is all in
	 * 
	 */
	public boolean getAllIn();
	/**
	 *  @param val value to set the all in variable
	 * 
	 */
	public void setAllIn(boolean val);

	/**
	 *  @return if the palyer won
	 * 
	 */
	public boolean getWin();
	/**
	 *  @param val value to set the win variable
	 * 
	 */
	public void setWin(boolean val);
}