package serie06;

import util.Contract;

public class StdDrinksMachineModel implements DrinksMachineModel {
	
	private Stock<DrinkTypes> drinkStock;
	private MoneyAmount cashBox;
	private MoneyAmount changeBox;
	private MoneyAmount credit;
	private DrinkTypes dt;
	
	
	public StdDrinksMachineModel() {
		drinkStock = new StdStock<DrinkTypes>();
		for (DrinkTypes d : DrinkTypes.values()) {
			drinkStock.addElement(d, MAX_DRINK);
		}
		cashBox = new StdMoneyAmount();
		changeBox = new StdMoneyAmount();		
		credit = new StdMoneyAmount();
		dt = null;
		}

	@Override
	public int getDrinkNb(DrinkTypes d) {
		Contract.checkCondition(d != null, "d null");
		return drinkStock.getNumber(d);
	}

	@Override
	public DrinkTypes getLastDrink() {
		return dt;
	}

	@Override
	public int getCreditAmount() {
		return credit.getTotalValue();
	}

	@Override
	public int getCreditNb(CoinTypes c) {
		Contract.checkCondition(c != null, "c null");
		return credit.getNumber(c);
	}

	@Override
	public int getCashAmount() {
		return cashBox.getTotalValue();
	}

	@Override
	public int getCashNb(CoinTypes c) {
		Contract.checkCondition(c != null, "c null");
		return cashBox.getNumber(c);
	}

	@Override
	public int getChangeAmount() {
		return changeBox.getTotalValue();
	}

	@Override
	public int getChangeNb(CoinTypes c) {
		Contract.checkCondition(c != null, "c null");
		return changeBox.getNumber(c);
	}

	@Override
	public boolean canGetChange() {
		CoinTypes[] ct = CoinTypes.values();
		for (int i = 1; i < ct[ct.length - 1].getFaceValue(); i++) {
			if (cashBox.computeChange(i) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void selectDrink(DrinkTypes d) {
		Contract.checkCondition(d != null, "d null");
		Contract.checkCondition(getDrinkNb(d) >= 1,
				"trop peu de " + d.toString());
		Contract.checkCondition(getCreditAmount() >= d.getPrice(),
				"Trop peu de cash inséré ");
		Contract.checkCondition(getLastDrink() == null,
				"Prenez votre precedante boisson");
		drinkStock.removeElement(d);
		dt = d;
		if (canGetChange()) {
			cashBox.addAmount(credit);
			MoneyAmount ma = cashBox.computeChange(getCreditAmount() 
					- d.getPrice());
			changeBox.addAmount(ma);
			cashBox.removeAmount(ma);
		} else { 
			cashBox.addAmount(credit);
		}
		credit.reset();
		
	}

	@Override
	public void fillStock(DrinkTypes d, int q) {
		Contract.checkCondition(d != null, "d null");
		Contract.checkCondition(q > 0 && q + getDrinkNb(d) 
		<= MAX_DRINK, "Erreur de remplissage");
		drinkStock.addElement(d, q);
	}

	@Override
	public void fillCash(CoinTypes c, int q) {
		Contract.checkCondition(c != null, "c null");
		Contract.checkCondition(q > 0 && q + getCashNb(c) 
		<= MAX_COIN, "Erreur de remplissage");
		cashBox.addElement(c, q);
	}

	@Override
	public void insertCoin(CoinTypes c) {
	     if (getCashNb(c) + getCreditNb(c) == MAX_COIN) {
	    	 changeBox.addElement(c);
	     } else {
	    	 credit.addElement(c);
	     }
	}

	@Override
	public void cancelCredit() {
		changeBox.addAmount(credit);
		credit.reset();
	}

	@Override
	public void takeDrink() {
		dt = null;
	}

	@Override
	public void takeChange() {
		changeBox.reset();
	}

	@Override
	public void reset() {
		changeBox.reset();
		credit.reset();
		cashBox.reset();
		drinkStock.reset();
		dt = null;
	}

}
