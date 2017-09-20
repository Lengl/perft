package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.List;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;
import static ru.pflb.perft.Piece.*;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Board {

    private Piece[] mailbox120 = {
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 0-9
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 10-19
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 20-29
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 30-39
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 40-49
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 50-59
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 60-69
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 70-79
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 80-89
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 90-99
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 100-109
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT  // 110-119
    };

    private Color sideToMove = WHITE;

    private Square[] kingPos = new Square[2];

    private Square[][] piecePos = new Square[12][10];


    public Board(String fen) {
        String[] fenParts = fen.split("\\s");

        sideToMove = fenParts[1].startsWith("w") ? WHITE : BLACK;

        for (byte square = 98, fenIndex = 0; fenIndex < fenParts[0].length(); fenIndex++, square--) {
            char c = fenParts[0].charAt(fenIndex);
            switch (c) {
                case 'K':
                    mailbox120[square] = W_KING;
                    kingPos[WHITE.color] = new Square(square);
                    break;
                case 'R':
                    mailbox120[square] = W_ROOK;
                    for (int i = 0; i < piecePos[W_ROOK.code].length; i++) {
                        if (piecePos[W_ROOK.code][i].isValid()) {
                            piecePos[W_ROOK.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'B':
                    mailbox120[square] = W_BISHOP;
                    for (int i = 0; i < bishopPos[WHITE.color].length; i++) {
                        if (bishopPos[WHITE.color][i] == 0) {
                            bishopPos[WHITE.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'Q':
                    mailbox120[square] = W_QUEEN;
                    for (int i = 0; i < queenPos[WHITE.color].length; i++) {
                        if (queenPos[WHITE.color][i] == 0) {
                            queenPos[WHITE.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'N':
                    mailbox120[square] = W_KNIGHT;
                    for (int i = 0; i < knightPos[WHITE.color].length; i++) {
                        if (knightPos[WHITE.color][i] == 0) {
                            knightPos[WHITE.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'k':
                    mailbox120[square] = B_KING;
                    kingPos[BLACK.color] = square;
                    break;
                case 'r':
                    mailbox120[square] = B_ROOK;
                    for (int i = 0; i < rookPos[BLACK.color].length; i++) {
                        if (rookPos[BLACK.color][i] == 0) {
                            rookPos[BLACK.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'b':
                    mailbox120[square] = B_BISHOP;
                    for (int i = 0; i < bishopPos[BLACK.color].length; i++) {
                        if (bishopPos[BLACK.color][i] == 0) {
                            bishopPos[BLACK.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'q':
                    mailbox120[square] = B_QUEEN;
                    for (int i = 0; i < queenPos[BLACK.color].length; i++) {
                        if (queenPos[BLACK.color][i] == 0) {
                            queenPos[BLACK.color][i] = square;
                            break;
                        }
                    }
                    break;
                case 'n':
                    mailbox120[square] = B_KNIGHT;
                    for (int i = 0; i < knightPos[BLACK.color].length; i++) {
                        if (knightPos[BLACK.color][i] == 0) {
                            knightPos[BLACK.color][i] = square;
                            break;
                        }
                    }
                    break;
                case '/':
                    square -= 1;
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                    square -= c - '1';
                    break;
                default:
                    throw new IllegalStateException("Недопустимый символ - " + c);
            }
        }
    }

    public List<Move> genAllMoves() {
        throw new NotImplementedException();
    }

    public void makeMove(Move move) {
        throw new NotImplementedException();
    }

    public void takeBack(Move move) {
        throw new NotImplementedException();
    }
}
