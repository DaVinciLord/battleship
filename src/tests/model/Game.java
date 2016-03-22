package tests.model;

import model.ai.IAdvisor;
import model.ai.MediumAdvisor;
import model.ai.NoobAdvisor;
import model.board.Board;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {	
    public Game() {
        initplayer();
        initview();
        initserver();  
    }
    
    public void startGame() {
    	Scanner scan = new Scanner(System.in);
        Coordinates tableau = new Coordinates(10, 10);
        IBoard<State> plateauJ1 = new Board<State>(tableau);
        IBoard<State> plateauJ2 = new Board<State>(tableau);
        for (int i = 0; i <10; i++) {
            for (int j = 0; j <10; j++) {
                Coordinates tab2 = new Coordinates(i, j);
                plateauJ1.setItem(tab2,State.NOTAIMED);    
                plateauJ2.setItem(tab2,State.NOTAIMED); 
            }          
        }
        ArrayList<Integer> ships = new ArrayList<Integer>();
        ships.add(2);
        ships.add(3);
        ships.add(4);
        
        IAdvisor aiJ1 = new MediumAdvisor(plateauJ2, ships);
        IAdvisor aiJ2 = new NoobAdvisor(plateauJ1, ships);
        
        int nbrShipLeftJ1 = 9;
        int nbrShipLeftJ2 = 9;
        
        ArrayList<Coordinates> shipsJ1 = new ArrayList<Coordinates>();
        shipsJ1.add(new Coordinates(1,1));
        shipsJ1.add(new Coordinates(1,2));
        shipsJ1.add(new Coordinates(1,3));
        shipsJ1.add(new Coordinates(5,5));
        shipsJ1.add(new Coordinates(6,5));
        shipsJ1.add(new Coordinates(7,7));
        shipsJ1.add(new Coordinates(7,8));
        shipsJ1.add(new Coordinates(7,6));
        shipsJ1.add(new Coordinates(7,5));
        
        ArrayList<Coordinates> shipsJ2 = new ArrayList<Coordinates>();
        shipsJ2.add(new Coordinates(1,1));
        shipsJ2.add(new Coordinates(1,2));
        shipsJ2.add(new Coordinates(1,3));
        shipsJ2.add(new Coordinates(5,5));
        shipsJ2.add(new Coordinates(6,5));
        shipsJ2.add(new Coordinates(9,7));
        shipsJ2.add(new Coordinates(9,8));
        shipsJ2.add(new Coordinates(9,6));
        shipsJ2.add(new Coordinates(9,5));
        
        
        while (nbrShipLeftJ1 != 0 && nbrShipLeftJ2 != 0) {
            System.out.println("C'est le tour de J1 : " + aiJ1.getClass().getSimpleName());

            Coordinates coords = aiJ1.getAdvise();
           
            boolean contains = false;
            for (Coordinates tabs : shipsJ2) {
                if (tabs.get(0) == coords.get(0) && tabs.get(1) == coords.get(1)) contains = true;
                
            }
                
            if (contains) {    
                plateauJ2.setItem(coords, State.HIT);
                nbrShipLeftJ2 --;
                System.out.println("Un bateau de J2 à été touché en " + coords.get(0) + ";" + coords.get(1) + " !");
                System.out.println("Il reste " + nbrShipLeftJ2 + " Bateau(x) au joueur 2");
            } else {
                System.out.println("Tir en " + coords.get(0) + ";" + coords.get(1) + " Le tir a fini dans l'eau...");
                plateauJ2.setItem(coords, State.MISSED);
            }
            drawGridKanto(plateauJ2);
            scan.nextLine();
            
            System.out.println("C'est le tour de J2 : " + aiJ2.getClass().getSimpleName());
            contains = false;
            coords = aiJ2.getAdvise();
            
            for (Coordinates tabs : shipsJ1) {
                if (tabs.get(0) == coords.get(0) && tabs.get(1) == coords.get(1)) contains = true;
            }
            
            if (contains) {
                plateauJ1.setItem(coords, State.HIT);
                nbrShipLeftJ1 --;
                System.out.println("Un bateau de J1 à été touché en " + coords.get(0) + ";" + coords.get(1) + " !");
                System.out.println("Il reste " + nbrShipLeftJ1 + " Bateau(x) au joueur 1");
            } else {
                System.out.println("Tir en " + coords.get(0) + ";" + coords.get(1) + " Le tir a fini dans l'eau...");
                plateauJ1.setItem(coords, State.MISSED);
            }
            drawGridKanto(plateauJ1);
            scan.nextLine();
            
        }
    }

    private void initserver() {
        // TODO Auto-generated method stub
        
    }

    private void initview() {
        // TODO Auto-generated method stub
    }

    private void initplayer() {
        // TODO Auto-generated method stub
        
    }
    
	private void drawGridKanto(IBoard<State> board) {
		int i = 0;
    	for(State s : board) {
    		if (s == State.NOTAIMED) {
    			System.out.print(" . ");
    		} else if (s == State.MISSED) {
    			System.out.print(" O ");
    		} else {
    			System.out.print(" X ");
    		}
    		if (++i % 10 == 0) {
    			System.out.println();
    		}
    	}
    }
}
