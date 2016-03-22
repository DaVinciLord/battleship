package exceptions.ship;

public class ShipBadLengthException extends ShipException {

    private static final long serialVersionUID = -6345929802254090903L;
    
    public ShipBadLengthException(String msg) {
        super(msg);
    }
}
