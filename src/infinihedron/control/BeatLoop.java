package infinihedron.control;

public class BeatLoop {

	private final Thread thread;
	private final int interval;
	private final BeatListener listener;
	private volatile boolean stop = false;

	public BeatLoop(int interval, BeatListener listener) {
		this.interval = interval;
		this.listener = listener;
		this.thread = new Thread(() -> loop());
		this.thread.start();
	}

	public synchronized void stop() {
		if (!this.stop) {
			System.out.println("Stopped beat stopped again.");
		}
		this.stop = true;
	}

	private void loop() {
		while (!stop) {
			listener.beat(interval);
			sleep(interval);
		}
	}

	private void sleep(int ms) {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			System.out.println("Beat Loop Interrupted");
			e.printStackTrace();
		}
	}

	
}
