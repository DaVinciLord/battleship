package gui.other;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.board.State;

public class Case extends JPanel {

	private int posX;
	private int posY;
	private int posZ;
	private int state;
	
	public Case(int posX, int posY, int posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		paintCase();
	}
	
	
	
	
	private void paintCase() {
		setPreferredSize(new Dimension(30,30));
		if (posX % 2 == 0) {
			if (posY % 2 == 0) {
				setBackground(new Color(155,155,155));
			}
			else {
				setBackground(new Color(255,255,255));
			}
		}
		else {
			if (posY % 2 == 0) {
				setBackground(new Color(255,255,255));
			}
			else {
				setBackground(new Color(155,155,155));
			}
		}
	}
	
	public void setState(State state) {
		switch (state) {
		case MISSED:
			setBackground(new Color(100,100,200));
			break;
		
		case HIT:
			setBackground(new Color(255,100,0));
			break;
		
		case SUNK:
			setBackground(new Color(255,0,0));
			break;

		default:
			break;
		}
	}
	
	public void addMouseListener() {
		addMouseListener(new MouseListener() {			
			public void mousePressed(MouseEvent e) {
				setState(State.HIT);
			}
			
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
	}
}
