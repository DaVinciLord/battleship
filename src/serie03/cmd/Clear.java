package serie03.cmd;

import java.util.LinkedList;
import java.util.List;

import serie03.Text;
import util.Contract;

/**
 * @inv
 *     getBackup() != null
 * @cons
 *     $ARGS$ Text text
 *     $PRE$ text != null
 *     $POST$
 *         getText() == text
 *         getState() == State.DO
 *         getBackup().size() == 0
 */

public class Clear extends AbstractCommand {
	

    private List<String> backup;
    
    
    public Clear(Text text) {
        super(text);
        backup = new LinkedList<String>();
    }	

	@Override
	protected void doIt() {
        Contract.checkCondition(canDo());
		for (int i = 1; i <= getText().getLinesNb(); i++) {
			backup.add(i - 1, getText().getLine(i));	        
		}
		getText().clear();
	}

	@Override
	protected void undoIt() {
        Contract.checkCondition(canUndo());
		int size = backup.size();
		for (int i = 0; i < size; i++) {
	        getText().insertLine(i + 1, backup.get(i));
		}
		backup.clear();
	}
	
	public List<String> getBackup() {
		return backup;
	}
}
