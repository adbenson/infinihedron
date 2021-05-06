package infinihedron.scenes;

import processing.core.PApplet;

public class BlankScene extends Scene {

	BlankScene(PApplet processing) {
		super(processing);
	}

	@Override
	public String getName() {
		return "Blank";
	}
	
	public void draw(long time) {
		p.fill(0);
		p.rect(0, 0, p.height, p.width);
	}

}
