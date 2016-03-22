package exceptions;

public class BattleshipException extends Exception {
    private static final long serialVersionUID = 8341599250437584428L;
    private String msg;

    public BattleshipException(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return msg;
    }
}
