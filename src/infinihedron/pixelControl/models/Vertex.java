package infinihedron.pixelControl.models;

public class Vertex {
	public final Layer layer;
	public final int longitude;
	public Vertex(Layer layer, int longitude) {
		this.layer = layer;
		this.longitude = longitude;
	}

	public String toString() {
		return "Vertex(" + layer + longitude + ")";
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Vertex)) {
			return false;
		}
		Vertex v = (Vertex) o;
		return v.layer.equals(layer) && v.longitude == longitude;
	}

	public int hashCode() {
		return (layer.ordinal() * 10) + longitude;
	}

}
