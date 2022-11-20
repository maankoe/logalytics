package logalytics.tail;

import java.io.FileNotFoundException;

public interface TailHandler {
    void handle(char x);

    void exception(Exception exception);
}
