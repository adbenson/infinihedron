package infinihedron.scenes;

import processing.core.PApplet;

public class StrobeScene extends Scene {
	
	public StrobeScene(PApplet processing) {
		super(processing, SceneType.Strobe);
	}

	private boolean state = true;

	@Override
	public void draw(long time) {
		System.out.println(this.state);
		p.fill(this.state ? 0 : 255);
		p.rect(0, 0, p.height, p.width);
	}
	
	@Override
	public void beat(int interval, long time) {
		super.beat(interval, time);
		
		this.state = !this.state;
	}

}
