package ru.pflb.perft;

import java.util.ArrayList;
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

    /* Очередь хода */
    private Color sideToMove = WHITE;

    private Square[] kingPos = new Square[2];

    private Square[][] piecePos = new Square[12][10];

    public Board(String fen) {
        for (Square[] squares : piecePos) {
            for (int i = 0; i < squares.length; i++) {
                squares[i] = Square.invalid();
            }
        }

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
                        if (!piecePos[W_ROOK.code][i].isValid()) {
                            piecePos[W_ROOK.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'B':
                    mailbox120[square] = W_BISHOP;
                    for (int i = 0; i < piecePos[W_BISHOP.code].length; i++) {
                        if (!piecePos[W_BISHOP.code][i].isValid()) {
                            piecePos[W_BISHOP.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'Q':
                    mailbox120[square] = W_QUEEN;
                    for (int i = 0; i < piecePos[W_QUEEN.code].length; i++) {
                        if (!piecePos[W_QUEEN.code][i].isValid()) {
                            piecePos[W_QUEEN.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'N':
                    mailbox120[square] = W_KNIGHT;
                    for (int i = 0; i < piecePos[W_KNIGHT.code].length; i++) {
                        if (!piecePos[W_KNIGHT.code][i].isValid()) {
                            piecePos[W_KNIGHT.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'k':
                    mailbox120[square] = B_KING;
                    kingPos[BLACK.color] = new Square(square);
                    break;
                case 'r':
                    mailbox120[square] = B_ROOK;
                    for (int i = 0; i < piecePos[B_ROOK.code].length; i++) {
                        if (!piecePos[B_ROOK.code][i].isValid()) {
                            piecePos[B_ROOK.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'b':
                    mailbox120[square] = B_BISHOP;
                    for (int i = 0; i < piecePos[B_BISHOP.code].length; i++) {
                        if (!piecePos[B_BISHOP.code][i].isValid()) {
                            piecePos[B_BISHOP.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'q':
                    mailbox120[square] = B_QUEEN;
                    for (int i = 0; i < piecePos[B_QUEEN.code].length; i++) {
                        if (!piecePos[B_QUEEN.code][i].isValid()) {
                            piecePos[B_QUEEN.code][i] = new Square(square);
                            break;
                        }
                    }
                    break;
                case 'n':
                    mailbox120[square] = B_KNIGHT;
                    for (int i = 0; i < piecePos[B_KNIGHT.code].length; i++) {
                        if (!piecePos[B_KNIGHT.code][i].isValid()) {
                            piecePos[B_KNIGHT.code][i] = new Square(square);
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

    private static final byte ROOK_OFFSET[] = {-10, -1, 1, 10};

    public List<Move> genAllMoves() {
        List<Move> moves = new ArrayList<>();
        Piece piece = sideToMove == WHITE ? W_ROOK : B_ROOK;
        Square[] squares = piecePos[piece.code];
        for (Square from : squares) {
            if (!from.isValid()) {
                break;
            }
            for (byte offset : ROOK_OFFSET) {
                byte to;
                for (to = (byte) (from.value + offset); mailbox120[to] == EMP; to += offset) {
                    moves.add(new Move(from, new Square(to), piece));
                }
                // для вражеских фигур генерируем взятия
                if (mailbox120[to] != OUT && mailbox120[to].getColor() != sideToMove) {
                    moves.add(new Move(from, new Square(to), piece, mailbox120[to]));
                }
            }
        }

//        throw new NotImplementedException();

        return moves;
    }

    public void makeMove(Move move) {

        // перемещаем фигуру на новое поле
        mailbox120[move.to.value] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (Square square : piecePos[move.piece.code]) {
            if (square.value == move.from.value) {
                square.value = move.to.value;
            }
        }

        // удаляем ходящую фигуру с предыдущего поля
        mailbox120[move.from.value] = move.piece;

        if (move.isCapture()) {

            // обновляем массив быстрого доступа для взятой фигуры
            for (int i = 0; i < piecePos[move.capture.code].length; i++) {
                for (int j = i + 1; j < piecePos[move.capture.code].length; j++) {
                    piecePos[move.capture.code][j-1].value = piecePos[move.capture.code][j].value;
                    if (!piecePos[move.capture.code][j].isValid()) {
                        break;
                    }
                }
            }
        }

    }

    public void takeBack(Move move) {

        // возвращаем фигуру на старое поле
        mailbox120[move.from.value] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (Square square : piecePos[move.piece.code]) {
            if (square.value == move.to.value) {
                square.value = move.from.value;
            }
        }

        if (move.isCapture()) {

            // возвращение взятой фигуры
            mailbox120[move.to.value] = move.capture;

            // обновляем массив быстрого доступа для взятой фигуры
            for (int i = 0; i < piecePos[move.capture.code].length; i++) {
                if (!piecePos[move.capture.code][i].isValid()) {
                    piecePos[move.capture.code][i] = move.to;
                    break;
                }
            }
        }
    }

    /**
     * Проверка того, что король стороны, только что сделавшей ход, не находится под шахом.
     */
    public boolean isLegal() {

        return true;
//        // TODO - реализовать курсанту
//        throw new NotImplementedException();
    }
}
