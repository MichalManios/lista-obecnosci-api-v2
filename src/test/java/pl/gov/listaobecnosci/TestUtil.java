package pl.gov.listaobecnosci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    public static String toJsonString(Object jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        var module = new SimpleModule();
        module.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        module.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        objectMapper.registerModule(module);

        return objectMapper.writeValueAsString(jsonObject);
    }

    public static void persistAll(EntityManager entityManager, Object... objects) {
        Arrays.stream(objects).forEachOrdered(entityManager::persist);
    }
}
