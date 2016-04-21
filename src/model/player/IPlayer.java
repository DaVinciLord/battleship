package model.player;


import java.util.List;
import java.util.Map;

import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Case;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;
import model.coordinates.CoordinatesListener;
import model.ship.IShip;
    
    /**
     * Interface modélisant les données physiques nécéssaires au déroulement d'une partie, pour un joueur,
     * c'est à dire un plateau où l'on tire, un plateau où l'on subit des tirs, ainsi qu'un ensemble de bateaux. 
     *  @inv
     *  life >= 0 && life <= sum(forall(Ships : getShips.getMaxLife()))
     *  isAlive == life > 0
     *  isReady au temps t --> isReady au temps t+1
     *  shipNames.get(i) == getShips.get(i).getName();
     * @cons <pre>
     *     $DESC$ Crée un joueur.
     *     $ARGS$ Coordinates dimension, les tailles des différentes dimensions des plateaux
     *     $PRE$
     *          forall(Int i : dimension) {i > getShips.get(getShips.size - 1).getMaxHP()
     *     $POST$
     *          getSelfGrid.getDimension = dimension
     *          getOpponentGrid.getDimension = dimension
     *          forall(Case : myGrid) Case != null
     *          forall(State : myGrid) State == NOTAIMED
     *          getLife = 0
     *          ready = false
     *          forall ( Ship : ships) ShipType.contains(Ship)
     *
     * @cons <pre>
     *     $DESC$ Crée un joueur avec une liste custom de bateaux.
     *     $ARGS$ Coordinates dimension, les tailles des différentes dimensions des plateaux
     *            ShipNal une Table contenant des longueurs de bateaux et leur nom respectif
     *     $PRE$
     *          forall(Int i : dimension) {i > getShips.get(getShips.size - 1).getMaxHP()
     *     $POST$
     *          getSelfGrid.getDimension = dimension
     *          getOpponentGrid.getDimension = dimension
     *          forall(Case : myGrid) Case != null
     *          forall(State : myGrid) State == NOTAIMED
     *          getLife = 0
     *          ready = false
     *          forall ( Ship : ships) ShipNal.contains(Ship)
     *          
     */
    public interface IPlayer {
       
        
        // REQUÊTES
        /**
         * Retourne la somme des segments de bateau encore en jeu
         */
        public int life();
        
        /**
         * Si le joueur possède encore des bateau à flot
         */
        public boolean isAlive();
        
        
        /**
         * Si le joueur est pret à démarrer la partie
         */
        public boolean isReady();
        
        /**
         * La liste des noms des bateaux du joueur
         */
        public List<String> shipNames();
        
        /**
         * Les bateaux du joueur
         */
        public List<IShip> getShips();
        
        /**
         * Bateaux et leurs noms
         */
        public Map<String, IShip> getShipsAndNames();
        
        /**
         * La grille sur la quelle mes bateaux sont.
         */
        public IBoard<Case> getShipGrid();
        
        /**
         * La grille sur laquelle je tire.
         */
        public IBoard<State> getShootGrid();
        
        /**
         * Indique si un ship est placé, en fonction de son nom.
         * @pre shipNames().contains(name);
         */
        public boolean isShipPlaced(String name);
        
        
        /**
         * Indique si tous les ships sont placés.
         */
        public boolean isAllShipPlaced();
        
        // COMMANDES
        
        
        /**
         * Permet de placer un bateau sur le plateau.
         * @pre shipNames.contains(name)
         *      !ready
         * 
         * @param name le nom du bateau à placer
         * @param proue les coordonnées de sa proue
         * @param poupe les coordonnées de sa poupe
         * @throws ShipCaseRaceException
         * @throws ShipBadLengthException
         * @throws ShipOffLimitException
         * @throws ShipNotAlignException
         * 
         * Lance un CoordinatesEvent pour chaque case occupée par le navire, avec getActionType.equals("ship placed")
         */
        public void placeShip(String name, Coordinates proue, Coordinates poupe)
                throws ShipCaseRaceException, ShipBadLengthException,
                ShipOffLimitException, ShipNotAlignException ;
        
        /**
         * Permet de retirer un bateau du plateau.
         * @pre shipNames.contains(name)
         *      !ready
         * 
         * @param name le nom du bateau à retirer
         * 
         * Lance un CoordinatesEvent pour chaque case occupée par le navire, avec getActionType.equals("ship removed")
         */
        public void removeShip(String name);
        
        /**
         * Permet de verrouiller l'usage des fonctions de déplacement de bateau
         * en se définissant comme prêt
         * @pre forall(Ship : ship) Ship.isPlaced
         * 
         */
        public void setReady();
        
        /**
         * Permet de subir un tir.
         * @pre isReady
         *      fire.length == selfGrid.dimensionNb()
         *      old selfGrid.getItem(fire).getState() == State.NOTAIMED
         * @post if selfGrid.get(fire) == HIT || SUNK --> getLife == old getLife - 1   
         * @param fire les coordonnées de l'endroit qui a été visé
         * @return l'état de la case visée après le tir
         * 
         * Lance un CoordinatesEvent pour la case ciblée, avec getActionType.equals("shoot received")
         */
        public State takeHit(Coordinates fire);
        
        /**
         * Met à jour la grille sur la quelle on a tiré.
         * @param fire les coordonnées où l'on viens de tirer
         * @param s l'état de la case retourné par l'autre joueur.
         * 
         * Lance un CoordinatesEvent pour la case ciblée, avec getActionType.equals("shoot fired")
         */
        public void updateFireGrid(Coordinates fire, State s);
        
        /**
         * Ajoute un CoordinatesListener.
         */
        public void addCoordinatesListener(CoordinatesListener cl);
        
        /**
         * Retire un CoordinatesListener.
         */
        public void removeCoordinatesListener(CoordinatesListener cl);
        
        /**
         * Tableau des CoordinatesListener attachés à ce IPlayer.
         */
        public CoordinatesListener[] getCoordinatesListeners();
}
