
/*
 * Assignment 1 - CSC345
 * Developers: Kevin Sangurima, Phillip Nam, Ryan Clark
 * Description: This code draws a picinc scene. The main template comes from the book and then we did
 *              modification in order to adapt it to our scene. For any information about the code
 *              please contact one of the deveopers.
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class PicnicScene extends JPanel {
    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Why do java programmers have to wear glasses? //Because they can't C#..."); // Small
                                                                                                         // programmer
                                                                                                         // joke
        PicnicScene panel = new PicnicScene(); // Create the panel
        window.setContentPane(panel); // Add panel to the main window pane
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack(); // Set window size based on the preferred sizes of its contents.
        window.setResizable(true); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // Set up the animation timer and operation
        Timer animationTimer; // A Timer that will emit events to drive the animation.
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panel.frameNumber++; // Advance to the "next" frame.
                panel.repaint();
            }
        });

        // Center window on screen.
        window.setLocation((screen.width - window.getWidth()) / 2, (screen.height - window.getHeight()) / 2);
        window.setVisible(true); // Open the window, making it visible on the screen.
        animationTimer.start(); // Start the timer going.
    }

    private float pixelSize; // This is the measure of a pixel in the coordinate system
                             // set up by calling the applyLimits method. It can be used
                             // for setting line widths, for example.
    private int frameNumber = 0; // Which frame we are on... Used in animation.
    // private int[] xSeeSaw=[0,5,0]
    // private int[] ySeeSaw=[]

    /**
     * This constructor sets up a PicnicScene when it is created. Here, it sets the
     * size of the drawing area. (The size is set as a "preferred size," which will
     * be used by the pack() command in the main() routine.)
     */
    public PicnicScene() {
        setPreferredSize(new Dimension(1100, 1000)); // Set size of drawing area, in pixels.
    }

    /**
     * The paintComponent method draws the content of the JPanel. The parameter is a
     * graphics context that can be used for drawing on the panel. Note that it is
     * declared to be of type Graphics but is actually of type Graphics2D, which is
     * a subclass of Graphics.
     */
    protected void paintComponent(Graphics g) {
        /*
         * First, create a Graphics2D drawing context for drawing on the panel.
         * (g.create() makes a copy of g, which will draw to the same place as g, but
         * changes to the returned copy will not affect the original.)
         */
        Graphics2D g2 = (Graphics2D) g.create();

        /*
         * Turn on antialiasing in this graphics context, for better drawing.
         */
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
         * Fill in the entire drawing area with the grass and the sky each with it's
         * respective color
         */
        g2.setPaint(new Color(0, 191, 255));
        g2.fillRect(0, 0, getWidth(), getHeight() / 2); // From the old graphics API!

        g2.setPaint(new Color(124, 252, 0));
        g2.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2); // From the old graphics API!

        g2.setPaint(new Color(248, 24, 148));// hot pink
        g2.setStroke(new BasicStroke(2 * pixelSize));
        // Draw y-axis and its tick marks

        /*
         * Here, I set up a new coordinate system on the drawing area, by calling the
         * applyLimits() method that is defined below. Without this call, I would be
         * using regular pixel coordinates. This function sets the global variable
         * pixelSize, which I need for stroke widths in the transformed coordinate
         * system.
         */

        applyWindowToViewportTransformation(g2, -5, 10, -1, 14, true);

        /*
         * Finish by drawing a few shapes as an example. You can erase the rest of this
         * subroutine and substitute your own drawing.
         */

        drawScene(g2);
    }

    /**
     * Draw the scene
     */
    private void drawScene(Graphics2D g2) {
        // drawCoordinateFrame(g2, 10); // 10 is the number of "ticks" to show

        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform
        g2.scale(.3, .3); // No scaling yet, but setting it up to make a tad bigger
        drawMainScene(g2);
    }

    private void drawLake(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(new Color(0, 0, 255)); // Blue
        Path2D lake = new Path2D.Double();
        lake.moveTo(1000, 0);
        lake.curveTo(500, -400, 500, -400, 0, 0);
        g2.scale(0.05, 0.03); // Big lake needs to shrink a bit
        g2.translate(-300, 725); // Center it a little more around original origin
        g2.fill(lake); // Now draw it
        g2.setTransform(cs);

    }

    private void drawBird(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        Arc2D arc = new Arc2D.Double(10, 35, .5, .25, 0, -180, Arc2D.OPEN);
        g2.setPaint(Color.BLACK);
        // g2.drawArc(10, 35, 1, 1, -20, -200);
        g2.draw(arc);

        // g2.rotate(Math.PI/4);
        // g2.translate(10,35);
        g2.setTransform(cs);

    }

    private void drawBag(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(new Color(92, 77, 57));

        g2.fillRect(21, 12, 3, 2);
        // g2.translate(0, 1);

        g2.draw(new Arc2D.Double(21.5, 13, 2, 2, 0, -180, Arc2D.OPEN));

        g2.setTransform(cs);

    }

    private void drawBlanket(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(new Color(207, 185, 151));
        g2.shear(.8, 0);
        g2.translate(6, 1);
        g2.fillRect(0, 0, 10, 12);

        g2.shear(-.8, 0);
        g2.translate(5, 5);
        g2.setPaint(Color.RED);
        g2.fillOval(3, 3, 1, 1);

        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(10 * pixelSize));
        g2.draw(new Line2D.Double(5.7,2.25,4,2.25));
        g2.draw(new Line2D.Double(6.4,3,4,0));
        g2.draw(new Line2D.Double(4,0,2.5,0));
        g2.draw(new Line2D.Double(2.5,0,1.8,-2));
  
        g2.fill(new Ellipse2D.Double(6,2,2.7,2.7));
        g2.setColor(Color.WHITE);
        g2.fill(new  Ellipse2D.Double(6.25,2.25,2.2,2.2));

        g2.setTransform(cs);

    }

    private void drawSun(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        int sunHeight = 10;
        double diameterModifier = 0;
        int alphaModifier = 0;

        if ((0 <= frameNumber % 125) && (frameNumber % 125 <= 25)) {
            alphaModifier = 4;
            diameterModifier = 0.007;
        } else if ((25 < frameNumber % 125) && (frameNumber % 125 <= 50)) {
            alphaModifier = 8;
            diameterModifier = 0.012;
        } else if ((50 < frameNumber % 125) && (frameNumber % 125 <= 75)) {
            alphaModifier = 5;
            diameterModifier = 0.008;
        } else if ((75 < frameNumber % 125) && (frameNumber % 125 <= 100)) {
            alphaModifier = 10;
            diameterModifier = 0.015;
        } else if ((frameNumber % 125) > 100) {
            alphaModifier = 6;
            diameterModifier = 0.010;
        }

        g2.scale(4, 4.3);
        g2.setPaint(new Color(255, 255, 0, 255 - alphaModifier)); // Set color to yellow
        g2.fill(new Ellipse2D.Double(4, sunHeight, 1.7 - diameterModifier, 1.7 - diameterModifier)); // lower left X,
                                                                                                     // lower left Y,
                                                                                                     // width, height

        g2.setPaint(new Color(255, 255, 0, 204 - alphaModifier)); // Set color to yellow
        g2.fill(new Ellipse2D.Double(3.8, sunHeight - .2, 2.1 - diameterModifier, 2.1 - diameterModifier)); // lower
                                                                                                            // left X,
                                                                                                            // lower
                                                                                                            // left Y,
                                                                                                            // width,
                                                                                                            // height

        g2.setPaint(new Color(255, 255, 0, 153 - alphaModifier)); // Set color to yellow
        g2.fill(new Ellipse2D.Double(3.6, sunHeight - .4, 2.5 - diameterModifier, 2.5 - diameterModifier)); // lower
                                                                                                            // left X,
                                                                                                            // lower
                                                                                                            // left Y,
                                                                                                            // width,
                                                                                                            // height

        g2.setPaint(new Color(255, 255, 0, 102 - alphaModifier)); // Set color to yellow
        g2.fill(new Ellipse2D.Double(3.4, sunHeight - .6, 2.9 - diameterModifier, 2.9 - diameterModifier)); // lower
                                                                                                            // left X,
                                                                                                            // lower
                                                                                                            // left Y,
                                                                                                            // width,
                                                                                                            // height

        g2.setPaint(new Color(255, 255, 0, 51 - alphaModifier)); // Set color to yellow
        g2.fill(new Ellipse2D.Double(3.2, sunHeight - .8, 3.3 - diameterModifier, 3.3 - diameterModifier)); // lower
                                                                                                            // left X,
                                                                                                            // lower
                                                                                                            // left Y,
                                                                                                            // width,
                                                                                                            // height

        g2.setTransform(cs);
    }

    private void drawSeeSaw(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();

        // base
        g2.setPaint(new Color(58, 95, 11));
        Path2D base = new Path2D.Double();
        g2.setStroke(new BasicStroke(4 * pixelSize));
        base.moveTo(-8.5, 2.5);
        base.lineTo(-3.5, 2.5);
        base.lineTo(-6, 6);
        base.closePath();
        g2.fill(base);

        // seesaw
        g2.setPaint(new Color(200, 0, 200));// hot pink
        g2.setStroke(new BasicStroke(1));

        g2.draw(new Line2D.Double(-14, 4, 3, 8.65));

        // Left person
        g2.setStroke(new BasicStroke(15 * pixelSize));
        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(-11.32, 10.25, -11.32, 4.65));
        g2.draw(new Line2D.Double(-11.32, 9, -8.32, 5.6));
        g2.draw(new Line2D.Double(-11.32, 4.65, -9.32, 3.6));
        g2.draw(new Line2D.Double(-9.32, 3.6, -11.32, 2.6));
        g2.fill(new Ellipse2D.Double(-14, 10, 5.5, 5.5));
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(-13.75, 10.25, 5, 5));

        // right person
        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(2.6, 14, 2.6, 8.5));
        g2.draw(new Line2D.Double(2.6, 13.5, .6, 8.5));
        g2.draw(new Line2D.Double(2.6, 8.5, 2, 6.5));
        g2.draw(new Line2D.Double(2, 6.5, 1.8, 4.5));

        g2.fill(new Ellipse2D.Double(-0.25, 14, 5.5, 5.5));
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(-0, 14.25, 5, 5));

        // g2.scale(4, 4);
        // g2.translate(2, 0);
        g2.setTransform(cs);
        // g2.drawPolygon(xPoints, yPoints, nPoints);
        // Triangle 58,95,11
        // Wood
        // 200,0,200

    }

    private void drawTree(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        Path2D Trunk = new Path2D.Double();
        g2.setPaint(new Color(83, 53, 10)); // brown
        g2.setStroke(new BasicStroke(4 * pixelSize));
        Trunk.moveTo(-17.5, 17);
        // Trunk.lineTo(-20,4.5); //This is the next point location (Debug)
        Trunk.curveTo(-17.5, 8, -19, 6, -20.5, 4.5);
        Trunk.lineTo(-16.0, 6.0);
        Trunk.lineTo(-14.3, 3.5);
        Trunk.lineTo(-13.0, 6.7);
        Trunk.lineTo(-8.8, 5.1);
        Trunk.curveTo(-11.0, 7.0, -12.9, 10, -13.5, 17);
        Trunk.closePath();
        g2.setPaint(new Color(85, 57, 11)); // Brown
        g2.fill(Trunk);
        g2.draw(Trunk);
        g2.setPaint(new Color(58, 95, 11)); // Dark Green
        g2.fill(new Ellipse2D.Double(-22.5, 16.5, 14, 14));
        g2.scale(1.2,1.2);
        g2.setTransform(cs);
        
    }
    
    private void drawMainScene(Graphics2D g2) {
        drawLake(g2);
        drawTree(g2);
        {
            AffineTransform cs = g2.getTransform();
            g2.translate(45, 0);
            drawTree(g2);
            g2.setTransform(cs);
        }
        
        drawSeeSaw(g2);
        drawSun(g2);
        drawBird(g2);
        drawBlanket(g2);
        drawBag(g2);
    }

    /**
     * Applies a coordinate transform to a Graphics2D graphics context. The upper
     * left corner of the viewport where the graphics context draws is assumed to be
     * (0,0). The coordinate transform will make a requested view window visible in
     * the drawing area. The requested limits might be adjusted to preserve the
     * aspect ratio. This method sets the value of the global variable pixelSize,
     * which is defined as the maximum of the width of a pixel and the height of a
     * pixel as measured in the coordinate system. (If the aspect ratio is
     * preserved, then the width and height will agree.
     * 
     * @param g2             The drawing context whose transform will be set.
     * @param left           requested x-value at left of drawing area.
     * @param right          requested x-value at right of drawing area.
     * @param bottom         requested y-value at bottom of drawing area; can be
     *                       less than top, which will reverse the orientation of
     *                       the y-axis to make the positive direction point
     *                       upwards.
     * @param top            requested y-value at top of drawing area.
     * @param preserveAspect if preserveAspect is false, then the requested view
     *                       window rectangle will exactly fill the viewport; if it
     *                       is true, then the limits will be expanded in one
     *                       direction, horizontally or vertically, if necessary, to
     *                       make the aspect ratio of the view window match the
     *                       aspect ratio of the viewport. Note that when
     *                       preserveAspect is false, the units of measure in the
     *                       horizontal and vertical directions will be different.
     */
    private void applyWindowToViewportTransformation(Graphics2D g2, double left, double right, double bottom,
            double top, boolean preserveAspect) {
        int width = getWidth(); // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }
}
