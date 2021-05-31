package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;

public class Blank extends Palette {

	public Blank() {
		super(1);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
}
