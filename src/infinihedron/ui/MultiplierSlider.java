package infinihedron.ui;

import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import infinihedron.control.Change;

public class MultiplierSlider extends JPanel {
	
	public static final int[] MULTIPLIER_FACTORS = new int[] {
		-8, -6, -4, -3, -2, 1, 2, 3, 4, 6, 8
	};
	public static final int DEFAULT_MULTIPLIER = 5;

	public MultiplierSlider(Change<Integer> multiplierListener) {
		super();

		JSlider slider = new JSlider(0, MULTIPLIER_FACTORS.length - 1, DEFAULT_MULTIPLIER);

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		for (int i = 0; i < MULTIPLIER_FACTORS.length; i++) {
			String label = getMultiplierLabel(i);
			labels.put(i, new JLabel(label));
		}

		slider.setLabelTable(labels);

		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(false);
		slider.setPaintLabels(true);
		slider.setOrientation(SwingConstants.HORIZONTAL);
		slider.setPreferredSize(new Dimension(375, 40));

		slider.addChangeListener(e -> multiplierListener.changed(MULTIPLIER_FACTORS[slider.getValue()]));

		this.add(slider);
	}

	private String getMultiplierLabel(int multiplier) {
		int factor = MULTIPLIER_FACTORS[multiplier];
		if (factor > 0) {
			return factor + "x";
		} else {
			return "/" + (-factor);
		}
	}

}
