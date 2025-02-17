package infinihedron.pixelControl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.Segment;
import infinihedron.projections.MapReader;
import infinihedron.projections.StereographicProjection;
import processing.core.PApplet;

public class PixelController {

	private static final int pixelsPerEdge = 12;
	private static final int horizontalDivisions = 10;
	private static final int pixelsPerChannel = 64;

	private static final int stereographicRadius = 80;

	private OPC opc;

	private List<Pixel> pixels;

	public PixelController(PApplet processing, String hostName) {
		List<Segment> segments = loadSegments("stereographicSegmentMap.json");
		pixels = getPixels(segments);

		opc = new OPC(processing, hostName, 7890);

		opc.setPixelCount(512);
		Point max = new Point(processing.width / 2, processing.height);
		Point mid = new Point(processing.width / 4, processing.height / 2);

		for (Pixel p : pixels) {
			Point real = p.add(mid);
			System.out.println(p.index + "\t" + real.x + "\t" + real.y);
			if (real.x > max.x || real.y > max.y || real.x < 0 || real.y < 0) {
				throw new RuntimeException("Pixel off canvas: " + real.x + ", " + real.y);
			}
			opc.led(p.index, (int)real.x, (int)real.y);
		}
	}

	public void setFade(float fade) {
		this.opc.setFade(fade);
	}

	private List<Pixel> getPixels(List<Segment> segments) {
		return StereographicProjection.generatePixels(
			segments,
			stereographicRadius,
			horizontalDivisions,
			pixelsPerEdge,
			pixelsPerChannel
		);
	}

	private List<Segment> loadSegments(String file) {
		try {
			return MapReader.get(file).stream().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
