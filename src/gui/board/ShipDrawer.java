package gui.board;


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
    
    public static final File DEFAULT_DIR = new File("./ressources/images/useless");
    
    // ATTRIBUTS
    
    private File imgDir;
    
    // CONSRUCTEUR
    
    public ShipDrawer(File imgDir) {
        this.imgDir = imgDir;
    }
    
    public ShipDrawer() {
        this(DEFAULT_DIR);
    }
    
    
	@Override
	public void drawOnBoard(Graphics g, IBoard<Case> board, Coordinates axes, float scale, float alpha) {
		BufferedImage img = null;
		int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
		int axeX = 0;
        int axeY = 0;
        for (int k = 0; k < board.dimensionNb(); k++) {
            if (axes.get(k) == -1) {
                axeX = k;
            } else if (axes.get(k) == -2) {
                axeY = k;
            }
        }
        int[] c = axes.getCoordinates();
        for (int x = 0; x < board.getDimensionsSizes().get(axeX); x++) {
            for (int y = 0; y < board.getDimensionsSizes().get(axeY); y++) {
                c[axeX] = x;
                c[axeY] = y;
                Coordinates caseCoord = new Coordinates(c);
                IShip ship = board.getItem(caseCoord).getShip();
                if (ship != null) {
                	if (!ship.getProue().isOnAxe(axes) || !ship.getPoupe().isOnAxe(axes)) {
                		try {
							img = ImageIO.read(new File(imgDir, "ship-cut.png"));
							g.drawImage(img, x * caseSize, y * caseSize,
	                                caseSize, caseSize, null);
						} catch (IOException e) {
							e.printStackTrace();
						}
                	} else { 
                		int[] ori = axes.getCoordinates();
                		ori[axeY] = y;
                		Coordinates horiz = new Coordinates(ori);
                		if (ship.getProue().isOnAxe(horiz) && ship.getPoupe().isOnAxe(horiz)) {
                			if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                				boolean isProue = true;
                				if (caseCoord.get(axeX) == 0) {
                					isProue = false;
                				} else {
                					int[] sideCase = caseCoord.getCoordinates();
                					sideCase[axeX] = caseCoord.get(axeX) - 1;
                					Coordinates sideCoord = new Coordinates(sideCase);
                					isProue = board.getItem(sideCoord).getShip() == ship;
                				}
                				if (isProue) {
                					try {
										img = ImageIO.read(new File(imgDir, "ship3-1-r.png"));
										g.drawImage(img, x * caseSize, y * caseSize,
				                                caseSize, caseSize, null);
									} catch (IOException e) {
										e.printStackTrace();
									}
                				} else {
                					try {
										img = ImageIO.read(new File(imgDir, "ship3-3-r.png"));
										g.drawImage(img, x * caseSize, y * caseSize,
				                                caseSize, caseSize, null);
									} catch (IOException e) {
										e.printStackTrace();
									}
                				}
                			} else {
                				try {
									img = ImageIO.read(new File(imgDir, "ship3-2-r.png"));
									g.drawImage(img, x * caseSize, y * caseSize,
			                                caseSize, caseSize, null);
								} catch (IOException e) {
									e.printStackTrace();
								}
                			}
                		} else { 
                			if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                				if (caseCoord.equals(ship.getProue()) || caseCoord.equals(ship.getPoupe())) {
                    				boolean isProue = true;
                    				if (caseCoord.get(axeY) == 0) {
                    					isProue = true;
                    				} else {
                    					int[] sideCase = caseCoord.getCoordinates();
                    					sideCase[axeY] = caseCoord.get(axeY) - 1;
                    					Coordinates sideCoord = new Coordinates(sideCase);
                    					isProue = board.getItem(sideCoord).getShip() != ship;
                    				}
                    				if (isProue) {
                    					try {
    										img = ImageIO.read(new File(imgDir, "ship3-1.png"));
    										g.drawImage(img, x * caseSize, y * caseSize,
    				                                caseSize, caseSize, null);
    									} catch (IOException e) {
    										e.printStackTrace();
    									}
                    				} else {
                    					try {
    										img = ImageIO.read(new File(imgDir, "ship3-3.png"));
    										g.drawImage(img, x * caseSize, y * caseSize,
    				                                caseSize, caseSize, null);
    									} catch (IOException e) {
    										e.printStackTrace();
    									}
                    				}
                				}
                			} else {
                				try {
									img = ImageIO.read(new File(imgDir, "ship3-2.png"));
									g.drawImage(img, x * caseSize, y * caseSize,
			                                caseSize, caseSize, null);
								} catch (IOException e) {
									e.printStackTrace();
								}
                			}
                		} 
                	}
                } 
                
                // TIRS
                
                State fireState = board.getItem(caseCoord).getState();
                if (fireState == State.MISSED) {
                    try {
                        img = ImageIO.read(new File(imgDir, "water.png"));
                        g.drawImage(img, x * caseSize, y * caseSize,
                                caseSize, caseSize, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (fireState == State.HIT) {
                    try {
                        img = ImageIO.read(new File(imgDir, "hit.png"));
                        g.drawImage(img, x * caseSize, y * caseSize,
                                caseSize, caseSize, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
	}

	@Override
	public void drawCase(Graphics g, IBoard<Case> board, Coordinates axes, float scale, float alpha,
			Coordinates position) {
		BufferedImage img = null;
        int caseSize = (int)((GraphicBoard.DEFAULT_CASE_SIZE * scale));
        int x = 0;
        int y = 0;
        int axeX = 0;
        int axeY = 0;
        for (int k = 0; k < board.dimensionNb(); k++) {
            if (axes.get(k) == -1) {
                x = position.get(k);
                axeX = k;
            } else if (axes.get(k) == -2) {
                y = position.get(k);
                axeY = k;
            }
        }
        
        // NAVIRES
        
        IShip ship = board.getItem(position).getShip();
        if (ship != null) {
        	if (!ship.getProue().isOnAxe(axes) || !ship.getPoupe().isOnAxe(axes)) {
        		try {
					img = ImageIO.read(new File(imgDir, "ship-cut.png"));
					g.drawImage(img, x * caseSize, y * caseSize,
                            caseSize, caseSize, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	} else { 
        		int[] ori = axes.getCoordinates();
        		ori[axeY] = y;
        		Coordinates horiz = new Coordinates(ori);
        		if (ship.getProue().isOnAxe(horiz) && ship.getPoupe().isOnAxe(horiz)) {
        			if (position.equals(ship.getProue()) || position.equals(ship.getPoupe())) {
        				boolean isProue = true;
        				if (position.get(axeX) == 0) {
        					isProue = false;
        				} else {
        					int[] sideCase = position.getCoordinates();
        					sideCase[axeX] = position.get(axeX) - 1;
        					Coordinates sideCoord = new Coordinates(sideCase);
        					isProue = board.getItem(sideCoord).getShip() == ship;
        				}
        				if (isProue) {
        					try {
								img = ImageIO.read(new File(imgDir, "ship3-1-r.png"));
								g.drawImage(img, x * caseSize, y * caseSize,
		                                caseSize, caseSize, null);
							} catch (IOException e) {
								e.printStackTrace();
							}
        				} else {
        					try {
								img = ImageIO.read(new File(imgDir, "ship3-3-r.png"));
								g.drawImage(img, x * caseSize, y * caseSize,
		                                caseSize, caseSize, null);
							} catch (IOException e) {
								e.printStackTrace();
							}
        				}
        			} else {
        				try {
							img = ImageIO.read(new File(imgDir, "ship3-2-r.png"));
							g.drawImage(img, x * caseSize, y * caseSize,
	                                caseSize, caseSize, null);
						} catch (IOException e) {
							e.printStackTrace();
						}
        			}
        		} else { 
        			if (position.equals(ship.getProue()) || position.equals(ship.getPoupe())) {
        				if (position.equals(ship.getProue()) || position.equals(ship.getPoupe())) {
            				boolean isProue = true;
            				if (position.get(axeY) == 0) {
            					isProue = true;
            				} else {
            					int[] sideCase = position.getCoordinates();
            					sideCase[axeY] = position.get(axeY) - 1;
            					Coordinates sideCoord = new Coordinates(sideCase);
            					isProue = board.getItem(sideCoord).getShip() != ship;
            				}
            				if (isProue) {
            					try {
									img = ImageIO.read(new File(imgDir, "ship3-1.png"));
									g.drawImage(img, x * caseSize, y * caseSize,
			                                caseSize, caseSize, null);
								} catch (IOException e) {
									e.printStackTrace();
								}
            				} else {
            					try {
									img = ImageIO.read(new File(imgDir, "ship3-3.png"));
									g.drawImage(img, x * caseSize, y * caseSize,
			                                caseSize, caseSize, null);
								} catch (IOException e) {
									e.printStackTrace();
								}
            				}
        				}
        			} else {
        				try {
							img = ImageIO.read(new File(imgDir, "ship3-2.png"));
							g.drawImage(img, x * caseSize, y * caseSize,
	                                caseSize, caseSize, null);
						} catch (IOException e) {
							e.printStackTrace();
						}
        			}
        		} 
        	}
        }
        
        // TIRS
        
        State fireState = board.getItem(position).getState();
        if (fireState == State.MISSED) {
            try {
                img = ImageIO.read(new File(imgDir, "water.png"));
                g.drawImage(img, x * caseSize, y * caseSize,
                        caseSize, caseSize, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (fireState == State.HIT) { 
            try {
                img = ImageIO.read(new File(imgDir, "hit.png"));
                g.drawImage(img, x * caseSize, y * caseSize,
                        caseSize, caseSize, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
