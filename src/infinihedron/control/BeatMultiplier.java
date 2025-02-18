package infinihedron.control;

public class BeatMultiplier implements BeatListener {

	public static final int[] MULTIPLIER_FACTORS = new int[] {
		-8, -6, -4, -3, -2, 1, 2, 3, 4, 6, 8
	};

	private final int LEAST_COMMON_MULTIPLE = 24;

	private int multiplier;

	private int superInterval;
	private long lastSuperBeat;
	private int interval;
	private volatile long lastBeat;

	private BeatListener listener;
		
	private volatile BeatLoop loop;

	private volatile int skippedBeatCounter;

	public BeatMultiplier(BeatListener listener) {
		multiplier = 1;
		lastBeat = 0;
		skippedBeatCounter = -1;
		this.listener = listener;
	}

	@Override
	public synchronized void beat(int superInterval) {
		this.lastSuperBeat = System.currentTimeMillis();
		boolean isIntervalChanged = this.superInterval != superInterval;
		this.superInterval = superInterval;

		if (multiplier > 0) {
			multiplyBeat();
		} else {
			divideBeat(isIntervalChanged);
		}
	}

	public synchronized void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
		if (multiplier > 0) {
			stop();
		} else {
			resetDividedBeat();
		}
	}

	private void multiplyBeat() {
		stop();
		interval = superInterval / multiplier;
		loop = new BeatLoop(interval, __ -> {
			this.lastBeat = System.currentTimeMillis();
			this.triggerBeat();
		});
	}

	private void resetDividedBeat() {
		interval = superInterval * -multiplier;
		skippedBeatCounter = -1;
		this.triggerBeat();
	}

	private void divideBeat(boolean reset) {
		if (reset) {
			resetDividedBeat();
		}
		skippedBeatCounter++;

		if (skippedBeatCounter == -multiplier) {
			this.triggerBeat();
			skippedBeatCounter = 0;
		}
	}

	private void triggerBeat() {
		this.lastBeat = System.currentTimeMillis();
		this.listener.beat(interval);
	}

	private void stop() {
		if (loop != null) {
			loop.stop();
		}
	}
}
