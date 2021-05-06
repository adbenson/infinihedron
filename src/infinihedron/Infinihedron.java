package infinihedron;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import infinihedron.control.InfinihedronControlWindow;
import infinihedron.models.Pixel;
import infinihedron.models.Segment;
import infinihedron.projections.MapReader;
import infinihedron.projections.StereographicProjection;
import processing.core.PApplet;

public class Infinihedron extends PApplet {
	private static final int pixelsPerEdge = 12;
	private static final int horizontalDivisions = 10;
	private static final int pixelsPerChannel = 64;

	private static final int stereographicRadius = 170;
	
	private List<Pixel> pixels;

	// The argument passed to main must match the class name
	public static void main(String[] args) {
		PApplet.main("infinihedron.Infinihedron");
	}

	// method used only for setting the size of the window
	public void settings() {

		  fullScreen();
		size(1200, 1200);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setLocation(600, 0);
		surface.setSize(1200, 1200);

		try {
			List<Segment> segments = MapReader.get("stereographicSegmentMap.json").stream().collect(Collectors.toList());
			
			pixels = StereographicProjection.generatePixels(
				segments,
				stereographicRadius,
				horizontalDivisions,
				pixelsPerEdge,
				pixelsPerChannel
			);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InfinihedronControlWindow.launch();
	}

	// identical use to draw in Prcessing IDE
	public void draw() {
		translate(600, 600);
		for (Pixel p : pixels) {
			circle(p.x, p.y, 5);
		}
	}
}