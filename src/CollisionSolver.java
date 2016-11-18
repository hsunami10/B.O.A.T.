/*
 * Created by John Laboe
 * This class calculates the physics for the balls colliding - perfectly elastic collisions
 */

import java.awt.*;

public class CollisionSolver
{
    // instance variables - replace the example below with your own
    
    public CollisionSolver()
    {
        // initialize instance variables
        
    }
    /*
     * Takes the center position of two balls as well as the components of their velocities
     * and returns a vector giving the components of their velocities following the collision.
     * 
     * Does not check if the two particles collided
     */
    public double[] equalMassCollision(double x1, double y1, double vx1, double vy1,  
                                        double x2, double y2, double vx2, double vy2){
                 double relVX = vx1-vx2;
                 double relVY = vy1-vy2;
                 double theta = angleBetween(relVX, relVY, x2-x1, y2-y1);
                 double magV2 = Math.cos(theta)*magVector(relVX,relVY);
                 double[] solution = new double[4];
                 
                 // X Y of second ball
                 solution[2]  = normalize(x2-x1, y2-y1)[0]*magV2;
                 solution[3]  = normalize(x2-x1, y2-y1)[1]*magV2;
                 
                 // X Y of first ball
                 solution[0] = relVX - solution[2] + vx2;
                 solution[1] = relVY - solution[3] + vy2;
                 solution[2] += vx2;
                 solution[3] += vy2;
                 
                 
                 return solution;
        }
        
     public double magVector(double x, double y)
     {
         return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        }
     public double[] normalize(double x, double y)
     {
         return new double[]{x/magVector(x,y),y/magVector(x,y)};

        }
     public double angleBetween(double x1, double y1, double x2, double y2){
         return Math.acos((x1*x2+y1*y2)/(magVector(x1,y1)*magVector(x2,y2)));
        }
     
    
     
}
