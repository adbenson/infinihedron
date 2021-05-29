package infinihedron.scenes;

import infinihedron.control.BeatRate;

public class SceneStateImpl implements SceneState {
	
	private SceneType type = SceneType.Blank;
	private int multiplier = BeatRate.DEFAULT_MULTIPLIER;

	public SceneType getType() {
		return type;
	}

	public void setType(SceneType type) {
		this.type = type;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}
}
