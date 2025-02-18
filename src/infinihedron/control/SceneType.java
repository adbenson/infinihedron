package infinihedron.control;

import infinihedron.scenes.BlankScene;
import infinihedron.scenes.FadeScene;
import infinihedron.scenes.RingsScene;
import infinihedron.scenes.Scene;

public enum SceneType {
	Blank("Blank", BlankScene.class),
	Fade("Fade", FadeScene.class),
	Rings("Rings", RingsScene.class);
	
	public final Class<? extends Scene> clazz;
	public final String name;

	SceneType(String name, Class<? extends Scene> clazz) {
		this.clazz = clazz;
		this.name = name;
	}
}
