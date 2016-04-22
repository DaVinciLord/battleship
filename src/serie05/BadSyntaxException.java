package serie05;

public class BadSyntaxException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadSyntaxException() {
        super();
    }
    
    public BadSyntaxException(String message) {
        super(message);
    }
}
