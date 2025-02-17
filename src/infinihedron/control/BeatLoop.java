package infinihedron.control;

public class BeatLoop {

	private final Thread thread;
	private final int interval;
	private final BeatListener listener;

	private volatile boolean isStopped = false;
	private volatile long nextBeat;

	public BeatLoop(int interval, BeatListener listener) {
		this.interval = interval;
		this.listener = listener;
		this.nextBeat = System.currentTimeMillis() + interval;
		this.thread = new Thread(() -> loop());
		this.thread.start();
	}

	public synchronized void stop() {
		if (!this.isStopped) {
			System.out.println("Stopped beat stopped again.");
		}
		this.isStopped = true;
	}

	private void loop() {
		while (!isStopped) {
			listener.beat(interval);
			nextBeat += interval;
			long timeToNextBeat = nextBeat - System.currentTimeMillis();
			sleep(timeToNextBeat);
		}
	}

	private void sleep(long ms) {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			System.out.println("Beat Loop Interrupted");
			e.printStackTrace();
		}
	}

	
}
