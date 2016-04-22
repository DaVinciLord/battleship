package serie03;

import serie03.cmd.Clear;
import serie03.cmd.Command;
import serie03.cmd.DeleteLine;
import serie03.cmd.InsertLine;
import util.Contract;

public class StdEditor implements Editor {


    private History<Command> hstr;
	private Text text;
	
	public StdEditor(int hs) {
		Contract.checkCondition(hs > 0, "hs null");
		hstr = new StdHistory<Command>(hs);
		text = new StdText();
	}
	
	public StdEditor() {
		this(DEFAULT_HISTORY_SIZE);
	}
	
	
	
	@Override
	public int getTextLinesNb() {
		return text.getLinesNb();
	}

	@Override
	public String getTextContent() {
		String bla = "";
				for (int i = 0; i < getTextLinesNb(); i++) {
					bla += text.getLine(i) + "\n";
				}			
		return bla;
	}

	@Override
	public int getHistorySize() {
		return hstr.getMaxHeight();
	}

	@Override
	public int nbOfPossibleUndo() {
		return hstr.getCurrentPosition();
	}

	@Override
	public int nbOfPossibleRedo() {
		return hstr.getEndPosition() - hstr.getCurrentPosition();
	}

	@Override
	public void clear() {
		Command c = new Clear(text);
		hstr.add(c);
		c.act();
	}

	@Override
	public void insertLine(int numLine, String s) {
		Contract.checkCondition(s != null && numLine >= 1 
				&& numLine <= getTextLinesNb() + 1, "Arg error insertline");
		Command c = new InsertLine(text, numLine, s);
		hstr.add(c);
		c.act();
	}

	@Override
	public void deleteLine(int numLine) {
		Contract.checkCondition(numLine >= 1 
				&& numLine <= getTextLinesNb(),
				"Arg error deleteline");
		Command c = new DeleteLine(text, numLine);
		hstr.add(c);
		c.act();
	}

	@Override
	public void redo() {
		Contract.checkCondition(nbOfPossibleRedo() > 0, "Erreur redo");
		hstr.goForward();
		hstr.getCurrentElement().act();
	}

	@Override
	public void undo() {
		Contract.checkCondition(nbOfPossibleUndo() > 0, "Erreur undo");
		hstr.getCurrentElement().act();
		hstr.goBackward();
	}

}
