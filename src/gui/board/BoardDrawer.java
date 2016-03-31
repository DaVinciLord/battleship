package gui.board;

import java.awt.Graphics;

import model.board.IBoard;
import model.coordinates.Coordinates;

public interface BoardDrawer<E> {
    
    /**
     * Dessine le contenu d'un GraphicBoard selon ces éléments
     * @param g
     *     Le contexte graphique du GraphicBoard (qu'il faudra utiliser pour
     *     dessiner).
     * @param board
     *     Le modèle représenté (en partie) par le GraphicBoard.
     * @param axes
     *     Les axes et les coordonnées fixes indiquant quelle partie de
     *     board est représentée par le GraphicBoard (voir les commentaires du
     *     constructeur de GraphicBoard).
     * @param scale
     *     Un facteur d'échelle utile pour un effet de perspective :
     *     scale == 1.f <==> taille des cases == GraphicBoard.CASE_SIZE
     */
    void drawOnBoard(Graphics g, IBoard<E> board, Coordinates axes, float scale);

}
