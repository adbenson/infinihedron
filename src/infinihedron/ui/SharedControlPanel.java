package infinihedron.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

import infinihedron.control.BeatRunner;
import infinihedron.control.ChangeListener;

/**
 * Close button
 * Connection
 * Tap-to-beat
 * BPM control
 * Fader
 */
public class SharedControlPanel extends JPanel {

	private final int textMargin = 2;
	private final Insets insets = new Insets(textMargin, textMargin, textMargin, textMargin);
	private final Font bigFont = new Font("Arial", Font.BOLD, 32);

	// private StateManager stateManager = StateManager.getInstance();
	// private State state = stateManager.getCurrent();

	public static final int[] MULTIPLIER_FACTORS = new int[] {
		-8, -6, -4, -3, -2, 1, 2, 3, 4, 6, 8
	};
	public static final int DEFAULT_MULTIPLIER = 5;

	private int beatInterval = BeatRunner.DEFAULT_INTERVAL;

	private BeatRunner beatRunner;
	private ChangeListener<Float> fadeChangeListener;

	private JLabel bpmLabel;

	public SharedControlPanel(BeatRunner beatRunner, ChangeListener<Float> fadeChangeListener) {
		super();
		this.beatRunner = beatRunner;
		this.fadeChangeListener = fadeChangeListener;
		populate();
	}

	private void populate() {
		this.setPreferredSize(new Dimension(400, 200));
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(closeButton());
		// this.add(connectionSelector());
		this.add(tapToBeatButton());
		this.add(bpmControl());
		this.add(fadeSlider());
	}

	private JButton closeButton() {
		JButton close = new JButton("X");
		close.setMargin(insets);
		close.setFont(new Font("Arial", Font.PLAIN, 28));
		close.setPreferredSize(new Dimension(30, 30));
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
		button.setMargin(insets);
		button.setFont(bigFont);
		button.setPreferredSize(new Dimension(100, 100));

		TapToBeat ttb = new TapToBeat(
			i -> setInterval(i),
			(x, state) -> button.setText(state == "idle" ? "Tap" : "..."));

		button.addActionListener(e -> ttb.tapped(System.currentTimeMillis()));

		panel.add(button);

		return panel;
	}

	private JPanel bpmControl() {
		JPanel panel = new JPanel();

		JButton decrease = new JButton("-");
		decrease.setMargin(insets);
		decrease.setFont(bigFont);
		decrease.setPreferredSize(new Dimension(50, 50));
		decrease.addActionListener(__ -> changeBpm(-1));
		panel.add(decrease, BorderLayout.WEST);

		bpmLabel = new JLabel("", SwingConstants.CENTER);
		bpmLabel.setFont(bigFont);
		updateBpmLabel();
		panel.add(bpmLabel, BorderLayout.NORTH);

		JButton increase = new JButton("+");
		increase.setMargin(insets);
		increase.setFont(bigFont);
		increase.setPreferredSize(new Dimension(50, 50));
		increase.addActionListener(__ -> changeBpm(1));
		panel.add(increase, BorderLayout.EAST);

		return panel;
	}

	private void updateBpmLabel() {
		bpmLabel.setText((BeatRunner.BPM_TO_MS / beatInterval) + "");
	}

	private void setInterval(int interval) {
		if (interval < 1) {
			return; 
		}
		beatInterval = interval;
		this.beatRunner.setInterval(interval);
		updateBpmLabel();
	}

	private void changeBpm(int difference) {
		float bpm = (float)BeatRunner.BPM_TO_MS / beatInterval;
		int newInterval = Math.round(BeatRunner.BPM_TO_MS / (bpm + difference));
		setInterval(newInterval);
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
			fadeChangeListener.changed(slider.getValue() / 100.0f, "sceneFader");
		});

		panel.add(slider);

		return panel;
	}

}
