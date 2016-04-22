package serie11.model;

public class PetException extends Exception {
    
	private static final long serialVersionUID = 2045904631278700049L;

	public PetException() {
        super();
    }
    
    public PetException(String msg) {
        super(msg);
    }
}
