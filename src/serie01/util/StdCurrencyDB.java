package serie01.util;

import util.Contract;

public class StdCurrencyDB implements CurrencyDB {

	private double[] exchangerate;
	private String[][] base;
	
	
	public StdCurrencyDB() {
		CurrencyId[] tab = CurrencyId.values();
		Contract.checkCondition(tab != null, "");
		exchangerate = new double[tab.length];
		base = new String[tab.length][3];
		for (int i = 0; i < tab.length; i++) {
			exchangerate[i] = tab[i].getRateForYear2001();
			base[i][0] = tab[i].getIsoCode();
			base[i][1] = tab[i].getName();
			base[i][2] = tab[i].getLand();
		}
	}
	public double getExchangeRate(CurrencyId id) {
		Contract.checkCondition(id != null, "");
		return exchangerate[id.ordinal()];
	}


	public String getIsoCode(CurrencyId id) {
		Contract.checkCondition(id != null, "");
		return base[id.ordinal()][0];
	}

	public String getLand(CurrencyId id) {
		Contract.checkCondition(id != null, "");
		return base[id.ordinal()][2];
	}


	public String getName(CurrencyId id) {
		Contract.checkCondition(id != null, "");
		return base[id.ordinal()][1];
	}


	public void setExchangeRate(CurrencyId id, double rate) {
		Contract.checkCondition(id != null && rate > 0, "");
		exchangerate[id.ordinal()] = rate;
	}

}
