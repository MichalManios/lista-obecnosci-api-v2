package pl.gov.listaobecnosci.freedays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gov.listaobecnosci.freedays.dto.FreedayDTO;
import pl.gov.listaobecnosci.freedays.entity.Freeday;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IFreedayMapperTest {

    private IFreedayMapper mapper;

    private Freeday freedayFirst;

    private FreedayDTO freedayDTOFirst;

    private List<Freeday> listFreeday;

    private List<FreedayDTO> listFreedayDTO;

    @BeforeEach
    void setUp() {
        mapper = new IFreedayMapperImpl();

        freedayFirst = Freeday.builder()
                .id(1L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy rok")
                .build();

        Freeday freedaySecond = Freeday.builder()
                .id(2L)
                .day(3)
                .month(5)
                .nameOfHoliday("3-go maja")
                .build();

        Freeday freedayThird = Freeday.builder()
                .id(3L)
                .day(25)
                .month(12)
                .nameOfHoliday("Boże Narodzenie")
                .build();

        freedayDTOFirst = FreedayDTO.builder()
                .id(1L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy rok")
                .build();

        FreedayDTO freedayDTOSecond = FreedayDTO.builder()
                .id(2L)
                .day(3)
                .month(5)
                .nameOfHoliday("3-go maja")
                .build();

        FreedayDTO freedayDTOThird = FreedayDTO.builder()
                .id(3L)
                .day(25)
                .month(12)
                .nameOfHoliday("Boże Narodzenie")
                .build();

        listFreeday = List.of(freedayFirst, freedaySecond, freedayThird);

        listFreedayDTO = List.of(freedayDTOFirst, freedayDTOSecond, freedayDTOThird);
    }

    @Test
    void shouldMapToFreeday() {
        assertThat(mapper.mapToFreeday(freedayDTOFirst)).usingRecursiveComparison().isEqualTo(freedayFirst);
    }

    @Test
    void shouldMapToFreedayDTO() {
        assertThat(mapper.mapToFreedayDTO(freedayFirst)).usingRecursiveComparison().isEqualTo(freedayDTOFirst);
    }

    @Test
    void shouldMapToFreedayDTOs() {
        assertThat(mapper.mapToFreedayDTOs(listFreeday)).usingRecursiveComparison().isEqualTo(listFreedayDTO);
    }
}