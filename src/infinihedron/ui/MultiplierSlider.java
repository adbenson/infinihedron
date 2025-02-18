package infinihedron.ui;

import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import infinihedron.control.BeatMultiplier;
import infinihedron.control.Change;

public class MultiplierSlider extends JPanel {
	
	public static final int DEFAULT_MULTIPLIER = 5;
	private final JSlider slider;
	private Change<Integer> listener;

	public MultiplierSlider(Change<Integer> multiplierListener) {
		super();

		this.listener = multiplierListener;

		slider = new JSlider(0, BeatMultiplier.MULTIPLIER_FACTORS.length - 1, DEFAULT_MULTIPLIER);

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		for (int i = 0; i < BeatMultiplier.MULTIPLIER_FACTORS.length; i++) {
			String label = getMultiplierLabel(i);
			labels.put(i, new JLabel(label));
		}

		slider.setLabelTable(labels);

		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(false);
		slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.HORIZONTAL);
		slider.setPreferredSize(new Dimension(375, 40));

		slider.addChangeListener(__ -> this.notifyListener());

		this.add(slider);
	}

	private String getMultiplierLabel(int multiplier) {
		int factor = BeatMultiplier.MULTIPLIER_FACTORS[multiplier];
		if (factor > 0) {
			return factor + "x";
		} else {
			return "/" + (-factor);
		}
	}

	private void notifyListener() {
		int factor = BeatMultiplier.MULTIPLIER_FACTORS[slider.getValue()];
		// This gets us off the UI thread
		new Thread(() -> {
			this.listener.changed(factor);
		}).start();
	}

}
