package infinihedron.pixelControl.models;

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

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Point)) {
			return false;
		}
		Point p = (Point) o;
		return p.x == x && p.y == y && p.z == z;
	}

	public int hashCode() {
		return (int)(x * 1000 + y * 100 + z);
	}

	public String toString() {
		return "Point(" + x + ", " + y + ", " + z + ")";
	}
}
