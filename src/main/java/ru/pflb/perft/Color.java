package ru.pflb.perft;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public enum Color {

    WHITE((byte)0), BLACK((byte)1);

    public byte color;

    Color(byte color) {
        this.color = color;
    }
}
