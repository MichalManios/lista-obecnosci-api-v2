package pl.gov.listaobecnosci.common.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = DescribableEnumSerializer.class)
public interface DescribableEnum {

    String getDescription();

    String name();
}
