package ca.ulaval.glo4002.thunderbird.boarding.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class InstantDeserializer extends StdDeserializer<Instant> {
    public InstantDeserializer() {
        this(null);
    }

    public InstantDeserializer(Class<Instant> t) {
        super(t);
    }

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String str = jsonParser.getValueAsString();

        return ISO_INSTANT.parse(str, Instant::from);
    }
}