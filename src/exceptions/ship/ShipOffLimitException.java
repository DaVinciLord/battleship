package exceptions.ship;

public class ShipOffLimitException extends ShipException {

    private static final long serialVersionUID = 6186134227769009572L;
    
    public ShipOffLimitException(String msg) {
        super(msg);
    }
}
