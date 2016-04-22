package serie07;

import java.util.Observable;

public class StdSlotModel extends Observable implements SlotModel {
	
	private int lpo;
	
	private int  moneylost;
	
	private int moneywon;
	
	private char[] result = {' ', ' ', ' '};
	
	private static final int MAXWIN = 300;
	
	private static final int MINWIN = 5;

	public StdSlotModel() {
		moneylost = 0;
		moneywon = 0;
		lpo = 0;
	}
	
	
	@Override
	public int lastPayout() {
		return lpo;
	}

	@Override
	public int moneyLost() {
		return moneylost;
	}

	@Override
	public int moneyWon() {
		return moneywon;
	}

	@Override
	public String result() {
		return "" + result[0] + result[1] + result[2];
	}

	@Override
	public void gamble() {
		moneylost += 1;
		lpo = 0;
		for (int a = result.length - 1; a >= 0; a--) {
			result[a] = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
		}
		
		if (result[0] == result[1] && result[1] == result[2]) {
			lpo = MAXWIN;
			moneywon += lpo;
		return;
		}
		if (result[0] == result[1] 
				|| result[1] == result[2] 
						|| result[0] == result[2]) {
			lpo = MINWIN;
			moneywon += lpo;
		}
		
	}

}
