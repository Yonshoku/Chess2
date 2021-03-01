package chessgui;

import java.util.*;

public class MovesLog {
    private static MovesLog movesLog;
    private int movesMade = 0;
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
        movesMade = 0;
    }

    public Move getMove(int num) {
        return log.get(num);  
    }

    public Move getLastMove() {
        if (log.size() == 0)
            return null;

        return getMove(movesMade - 1);
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void plusOneMovesMade() {
        movesMade++;
    }
}

