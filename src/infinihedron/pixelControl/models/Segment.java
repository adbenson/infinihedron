package infinihedron.pixelControl.models;

public class Segment {
	public final Vertex start;
	public final Vertex end;
	public final int channel;
	public final int segmentInChannel;

	public Segment(Vertex start, Vertex end, int channel, int segment) {
		this.start = start;
		this.end = end;
		this.channel = channel;
		this.segmentInChannel = segment;
	}
}
