package infinihedron;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import OPC.OPC;

import infinihedron.control.*;
import infinihedron.models.*;
import infinihedron.palettes.PaletteManager;
import infinihedron.projections.*;
import infinihedron.scenes.*;

import processing.core.PApplet;

public class Infinihedron extends PApplet implements ChangeListener<State> {

	public static final String DEFAULT_HOST = "localhost";

	private static final int pixelsPerEdge = 12;
	private static final int horizontalDivisions = 10;
	private static final int pixelsPerChannel = 64;

	private static final int stereographicRadius = 100;

	private OPC opc;
	
	private Map<SceneType, Scene> scenes = new EnumMap<>(SceneType.class);

	private Scene scene;
	
	private List<Pixel> pixels;

	private StateManager stateManager;
	private State state;

	private BeatRate beatA = new BeatRate(this);

	private Point mid;

	private PaletteManager palettes = PaletteManager.getInstance();

	public static void main(String[] args) {

		// The argument passed to main must match the class name
		PApplet.main("infinihedron.Infinihedron");
	}

	// method used only for setting the size of the window
	public void settings() {
		// Doesn't actually go "fullscreen", but does remove border and title bar.
		fullScreen();
		size(800, 800);
		mid = new Point(400, 400);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setLocation(400, 0);
		surface.setSize(800, 800);

		scenes.put(SceneType.Blank, new BlankScene(this));
		scenes.put(SceneType.Strobe, new StrobeScene(this));
		scenes.put(SceneType.Fade, new FadeScene(this));

		stateManager = StateManager.getInstance();
		state = stateManager.getCurrent();

		scene = scenes.get(state.getSceneA().getType());
		scene.setPalette(palettes.get(state.getSceneA().getPalette()));

		List<Segment> segments = loadSegments("stereographicSegmentMap.json");
		pixels = getPixels(segments);

		opc = new OPC(this, state.getOpcHostName(), 7890);

		opc.setPixelCount(512);
		for (Pixel p : pixels) {
			Point real = p.add(mid);
			//System.out.println(p.index + "\t" + real.x + "\t" + real.y);
			opc.led(p.index, (int)real.x, (int)real.y);
		}
		
		InfinihedronControlWindow.launch();

		stateManager.addChangeListener(this);

		beatA.listen(interval -> this.scene.beat(interval, millis()));
	}

	// identical use to draw in Prcessing IDE
	public void draw() {
		boolean connected = opc.isConnected();
		if (connected != state.getIsOpcConnected()) {
			state.setIsOpcConnected(connected);
		}

		long time = millis();

		scene.draw(time);
		scene.draw(time, beatA.getBeatFraction());

		translate(mid.x, mid.y);
		for (Pixel p : pixels) {
			circle(p.x, p.y, 5);
		}
	}

	@Override
	public void changed(State state, String propertyName) {
		System.out.println("State change: " + propertyName);
		if (propertyName.equals("sceneA") || propertyName.equals("sceneA.type")) {
			SceneType type = state.getSceneA().getType();
			scene = scenes.get(type);
			scene.setPalette(palettes.get(state.getSceneA().getPalette()));
		}

		if (propertyName.equals("bpm")) {
			int bpm = state.getBpm();
			beatA.updateBpm(bpm);
		}

		if (propertyName.equals("sceneA.multiplier")) {
			int multiplier = state.getSceneA().getMultiplier();
			beatA.updateMultiplier(multiplier);
		}

		if (propertyName.equals("opcHostName")) {
			opc.dispose();
			opc = new OPC(this, state.getOpcHostName(), 7890);
		}

		if (propertyName.equals("sceneA.palette")) {
			scene.setPalette(palettes.get(state.getSceneA().getPalette()));
		}
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
