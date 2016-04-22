package serie06;

import util.Contract;


public class StdMoneyAmount extends StdStock<CoinTypes> implements MoneyAmount {

	private static final int[] TAB = {200, 100, 50, 20, 10, 5, 2, 1};

	@Override
	public int getValue(CoinTypes c) {
		Contract.checkCondition(c != null, "c null !");
		return super.getNumber(c) * c.getFaceValue();
	}

	@Override
	public int getTotalValue() {
		int j = 0;
		for (CoinTypes c : super.getStock().keySet()) {
			j += getValue(c);
		}
		return j;
	}

	@Override
	public void addAmount(MoneyAmount amount) {
		Contract.checkCondition(amount != null, "null amount");
		for (CoinTypes c : ((StdMoneyAmount) amount).getStock().keySet()) {
			addElement(c, amount.getNumber(c));		
		}
	}

	@Override
	public MoneyAmount computeChange(int s) {
		Contract.checkCondition(s > 0, "s negatif");
		MoneyAmount ma = new StdMoneyAmount();
		int n = 1;
		for (int c : TAB) {
			while ((s - c >= 0) 
					&& (this.getNumber(CoinTypes.getCoinType(c)) >= n)) {
				s -= c;
				ma.addElement(CoinTypes.getCoinType(c));
				n++;
			}
			n = 1;
		}
		if (s != 0) {
			return null;
		}
		return ma;
	}

	@Override
	public void removeAmount(MoneyAmount amount) {
		Contract.checkCondition(amount != null, "null amount");		
		for (CoinTypes c : ((StdMoneyAmount) amount).getStock().keySet()) {
			removeElement(c, amount.getNumber(c));		
		}
	}

}
