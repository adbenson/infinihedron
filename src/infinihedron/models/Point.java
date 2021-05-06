package infinihedron.models;

import processing.core.PVector;

public class Point {
	public final float x;
	public final float y;
	public final float z;

	private final PVector v;

	public Point(PVector v) {
		this(v.x, v.y, v.z); 
	}

	public Point(double d, double e) {
		this(d, e, 0);
	}
	
	public Point(double x, double y, double z) {
		this.v = new PVector((float)x, (float)y, (float)z);
		this.x = (float)x;
		this.y = (float)y; 
		this.z = (float)z;
	}

	public Point add(Point that) {
		return new Point(copy().add(that.v));
	}

	public Point add(float x, float y, float z) {
		return this.add(new Point(x, y, z));
	}

	public Point inverse() {
		return new Point(-v.x, -v.y, -v.z);
	}

	public Point clone() {
		return new Point(this.v);
	}
	
	private PVector copy() {
		return this.v.copy();
	}
}
