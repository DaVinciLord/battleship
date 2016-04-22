package serie01.model;

import serie01.util.Currency;
import serie01.util.CurrencyId;
import util.Contract;

public class StdMultiConverter implements MultiConverter {
	
	private double amount;
	
	private final Currency[] currency;
	
	public StdMultiConverter(int n) {
		Contract.checkCondition(n >= 2);
		amount = 0.0;
		currency = new Currency[n];
		for (int i = 0; i < n; i++) {
			currency[i] = Currency.get(CurrencyId.EUR);
		}
	}
	
	public double getAmount(int index) {
		Contract.checkCondition(index >= 0 && index < getCurrencyNb(), "");
		return amount * currency[index].getExchangeRate();
	}


	public Currency getCurrency(int index) {
		Contract.checkCondition(index >= 0 && index < getCurrencyNb(), "");
		return currency[index];
	}


	public int getCurrencyNb() {
		return currency.length;
	}

	public double getExchangeRate(int index1, int index2) {
		Contract.checkCondition(index1 >= 0 && index1 < getCurrencyNb(), "");
		Contract.checkCondition(index2 >= 0 && index2 < getCurrencyNb(), "");
		return (getCurrency(index1).getExchangeRate() 
				/ getCurrency(index2).getExchangeRate());
	}

	public void setAmount(int index, double amount) {
		Contract.checkCondition(index >= 0 && index < getCurrencyNb(), "");
		Contract.checkCondition(amount > 0.0, "");
		this.amount = amount;
	}

	public void setCurrency(int index, Currency c) {
		Contract.checkCondition(index >= 0 && index < getCurrencyNb(), "");
		Contract.checkCondition(c != null, "");
		currency[index] = c;
	}

}
