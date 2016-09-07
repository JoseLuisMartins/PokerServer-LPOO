package Logic;



import java.util.ArrayList;
import java.util.HashMap;

import Gui.UiInterface;
import common.Action;

import common.ConnectMessage;
import common.GameInterface;
import common.NotificationType;
import common.PlayerInterface;


 
public class Game implements GameInterface {
 
	private HashMap<String, PlayerInterface> players = new HashMap<String, PlayerInterface>();
	private ArrayList<PlayerInterface> playersInOrder = new ArrayList<PlayerInterface>();
	private int pot;
	private int maxbet;
	private int roundIteration;
	private int dealerInd;
	private int playerWithMaxBetInd; 
	private boolean startingIteration;
	private int currentPlayerInd;
	private Round round;
	private int numberOfPlayersInGame;
	private int numberOfPlayersAllIn;
	private UiInterface ui = null;
 
	/**
	 * Constructor of the game
	 * 
	 */
	public Game()  {
		super();	
		pot=0;
		maxbet=0;
		dealerInd=0;
		playerWithMaxBetInd=0;
		startingIteration=true;
		roundIteration=0;
		currentPlayerInd=0;
		round=null;
		numberOfPlayersInGame=0;
		numberOfPlayersAllIn=0;
		playersInOrder = new ArrayList<PlayerInterface>();
	}
	
	/**
	 * set's the UI of the game
	 * 
	 * @param i the UI
	 * 
	 */
	public void setUi(UiInterface i){
		ui=i;
	}

	/**
	 * @return return the current round of the game
	 * 
	 */	
	public Round getRound(){
		return round;
	}

	@Override
	public  ConnectMessage  join(PlayerInterface player) throws InterruptedException    {
		
		
		
		if(round != null)
			return ConnectMessage.ROUND_IN_PROGRESS;
		if(players.size() == 6)
			return ConnectMessage.TABLE_FULL;
		if(players.containsKey(player.getName()))
			return ConnectMessage.NAME_CHOSEN;
		
		
		ui.drawPlayerCards(false);
		Table.getInstance().resetTable();
		
		System.out.println("joined by-->" + player.getName());
		players.put(player.getName(), player);
		playersInOrder.add(player);
		System.out.println("Number of players->" + playersInOrder.size());
		
		ui.gameState(1);
		
		return ConnectMessage.SUCESS;
	}

	@Override
	public void check() {
		ui.updateUI();
		boolean playersReady=true;
		for (int i = 0; i < playersInOrder.size(); i++) {
			if(!playersInOrder.get(i).getInGame()){
				playersReady=false;
				break;
			}
		}

		System.out.println("checked ");
		
		if(players.size() >= 2 && playersReady && round == null){
			roundIteration = 1;
			numberOfPlayersInGame=players.size();
			ui.gameState(2);
			nextIteration();
		}

	}


	@Override
	public void leave(String name)  {
		players.remove(name);

		numberOfPlayersInGame--;
		
		
		int ind=0;
		for (int i = 0; i < playersInOrder.size(); i++) {
			if(playersInOrder.get(i).getName().equals(name))
				ind=i;
		}
 
		
		playersInOrder.remove(ind);
		System.out.println("Number of players->" + playersInOrder.size() + "  map-> " + players.size());
		
		
		if(playerWithMaxBetInd > ind)
			playerWithMaxBetInd--;
		else if((playerWithMaxBetInd == ind) && (ind == (playersInOrder.size()-1)))
			playerWithMaxBetInd=0;
			
		if(currentPlayerInd > ind)
			currentPlayerInd--;
		else if(currentPlayerInd == ind && round != null){
			if(currentPlayerInd == 0)
				currentPlayerInd = (playersInOrder.size()-1);
			else
				currentPlayerInd--; 
					
			notifyNextPlayer();
		}
				

		dealerInd=0;
			
		if(round != null){
			round.setPlayers(playersInOrder);
		}
		
		ui.updateUI();
	}

	@Override
	public void playerAction(String name, Action act, int value)  {
		PlayerInterface player=players.get(name);
		int inc;
		
		switch (act) {
		case RAISE:
			maxbet=value;
			inc = value - player.getCurrentBet();
			pot += inc;
			ui.playAnimation(currentPlayerInd,inc);
			player.raiseBet(value);
			playerWithMaxBetInd=currentPlayerInd;
			if(player.getAllIn())
				numberOfPlayersAllIn++;
			break;
		case FOLD:
			numberOfPlayersInGame--;
			player.setIngame(false);
		
			
			break;
		case CALL_CHECK: 
			inc =  maxbet - player.getCurrentBet();
			pot += inc;
			ui.playAnimation(currentPlayerInd,inc);
			player.raiseBet(maxbet);
			if(player.getAllIn())
				numberOfPlayersAllIn++;
		
			
			break;
		case ALL_IN:
			inc = player.getMoney() + player.getCurrentBet();
			maxbet = player.getMoney() + player.getCurrentBet();
			pot += player.getMoney();
			ui.playAnimation(currentPlayerInd, inc);
			player.setAllIn(true);
			playerWithMaxBetInd=currentPlayerInd;
			numberOfPlayersAllIn++;
		
			
			break;
		default:
			break;
		}

		notifyNextPlayer();
	}


	private void notifyNextPlayer(){//notifica os jogadores todos para jogar até acabar a roundIteration actual


		currentPlayerInd = (++currentPlayerInd) % players.size();
		PlayerInterface player=playersInOrder.get(currentPlayerInd);
		
		
		
		boolean aux=false;
		//quando estão todos all in menos um jogador mas esse jogador já igualou a aposta, o jogo pode continuar até ao final
		//quando deram todos fold e só há um jogador em jogo
		if((numberOfPlayersInGame == 1) ||(((numberOfPlayersAllIn+1) == numberOfPlayersInGame) && (maxbet == player.getCurrentBet()) && (player.getAllIn() == false))){
			aux=true;
		}
		
		if((currentPlayerInd == playerWithMaxBetInd && !startingIteration) || (numberOfPlayersAllIn == numberOfPlayersInGame) || aux){
			roundIteration = (++roundIteration) % RoundState.values().length;
			System.out.println("numberOfPlayersAllIn-> " + numberOfPlayersAllIn + "numberOfPlayers" + numberOfPlayersInGame);
			nextIteration();
		}else if(!player.getInGame() || player.getAllIn() ){
			notifyNextPlayer();
		}
		else{ // se estiver em all-in não é preciso notificar 
			ui.updateUI();
			player.notify(NotificationType.YOURTURN,maxbet);
			System.out.println("notify-> " + player.getName() + "  maxbet->" + maxbet);
		}


		if(startingIteration)
			startingIteration=false;

		
	}

	private void nextIteration() {//responsavel por passar para a proxima estado da ronda 
		RoundState state=RoundState.values()[roundIteration];

		System.out.println(state.toString());
		switch (state) {
		case WAITING_FOR_PLAYERS:
			for (int i = 0; i < playersInOrder.size(); i++) {
				playersInOrder.get(i).notify(NotificationType.WAITING,0);
			}
			return;
		case PRE_FLOP:
			reset();
			ui.drawPlayerCards(false);
			startingIteration=false;
			round=new Round(playersInOrder);
			currentPlayerInd=(dealerInd +1)%players.size();
			PlayerInterface player=playersInOrder.get(currentPlayerInd);
			player.raiseBet(25);
			currentPlayerInd = ++currentPlayerInd % players.size();
			player=playersInOrder.get(currentPlayerInd);
			player.raiseBet(50);
			playerWithMaxBetInd=currentPlayerInd;
			pot=75;
			maxbet=50;
			round.preFlop();
			ui.updateUI();
			break;
		case FLOP:
			reset();
			round.flop();
			ui.updateUI();
			break;
		case TURN:
			reset();
			round.turn();
			ui.updateUI();
			break;
		case RIVER:
			reset();
			round.river();
			ui.updateUI();
			break;
		case GET_WINNERS:
			round.findWinner();
			ui.drawPlayerCards(true);
			ui.updateUI();
	
			//notify player's end of round
			int prize=pot/round.getNumWinners();	
			for (int i = 0; i < playersInOrder.size(); i++) {
				PlayerInterface p=playersInOrder.get(i);
				if(p.getWin())
					p.notify(NotificationType.YOUWIN,prize);
				else
					p.notify(NotificationType.YOULOSE,0);
			}
			
			round=null;
			roundIteration=0;
			dealerInd = (dealerInd + 1) % players.size();
			numberOfPlayersAllIn=0;
			return;
		}
		 
		notifyNextPlayer();
	}
 
	/**
	 * @return the current game pot
	 */
	public int getPot(){
		return pot;
	}

	/**
	 * @return the index of the current player palying
	 */
	public int getcurrentPlayerInd(){
		return currentPlayerInd;
	}
	/**
	 * @return the number of player in game
	 */
	public int getnumberOfPlayers(){
		return numberOfPlayersInGame;
	}
	
	/**
	 * @return the player's
	 */
	public ArrayList<PlayerInterface> getPlayers(){
		return playersInOrder;
	}
	
	/**
	 * @return the index of the dealer
	 */
	public int getDealerInd(){
		return dealerInd;
	}

	private void reset(){// faz reset das variaveis do jogo e dos players de modo a ser possivel começar uma nova ronda
		currentPlayerInd=dealerInd;
		playerWithMaxBetInd= ((dealerInd+1) % players.size());
		maxbet=0;
		startingIteration=true;

		for (int i = 0; i < playersInOrder.size(); i++) {
			if(playersInOrder.get(i).getInGame())
				playersInOrder.get(i).setCurrentBet(0);
		}
	}

	/**
	 * @param name name of the player
	 * @return a player with the name specified
	 */
	public PlayerInterface getPlayerByString(String name){
		return players.get(name);
	}

}
