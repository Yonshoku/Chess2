package chessgui;

import java.util.*;

public class MovesLog {
    private static MovesLog movesLog;
    private int movesNum = 0;
    private List<Move> log = new ArrayList<Move>();     
    
    public static final MovesLog getMovesLog() {
        if (movesLog == null) {
            movesLog = new MovesLog();
        }

        return movesLog;
    }

    public void add(Move move) {
        log.add(move);
    }

    public void reset() {
        log.clear();
        movesNum = 0;
    }

    public Move getMove(int num) {
        return log.get(num);  
    }

    public Move getLastMove() {
        if (log.size() == 0)
            return null;

        return getMove(movesNum - 1);
    }

    public void deleteLastMove() {
        log.remove(movesNum - 1);
    }

    public int getMovesNum() {
        return movesNum;
    }

    public void incrementMovesNum() {
        movesNum++;
    }
    
    public void decrementMovesNum() {
        if (movesNum > 0)
            movesNum--;
    }
}

