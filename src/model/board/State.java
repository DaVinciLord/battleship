package model.board;

public enum State {
    
    NOTAIMED("not aimed"),
    MISSED("missed"),
    HIT("hit"),
    SUNK("sunk");
    
	private String name;
	
	State(String s) {
		name = s;
	}
    
    public State getState() {
        return this;
    }
    
    public String toString() {
    	return name;
    }
}
