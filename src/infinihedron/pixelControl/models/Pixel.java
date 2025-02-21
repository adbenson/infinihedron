package infinihedron.pixelControl.models;

public class Pixel extends Point {
	public final int outputIndex;
	public final int positionIndex;

	public Pixel(Point p, int oi, int pi) {
		super(p.x, p.y, p.z);
		this.outputIndex = oi;
		this.positionIndex = pi;
	}
}
