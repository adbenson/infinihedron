package infinihedron.scenes;

import javax.swing.JLabel;
import javax.swing.JPanel;

import infinihedron.control.SceneType;
import processing.core.PApplet;

public class BlankScene extends Scene {

	public BlankScene(PApplet processing) {
		super(processing, SceneType.Blank);
	}
	
	public void draw(long time) {
		p.fill(0);
		p.rect(0, 0, p.height, p.width);
	}

	@Override
	public JPanel getControls() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Blank Scene Controls"));
		return panel;
	}

}
