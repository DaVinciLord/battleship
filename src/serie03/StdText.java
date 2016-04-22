package serie03;

import java.util.LinkedList;

import util.Contract;

public class StdText implements Text {
    
    // ATTRIBUTS
    
    private final LinkedList<String> lines;

    // CONSTRUCTEURS
    
    public StdText() {
        lines = new LinkedList<String>();
    }

    // REQUETES
    
    public int getLinesNb() {
        return lines.size();
    }
    
    public String getLine(int i) {
        Contract.checkCondition((i >= 1) && (i <= getLinesNb()),
                "mauvais numéro de ligne : " + i);
        
        return lines.get(i - 1);
    }
    
    public String getContent() {
        StringBuffer result = new StringBuffer();
        for (String s : lines) {
            result.append(s + NL);
        }
        return result.toString();
    }

    // COMMANDES
    
    public void insertLine(int numLine, String s) {
        Contract.checkCondition((numLine >= 1) && (numLine <= getLinesNb() + 1),
                "mauvais numéro de ligne : " + numLine);
        Contract.checkCondition(s != null,
                "la chaîne fournie n'existe pas");
        
        lines.add(numLine - 1, s);
    }
    
    public void deleteLine(int numLine) {
        Contract.checkCondition((numLine >= 1) && (numLine <= getLinesNb()),
                "mauvais numéro de ligne : " + numLine);
        
        lines.remove(numLine - 1);
    }
    
    public void clear() {
        lines.clear();
    }
}
