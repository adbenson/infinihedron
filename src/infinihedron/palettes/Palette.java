package infinihedron.palettes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JComponent;

public abstract class Palette extends JComponent {
	
	public static final int WIDTH = 256;
	public static final int HEIGHT = 32;

	public static Random r = new Random();

	protected final int modulo;
	protected final int segment;

	protected BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

	protected static final Color purple = new Color(102, 0, 153);
	protected static final Color teal = new Color(0, 187, 197);

	public Palette(int modulo) {
		this.modulo = modulo;
		this.segment = WIDTH / modulo;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.draw2();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	public Color getColor() {
		return getColor(new Random().nextFloat());
	}
	
	public Color getColor(float value) {
		return getColorAt((int)((WIDTH - 1) * value));
	};

	public Color getColor(int value) {
		int i = value % this.modulo;
		return getColorAt(segment * (i + 1));
	};

	public Color getColorAt(int x) {
		try {
			return new Color(image.getRGB(x, 1));
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			System.out.println("Palette color requested is out-of-bounds: " + x);
			new Exception().printStackTrace();
			return Color.black;
		}
	}

	public Color getRandomColor() {
		return this.getColor(r.nextInt(modulo));
	};
	
	public abstract void draw(Graphics2D g);

	protected void paintRect(Graphics2D g, Paint paint, int start, int end) {
		g.setPaint(paint);
		g.fillRect(start, 0, end, HEIGHT);
	}

	private void draw2() {
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		draw(g2d);
	}

	protected void paintGradients(Graphics2D g, Color[] colors) {
		int width = WIDTH / (colors.length - 1);

		for (int i = 0; i < (colors.length - 1); i++) {
			Color c1 = colors[i];
			Color c2 = colors[i + 1];
			int x = i * width;
			// Correct for rounding errors by filling out the remaining pixels with the last color
			if (i == colors.length - 2) {
				width = WIDTH - x;
			}
			paintGradient(g, c1, c2, x, width);
		}
	}

	protected void paintGradient(Graphics2D g, Color start, Color end, int x, int width) {
		GradientPaint gradient = new GradientPaint(x, 0, start, x + width, 0, end);
		g.setPaint(gradient);
		g.fillRect(x, 0, width, HEIGHT);
	}

	protected void paintBlock(Graphics2D g, Color color, int x, int width) {
		g.setPaint(color);
		g.fillRect(x, 0, width, HEIGHT);
	}

}
