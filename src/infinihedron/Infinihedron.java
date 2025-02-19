package infinihedron;

import java.awt.Point;

import infinihedron.control.BeatRunner;
import infinihedron.control.SceneManager;
import infinihedron.palettes.PaletteType;
import infinihedron.pixelControl.PixelController;
import infinihedron.projections.Projection;
import infinihedron.projections.StereographicProjection;
import infinihedron.ui.InfinihedronControlPanel;
import processing.core.PApplet;

public class Infinihedron extends PApplet {

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;

	public static final Point midPointA = new Point(WIDTH / 4, HEIGHT / 2);
	public static final Point midPointB = new Point(WIDTH * 3 / 4, HEIGHT / 2);

	private SceneManager sceneA;
	private SceneManager sceneB;

	private BeatRunner beatRunner;

	private PixelController pixels;

	private Projection projection;

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

		projection = new StereographicProjection();

		pixels = new PixelController(this, "localhost", projection.getPixels());

		sceneA = new SceneManager(this);
		sceneB = new SceneManager(this);

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

	public Projection getProjection() {
		return projection;
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

		translate(midPointA.x, midPointA.y);
		sceneA.getCurrentScene().draw();

		translate(midPointB.x - midPointA.x, 0);
		sceneB.getCurrentScene().draw();
	}


}
