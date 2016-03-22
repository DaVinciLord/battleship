package model.ai;

import model.board.Board;
import model.board.IBoard;
import model.board.State;
import model.coordinates.Coordinates;

import java.util.List;

public class NoobAdvisor extends AAdvisor {


    public NoobAdvisor(IBoard<State> tab, List<Integer> ships) {
        super(tab, ships);
    }

    public NoobAdvisor() {
    }
        /**
            * Cette méthode est l'échelon le plus simple de l'intelligence artificielle,
            * elle va juste tirer un nombre aléatoire et regarder si la case correspondante
            * est <code>NOTAIMED</code>, dans le cas contraire, elle recomencera jusqu'à que la case trouvée
            * soit <code>NOTAIMED</code>.
            * 
            */
    @Override
    public Coordinates getAdvise() {
        int[] coords = new int[getEnemiesBoard().dimensionNb()];
        Coordinates dims = getEnemiesBoard().getDimensionsSizes();
        Coordinates result;
        do {
                
            int dist = (int) (Math.random() * getEnemiesBoard().size());
            int size = getEnemiesBoard().size();
            
            for(int i = 0; i < getEnemiesBoard().dimensionNb(); i++) {
                size /= dims.get(getEnemiesBoard().dimensionNb() - 1 - i);            
                coords[getEnemiesBoard().dimensionNb() - 1 - i] = dist / size;
                dist = dist % size;
            }
            result = new Coordinates(coords);
        } while (getEnemiesBoard().getItem(result).getState() != State.NOTAIMED);
        
        return result;
    }
}
