package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import Logic.Game;
import Logic.Table;
import common.Card;
import common.PlayerInterface;

public class GameUI extends JPanel implements UiInterface{

	private static final long serialVersionUID = 1L;
	private BufferedImage cardsImage;
	private BufferedImage background;
	private BufferedImage table;
	private BufferedImage wood;
	private BufferedImage money;
	private BufferedImage play;
	private BufferedImage waiting;
	private BufferedImage dealerImage;
	private BufferedImage smalBlindImage;
	private BufferedImage bigBlindImage;
	private BufferedImage dealerAvatar;
	static Game game;
	private ArrayList<Integer> xValues ;
	private ArrayList<Integer> yValues ;
	private ArrayList<Integer> posCards; // dx1 - dy1 - dx2 - dy2
	private ArrayList<Integer> positionXOfMoney;
	private ArrayList<Integer> positionYOfMoney;
	private int diameter;
	private int cellWidth;
	private int cellHeight;
	private int x;
	private int y;
	private Boolean animating = false;
	private int potPosX;
	private int potPosY;
	private Boolean left = false;
	private Boolean up = false;
	private Timer timer;
	private int animationAmount;
	private boolean drawPlayerCards = false;
	private int screenHeight;
	private int screenWidth;
	private int state=0;

	public GameUI(Game g) {

		try {
			cardsImage = ImageIO.read(new File("cards.jpg"));
			background = ImageIO.read(new File("background.jpg"));
			table = ImageIO.read(new File("table.jpg"));
			wood = ImageIO.read(new File("wood.jpg"));
			money = ImageIO.read(new File("money.png"));
			play =  ImageIO.read(new File("play.jpg"));
			waiting = ImageIO.read(new File("waiting.jpg"));
			dealerImage = ImageIO.read(new File("dealer.png"));
			smalBlindImage = ImageIO.read(new File("smallblind.png"));
			bigBlindImage = ImageIO.read(new File("bigblind.png"));
			dealerAvatar = ImageIO.read(new File("dealeravatar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		game = g;
		game.setUi((UiInterface)this);
		xValues = new ArrayList<Integer>(Arrays.asList(1057, 1020, 800, 440, 240, 205));
		yValues = new ArrayList<Integer>(Arrays.asList(150, 410, 515, 510, 405, 150));
		positionXOfMoney = new ArrayList<Integer>(Arrays.asList(967, 965, 800, 490, 370, 330));
		positionYOfMoney = new ArrayList<Integer>(Arrays.asList(200, 385, 450, 435, 385, 215));
		cellWidth = cardsImage.getWidth()/13;
		cellHeight = cardsImage.getHeight()/5;
		initializePosCards();
		screenHeight = 768;
		screenWidth = 1366;
		potPosX = screenWidth/2 ;
		potPosY = screenHeight/2 - 50 ;
		diameter = 100;
	}


	public void drawText(Graphics g, String text, int x, int y, int size){
		Font f = g.getFont();
		g.setFont(new Font( "Serif", Font.BOLD, size));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
		g.setFont(f);
	}

	public void initializePosCards(){
		posCards = new ArrayList<Integer>();

		for (int i = 0 ; i< xValues.size(); i++) {
			for(int j = 0; j < 2; j++){
				int dx2 = xValues.get(i) - j * cellWidth;
				int dx1 = dx2 - cellWidth;
				int dy1 = yValues.get(i) ;
				int dy2 = yValues.get(i) + cellHeight;

				posCards.add(dx1);
				posCards.add(dy1);
				posCards.add(dx2);
				posCards.add(dy2);	
			}
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		ArrayList<PlayerInterface> players = game.getPlayers();
		
		switch (state) {
		case 0:
			g.drawImage(play, 0, 0, null);
			drawText(g, "Poker", 0, 100, 100);
			break;
		case 1:
			g.drawImage(waiting, 0, 0, null);
			drawText(g, "Waiting for players ...", 400, screenHeight/2 - 50, 60);
			
			
			for(int i = 0; i < players.size(); i++){
				if(players.get(i).getInGame()){
					drawPlayer(g,players.get(i),i,Color.GREEN);
				}
				else{
					drawPlayer(g,players.get(i),i,Color.RED);
				}
			}
			break; 
		case 2:
			
			if(!animating){
				g.drawImage(background, 0, 0, null);
				g.drawImage(dealerAvatar, screenWidth/2 + 90, 5, screenHeight/2 + 250, 90, 0, 0, dealerAvatar.getWidth(), dealerAvatar.getHeight(), null);
			}

			drawBackgroundImages((Graphics2D)g);

			
			
			int dy1 = screenHeight/5;
			int dy2 = dy1 + 125;
			Card[] cards = Table.getInstance().getTableContent();

			for(int i = 0; i < cards.length; i++ ){

				int dx1 =  i* cellWidth + i * 20 + screenWidth/3;
				int dx2 = dx1 + cellWidth ;

				ArrayList<Integer> pos = cards[i].postionOnImage(cellWidth, cellHeight);
				g.drawImage(cardsImage, dx1,dy1,dx2,dy2, pos.get(0), pos.get(1), pos.get(2),pos.get(3) , null);
			}

			drawMoneyPlayers(g);

			if(animating){
				g.setColor(Color.WHITE);
				g.drawImage(money, x, y, x + 40, y + 40, 0, 0, money.getWidth(), money.getHeight(), null);
				g.drawString("" + animationAmount, x, y);
			}else{	
				if(!drawPlayerCards ){
					for(int i = 0; i < players.size(); i++){
						if(i == game.getcurrentPlayerInd()){
							drawPlayer(g,players.get(i),i,Color.GREEN);
						}
						else if(players.get(i).getInGame()){
							drawPlayer(g,players.get(i),i,Color.RED);
						}else
							drawPlayer(g,players.get(i),i,Color.DARK_GRAY);
					}
				}else{
					showPlayerCards(g,game.getPlayers());
					String rank="";
					
					StringBuilder s=new StringBuilder();
					
					for(int i = 0; i < players.size(); i++){
						if(players.get(i).getWin()){
							rank=players.get(i).getHandRank().toString();
							drawPlayer(g,players.get(i),i,Color.BLUE);
							s.append(players.get(i).getName());
							s.append("   ");
						}else
							drawPlayer(g,players.get(i),i,Color.BLACK);
					}
					
					int size = 18;
					drawText(g, rank, screenWidth/2 - (rank.length()/2) * size/2, screenHeight/2 + 10 ,size);
					drawText(g, s.toString(), screenWidth/2 - (rank.length()/2) * size/2, screenHeight/2 + 40 ,size);
				}
			}

			int pot=game.getPot();
			g.drawImage(money, potPosX, potPosY, potPosX + 40, potPosY + 40, 0, 0, money.getWidth(), money.getHeight(), null);
			g.setColor(Color.WHITE);
			
			
			g.drawString("" + pot, potPosX, potPosY);

			break;
		}
	}


	public void drawMoneyPlayers(Graphics g){
		for(int i = 0; i < game.getPlayers().size(); i++){
				g.setColor(Color.WHITE);
				g.drawImage(money, positionXOfMoney.get(i), positionYOfMoney.get(i), positionXOfMoney.get(i) + 40, positionYOfMoney.get(i) + 40, 0, 0, money.getWidth(), money.getHeight(), null);
		}
	}


	public void drawPlayer(Graphics g,PlayerInterface player, int id, Color c){
		g.setColor(c);
		g.fillOval(xValues.get(id), yValues.get(id), diameter, diameter);
		g.setColor(Color.WHITE);
		g.drawString(player.getName(), xValues.get(id) + 20, yValues.get(id) + 30);
		g.drawString("Money: " + player.getMoney(), xValues.get(id) + 20, yValues.get(id) + 46);
		g.drawString("Bet: " + player.getCurrentBet(), xValues.get(id) + 20, yValues.get(id) + 62);
		
		int dealer = game.getDealerInd();
		int numPlayers = game.getPlayers().size();
		
		if(dealer==id)
			g.drawImage(dealerImage, xValues.get(id)+20, yValues.get(id)+72,  xValues.get(id)+20 + 30, yValues.get(id)+72 + 30, 0, 0, dealerImage.getWidth(), dealerImage.getHeight(), null);
		else if(((dealer + 1)%numPlayers) == id)
				g.drawImage(smalBlindImage, xValues.get(id)+55, yValues.get(id)+72,  xValues.get(id)+55 + 30, yValues.get(id)+72 + 30, 0, 0, smalBlindImage.getWidth(), smalBlindImage.getHeight(), null);
		
		if(((dealer + 2)%numPlayers) == id)
			g.drawImage(bigBlindImage, xValues.get(id)+55, yValues.get(id)+72,  xValues.get(id)+55 + 30, yValues.get(id)+72 + 30, 0, 0, bigBlindImage.getWidth(), bigBlindImage.getHeight(), null);
	}

	public void drawAllPlayersInRed(Graphics g, ArrayList<PlayerInterface> players){
		for(int i = 0; i < players.size(); i++){
			drawPlayer(g, players.get(i), i, Color.RED);
		}
		repaint();
	}

	private void showPlayerCards(Graphics g, ArrayList<PlayerInterface> players){
		for(int i = 0; i < players.size(); i++){
			ArrayList<Card> hand = players.get(i).getHand();
			for(int j = 0; j < hand.size(); j++){	
				ArrayList<Integer> position = hand.get(j).postionOnImage(cellWidth, cellHeight);
				g.drawImage(cardsImage, posCards.get(0 + i*8 + j* 4),posCards.get(1 + i*8 + j* 4),posCards.get(2 + i*8 + j* 4),posCards.get(3 +i*8 + j* 4), position.get(0), position.get(1), position.get(2),position.get(3), null);
			}
		}
	}

	@Override
	public void updateUI() {
		repaint();
	}

	@Override
	public void playAnimation(int id, int val) {
		
		if(val == 0)
			return;
		
		animating = true;
		x = positionXOfMoney.get(id);
		y = positionYOfMoney.get(id);
		int division = 20;
		int velY =(potPosY - y)/division;
		int velX = (potPosX - x)/division;

		animationAmount = val;
		
		if(velY > 0)
			up = true;
		else
			up = false;

		if(id < 3)
			left = false;
		else 
			left = true;

		int time=5;
		timer = new Timer(time ,new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//atualiza as variaveis
				if( ((left == false) && potPosX > x ) || ((left == true) && potPosX < x)){
					timer.stop();
					animating = false;
					repaint();
					return;
				}

				if(((up == false) && potPosY > y ) || ((up == true) && potPosY < y)){
					timer.stop();
					animating = false;
					repaint();
					return;
				}

				x += velX;
				y += velY;

				repaint();
			}
		});
		timer.start();
		
		try {
			Thread.sleep(time*division);
		} catch (Exception e) {
			
		}
		
		repaint();
	}


	public void drawBackgroundImages(Graphics2D g2d){
		int xOvalPosition = screenWidth/5;
		int yOvalPosition =  screenHeight/10 ;


		Shape oval = new Ellipse2D.Float(xOvalPosition, yOvalPosition , (xOvalPosition * 3), (yOvalPosition * 6));
		Rectangle2D tr = new Rectangle2D.Double(0, 0, wood.getWidth(), wood.getHeight());
		TexturePaint tp = new TexturePaint(wood, tr);
		g2d.setPaint(tp);
		g2d.fill(oval);

		Shape oval2 = new Ellipse2D.Float(xOvalPosition + 25, yOvalPosition + 25, (xOvalPosition * 3) - 50, (yOvalPosition * 6) - 50);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, table.getWidth(), table.getHeight());
		TexturePaint t = new TexturePaint(table, rec);
		g2d.setPaint(t);
		g2d.fill(oval2);
	}


	@Override
	public void drawPlayerCards(boolean flag) {
		drawPlayerCards = flag;
	}


	public void drawRectangle(Graphics g, int x, int y){
		int margem = 5;
		g.drawRect(x - margem, y - margem, x + cellWidth + margem, y + cellHeight + margem);
	}

	@Override
	public void gameState(int state) {
		this.state = state;
		repaint();
	}

}
