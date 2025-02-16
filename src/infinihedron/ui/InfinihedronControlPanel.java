package infinihedron.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import infinihedron.Infinihedron;
import infinihedron.control.SceneManager;

public class InfinihedronControlPanel extends JPanel {

	public static final int HEIGHT = 200;

	public static void launch(SceneManager sceneA, SceneManager sceneB) {
		javax.swing.SwingUtilities.invokeLater(() -> start(sceneA, sceneB));
	}

	public InfinihedronControlPanel(SceneManager managerA, SceneManager managerB) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setOpaque(true);

		add(new SceneControlPanel(managerA));
		add(new SharedControlPanel());
		add(new SceneControlPanel(managerB));
	}

	private static void start(SceneManager sceneA, SceneManager sceneB) {
		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Infinihedron.WIDTH, HEIGHT));
		frame.setLocation(0, Infinihedron.HEIGHT);
		frame.setUndecorated(true);

		JPanel content = new InfinihedronControlPanel(sceneA, sceneB);
		frame.setContentPane(content);

		frame.pack();
		frame.setVisible(true);
	}

}
