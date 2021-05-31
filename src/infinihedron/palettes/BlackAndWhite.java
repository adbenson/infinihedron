package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;

public class BlackAndWhite extends Palette {

	public BlackAndWhite() {
		super(2);
	}

	@Override
	public Color getColor(float value) {
		return (value < 0.5) ? Color.white : Color.black;
	}

	@Override
	public Color getColor(int value) {
		return (value % modulo) == 0 ? Color.white : Color.black;
	}

	@Override
	public void draw(Graphics2D g) {
		paintBlock(g, Color.black, 0, WIDTH / 2);
		paintBlock(g, Color.white, WIDTH / 2, WIDTH / 2);
	}
	
}
