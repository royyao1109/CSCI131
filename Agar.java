/*----------------------------------------------------------------
 *  Author: TODO
 *  Email: TODO
 *  Written: TODO 
 *  
 *  A game similar to http://agar.io, in which the user controls a round blob
 *  with the mouse and tries to eat smaller round blobs and avoid spikey shapes.
 *
 *  Example: java Agar
 *----------------------------------------------------------------*/

public class Agar {

    // Create a small ball with random position and velocity.
    public static Ball launchRandomBall() {

	Ball b = new Ball(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.03, 0.05));

	b.setHue(Math.random());

	b.aimTowards(Math.random(), Math.random(), 0.03);
        // TODO: add Code to create a small ball at a random position,
        // aim it towards a random point, and give it a pretty color.
        // Then return the ball.
        return b; // Delete this line, it is just a placeholder.
    }

    // Make the player blob eat the food blob by growing larger.
    public static void eat(Ball player, Ball food) {
        player.grow(food.size()/2);
        System.out.printf("Yum! Now I am %.5f big!\n", player.size());
    }

    // Poison the player blob by shrinking it.
    public static void poison(Ball player) {
        player.grow(-player.size()/4.0);
        StdOut.printf("Ack! I shrank to %.5f small!\n", player.size());
    }

    public static void main(String args[]) {

        // Create a medium-sized ball for the player.
        Ball player = new Ball(0.5, 0.5, 0.07);
        // TODO: make the player red instead of a random color.
        player.setColor(StdDraw.RED);

        // TODO: Create a small random ball to act as bouncing food.

	Ball food1 = launchRandomBall();

	Ball food2 = launchRandomBall();

        // TODO: Create a second small random bouncing food ball.

        // TODO (extra): Create a star to act as poison.

        // Loop the animation forever.
        while (true) {

            // Update player so it is aiming towards the mouse.
            player.aimTowards(StdDraw.mouseX(), StdDraw.mouseY(), 1.0/6.0);

            // Make the player bounce off the edge of the screen.
            player.bounce();

            // TODO: Make the food blobs bounce off the edge of the screen.
	    food1.bounce();
	    food2.bounce();

            // Move the player.
            player.move(20);

            // TODO: Move the food blobs.
	    food1.move(20);
	    food2.move(20);

            // TODO: Check if the player has eaten either of the food balls. If
            // so, call the eat() function above and replace that food ball by
            // launching a newly created random food ball.
	    if(player.covering(food1)) {
		eat(player, food1);
		food1 = launchRandomBall();
	    }

	    if(player.covering(food2)) {
		eat(player, food2);
		food2 = launchRandomBall();
	    }

	    

            // TODO (extra): Check if the player has touched the poison. If so,
            // call the poison() function above.

            // Draw the scene on the StdDraw canvas.
            StdDraw.clear(StdDraw.WHITE);
            // TODO: Draw the two food balls.
	    food1.draw();
	    food2.draw();
            // TODO (extra): Draw the poison.
            // Draw the player.
            player.draw();

            // Show the canvas on the screen.
            StdDraw.show(20);
        }

    }
}
