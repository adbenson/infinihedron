package infinihedron.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.control.Change;
import infinihedron.control.SceneManager;

public class InfinihedronControlPanel extends JPanel {

	public static final int HEIGHT = 200;

	public static void launch(SceneManager sceneA, SceneManager sceneB, Change<Integer> intervalListener, Change<Float> fadeListener, Change<String> exitListener) {
		javax.swing.SwingUtilities.invokeLater(() -> new InfinihedronControlPanel(sceneA, sceneB, intervalListener, fadeListener, exitListener));
	}

	public InfinihedronControlPanel(SceneManager sceneA, SceneManager sceneB, Change<Integer> intervalListener, Change<Float> fadeListener, Change<String> exitListener) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setOpaque(true);

		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Infinihedron.WIDTH, HEIGHT));
		frame.setLocation(0, Infinihedron.HEIGHT);
		frame.setUndecorated(true);

		// JPanel content = new InfinihedronControlPanel();
		this.add(new SceneControlPanel(sceneA));
		this.add(new SharedControlPanel(intervalListener, fadeListener, exitListener));
		this.add(new SceneControlPanel(sceneB));

		frame.setContentPane(this);

		frame.pack();
		frame.setVisible(true);
	}

}
