package ca.ulaval.glo4002.thunderbird.reservation.config;

import ca.ulaval.glo4002.thunderbird.reservation.util.InstantDeserializer;
import ca.ulaval.glo4002.thunderbird.reservation.util.InstantSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.*;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JsonMapperConfiguration extends JacksonJaxbJsonProvider {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule instantModule = new SimpleModule();
        instantModule.addSerializer(Instant.class, new InstantSerializer());
        instantModule.addDeserializer(Instant.class, new InstantDeserializer());

        mapper.registerModule(instantModule);
        mapper.registerModule(new ParameterNamesModule());
        mapper.setPropertyNamingStrategy(new SnakeCaseStrategy());
        mapper.configure(AUTO_DETECT_GETTERS, false);
        mapper.configure(AUTO_DETECT_IS_GETTERS, false);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(AUTO_DETECT_SETTERS, false);
        mapper.setVisibility(FIELD, ANY);
    }

    public JsonMapperConfiguration() {
        super();
        setMapper(mapper);
    }
}