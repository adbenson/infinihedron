package infinihedron.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import infinihedron.control.BeatRate;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.scenes.SceneType;

/**
 * Scene selection
 * pallette selection
 * BPM multiplier (speed)
 * Density slider
 * Intensity slider
 */
public class SceneControlPanel extends JPanel {

	private PaletteManager palettes = PaletteManager.getInstance();

	public SceneControlPanel() {
		super();
		this.setOpaque(true);
		populate();
	}

	private void populate() {
		this.setPreferredSize(new Dimension(400, 200));
		this.add(sceneSelector());
		this.add(palettes());
		this.add(multiplierSlider());
	}

	private JPanel sceneSelector() {
		JPanel panel = new JPanel();
		JComboBox<SceneType> combo = new JComboBox<>(SceneType.values());
		// combo.addActionListener();
		
		panel.add(combo);
		return panel;
	}

	private JPanel palettes() {
		JPanel panel = new JPanel();

		JComboBox<PaletteType> combo = new JComboBox<>(PaletteType.values());

		PaletteRenderer renderer = new PaletteRenderer();
		combo.setRenderer(renderer);

		// combo.addActionListener(e -> state.getSceneA().setPalette((PaletteType)combo.getSelectedItem()));

		panel.add(combo);

		return panel;
	}

	private JPanel multiplierSlider() {
		JSlider slider = new JSlider(0, BeatRate.MULTIPLIER_FACTORS.length - 1, BeatRate.DEFAULT_MULTIPLIER);

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		for (int i = 0; i < BeatRate.MULTIPLIER_FACTORS.length; i++) {
			String label = getMultiplierLabel(i);
			labels.put(i, new JLabel(label));
		}

		slider.setLabelTable(labels);

		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.VERTICAL);

		// slider.addChangeListener(e -> state.getSceneA().setMultiplier(slider.getValue()));

		JLabel label = new JLabel("1x");
		// stateManager.addChangeListener((state, prop) -> label.setText(getMultiplierLabel(state.getSceneA().getMultiplier())));

		JPanel panel = new JPanel();

		panel.add(label);
		panel.add(slider);

		return panel;
	}

	private String getMultiplierLabel(int multiplier) {
		int factor = BeatRate.MULTIPLIER_FACTORS[multiplier];
		if (factor > 0) {
			return factor + "x";
		} else {
			return "/" + (-factor);
		}
	}

	class PaletteRenderer implements ListCellRenderer<PaletteType> {
		@Override
		public Component getListCellRendererComponent(JList<? extends PaletteType> list, PaletteType value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return palettes.get(value);

		}
	}
}
