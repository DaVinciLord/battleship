package model.ai;

import java.util.ArrayList;
import java.util.List;

import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

public class MediumAdvisor extends AAdvisor {
    private Coordinates lastCoords;
    private List<Coordinates> casesOfInterest;
    private int minSize;
    
    public MediumAdvisor(IBoard<State> tab, List<Integer> ships) {
        super(tab, ships);
        lastCoords = null;
        casesOfInterest = new ArrayList<Coordinates>();
        minSize = ships.get(0);casesOfInterest = new ArrayList<Coordinates>();
    }

    public MediumAdvisor() {
        casesOfInterest = new ArrayList<Coordinates>();
    }

    public void setShipLeft(List<Integer> shipLeft) {
        super.setShipLeft(shipLeft);
        minSize = shipLeft.get(0);
    }
    
    @Override
    public Coordinates getAdvise() {
        if (lastCoords != null) {
            updateMode();
        }
        
        if (mode == MODE_HUNTING) {
            do {
                lastCoords = casesOfInterest.remove(0);
            } while (getEnemiesBoard().getItem(lastCoords) != State.NOTAIMED && !casesOfInterest.isEmpty());
            if (casesOfInterest.size() == 0) {
                mode = MODE_TARGETING;
            }
            if (getEnemiesBoard().getItem(lastCoords) != State.NOTAIMED) {
                return getAdvise(); 
            }
            return lastCoords;
        }
        Coordinates result;
        int[] coords = new int[getEnemiesBoard().dimensionNb()];
        Coordinates dims = getEnemiesBoard().getDimensionsSizes();
        int mod = 0;
        do {
            mod = 0;
            int dist = (int) (Math.random() * getEnemiesBoard().size());
            int size = getEnemiesBoard().size();
            
            for(int i = 0; i < getEnemiesBoard().dimensionNb(); i++) {
                size /= dims.get(getEnemiesBoard().dimensionNb() - 1 - i);            
                coords[getEnemiesBoard().dimensionNb() - 1 - i] = dist / size;
                mod += dist / size;
                mod = mod % minSize;
                dist = dist % size;
                
            }
            result = new Coordinates(coords);
        } while (mod != 0 || getEnemiesBoard().getItem(result).getState() != State.NOTAIMED);
        lastCoords = result;
        return result;   
    }
    
    
    private void updateMode() {
        
        if (getEnemiesBoard().getItem(lastCoords) == State.HIT || getEnemiesBoard().getItem(lastCoords) == State.SUNK) {
            int[] coords= new int[getEnemiesBoard().dimensionNb()];
            for (int i = 0; i < getEnemiesBoard().dimensionNb(); i++) {
                if (lastCoords.get(i) > 0) {
                    coords = lastCoords.getCoordinates();
                    coords[i] --;
                    Coordinates c = new Coordinates(coords);
                    if (getEnemiesBoard().getItem(c).getState() == State.NOTAIMED) {
                        casesOfInterest.add(c);
                    }
                }
                if (lastCoords.get(i) < getEnemiesBoard().getDimensionsSizes().get(i) - 1) {
                    coords = lastCoords.getCoordinates();
                    coords[i] ++;
                    Coordinates c = new Coordinates(coords);
                    if (getEnemiesBoard().getItem(c).getState() == State.NOTAIMED) {
                        casesOfInterest.add(c);
                    }
                }
     
            }
            
        }
        if (casesOfInterest.size() != 0) {
            mode = MODE_HUNTING;
        }
    }
    

}
