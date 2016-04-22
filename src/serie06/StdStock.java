package serie06;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import util.Contract;

public class StdStock<E> implements Stock<E> {
	
	private Map<E, Integer> stck;
	
	public StdStock() {
		stck = new TreeMap<E, Integer>();
	}
	
	@Override
	public int getNumber(E e) {
		Contract.checkCondition(e != null, "e  null");
		if (stck.get(e) == null) {
			return 0;
		}
		return stck.get(e).intValue();
	}

	@Override
	public int getTotalNumber() {
		int j = 0;
		for (Integer i : stck.values()) {
			j += i.intValue();
		}
		return j;
	}

	@Override
	public void addElement(E e) {
		Contract.checkCondition(e != null, "e  null");
		stck.put(e, getNumber(e) + 1);
	}

	@Override
	public void addElement(E e, int qty) {
		Contract.checkCondition(e != null, "e null");
		Contract.checkCondition(qty > 0, "qty neg");
		stck.put(e, getNumber(e) + qty);
	}

	@Override
	public void removeElement(E e) {
		Contract.checkCondition(e != null, "e  null");
		Contract.checkCondition(getNumber(e) >= 1,
				"trop peu de " + e.toString());
		stck.put(e, getNumber(e) - 1);
	}

	@Override
	public void removeElement(E e, int qty) {
		Contract.checkCondition(e != null, "e null");
		Contract.checkCondition(getNumber(e) >= qty,
				"trop peu de " + e.toString());
		Contract.checkCondition(qty > 0, "qty neg");
		stck.put(e, getNumber(e) - qty);
	}

	@Override
	public void reset() {
		stck.clear();
	}
	
	public Map<E, Integer> getStock() {
		return Collections.unmodifiableMap(stck);
	}

}
