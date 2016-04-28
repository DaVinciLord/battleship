package model.board;

import model.ship.IShip;

public class Case {
	
	// ATTRIBUTS
	
	private IShip ship;
	private boolean isAimed;
	
	// CONSTRUCTEUR
	
	public Case() {
		ship = null;
		isAimed = false;
	}
	
	// REQUETES
	
	public State getState() {
		if (isAimed) {
			if (ship != null) {
				return State.HIT;
			}
			return State.MISSED;
		}
		return State.NOTAIMED;
	}
	
	public IShip getShip() {
		return ship;
	}
	
	// COMMANDES
	
	public void setShip(IShip ship) {
		this.ship = ship;
	}
	
	// correspond à un tir (à ajouter après)
	public State fireAt() {
		isAimed = true;
		if (ship != null) {
			return ship.takeHit();
		} else {
			return State.MISSED;
		}
	}
}
