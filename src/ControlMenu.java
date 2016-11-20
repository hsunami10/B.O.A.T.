/*
 * This is the class for the control menu
 * Players here can choose their controls
 */

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

public class ControlMenu {
	
    final int WIDTH_RECT = 180, HEIGHT_RECT = 60;
    public Rectangle backButton;
    PushOff push;
    boolean mouseInBack;
    
    public ControlMenu() {
        push = new PushOff();
        mouseInBack = false;
        backButton = new Rectangle(push.WIDTH/2 - WIDTH_RECT/2, 625, WIDTH_RECT, HEIGHT_RECT);
    }
    
    public void draw(Graphics g) {
        
        Graphics2D g2d = (Graphics2D)g;
        
        // Title
        Font fntTitle = new Font("arial", Font.BOLD, 40);
        g.setFont(fntTitle);
        g.setColor(Color.white);
        //g.drawString("PUSH OFF", 250, 160);
        g.drawString("Customize controls", push.WIDTH/2 - 200, 120);
        
        // Buttons
        Font fnt1 = new Font("arial", Font.BOLD, 20);
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
        g.drawString("Back", backButton.x + 52, backButton.y + 43);
    }
}
