package common;


public interface GameInterface   {

	/**
	 *  Method used by the player to join the game
	 * @param player Call Back object
	 * @throws InterruptedException exception
	 * 
	 * @return the message
	 */
	public ConnectMessage join(PlayerInterface player) throws InterruptedException;

	
	/**
	 *  Method used by the player to leave the game
	 * @param name name of the player that wants to leave
	 *
	 */
	public void leave(String name);

	/**
	 *  Method used by the player to send an action to the game
	 * @param name name of the player
	 * @param act action of the player
	 * @param value value of the action
	 *
	 */
	public void playerAction(String name, Action act, int value);
	
	
	/**
	 *  Method used to check if the game is ready to start
	 *
	 */
	public void check();
	
}