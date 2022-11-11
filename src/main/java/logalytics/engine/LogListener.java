package logalytics.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogListener {
    List<LogReader> readers;

    @Autowired
    public LogListener(LogListenerConfig config) throws IOException {
        this.readers = new ArrayList<>();
        for (String file : config.getFiles()) {
            readers.add(new LogReader(
                    new BufferedReader(new FileReader(file))
            ));
        }
        for (LogReader reader : readers) {
            System.out.println(reader.readEntry().raw());
        }
    }
}
