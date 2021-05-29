package infinihedron.control;

import infinihedron.scenes.SceneState;
import infinihedron.scenes.SceneStateImpl;

public class StateImpl implements State {

	private int bpm = BeatRate.MIN_BPM;

	private SceneState sceneA = new SceneStateImpl();

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public SceneState getSceneA() {
		return sceneA;
	}

	public void setSceneA(SceneState sceneA) {
		this.sceneA = sceneA;
	}

}

