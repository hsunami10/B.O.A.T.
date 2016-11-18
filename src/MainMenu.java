/*
 * Created by: Michael Hsu
 * This is the main menu class
 * Includes all the properties of the main menu
 * This class is displayed first, once the applet runs
 * Includes a play button, controls button, options button, and exit button
 */

import java.awt.*;

public class MainMenu {
	
	final int WIDTH_RECT = 180, HEIGHT_RECT = 60;
	public Rectangle playButton, controlButton, infoButton, exitButton;
	PushOff push;
	
	boolean mouseInPlay, mouseInControl, mouseInInfo, mouseInExit;
	
	public MainMenu() {
		push = new PushOff();
		
		playButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 250, WIDTH_RECT, HEIGHT_RECT);
		controlButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 375, WIDTH_RECT, HEIGHT_RECT);
		infoButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 500, WIDTH_RECT, HEIGHT_RECT);
		exitButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 625, WIDTH_RECT, HEIGHT_RECT);
	}
	
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		// Title
		Font fntTitle = new Font("arial", Font.BOLD, 80);
		g.setFont(fntTitle);
		g.setColor(Color.white);
		//g.drawString("PUSH OFF", 250, 160);
		g.drawString("B.O.A.T.", push.WIDTH/2 - 150, 160);
		
		// Buttons
		Font fnt1 = new Font("arial", Font.BOLD, 40);
		g.setFont(fnt1);
		g.setColor(Color.white);
		g2d.draw(playButton);
		g2d.draw(controlButton);
		g2d.draw(infoButton);
		g2d.draw(exitButton);
		
		if(mouseInPlay) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  250,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("Play", playButton.x + 47, playButton.y + 43);
		
		if(mouseInControl) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  375,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("Controls", controlButton.x + 11, controlButton.y + 43);
		
		if(mouseInInfo) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  500,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("Info", infoButton.x + 55, infoButton.y + 43);
		
		if(mouseInExit) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  625,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("Exit", exitButton.x + 52, exitButton.y + 43);
		
	}
}
