package infinihedron.scenes;

import java.awt.Color;

import javax.swing.JPanel;

import infinihedron.control.SceneType;
import infinihedron.ui.MultiplierSlider;
import processing.core.PApplet;

public class FireworksScene extends Scene {

	private final SpriteManager<Firework> sprites;

	private final int maxRadius = 250;
	private final float gradientStep = 0.1f;

	private final int birthAge = 10;
	private final int minBirthInterval = 0;
	private final int maxBirthInterval = 1;

	public FireworksScene(PApplet processing) {
		super(processing, SceneType.Fireworks);

		sprites = new SpriteManager<Firework>(
			birthAge, birthAge, minBirthInterval, maxBirthInterval,
			() -> new Firework()
		);
	}

	@Override
	public void draw() {
		sprites.draw();
	}

	@Override
	public void beat() {
		sprites.beat();
	}

	@Override
	public JPanel getControls() {
		JPanel panel = new JPanel();
		panel.add(new MultiplierSlider(m -> setBeatMultiplier(m)));
		return panel;
	}

	class Firework extends SpriteManager.Sprite {

		final Color c;

		Firework() {
			x = randomBetween((int)origin.x, (int)limit.x);
			y = randomBetween((int)origin.y, (int)limit.y);
			c = palette.getRandomColor();
		}
		
		@Override
		public void draw() {
			if (life == birthAge) {
				gradientCircle(x, y, c, (int)(maxRadius * getBeatFraction()), 1);
			} else {
				float preciseAge = life + (1 - getBeatFraction());
				gradientCircle(x, y, c, maxRadius, preciseAge / birthAge);
			}
			
		}

		private void gradientCircle(int x, int y, Color c, int radius, float maxAlpha) {
			for (float step = gradientStep; step < 1; step += gradientStep) {
				p.fill(adjustAlpha(c, (1 - step) * maxAlpha));
				p.circle(x, y, radius * step);
			}
		}
	
	}

}
