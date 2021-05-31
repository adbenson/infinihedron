package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

public class RainbowPalette extends Palette {

	private final static int modulo = 255;

	public RainbowPalette() {
		super(modulo);
	}

	@Override
	public void draw(Graphics2D g) {
		Color[] colors = new Color[] {
			Color.red, Color.orange, Color.yellow, Color.green, Color.blue, purple, Color.red
		};

		paintGradients(g, colors);

		int x = WIDTH / modulo;
		// paintGradient(g, Color.red, Color.orange, 0, x);
		// paintGradient(g, Color.orange, Color.yellow, x, x);
		// paintGradient(g, Color.yellow, Color.green, 2 * x, x);
		// paintGradient(g, Color.green, Color.blue, 3 * x, x);
		// paintGradient(g, Color.blue, purple, 4 * x, x);
		// paintGradient(g, purple, Color.red, 5 * x, x);
		
	}

	private void draw2(Graphics2D g, Color[] colors) {
		int x = WIDTH / modulo;
	}
	
}
