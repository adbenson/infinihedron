package infinihedron.scenes;

import javax.swing.JLabel;
import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.projections.Projection;

public class BlankScene extends Scene {

	public BlankScene(Infinihedron infinihedron, Projection projection) {
		super(infinihedron, projection);
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
