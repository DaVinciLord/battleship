package serie05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;

import serie04.Civ;
import serie04.Contact;
import serie04.StdContact;
import serie04.StdPhoneBook;
import util.Contract;

public class StdPersistentPhoneBook extends 
StdPhoneBook implements PersistentPhoneBook {
	
	private File fichier;
	
	public StdPersistentPhoneBook(File file) {
		fichier = file;		
	}
	
	public StdPersistentPhoneBook() {
		fichier = null;		
	}

	@Override
	public File getFile() {
		return fichier;
	}

	@Override
	public void setFile(File file) {
		Contract.checkCondition(file != null, "Fichier null");
		fichier = file;
	}

	@Override
	public void load() throws IOException, BadSyntaxException {
		Contract.checkCondition(getFile() != null, "Fichier null");
		super.clear();
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fichier));
			try {
			String line;
			while ((line = buff.readLine()) != null) {
					Matcher m = LINE_RECOGNIZER.matcher(line);
					if (!m.matches()) {
						super.clear();
						throw new BadSyntaxException();
					}
					String[] tab = line.split(":");
					String[] numbers = tab[3].split(",");
					LinkedList<String> nums = new LinkedList<String>();
					for (String s : numbers) {
						nums.add(s.trim());
					}
					super.addEntry(new StdContact(Civ.values()
							[(Integer.parseInt(tab[0].trim()))],
							tab[1].trim(), tab[2].trim()), nums);
				}
			  
			} catch (IOException ioe) {
				System.out.println("Erreur --'" + ioe.toString());
				throw ioe;
			} finally {
				buff.close();
			}
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		}
	}

	@Override
	public void save() throws IOException {
		Contract.checkCondition(getFile() != null, "Fichier null");
		LinkedList<String> ll = new LinkedList<String>();
		for (Contact c : super.contacts()) {
			String s = c.getCivility().ordinal() + ":" + c.getLastName()
			+ ":" + c.getFirstName() + ":" + super.firstPhoneNumber(c);
			for (int i = 1; i < this.phoneNumbers(c).size(); i++) {
				s += "," + this.phoneNumbers(c).get(i);
			}
			ll.add(s);
		}
		try {
			BufferedWriter buff = new BufferedWriter(new FileWriter(fichier));
			try {
			for (String s : ll) {
				buff.write(s);
				buff.newLine();
				}
			} catch (IOException ioe) {
				System.out.println("Erreur --'" + ioe.toString());
				throw ioe;
			} finally {
				buff.close();
			}
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		}
	}

}
