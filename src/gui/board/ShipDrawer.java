package gui.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.board.Case;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.ship.IShip;

public class ShipDrawer implements BoardDrawer<Case> {

	@Override
	public void drawOnBoard(Graphics g, IBoard<Case> board, Coordinates axes, float scale, float alpha) {
		// TODO Stub de la méthode généré automatiquement
		BufferedImage img = null;
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
                Coordinates caseCoord = new Coordinates(c);
                IShip ship = board.getItem(caseCoord).getShip();
                if (ship != null) {
                	// on dessine le ship
                	if (!ship.getProue().isOnAxe(axes) || !ship.getPoupe().isOnAxe(axes)) {
                		// cas où le ship est dans le sens de la profondeur.
                	} else { // fin du cas profondeur
                		int[] ori = axes.getCoordinates();
                		ori[axeX] = x;
                		Coordinates horiz = new Coordinates(ori);
                		if (ship.getProue().isOnAxe(horiz) && ship.getPoupe().isOnAxe(horiz)) {
                			// cas où le ship est disposé horizontalement
                			if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                				// on est sur la proue ou la poupe
                				boolean isProue = true;
                				if (caseCoord.get(axeX) == 0) {
                					// on est forcement sur la poupe
                					isProue = false;
                				} else {
                					int[] sideCase = caseCoord.getCoordinates();
                					sideCase[axeX] = caseCoord.get(axeX) - 1;
                					Coordinates sideCoord = new Coordinates(sideCase);
                					isProue = board.getItem(sideCoord).getShip() == ship;
                				}
                				if (isProue) {
                					// dessine la proue
                					try {
										img = ImageIO.read(new File("./ressources/images/useless/ship3-1-r.png"));
										g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
									} catch (IOException e) {
										// TODO Bloc catch généré automatiquement
										e.printStackTrace();
									}
                				} else {
                					// dessine la poupe
                					try {
										img = ImageIO.read(new File("./ressources/images/useless/ship3-3-r.png"));
										g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
									} catch (IOException e) {
										// TODO Bloc catch généré automatiquement
										e.printStackTrace();
									}
                				}
                			} else {
                				// on est sur une section intermédiaire
                				try {
									img = ImageIO.read(new File("./ressources/images/useless/ship3-2-r.png"));
									g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
								} catch (IOException e) {
									// TODO Bloc catch généré automatiquement
									e.printStackTrace();
								}
                			}
                		} else { // fin du cas horizontal
                			// cas où le ship est disposé verticalement
                			if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                				// on est sur la proue ou la poupe
                				if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                    				// on est sur la proue ou la poupe
                    				boolean isProue = true;
                    				if (caseCoord.get(axeY) == 0) {
                    					// on est forcement sur la poupe
                    					isProue = true;
                    				} else {
                    					int[] sideCase = caseCoord.getCoordinates();
                    					sideCase[axeY] = caseCoord.get(axeY) - 1;
                    					Coordinates sideCoord = new Coordinates(sideCase);
                    					isProue = board.getItem(sideCoord).getShip() != ship;
                    				}
                    				if (isProue) {
                    					// dessine la proue
                    					try {
    										img = ImageIO.read(new File("./ressources/images/useless/ship3-1.png"));
    										g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
    									} catch (IOException e) {
    										// TODO Bloc catch généré automatiquement
    										e.printStackTrace();
    									}
                    				} else {
                    					// dessine la poupe
                    					try {
    										img = ImageIO.read(new File("./ressources/images/useless/ship3-3.png"));
    										g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
    												(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
    									} catch (IOException e) {
    										// TODO Bloc catch généré automatiquement
    										e.printStackTrace();
    									}
                    				}
                				}
                			} else {
                				// on est sur une section intermédiaire
                				try {
									img = ImageIO.read(new File("./ressources/images/useless/ship3-2.png"));
									g.drawImage(img, (int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE),
											(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE), null);
								} catch (IOException e) {
									// TODO Bloc catch généré automatiquement
									e.printStackTrace();
								}
                			}
                		} // fin du cas vertical
                	}
                	/*
                	g.setColor(new Color(0.3f, 0.3f, 0.3f, alpha));
                	g.fillRect((int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE) + 2,
                			(int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE) + 2, 
                			(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE) - 2, 
                			(int) (scale * GraphicBoard.DEFAULT_CASE_SIZE) - 2);
                	*/
                } // fin du cas où on a un ship
                if (board.getItem(new Coordinates(c)).getState() != State.NOTAIMED) {
                    // on dessine le tir
                    int diameter = (int) ((scale * GraphicBoard.DEFAULT_CASE_SIZE) / 3);
                    g.setColor(new Color(1.f, 0.f, 0.f, alpha));
                    g.fillOval((int) (x * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                            (int) (y * scale * GraphicBoard.DEFAULT_CASE_SIZE) + diameter,
                            diameter, diameter);
                }
            }
        }
		
	}

	@Override
	public void drawCase(Graphics g, IBoard<Case> board, Coordinates axes, float scale, float alpha,
			Coordinates position) {
		// TODO Stub de la méthode généré automatiquement
		
	}

}
