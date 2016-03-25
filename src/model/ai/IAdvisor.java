package model.ai;

import java.util.List;

import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

/**
     * Interface spécifiant l'intelligence artificielle qui pourra être utilisée
     * par un joueur humain, ou une AI, et qui permet de calculer un coup à jouer
     * @cons 
     *<pre>
     * $DESC$
     *     Une IA avec un board de State, un board de Case, et une liste de Bateaux
     * $ARGS$
     *     <code>NArray<<code>State</code>></code> hisboard
     *     <code>List<<code>Ship</code>> myships
     * $PRE$
     *     hisboard != null
     *     myship != null
     *     !myship.isEmpty()
     * $POST$
     *   
     *     getEnemyBoard == hisboard
     */



public interface IAdvisor {
    //Valeur pour le mode targeting de l'IA
    public final int MODE_TARGETING = 0;
    //Valeur pour le mode hunting de l'IA
    public final int MODE_HUNTING = 1;
    
    /**
     * Setter du enemyBoard.
     *
     * @param enemyBoard
     */
    public void setEnemyBoard(IBoard<State> enemyBoard);
    
    /**
     * Setter de shipLeft.
     * 
     */
    public void setShipLeft(List<Integer> shipLeft);
    
    
    
    /**
     * Methode permettant de renvoyer le 
     * plateau actuel des positions enemies repérées pas l'IA
     * @post<pre>
     *     Narray != null</pre>
     * @return enemyBoard
     */
    public IBoard<State> getEnemiesBoard();
    
    /**
     * 
     * @return
     *      ships.isEmpty()
     */
    public boolean noShipLeft();
    /**
     * Permet d'obtenir le coup conseillé par l'IA
     * @pre<pre>
     *     !noShipLeft()
     * @post<pre>
     *     getEnemiesBoard().get(coord).getState() == State.NOT_AIMED
     */
    public Coordinates getAdvise();
    
    /**
     * 
     * @return
     *      mode
     */
    public int getMode();

    
    
}