package infinihedron.control;

import infinihedron.projections.ProjectionType;
import infinihedron.scenes.BlankScene;
import infinihedron.scenes.FadeScene;
import infinihedron.scenes.FirefliesScene;
import infinihedron.scenes.FireworksScene;
import infinihedron.scenes.RingsScene;
import infinihedron.scenes.Scene;

public enum SceneType {
	Blank("Blank", BlankScene.class, ProjectionType.DIRECT_ADDRESS),
	Fade("Fade", FadeScene.class, ProjectionType.STEREOGRAPHIC),
	Rings("Rings", RingsScene.class, ProjectionType.STEREOGRAPHIC),
	Fireworks("Fireworks", FireworksScene.class, ProjectionType.STEREOGRAPHIC),
	Fireflies("Fireflies", FirefliesScene.class, ProjectionType.STEREOGRAPHIC);

	public static final SceneType DEFAULT_SCENE = Fireflies;
	
	public final String name;
	public final Class<? extends Scene> clazz;
	public final ProjectionType projection;

	SceneType(String name, Class<? extends Scene> clazz, ProjectionType projection) {
		this.clazz = clazz;
		this.name = name;
		this.projection = projection;
	}
}
