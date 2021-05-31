package infinihedron.control;

import infinihedron.scenes.SceneState;

public interface State {

	public String getOpcHostName();
	public void setOpcHostName(String name);

	public boolean getIsOpcConnected();
	public void setIsOpcConnected(boolean connected);

	public int getBpm();
	public void setBpm(int bpm);

	public SceneState getSceneA();
	public void setSceneA(SceneState sceneA);

}
 
