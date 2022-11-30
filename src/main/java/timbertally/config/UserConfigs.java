package timbertally.config;

import timbertally.config.parsing.ConfigParseException;
import timbertally.config.parsing.LogSchemaConfigParser;
import timbertally.config.parsing.LogWatcherConfigLoader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.FileInputStream;
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
        return new LogWatcherConfigLoader(
                new LogSchemaConfigParser()
        ).load(new FileInputStream(this.logWatcherConfigPath));
    }
}
