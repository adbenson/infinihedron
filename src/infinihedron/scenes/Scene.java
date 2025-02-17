package infinihedron.scenes;

import java.util.Random;

import javax.swing.JPanel;

import infinihedron.control.BeatListener;
import infinihedron.control.BeatRunner;
import infinihedron.control.DrawState;
import infinihedron.control.SceneType;
import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.pixelControl.models.Point;
import processing.core.PApplet;

public abstract class Scene implements BeatListener {
	private final PaletteManager paletteManager = PaletteManager.getInstance();
	
	public final SceneType type;

	protected final PApplet p;
	protected Palette palette = paletteManager.get(PaletteType.Blank);

	protected long lastBeat = 0;
	protected int superInterval = BeatRunner.DEFAULT_INTERVAL;
	protected int beatInterval = BeatRunner.DEFAULT_INTERVAL;

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
	
	public abstract void draw(DrawState drawState);
	
	@Override
	public final void beat(int interval) {
		this.beatInterval = interval;
		this.lastBeat = System.currentTimeMillis();
		beat();
	}

	public void beat() {}
	
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
