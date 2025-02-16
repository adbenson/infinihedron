package infinihedron.scenes;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import infinihedron.control.DrawState;
import infinihedron.control.SceneType;
import processing.core.PApplet;

public class FadeScene extends Scene {
	
	public FadeScene(PApplet processing) {
		super(processing, SceneType.Fade);
	}

	// private boolean state = true;

	private int state = 0;

	@Override
	public void draw(DrawState drawState) {
		float beatFraction = drawState.beatFraction;
System.out.println("FadeScene.draw start: " + beatFraction);
		// int mid = (int)(255 * beatFraction);
		Color color = palette.getColor(beatFraction);
		p.fill(color.getRGB());
		p.rect(origin.x, origin.y, limit.x, limit.y);
System.out.println("FadeScene.draw color: " + color.getRGB());
	}
	
	@Override
	public void beat(int interval, long time) {
		super.beat(interval, time);
		// this.state = !this.state;

		state = (state + 1) % 255;
System.out.println("FadeScene.beat: " + state);
	}

	@Override
	public JPanel getControls() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Fade Scene Controls"));
		return panel;
	}

}
