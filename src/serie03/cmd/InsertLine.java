package serie03.cmd;

import serie03.Text;
import util.Contract;
/**
 * @inv
 *     1 <= getIndex()
 *     getLine() != null
 *     canDo() ==> getIndex() <= getText().getLinesNb() + 1
 *     canUndo() ==> getIndex() <= getText().getLinesNb()
 * @cons
 *     $ARGS$ Text text, int numLine, String line
 *     $PRE$
 *         text != null
 *         line != null
 *         1 <= numLine
 *     $POST$
 *         getText() == text
 *         getIndex() == numLine
 *         getLine().equals(line)
 *         getState() == State.DO
 */

public class InsertLine extends AbstractCommand {

    private String line;
    private int place;
    
    
    public InsertLine(Text text, int numLine, String l) {
        super(text);
        Contract.checkCondition(l != null,
                "La ligne est nulle");
        Contract.checkCondition(numLine >= 1,
                "Numero de ligne trop petit");
        Contract.checkCondition(text != null,
                "text null");
        line = l;
        place = numLine;
    }
    
    // REQUETES
    
    @Override
    public boolean canDo() {
        return
            super.canDo()
            && line != null
            && place <= getText().getLinesNb() + 1;
    }
    
    public boolean canUndo() {
        return
            super.canUndo()
            && line != null
            && place <= getText().getLinesNb();
    }
    
    
    int getIndex() {
    	if (canUndo()) {
        return place + 1;
    	}
    	return place;
    }
    
    /**
     * La ligne à inserer.
     */
    String getLine() {
        return line;
    }
    
	protected void doIt() {
        Contract.checkCondition(canDo());
        getText().insertLine(place, line);
	}

	@Override
	protected void undoIt() {
        Contract.checkCondition(canUndo());
        getText().deleteLine(place);
	}

}
