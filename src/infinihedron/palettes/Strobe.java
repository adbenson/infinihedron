package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Strobe extends Palette {

	public Strobe() {
		super(10);
	}

	@Override
	public Color getColor(float value) {
		return (value > 0.9) ? Color.white : Color.black;
	}

	@Override
	public Color getColor(int value) {
		return (value % modulo) == 0 ? Color.white : Color.black;
	}

	@Override
	public void draw(Graphics2D g) {
		int line = (int)(WIDTH * 0.9);
		paintBlock(g, Color.black, 0, line);
		paintBlock(g, Color.white, line, 255 - line);
	}
	
}
