package infinihedron.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.control.BeatRunner;
import infinihedron.control.ChangeListener;
import infinihedron.control.SceneManager;

public class InfinihedronControlPanel extends JPanel {

	public static final int HEIGHT = 200;

	public static void launch(SceneManager sceneA, SceneManager sceneB, BeatRunner beatRunner, ChangeListener<Float> fadeChangeListener) {
		javax.swing.SwingUtilities.invokeLater(() -> new InfinihedronControlPanel(sceneA, sceneB, beatRunner, fadeChangeListener));
	}

	public InfinihedronControlPanel(SceneManager sceneA, SceneManager sceneB, BeatRunner beatRunner, ChangeListener<Float> fadeChangeListener) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setOpaque(true);

		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Infinihedron.WIDTH, HEIGHT));
		frame.setLocation(0, Infinihedron.HEIGHT);
		frame.setUndecorated(true);

		// JPanel content = new InfinihedronControlPanel();
		this.add(new SceneControlPanel(sceneA));
		this.add(new SharedControlPanel(beatRunner, fadeChangeListener));
		this.add(new SceneControlPanel(sceneB));

		frame.setContentPane(this);

		frame.pack();
		frame.setVisible(true);
	}

}
