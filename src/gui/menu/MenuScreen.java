package gui.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton singlePlayer;
	private JButton multiPlayer;
	private JButton options;
	private JButton quit;
	
	public MenuScreen() {
		createView();
		placeComponents();
		createController();
	}
	
	private void createView() {
		this.setLayout(new BorderLayout());
		singlePlayer = new JButton("Solo");
		singlePlayer.setPreferredSize(new Dimension(300, 40));
		multiPlayer = new JButton("Multijoueur");
		multiPlayer.setPreferredSize(new Dimension(300, 40));
		options = new JButton("Options");
		options.setPreferredSize(new Dimension(200, 30));
		quit = new JButton("Quitter");
		quit.setPreferredSize(new Dimension(200, 30));
	}
	
	private void placeComponents() {
		JPanel p = new JPanel();
		
		this.add(singlePlayer);
		this.add(multiPlayer);
		this.add(options);
		this.add(quit);
	}
	
	private void createController() {
		
	}
	
	public void show() {
		this.setVisible(true);
	}
	
	public void hide() {
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		MenuScreen menu = new MenuScreen();
		frame.add(menu, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setVisible(true);
	}

}

