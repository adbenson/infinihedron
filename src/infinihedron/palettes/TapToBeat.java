package infinihedron.palettes;

import java.util.ArrayList;
import java.util.List;

import infinihedron.control.BeatListener;

public class TapToBeat {

	private static final int MIN_TAPS = 3;
	private static final int PAUSE = 1500;

	private List<Integer> taps = new ArrayList<>();
	private long previousTap = 0;
	private BeatListener listener;

	public TapToBeat(BeatListener listener) {
		this.listener = listener;
	}

	public void tapped(long time) {
		if (previousTap != 0) {
			taps.add((int)(time - previousTap));
		}

		previousTap = time;
		startPause(time);
	}

	private void startPause(long time) {
		new Thread(() -> {
			try {
				Thread.sleep(PAUSE);
				check(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void check(long time) {
		if (time != previousTap) {
			return;
		}

		// The first tap isn't in the list
		if (taps.size() < (MIN_TAPS - 1)) {
			reset();
			return;
		}

		calculateBeat();
	}

	private void reset() {
		taps = new ArrayList<>();
		previousTap = 0;
	}

	private void calculateBeat() {
		int sum = 0;
		for (int tap : taps) {
			sum += tap;
		}

		int average = sum / taps.size();
		int bpm = 60000 / average;

		System.out.println("bpm: " + bpm);

		listener.beat(average);

		reset();
	}
}
