package infinihedron.projections;

import java.util.ArrayList;
import java.util.List;

import infinihedron.pixelControl.models.Layer;
import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.Segment;
import infinihedron.pixelControl.models.Vertex;

public class StereographicProjection extends Projection {

	public static List<Pixel> generatePixels(
		List<Segment> segments,
		int layerRadius,
		int horizontalDivisions,
		int pixelsPerEdge,
		int pixelsPerChannel
	) {
		List<Pixel> pixels = new ArrayList<>();
		
		for (Segment segment : segments) {
			Point start = stereographicVertexPoint(segment.start, layerRadius, horizontalDivisions);
			Point end = stereographicVertexPoint(segment.end, layerRadius, horizontalDivisions);

			int startIndex = (segment.channel * pixelsPerChannel) + (segment.segmentInChannel * pixelsPerEdge);

			List<Pixel> segmentPixels = pixelSegment(start, end, startIndex, pixelsPerEdge);
			
			pixels.addAll(segmentPixels);
		}
		
		return pixels;
	}

	private static Point stereographicVertexPoint(Vertex v, int layerRadius, int divisions) {
		int r = (int)layerRadius(v.layer, layerRadius);
		float angle = (float)(v.longitude * ((Math.PI * 2) / divisions));
		return pointOnCircle(r, -angle);
	}

	private static float layerRadius(Layer layer, int layerRadius) {
		// These values were determined largely experimentally to minimize the difference
		// in percieved edge length. I tried to find a mathematical solution but ugh.
		return 
			(float)(
			layer == Layer.A ? layerRadius :
			layer == Layer.B ? layerRadius * 2 :
			layer == Layer.C ? layerRadius * 2.5 :
			layer == Layer.D ? layerRadius * 3.5 :
			-1
		);
	}

	private static Point pointOnCircle(float r, float angle) {
		angle += Math.PI;
		return new Point(
			r * Math.sin(angle),
			r * Math.cos(angle)
		);
	}

}
