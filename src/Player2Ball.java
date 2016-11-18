import java.awt.Color;
import java.awt.Graphics;

public class Player2Ball implements Balls {
	
	double xpos, ypos, dx, dy, xAccelP, xAccelM, yAccelP, yAccelM;
	boolean movingHorizPlus, movingVertPlus, movingHorizMinus, movingVertMinus;
	final double FRICTION = 0.985;
	double perspRadius;
	final double REAL_RADIUS = 25;
	double t = 0, d = 50;
	boolean offTable, radiusOff;
	PushOff push;
	final int MAXVEL = 5;
	
	public Player2Ball(int player, int xposition, int yposition, int radiusTable) {
		
		perspRadius = REAL_RADIUS;
		
		push = new PushOff();
		
		if(player == 1) {
			xpos = xposition - radiusTable + perspRadius * 2;
			ypos = yposition;
		}
		else if(player == 2) {
			xpos = xposition + radiusTable - perspRadius * 2;
			ypos = yposition;
		}
		else if(player == 3) {
			xpos = xposition;
			ypos = yposition - radiusTable + perspRadius * 2;
		}
		else if(player == 4) {
			xpos = xposition;
			ypos = yposition + radiusTable - perspRadius * 2;
		}
		
	}
	
	public boolean radiusOff(boolean input) {
		return radiusOff = input;
	}
	
	// Check if the ball is off the table
	public boolean isOffTable(boolean input) {
		return offTable = input;
	}
	
	public double getRadius() {
		return perspRadius;
	}
	
	public double getAccel() {
		return 0.1;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		
		// Shrink ball when it's off the table
		if(offTable) {
			t += 0.065;
			perspRadius = d/(d + 4.9* Math.pow(t, 2)) * REAL_RADIUS;
			dx *= (d+4.9*Math.pow(.97*t-0.15, 2))/(d + 4.9* Math.pow(t, 2));
			dy *= (d+4.9*Math.pow(.97*t-0.15, 2))/(d + 4.9* Math.pow(t, 2));
		}
		
		g.fillOval((int)(xpos-perspRadius), (int)(ypos-perspRadius), (int)perspRadius*2, (int)perspRadius*2);
	}
	
	public void move() {
		// horizontal plus and vertical plus are constant????
		// Set velocity
		if(movingHorizPlus && !radiusOff)
			dx += getAccel();//(xAccelP += .02);
		if(movingHorizMinus && !radiusOff)
			dx -= getAccel();//(xAccelM += .02);
		if(movingVertPlus && !radiusOff)
			dy += getAccel();//(yAccelP += .02);
		if(movingVertMinus && !radiusOff)
			dy -= getAccel();//(yAccelM += .02);
		
		// Simulate friction
		if(!movingHorizPlus && !movingHorizMinus) {
			dx *= FRICTION;
			xAccelP = 0;
			xAccelM = 0;
			/*xAccelP -= .01;
			xAccelM -= .01;
			if(xAccelP < 0)
				xAccelP = 0;
			if(xAccelM < 0)
				xAccelM = 0;*/
		}
		if(!movingVertPlus && !movingVertMinus) {
			dy *= FRICTION;
			yAccelP = 0;
			yAccelM = 0;
		}
		
		if(dx > MAXVEL)
			dx = MAXVEL;
		if(dx < -MAXVEL)
			dx = -MAXVEL;
		if(dy > MAXVEL)
			dy = MAXVEL;
		if(dy < -MAXVEL)
			dy = -MAXVEL;
		
		// Velocity cap
		/*if(dx >= 8)
			dx = 8;
		else if(dy >= 8)
			dy = 8;*/
		
		// Update position
		xpos += dx;
		ypos += dy;
		
	/*	if(xpos < perspRadius || xpos > 900 - perspRadius)
			dx = -dx;
		if(ypos < perspRadius || ypos > 800 - perspRadius)
			dy = -dy; */
	}
	
	public double getX() {
		return xpos;
	}
	
	public double getY() {
		return ypos;
	}

	public void setX(double input) {
		xpos = input;
	}

	public void setY(double input) {
		ypos = input;
	}
	
	public double getXVel() {
		return dx;
	}

	public double getYVel() {
		return dy;
	}
	
	public void setXVel(double n) {
		dx = n;
	}
	
	public void setYVel(double n) {
		dy = n;
	}
	
	public void setDirectionXPlus(boolean input) {
		movingHorizPlus = input;
	}

	public void setDirectionYPlus(boolean input) {
		movingVertPlus = input;
	}
	
	public void setDirectionXMinus(boolean input) {
		movingHorizMinus = input;
	}

	public void setDirectionYMinus(boolean input) {
		movingVertMinus = input;
	}
}
