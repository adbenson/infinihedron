package infinihedron.palettes;

import java.util.HashMap;
import java.util.Map;

public class PaletteManager {
	private static PaletteManager instance = null;

	public static PaletteManager getInstance() {
		if (instance == null) {
			instance = new PaletteManager();
		}
		return instance;
	}

	private Map<PaletteType, Palette> instances = new HashMap<>();

	private PaletteManager() {
		instances.put(PaletteType.Blank, new Blank());
		instances.put(PaletteType.BlackAndWhite, new BlackAndWhite());
		instances.put(PaletteType.Rainbow, new RainbowPalette());
		instances.put(PaletteType.Aesthetic, new Aesthetic());
	}

	public Palette get(PaletteType type) {
		return instances.get(type);
	}

}
