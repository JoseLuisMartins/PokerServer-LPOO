package network;


import common.GameInterface;
import lipermi.handler.CallHandler;
import lipermi.net.Server;

public class ServerHandler {
	
	/**
	 * starts the server
	 * 
	 * @param game the object binded to the server
	 * 
	 */
	public static void initServer(GameInterface game){
		try {				
			CallHandler callHandler=new CallHandler();
			
			callHandler.registerGlobal(GameInterface.class, game);
			
			Server server= new Server();
			int thePortIWantToBind = 4455;
			server.bind(thePortIWantToBind, callHandler);
			
			System.out.println("server running!");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
