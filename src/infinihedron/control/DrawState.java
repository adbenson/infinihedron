package infinihedron.control;

import infinihedron.palettes.Palette;

public class DrawState {
	public final long time;
	public final float beatFraction;
	public final Palette palette;

	public DrawState(long time, float beatFraction, Palette palette) {
		this.time = time;
		this.beatFraction = beatFraction;
		this.palette = palette;
	}
}
