package infinihedron.scenes;

public interface SceneState{

	public SceneType getType();
	public void setType(SceneType type);

	public int getBpm();
	public void setBpm(int bpm);

}
