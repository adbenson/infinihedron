package infinihedron.scenes;

public enum SceneType {
	Blank(BlankScene.class),
	Strobe(StrobeScene.class);
	
	public final Class<? extends Scene> clazz;

	SceneType(Class<? extends Scene> clazz) {
		this.clazz = clazz;
	}
}
