package infinihedron;

import infinihedron.control.BeatRunner;
import infinihedron.control.SceneManager;
import infinihedron.palettes.PaletteType;
import infinihedron.pixelControl.PixelController;
import infinihedron.pixelControl.models.Point;
import infinihedron.ui.InfinihedronControlPanel;
import processing.core.PApplet;

public class Infinihedron extends PApplet {

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;

	public static final Point MID_POINT_A = new Point(WIDTH / 4, HEIGHT / 2);
	public static final Point MID_POINT_B = new Point(WIDTH * 3 / 4, HEIGHT / 2);
	public static final Point LIMIT_POINT = new Point(WIDTH / 2, HEIGHT);

	private SceneManager sceneA;
	private SceneManager sceneB;

	private BeatRunner beatRunner;

	private PixelController pixels;

	public static void main(String[] args) {
		// The argument passed to main must match the class name
		PApplet.main("infinihedron.Infinihedron");
	}

	// method used only for setting the size of the window
	public void settings() {
		// Doesn't actually go "fullscreen", but does remove border and title bar.
		fullScreen();
		size(WIDTH, HEIGHT);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setLocation(0, 0);
		surface.setSize(WIDTH, HEIGHT);

		beatRunner = new BeatRunner();

		sceneA = new SceneManager(this);
		sceneB = new SceneManager(this);

		pixels = new PixelController(this, "localhost", sceneA, sceneB);

		beatRunner.addListener(sceneA);
		beatRunner.addListener(sceneB);

		InfinihedronControlPanel.launch(
			sceneA,
			sceneB,
			i -> beatRunner.setInterval(i),
			f -> pixels.setFade(f),
			__ -> quit()
		);
	}

	private void quit() {
		sceneA.setPaletteType(PaletteType.Blank);
		sceneB.setPaletteType(PaletteType.Blank);
		System.exit(0);
	}

	// identical use to draw in Prcessing IDE
	public void draw() {
		noStroke();
		fill(0);
		rect(0, 0, width, height);

		translate(MID_POINT_A.x, MID_POINT_A.y);
		sceneA.getCurrentScene().draw();

		translate(MID_POINT_B.x - MID_POINT_A.x, 0);
		sceneB.getCurrentScene().draw();
	}


}
