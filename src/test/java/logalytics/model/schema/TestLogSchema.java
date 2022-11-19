package logalytics.model.schema;

import logalytics.engine.LogReader;
import logalytics.model.Entry;
import logalytics.model.parsing.LogParser;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLogSchema {
    @Test
    public void testLogSchemaParse() {
        LogParser parser = mock(LogParser.class);
        LogSchema schema = new LogSchema(mock(LogReader.class), parser);
        Entry expectedEntry = mock(Entry.class);
        when(parser.parse(anyString())).thenReturn(expectedEntry);
        Entry entry = schema.parse("Hello world");
        assertThat(entry).isSameAs(expectedEntry);
    }

    @Test
    public void testLogSchemaReader() {
        List<String> lines = Lists.newArrayList("Hello world", "we meet again");
        LogReader reader = mock(LogReader.class);
        when(reader.producer()).thenReturn(Flux.fromIterable(lines));
        LogSchema schema = new LogSchema(reader, mock(LogParser.class));
        StepVerifier.create(schema.producer())
                .expectNextSequence(lines)
                .verifyComplete();
    }
}
