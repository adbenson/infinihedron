package infinihedron.scenes;

public class SceneStateImpl implements SceneState {
	
	private SceneType type = SceneType.Blank;

	private int bpm = 120;

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
