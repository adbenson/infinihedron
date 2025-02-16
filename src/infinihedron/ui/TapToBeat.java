package infinihedron.ui;

import java.util.ArrayList;
import java.util.List;

import infinihedron.control.BeatListener;
import infinihedron.control.ChangeListener;

public class TapToBeat {

	private static final int MIN_TAPS = 3;
	private static final int RESET_DELAY = 1500;

	private List<Integer> tapIntervals = new ArrayList<>();
	private long previousTap = 0;
	private BeatListener listener;
	private ChangeListener<TapToBeat> statusChangeListener;

	public TapToBeat(BeatListener listener, ChangeListener<TapToBeat> statusChangeListener) {
		this.listener = listener;
		this.statusChangeListener = statusChangeListener;
	}

	public void tapped(long time) {
		this.statusChangeListener.changed(this, "tapping");
		// If this is the first tap in a new set, there's no interval to calculate
		if (previousTap != 0) {
			tapIntervals.add((int)(time - previousTap));
		}

		previousTap = time;
		startResetTimer(time);

		// The first tap isn't in the list
		if (tapIntervals.size() > (MIN_TAPS - 1)) {
			calculateBeat();
		}
	}

	private void startResetTimer(long time) {
		new Thread(() -> {
			try {
				Thread.sleep(RESET_DELAY);
				// Only run on the most recent click
				if (time == previousTap) {
					reset();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void reset() {
		tapIntervals = new ArrayList<>();
		previousTap = 0;
		statusChangeListener.changed(this, "idle");
	}

	private void calculateBeat() {
		int sum = 0;
		for (int tap : tapIntervals) {
			sum += tap;
		}

		int average = sum / tapIntervals.size();
		// Convert ms to BMP
		int bpm = 60000 / average;

		System.out.println("bpm: " + bpm);

		listener.beat(average);
	}
}
