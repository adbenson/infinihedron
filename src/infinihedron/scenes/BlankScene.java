package infinihedron.scenes;

import processing.core.PApplet;

public class BlankScene extends Scene {

	public BlankScene(PApplet processing) {
		super(processing, SceneType.Blank);
	}
	
	public void draw(long time) {
		p.fill(0);
		p.rect(0, 0, p.height, p.width);
	}

}
