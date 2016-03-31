package gui.other;

public class Chrono {

	private static long chrono = 0 ;
	
	public Chrono() {
		start();
		stop();
	}

	static void start() {
	chrono = System.currentTimeMillis() ;
	}

	static void stop() {
	long chrono2 = System.currentTimeMillis() ;
	long temps = chrono2 - chrono ;
	System.out.println("Temps ecoule = " + temps + " ms") ;
	} 
	
	public static void main(String[] args) throws InterruptedException {
		Chrono chrono = new Chrono();
	}
	
}

