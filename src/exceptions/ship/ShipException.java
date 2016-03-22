package exceptions.ship;

import exceptions.BattleshipException;

public class ShipException extends BattleshipException {

    private static final long serialVersionUID = 6082249203125638020L;

    public ShipException(String msg) {
        super(msg);
    }

}
