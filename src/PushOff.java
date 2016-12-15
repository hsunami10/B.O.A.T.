/*
 *	Made by Michael Hsu and John Laboe
 *	Created 10/20/16
 * 	Title: Push Off
 * 	Description: Push Off is game... The objective? To stay on the table as long as you can
 */

import java.awt.*;
import java.text.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.applet.*;

public class PushOff extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	/*
	 * Potential names: B.O.A.T., Push Off
	 */
	
	// Modes of gameplay:
	// Classic - circle gets smaller
	// Shape Shift
	// Holes
	// Blackout
	// Extreme
	
	// Once one menu boolean is set to true, the others have to be false
	
	final int WIDTH = 900, HEIGHT = 800;
	Thread thread; //= new Thread(this);
	final int XPOS = 450, YPOS = 400;
	public int radiusTable; // make smaller if people don't die in a set time
	public int numOfPlayers;
	//public boolean mouseIn;
	
	DecimalFormat justNum = new DecimalFormat("0.00");
	int levelNum = 1;
	
	Font timerFont;
	
	boolean players2, players3, players4;
	
	// goToGamePlay goes to the game, but gives a 5 sec warning before the game actually starts
	boolean gameStarted, goToMainMenu, goToGameMode, goToPlayerMenu, goToGamePlay, goToControlMenu, goToInfoMenu, playAgain;
	// Allow the user to choose controls - false until they press on the button
	boolean p1UpChoose, p1DownChoose, p1LeftChoose, p1RightChoose, p2UpChoose, p2DownChoose, p2LeftChoose, p2RightChoose,
			p3UpChoose, p3DownChoose, p3LeftChoose, p3RightChoose, p4UpChoose, p4DownChoose, p4LeftChoose, p4RightChoose;
	int p1UpKey, p1DownKey, p1LeftKey, p1RightKey, p2UpKey, p2DownKey, p2LeftKey, p2RightKey, p3UpKey, p3DownKey, p3LeftKey, p3RightKey, p4UpKey, p4DownKey, p4LeftKey, p4RightKey; 
	
	Graphics gfx;
    Image img;
    
    PlayerBall p1;
    Player2Ball p2;
    Player3Ball p3;
    Player4Ball p4;
    
    int countTime = 0;
    double randomTime;
    
    ChooseNumPlayersMenu playerMenu;
    MainMenu mainMenu;
    InfoMenu infoMenu;
    ControlMenu controlMenu;
    
    CollisionSolver collide;
    
    int pseudoTimer = 0;
    
    double[] speedChange;
    
    public PushOff() {
    	
    }
    
	public void init() {
		timerFont = new Font("arial", Font.BOLD, 50);
		
		this.resize(WIDTH, HEIGHT);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		randomTime = Math.random()*200;
		radiusTable = 325;
		
		p1 = new PlayerBall(1, XPOS, YPOS, radiusTable);
		p2 = new Player2Ball(2, XPOS, YPOS, radiusTable);
		p3 = new Player3Ball(3, XPOS, YPOS, radiusTable);
		p4 = new Player4Ball(4, XPOS, YPOS, radiusTable);

		collide = new CollisionSolver();
		
		img = createImage(WIDTH,HEIGHT);
    	gfx = img.getGraphics();
    	
		playerMenu = new ChooseNumPlayersMenu();
		infoMenu = new InfoMenu();
		mainMenu = new MainMenu();
		controlMenu = new ControlMenu();
    	
    	// This starts this thread (the game)
    	thread = new Thread(this);
    	thread.start();
    	
    	goToMainMenu = true;
    /*	gameStarted = false;
    	goToGameMode = false;
    	goToPlayerMenu = false;
    	goToGamePlay = false;
    	goToControlMenu = false;
    	goToInfoMenu = false;
    	playAgain = false;*/
    /*	
    	if(goToMainMenu)
    		mainMenu = new MainMenu();
    	if(goToPlayerMenu)
    		playerMenu = new ChooseNumPlayersMenu();
    		*/
	}
	
	public void paint(Graphics g) {
		
		// Draw Main Menu once the applet has started
		if(goToMainMenu) {// && !gameStarted && !goToGameMode && !goToPlayerMenu && !goToGamePlay && !goToControlMenu) {
			/*gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
			playerMenu.draw(gfx);
			g.drawImage(img, 0, 0, this);*/
			gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
			mainMenu.draw(gfx);
			
		}
		
		if(goToPlayerMenu) {
			gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
			playerMenu.draw(gfx);
			//g.drawImage(img, 0, 0, this);
		}

		if(goToInfoMenu) {
			gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
			infoMenu.draw(gfx);
		}

		if(goToControlMenu){
			gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
			controlMenu.draw(gfx);
		}
		
		// Draw the game once it starts (after the number of players are chosen)
		if(gameStarted) {
			
			g.setColor(Color.white);
			g.setFont(timerFont);
			g.drawString(justNum.format((double)(797 + randomTime - countTime)/100), 400, 60);
			g.drawString("Level", 10, 50);

			gfx.setColor(Color.BLACK);
			gfx.fillRect(0, 0, WIDTH, HEIGHT);
		
			gfx.setColor(Color.lightGray);
			countTime++;
			
			// Shrink every 8-10 seconds
			if(countTime % 1000 > 797 + randomTime) { 
				radiusTable -= (17 + Math.random()*6);
				randomTime = Math.random()*200;
				countTime = 0;
				levelNum++;
			}
			g.drawString(Integer.toString(levelNum), 175, 50);
			
			gfx.fillOval(XPOS - radiusTable, YPOS - radiusTable, radiusTable*2, radiusTable*2);
			
			// Choose number of players, and set others to dead
			if(players2) {
				p1.draw(gfx);
				p2.draw(gfx);
				p3.setX(0);
				p3.setY(0);
				p4.setX(0);
				p4.setY(0);
			}
			if(players3) {
				p1.draw(gfx);
				p2.draw(gfx);
				p3.draw(gfx);
				p4.setX(0);
				p4.setY(0);
			}
			if(players4) {
				p1.draw(gfx);
				p2.draw(gfx);
				p3.draw(gfx);
				p4.draw(gfx);
			}
		}
		g.drawImage(img, 0, 0, this);
	}
	
	
	public void update(Graphics g) {
		paint(g);
		if(gameStarted) {
			g.setColor(Color.white);
			g.setFont(timerFont);
			g.drawString(justNum.format((double)(797 + randomTime - countTime)/100), 400, 60);
			g.drawString("Level", 10,50);
			g.drawString(Integer.toString(levelNum), 175, 50);
		}
	}
	
	public void run() {
		while(true) {

			p1.move();
			p2.move();
			p3.move();
			p4.move();
		
			repaint();
			
			// Check whether the balls are off the tables
			// Player 1 off table
			if(Math.sqrt(Math.pow(p1.getX()-XPOS, 2) + Math.pow(p1.getY()-YPOS, 2)) > radiusTable + p1.getRadius()) {
				
				p1.isOffTable(true);
			}
			else
				p1.isOffTable(false);
			
			if(Math.sqrt(Math.pow(p1.getX()-XPOS, 2) + Math.pow(p1.getY()-YPOS, 2)) > radiusTable)
				p1.radiusOff(true);
			else
				p1.radiusOff(false);
			// Player 2 off table
			if(Math.sqrt(Math.pow(p2.getX()-XPOS, 2) + Math.pow(p2.getY()-YPOS, 2)) > radiusTable + p2.getRadius()) {
				
				p2.isOffTable(true);
			}
			else
				p2.isOffTable(false);
			
			if(Math.sqrt(Math.pow(p2.getX()-XPOS, 2) + Math.pow(p2.getY()-YPOS, 2)) > radiusTable)
				p2.radiusOff(true);
			else
				p2.radiusOff(false);
			// Player 3 off table
			if(Math.sqrt(Math.pow(p3.getX()-XPOS, 2) + Math.pow(p3.getY()-YPOS, 2)) > radiusTable + p3.getRadius()) {
				
				p3.isOffTable(true);
			}
			else
				p3.isOffTable(false);
			
			if(Math.sqrt(Math.pow(p3.getX()-XPOS, 2) + Math.pow(p3.getY()-YPOS, 2)) > radiusTable)
				p3.radiusOff(true);
			else
				p3.radiusOff(false);
			// Player 4 off table
			if(Math.sqrt(Math.pow(p4.getX()-XPOS, 2) + Math.pow(p4.getY()-YPOS, 2)) > radiusTable + p4.getRadius()) {
				
				p4.isOffTable(true);
			}
			else
				p4.isOffTable(false);
			
			if(Math.sqrt(Math.pow(p4.getX()-XPOS, 2) + Math.pow(p4.getY()-YPOS, 2)) > radiusTable)
				p4.radiusOff(true);
			else
				p4.radiusOff(false);
			
			// Add more knockback
			/*** Check collisions ***/
			// Player 1 and 2
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)) < p1.getRadius() + p2.getRadius())) {
				speedChange = collide.equalMassCollision(p1.getX(), p1.getY(), p1.getXVel(), p1.getYVel()
											, p2.getX(), p2.getY(), p2.getXVel(), p2.getYVel());
				
				p2.setXVel(speedChange[2]);
				p2.setYVel(speedChange[3]);
				
				p1.setXVel(speedChange[0]);
				p1.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			// Player 1 and 3
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) + Math.pow(p1.getY() - p3.getY(), 2)) < p1.getRadius() + p3.getRadius())) {
				speedChange = collide.equalMassCollision(p1.getX(), p1.getY(), p1.getXVel(), p1.getYVel()
											, p3.getX(), p3.getY(), p3.getXVel(), p3.getYVel());
				
				p3.setXVel(speedChange[2]);
				p3.setYVel(speedChange[3]);
				
				p1.setXVel(speedChange[0]);
				p1.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			// Player 1 and 4
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p1.getX() - p4.getX(), 2) + Math.pow(p1.getY() - p4.getY(), 2)) < p1.getRadius() + p4.getRadius())) {
				speedChange = collide.equalMassCollision(p1.getX(), p1.getY(), p1.getXVel(), p1.getYVel()
											, p4.getX(), p4.getY(), p4.getXVel(), p4.getYVel());
				
				p4.setXVel(speedChange[2]);
				p4.setYVel(speedChange[3]);
				
				p1.setXVel(speedChange[0]);
				p1.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			// Player 2 and 3
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p2.getX() - p3.getX(), 2) + Math.pow(p2.getY() - p3.getY(), 2)) < p2.getRadius() + p3.getRadius())) {
				speedChange = collide.equalMassCollision(p2.getX(), p2.getY(), p2.getXVel(), p2.getYVel()
											, p3.getX(), p3.getY(), p3.getXVel(), p3.getYVel());
				
				p3.setXVel(speedChange[2]);
				p3.setYVel(speedChange[3]);
				
				p2.setXVel(speedChange[0]);
				p2.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			// Player 2 and 4
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p2.getX() - p4.getX(), 2) + Math.pow(p2.getY() - p4.getY(), 2)) < p2.getRadius() + p4.getRadius())) {
				speedChange = collide.equalMassCollision(p2.getX(), p2.getY(), p2.getXVel(), p2.getYVel()
											, p4.getX(), p4.getY(), p4.getXVel(), p4.getYVel());
				
				p4.setXVel(speedChange[2]);
				p4.setYVel(speedChange[3]);
				
				p2.setXVel(speedChange[0]);
				p2.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			// Player 3 and 4
			if (pseudoTimer == 0 && (Math.sqrt(Math.pow(p3.getX() - p4.getX(), 2) + Math.pow(p3.getY() - p4.getY(), 2)) < p3.getRadius() + p4.getRadius())) {
				speedChange = collide.equalMassCollision(p3.getX(), p3.getY(), p3.getXVel(), p3.getYVel()
											, p4.getX(), p4.getY(), p4.getXVel(), p4.getYVel());
				
				p4.setXVel(speedChange[2]);
				p4.setYVel(speedChange[3]);
				
				p3.setXVel(speedChange[0]);
				p3.setYVel(speedChange[1]);
				pseudoTimer = 5;
			}
			if(pseudoTimer > 0)
				pseudoTimer--;
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*** Controls for players ***/
	// Player 1: WASD
	// Player 2: up down left right
	// Player 3: UHJK
	// Player 4: 8456
	public void keyPressed(KeyEvent e) {
		if(gameStarted) {
			// Player 1
			if(e.getKeyCode() == KeyEvent.VK_W) {
				p1.setDirectionYMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				p1.setDirectionYPlus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				p1.setDirectionXMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				p1.setDirectionXPlus(true);
			}
			// Player 2
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				p2.setDirectionYMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				p2.setDirectionYPlus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				p2.setDirectionXMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				p2.setDirectionXPlus(true);
			}
			// Player 3
			if(e.getKeyCode() == KeyEvent.VK_U) {
				p3.setDirectionYMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_J) {
				p3.setDirectionYPlus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_H) {
				p3.setDirectionXMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_K) {
				p3.setDirectionXPlus(true);
			}
			// Player 4
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				p4.setDirectionYMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				p4.setDirectionYPlus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				p4.setDirectionXMinus(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				p4.setDirectionXPlus(true);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(gameStarted) {
			// Player 1
			if(e.getKeyCode() == KeyEvent.VK_W) {
				p1.setDirectionYMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				p1.setDirectionYPlus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				p1.setDirectionXMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				p1.setDirectionXPlus(false);
			}
			// Player 2
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				p2.setDirectionYMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				p2.setDirectionYPlus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				p2.setDirectionXMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				p2.setDirectionXPlus(false);
			}
			// Player 3
			if(e.getKeyCode() == KeyEvent.VK_U) {
				p3.setDirectionYMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_J) {
				p3.setDirectionYPlus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_H) {
				p3.setDirectionXMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_K) {
				p3.setDirectionXPlus(false);
			}
			// Player 4
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				p4.setDirectionYMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				p4.setDirectionYPlus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				p4.setDirectionXMinus(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				p4.setDirectionXPlus(false);
			}
		}
	}

	public void keyTyped(KeyEvent e){ }

	// MouseListener
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		// else if forces the user to click another time
		// Main menu
		if (goToControlMenu) {
			if(my > 625 && my < 625 + mainMenu.HEIGHT_RECT) {
				goToPlayerMenu = false;
				goToMainMenu = true;
				gameStarted = false;
				goToGameMode = false;
				goToGamePlay = false;
				goToControlMenu = false;
				goToInfoMenu = false;
				playAgain = false;
			}
		}

		if (goToInfoMenu)
		{
			if(my > 625 && my < 625 + mainMenu.HEIGHT_RECT) {
				goToPlayerMenu = false;
				goToMainMenu = true;
				gameStarted = false;
				goToGameMode = false;
				goToGamePlay = false;
				goToControlMenu = false;
				goToInfoMenu = false;
				playAgain = false;
			}
		}
		if(goToMainMenu) {
			
			if(mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 && mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT) {
				// Play button
				if(my > 250 && my < 250 + mainMenu.HEIGHT_RECT) {
					//goToGameMode = true;

					goToPlayerMenu = true;
					goToMainMenu = false;
					gameStarted = false;
					goToGameMode = false;
					goToGamePlay = false;
					goToControlMenu = false;
					goToInfoMenu = false;
					playAgain = false;
				}
				// Controls button
				if(my > 375 && my < 375 + mainMenu.HEIGHT_RECT) {
					// control menu is true here 
					goToPlayerMenu = false;
					goToMainMenu = false;
					gameStarted = false;
					goToGameMode = false;
					goToGamePlay = false;
					goToControlMenu = true;
					goToInfoMenu = false;
					playAgain = false;
				}
				// Info button
				if(my > 500 && my < 500 + mainMenu.HEIGHT_RECT) {
					goToPlayerMenu = false;
					goToMainMenu = false;
					gameStarted = false;
					goToGameMode = false;
					goToGamePlay = false;
					goToControlMenu = false;
					goToInfoMenu = true;
					playAgain = false;
				}
				// Exit button
				if(my > 625 && my < 625 + mainMenu.HEIGHT_RECT) {
					System.exit(1);
				}
			}
		}
		
		// Player menu
		else if(goToPlayerMenu) {

			if(mx > 350 && mx < 550) {
				
				// 2 players
				if(my > 250 && my < 310) {
					// change gameStarted to goToGamePlay later
					gameStarted = true;
					players2 = true;
				}
				// 3 players
				if(my > 450 && my < 510) {
					gameStarted = true;
					players3 = true;
				}
				// 4 players
				if(my > 650 && my < 710) {
					gameStarted = true;
					players4 = true;
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	// MouseMotionListener
	public void mouseDragged(MouseEvent arg0) {
		
	}
	
	// Change color of text when hovered over (playerMenu)
	public void mouseMoved(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(goToControlMenu){
			if(mx > WIDTH/2 - controlMenu.WIDTH_RECT/2 && mx < WIDTH/2 - controlMenu.WIDTH_RECT/2 + controlMenu.WIDTH_RECT) {
				if(my > 625 && my < 625 + controlMenu.HEIGHT_RECT)
					controlMenu.mouseInBack = true;
			}
			// not in bounds 
			if(mx < WIDTH/2 - controlMenu.WIDTH_RECT/2 || mx > WIDTH/2 - controlMenu.WIDTH_RECT/2 + controlMenu.WIDTH_RECT)
				controlMenu.mouseInBack = false;
			if(my < 625 || my > 625 + controlMenu.HEIGHT_RECT)
				controlMenu.mouseInBack = false;
		}

		if(goToInfoMenu){
			if(mx > WIDTH/2 - infoMenu.WIDTH_RECT/2 && mx < WIDTH/2 - infoMenu.WIDTH_RECT/2 + infoMenu.WIDTH_RECT) {
				if(my > 625 && my < 625 + infoMenu.HEIGHT_RECT)
					infoMenu.mouseInBack = true;
			}
			// not in bounds 
			if(mx < WIDTH/2 - infoMenu.WIDTH_RECT/2 || mx > WIDTH/2 - infoMenu.WIDTH_RECT/2 + infoMenu.WIDTH_RECT)
				infoMenu.mouseInBack = false;
			if(my < 625 || my > 625 + infoMenu.HEIGHT_RECT)
				infoMenu.mouseInBack = false;
		}

		// Main menu
		if(goToMainMenu) {
			// In bounds
			// Play button
			if(mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 && mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT) {
				if(my > 250 && my < 250 + mainMenu.HEIGHT_RECT)
					mainMenu.mouseInPlay = true;
			}
			// Controls button
			if(mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 && mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT) {
				if(my > 375 && my < 375 + mainMenu.HEIGHT_RECT)
					mainMenu.mouseInControl = true;
			}
			// Info button
			if(mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 && mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT) {
				if(my > 500 && my < 500 + mainMenu.HEIGHT_RECT)
					mainMenu.mouseInInfo = true;
			}
			// Exit button
			if(mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 && mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT) {
				if(my > 625 && my < 625 + mainMenu.HEIGHT_RECT)
					mainMenu.mouseInExit = true;
			}
			
			// Not in bounds
			// Play button
			if(mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 || mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT)
				mainMenu.mouseInPlay = false;
			if(my < 250 || my > 250 + mainMenu.HEIGHT_RECT)
				mainMenu.mouseInPlay = false;
			// Controls button
			if(mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 || mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT)
				mainMenu.mouseInControl = false;
			if(my < 375 || my > 375 + mainMenu.HEIGHT_RECT)
				mainMenu.mouseInControl = false;
			// Info button
			if(mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 || mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT)
				mainMenu.mouseInInfo = false;
			if(my < 500 || my > 500 + mainMenu.HEIGHT_RECT)
				mainMenu.mouseInInfo = false;
			// Exit button
			if(mx < WIDTH/2 - mainMenu.WIDTH_RECT/2 || mx > WIDTH/2 - mainMenu.WIDTH_RECT/2 + mainMenu.WIDTH_RECT)
				mainMenu.mouseInExit = false;
			if(my < 625 || my > 625 + mainMenu.HEIGHT_RECT)
				mainMenu.mouseInExit = false;
		}
		
		// Player menu
		if(goToPlayerMenu) {
			// If in bounds of button
			// 2 players button
			if(mx > 350 && mx < 550)
				if(my > 250 && my < 310) {
					playerMenu.mouseIn2 = true;
				}
			// 3 players button
			if(mx > 350 && mx < 550)
				if(my > 450 && my < 510) {
					playerMenu.mouseIn3 = true;
				}
			// 4 players button
			if(mx > 350 && mx < 550)
				if(my > 650 && my < 710) {
					playerMenu.mouseIn4 = true;
				}
		
			// If not in bounds of button
			// 2 players button
			if(mx < 350 || mx > 550)
				playerMenu.mouseIn2 = false;
			if(my < 250 || my > 310)
				playerMenu.mouseIn2 = false;
			// 3 players button
			if(mx < 350 || mx > 550)
				playerMenu.mouseIn3 = false;
			if(my < 450 || my > 510)
				playerMenu.mouseIn3 = false;
			// 4 players button
			if(mx < 350 || mx > 550)
				playerMenu.mouseIn4 = false;
			if(my < 650 || my > 710)
				playerMenu.mouseIn4 = false;
		}
		
		// Info Menu
		

	}
	

}

