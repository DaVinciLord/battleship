package model.ship;

import exceptions.ship.OverPanamaException;
import exceptions.ship.ShipBadLengthException;
import exceptions.ship.ShipCaseRaceException;
import exceptions.ship.ShipNotAlignException;
import exceptions.ship.ShipOffLimitException;
import model.board.Case;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Ship implements IShip {
	
	// ATTRIBUTS
	
	private final IBoard<Case> sea;
    private final String name;
    private int hp;
    private final int maxHp;
    private List<Coordinates> positions;
	/**
	 * Coordonnées de la proue (le mât de Beaupré est celui qui pointe vers l'avant).
	 */
	private Coordinates beaupre;
	
	/**
	 * Coordonnées de la poupe (le mât d'artimont est le plus proche de la poupe).
	 */
	private Coordinates artimont;
	
	
	// CONSTRUCTEUR
    
    public Ship(IBoard<Case> board, ShipType type) throws OverPanamaException {
        if (board == null) {
            throw new AssertionError("il faut un board");
        }
        if (type == null) {
            throw new AssertionError("il faut un type de navire");
        }
        int n = 0;
        for (int k : board.getDimensionsSizes()) {
            if (k > n) {
                n = k;
            }
        }
        if (type.getMaxHP() > n) {
            throw new OverPanamaException("board trop petit pour ce navire");
        }
        sea = board;
        name = type.getName();
        hp = type.getMaxHP();
        maxHp = type.getMaxHP();
    }
    
    public Ship(IBoard<Case> board, int length, String name) {
        if (board == null) {
            throw new AssertionError("il faut un board");
        }
        if (name == null) {
            throw new AssertionError("il faut un nom");
        }
        if (length <= 0) {
            throw new AssertionError("il faut une longueur positive");
        }
        int n = 0;
        for (int k : board.getDimensionsSizes()) {
            if (k > n) {
                n = k;
            }
        }
        if (length > n) {
            throw new AssertionError("Over panama");
        }
        sea = board;
        this.name = name;
        hp = length;
        maxHp = length;
    }
	@Override
	public int getHP() {
		return hp;
	}

	@Override
	public int getMaxHP() {
		return maxHp;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Coordinates> getPosition() {
	    if (positions == null) {
	        return null;
	    }
		return new ArrayList<Coordinates>(positions);
	}
	
	public boolean isPlaced() {
	    return positions != null;
	}
	
	@Override
	public Coordinates getPoupe() {
		return artimont;

	}

	@Override
	public Coordinates getProue() {
		return beaupre;
	}

	

	@Override
	public void setPosition(Coordinates proue, Coordinates poupe)
	        throws ShipBadLengthException, ShipOffLimitException,
			ShipNotAlignException, ShipCaseRaceException
	         {
	    if (positions != null) {
	        throw new AssertionError("navire déjà à flot");
	    }
		// conditions de bonnes dimensions :
		if (proue == null || poupe == null) {
			throw new AssertionError("navire fantôme ?");
		}
		if (proue.length != sea.dimensionNb()
				|| proue.length != poupe.length) {
			throw new AssertionError("navire extra-dimensionnel ?");
		}
		
		int extr = -1; // stockera la coordonnée dim la plus basse de la proue
		int dim = -1; // stockera la dimension du sens du navire

		Coordinates dims = sea.getDimensionsSizes();
		
		for (int k = 0; k < proue.length; k++) {
			// condition de non-dépassement :
			if (proue.get(k) < 0 || proue.get(k) > dims.get(k)) {
				throw new ShipOffLimitException("at world's end (proue)");
			}
			if (poupe.get(k) < 0 || poupe.get(k) > dims.get(k)) {
				throw new ShipOffLimitException("at world's end (poupe)");
			}
			// condition d'alignement et de taille
			if (proue.get(k) != poupe.get(k)) {
				if (dim == -1) {
					if (Math.abs(proue.get(k) - poupe.get(k)) + 1 != getMaxHP()) {
						throw new ShipBadLengthException("mauvaise taille");
					}
					dim = k;
					extr = Math.min(proue.get(k), poupe.get(k));
				} else {
					throw new ShipNotAlignException("garde le cap, moussaillon");
				}
			}
		}
		// traitement spécial si navires de longueur 1 (aucune coordonnée différente entre proue et poupe) :
		if (extr == -1) {
			if (getMaxHP() != 1) {
				throw new ShipBadLengthException("mauvaise taille (pas 1)");
			} else {
				dim = 0;
				extr = proue.get(0);
			}
		}
		
		// Vérification de non chevauchement
		List<Coordinates> sections = new ArrayList<Coordinates>();
		int[] varcoordtab;
		for (int k = 0; k < getMaxHP(); k++) {
			varcoordtab = proue.getCoordinates();
			varcoordtab[dim] = extr + k;
			Coordinates varcoord = new Coordinates(varcoordtab);
			if (sea.getItem(varcoord).getShip() != null) {
				throw new ShipCaseRaceException("Eperonnage !");
			}
			sections.add(varcoord);
		}
		
		// enfin on place le navire :
		for (Coordinates coord : sections) {
			sea.getItem(coord).setShip(this);
		}
		positions = sections;
		beaupre = proue;
		artimont = poupe;
	}

	@Override
	public IBoard<Case> getBoard() {
		return sea;
	}

    @Override
    public void removePosition() {
        if (positions == null) {
            throw new AssertionError("navire déjà en cale sèche");
        }
        for (Coordinates coord : positions) {
            sea.getItem(coord).setShip(null);
        }
        positions = null;
        beaupre = null;
        artimont = null;
    }

	@Override
	public State takeHit() {
		if (hp == 0) {
			throw new AssertionError("navire déjà coulé");
		}
		hp--;
		return hp == 0 ? State.SUNK : State.HIT;
	}

}
