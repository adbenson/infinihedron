package infinihedron.control;

public class MultipliedBeatRunner implements BeatListener {

	private int multiplier;
	private int superInterval;
	private int interval;
	private BeatListener listener;
	private long lastBeat;
	private BeatLoop loop;

	public MultipliedBeatRunner(BeatListener listener) {
		multiplier = 1;
		interval = 0;
		lastBeat = 0;
		this.listener = listener;
	}

	@Override
	public void beat(int superInterval) {
		listener.beat(interval);
		// this.lastBeat = System.currentTimeMillis();
		// this.superInterval = superInterval;
		// restart();
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
		restart();
	}

	private int getMultipliedInterval(int superInterval) {
		if (multiplier > 0) {
			return superInterval / multiplier;
		} else {
			return superInterval * -multiplier;
		}
	}

	private void restart() {
		if (loop != null) {
			loop.stop();
		}

		this.interval = getMultipliedInterval(superInterval);

		if (lastBeat == 0) {
			lastBeat = System.currentTimeMillis();
		}

		int timeSinceLastBeat = (int)(System.currentTimeMillis() - lastBeat);

		// Resynchronize the beats
		int timeToNextBeat = superInterval < interval ? 
			timeSinceLastBeat % interval :
			interval - timeSinceLastBeat;

		new Thread(() -> {
			try {
				Thread.sleep(timeToNextBeat);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.listener.beat(interval);
			loop = new BeatLoop(interval, __ -> this.listener.beat(interval));
		}).start();
	}

}
