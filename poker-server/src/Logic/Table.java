package Logic;

import common.Card;
import common.Rank;
import common.Suit;

public class Table {
 
	private Card[] tableContent = new Card[5];
	private Card noCard=new Card(Suit.NONE,Rank.NONE);
	static Table table=new Table();
	

	private Table(){
		resetTable();
	} 
	 
	/**
	 * 
	 * @return the table instance
	 * 
	 */
	public static Table getInstance(){
		return table;
	}
	
	/**
	 * set's the a card on the table
	 * 
	 * @param ind index of the card to be set
	 * @param c card to be set
	 * 
	 */
	public void setCard(int ind,Card c){
		tableContent[ind]=c;
	}
	
	/**
	 * Reset´s the table content
	 * 
	 */
	public void resetTable(){
		for (int i = 0; i < tableContent.length; i++) {
			tableContent[i]= noCard;
		}
	}
	
	
	/**
	 * 
	 * @return table content
	 * 
	 */
	public Card[] getTableContent(){
		return tableContent;
	}
	
	@Override
	public String toString() {
		return (tableContent[0] + "|" + tableContent[1] + "|"
				+ tableContent[2] + "|" + tableContent[3] + "|" 
				+ tableContent[4] );
	}
	
}
