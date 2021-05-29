package infinihedron.scenes;

public interface SceneState{

	public SceneType getType();
	public void setType(SceneType type);

	public int getMultiplier();
	public void setMultiplier(int multiplier);

}
