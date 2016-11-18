import java.awt.*;

public interface Balls {
	public void draw(Graphics g);
	public void move();
	public double getX();
	public double getY();
	public double getXVel();
	public double getYVel();
}
