package serie04;
import util.Contract;

public class StdContact implements Contact {

	private Civ civilite;
	private String nom;
	private String prenom;
	
	public StdContact(String n, String p) {
		Contract.checkCondition(n != null, "n null");
		Contract.checkCondition(p != null, "p null");
		Contract.checkCondition(!p.equals("") || !n.equals(""), "p et n vide");
		nom = n;
		prenom = p;
		civilite = Civ.UKN;
	}

	public StdContact(Civ c, String n, String p) {
		Contract.checkCondition(n != null, "n null");
		Contract.checkCondition(p != null, "p null");
		Contract.checkCondition(!p.equals("") || !n.equals(""), "p et n vide");
		Contract.checkCondition(c != null, "c null");
		nom = n;
		prenom = p;
		civilite = c;
	}
	
	
	@Override
	public int compareTo(Contact c) {
		if (c == null) {
			throw new NullPointerException(); 
		}
		int a = -(c.getLastName().compareTo(this.getLastName()));
		if (a == 0) {
			a = -(c.getFirstName().compareTo(this.getFirstName()));
		if (a == 0) {
			a = -(c.getCivility().compareTo(this.getCivility()));
		}
		}
		return a;
	}
	
	public boolean equals(Object obj) {	
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		return (compareTo((StdContact) obj) == 0);
	}

	@Override
	public Civ getCivility() {
		return civilite;
	}

	@Override
	public String getFirstName() {
		return new String(prenom);
	}

	@Override
	public String getLastName() {
		return new String(nom);
	}

	@Override
	public void setCivility(Civ civility) {
		Contract.checkCondition(civility != null 
				&& getCivility().canEvolveTo(civility), "Civilté nulle");
		civilite = civility;
	}
	
	public String toString() {
	
	return civilite.toString() + " " + prenom + " " + nom;
	}
	
	public int hashCode() {
		return nom.hashCode() + prenom.hashCode();
	}

}
