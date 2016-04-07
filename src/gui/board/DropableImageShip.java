package gui.board;

import model.ship.Ship;

/**
 * Interface pour La représentation des vaisseaux.
 * Permet d'ajouter ou de retirer le Ship correspondant de son Board.
 * @inv <pre>
 *     getLength() == getShip().getMaxHP()
 *     dimensions de l'image :
 *     getOrientation() == Ori.HORIZONTAL ==>
 *         getSize().x == getScale() * GraphicBoard.DEFAULT_CASE_SIZE * getLength()
 *         getSize().y == getScale() * GraphicBoard.DEFAULT_CASE_SIZE
 *     getOrientation() == Ori.VERTICAL ==>
 *         getSize().x == getScale() * GraphicBoard.DEFAULT_CASE_SIZE
 *         getSize().y == getScale() * GraphicBoard.DEFAULT_CASE_SIZE * getLength()
 *     isVisible() <==> !getShip().IsPlaced() // à voir, je ne suis pas encore sûr pour ce comportement</pre>
 * @cons <pre>
 *     $DESC$ Constructeur de l'image.
 *     $ARGS$ Ship ship, Ori o
 *     $PRE$ ship != null, o != null
 *     $POST$
 *         getShip() == ship
 *         getOrientation == o
 *         getScale() == 1.f</pre>
 * @author vimard
 */
public interface DropableImageShip {
    
    /**
     * Longueur du Ship représenté.
     */
    int getLength();
    
    /**
     * Échelle du dessin (elle doit correspondre à celle du GraphicBoardLayer
     * pour un rendu visuel cohérent)
     */
    int getScale();
    
    /**
     * Règle l'échelle du dessin.
     */
    void setScale(float scale);
    
    /**
     * Orientation apparente de l'image.
     */
    Ori getOrientation();
    
    /**
     * Pour modifier l'orientation apparente de l'image.
     */
    void setOrientation(Ori o);
    
    /**
     * Le ship représenté sur cette image.
     */
    Ship getShip();
    
    /**
     * Énumération des orientations possibles.
     * Correspondent aux axes X et Y des GraphicBoard.
     */
    enum Ori{VERTICAL, HORIZONTAL};
    
    
}
