package infinihedron.projections;

import java.util.ArrayList;
import java.util.List;

import infinihedron.Infinihedron;
import infinihedron.pixelControl.PixelController;
import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.SegmentLine;

public abstract class Projection {

	public abstract List<Pixel> getPixels();

	public abstract List<SegmentLine> getSegments();

	protected final int[] pixelValues = new int[PixelController.PIXEL_COUNT];

	public abstract int[] getPixelValues(int[] displayedPixels, int offset);

	protected Pixel generatePixel(Point p, int outputIndex) {
		Point uncentered = p.add(Infinihedron.MID_POINT_A);
		if (uncentered.x < 0 || uncentered.y < 0 || uncentered.x > Infinihedron.LIMIT_POINT.x || uncentered.y > Infinihedron.LIMIT_POINT.y) {
			throw new RuntimeException("Pixel off canvas: " + uncentered.x + ", " + uncentered.y);
		}
		
		int positionIndex = (int)uncentered.x + Infinihedron.WIDTH * (int)uncentered.y;
		return new Pixel(p, outputIndex, positionIndex);
	}

	protected List<Pixel> pixelSegment(Point start, Point end, int startIndex, int pixelsPerEdge) {
		List<Pixel> pixels = new ArrayList<>(); 
		
		List<Point> points = pointLine(start, end, pixelsPerEdge);
		
		for (int i=0; i<points.size(); i++) {
			Point p = points.get(i);
			pixels.add(generatePixel(p, startIndex + i));
		}
		
		return pixels;
	}

	protected List<Point> pointLine(Point start, Point end, int n) {
		List<Point> points = new ArrayList<>();
		
		for(int i=0; i<n; i++) {
			points.add(nthPoint(start, end, n, i));
		}
		
		return points;
	}

	protected Point nthPoint(Point start, Point end, int m, int n) {
		float numerator = (2f * n) + 1;
		float divisor = 2f * m;
		return pointOnLine(start, end, numerator / divisor);
	}

	public Point pointOnLine(Point start, Point end, float distance) {
		float xOffset = distance * (end.x - start.x);
		float yOffset = distance * (end.y - start.y);
		float zOffset = distance * (end.z - start.z);
		return new Point(start.x + xOffset, start.y + yOffset, start.z + zOffset);
	}

}
