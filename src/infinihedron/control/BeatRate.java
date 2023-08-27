package infinihedron.control;

import java.util.HashSet;
import java.util.Set;

import processing.core.PApplet;

public class BeatRate {

	public static final int MIN_BPM = 60;
	public static final int MAX_BPM = 220;
	public static final int DEFAULT_BPM = 120;

	public static final int[] MULTIPLIER_FACTORS = new int[] {
		-8, -6, -4, -3, -2, 1, 2, 3, 4, 6, 8
	};
	public static final int DEFAULT_MULTIPLIER = 5;

	private PApplet processing;

	private int bpm = DEFAULT_BPM;
	private int interval = 0;
	// private long nextBeat = 0;
	private long prevBeat = 0;
	private int multiplier = DEFAULT_MULTIPLIER;
	private float intervalReciprocal = 0;

	private BeatLoop beat;

	private Set<BeatListener> listeners = new HashSet<>();

	public BeatRate(PApplet processing) {
		this.processing = processing;
		updateBpm(DEFAULT_BPM);
		beatNow();
	}

	public void updateBpm(int bpm) {
		this.bpm = bpm;
		update();
	}

	public void updateMultiplier(int multiplier) {
		this.multiplier = multiplier;
		update();
	}

	public void beatNow() {
		update();
	}

	public float getBeatFraction() {
		return (processing.millis() - prevBeat) * intervalReciprocal;
	}

	private void update() {
		int baseInterval = 60000 / bpm;
		interval = getMultipliedInterval(baseInterval, multiplier);
		intervalReciprocal = 1.0f / interval;
		prevBeat = processing.millis();
		// nextBeat = prevBeat + interval;
		startBeatLoop();
	}

	private static int getMultipliedInterval(int interval, int multiplier) {
		int factor = MULTIPLIER_FACTORS[multiplier];
		if (factor >= 0) {
			return interval / factor;
		} else {
			return interval * -factor;
		}
	}

	public void listen(BeatListener listener) {
		this.listeners.add(listener);
	}

	private synchronized void beat() {
		prevBeat = processing.millis();
		// nextBeat = prevBeat + interval;
		for (BeatListener l : listeners) {
			l.beat(interval);
		}
	}

	private synchronized void startBeatLoop() {
		if (beat != null) {
			beat.stop();
		}

		beat = new BeatLoop(interval, __ -> beat());
	}

}
