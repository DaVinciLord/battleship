package model.ship;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Board;
import model.board.Case;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

import java.util.List;

/**
 * Modélise un vaisseau pour bataille navale en nombre de dimensions quelconque.
 * @inv <pre>
 *     0 <= getHP() <= getMaxHP()
 *     IF getPosition() != null :
 *         (getProue().equals(getPosition().get(0))
 *         && getPoupe().equals(getPosition().get(getPosition().size() - 1)))
 *         ||
 *         (getPoupe().equals(getPosition().get(0))
 *         && getProue().equals(getPosition().get(getPosition().size() - 1)))
 *         forall int[] coord :
 *             getBoard().getItem(coord).getShip() == this <==>
 *                 getPositions().contains(coord)
 *     ELSE
 *         getProue() == getPoupe() == null
 *         forall int[] coord :
 *             getBoard().getItem(coord).getShip() != this
 *     getBoard() != null</pre>
 * @cons <pre>
 *     $DESC$ Crée un vaisseau.
 *     $ARGS$ NArray/<Case/> board, ShipType type
 *     $PRE$
 *         board != null
 *         type != null
 *     $POST$
 *         getHP() == getMaxHP() == type.getMaxHP()
 *         getName().equals(type.getName())
 *         getPosition() == null
 *         getBoard() == board </pre>
 */
public interface IShip {
    
    /**
     * Nommbre de points de vie du vaisseau.
     */
	public int getHP();
	
	/**
	 * Nombre de points de vie initial du vaisseau, correspondant à sa longueur.
	 */
	public int getMaxHP();
	
	/**
	 * nom du vaisseau.
	 */
	public String getName();
	
	/**
	 * coordonnées occupées par le vaisseau.
	 */
	public List<Coordinates> getPosition();
	
	/**
	 * coordonnées de l'extrémité arrière du vaisseau.
	 */
	public Coordinates getPoupe();
	
	/**
     * coordonnées de l'extrémité avant du vaisseau.
     */
	public Coordinates getProue();
	
	/**
	 * "plateau" de jeu du vaisseau.
	 */
	public IBoard<Case> getBoard();
	
	/**
	 * Indique si le vaisseau est placé sur son getBoard();
	 */
	boolean isPlaced();
	
	
	/**
	 * Placement du vaisseau.
	 * @throws ShipCaseRaceException 
	 * @throws ShipBadLengthException 
	 * @throws ShipOffLimitException 
	 * @throws ShipNotAlignException 
	 * @pre <pre>
	 *     getPosition() == null
	 *     proue != null
	 *     poupe != null
	 *     Condition de bonne dimension :
	 *     proue.length == poupe.length == getBoard().dimensionNb() </pre>
	 * 
	 * Conditions pour éviter les throws
	 *     Condition d'alignement et de longueur :
	 *     Il existe k unique tel que :
	 *         Math.abs(proue[k] - Poupe[k]) + 1 == getMaxHP()
	 *         pour tout i != k proue[i] == poupe[i]
	 *     Condition de non chevauchement :
	 *     pour tout int[] coord tel que coord[k] compris entre proue[k] et 
	 *     poupe[k] et coord[i] == proue[i] pour tout i != k :
	 *         getBoard().getItem(coord).getShip() == null 
	 * @post <pre>
	 *     Si une exception a été levée, rien n'a changé. Sinon :
	 *     pour tout int[] coord tel que coord[k] compris entre proue[k] et 
     *     poupe[k] et coord[i] == proue[i] pour tout i != k :
     *         getBoard().getItem(coord).getShip() == this </pre>
	 */
	public void setPosition(Coordinates proue, Coordinates poupe)
	        throws ShipCaseRaceException, ShipBadLengthException,
			ShipOffLimitException, ShipNotAlignException;
	
	/**
	 * Enlève le vaisseau.
	 * @pre <pre>
	 *     getPosition() != null </pre>
	 * @post <pre>
	 *     getPosition() == null </pre>
	 */
	public void removePosition();
	
	/**
	 * Fait perdre un point de vie au vaisseau.
	 * @pre <pre>
	 *     getHP() > 0 </pre>
	 * @post <pre>
	 *     getHP() = old getHP() - 1 
	 *     IF getHP() == 0 return State.SUNK
	 *     ELSE return State.HIT </pre>
	 */
	public State takeHit();
	
}
