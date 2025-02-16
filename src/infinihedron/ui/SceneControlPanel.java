package infinihedron.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import infinihedron.control.SceneManager;
import infinihedron.control.SceneType;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.scenes.Scene;

/**
 * Scene selection
 * pallette selection
 * BPM multiplier (speed)
 * Density slider
 * Intensity slider
 */
public class SceneControlPanel extends JPanel {

	private PaletteManager palettes = PaletteManager.getInstance();

	private SceneManager manager;

	private JPanel sceneSpecificControls;

	public SceneControlPanel(SceneManager manager) {
		super();
		this.manager = manager;
		populate();
	}

	private void populate() {
		this.setPreferredSize(new Dimension(400, 200));
		this.add(sceneSelector());
		this.add(palettes());
		this.add(sceneSpecificControls());
	}

	private JPanel sceneSelector() {
		JPanel panel = new JPanel();
		JComboBox<SceneType> combo = new JComboBox<>(SceneType.values());
		
		combo.addActionListener(e -> sceneSelected((SceneType)combo.getSelectedItem()));

		panel.add(combo);
		return panel;
	}

	private void sceneSelected(SceneType type) {
		manager.setSceneType(type);
		CardLayout layout = (CardLayout)sceneSpecificControls.getLayout();
		layout.show(sceneSpecificControls, type.name());
	}

	private JPanel palettes() {
		JPanel panel = new JPanel();

		JComboBox<PaletteType> combo = new JComboBox<>(PaletteType.values());

		PaletteRenderer renderer = new PaletteRenderer();
		combo.setRenderer(renderer);

		combo.addActionListener(l -> {
			PaletteType type = (PaletteType)combo.getSelectedItem();
			manager.setPaletteType(type);
		});
		// combo.addActionListener(e -> state.getSceneA().setPalette((PaletteType)combo.getSelectedItem()));

		panel.add(combo);

		return panel;
	}


	private JPanel sceneSpecificControls() {
		sceneSpecificControls = new JPanel(new CardLayout());
		for(SceneType type : SceneType.values()) {
			Scene scene = manager.getScene(type);
			if (scene == null) {
				throw new RuntimeException("WTF");
			}
			sceneSpecificControls.add(scene.getControls(), type.name());
		}
		return sceneSpecificControls;
	}

	class PaletteRenderer implements ListCellRenderer<PaletteType> {
		@Override
		public Component getListCellRendererComponent(JList<? extends PaletteType> list, PaletteType value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return palettes.get(value);

		}
	}
}
