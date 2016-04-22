package serie04;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

import util.Contract;

public class StdPhoneBook implements PhoneBook {
	
	private TreeMap<Contact, LinkedList<String>> hm;
	
	public StdPhoneBook() {
		hm = new TreeMap<Contact, LinkedList<String>>();
	}
	
	@Override
	public String firstPhoneNumber(Contact p) {
		Contract.checkCondition(p != null && contains(p), "Erreur Contact");
		return phoneNumbers(p).get(0);
	}

	@Override
	public List<String> phoneNumbers(Contact p) {
		Contract.checkCondition(p != null && contains(p), "Erreur Contact");
		return Collections.unmodifiableList(hm.get(p));
	}

	@Override
	public NavigableSet<Contact> contacts() {
		return hm.navigableKeySet();
	}

	@Override
	public boolean contains(Contact p) {
		Contract.checkCondition(p != null, "p null");
		return hm.containsKey(p);
	}

	@Override
	public boolean isEmpty() {
		return hm.isEmpty();
	}

	@Override
	public void addEntry(Contact p, List<String> nums) {
		Contract.checkCondition(p != null && !contains(p), "p null, p présent");
		Contract.checkCondition(nums != null && nums.size() > 0, "err num");
		LinkedList<String> l = new LinkedList<String>();
			for (String str : nums) {
				if (!l.contains(str)) {
					l.add(str);
				}
			}
		hm.put(p, l);
	}

	@Override
	public void addPhoneNumber(Contact p, String n) {
		Contract.checkCondition(p != null, "p null");
		Contract.checkCondition((n != null) && !n.equals(""), "Err n");
		if (!contains(p)) {
			List<String> a = new LinkedList<String>();
			a.add(n);
			addEntry(p, a);
		} else {
			if (!phoneNumbers(p).contains(n)) {
				hm.get(p).add(n);
			}
		}
		
	}

	@Override
	public void removeEntry(Contact p) {
		Contract.checkCondition(p != null && contains(p), "Err p");
		hm.remove(p);
	}

	@Override
	public void deletePhoneNumber(Contact p, String n) {
		Contract.checkCondition((p != null) && contains(p), "Err p");
		Contract.checkCondition((n != null) && !n.equals(""), "Err n");
		if (phoneNumbers(p).size() == 1 && phoneNumbers(p).get(0) == n) {
			removeEntry(p);
		} else {
			hm.get(p).remove(n);
		}
	}

	@Override
	public void clear() {
		hm.clear();
	}

}
