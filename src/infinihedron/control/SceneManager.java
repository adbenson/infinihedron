package infinihedron.control;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import infinihedron.palettes.PaletteType;
import infinihedron.scenes.Scene;
import processing.core.PApplet;

public class SceneManager implements BeatListener {

	private HashMap<SceneType, Scene> scenes;
	
	private SceneType sceneType;

	private PaletteType paletteType;

	private MultipliedBeatRunner beatRunner;
	
	public SceneManager(PApplet processing) {
		scenes = new HashMap<SceneType, Scene>();
		instantiateScenes(processing);
		sceneType = SceneType.Fade;
		paletteType = PaletteType.Rainbow;
	}

	private void instantiateScenes(PApplet processing) {
		for (SceneType type : SceneType.values()) {
			try {
				Scene scene = instantiateScene(type, processing);
				scene.setPaletteType(paletteType);
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
		if (!scenes.containsKey(sceneType)) {
			Class<? extends Scene> clazz = sceneType.clazz;
			try {
				Constructor<? extends Scene> ctor = clazz.getConstructor(new Class[] { PApplet.class });
				Scene instance = ctor.newInstance(this);
				scenes.put(sceneType, instance);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return scenes.get(sceneType);
	}
	
	public void setSceneType(SceneType type) {
		this.sceneType = type;
	}

	public void setPaletteType(PaletteType type) {
		this.paletteType = type;
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
