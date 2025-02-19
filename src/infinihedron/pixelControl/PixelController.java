package infinihedron.pixelControl;

import java.util.List;

import infinihedron.pixelControl.models.Pixel;
import infinihedron.pixelControl.models.Point;
import processing.core.PApplet;

public class PixelController {

	private final OPC opc;

	public PixelController(PApplet processing, String hostName, List<Pixel> pixels) {

		opc = new OPC(processing, hostName, 7890);

		opc.setPixelCount(512);
		Point max = new Point(processing.width / 2, processing.height);
		Point mid = new Point(processing.width / 4, processing.height / 2);

		for (Pixel p : pixels) {
			Point real = p.add(mid);
			if (real.x > max.x || real.y > max.y || real.x < 0 || real.y < 0) {
				throw new RuntimeException("Pixel off canvas: " + real.x + ", " + real.y);
			}
			opc.led(p.index, (int)real.x, (int)real.y);
		}
	}

	public void setFade(float fade) {
		this.opc.setFade(fade);
	}

}
