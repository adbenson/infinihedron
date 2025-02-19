package infinihedron.projections;

import java.util.ArrayList;
import java.util.List;

import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.SegmentLine;

public abstract class Projection {

	public abstract List<Pixel> getPixels();

	public abstract List<SegmentLine> getSegments();

	protected static List<Pixel> pixelSegment(Point start, Point end, int startIndex, int pixelsPerEdge) {
		List<Pixel> pixels = new ArrayList<>(); 
		
		List<Point> points = pointLine(start, end, pixelsPerEdge);
		
		for (int i=0; i<points.size(); i++) {
			pixels.add(new Pixel(points.get(i), startIndex + i));
		}
		
		return pixels;
	}

	protected static List<Point> pointLine(Point start, Point end, int n) {
		List<Point> points = new ArrayList<>();
		
		for(int i=0; i<n; i++) {
			points.add(nthPoint(start, end, n, i));
		}
		
		return points;
	}

	protected static Point nthPoint(Point start, Point end, int m, int n) {
		float numerator = (2f * n) + 1;
		float divisor = 2f * m;
		return pointOnLine(start, end, numerator / divisor);
	}

	public static Point pointOnLine(Point start, Point end, float distance) {
		float xOffset = distance * (end.x - start.x);
		float yOffset = distance * (end.y - start.y);
		float zOffset = distance * (end.z - start.z);
		return new Point(start.x + xOffset, start.y + yOffset, start.z + zOffset);
	}

}
