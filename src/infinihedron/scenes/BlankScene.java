package infinihedron.scenes;

import javax.swing.JLabel;
import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.control.SceneType;

public class BlankScene extends Scene {

	public BlankScene(Infinihedron infinihedron) {
		super(infinihedron, SceneType.Blank);
	}
	
	@Override
	public void draw() {
		p.fill(0);
		p.rect(origin.x, origin.y, size.x, size.y);
	}

	@Override
	public JPanel getControls() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Blank Scene Controls"));
		return panel;
	}

}
