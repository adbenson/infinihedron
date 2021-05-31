package infinihedron.scenes;

import infinihedron.control.BeatRate;
import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteType;

public class SceneStateImpl implements SceneState {
	
	private SceneType type = SceneType.Blank;
	private int multiplier = BeatRate.DEFAULT_MULTIPLIER;
	private PaletteType palette = PaletteType.Rainbow;

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

	@Override
	public PaletteType getPalette() {
		return palette;
	}

	@Override
	public void setPalette(PaletteType palette) {
		this.palette = palette;
	}
}
