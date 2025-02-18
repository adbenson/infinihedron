package infinihedron.scenes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import infinihedron.control.SceneType;
import processing.core.PApplet;

public class RingsScene extends Scene {

	private final int maxRadius;

	private long nextRingTime;
	private List<Ring> rings = new ArrayList<>();

	private Map<Ring, Boolean> toChange = new HashMap<>();

	public RingsScene(PApplet processing) {
		super(processing, SceneType.Rings);
		maxRadius = (int)(p.height * 1.5);
		nextRingTime = 0;
	}

	@Override
	public synchronized void draw() {
		long time = System.currentTimeMillis();
		if (time > nextRingTime) {
			spawnRing(time);
			nextRingTime = time + beatInterval;
		}

		p.noFill();

		for (Ring ring : rings) {
			if (ring.isDone(time)) {
				addOrRemoveRing(ring, false);
			} else {
				ring.draw(time);
			}
		}

		commitChanges();
	}

	@Override
	public void beat() {
		spawnRing(lastBeat);
	}

	void spawnRing(long time) {
		int duration = beatInterval * 3;
		Ring ring = new Ring(time, duration, maxRadius);
		addOrRemoveRing(ring, true);
	}

	void end(Ring ring) {
		addOrRemoveRing(ring, false);
	}

	private synchronized void addOrRemoveRing(Ring ring, boolean add) {
		toChange.put(ring, add);
	}

	private synchronized void commitChanges() {
		for (Map.Entry<Ring, Boolean> change : toChange.entrySet()) {
			if (change.getValue()) {
				rings.add(change.getKey());
			} else {
				rings.remove(change.getKey());
			}
		}

		toChange = new HashMap<>();
	}
	
	class Ring {
		private static final int bands = 20;
		private static final int ringWidth = 30;

		private Color hue;

		private long startTime;
		private long endTime;
		private int duration;

		private final int maxRadius;

		Ring(long start, int duration, int radius) {
			startTime = start;
			endTime = start + duration;
			this.duration = duration;
			maxRadius = radius;

			hue = palette.getRandomColor();
		}

		void draw(long time) {
			p.strokeWeight(ringWidth);

			long runTime = time - startTime;
			float timeFactor = (float)runTime / duration;

			int radius = (int)(maxRadius * timeFactor);

			for (int n = 0; n < bands; n++) {
				int r = radius - (n * ringWidth);
				if (r > 0) {
					float brightness = (float)(bands - n) / bands;

					int c = p.color(hue.getRed(), hue.getGreen(), hue.getBlue(), brightness * 255);
					p.stroke(c);
					p.circle(0, 0, r);
				}
			}
		}

		boolean isDone(long time) {
			return time > endTime;
		}
	}

}
