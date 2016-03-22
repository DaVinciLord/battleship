package model.ship;

public enum ShipType {
    AIRCRAFT(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");
    
    private int size;
    private String name;
    
    ShipType(int size, String name) {
        this.size = size;
        this.name = name;
    }
    
    public int getMaxHP() {
        return size;
    }
    
    public String getName() {
    	return name;
    }
}
