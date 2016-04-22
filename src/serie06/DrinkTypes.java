package serie06;

public enum DrinkTypes {
	 COFFEE(30), CHOCOLATE(45), ORANGE_JUICE(110);

	private int val;

	DrinkTypes(int i) {
		val = i;
	}
	
	public int getPrice() {
		return val;
	}
	
	public String toString() {
		return this.name().toLowerCase().replace('_', ' ');
	}
}
