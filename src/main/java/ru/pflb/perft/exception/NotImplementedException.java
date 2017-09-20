package ru.pflb.perft.exception;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
        super("Еще не реализовано");
    }
}
