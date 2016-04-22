package serie03;

import java.util.ArrayDeque;
import java.util.Deque;

import util.Contract;

public class StdHistory<E> implements History<E> {
	
	//ATTRIBUTS
	
	private final int maxheight;
	 
	private int current;
	
	private int end;
	
	private Deque<E> pile1;
	
	private Deque<E> pile2;
	
	//CONSTRUCTEUR
	
	public StdHistory(int max) {
		Contract.checkCondition(max > 0, "Random answer");
		maxheight = max;
		current = 0;
		end = 0;
		pile1 = new ArrayDeque<E>(max);
		pile2 = new ArrayDeque<E>(max);
		
	}

	@Override
	public int getMaxHeight() {
		
		return maxheight;
	}

	@Override
	public int getCurrentPosition() {
		return current;
	}

	@Override
	public E getCurrentElement() {
		Contract.checkCondition(getCurrentPosition() > 0, "Euh...");
		return pile1.getLast();
	}

	@Override
	public int getEndPosition() {
		return end;
	}

	@Override
	public void add(E e) {
		Contract.checkCondition(e != null, "Euh ...");
		if (current == maxheight) {
			pile1.pollLast();
		}
		current = Math.min(getCurrentPosition() + 1, getMaxHeight());
		end = getCurrentPosition();
		pile1.push(e); 
	}
	
	@Override
	public void goForward() {
		Contract.checkCondition(getCurrentPosition() < getEndPosition(),
				"Vous êtes à la fin de l'historique");
		current++;
		pile1.push(pile2.pop());
	}

	@Override
	public void goBackward() {
		Contract.checkCondition(getCurrentPosition() > 0,
				"Vous êtes au début de l'historique");
		current--;
		pile2.push(pile1.pop());
	}
	


}
