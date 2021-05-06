package infinihedron.models;

public class Pixel extends Point {
	public final int index;

	public Pixel(Point p, int i) {
		super(p.x, p.y, p.z);
		this.index = i;
	}
}