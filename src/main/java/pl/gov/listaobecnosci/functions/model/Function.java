package pl.gov.listaobecnosci.functions.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.gov.listaobecnosci.common.util.DescribableEnum;
import pl.gov.listaobecnosci.common.util.DescribableEnumDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using = Function.Deserializer.class)
@Getter
@RequiredArgsConstructor
public enum Function implements DescribableEnum {

    DIRECTOR (0, "Dyrektor"),
    DEPUTY_DIRECTOR (1, "Dyrektor - zastępca"),
    MANAGER (2, "Kierownik"),
    DEPUTY_MANAGER (3,"Kierownik - zastępca"),
    OFFICER (4, "SW - funkcjonariusz"),
    WORKER (5, "SW - pracownik");

    private final int id;

    private final String description;

    private static final Map<String, Function> map;

    static {
        map = new HashMap<String, Function>();
        for (Function v : Function.values()) {
            map.put(v.description, v);
        }
    }

    public static Function findByKey(String description) {
        return map.get(description);
    }

    static class Deserializer extends DescribableEnumDeserializer<Function> {

    }
}
