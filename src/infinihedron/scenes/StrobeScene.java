package infinihedron.scenes;

import processing.core.PApplet;

public class StrobeScene extends Scene {
	
	StrobeScene(PApplet processing) {
		super(processing);
		// TODO Auto-generated constructor stub
	}

	private boolean state = true;
	
	public String getName() {
		return "Strobe";
	}
	
	public void draw(long time) {
		
	}
	
	public void beat(int interval, long time) {
		super.beat(interval, time);
		
		this.state = !this.state;
	}

}
