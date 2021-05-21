package infinihedron.control;

import infinihedron.scenes.SceneState;

public interface State {

	public SceneState getSceneA();
	public void setSceneA(SceneState sceneA);

	public int getBpm();
	public void setBpm(int bpm);

}

