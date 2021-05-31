package infinihedron.control;

import infinihedron.Infinihedron;
import infinihedron.scenes.SceneState;
import infinihedron.scenes.SceneStateImpl;

public class StateImpl implements State {

	private String hostname = Infinihedron.DEFAULT_HOST;
	private boolean connected = false;

	private int bpm = BeatRate.MIN_BPM;
	private SceneState sceneA = new SceneStateImpl();

	@Override
	public int getBpm() {
		return bpm;
	}

	@Override
	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	@Override
	public SceneState getSceneA() {
		return sceneA;
	}

	@Override
	public void setSceneA(SceneState sceneA) {
		this.sceneA = sceneA;
	}

	@Override
	public String getOpcHostName() {
		return hostname;
	}

	@Override
	public void setOpcHostName(String name) {
		this.hostname = name;
	}

	@Override
	public boolean getIsOpcConnected() {
		return connected;
	}

	@Override
	public void setIsOpcConnected(boolean connected) {
		this.connected = connected;
	}

}

