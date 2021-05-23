package infinihedron;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import infinihedron.control.*;
import infinihedron.models.*;
import infinihedron.projections.*;
import infinihedron.scenes.*;

import processing.core.PApplet;

public class Infinihedron extends PApplet implements ChangeListener<State> {
	private static final int pixelsPerEdge = 12;
	private static final int horizontalDivisions = 10;
	private static final int pixelsPerChannel = 64;

	private static final int stereographicRadius = 170;
	
	private Map<SceneType, Scene> scenes = new EnumMap<>(SceneType.class);

	private Scene scene;
	
	private List<Pixel> pixels;

	private StateManager stateManager;

	private long nextBeat = 0;
	private int beatInterval = 1000;

	public static void main(String[] args) {
		// The argument passed to main must match the class name
		PApplet.main("infinihedron.Infinihedron");
	}

	// method used only for setting the size of the window
	public void settings() {
		// Doesn't actually go "fullscreen", but does remove border and title bar.
		fullScreen();
		size(1200, 1200);
	}

	// identical use to setup in Processing IDE except for size()
	public void setup() {
		surface.setLocation(600, 0);
		surface.setSize(1200, 1200);

		scenes.put(SceneType.Blank, new BlankScene(this));
		scenes.put(SceneType.Strobe, new StrobeScene(this));

		stateManager = StateManager.getInstance();
		// state = stateManager.getCurrent();
		scene = scenes.get(stateManager.getCurrent().getSceneA().getType());

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

		stateManager.addChangeListener(this);
	}

	// identical use to draw in Prcessing IDE
	public void draw() {
		long time = millis();

		if (time > nextBeat) {
			nextBeat = time + beatInterval;
			scene.beat(beatInterval, time);
		}

		scene.draw(time);

		translate(600, 600);
		for (Pixel p : pixels) {
			circle(p.x, p.y, 5);
		}
	}

	@Override
	public void changed(State state, String propertyName) {
		System.out.println("State change: " + propertyName);
		if (propertyName.equals("sceneA") || propertyName.equals("sceneA.type")) {
			SceneType type = state.getSceneA().getType();
			System.out.println("Scene changed: " + type);
			scene = scenes.get(type);
		}

		if (propertyName.equals("sceneA.bpm")) {
			int bpm = state.getSceneA().getBpm();
			beatInterval = (int)((60.0 / bpm) * 1000);
		}
	}
}
