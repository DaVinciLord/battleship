package serie07;

import java.awt.Color;
import java.util.Observable;

/**
 * Notre modèle est une séquence de couleurs (toutes distinctes) que l'on peut
 *  parcourir cycliquement.
 * Les valeurs de getColor() après chaque exécution de changeColor() sont
 *  celles obtenues en parcourant cycliquement le tableau COLORS.
 */
public class StdRainbowModel extends Observable implements RainbowModel {

    // ATTRIBUTS
    
    private static final Color[] COLORS = {
        Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
        Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
        Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
    };
    private int currentColorIndex;

    // CONSTRUCTEURS
    
    /**
     * Un modèle standard, dont la couleur courante est la première couleur
     *  du tableau.
     */
    public StdRainbowModel() {
        currentColorIndex = 0;
    }

    // REQUETES
    
    public Color getColor() {
        return COLORS[currentColorIndex];
    }

    // COMMANDES
    
    public void changeColor() {
        currentColorIndex = (currentColorIndex + 1) % COLORS.length;
        // le modèle prend en compte le fait que son état a changé
        setChanged();
        notifyObservers();
    }
}
