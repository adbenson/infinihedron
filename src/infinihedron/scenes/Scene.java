package infinihedron.scenes;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

import infinihedron.control.BeatListener;
import infinihedron.control.BeatMultiplier;
import infinihedron.control.BeatRunner;
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
	protected int beatInterval = BeatRunner.DEFAULT_INTERVAL;

	protected final Random random;

	protected final Point origin;
	protected final Point limit;
	protected final Point size;

	protected final BeatMultiplier beatMultiplier;
	
	Scene(PApplet processing, SceneType type) {
		this.p = processing;
		this.type = type;
		this.random = new Random();
		
		this.size = new Point(processing.width / 2, processing.height);
		this.limit = new Point(size.x / 2, size.y / 2);
		this.origin = new Point(-limit.x, -limit.y);

		this.lastBeat = System.currentTimeMillis();
		beatMultiplier = new BeatMultiplier(i -> subBeat(i));
	}
	
	public abstract void draw();
	
	/**
	 * Called by the master beat runner
	 * Passes it on to the beat multiplier
	 */
	@Override
	public final void beat(int interval) {
		beatMultiplier.beat(interval);
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

	public void stop() {
		beatMultiplier.stop();
	}

	protected float getBeatFraction() {
		return (float)(System.currentTimeMillis() - lastBeat) / beatInterval;
	}

	protected void setBeatMultiplier(int multiplier) {
		beatMultiplier.setMultiplier(multiplier);
	}

	protected int adjustAlpha(Color hue, float alpha) {
		return p.color(hue.getRed(), hue.getGreen(), hue.getBlue(), alpha * 255);
	}

	protected int randomBetween(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	protected float randomBetween(float min, float max) {
		return random.nextFloat() * (max - min) + min;
	}

	/**
	 * Called by beat multiplier
	 * Calls child class beat method
	 * @param interval
	 */
	private void subBeat(int interval) {
		lastBeat = System.currentTimeMillis();
		beatInterval = interval;
		beat();
	}

}
