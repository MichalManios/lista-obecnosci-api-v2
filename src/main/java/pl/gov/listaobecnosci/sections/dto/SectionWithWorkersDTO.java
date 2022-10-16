package pl.gov.listaobecnosci.sections.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gov.listaobecnosci.workers.entity.Worker;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectionWithWorkersDTO {

    private Long id;

    @NotNull
    private String name;

    private List<Worker> workers;
}
