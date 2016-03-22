package tests.model;

import model.ai.EasyAdvisor;
import model.ai.IAdvisor;
import model.board.Board;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
//        int[] tableau = {5,5,5};
//        NArray<Integer> tab = new NArray<Integer>(tableau);
//        for (int i = 0; i <5; i++) {
//            for (int j = 0; j <5; j++) {
//                for (int k = 0; k <5; k++) {
//                    int[] tab2 = {i,j,k};
//                tab.setItem(tab2, i*100+j*10+k);    
//                }          
//            }
//        }
//        for (Integer i : tab) {
//            System.out.println(i);
//        }
      //  for (int n = 0; n < 20000; n++) {
      long time = System.currentTimeMillis();
        Coordinates tableau = new Coordinates (10,11,12);
        IBoard<State> tab = new Board<State>(tableau);
        for (int i = 0; i <10; i++) {
            for (int j = 0; j <11; j++) {
                for (int k = 0; k <12; k++) {
                    Coordinates tab2 = new Coordinates(i,j,k);
                tab.setItem(tab2,State.NOTAIMED);    
                }          
            }
        }
        int cpt1 = 0;
        int cpt2 = 2;
        ArrayList<Integer> ships = new ArrayList<Integer>();
        ships.add(2);
        IAdvisor ai = new EasyAdvisor(tab, ships);
        for (int i = 0; i < 10*11*12 ; i++) {
            Coordinates coords = ai.getAdvise();
            
            
           // System.out.println("" + coords[0] + "-" + coords[1]+ "-" + coords[2]);
            if (coords.get(0) == 5 && coords.get(1) == 5 && coords.get(2) == 5
                    || coords.get(0) == 5 && coords.get(1) == 5 && coords.get(2) == 6
                    /*|| coords[0] == 5 && coords[1] == 5 && coords[2] == 7*/ ) {
                tab.setItem(coords, State.HIT);
                cpt2--;
                //System.out.println("touché");
                System.out.println("" + coords.get(0) + coords.get(1) + coords.get(2));
            } else {
            tab.setItem(coords, State.MISSED);
            if (cpt2 != 0) cpt1 ++;
           // System.out.println("e");
            }
        } 
        System.out.println(cpt1);
       // System.out.println((System.currentTimeMillis() - time));
      
    }
        
  //  }
    
    
}
