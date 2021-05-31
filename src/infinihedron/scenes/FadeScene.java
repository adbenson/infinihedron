package infinihedron.scenes;

import java.awt.Color;

import processing.core.PApplet;

public class FadeScene extends Scene {
	
	public FadeScene(PApplet processing) {
		super(processing, SceneType.Strobe);
	}

	// private boolean state = true;

	private int state = 0;

	@Override
	public void draw(long time, float beatFraction) {
		int mid = (int)(255 * beatFraction);
		Color color = palette.getColor(beatFraction);
		p.fill(color.getRGB());
		p.rect(0, 0, p.height, p.width);
	}
	
	@Override
	public void beat(int interval, long time) {
		super.beat(interval, time);
		// this.state = !this.state;

		state = (state + 1) % 255;
	}

}
