package gui.board;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

public class ShootDrawer implements BoardDrawer<State> {

	
	public void drawOnBoard(Graphics g, IBoard<State> board,
            Coordinates axes, float scale, float alpha) {
        // TODO Stub de la méthode généré automatiquement
        int axeX = 0;
        int axeY = 0;
        // on repère sur quelle tranche de board on est (et son orientation)
        for (int k = 0; k < board.dimensionNb(); k++) {
            if (axes.get(k) == -1) {
                axeX = k;
            } else if (axes.get(k) == -2) {
                axeY = k;
            }
        }
        // on parcourt la tranche
        int[] c = axes.getCoordinates();
        for (int x = 0; x < board.getDimensionsSizes().get(axeX); x++) {
            for (int y = 0; y < board.getDimensionsSizes().get(axeY); y++) {
                c[axeX] = x;
                c[axeY] = y;
             // Si le tir n'a abouti sur aucun bateau
                if (board.getItem(new Coordinates(c)) == State.MISSED) {
                	BufferedImage img = null;
                    try {
						img = ImageIO.read(new File("./ressources/images/useless/water.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
                    g.drawImage(img, (int)(x * caseSize),
                    				 (int)(y * caseSize),
                    				 (int)((x * caseSize) + caseSize),
                    				 (int)((y * caseSize) + caseSize),
                    				 0, 0, img.getWidth(), img.getHeight(), null);
                }
             // Si le tir a abouti sur un bateau
                else if (board.getItem(new Coordinates(c)) == State.HIT) {
                	BufferedImage img = null;
                    try {
						img = ImageIO.read(new File("./ressources/images/useless/hit.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
                    g.drawImage(img, (int)(x * caseSize),
                    				 (int)(y * caseSize),
                    				 (int)((x * caseSize) + caseSize),
                    				 (int)((y * caseSize) + caseSize),
                    				 0, 0, img.getWidth(), img.getHeight(), null);
                }
               
            }
        }
        
    }

	public void drawCase(Graphics g, IBoard<State> board,
            Coordinates axes, float scale, float alpha,
            Coordinates position) {
        // TODO Stub de la méthode généré automatiquement
        if (board.getItem(position) != State.NOTAIMED) {
            // on cherche les coordonnées "graphiques" de la case
            int x = 0;
            int y = 0;
            for (int k = 0; k < board.dimensionNb(); k++) {
                if (axes.get(k) == -1) {
                    x = position.get(k);
                } else if (axes.get(k) == -2) {
                    y = position.get(k);
                }
            }
            // Si le tir n'a abouti sur aucun bateau
            if (board.getItem(position) == State.MISSED) {
            	BufferedImage img = null;
                try {
					img = ImageIO.read(new File("./ressources/images/useless/water.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
                g.drawImage(img, (int)(x * caseSize),
                				 (int)(y * caseSize),
                				 (int)((x * caseSize) + caseSize),
                				 (int)((y * caseSize) + caseSize),
                				 0, 0, img.getWidth(), img.getHeight(), null);
            }
            
            // Si le tir a abouti sur un bateau
            else if (board.getItem(position) == State.HIT) {
            	BufferedImage img = null;
                try {
					img = ImageIO.read(new File("./ressources/images/useless/hit.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
                g.drawImage(img, (int)(x * caseSize),
                				 (int)(y * caseSize),
                				 (int)((x * caseSize) + caseSize),
                				 (int)((y * caseSize) + caseSize),
                				 0, 0, img.getWidth(), img.getHeight(), null);
            }
        }
        
    }

}
