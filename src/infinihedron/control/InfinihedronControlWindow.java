package infinihedron.control;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import infinihedron.scenes.BlankScene;
import infinihedron.scenes.Scene;
import infinihedron.scenes.Scenes;
import infinihedron.scenes.StrobeScene;

public class InfinihedronControlWindow extends JPanel {

	public static void launch() {
		javax.swing.SwingUtilities.invokeLater(() -> start());
	}

	private static void start() {
		JFrame frame = new JFrame("Infinihedron");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 1200));
		frame.setUndecorated(true);

		JPanel content = new InfinihedronControlWindow();
		frame.setContentPane(content);

		frame.pack();
		frame.setVisible(true);
	}
	
	InfinihedronControlWindow() {
		super();
		this.setOpaque(true);
		populate();
	}
	
	private void populate() {
		this.add(closeButton());
		
		JComboBox<Scenes> combo = sceneList();
		this.add(sceneList());
	}
	
	private JButton closeButton()  {
		JButton close = new JButton("X");
		close.addActionListener(e -> System.exit(0));
		return close;
	}
	
	@SuppressWarnings("unchecked")
	private JComboBox<Scenes> sceneList() {
		List<Scenes> scenes = Arrays.asList(Scenes.values());

		JComboBox<Scenes> combo = new JComboBox<Scenes>(scenes);
		
//		combo.setRenderer(new ToStringListCellRenderer(combo.getRenderer(), toString)));
		
		return combo;
	}
}

