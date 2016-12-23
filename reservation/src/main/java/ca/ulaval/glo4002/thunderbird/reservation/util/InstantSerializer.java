package ca.ulaval.glo4002.thunderbird.reservation.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class InstantSerializer extends StdSerializer<Instant> {
    public InstantSerializer() {
        this(null);
    }

    public InstantSerializer(Class<Instant> t) {
        super(t);
    }

    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(ISO_INSTANT.format(instant));
    }
}