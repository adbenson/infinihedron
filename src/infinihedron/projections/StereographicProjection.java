package infinihedron.projections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import infinihedron.pixelControl.models.Layer;
import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.Segment;
import infinihedron.pixelControl.models.SegmentLine;
import infinihedron.pixelControl.models.Vertex;

public class StereographicProjection extends Projection {

	private static final int pixelsPerEdge = 12;
	private static final int horizontalDivisions = 10;
	private static final int pixelsPerChannel = 64;

	private static final int stereographicRadius = 80;
	
	private final List<SegmentLine> segments;
	private final List<Pixel> pixels;

	public StereographicProjection() {
		List<Segment> baseSegments = loadSegments("stereographicSegmentMap.json");
		segments = StereographicProjection.generateSegmentLines(baseSegments, stereographicRadius, horizontalDivisions);
		pixels = StereographicProjection.generatePixels(segments, pixelsPerEdge, pixelsPerChannel);
	}
	
	@Override
	public List<Pixel> getPixels() {
		return pixels;
	}

	@Override
	public List<SegmentLine> getSegments() {
		return segments;
	}

	private static List<SegmentLine> generateSegmentLines(
		List<Segment> segments,
		int layerRadius,
		int horizontalDivisions
	) {
		List<SegmentLine> lines = new ArrayList<>();
		
		for (Segment segment : segments) {
			Point start = stereographicVertexPoint(segment.start, layerRadius, horizontalDivisions);
			Point end = stereographicVertexPoint(segment.end, layerRadius, horizontalDivisions);
			
			lines.add(new SegmentLine(segment, start, end));
		}
		
		return lines;
	}

	private static List<Pixel> generatePixels(
		List<SegmentLine> segments,
		int pixelsPerEdge,
		int pixelsPerChannel
	) {
		List<Pixel> pixels = new ArrayList<>();
		
		for (SegmentLine segment : segments) {

			int startIndex = (segment.channel * pixelsPerChannel) + (segment.segmentInChannel * pixelsPerEdge);

			List<Pixel> segmentPixels = pixelSegment(segment.startPoint, segment.endPoint, startIndex, pixelsPerEdge);
			
			pixels.addAll(segmentPixels);
		}
		
		return pixels;
	}

	private static List<Segment> loadSegments(String file) {
		try {
			return MapReader.get(file).stream().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
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
