package infinihedron.palettes;

import java.awt.Graphics2D;
import java.awt.Color;

public class Aesthetic extends Palette {

	public Aesthetic() {
		super(255);
	}

	@Override
	public void draw(Graphics2D g) {
		paintGradients(g, new Color[] {
			teal, purple, teal
		});
	}
	
}
