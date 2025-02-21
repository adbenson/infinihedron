package infinihedron.pixelControl;

import infinihedron.Infinihedron;
import infinihedron.control.SceneManager;
import processing.core.PApplet;

public class PixelController {

	public static final int BYTES_PER_PIXEL = 3;

	private final OPC opc;
	private float fade = 0.0f;
	private PApplet processing;
	private SceneManager sceneA;
	private SceneManager sceneB;

	// There are 360 "real" pixels but due to gaps in the addressing scheme we need to pad for more
	public static final int PIXEL_COUNT = 472;
	public static final int RIGHT_PIXEL_OFFSET = Infinihedron.WIDTH / 2;

	public PixelController(PApplet processing, String hostName, SceneManager sceneA, SceneManager sceneB) {
		this.processing = processing;
		processing.registerMethod("draw", this);

		opc = new OPC(hostName, 7890);
		opc.setPixelCount(PIXEL_COUNT);

		this.sceneA = sceneA;
		this.sceneB = sceneB;
	}

	public void setFade(float fade) {
		this.fade = fade;
	}

	public void draw() {
		int ledOffset = OPC.FIRST_PIXEL_OFFSET;

		processing.loadPixels();
		int[] displayedPixels = processing.pixels;

		// [r1, g1, b1, r2, g2, b2, ...]
		int[] valuesA = sceneA.getSceneProjection().getPixelValues(displayedPixels, 0);
		int[] valuesB = sceneB.getSceneProjection().getPixelValues(displayedPixels, RIGHT_PIXEL_OFFSET);

		float antiFade = 1.0f - fade;

		for (int i = 0; i < PIXEL_COUNT; i++) {
			int pixelLeft = valuesA[i];
			int pixelRight = valuesB[i];

			if (fade == 0) {
				opc.setPixel(i, pixelLeft);
			} else if (fade == 1) {
				opc.setPixel(i, pixelRight);
			} else {
				byte r = (byte) (((pixelLeft >> 16) & 0xFF) * antiFade + ((pixelRight >> 16) & 0xFF) * fade);
				byte g = (byte) (((pixelLeft >> 8) & 0xFF) * antiFade + ((pixelRight >> 8) & 0xFF) * fade);
				byte b = (byte) ((pixelLeft & 0xFF) * antiFade + (pixelRight & 0xFF) * fade);

				opc.setPixel(ledOffset, r, g, b);
			}

			ledOffset += BYTES_PER_PIXEL;
		}

		opc.writePixels();
		// Shows the position of the pixels on the screen
		processing.updatePixels();
	}

}
