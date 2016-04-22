package serie11.model;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

import util.Contract;

public class StdPetModel extends Observable implements PetModel {

	private FilDocManager fdm;
	private FilDocManager oldfdm;
	
	public StdPetModel() {
		fdm = new StdFilDocManager();
		oldfdm = new StdFilDocManager();
	}
	
	@Override
	public Document getDocument() {
		this.setChanged();
		this.notifyObservers();
		return fdm.getDocument();
	}

	@Override
	public File getFile() {
		return fdm.getFile();
	}

	@Override
	public State getState() {
		return fdm.getState();
	}

	@Override
	public boolean isSynchronized() {
		this.setChanged();
		this.notifyObservers();
		if (fdm.getState() == State.FIL_DOC) {
			if (oldfdm.getState() == State.FIL_DOC) {
				return fdm.getDocument().equals(oldfdm.getDocument());
			}
		}
		return false;
	}

	@Override
	public void clearDoc() throws PetException {
		Contract.checkCondition(getDocument() != null, "null doc");
		fdm.setDocument(new DefaultStyledDocument());
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void removeDocAndFile() {
		Contract.checkCondition(getDocument() != null, "null doc");
		fdm.removeDocument();
		fdm.removeFile();
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void resetCurrentDocWithCurrentFile() throws PetException {
		Contract.checkCondition(getState() == State.FIL_DOC, "Mauvais State");
		Contract.checkCondition(!isSynchronized(), "Pas syncro");
		try {
			fdm.setDocument(new DefaultStyledDocument());
			fdm.load();
		} catch (IOException | BadLocationException e) {	
			e.printStackTrace();
			throw new PetException();
		}
		oldfdm.setDocument(fdm.getDocument());
		oldfdm.setFile(fdm.getFile());
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void saveCurrentDocIntoCurrentFile() throws PetException {
		Contract.checkCondition(getState() == State.FIL_DOC, "Mauvais State");
		Contract.checkCondition(!isSynchronized(), "Pas syncro");
		try {
			fdm.save();
		} catch (IOException | BadLocationException e) {	
			e.printStackTrace();
			throw new PetException();
		}
		oldfdm.setDocument(fdm.getDocument());
		oldfdm.setFile(fdm.getFile());
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void saveCurrentDocIntoFile(File f) throws PetException {
		Contract.checkCondition(getState() == State.FIL_DOC, "Mauvais State");
		Contract.checkCondition(!isSynchronized(), "Pas syncro");
		Contract.checkCondition(f != null && f.isFile() 
				&& f.canRead() && f.canWrite()
				, "Fichier incorrect");
		fdm.setFile(f);
		try {
			fdm.save();
		} catch (IOException | BadLocationException e) {	
			e.printStackTrace();
			throw new PetException();
		}
		oldfdm.setDocument(fdm.getDocument());
		oldfdm.setFile(fdm.getFile());
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void setNewDocAndNewFile(File f) throws PetException {
		Contract.checkCondition(f != null && f.isFile() 
				&& f.canRead() && f.canWrite()
				, "Fichier incorrect");
		
		try {
			fdm.setFile(f);
			fdm.setDocument(new DefaultStyledDocument());
			fdm.load();
			oldfdm.setFile(f);
			oldfdm.setDocument(new DefaultStyledDocument());
			oldfdm.load();
		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
			e.printStackTrace();
			throw new PetException();
		}
		this.setChanged();
		this.notifyObservers();
		
	}

	@Override
	public void setNewDocFromFile(File f) throws PetException {
		Contract.checkCondition(f != null && f.isFile() 
				&& f.canRead() && f.canWrite()
				, "Fichier incorrect");
		fdm.setFile(f);
		try {
			fdm.setDocument(new DefaultStyledDocument());
			fdm.load();
		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
			throw new PetException();
		}
		fdm.removeFile();
		this.setChanged();
		this.notifyObservers();

	}

	@Override
	public void setNewDocWithoutFile() {
		fdm.setDocument(new DefaultStyledDocument());
		fdm.removeFile();
		this.setChanged();
		this.notifyObservers();
	}

}
