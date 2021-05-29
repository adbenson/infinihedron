package infinihedron.control;

import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import infinihedron.scenes.SceneType;

public class InfinihedronControlWindow extends JPanel {

	private StateManager stateManager = StateManager.getInstance();
	private State state = stateManager.getCurrent();

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
		
		JComboBox<SceneType> scenesA = sceneList();
		scenesA.addActionListener(e -> state.getSceneA().setType((SceneType)scenesA.getSelectedItem()));
		this.add(scenesA);

		JPanel bpmA = bpmSlider();
		this.add(bpmA);

		JPanel multi = multiplier();
		this.add(multi);
	}
	
	private JButton closeButton() {
		JButton close = new JButton("X");
		close.addActionListener(e -> System.exit(0));
		return close;
	}

	private JComboBox<SceneType> sceneList() {
		JComboBox<SceneType> combo = new JComboBox<>(SceneType.values());
		
//		combo.setRenderer(new ToStringListCellRenderer(combo.getRenderer(), toString)));
		
		return combo;
	}
	
	private JPanel bpmSlider() {
		JSlider slider = new JSlider(BeatRate.MIN_BPM, BeatRate.MAX_BPM, BeatRate.DEFAULT_BPM);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		JLabel label = new JLabel();
		JPanel panel = new JPanel();

		stateManager.addChangeListener((state, prop) -> label.setText(state.getBpm() + " bpm"));

		slider.addChangeListener(e -> state.setBpm(slider.getValue()));

		panel.add(label);
		panel.add(slider);

		return panel;
	}

	//[-4, -2, 1, 2, 4].length = 5
	//[ 0,  1, 2, 3, 4]

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

	private JPanel labeledComponent(JComponent component, String text) {
		JLabel label = new JLabel(text);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(component);
		return panel;
	}
}

