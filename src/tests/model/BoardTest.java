package tests.model;

import model.board.Board;
import model.board.IBoard;
import model.coordinates.Coordinates;

import java.util.Iterator;


public class BoardTest {
	public static void main(String[] args) {
	    /*
	    long initTime = System.currentTimeMillis();
	    
	    Coordinates dims = new Coordinates(5, 5, 5, 5, 5);
	    
		INArray<String> tabN = new NArray<String>(dims);
		// test de dimZeroIterator()
		int compteur = 0;
		Iterator<INArray<String>> itDimZ = tabN.dimZeroIterator();
		while (itDimZ.hasNext()) {
		    itDimZ.next().setItem(new int[0], "item numéro " + compteur);
		    compteur++;
		}
		*/
		/*
		int[] coords = new int[5];
		
		for (int i = 0; i < dims[0]; i++) {
			coords[0] = i;
			for (int j = 0; j < dims[1]; j++) {
				coords[1] = j;
				for (int k = 0; k < dims[2]; k++) {
					coords[2] = k;
					for (int l = 0; l < dims[3]; l++) {
						coords[3] = l;
						for (int m = 0; m < dims[4]; m++) {
						    coords[4] = m;   
						    tabN.setItem(coords, "i = " + i + ", j = " + j + ", k = " + k + ", l = " + l + ", m = " + m);
						}
					}
				}
			}
		}
		System.out.println("nb dimensions : " + tabN.dimensionNb() + " " + tabN.getDimensionsSizes().length);
		
		System.out.println(tabN.getItem(new int[]{1, 0, 2, 3, 4}));
		
		int[] dimCoords = {4, -1, 1, -1, 0};
		
		INArray<String> soustabN = tabN.getNArray(dimCoords);
		System.out.println("nb dimensions : " + soustabN.dimensionNb());
		
		for (int i = 0; i < dims[1]; i++) {
		    System.out.println("ligne numéro : " + i);
		    for (int k = 0; k < dims[3]; k++) {
		        System.out.println(soustabN.getItem(new int[]{i, k}));
		    }
		}
		
		// test de l'itérateur avec la boucle foreach
		System.out.println("test boucle foreach");
		for (String s : tabN) {
		    System.out.println(s);
		    // test echec rapide
		    // tabN.setItem(new int[]{1, 0, 2, 3}, "baton dans les roues");
		}
		
		long deltaTime = System.currentTimeMillis() - initTime;
		*/
		System.out.println("Mêmes tests avec FastNArray");
		
		long initTime = System.currentTimeMillis();

		Coordinates fdimCoords = new Coordinates(5, 5, 5, 5, 5);
		
		IBoard<String> FtabN = new Board<String>(fdimCoords);
		int compteur = 0;
		Iterator<IBoard<String>> FitDimZ = FtabN.dimZeroIterator();
        while (FitDimZ.hasNext()) {
            FitDimZ.next().setItem(new Coordinates(), "item numéro " + compteur);
            compteur++;
        }
        /*
        int[] Fcoords = new int[5];
        
        
        for (int i = 0; i < fdimCoords[0]; i++) {
            Fcoords[0] = i;
            for (int j = 0; j < fdimCoords[1]; j++) {
                Fcoords[1] = j;
                for (int k = 0; k < fdimCoords[2]; k++) {
                    Fcoords[2] = k;
                    for (int l = 0; l < fdimCoords[3]; l++) {
                        Fcoords[3] = l;
                        for (int m = 0; m < fdimCoords[4]; m++) {
                            Fcoords[4] = m;
                            FtabN.setItem(Fcoords, "i = " + i + ", j = " + j + ", k = " + k + ", l = " + l + ", m = " + m);
                        }
                    }
                }
            }
        }*/
        
        System.out.println("nb dimensions : " + FtabN.dimensionNb() + " " + FtabN.getDimensionsSizes().length);
        
        System.out.println(FtabN.getItem(new Coordinates(1, 0, 2, 3, 3)));

		Coordinates FdimCoords = new Coordinates(4, -1, 1, -1, 0);
        
        IBoard<String> FsoustabN = FtabN.getBoard(FdimCoords);
        System.out.println("nb dimensions : " + FsoustabN.dimensionNb());
        
        for (int i = 0; i < fdimCoords.get(1); i++) {
            System.out.println("ligne numéro : " + i);
            for (int k = 0; k < fdimCoords.get(3); k++) {
                System.out.println(FsoustabN.getItem(new Coordinates(i, k)));
            }
        }
        
        // test de l'itérateur avec la boucle foreach
        System.out.println("test boucle foreach");
        for (String s : FtabN) {
            System.out.println(s);
            // test echec rapide
            // FtabN.setItem(new int[]{1, 0, 2, 3, 2}, "baton dans les roues");
        }
        
        long fdeltaTime = System.currentTimeMillis() - initTime;
        
        System.out.println("\nFastNArray : " + fdeltaTime);
	}
}
