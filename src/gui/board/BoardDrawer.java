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
     * @param alpha
     *     l'opacité que doit avoir le dessin.
     */
    void drawOnBoard(Graphics g, IBoard<E> board, Coordinates axes, float scale, float alpha);
    
    /**
     * Dessine le contenu d'une case du GraphicBoard selon ces éléments.
     * À utiliser pour mettre à jour une case.
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
     * @param alpha
     *     l'opacité que doit avoir le dessin.
     * @param position
     *     La position de la case.
     */
    void drawCase(Graphics g, IBoard<E> board, Coordinates axes, float scale, float alpha, Coordinates position);

}
