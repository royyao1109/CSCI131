/*----------------------------------------------------------------
 *  Author:   
 *  Email:    
 *  Written:  
 *  Animation of two stars, one bouncing left and right.
 *
 *  Example: java Bounce
 *----------------------------------------------------------------*/

public class Bounce {

    public static void main(String args[]) {

        // TODO: Allocate a new Star object and make it a pretty color.
        //
	Star s = new Star(0.5, 0.5, 0.05, 7);
	s.setColor(StdDraw.BLUE);
        // TODO: Allocate a second Star object and make it a pretty color.
	Star a = new Star(0.0, 0.8, 0.05, 9);
	a.setColor(StdDraw.RED);

        // TODO: Declare and initialize a variable for the x velocity.
	double  vx = 0.3;

        // Loop the animation forever.
        while (true) {

            // TODO: Update one star's x position, by adding the x velocity to
            // it. Actually, only add (velocity * 0.020) since this loop will
            // run every 20 milliseconds, i.e. 50 times per second.
	    double x = a.getX();
	    a.setXPosition(x + (vx * 0.02));
	    

            // TODO: If the star's x position is less than 0.0 or greater than
            // 1.0, negate the x velocity.
	    if( a.getX() > 1.0 || a.getX() < 0.0) {
		vx = vx * -1;
	    }

            // Draw the scene on the StdDraw canvas.
            StdDraw.clear(StdDraw.WHITE);
            // TODO: call each star's draw() function.
	    s.draw();
	    a.draw();

            // Show the canvas on the screen.
            StdDraw.show(20);
        }

    }
}
