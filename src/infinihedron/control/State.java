package infinihedron.control;

import infinihedron.scenes.BlankScene;
import infinihedron.scenes.Scene;

public class State {

	public static volatile Class<? extends Scene> sceneA = BlankScene.class;

}
