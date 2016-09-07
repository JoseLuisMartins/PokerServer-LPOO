package Gui;


import java.awt.EventQueue;
import javax.swing.JFrame;


import Logic.Game;
import common.GameInterface;
import network.ServerHandler;


public class InterfaceServer{
	
	private JFrame frame;
	static Game game;
	private static GameUI drawn;
	  
	public static void main(String[] args) {
		game = new Game();
		drawn=new GameUI(game);
		
		ServerHandler.initServer((GameInterface)game);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceServer window = new InterfaceServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
  
	public InterfaceServer() {
		
		initialize();	
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(1366, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		drawn.setSize(1366, 768);
		frame.getContentPane().add(drawn);
	}
}
