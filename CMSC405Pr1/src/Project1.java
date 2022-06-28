//Patric Centorbi
//CMSC 405 Section 2225
//June 28, 2022
//Project 1
//Project1.java
//Main class that creates window and content panel and draws shapes defined in
//Image.java class. Also performs requested transitions that are activated with 
//JButton in the window. Uses elements from CMSC405P1Template.java
//*********************************************************************************************************

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Project1 extends JPanel {
    // Create instance of Image object and 3 BufferedImage objects from the shape definitions
    Image images = new Image();
    BufferedImage image1 = images.createShape(Image.shapeP);
    BufferedImage image2 = images.createShape(Image.shapeA);
    BufferedImage image3 = images.createShape(Image.shapeT);
    private float pixelSize;
    private static int frameNumber = 1;
    static int translateX;
    static int translateY;
    static double rotation;
    static double scaleX;
    static double scaleY;

    public static void main(String[] args) {
	JFrame window = new JFrame("CMSC 405 Project 1"); // Make the window
	JButton btn = new JButton("Next");
	final Project1 panel = new Project1();
	window.setContentPane(panel);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable(false);
	window.add(btn);
	btn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (panel.frameNumber > 5) {
		    panel.frameNumber = 1;
		} else {
		    panel.frameNumber++;
		}
		panel.repaint();
	    }// actionPerformed
	});
	window.pack();
	window.setVisible(true);
    }// main

    // Constructor for Project1 object
    public Project1() {
	setPreferredSize(new Dimension(500, 300));
    }

    // Creating Graphics2D object and casting base Graphics as G2D
    // Also enabling antialiasing
    protected void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setPaint(Color.BLACK);
	g2.fillRect(0, 0, getWidth(), getHeight());
	applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

	AffineTransform savedTransform = g2.getTransform();
	switch (frameNumber) {
	case 2:
	    System.out.println("Translate x -5 and y + 7.");
	    translateX = translateX - 5;
	    translateY = translateY + 7;
	    break;
	case 3:
	    System.out.println("Rotate 45 degrees counter clockwise.");
	    rotation = rotation + (Math.PI / 4);
	    break;
	case 4:
	    System.out.println("Rotate 90 degrees clockwise.");
	    rotation = rotation + (3 * Math.PI / 2);
	    break;
	case 5:
	    System.out.println("Scale x * 2 and y / 2.");
	    scaleX = scaleX / 2;
	    scaleY = scaleY * 2;
	    break;
	default:
	    System.out.println("Set images to initial values.");
	    translateX = -5;
	    translateY = 7;
	    rotation = 3 * Math.PI / 2;
	    scaleX = 1.0;
	    scaleY = 1.0;
	    break;
	}// switch

	// Draw initial P image
	g2.translate((-80 + translateX), (0 + translateY));
	g2.rotate(rotation);
	g2.scale(scaleX, scaleY);
	g2.drawImage(image1, 0, 0, this);
	g2.setTransform(savedTransform);

	// Draw initial A image
	g2.translate((-5 + translateX), (0 + translateY));
	g2.rotate(rotation);
	g2.scale(scaleX, scaleY);
	g2.drawImage(image2, 0, 0, this);
	g2.setTransform(savedTransform);

	// Draw T in initial image
	g2.translate((70 + translateX), (0 + translateY));
	g2.rotate(rotation);
	g2.scale(scaleX, scaleY);
	g2.drawImage(image3, 0, 0, this);
	g2.setTransform(savedTransform);

    }// paintComponent

    // Borrowed this method from CMSC405P1Template which borrowed it from
    // AnimationStarter example out of textbook
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
    }// applyWindowToViewportTransformation

}