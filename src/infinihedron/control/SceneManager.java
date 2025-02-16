package infinihedron.control;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.scenes.Scene;
import processing.core.PApplet;

public class SceneManager implements BeatListener {

	public static final SceneType defaultSceneType = SceneType.Fade;
	public static final PaletteType defaultPaletteType = PaletteType.Rainbow;
	
	private final PaletteManager paletteManager = PaletteManager.getInstance();

	private HashMap<SceneType, Scene> scenes;
	
	private Scene scene;
	private Palette palette;

	private MultipliedBeatRunner beatRunner;
	
	public SceneManager(PApplet processing) {
		scenes = new HashMap<SceneType, Scene>();
		instantiateScenes(processing);
		setSceneType(defaultSceneType);
		setPaletteType(defaultPaletteType);
		beatRunner = new MultipliedBeatRunner(interval -> {
			getCurrentScene().beat(interval, System.currentTimeMillis());
		});
	}

	private void instantiateScenes(PApplet processing) {
		for (SceneType type : SceneType.values()) {
			try {
				Scene scene = instantiateScene(type, processing);
				scene.setPalette(palette);
				scenes.put(type, scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Scene instantiateScene(SceneType type, PApplet processing) throws Exception {
		Class<? extends Scene> clazz = type.clazz;
		Constructor<? extends Scene> ctor = clazz.getConstructor(new Class[] { PApplet.class });
		Scene instance = ctor.newInstance(processing);
		return instance;
	}
	
	public Scene getCurrentScene() {
		return scene;
	}
	
	public void setSceneType(SceneType type) {
		this.scene = scenes.get(type);
	}

	public void setPaletteType(PaletteType type) {
		this.palette = paletteManager.get(type);
		this.scene.setPalette(palette);
	}

	public Scene getScene(SceneType type) {
		return scenes.get(type);
	}

	public void setMultiplier(int multiplier) {
		beatRunner.setMultiplier(multiplier);
	}

	@Override
	public void beat(int interval) {
		beatRunner.beat(interval);
	}
	
}
