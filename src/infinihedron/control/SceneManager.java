package infinihedron.control;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import infinihedron.Infinihedron;
import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.projections.Projection;
import infinihedron.projections.ProjectionManager;
import infinihedron.scenes.Scene;

public class SceneManager implements BeatListener {
	
	private final PaletteManager paletteManager = PaletteManager.getInstance();
	private final ProjectionManager projectionManager;

	private HashMap<SceneType, Scene> scenes;
	
	private Scene scene;
	private SceneType sceneType;
	private Palette palette;
	
	public SceneManager(Infinihedron infinihedron) {
		projectionManager = new ProjectionManager();
		scenes = new HashMap<SceneType, Scene>();
		instantiateScenes(infinihedron);
		setSceneType(SceneType.DEFAULT_SCENE);
		setPaletteType(PaletteType.DEFAULT_PALETTE);
	}

	private void instantiateScenes(Infinihedron infinihedron) {
		for (SceneType type : SceneType.values()) {
			try {
				Scene scene = instantiateScene(type, infinihedron);
				scene.setPalette(palette);
				scenes.put(type, scene);
			} catch (Exception e) {
				System.out.println("Failed to instantiate scene: " + type);
				e.printStackTrace();
			}
		}
	}

	private Scene instantiateScene(SceneType type, Infinihedron infinihedron) throws Exception {
		Projection projection = projectionManager.get(type.projection);
		Class<? extends Scene> clazz = type.clazz;
		Constructor<? extends Scene> ctor = clazz.getConstructor(new Class[] { Infinihedron.class, Projection.class });
		Scene instance = ctor.newInstance(infinihedron, projection);
		return instance;
	}
	
	public Scene getCurrentScene() {
		return scene;
	}

	public Projection getSceneProjection() {
		return projectionManager.get(sceneType.projection);
	}
	
	public void setSceneType(SceneType type) {
		if (this.scene != null) {
			this.scene.stop();
		}
		this.sceneType = type;
		this.scene = scenes.get(type);
		this.scene.setPalette(palette);
	}

	public void setPaletteType(PaletteType type) {
		this.palette = paletteManager.get(type);
		this.scene.setPalette(palette);
	}

	public Scene getScene(SceneType type) {
		return scenes.get(type);
	}

	@Override
	public void beat(int interval) {
		scene.beat(interval);
	}
	
}
