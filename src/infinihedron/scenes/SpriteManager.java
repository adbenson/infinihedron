package infinihedron.scenes;

import java.util.LinkedList;
import java.util.List;

import infinihedron.scenes.SpriteManager.Sprite;

interface SpriteCreator<SpriteType extends Sprite> {
	SpriteType create();
}

public class SpriteManager<SpriteType extends Sprite> {

	protected volatile List<SpriteType> sprites = new LinkedList<>();

	private volatile List<SpriteType> toAdd = new LinkedList<>();
	private volatile List<SpriteType> toRemove = new LinkedList<>();

	private final int minAge;
	private final int maxAge;
	private final int minBirthInterval;
	private final int maxBirthInterval;
	private final float birthFactor;
	private final SpriteCreator<SpriteType> creator;

	private int beatsSinceLastBirth;

	public SpriteManager(int minAge, int maxAge, int minBirthInterval, int maxBirthInterval, SpriteCreator<SpriteType> creator) {
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.minBirthInterval = minBirthInterval;
		this.maxBirthInterval = maxBirthInterval;
		this.birthFactor = 1.0f / (maxBirthInterval - minBirthInterval);
		this.creator = creator;
		beatsSinceLastBirth = minBirthInterval;
	}

	public void draw() {
		for (SpriteType sprite : sprites) {
			if (sprite.life > 0) {
				sprite.draw();
			}
		}

		sprites.removeAll(toRemove);
		sprites.addAll(toAdd);
		toRemove.clear();
		toAdd.clear();
	}

	public void beat() {
		sprites.forEach(s -> {
			if (s.life <= 0) {
				toRemove.add(s);
			} else {
				s.beat();
				s.life--;
			}
		});

		if (beatsSinceLastBirth >= maxBirthInterval) {
			birthSprite();
		} else if (beatsSinceLastBirth >= minBirthInterval) {
			if (Math.random() < birthFactor) {
				birthSprite();
			}
		} else {
			beatsSinceLastBirth++;
		}
	}

	private void birthSprite() {
		int life = (int) (Math.random() * (maxAge - minAge)) + minAge;
		SpriteType sprite = creator.create();
		sprite.life = life;
		toAdd.add(sprite);
		beatsSinceLastBirth = 0;
	}

	public static abstract class Sprite {
		int x;
		int y;
		int life;
	
		Sprite() {
			x = 0;
			y = 0;
			life = 0;
		}
	
		public void beat() {}
		public abstract void  draw();
	}

}
