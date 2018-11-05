/*----------------------------------------------------------------
 *  Author:   K. Walsh
 *  Email:    kwalsh@holycross.edu
 *  Written:  7/8/2015
 *  
 *  A class used for drawing a "star" sprite. It is meant to be
 *  used by other classes, e.g. games, but it also has a main()
 *  function for testing purposes.
 *
 *  Example: java Star
 *----------------------------------------------------------------*/

import java.awt.Color;

/**
 * Each Star object represents a star-shaped sprite. It has a position, a color,
 * a size, etc., and it can draw itself using the StdDraw canvas. For example,
 * here is how to create a 5-pointed star with radius 0.1 and (x, y) coordinates
 * (0.5, 0.5), then draw it on the screen:
 *
 *    Star s = new Star(0.5, 0.5, 0.1, 5);
 *    s.draw();
 *    StdDraw.show();
 *
 * You can change the star's position, size, color, etc. using the accessor
 * functions. Changing these properties doesn't affect what has already been
 * drawn. Instead, you must call draw() again to draw the new image. By itself,
 * the Star class doesn't offer much support for animation. For example, a Star
 * object doesn't have a speed or velocity.
 */
public class Star { 

    // Position of this star on the canvas. Units are "meters".
    private double x, y;

    // Points defining the boundaries of this star. These two arrays define the
    // (x, y) coordinates of each point and each inside corner of the star.
    private double px[], py[];

    // Radius of this star when drawn on the canvas. Units are "meters".
    private double radius;

    // Number of points this star has.
    private int points;

    // Color used to draw this star on the canvas.
    private Color color;

    /**
     * Constructor: Initializes a new n-pointed star at the given (x, y)
     * coordinates with the given radius. The color is BLACK initially.
     */
    public Star(double x, double y, double radius, int n) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.points = n;
        this.color = StdDraw.BLACK;
        this.px = new double[2*n];
        this.py = new double[2*n];
        updateBoundary();
    }

    /**
     * Constructor: Initialize a new n-pointed star. It will be placed at
     * coordinates (0.5, 0.5) with radius 0.1. The color is BLACK initially.
     */
    public Star(int n) {
        this.x = 0.5;
        this.y = 0.5;
        this.radius = 0.1;
        this.points = n;
        this.color = StdDraw.BLACK;
        this.px = new double[2*n];
        this.py = new double[2*n];
        updateBoundary();
    }

    /**
     * Return a string representation of this star. Possibly useful for
     * debugging.
     */
    public String toString() {
        return "Star(" + x + ", " + y + ", " + radius + ", " + points + ")";
    }

    /**
     * Set the x coordinate of this star on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the star will be drawn at the new position.
     */
    public void setXPosition(double x) {
        this.x = x;
        updateBoundary();
    }

    /**
     * Set the y coordinate of this star on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the star will be drawn at the new position.
     */
    public void setYPosition(double y) {
        this.y = y;
        updateBoundary();
    }

    /**
     * Set the position of this star on the canvas. Units are "meters". This
     * does not affect any drawing that has already been done. The next time
     * draw() is called, the star will be drawn at the new position.
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateBoundary();
    }

    /**
     * Set the radius of this star. Units are "meters". This does not affect any
     * drawing that has already been done. The next time draw() is called, the
     * star will be drawn with the new radius.
     */
    public void setRadius(double r) {
        radius = r;
        updateBoundary();
    }

    /**
     * Set the color of this star. This does not affect any drawing that has
     * already been done. The next time draw() is called, the star will be drawn
     * with the new color.
     */
    public void setColor(Color c) {
        color = c;
    }

    /**
     * Return the x coordinate of this star.
     */
    public double getX() {
        return x;
    }

    /**
     * Return the y coordinate of this star.
     */
    public double getY() {
        return y;
    }

    /**
     * Return the radius of this star.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Calculate the approximate size (or "surface area") of this star. Units
     * are square "meters".
     */
    public double size() {
        return Math.PI * radius * radius;
    }

    /**
     * Draw this star on the StdDraw canvas. After calling this function,
     * clients should call one of the StdDraw.show() functions so that the
     * updated canvas appears on the screen.
     */
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledPolygon(px, py);
    }

    /**
     * Sharpness factor for the border of a star. Larger means more pointy.
     */
    private static final double SHARPNESS = 0.2;

    /**
     * Update the boundary points of this star. This is called internally by
     * other functions in this class every time the size or position changes.
     * The results of this function are used by the draw() function.
     */
    private void updateBoundary() {
        for (int i = 0; i < 2 * points; i++) {
            double angle = i * 2 * Math.PI / (2 * points);
            double r = radius * (1.0 + (i % 2 == 0 ? SHARPNESS : -SHARPNESS));
            px[i] = x + r * Math.sin(angle);
            py[i] = y + r * Math.cos(angle);
        }
    }

    /**
     * A main() function for testing purposes.
     * <pre>
     * {@code
     *   // Draw a large yellow 7-pointed star in the middle of the canvas.
     *   Star a = new Star(0.5, 0.5, 0.3, 7);
     *   a.setColor(StdDraw.YELLOW);
     *   a.draw();
     *
     *   // Draw 10 small blue 11-pointed stars at random positions.
     *   for (int i = 0; i < 10; i++) {
     *       Star b = new Star(StdRandom.uniform(),
     *               StdRandom.uniform(), 0.03, 11);
     *       b.setColor(StdDraw.BLUE);
     *       b.draw();
     *   }
     *
     *   // Show the canvas on the screen.
     *   StdDraw.show();
     * }
     * </pre>
     */
    public static void main(String args[]) {
        // Draw a large yellow 7-pointed star in the middle of the canvas.
        Star a = new Star(0.5, 0.5, 0.3, 7);
        a.setColor(StdDraw.YELLOW);
        a.draw();

        // Draw 10 small blue 11-pointed stars at random positions.
        for (int i = 0; i < 10; i++) {
            Star b = new Star(StdRandom.uniform(),
                    StdRandom.uniform(), 0.03, 11);
            b.setColor(StdDraw.BLUE);
            b.draw();
        }

        // Show the canvas on the screen.
        StdDraw.show();
    }
}
