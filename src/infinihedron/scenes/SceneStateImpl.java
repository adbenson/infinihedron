package infinihedron.scenes;

public class SceneStateImpl implements SceneState {
	
	private SceneType type;

	private int bpm;

	public SceneType getType() {
		return type;
	}

	public void setType(SceneType type) {
		this.type = type;
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}
}
