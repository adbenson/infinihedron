package infinihedron.pixelControl.models;

public class Segment {
	public final Vertex start;
	public final Vertex end;
	public final int channel;
	public final int segmentInChannel;

	public Segment(Layer startLayer, int startLongitude, Layer endLayer, int endLongitude, int channel, int segment) {
		this.start = new Vertex(startLayer, startLongitude);
		this.end = new Vertex(endLayer, endLongitude);
		this.channel = channel;
		this.segmentInChannel = segment;
	}

	public Segment(Vertex start, Vertex end, int channel, int segment) {
		this.start = start;
		this.end = end;
		this.channel = channel;
		this.segmentInChannel = segment;
	}
}
