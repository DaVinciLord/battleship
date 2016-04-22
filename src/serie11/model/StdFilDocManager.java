package serie11.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import util.Contract;

public class StdFilDocManager implements FilDocManager {
	private Document doc;
	private File file;
	
	public StdFilDocManager() {
		doc = null;
		file = null;
	}
	
	@Override
	public Document getDocument() {
		return doc;
	}

	@Override
	public File getFile() {
		if (file != null 
				&& (!file.isFile() 
        		|| !file.canRead()
        		|| !file.canWrite())) {
			return null;
		}
		return file;
	}

	@Override
	public State getState() {
		if (getFile() == null) {
			if (doc == null) {
				return State.NOF_NOD;
			}
			return State.NOF_DOC;
		}
		if (doc == null) {
			return State.FIL_NOD;
		}
		return State.FIL_DOC;
	}

	@Override
	public void load() throws IOException, BadLocationException {
		Contract.checkCondition(getState() == State.FIL_DOC, "Mauvais etat");
		BufferedReader bfr = new BufferedReader(new FileReader(file));
		char[] buf = new char[(int) file.length()];
		bfr.read(buf);
		doc.insertString(0, new String(buf), null);
		bfr.close();
	}

	@Override
	public void removeDocument() {
		doc = null;
	}

	@Override
	public void removeFile() {
		file = null;
	}

	@Override
	public void save() throws IOException, BadLocationException {
		Contract.checkCondition(getState() == State.FIL_DOC, "Mauvais etat");
		BufferedWriter bfw;
		bfw = new BufferedWriter(new FileWriter(file));
		char[] buf = new char[doc.getLength()];
		buf = doc.getText(0, doc.getLength()).toCharArray();
		bfw.write(buf);
		bfw.close();
	}

	@Override
	public void setDocument(Document d) {
		Contract.checkCondition(d != null, "Document nul");
		doc = d;
	}

	@Override
	public void setFile(File f) {
		Contract.checkCondition(f != null && f.isFile(), "Document nul");
		file = f;
	}

}
