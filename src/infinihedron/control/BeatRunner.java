package infinihedron.control;

import java.util.HashSet;
import java.util.Set;

public class BeatRunner {

	public static final int BPM_TO_MS = 60000;
	public static final int DEFAULT_BPM = 120;
	public static final int DEFAULT_INTERVAL = 60000 / 120;

	private int interval = 0;
	private long prevBeat = 0;
	private float intervalReciprocal = 0;

	private BeatLoop beat;

	private Set<BeatListener> listeners = new HashSet<>();

	public BeatRunner() {
		setInterval(DEFAULT_INTERVAL);
		beatNow();
	}

	public void setInterval(int interval) {
		this.interval = interval;
		update();
	}

	public void beatNow() {
		update();
	}

	public float getBeatFraction() {
		return (System.currentTimeMillis() - prevBeat) * intervalReciprocal;
	}

	private void update() {
		intervalReciprocal = 1.0f / interval;
		prevBeat = System.currentTimeMillis();
		startBeatLoop();
	}

	public void addListener(BeatListener listener) {
		this.listeners.add(listener);
	}

	private synchronized void beat() {
		prevBeat = System.currentTimeMillis();
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
