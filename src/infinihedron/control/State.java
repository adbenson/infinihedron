package infinihedron.control;

import infinihedron.scenes.SceneState;

public interface State {

	public int getBpm();
	public void setBpm(int bpm);

	public SceneState getSceneA();
	public void setSceneA(SceneState sceneA);

}

