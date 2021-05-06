package infinihedron.palettes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JComponent;

public abstract class Palette extends JComponent {
	
	public static final int WIDTH = 280;
	public static final int HEIGHT = 80;
	
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }
	
	public Color getColor() {
		return getColor(new Random().nextFloat());
	}
	
	public abstract Color getColor(float value);
	
	public abstract Color draw(Graphics2D g);

}
