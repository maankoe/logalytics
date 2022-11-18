package logalytics.engine;

import logalytics.config.parsing.ConfigParseException;
import logalytics.config.UserConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LogListener {
    List<LogReader> readers;

    @Autowired
    public LogListener(UserConfigs listenerConfig) throws IOException, ConfigParseException {
        System.out.println(listenerConfig.logWatcherConfig());
//        this.readers = new ArrayList<>();
//        for (LogConfig logConfig : listenerConfig.getLogConfigs()) {
//            RegexSchema schema = new RegexSchema(logConfig.getRegex(), logConfig.getGroups());
//            readers.add(new LogReader(
//                    new BufferedReader(new FileReader(logConfig.getFileName())),
//                    new RegexParser(schema)
//            ));
//        }
//        for (LogReader reader : readers) {
//            System.out.println(reader.readEntry().raw());
//        }
    }
}
