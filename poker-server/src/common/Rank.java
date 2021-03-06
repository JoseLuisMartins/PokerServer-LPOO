package common;


public enum Rank {
	TWO(0),
	THREE(1),
	FOUR(2),
	FIVE(3),
	SIX(4),
	SEVEN(5),
	EIGHT(6),
	NINE(7),
	TEN(8),
	JACK(9),
	QUEEN(10),
	KING(11),
	ACE(12),
	NONE(13);
	
	private int value;
	
	private Rank(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
}