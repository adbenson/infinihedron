package infinihedron.scenes;

public enum Scenes {
	Blank(BlankScene.class),
	Strobe(StrobeScene.class);
	
	public final Class<? extends Scene> scene;

	Scenes(Class<? extends Scene> scene) {
		this.scene = scene;
	}
}
