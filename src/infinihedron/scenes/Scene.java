package infinihedron.scenes;

import infinihedron.palettes.Palette;
import processing.core.PApplet;

public abstract class Scene {
	
	protected final PApplet p;
	protected Palette palette;
	protected int interval;
	protected long lastBeat = 0;
	
	Scene(PApplet processing) {
		this.p = processing;
		// Default 60 BPM
		this.beat(1000, 0);
	}

	public abstract String getName();
	
	public abstract void draw(long time);
	
	public void beat(int interval, long time) {
		this.interval = interval;
		this.lastBeat = time;
	}
	
	public void setPalette(Palette palette) {
		this.palette = palette;
	}

}
