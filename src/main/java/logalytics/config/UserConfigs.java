package logalytics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;
import java.io.IOException;

@ConstructorBinding
@ConfigurationProperties
@EnableConfigurationProperties
public class UserConfigs {
    private final String logWatcherConfigPath;

    public UserConfigs(String logWatcherConfigPath) {
        this.logWatcherConfigPath = logWatcherConfigPath;
    }

    public LogWatcherConfig logWatcherConfig() throws IOException, ConfigParseException {
        return new LogWatcherConfigLoader().load(new File(this.logWatcherConfigPath));
    }
}
