package infinihedron.control;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import infinihedron.scenes.SceneType;

public class InfinihedronControlWindow extends JPanel {

	public static final int MIN_BPM = 60;
	public static final int MAX_BPM = 180;

	private StateManager stateManager = StateManager.getInstance();

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
		// scenesA.addActionListener(e -> state.change(StateValue.SceneA, (SceneType)scenesA.getSelectedItem()));
		this.add(scenesA);

		JPanel bpmA = bpmSlider();
		this.add(bpmA);
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
		JSlider slider = new JSlider(60, 180);
		slider.setMajorTickSpacing(40);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		JLabel label = new JLabel();
		JPanel panel = new JPanel();

		stateManager.addChangeListener((state, prop) -> label.setText(state.getBpm() + " bpm"));

		slider.addChangeListener(e -> stateManager.getCurrent().setBpm(slider.getValue()));

		panel.add(label);
		panel.add(slider);

		return panel;
	}

	private JPanel labeledComponent(JComponent component, String text) {
		JLabel label = new JLabel(text);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(component);
		return panel;
	}
}

