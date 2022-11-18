package logalytics.config.parsing;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeParser<T> {
    T parse(JsonNode node) throws ConfigParseException;
}
