package infinihedron.control;

import java.util.HashSet;
import java.util.Set;

public class BeatRunner {

	public static final int MIN_BPM = 60;
	public static final int MAX_BPM = 220;
	public static final int DEFAULT_BPM = 120;

	private int bpm = DEFAULT_BPM;
	private int interval = 0;
	private long prevBeat = 0;
	private float intervalReciprocal = 0;

	private BeatLoop beat;

	private Set<BeatListener> listeners = new HashSet<>();

	public BeatRunner() {
		setBpm(DEFAULT_BPM);
		beatNow();
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
		update();
	}

	public void beatNow() {
		update();
	}

	public float getBeatFraction() {
		return (System.currentTimeMillis() - prevBeat) * intervalReciprocal;
	}

	private void update() {
		interval = 60000 / bpm;
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
