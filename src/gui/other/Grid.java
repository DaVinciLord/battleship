package gui.other;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Grid extends JPanel{
	
	private int width;
	private int height;
	private boolean enable;
	
	public Grid(int width, int height, boolean enable) {
		this.width = width;
		this.height = height;
		this.enable = enable;
		createGrid();
	}
	
	private void createGrid() {
		JPanel p = new JPanel(new GridLayout(width, height)); {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					Case c = new Case(i, j, 0);
					if (enable) { c.addMouseListener(); }
					p.add(c);
				}
			}
		}
		p.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
		add(p);
	}
	
	public int getWidthGrid() {
		return this.width;
	}
	
	public int getHeightGrid() {
		return this.height;
	}
	
}
