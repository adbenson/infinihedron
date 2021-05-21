package infinihedron.control;

import infinihedron.scenes.SceneState;
import infinihedron.scenes.SceneStateImpl;

public class StateImpl implements State {

	private SceneState sceneA = new SceneStateImpl();

	private int bpm;

	public SceneState getSceneA() {
		return sceneA;
	}

	public void setSceneA(SceneState sceneA) {
		this.sceneA = sceneA;
	}

	public int getBpm() {return bpm;}
	public void setBpm(int bpm) {this.bpm = bpm;}

}

