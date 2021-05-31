package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;

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
	}
	
}
