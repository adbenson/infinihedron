package infinihedron.scenes;

import java.awt.Color;

import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.control.SceneType;
import infinihedron.ui.MultiplierSlider;

public class FadeScene extends Scene {
	
	public FadeScene(Infinihedron infinihedron) {
		super(infinihedron, SceneType.Fade);
	}

	// private boolean state = true;

	private int state = 0;

	@Override
	public void draw() {
		float beatFraction = getBeatFraction();
		// int mid = (int)(255 * beatFraction);
		Color color = palette.getColor(beatFraction);
		p.fill(color.getRGB());
		p.rect(origin.x, origin.y, size.x, size.y);
	}
	
	@Override
	public void beat() {
		state = (state + 1) % 255;
	}

	@Override
	public JPanel getControls() {
		JPanel panel = new JPanel();
		panel.add(new MultiplierSlider(m -> setBeatMultiplier(m)));
		return panel;
	}

}
