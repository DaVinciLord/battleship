package serie02;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import util.Contract;


public class StdSplitManager implements SplitManager {
	private File fichier;
	private long[] splitsize;
	
	public StdSplitManager(File f) {
		Contract.checkCondition(f != null, "Votre fichier est nul --'");
		fichier = f;
		if (fichier.length() > 0) {
			splitsize = new long[1];
			splitsize[0] = f.length();
		} else {
			splitsize = new long[0];
		}
	}
	
	public File getFile() {
		return fichier;
	}

	public int getMaxFragmentNb() {
		return (int) Math.min(MAX_FRAGMENT_NB,
				          	Math.max(1, Math.ceil(getFile().length() 
				        	/ MIN_FRAGMENT_SIZE)));
	}


	public String[] getSplitsNames() {
		String[] tab = new String[splitsize.length];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = fichier.getAbsolutePath() + "." + (i + 1);
		}
		return tab;
	}


	public long[] getSplitsSizes() {
		return splitsize;
	}


	public void setFile(File f) {
		Contract.checkCondition(f != null, "Votre fichier est null --'");
		fichier = f;
		if (fichier.length() > 0) {
			splitsize = new long[1];
			splitsize[0] = f.length();
		} else {
			splitsize = new long[0];
		}
	}


	public void setSplitsSizes(long fragSize) {
		Contract.checkCondition(getFile().length() 
				>= MIN_FRAGMENT_SIZE, "Votre fichier est trop petit --'");
		Contract.checkCondition(fragSize 
				>= MIN_FRAGMENT_SIZE, "Vous voulez hacher trop menu --'");
		int div = (int) (fichier.length() / fragSize);
		long mod = fichier.length() % fragSize;
		long[] tab = new long[div + 1];
		for (int i = 1; i < tab.length; i++) {
			tab[i - 1] = fragSize;
		}
		tab[tab.length - 1] = mod; 
		splitsize = tab;
	}


	public void setSplitsSizes(long[] fragSizes) {
		Contract.checkCondition(getFile().length() 
				>= MIN_FRAGMENT_SIZE, "Votre fichier est trop petit --'");
		Contract.checkCondition(fragSizes != null, 
				"Vous avez un hachoir nul --'");
		Contract.checkCondition(fragSizes.length 
				>= 1, "Vous voulez hacher en 1 seul morceau --'");
		Contract.checkCondition(checksize(fragSizes), 
				"Un de vos morceau semble trop petit --'");
		long totsize = 0;
		for (int i = 0; i < fragSizes.length; i++) {
			totsize += fragSizes[i];
		}
		if (totsize >= fichier.length()) {
			long ficsize = fichier.length();
			int i = 0;
			while (ficsize > 0) {
				if (ficsize - fragSizes[i] < 0) {
					break;
				} else {
					ficsize -= fragSizes[i];
				}
				i++;
			}
			long[] tab = new long[i + 1];
			for (int w = 0; w < (tab.length) - 1; w++) {
				tab[w] = fragSizes[i];
			}
			tab[i] = totsize;
			splitsize = tab;
		} else { 
			long ficsize = fichier.length();
			int i = 0;
			while (i < fragSizes.length) {
				if (ficsize - fragSizes[i] < 0) {
					break;
				} else {
					ficsize -= fragSizes[i];
				}
				i++;
			}
			long[] tab = new long[i + 1];
			for (int w = 0; w < (tab.length) - 1; w++) {
				tab[w] = fragSizes[w];
			}
			tab[i] = totsize;
			splitsize = tab;
		}
	}


	public void setSplitsNumber(int number) {
		Contract.checkCondition(getFile().length() 
				> 0, "Votre fichier est vide --'");
		Contract.checkCondition(number >= 1, "Votre nombre est nul --'");
		Contract.checkCondition(number 
				<= getMaxFragmentNb(), "Vous demandez trop de morceaux --'");
		long[] gSS;
		long n   = fichier.length();
		long q   = n / number;
		if (q < MIN_FRAGMENT_SIZE) {
			gSS = new long[(int) Math.ceil(n / MIN_FRAGMENT_SIZE)];
			for (int i = 0; i < gSS.length; i++) {
				gSS[i] = MIN_FRAGMENT_SIZE;
			}
			gSS[gSS.length - 1] = n - (gSS.length - 1) * MIN_FRAGMENT_SIZE;
			splitsize = gSS;
		} else {
			gSS = new long[number];
			for (int i = 0; i <= n % gSS.length; i++) {
				gSS[i] = q + 1;
			}
			for (int i = (int) n % gSS.length; i < gSS.length; i++) {
				gSS[i] = q;
			}
			splitsize = gSS;
		}
	}


	public void split() throws IOException {
		Contract.checkCondition(getFile().length()
				> 0, "Votre fichier est vide --'");
		java.io.BufferedInputStream bis;
		java.io.BufferedOutputStream bos; 
		try {
		bis = new java.io.BufferedInputStream(
				new java.io.FileInputStream(fichier));
		
		
		byte[] buf;
		File[] files = new File[getSplitsNames().length];
		for (long i = 0; i < splitsize.length; i++) {
			files[(int) i] = new File(getSplitsNames()[(int) i]);
			files[(int) i].createNewFile();
		buf = new byte[(int) splitsize[(int) i]];
		bos = new java.io.BufferedOutputStream(
				new java.io.FileOutputStream((files[(int) i])));
		for (long j = 0; j < splitsize[(int) i]; ++j) {
		    bis.read(buf);
		}
		bos.write(buf);
		bos.close();
		}
		bis.close();		



         } catch (FileNotFoundException e) {

		      e.printStackTrace();

		    } catch (IOException e) {

		      e.printStackTrace();

		    }       
		
	}

	private boolean checksize(long[] tab) {
		boolean a = true;
		for (int i = 0; i < tab.length; i++) {
			a = a && (tab[i] >= MIN_FRAGMENT_SIZE);
		}
		return a;
	}
	
}
