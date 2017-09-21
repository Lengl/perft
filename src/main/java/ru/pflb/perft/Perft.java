package ru.pflb.perft;

import java.util.List;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Perft {

    public static long search(Board board, int depth) {

        if (depth == 0) {
            return 1;
        }

        long moveCount = 0;

        List<Move> moves = board.genAllMoves();
        for (Move move : moves) {
            board.makeMove(move);

            if (board.isLegal()) {
                moveCount += search(board, depth - 1);
            }

            board.takeBack(move);

        }

        return moveCount;
    }
}
