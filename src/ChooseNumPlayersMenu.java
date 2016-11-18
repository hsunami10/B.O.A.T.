/*
 * Created by Michael Hsu
 * This menu allows the user to choose the number of players they want
 */

import java.awt.*;

public class ChooseNumPlayersMenu {
	
	public Rectangle players2, players3, players4;
	final int WIDTH_RECT = 200, HEIGHT_RECT = 60;
	
	boolean startGame = false;
	boolean mouseIn2, mouseIn3, mouseIn4;
	
	PushOff push;
	
	public ChooseNumPlayersMenu() {
		push = new PushOff();
		players2 = new Rectangle(push.WIDTH/2-WIDTH_RECT/2, 250, WIDTH_RECT, HEIGHT_RECT);
		players3 = new Rectangle(push.WIDTH/2-WIDTH_RECT/2, 450, WIDTH_RECT, HEIGHT_RECT);
		players4 = new Rectangle(push.WIDTH/2-WIDTH_RECT/2, 650, WIDTH_RECT, HEIGHT_RECT);
	}
	
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		
		// Title
		Font fnt = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("Choose the number of players", 85, 150);
		
		// Buttons
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.setColor(Color.white);
		g2d.draw(players2);
		g2d.draw(players3);
		g2d.draw(players4);
		
		if(mouseIn2) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  250,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("2 Players", players2.x + 35, players2.y + 40);
		
		if(mouseIn3) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  450,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("3 Players", players3.x + 35, players3.y + 40);
		
		if(mouseIn4) {
			g.setColor(Color.white);
			g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  650,  WIDTH_RECT,  HEIGHT_RECT);
			g.setColor(Color.black);
		}
		else
			g.setColor(Color.white);
		g.drawString("4 Players", players4.x + 35, players4.y + 40);
	}
}
