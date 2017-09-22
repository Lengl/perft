package ru.pflb.perft;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public enum Piece {

    // 1
    W_KING((byte)0, WHITE, 'K'),
    // 7
    B_KING((byte)0, BLACK, 'k'),
    // 2
    W_BISHOP((byte)1, WHITE, 'B'),
    // 8
    B_BISHOP((byte)1, BLACK, 'b'),
    // 3
    W_ROOK((byte)2, WHITE, 'R'),
    // 9
    B_ROOK((byte)2, BLACK, 'r'),
    // 4
    W_QUEEN((byte)3, WHITE, 'Q'),
    // 10
    B_QUEEN((byte)3, BLACK, 'q'),
    // 5
    W_KNIGHT((byte)4, WHITE, 'N'),
    // 11
    B_KNIGHT((byte)4, BLACK, 'n'),
    // -1
    OUT((byte)-1, '-'),
    // 0
    EMP((byte)0, '.');

    public final byte code;
    public final char name;

    Piece(byte pieceType, Color color, char name) {
        this((byte)(color.color * 6 + pieceType + 1), name);
    }

    Piece(byte code, char name) {
        this.code = code;
        this.name = name;
    }

    public Color getColor() {
        return code <= 6 ? WHITE : BLACK;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
