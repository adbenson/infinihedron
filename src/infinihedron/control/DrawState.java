package infinihedron.control;

public class DrawState {

	public final long time;
	public final float beatFraction;
	// public final int interval;

	public DrawState(long time, float beatFraction) {
		this.time = time;
		this.beatFraction = beatFraction;
		// this.interval = interval;
	}

}
