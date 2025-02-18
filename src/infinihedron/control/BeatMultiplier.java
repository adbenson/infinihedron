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

	private int skipsPerBeat;
	private volatile int skippedBeatCounter;

	public BeatMultiplier(BeatListener listener) {
		multiplier = 1;
		lastBeat = 0;
		skippedBeatCounter = 0;
		this.listener = listener;
	}

	@Override
	public synchronized void beat(int superInterval) {
		this.lastSuperBeat = System.currentTimeMillis();
		this.superInterval = superInterval;
		resync();
	}

	public synchronized void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
		resync();
	}

	private void resync() {
		if (loop != null) {
			loop.stop();
		}

		if (multiplier > 0) {
			divideBeats();
		} else {
			multiplyBeats();
		}
	}

	private void divideBeats() {
		interval = superInterval / multiplier;
		loop = new BeatLoop(interval, __ -> {
			this.lastBeat = System.currentTimeMillis();
			this.listener.beat(interval);
		});
	}

	private void multiplyBeats() {
		// TODO
		this.listener.beat(superInterval);
	}

	private void sleep(long ms) {
		if (ms <= 0) {
			System.out.println("Negative sleep time: " + ms);
			return;
		}

		try {
			Thread.sleep(Math.max(ms, 0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
