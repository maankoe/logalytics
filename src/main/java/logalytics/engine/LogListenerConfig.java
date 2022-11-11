package logalytics.engine;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix="log-listener")
@EnableConfigurationProperties
public class LogListenerConfig {
    private final List<String> files = new ArrayList<>();

    public List<String> getFiles() {
        return this.files;
    }
}
