package serie03.cmd;

import serie03.Text;
import util.Contract;

/**
 * @inv <pre>
 *     1 <= getIndex()
 *     canDo()
 *         ==> getIndex() <= getText().getLinesNb()
 *     canUndo()
 *         ==> getLine() != null
 *             getIndex() <= getText().getLinesNb() + 1 </pre>
 */
public class DeleteLine extends AbstractCommand {

    // ATTRIBUTS
    
    private final int index;
    
    private String line;

    // CONSTRUCTEURS
    
    /**
     * Une commande de suppression de la ligne de rang <code>numLine</code>
     *  dans <code>text</code>.
     * @pre <pre>
     *     text != null
     *     1 <= numLine </pre>
     * @post <pre>
     *     getText() == text
     *     getState() == State.DO
     *     getIndex() == numLine </pre>
     */
    public DeleteLine(Text text, int numLine) {
        super(text);
        Contract.checkCondition(numLine >= 1,
                "le numéro de ligne n'est pas valide (" + numLine + ")");

        index = numLine;
    }
    
    // REQUETES
    
    @Override
    public boolean canDo() {
        return
            super.canDo()
            && index <= getText().getLinesNb();
    }
    
    @Override
    public boolean canUndo() {
        return
            super.canUndo()
            && line != null
            && index <= getText().getLinesNb() + 1;
    }
    
    /**
     * Le rang où l'on doit supprimer la ligne dans le texte.
     */
    int getIndex() {
        return index;
    }
    
    /**
     * La ligne à supprimer.
     */
    String getLine() {
        return line;
    }
    
    // COMMANDES
    
    /**
     * Exécute la suppression d'une ligne dans le texte.
     * @post <pre>
     *     getText().getLinesNb() == old getText().getLinesNb() - 1
     *     getLine().equals(old getText().getLine(getIndex()))
     *     forall i:[getIndex()..getText().getLinesNb()] :
     *         getText().getLine(i).equals(old getText().getLine(i + 1)) </pre>
     */
    @Override
    protected void doIt() {
        Contract.checkCondition(canDo());
        
        line = getText().getLine(index);
        getText().deleteLine(index);
    }
    
    /**
     * Annule la suppression d'une ligne dans le texte.
     * @post <pre>
     *     getText().getLinesNb() == old getText().getLinesNb() + 1
     *     getText().getLine(getIndex()).equals(getLine())
     *     forall i:[getIndex() + 1..getText().getLinesNb()] :
     *         getText().getLine(i).equals(old getText().getLine(i - 1)) </pre>
     */
    @Override
    protected void undoIt() {
        Contract.checkCondition(canUndo());
        
        getText().insertLine(index, line);
    }
}
