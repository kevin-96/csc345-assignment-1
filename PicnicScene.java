
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class PicnicScene extends JPanel {
    public static void main(String[] args) {
        JFrame window;
        window = new JFrame("Transformers: Graphics in Disguise!"); // The parameter shows in the window title bar.
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
 //   private int[] xSeeSaw=[0,5,0]
   // private int[] ySeeSaw=[]

    /**
     * This constructor sets up a PicnicScene when it is created. Here, it sets the
     * size of the drawing area. (The size is set as a "preferred size," which will
     * be used by the pack() command in the main() routine.)
     */
    public PicnicScene() {
        setPreferredSize(new Dimension(1000, 1000)); // Set size of drawing area, in pixels.
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
         * Fill in the entire drawing area with a black background.
         */
        g2.setPaint(new Color(0, 191, 255));
        g2.fillRect(0, 0, getWidth(), getHeight() / 2); // From the old graphics API!

        g2.setPaint(new Color(124, 252, 0));
        g2.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2); // From the old graphics API!

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

        // Scene version 1
        AffineTransform cs = g2.getTransform(); // Save current "coordinate system" transform
        g2.scale(.3,.3); // No scaling yet, but setting it up to make a tad bigger
        // drawMainScene(g2);
      drawLake(g2);
      drawSeeSaw(g2);
      drawTree(g2);
    }

    private void drawLake(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(new Color(0,0,255)); // Blue
        Path2D lake = new Path2D.Double();
        lake.moveTo(1000, 0); // These coordinates were used because that was the frame
        lake.curveTo(916, 0, 833.4, 0, 750, -250); // of reference when I drew them in a separate drawing program!
        lake.curveTo(666.7, 0, 583.4, 0, 500, -400); // of reference when I drew them in a separate drawing program!
        lake.curveTo(416.7, 0, 333.4, 0, 250, -250); // of reference when I drew them in a separate drawing program!
        lake.curveTo(166.7, 0, 8, -0, 0, 0); // of reference when I drew them in a separate drawing program!
        lake.closePath();
        g2.scale(0.03, 0.03); // Big lake needs to shrink a bit
        g2.translate(-250, 725); // Center it a little more around original origin
        g2.fill(lake); // Now draw it




        g2.setTransform(cs);
    }

    private void drawSeeSaw(Graphics2D g2) {
        AffineTransform cs = g2.getTransform();
        g2.setPaint(new Color(58,95,11));
        Path2D base = new Path2D.Double();
        base.moveTo(100, 200);
        base.moveTo(200, 0);
        base.moveTo(0, 0);
        base.closePath();
        g2.draw(base);
        g2.scale(4, 4);
        // g2.translate(2, 0);
        g2.setTransform(cs);
        //g2.drawPolygon(xPoints, yPoints, nPoints);
//Triangle 58,95,11
// Wood
// 200,0,200

    }
private void drawTree(Graphics2D g2){
    AffineTransform cs = g2.getTransform();
    g2.setPaint(new Color(58,95,11)); // dark green
    g2.fill(new Ellipse2D.Double(-17, 16.5, 12, 12));
    g2.setTransform(cs);
    

}
    private void drawMainScene(Graphics2D g2) {
        {
            AffineTransform cs = g2.getTransform();
            g2.setPaint(new Color(255, 0, 0)); // red
            g2.scale(3, 3);
            drawHouseWithChimney(g2);
            g2.setTransform(cs);
        }

        {
            AffineTransform cs = g2.getTransform();
            g2.setPaint(Color.BLUE);
            g2.translate(-1, 0);
            g2.scale(0.1, 0.1);
            drawMailbox(g2);
            g2.setTransform(cs);
        }

        // A little "bench"
        g2.setPaint(new Color(0, 255, 0)); // Set color to green (or Color.GREEN)
        g2.fill(new Rectangle2D.Double(3, 0.1, 1, 0.4)); // x, y, width, height

        // A swingset
        {
            AffineTransform save = g2.getTransform();
            g2.setPaint(Color.GREEN);
            g2.setStroke(new BasicStroke(4 * pixelSize));
            g2.translate(-5, 0);
            g2.scale(0.4, 0.4);
            drawSwingSet(g2);
            g2.setTransform(save);
        }

        // A "sun"
        int sunHeight = 10;
        g2.setPaint(Color.YELLOW); // Set color to yellow
        g2.fill(new Ellipse2D.Double(4, sunHeight, 2, 2)); // lower left X, lower left Y, width, height

        // A group of sunflowers
        {
            AffineTransform save = g2.getTransform();
            g2.translate(4, 0);
            g2.scale(0.2, 0.2); // Scale these flowers down! But they are still HUGE!!!
            double[] heights = { 4.0, 6.0, 3.0, 4.5, 5.5 };
            int[] petals = { 10, 15, 8, 7, 12 };
            drawSunflowerGarden(g2, heights, petals);
            g2.setTransform(save);
        }

        // Draw cloud over the sun but moving left
        {
            AffineTransform save = g2.getTransform();
            g2.setPaint(new Color(240, 240, 240, 200));
            double dx = ((frameNumber + 150) % 600) * 0.05; // The mod helps it "wrap" around
            g2.translate(15 - dx, sunHeight); // Move it up and over with the framenumber used for animation...
            drawCloud(g2);
            g2.setTransform(save);
        }
    }

    /**
     * Draw a coordinate frame
     */
    private void drawCoordinateFrame(Graphics2D g2, int length) {
        g2.setPaint(Color.WHITE);
        g2.setStroke(new BasicStroke(2 * pixelSize));
        // Draw y-axis and its tick marks
        g2.draw(new Line2D.Double(0, 0, 0, length)); // Y-axis
        for (int i = 1; i <= length; i++)
            g2.draw(new Line2D.Double(-0.2, i, 0.2, i));

        // Draw x-axis and its tick marks
        g2.draw(new Line2D.Double(0, 0, length, 0));
        for (int i = 1; i <= length; i++)
            g2.draw(new Line2D.Double(i, -0.2, i, 0.2));
    }

    /**
     * Draw a garden of sunflowers
     * 
     * @param g2     The drawing environment
     * @param height The array of heights
     * @param petals The number of petals for each sunflower The size of the array
     *               for height and petals should match
     */
    private void drawSunflowerGarden(Graphics2D g2, double[] height, int[] petals) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        assert (height.length == petals.length);
        for (int i = 0; i < height.length; i++) {
            drawSunflower(g2, height[i], petals[i]);
            g2.translate(4, 0);
        }
        g2.setTransform(cs); // And restore it...
    }

    /**
     * Draw a simple sunflower
     * 
     * @param g2        The drawing environment
     * @param height    The height of the stalk (relative to sunflower shape)
     * @param numPetals The number of petals to generate around the center (times
     *                  two actually) The origin of the sunflower is designed to be
     *                  the base of the stalk
     */
    private void drawSunflower(Graphics2D g2, double height, int numPetals) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        g2.setColor(new Color(0, 150, 0)); // Dark green
        g2.fill(new Rectangle2D.Double(-0.25, 0, 0.5, height)); // Draw stalk
        {
            // Draw one leaf
            AffineTransform cs2 = g2.getTransform(); // Save C.S. state
            g2.translate(0, height / 4.0);
            g2.rotate(Math.PI / 6);
            g2.fill(new Ellipse2D.Double(0, -0.1, 1, 0.2));
            g2.setTransform(cs2);
        }
        {
            // Draw second leaf
            AffineTransform cs2 = g2.getTransform(); // Save C.S. state
            g2.translate(0, height / 2.0);
            g2.rotate(-Math.PI / 8);
            g2.scale(-1, 1); // Reflect it about y-axis
            g2.fill(new Ellipse2D.Double(0, -0.08, 1.2, 0.16));
            g2.setTransform(cs2);
        }

        g2.translate(0, height); // Move to top of stalk

        // Draw the petals (first layer)
        AffineTransform cs2 = g2.getTransform(); // Save C.S. state
        g2.setColor(new Color(240, 240, 0));
        double rotation = 2 * Math.PI / numPetals;
        for (int i = 0; i < numPetals; i++) {
            g2.fill(new Ellipse2D.Double(0, -0.25, 3, 0.5));
            g2.rotate(rotation); // Next petal is slightly rotated
        }
        g2.rotate(rotation / 2); // Slight adjustment for next layer
        g2.setColor(new Color(255, 255, 0, 200));
        for (int i = 0; i < numPetals; i++) {
            g2.fill(new Ellipse2D.Double(0, -0.25, 2.5, 0.5));
            g2.rotate(rotation); // Next petal is slightly rotated
        }
        g2.setTransform(cs2);

        // Draw the flower center
        g2.setColor(new Color(160, 82, 45)); // "Sienna"
        g2.fill(new Ellipse2D.Double(-0.75, -0.75, 1.5, 1.5)); // lower left X, lower left Y, width, height

        g2.setTransform(cs); // Restore previous C.S. state
    }

    private void drawSwingSet(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        Path2D frame = new Path2D.Double();
        frame.moveTo(0, 0);
        frame.lineTo(1, 4);
        frame.lineTo(2, 0);
        frame.moveTo(1, 4);
        frame.lineTo(7, 4);
        frame.lineTo(6, 0);
        frame.moveTo(7, 4);
        frame.lineTo(8, 0);
        frame.moveTo(3, 4);
        frame.lineTo(3, 1);
        frame.lineTo(5, 1);
        frame.lineTo(5, 4);
        g2.draw(frame);
        g2.setTransform(cs); // Restore previous C.S. state
    }

    private void drawCloud(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        Path2D cloud = new Path2D.Double();
        cloud.moveTo(64, 112); // These coordinates were used because that was the frame
        cloud.curveTo(32, 144, 0, 48, 64, 64); // of reference when I drew them in a separate drawing program!
        cloud.curveTo(32, 16, 112, 16, 112, 64);
        cloud.curveTo(144, 16, 192, 16, 192, 80);
        cloud.curveTo(240, 16, 304, 160, 192, 144);
        cloud.curveTo(240, 192, 64, 192, 112, 128);
        cloud.curveTo(96, 160, 48, 144, 64, 112);
        cloud.closePath();
        g2.scale(0.02, 0.02); // Big cloud needs to shrink a bit
        g2.translate(-100, -100); // Center it a little more around original origin
        g2.fill(cloud); // Now draw it
        g2.setTransform(cs); // Restore previous C.S. state
    }

    private void drawMailbox(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        g2.fill(new Rectangle2D.Double(0, 0, 1, 5));
        g2.fill(new Rectangle2D.Double(0, 5, 4, 2));
        g2.setTransform(cs); // Restore previous C.S. state
    }

    private void drawHouseWithChimney(Graphics2D g2) {
        AffineTransform cs = g2.getTransform(); // Save C.S. state
        drawHouse(g2);
        g2.translate(0.8, 0.8);
        g2.scale(0.1, 0.4);
        drawHouse(g2);
        g2.setTransform(cs); // Restore previous C.S. state
    }

    /**
     * Draw a simple house as a closed polygonal path
     */
    private void drawHouse(Graphics2D g2) {
        Path2D poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(0, 1);
        poly.lineTo(0.5, 1.5);
        poly.lineTo(1, 1);
        poly.lineTo(1, 0);
        poly.closePath();

        g2.fill(poly);
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
