package pl.gov.listaobecnosci.workers.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import pl.gov.listaobecnosci.functions.model.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gov.listaobecnosci.sections.entity.Section;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Builder
public class WorkerDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private Section section;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Function function;
}
