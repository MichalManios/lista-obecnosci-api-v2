package pl.gov.listaobecnosci.sections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gov.listaobecnosci.sections.dto.SectionDTO;
import pl.gov.listaobecnosci.sections.dto.SectionWithWorkersDTO;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

import static org.mockito.Mockito.mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ISectionMapperTest {

    private ISectionMapper mapper;

    private Section sectionFirst;

    private Section sectionSecond;

    private SectionDTO sectionDTOFirst;

    private SectionWithWorkersDTO sectionWithWorkersDTO;

    private List<Section> listSection;

    private List<SectionDTO> listSectionDTO;

    @BeforeEach
    void setUp() {
        mapper = new ISectionMapperImpl();

        var workers = mock(Worker.class);

        sectionFirst = Section.builder()
                .id(1L)
                .name("Finanse")
                .workers(null)
                .build();

        sectionSecond = Section.builder()
                .id(2L)
                .name("Ochrona")
                .workers(List.of(workers))
                .build();

        var sectionThird = Section.builder()
                .id(3L)
                .name("Kadry")
                .workers(List.of(workers))
                .build();

        sectionDTOFirst = SectionDTO.builder()
                .id(1L)
                .name("Finanse")
                .build();

        var sectionDTOSecond = SectionDTO.builder()
                .id(2L)
                .name("Ochrona")
                .build();

        var sectionDTOThird = SectionDTO.builder()
                .id(3L)
                .name("Kadry")
                .build();

        sectionWithWorkersDTO = SectionWithWorkersDTO.builder()
                .id(2L)
                .name("Ochrona")
                .workers(List.of(workers))
                .build();

        listSection = List.of(sectionFirst, sectionSecond, sectionThird);
        listSectionDTO = List.of(sectionDTOFirst, sectionDTOSecond, sectionDTOThird);
    }

    @Test
    void mapToSection() {
        assertThat(mapper.mapToSection(sectionDTOFirst)).usingRecursiveComparison().isEqualTo(sectionFirst);
    }

    @Test
    void mapToSectionDTO() {
        assertThat(mapper.mapToSectionDTO(sectionFirst)).usingRecursiveComparison().isEqualTo(sectionDTOFirst);
    }

    @Test
    void mapToSectionDTOs() {
        assertThat(mapper.mapToSectionDTOs(listSection)).usingRecursiveComparison().isEqualTo(listSectionDTO);
    }

    @Test
    void mapToSectionWithWorkersDTO() {
        assertThat(mapper.mapToSectionWithWorkersDTO(sectionSecond))
                .usingRecursiveComparison()
                .isEqualTo(sectionWithWorkersDTO);
    }
}