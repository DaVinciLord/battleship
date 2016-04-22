package serie09;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;


public class GraphicSpeedometer extends JComponent {
    // ATTRIBUTS

	private static final long serialVersionUID = 1L;
	private SpeedometerModel model;	
    // marge horizontale interne de part et d'autre du composant
    private static final int MARGIN = 40;
    // épaisseur de la ligne horizontale graduée
    private static final int THICK = 3;
    // demi-hauteur d'une graduation
    private static final int MARK = 5;
    // largeur de la base du triangle pour la tête de la flèche
    private static final int ARROW_BASE = 20;
    // épaisseur du corps de la flèche
    private static final int ARROW_THICK = 4;
    // hauteur du corps de la flèche
    private static final int ARROW_HEIGHT = 20;
    // facteur d'échelle pour l'espacement entre deux graduations
    private static final double ASPECT_RATIO = 1.25;
    // couleur bleu franc lorsque le moteur est allumé
    private static final Color BLUE = Color.BLUE;
    // couleur rouge franc lorsque le moteur est allumé
    private static final Color RED = Color.RED;
    // couleur bleu grisé lorsque le moteur est éteint
    private static final Color GRAYED_BLUE = new Color(0, 0, 255, 50);
    // couleur rouge grisé lorsque le moteur est éteint
    private static final Color GRAYED_RED = new Color(255, 0, 0, 50);
    // les vitesses affichées sont celles, entre 0 et
    //model.getMaxSpeed(), qui sont les multiples de SPLIT_SIZE
    private static final int SPLIT_SIZE = 10;


    // CONSTRUCTEURS
    public GraphicSpeedometer(SpeedometerModel m) {
    	super();
    	model = m;    
    	model.addObserver(new Observer() {
        public void update(Observable o, Object arg) {
        	repaint();
        }
    });
    }

    
    // OUTILS
    protected void paintComponent(Graphics g) {
        final int frameWidth = 3 * (3 * MARK + ARROW_BASE / 2 + ARROW_HEIGHT);
        final int frameHeight = (int) Math.floor(ASPECT_RATIO * ARROW_BASE
        		* (model.getMaxSpeed() / SPLIT_SIZE) + 2 * MARGIN);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
		int w = (int) Math.floor(getHeight() / 3);
		int h = (int) Math.floor(getWidth() - 2 * MARGIN);
		int xA = (int) Math.floor((MARGIN + (w * model.getSpeed()) 
				/ model.getMaxSpeed() - ARROW_BASE / 2));
        int yA = (int) Math.floor(h + THICK + 2 * MARK + ARROW_BASE / 2);
        int xB = xA + ARROW_BASE / 2;
        int yB = yA - ARROW_BASE / 2;
        int xC = xA + ARROW_BASE;
        int yC = yA;
        int xP = xB - ARROW_THICK / 2;
        int yP = yA;
        int[] xtab = {xA, xB, xC};
        int[] ytab = {yA, yB, yC};       
        g.setColor(model.isOn() ? RED : GRAYED_RED);
        g.fillPolygon(xtab, ytab, 3);
        g.fillRect(xP, yP, ARROW_THICK, ARROW_HEIGHT);
        g.setColor(model.isOn() ? BLUE : GRAYED_BLUE);
        g.fillRect(MARGIN, h, w, THICK);
        FontMetrics fm = g.getFontMetrics();

        for (int i = 0; i <= model.getMaxSpeed() / SPLIT_SIZE; i++) { 
        	String s = String.valueOf(i * SPLIT_SIZE);
        	int sWidth = fm.stringWidth(s);
            int xQ = (int) Math.floor(MARGIN + i * w * SPLIT_SIZE 
            		/ model.getMaxSpeed() - sWidth / 2);
            int yQ = h - 2 * MARK;
            g.drawLine(xQ + sWidth / 2, yQ + MARK, xQ + sWidth 
            		/ 2, yQ + 3 * MARK);
            g.drawString(s, xQ, yQ);
        }
        }
    


}
