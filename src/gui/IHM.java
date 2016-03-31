package gui;

import gui.other.Grid;
import gui.other.Tchat;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;


public class IHM {
	
	JFrame mainFrame;
	
	JMenuBar menuBar;
	JMenu graphics;
	JMenu sounds;
	JMenu help;
	JMenu quit;
	
	//Player player1;
	
	Grid grid_p1;
	Grid grid_p2;
	Tchat tchat;
	
	
	
	public IHM() {
		createView();
		placeComponents();
		createController();
	}
	
	private void createView() {
		mainFrame = new JFrame("Bataille Navale");
		mainFrame.setLayout(new BorderLayout());
		menuBar = new JMenuBar();
		graphics = new JMenu("Graphismes");
		sounds = new JMenu("Sons");
		help = new JMenu("Aide");
		quit = new JMenu("Quitter");
		grid_p1 = new Grid(10,10,true);
		grid_p2 = new Grid(10,10,false);
		tchat = new Tchat("Snake76930");
	}
	
	private void placeComponents() {
		/* --- MenuBar --- */
		String[] graph_value = {"Faible","Moyen","Elevé"};
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < graph_value.length; i++) {
			JRadioButtonMenuItem rb = new JRadioButtonMenuItem(graph_value[i]);
			if (i == 0) { rb.setSelected(true); }
			group.add(rb);
			graphics.add(rb);
		}
		menuBar.add(graphics);
		
		
		sounds.add(new JCheckBoxMenuItem("Tchat", true));
		sounds.add(new JCheckBoxMenuItem("Bruitages", true));
		sounds.add(new JCheckBoxMenuItem("Musiques", true));
		menuBar.add(sounds);
		
		help.add(new JMenuItem("Comment jouer ?"));
		help.add(new JMenuItem("A propos de..."));
		menuBar.add(help);
		
		menuBar.add(quit);
		
		mainFrame.setJMenuBar(menuBar);
		
		/* --- NORTH --- */
		JPanel p = new JPanel(); {
			p.add(grid_p1);
			p.add(grid_p2);
		}
		mainFrame.add(p, BorderLayout.NORTH);
		
		
		
		/* --- CENTER --- */
		
		
		/* --- SOUTH --- */
		mainFrame.add(tchat, BorderLayout.CENTER);
	}
	
	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	public void display() {
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new IHM().display();
			}
		});
	}
}
