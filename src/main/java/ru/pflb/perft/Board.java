package ru.pflb.perft;

import java.util.ArrayList;
import java.util.List;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;
import static ru.pflb.perft.Piece.*;
import static ru.pflb.perft.Square.*;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Board {

    public static final int TOTAL_PIECE_TYPES = 12;
    public static final int MAX_PER_TYPE = 10;
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

    private byte[][] piecePos = new byte[TOTAL_PIECE_TYPES][MAX_PER_TYPE];

    public Board(String fen) {

        String[] fenParts = fen.split("\\s");
        sideToMove = fenParts[1].startsWith("w") ? WHITE : BLACK;

        for (byte square = 98, fenIndex = 0; fenIndex < fenParts[0].length(); fenIndex++, square--) {
            char c = fenParts[0].charAt(fenIndex);
            switch (c) {
                case 'K':
                    mailbox120[square] = W_KING;
                    piecePos[W_KING.code][0] = square;
                    break;
                case 'R':
                    mailbox120[square] = W_ROOK;
                    for (int i = 0; i < piecePos[W_ROOK.code].length; i++) {
                        if (piecePos[W_ROOK.code][i] == 0) {
                            piecePos[W_ROOK.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'B':
                    mailbox120[square] = W_BISHOP;
                    for (int i = 0; i < piecePos[W_BISHOP.code].length; i++) {
                        if (piecePos[W_BISHOP.code][i] == 0) {
                            piecePos[W_BISHOP.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'Q':
                    mailbox120[square] = W_QUEEN;
                    for (int i = 0; i < piecePos[W_QUEEN.code].length; i++) {
                        if (piecePos[W_QUEEN.code][i] == 0) {
                            piecePos[W_QUEEN.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'N':
                    mailbox120[square] = W_KNIGHT;
                    for (int i = 0; i < piecePos[W_KNIGHT.code].length; i++) {
                        if (piecePos[W_KNIGHT.code][i] == 0) {
                            piecePos[W_KNIGHT.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'k':
                    mailbox120[square] = B_KING;
                    piecePos[B_KING.code][0] = square;
                    break;
                case 'r':
                    mailbox120[square] = B_ROOK;
                    for (int i = 0; i < piecePos[B_ROOK.code].length; i++) {
                        if (piecePos[B_ROOK.code][i] == 0) {
                            piecePos[B_ROOK.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'b':
                    mailbox120[square] = B_BISHOP;
                    for (int i = 0; i < piecePos[B_BISHOP.code].length; i++) {
                        if (piecePos[B_BISHOP.code][i] == 0) {
                            piecePos[B_BISHOP.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'q':
                    mailbox120[square] = B_QUEEN;
                    for (int i = 0; i < piecePos[B_QUEEN.code].length; i++) {
                        if (piecePos[B_QUEEN.code][i] == 0) {
                            piecePos[B_QUEEN.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'n':
                    mailbox120[square] = B_KNIGHT;
                    for (int i = 0; i < piecePos[B_KNIGHT.code].length; i++) {
                        if (piecePos[B_KNIGHT.code][i] == 0) {
                            piecePos[B_KNIGHT.code][i] = square;
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
    private static final byte PIECE_OFFSETS[][] = {
            // EMP
            {},
            // W KING
            {-11, -10, -9, -1, +11, +10, +9, +1},
            // W BISHOP
            {-11, -9, 9, 11},
            // W ROOK
            {-10, -1, 1, 10},
            // W QUEEN
            {-10, -1, 1, 10, -11, -9, 9, 11},
            // W KNIGHT
            {-8, -12, -19, -21, +8, +12, +19, +21},
            // W PAWN
            {},
            // B KING
            {-11, -10, -9, -1, +11, +10, +9, +1},
            // B BISHOP
            {-11, -9, 9, 11},
            // B ROOK
            {-10, -1, 1, 10},
            // B QUEEN
            {-10, -1, 1, 10, -11, -9, 9, 11},
            // B KNIGHT
            {-8, -12, -19, -21, +8, +12, +19, +21},
            // B PAWN
            {},
    };

    public List<Move> genAllMoves() {
        List<Move> moves = new ArrayList<>();

        Piece[] whiteSliders = {W_ROOK, W_BISHOP, W_QUEEN},
                blackSliders = {B_ROOK, B_BISHOP, B_QUEEN};

        // итерация по всем дальнобойным фигурам
        for (Piece piece : sideToMove == WHITE ? whiteSliders : blackSliders) {

            byte[] squares = piecePos[piece.code];
            for (byte from : squares) {
                if (from == 0) {
                    break;
                }
                for (byte offset : PIECE_OFFSETS[piece.code]) {
                    byte to;
                    for (to = (byte) (from + offset); mailbox120[to] == EMP; to += offset) {
                        moves.add(new Move(from, to, piece));
                    }
                    // для вражеских фигур генерируем взятия
                    if (mailbox120[to] != OUT && mailbox120[to].getColor() != sideToMove) {
                        moves.add(new Move(from, to, piece, mailbox120[to]));
                    }
                }
            }
        }

        // отдельно - генерация ходов короля и коня
        Piece[] whiteKN = {W_KING, W_KNIGHT},
                blackKN = {B_KING, B_KNIGHT};
        for (Piece piece : sideToMove == WHITE ? whiteKN : blackKN) {

            byte[] squares = piecePos[piece.code];
            for (byte from : squares) {
                if (from == 0) {
                    break;
                }
                for (byte offset : PIECE_OFFSETS[piece.code]) {
                    byte to = (byte) (from + offset);
                    if (mailbox120[to] == EMP) {
                        moves.add(new Move(from, to, piece));
                    } else if (mailbox120[to] != OUT && mailbox120[to].getColor() != sideToMove) {
                        // для вражеских фигур генерируем взятия
                        moves.add(new Move(from, to, piece, mailbox120[to]));
                    }
                }
            }
        }

        return moves;
    }

    public void makeMove(Move move) {

        // перемещаем фигуру на новое поле
        mailbox120[move.to] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (int i = 0; i < MAX_PER_TYPE; i++ ) {
            if (piecePos[move.piece.code][i] == move.from) {
                piecePos[move.piece.code][i] = move.to;
                break;
            }
        }

        // удаляем ходящую фигуру с предыдущего поля
        mailbox120[move.from] = EMP;

        if (move.isCapture()) {

            // обновляем массив быстрого доступа для взятой фигуры
            for (int i = 0; i < MAX_PER_TYPE; i++) {
                if (piecePos[move.capture.code][i] == move.to) {
                    // сдвигаем оставшиеся фигуры к началу массива, затирая i-тую
                    for (int j = i + 1; j < MAX_PER_TYPE; j++) {
                        piecePos[move.capture.code][j - 1] = piecePos[move.capture.code][j];
                        if (piecePos[move.capture.code][j] == 0) {
                            break;
                        }
                    }
                    // На случай, если у нас было 10 фигур и стало 9
                    piecePos[move.capture.code][MAX_PER_TYPE - 1] = 0;
                    break;
                }
            }
        }

        // передача очереди хода
        sideToMove = sideToMove == WHITE ? BLACK : WHITE;

    }

    public void takeBack(Move move) {

        // возвращаем фигуру на старое поле
        mailbox120[move.from] = move.piece;

        // обновляем массив быстрого доступа для ходящей фигуры
        for (int i = 0; i < MAX_PER_TYPE; i++ ) {
            if (piecePos[move.piece.code][i] == move.to) {
                piecePos[move.piece.code][i] = move.from;
                break;
            }
        }

        if (move.isCapture()) {

            // возвращение взятой фигуры
            mailbox120[move.to] = move.capture;

            // обновляем массив быстрого доступа для взятой фигуры
            for (int i = 0; i < MAX_PER_TYPE; i++) {
                if (piecePos[move.capture.code][i] == 0) {
                    piecePos[move.capture.code][i] = move.to;
                    break;
                }
            }
        } else {
            // пустое поле на месте стоянки фигуры
            mailbox120[move.to] = EMP;
        }

        // возврат очереди хода
        sideToMove = sideToMove == WHITE ? BLACK : WHITE;
    }

    private static final byte[] LINE_OFFSETS = PIECE_OFFSETS[W_ROOK.code],
        DIAG_OFFSETS = PIECE_OFFSETS[W_BISHOP.code],
        KNIGHT_OFFSETS = PIECE_OFFSETS[W_KNIGHT.code];

    /**
     * Проверка того, что король стороны, только что сделавшей ход, не находится под шахом.
     */
    public boolean isLegal() {
        // Проверяем короля стороны противоположной той, чей сейчас ход (т.к. в конце MakeMove мы меняем сторону)
        byte kingPos = sideToMove == WHITE ? piecePos[B_KING.code][0] : piecePos[W_KING.code][0];
        byte checkedPos;
        // Проверка горизонталей и вертикалей в каждом направлении смещения
        for (byte offset : LINE_OFFSETS) {
            checkedPos = (byte) (kingPos + offset);
            // если рядом стоит король - позиция нелегальная
            if (mailbox120[checkedPos].isKing())
                return false;

            while (mailbox120[checkedPos] != OUT) {
                if ( mailbox120[checkedPos] != EMP ) {
                    if ( mailbox120[checkedPos].getColor() == sideToMove &&
                            ( mailbox120[checkedPos].isQueen() || mailbox120[checkedPos].isRook() )
                            ) {
                        /* Король под шахом, если он под ударом дальнобойной горизонтальной фигуры того цвета
                         * чей ход будет сейчас
                         */
                        return false;
                    } else {
                        /* мы встретили фигуру своего цвета ИЛИ фигура другого цвета - не линейная дальнобойная,
                         * эта половина линии не опасна
                         */
                        break;
                    }
                }
                // иначе клетка пуста и мы смотрим следующую в этом направлении
                checkedPos += offset;
            }
        }
        // Проверка диагоналей в каждом направлении смещения
        for (byte offset : DIAG_OFFSETS) {
            checkedPos = (byte) (kingPos + offset);
            // если рядом стоит король - позиция нелегальная
            if (mailbox120[checkedPos].isKing())
                return false;

            while ( mailbox120[checkedPos] != OUT ) {
                if ( mailbox120[checkedPos] != EMP ) {
                    if ( mailbox120[checkedPos].getColor() == sideToMove &&
                            ( mailbox120[checkedPos].isQueen() || mailbox120[checkedPos].isBishop() )
                            ) {
                        /* Король под шахом, если он под ударом дальнобойной диагональной фигуры того цвета
                         * чей ход будет сейчас
                         */
                        return false;
                    } else {
                        /* мы встретили фигуру своего цвета ИЛИ фигура другого цвета - не горизонтальная дальнобойная,
                         * эта диагональ не опасна
                         */
                        break;
                    }
                }
                // иначе клетка пуста и мы смотрим следующую в этом направлении
                checkedPos += offset;
            }
        }
        // Проверка на шах от коня
        // в каждом направлении смещения
        for (byte offset : KNIGHT_OFFSETS) {
            // в каждом направлении смещения
            checkedPos = (byte) (kingPos + offset);
            // если стоит конь другого цвета - то король под шахом (проверка, что поле пустое или за пределами - не нужна)
            if ( mailbox120[checkedPos].getColor() == sideToMove && mailbox120[checkedPos].isKnight() ) {
                return false;
            }
        }
        // Мы проверили все фигуры, позиция легальна
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int s = A8.value; s >= H8.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A7.value; s >= H7.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A6.value; s >= H6.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A5.value; s >= H5.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A4.value; s >= H4.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A3.value; s >= H3.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A2.value; s >= H2.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A1.value; s >= H1.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }

        return sb.toString();
    }
}
