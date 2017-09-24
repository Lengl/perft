package ru.pflb.perft;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Move {

    public final Piece piece;

    public final Piece capture;

    public final byte from, to;

    public Move(byte from, byte to, Piece piece) {
        this(from, to, piece, null);
    }

    public Move(byte from, byte to, Piece piece, Piece capture) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capture = capture;
    }

    public boolean isCapture() {
        return capture != null;
    }

    /**
     * @return <a href="полная запись ходов">https://ru.wikipedia.org/wiki/%D0%A8%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D1%8F</a>
     */
    @Override
    public String toString() {
        return piece.name().substring(0, 1) + from + "-" + to;
    }

}
