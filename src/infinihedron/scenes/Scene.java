package infinihedron.scenes;

import infinihedron.palettes.Palette;
import processing.core.PApplet;

public abstract class Scene {
	
	public final SceneType type;

	protected final PApplet p;
	protected Palette palette;

	protected long lastBeat = 0;
	protected int beatInterval = 0;
	
	Scene(PApplet processing, SceneType type) {
		this.p = processing;
		// Default 60 BPM
		this.beat(1000, 0);
		this.type = type;
	}
	
	public void draw(long time) {};

	public void draw(long time, float beatFraction) {}
	
	public void beat(int interval, long time) {
		this.beatInterval = interval;
		this.lastBeat = time;
	}
	
	public void setPalette(Palette palette) {
		this.palette = palette;
	}

}
