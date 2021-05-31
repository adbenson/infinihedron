package infinihedron.control;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import infinihedron.palettes.Palette;
import infinihedron.palettes.PaletteManager;
import infinihedron.palettes.PaletteType;
import infinihedron.palettes.RainbowPalette;
import infinihedron.palettes.TapToBeat;
import infinihedron.scenes.SceneType;

public class InfinihedronControlWindow extends JPanel {

	private StateManager stateManager = StateManager.getInstance();
	private State state = stateManager.getCurrent();

	private PaletteManager palettes = PaletteManager.getInstance();

	private BeatListener beatListener;

	public static void launch() {
		javax.swing.SwingUtilities.invokeLater(() -> start());
	}

	private static void start() {
		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 1200));
		frame.setUndecorated(true);

		JPanel content = new InfinihedronControlWindow();
		frame.setContentPane(content);

		frame.pack();
		frame.setVisible(true);
	}
	
	InfinihedronControlWindow() {
		super();

		this.setOpaque(true);
		populate();
	}
	
	private void populate() {
		this.add(closeButton());

		this.add(opcConnection());

		this.add(sceneList());

		this.add(bpmSlider());

		this.add(tapToBeat());

		this.add(multiplier());

		this.add(palettes());
	}
	
	private JButton closeButton() {
		JButton close = new JButton("X");
		close.setPreferredSize(new Dimension(50, 50));
		close.addActionListener(e -> System.exit(0));
		return close;
	}

	private JPanel sceneList() {
		JPanel panel = new JPanel();
		JComboBox<SceneType> combo = new JComboBox<>(SceneType.values());
		combo.addActionListener(e -> state.getSceneA().setType((SceneType)combo.getSelectedItem()));
		
		panel.add(combo);
		return panel;
	}
	
	private JPanel bpmSlider() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JButton decrease = new JButton("-");
		decrease.setPreferredSize(new Dimension(50, 50));
		decrease.addActionListener(e -> state.setBpm(state.getBpm() - 1));
		panel.add(decrease, BorderLayout.WEST);

		JLabel label = new JLabel(BeatRate.DEFAULT_BPM + " bpm", SwingConstants.CENTER);
		panel.add(label, BorderLayout.NORTH);

		JSlider slider = new JSlider(BeatRate.MIN_BPM, BeatRate.MAX_BPM, BeatRate.DEFAULT_BPM);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel.add(slider, BorderLayout.CENTER);

		JButton increase = new JButton("+");
		increase.setPreferredSize(new Dimension(50, 50));
		increase.addActionListener(e -> state.setBpm(state.getBpm() + 1));
		panel.add(increase, BorderLayout.EAST);
	
		stateManager.addChangeListener((state, prop) -> {
			int bpm = state.getBpm();
			label.setText(bpm + " bpm");
			slider.setValue(bpm);
		});

		slider.addChangeListener(e -> state.setBpm(slider.getValue()));

		return panel;
	}

	private JPanel multiplier() {
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

		slider.addChangeListener(e -> state.getSceneA().setMultiplier(slider.getValue()));

		JLabel label = new JLabel("1x");
		stateManager.addChangeListener((state, prop) -> label.setText(getMultiplierLabel(state.getSceneA().getMultiplier())));

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

	private JPanel opcConnection() {
		JPanel panel = new JPanel();

		JComboBox<String> combo = new JComboBox<>(new String[] {"localhost", "infinihedron"});
		combo.addActionListener(e -> state.setOpcHostName((String)combo.getSelectedItem()));
		panel.add(combo);

		JLabel label = new JLabel("Not Connected");
		stateManager.addChangeListener((state, prop) -> {
			label.setText((state.getIsOpcConnected() ? "" : "Not") + "Connected");
		});
		panel.add(label);

		return panel;
	}

	private JPanel palettes() {
		JPanel panel = new JPanel();

		JComboBox<PaletteType> combo = new JComboBox<>(PaletteType.values());

		PaletteRenderer renderer = new PaletteRenderer();
		combo.setRenderer(renderer);

		combo.addActionListener(e -> state.getSceneA().setPalette((PaletteType)combo.getSelectedItem()));

		panel.add(combo);

		return panel;
	}

	private JPanel tapToBeat() {
		JPanel panel = new JPanel();

		JButton button = new JButton("Tap");
		button.setPreferredSize(new Dimension(200, 200));

		TapToBeat ttb = new TapToBeat(i -> state.setBpm(60000 / i));

		button.addActionListener(e -> ttb.tapped(System.currentTimeMillis()));

		panel.add(button);

		return panel;
	}

	class PaletteRenderer implements ListCellRenderer<PaletteType> {
		@Override
		public Component getListCellRendererComponent(JList<? extends PaletteType> list, PaletteType value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return palettes.get(value);

		}
	}
}

