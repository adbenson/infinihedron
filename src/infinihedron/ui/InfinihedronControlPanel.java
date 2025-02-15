package infinihedron.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InfinihedronControlPanel extends JPanel {

	public static void launch() {
		javax.swing.SwingUtilities.invokeLater(() -> start());
	}

	public InfinihedronControlPanel() {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setOpaque(true);
		populate();
	}

	private void populate() {
		add(new SceneControlPanel());
		add(new SharedControlPanel());
		add(new SceneControlPanel());
	}

	private static void start() {
		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 200));
		frame.setLocation(0, 600);
		frame.setUndecorated(true);

		JPanel content = new InfinihedronControlPanel();
		frame.setContentPane(content);

		frame.pack();
		frame.setVisible(true);
	}

}
