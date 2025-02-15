package infinihedron.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import infinihedron.control.BeatRate;
import infinihedron.control.State;
import infinihedron.control.StateManager;
import infinihedron.palettes.TapToBeat;

/**
 * Close button
 * Connection
 * Tap-to-beat
 * BPM control
 * Fader
 */
public class SharedControlPanel extends JPanel {

	private StateManager stateManager = StateManager.getInstance();
	private State state = stateManager.getCurrent();

	public SharedControlPanel() {
		super();
		this.setOpaque(true);
		populate();
	}

	private void populate() {
		this.setPreferredSize(new Dimension(400, 200));
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(closeButton());
		this.add(connectionSelector());
		this.add(tapToBeatButton());
		this.add(bpmControl());
		this.add(fadeSlider());
	}

	private JButton closeButton() {
		JButton close = new JButton("X");
		close.setPreferredSize(new Dimension(50, 50));
		close.addActionListener(e -> System.exit(0));
		return close;
	}

	private JPanel connectionSelector() {
		JPanel panel = new JPanel();

		JComboBox<String> combo = new JComboBox<>(new String[] {"localhost", "infinihedron"});
		// combo.addActionListener(e -> state.setOpcHostName((String)combo.getSelectedItem()));
		panel.add(combo);

		JLabel label = new JLabel("Not Connected");
		// stateManager.addChangeListener((state, prop) -> {
		// 	label.setText((state.getIsOpcConnected() ? "" : "Not") + "Connected");
		// });
		panel.add(label);

		return panel;
	}

	private JPanel tapToBeatButton() {
		JPanel panel = new JPanel();

		JButton button = new JButton("Tap");
		button.setPreferredSize(new Dimension(200, 200));

		TapToBeat ttb = new TapToBeat(
			i -> state.setBpm(60000 / i),
			(x, state) -> button.setText("Tap" + (state == "idle" ? "" : "...")));

		button.addActionListener(e -> ttb.tapped(System.currentTimeMillis()));

		panel.add(button);

		return panel;
	}

	private JPanel bpmControl() {
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
		slider.setPreferredSize(new Dimension(250, 50));
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

	private JPanel fadeSlider() {
		JPanel panel = new JPanel();

		return panel;
	}

}
