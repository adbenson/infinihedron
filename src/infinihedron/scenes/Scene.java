package infinihedron.scenes;

import java.util.Random;

import javax.swing.JPanel;

import infinihedron.control.BeatRunner;
import infinihedron.control.SceneType;
import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import processing.core.PApplet;

public abstract class Scene {
	
	public final SceneType type;

	protected final PApplet p;
	protected Palette palette = PaletteManager.getInstance().get(PaletteType.Blank);

	protected long lastBeat = 0;
	protected int beatInterval = BeatRunner.DEFAULT_BPM * 60000;

	protected Random random;
	
	Scene(PApplet processing, SceneType type) {
		this.p = processing;
		this.type = type;
		this.random = new Random();
	}
	
	public void draw(long time) {};

	public void draw(long time, float beatFraction) {}
	
	public void beat(int interval, long time) {
		this.beatInterval = interval;
		this.lastBeat = time;
		beat();
	}

	public void beat() {
	}
	
	public void setPalette(Palette palette) {
		this.palette = palette;
	}

	public JPanel getControls() {
		return new JPanel();
	}

}
