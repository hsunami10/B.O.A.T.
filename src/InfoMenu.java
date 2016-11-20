import java.awt.*;   
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InfoMenu implements MouseListener {
	
    final int WIDTH_RECT = 180, HEIGHT_RECT = 60;
    public Rectangle backButton;
    PushOff push;
    boolean mouseInBack;
    
    public InfoMenu() {
        push = new PushOff();
        mouseInBack = false;
        backButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 625, WIDTH_RECT, HEIGHT_RECT);
    }
    
    public void draw(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        // Title
        //g.drawString("PUSH OFF", 250, 160);
         g.setColor(Color.white);
    	g.setFont(new Font("arial", Font.BOLD, 16));
    	g.drawString("Instructions:", push.WIDTH/2-push.WIDTH/20,50);
    	g.drawString("The purpose of this game is to survive as long as possible as well as trying to", 100,100);
    	g.drawString("knock your oppenents, indicated by different colors off the podium. As time decreases,", 100,150);
    	g.drawString("the podium will get smaller and smaller.", 100, 200);
    	g.drawString("Made by: Michael Hsu, Daniel Zhu, and John Laboe", 100, 500);
        
        // Buttons
        Font fnt1 = new Font("arial", Font.BOLD, 40);
        g.setFont(fnt1);
        g.setColor(Color.white);
        g2d.draw(backButton);
        
        if(mouseInBack) {
            g.setColor(Color.white);
            g.fillRect(push.WIDTH/2-WIDTH_RECT/2,  625,  WIDTH_RECT,  HEIGHT_RECT);
            g.setColor(Color.black);
        }
        else
            g.setColor(Color.white);
        g.drawString("Back", backButton.x + 43, backButton.y + 43);
    }

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}


