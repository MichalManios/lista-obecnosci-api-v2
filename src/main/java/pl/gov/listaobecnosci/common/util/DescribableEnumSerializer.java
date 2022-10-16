package pl.gov.listaobecnosci.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DescribableEnumSerializer extends JsonSerializer<DescribableEnum> {

    @Override
    public void serialize(DescribableEnum describableEnum, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", describableEnum.name());
        jsonGenerator.writeStringField("description", describableEnum.getDescription());
        jsonGenerator.writeEndObject();
    }
}
