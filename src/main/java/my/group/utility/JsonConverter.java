package my.group.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;


public class JsonConverter {
    private static final Logger LOGGER = new MyLogger().getLogger();
    private final ObjectMapper mapper = new ObjectMapper();

    public String createJsonFromObjects(Object obj) {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return (mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            LOGGER.error("Invalid POJO object: {} ", obj, e);
            System.exit(0);
        }
        return null;
    }
}

