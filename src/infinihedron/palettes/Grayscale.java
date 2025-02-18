package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics2D;


public class Grayscale extends Palette {
	
	public Grayscale() {
		super(256);
	}

	@Override
	public void draw(Graphics2D g) {
		paintGradients(g, new Color[] {
			Color.white, Color.black, Color.white
		});
	}
	
}
