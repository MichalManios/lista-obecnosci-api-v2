package pl.gov.listaobecnosci.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class DescribableEnumDeserializer<T extends DescribableEnum> extends JsonDeserializer<T> {

    private final Class<T> typeClass;

    public DescribableEnumDeserializer() {
        this.typeClass = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        String name = ((JsonNode) treeNode.get("name")).asText();
        if (name == null) {
            return null;
        }

        return forName(name)
            .orElseThrow(() -> context.mappingException(
                String.format("No key %s in enum %s", name, typeClass.getName()))
            );
    }

    private Optional<T> forName(String name) {
        return Arrays.stream(typeClass.getEnumConstants())
            .filter(enumItem -> enumItem.name().equals(name))
            .findFirst();
    }
}
