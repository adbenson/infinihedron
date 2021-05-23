package infinihedron.control;

import infinihedron.scenes.SceneState;
import infinihedron.scenes.SceneStateImpl;

public class StateImpl implements State {

	private SceneState sceneA = new SceneStateImpl();

	public SceneState getSceneA() {
		return sceneA;
	}

	public void setSceneA(SceneState sceneA) {
		this.sceneA = sceneA;
	}

}

