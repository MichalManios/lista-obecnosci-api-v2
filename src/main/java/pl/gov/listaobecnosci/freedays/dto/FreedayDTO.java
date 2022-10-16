package pl.gov.listaobecnosci.freedays.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreedayDTO {

    private Long id;

    @NotNull
    private int day;

    @NotNull
    private int month;

    @NotNull
    private String nameOfHoliday;
}
