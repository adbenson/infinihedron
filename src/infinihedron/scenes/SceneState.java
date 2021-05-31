package infinihedron.scenes;

import infinihedron.palettes.PaletteType;

public interface SceneState{

	public SceneType getType();
	public void setType(SceneType type);

	public int getMultiplier();
	public void setMultiplier(int multiplier);

	public PaletteType getPalette();
	public void setPalette(PaletteType palette);

}
