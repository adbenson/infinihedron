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

import infinihedron.control.BeatRunner;

/**
 * Close button
 * Connection
 * Tap-to-beat
 * BPM control
 * Fader
 */
public class SharedControlPanel extends JPanel {

	private final Font bigFont = new Font("Arial", Font.BOLD, 24);

	// private StateManager stateManager = StateManager.getInstance();
	// private State state = stateManager.getCurrent();

	public static final int[] MULTIPLIER_FACTORS = new int[] {
		-8, -6, -4, -3, -2, 1, 2, 3, 4, 6, 8
	};
	public static final int DEFAULT_MULTIPLIER = 5;

	private int bpm;

	private BeatRunner beatRunner;

	public SharedControlPanel(BeatRunner beatRunner) {
		super();
		this.beatRunner = beatRunner;
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
		close.setFont(bigFont);
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
		button.setFont(bigFont);
		button.setPreferredSize(new Dimension(100, 100));

		TapToBeat ttb = new TapToBeat(
			i -> beatRunner.setBpm(60000 / i),
			(x, state) -> button.setText(state == "idle" ? "Tap" : "..."));

		button.addActionListener(e -> ttb.tapped(System.currentTimeMillis()));

		panel.add(button);

		return panel;
	}

	private JPanel bpmControl() {
		JPanel panel = new JPanel();

		JButton decrease = new JButton("-");
		decrease.setFont(bigFont);
		decrease.setPreferredSize(new Dimension(50, 50));
		// decrease.addActionListener(e -> state.setBpm(state.getBpm() - 1));
		panel.add(decrease, BorderLayout.WEST);

		JLabel label = new JLabel(BeatRunner.DEFAULT_BPM + " bpm", SwingConstants.CENTER);
		panel.add(label, BorderLayout.NORTH);

		JButton increase = new JButton("+");
		increase.setFont(bigFont);
		increase.setPreferredSize(new Dimension(50, 50));
		// increase.addActionListener(e -> state.setBpm(state.getBpm() + 1));
		panel.add(increase, BorderLayout.EAST);
	
		// stateManager.addChangeListener((state, prop) -> {
		// 	int bpm = state.getBpm();
		// 	label.setText(bpm + " bpm");
		// });

		return panel;
	}

	private JPanel fadeSlider() {
		JPanel panel = new JPanel();

		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);

		slider.setUI(new BasicSliderUI(slider) {
            @Override
            protected Dimension getThumbSize() {
                return new Dimension(20, 20); // Set custom thumb size
            }
			@Override
			public void paintThumb(Graphics g) {
				Rectangle knobBounds = thumbRect;
				g.setColor(Color.black);
				g.fillRect(knobBounds.x, knobBounds.y, 20, 20);
			}
        });
		

		slider.setMinorTickSpacing(1);

		slider.addChangeListener(__ -> {
			// state.setFade(slider.getValue() / 100.0f);
		});

		panel.add(slider);

		return panel;
	}

}
