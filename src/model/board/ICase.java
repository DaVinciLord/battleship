package model.board;

import model.ship.IShip;

/**
 * @TODO GREG FAIT LA DOC !!!
 */
public interface ICase {

    // REQUETES
    public State getState();
    public IShip getShip();

    // COMMANDES
    public void setShip(IShip ship);

    // correspond à un tir (à ajouter après)
    public State fireAt();
}