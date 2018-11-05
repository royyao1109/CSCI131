/*----------------------------------------------------------------
 *  Author:   K. Walsh
 *  Email:    kwalsh@holycross.edu
 *  Written:  7/8/2015 - forked from Star.java
 *  Updated:  7/9/2015 - added animation support
 *  
 *  A class used for drawing a "ball" sprite. It is meant to be
 *  used by other classes, e.g. games, but it also has a main()
 *  function for testing purposes.
 *
 *  Example: java Ball
 *----------------------------------------------------------------*/

import java.awt.Color;

/**
 * Each Ball object represents a round sprite. It has a position, a color, a
 * size, etc., and it can draw itself using the StdDraw canvas. For example,
 * here is how to create a ball with radius 0.1 and (x, y) coordinates (0.5,
 * 0.5), then draw it on the screen:
 *
 *    Ball b = new Ball(0.5, 0.5, 0.1);
 *    b.draw();
 *    StdDraw.show();
 *
 * You can change the ball's position, size, color, etc. using the accessor
 * functions. Changing these properties doesn't affect what has already been
 * drawn. Instead, you must call draw() again to draw the new image.
 *
 * Ball objects have some support for animation. Each ball has a velocity, and
 * the move() function will update the position according to the velocity. There
 * is also some code for detecting when the ball has gone off the edge of the
 * canvas and either "wrapping around" to the other side or "bouncing" off the
 * edge. See main() below for an example of this kind of animation.
 */
public class Ball { 

    // Position of this ball on the canvas. Units are "meters".
    private double x, y;

    // Radius of this ball when drawn on the canvas. Units are "meters".
    private double radius;

    // Velocity of this ball in the x and y directions. Positive vx values mean
    // rightward. Positive vy values mean upwards. Units are "meters" per
    // second.
    private double vx, vy;

    // Color used to draw this ball on the canvas.
    private Color color;

    /**
     * Initializes a new ball at the given (x, y) coordinates and with the given
     * radius. The color is BLACK initially, and the initial
     * velocity is zero.
     */
    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.vx = 0.0;
        this.vy = 0.0;
        this.radius = radius;
        this.color = StdDraw.BLACK;
    }

    /**
     * Initializes a new ball. It will be placed at coordinates (0.5, 0.5) with
     * radius 0.1. The color is BLACK initially, and the initial velocity is
     * zero.
     */
    public Ball() {
        this.x = 0.5;
        this.y = 0.5;
        this.vx = 0.0;
        this.vy = 0.0;
        this.radius = 0.1;
        this.color = StdDraw.BLACK;
    }

    /**
     * Return a string representation of this ball. Possibly useful for
     * debugging.
     */
    public String toString() {
        return "Ball(" + x + ", " + y + ", " + radius + ")" +
            " with speed "  + speed();
    }

    /**
     * Set the x coordinate of this ball on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the ball will be drawn at the new position.
     */
    public void setXPosition(double x) {
        this.x = x;
    }

    /**
     * Set the y coordinate of this ball on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the ball will be drawn at the new position.
     */
    public void setYPosition(double y) {
        this.y = y;
    }

    /**
     * Set the position of this ball on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the ball will be drawn at the new position.
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the velocity of this ball. Units are "meters" per second. The
     * velocity values affect how much the ball will move in the x and y
     * directions when the move() function is called. Positive vx values mean
     * rightward. Positive vy values mean upwards.
     */
    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Set the speed of this ball. Units are "meters" per second. The speed
     * values affect how fast the ball is moving, but not the direction. To
     * change the direction, use setVelocity() instead. Note: if the ball is
     * currenctly not moving at all, setting the speed to anything other than
     * zero will cause it to pick a random direction.
     */
    public void setSpeed(double s) {
        double oldSpeed = speed();
        if (oldSpeed < 0.0001) {
            // Avoid dividing by zero by picking a random direction.
            double angle = StdRandom.uniform(0, 2*Math.PI);
            vx = Math.cos(angle);
            vy = Math.sin(angle);
            oldSpeed = 1.0;
        }
        // Multiply current velocity to speed or slow the ball.
        vx = vx * s / oldSpeed;
        vy = vy * s / oldSpeed;
    }

    /**
     * Change the velocity of this ball so it is heading towards some new point
     * at the given speed. If the new point is close by, the actual speed will
     * be a little less than what was requested. If the new point is very close
     * by, exactly where this ball already is, then the new velocity will be
     * close to zero.
     */
    public void aimTowards(double newx, double newy, double speed) {
        double dx = newx - x;
        double dy = newy - y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        if (distance < radius) {
            // The point is close by, so divide by radius (a larger number)
            // instead of distance.
            vx = speed * dx / radius;
            vy = speed * dy / radius;
        } else {
            vx = speed * dx / distance;
            vy = speed * dy / distance;
        }
    }

    /**
     * Change the velocity of this ball so it is heading away from the given point
     * at the given speed. If the new point is very close by, almost exactly
     * where the ball already is, then the new velocity will be in a random
     * direction instead.
     */
    public void aimAwayFrom(double newx, double newy, double speed) {
        double dx = - (newx - x);
        double dy = - (newy - y);
        double distance = Math.sqrt(dx*dx + dy*dy);
        if (distance < 0.001) {
            // The point is too close by, so use a random destination to
            // avoid dividing by zero.
            double angle = StdRandom.uniform(0, 2*Math.PI);
            dx = Math.cos(angle);
            dy = Math.sin(angle);
            distance = 1.0;
        }
        vx = speed * dx / distance;
        vy = speed * dy / distance;
    }

    /**
     * Calculate the distance from this ball to a specific point.
     */
    public double distanceTo(double otherx, double othery) {
        double dx = x - otherx;
        double dy = y - othery;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Calculate the distance from this ball to another ball.
     */
    public double distanceTo(Ball other) {
        return distanceTo(other.getX(), other.getY());
    }

    /**
     * Calculate the distance from this ball to a star.
     */
    public double distanceTo(Star star) {
        return distanceTo(star.getX(), star.getY());
    }

    /**
     * Calculate the speed of this ball. Units are "meters" per second.
     */
    public double speed() {
        return Math.sqrt(vx*vx + vy*vy);
    }

    /*
     * Check whether this ball is touching a star.
     */
    public boolean touching(Star star) {
        // this ball is touching other if the distance
        // is less than our combined sizes.
        return (distanceTo(star) < radius + star.getRadius());
    }

    /**
     * Check whether this ball is touching another.
     */
    public boolean touching(Ball other) {
        // this ball is touching other if the distance
        // is less than our combined sizes.
        return (distanceTo(other) < radius + other.getRadius());
    }

    /**
     * Check whether this ball is completely covering another.
     */
    public boolean covering(Ball other) {
        // this ball is covering other if we are bigger than
        // the distance plus their size.
        return (radius > distanceTo(other) + other.getRadius());
    }

    /**
     * Check whether this ball is completely covering a star.
     */
    public boolean covering(Star star) {
        // this ball is covering other if we are bigger than
        // the distance plus their size.
        return (radius > distanceTo(star) + star.getRadius());
    }

    /**
     * Set the radius of this ball. Units are "meters". This does not affect any
     * drawing that has already been done. The next time draw() is called, the
     * ball will be drawn with the new radius.
     */
    public void setRadius(double r) {
        radius = r;
    }

    /**
     * Set the color of this ball. This does not affect any drawing that has
     * already been done. The next time draw() is called, the ball will be drawn
     * with the new color.
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Set the hue (or "color") of a ball. The hue value parameter shoud be
     * between 0.0 and 1.0. The saturation and lightness will be set to provide
     * a pleasant, solid medium-bright color.
     */
    public void setHue(double hue) {
        color = Color.getHSBColor((float)hue, 0.7f, 0.6f);
    }

    /**
     * Return the x coordinate of this ball.
     */
    public double getX() {
        return x;
    }

    /**
     * Return the y coordinate of this ball.
     */
    public double getY() {
        return y;
    }

    /**
     * Return the radius of this ball.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Calculate the size (or "surface area") of this ball. Units are square
     * "meters".
     */
    public double size() {
        return Math.PI * radius * radius;
    }

    /**
     * Make the size (or "surface area") of this ball larger or smaller by the
     * given amount. Units are square "meters".
     */
    public void grow(double amount) {
        setSize(size() + amount);
    }

    /**
     * Set the size of this ball. Units are square "meters".
     */
    public void setSize(double newSize) {
        radius = Math.sqrt(newSize / Math.PI);
    }

    /**
     * Draw this star on the StdDraw canvas. After calling this function,
     * clients should call one of the StdDraw.show() functions so that the
     * updated canvas appears on the screen.
     */
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, radius);
    }

    /**
     * Update the position of this ball, as if it had moved for the given
     * duration of time (in milliseconds) in the direction specified by its
     * velocity. It is intended that clients will call move(X) once every X
     * milliseconds. For example, you might call move(10) once every 100
     * milliseconds (or 10 times per second), which will give the illusion of
     * motion animation at 10 frames per second.
     */
    public void move(int durationInMilliseconds) {
        // We divide by 1000 because the (vx, vy) velocities are in meters per
        // second, not meters per millisecond.
        x += vx * durationInMilliseconds / 1000.0;
        y += vy * durationInMilliseconds / 1000.0;
    }

    /**
     * If this ball has completely moved off the StdDraw canvas, change its
     * position so it "wrap" around to the other side of the canvas. The
     * velocity is left as is. Calling this function between calls to move()
     * will give the illusion that the left and right sides of the screen "wrap
     * around", and similarly the top and bottom of the screen "wrap around".
     *
     * Note: This function assumes the drawing window scale has not been changed
     * from the default.
     */
    public void wrap() {
        while (x > 1.0 + radius) x -= (1.0 + 2.0*radius);
        while (y > 1.0 + radius) y -= (1.0 + 2.0*radius);
        while (x < 0.0 - radius) x += (1.0 + 2.0*radius);
        while (y < 0.0 - radius) y += (1.0 + 2.0*radius);
    }

    /**
     * If this ball is moving towards and touching an edge of the StdDraw
     * canvas, negate its velocity so it appears to "bounce" off the edge. The
     * position is left as is. Calling this function between calls to move()
     * will give the illusion that the edges of the screen are solid walls.
     *
     * Note: This function assumes the drawing window scale has not been changed
     * from the default.
     */
    public void bounce() {
        if ((vx > 0 && x + radius > 1.0) || (vx < 0 && x - radius < 0.0)) {
            vx *= -1;
        }
        if ((vy > 0 && y + radius > 1.0) || (vy < 0 && y - radius < 0.0)) {
            vy *= -1;
        }
    }

    /**
     * If this ball is touching the other ball, this ball will change it's
     * direction so that it is moving directly away from the other ball.
     */
    public void bounce(Ball other) {
        if (distanceTo(other) < this.radius + other.radius) {
            aimAwayFrom(other.getX(), other.getY(), this.speed());
        }
    }

    /**
     * If this ball is touching the star, this ball will change it's
     * direction so that it is moving directly away from the star.
     */
    public void bounce(Star other) {
        if (distanceTo(other) < this.radius + other.getRadius()) {
            aimAwayFrom(other.getX(), other.getY(), this.speed());
        }
    }

    /**
     * A main() function for testing purposes.
     * <pre>
     * {@code
     *   // Create a large yellow ball in the middle of the canvas, moving
     *   // to the right.
     *   Ball a = new Ball(0.5, 0.5, 0.3);
     *   a.setColor(StdDraw.YELLOW);
     *   a.setVelocity(0.1, 0.0); // rightward
     *
     *   // Create one small blue ball at random position and random velocity.
     *   Ball b = new Ball(StdRandom.uniform(), StdRandom.uniform(), 0.03);
     *   b.setColor(StdDraw.BLUE);
     *   b.setVelocity(StdRandom.uniform(), StdRandom.uniform());
     *
     *   // Animate the scene at 50 frames per second.
     *   int duration = 1000/50;
     *   while (true) {
     *       a.move(duration);
     *       b.move(duration);
     *
     *       a.bounce();
     *       b.wrap();
     *
     *       StdDraw.clear();
     *       a.draw();
     *       b.draw();
     *       StdDraw.show(duration);
     *   }
     * }
     * </pre>
     */
    public static void main(String args[]) {
        // Create a large yellow ball in the middle of the canvas, moving
        // to the right.
        Ball a = new Ball(0.5, 0.5, 0.3);
        a.setColor(StdDraw.YELLOW);
        a.setVelocity(0.1, 0.0); // rightward

        // Create one small blue ball at random position and random velocity.
        Ball b = new Ball(StdRandom.uniform(), StdRandom.uniform(), 0.03);
        b.setColor(StdDraw.BLUE);
        b.setVelocity(StdRandom.uniform(), StdRandom.uniform());

        // Animate the scene at 50 frames per second.
        int duration = 1000/50;
        while (true) {
            a.move(duration);
            b.move(duration);

            a.bounce();
            b.wrap();

            StdDraw.clear();
            a.draw();
            b.draw();
            StdDraw.show(duration);
        }
    }

}
