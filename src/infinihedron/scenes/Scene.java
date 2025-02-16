package infinihedron.scenes;

import java.util.Random;

import javax.swing.JPanel;

import infinihedron.control.BeatRunner;
import infinihedron.control.DrawState;
import infinihedron.control.SceneType;
import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.pixelControl.models.Point;
import processing.core.PApplet;

public abstract class Scene {
	private final PaletteManager paletteManager = PaletteManager.getInstance();
	
	public final SceneType type;

	protected final PApplet p;
	protected Palette palette = paletteManager.get(PaletteType.Blank);

	protected long lastBeat = 0;
	protected int beatInterval = BeatRunner.DEFAULT_BPM * 60000;

	protected Random random;

	protected Point origin;
	protected Point limit;
	
	Scene(PApplet processing, SceneType type) {
		this.p = processing;
		this.type = type;
		this.random = new Random();
		this.limit = new Point(processing.width / 2, processing.height);
		this.origin = new Point(-processing.width / 4, -processing.height / 2);		
	}
	
	public void draw(DrawState drawState) {};
	
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

	public void setPaletteType(PaletteType paletteType) {
		setPalette(paletteManager.get(paletteType));
	}

}
