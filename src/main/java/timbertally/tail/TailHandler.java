package timbertally.tail;

public interface TailHandler {
    void handle(char x);

    void exception(Exception exception);
}
