package serie04;

import util.Contract;

public enum Civ {
	UKN(""), MR("M."), MRS("Mme"), MS("Mlle");
	
	private String desc;
	
	private Civ(String s) {
		desc = s;
	}
    /**
     * @pre
     *     candidate != null
     * @post
     *     this == UKN ==> result == { MR, MRS, MS }.contains(candidate)
     *     this == MR  ==> result == false
     *     this == MRS ==> result == (candidate == MS)
     *     this == MS  ==> result == (candidate == MRS)
     */
    public boolean canEvolveTo(Civ candidate) {
    	Contract.checkCondition(candidate != null, "Votre candidat est null");
    	switch (this) {
    	case UKN : 
    	return (candidate == MR || candidate == MRS || candidate == MS);
    	case MR :
    	return false;
    	case MRS :
    	return (candidate == MS);
    	case MS :
        return (candidate == MRS);
    	default : 
    		throw new AssertionError("Civilité inconnue");
    	}
    	
    }
    /**
     * @post
     *     this == UKN ==> result.equals("")
     *     this == MR  ==> result.equals("M.")
     *     this == MRS ==> result.equals("Mme")
     *     this == MS  ==> result.equals("Mlle")
     */
    public String toString() {
    	return new String(desc);
    }
		
}
