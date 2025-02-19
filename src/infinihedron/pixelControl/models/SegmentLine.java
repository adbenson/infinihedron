package infinihedron.pixelControl.models;

public class SegmentLine extends Segment {
	public final Point startPoint;
	public final Point endPoint;

	public SegmentLine(Segment segment, Point startPoint, Point endPoint) {
		super(segment.start, segment.end, segment.channel, segment.segmentInChannel);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
}
