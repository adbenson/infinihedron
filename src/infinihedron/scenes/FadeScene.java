package infinihedron.scenes;

import processing.core.PApplet;

public class FadeScene extends Scene {
	
	public FadeScene(PApplet processing) {
		super(processing, SceneType.Strobe);
	}

	private boolean state = true;

	@Override
	public void draw(long time, float beatFraction) {
		int mid = (int)(255 * beatFraction);
		int color = this.state ? mid : 255 - mid;
		System.out.println("color " + color);
		p.fill(color);
		p.rect(0, 0, p.height, p.width);
	}
	
	@Override
	public void beat(int interval, long time) {
		super.beat(interval, time);
		
		this.state = !this.state;
	}

}
