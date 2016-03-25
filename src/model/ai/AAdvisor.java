package model.ai;

import java.util.Collections;
import java.util.List;

import model.board.IBoard;
import model.board.State;


public abstract class AAdvisor implements IAdvisor {

    private IBoard<State> enemyBoard;
    private List<Integer> shipLeft;
    protected int mode;
    
    public AAdvisor(IBoard<State> tab, List<Integer> ships) {
        enemyBoard = tab;
        shipLeft = ships;
        Collections.sort(shipLeft);
    }
    public AAdvisor() {

    }
    
    public void setEnemyBoard(IBoard<State> enemyBoard) {
        this.enemyBoard = enemyBoard;
    }
    
    public void setShipLeft(List<Integer> shipLeft) {
        this.shipLeft = shipLeft;
        Collections.sort(shipLeft);
    }
    
    
    
    @Override
    public IBoard<State> getEnemiesBoard() {
        return enemyBoard;
    }

    @Override
    public boolean noShipLeft() {
        return shipLeft.isEmpty();
    }



    @Override
    public int getMode() {
        return mode;
    }

}
