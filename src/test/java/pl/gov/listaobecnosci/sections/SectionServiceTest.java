package pl.gov.listaobecnosci.sections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.sections.entity.Section;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

    private SectionService service;

    @Mock
    ISectionRepository repository;

    @BeforeEach
    void setUp() {
        service = new SectionService(repository);
    }

    @Test
    void getAllSections() {
        var sectionFirst = mock(Section.class);
        var sectionSecond = mock(Section.class);
        var sectionThird = mock(Section.class);

        when(repository.findAll()).thenReturn(List.of(sectionFirst, sectionSecond, sectionThird));

        assertThat(service.getAllSections())
                .usingRecursiveComparison()
                .isEqualTo(List.of(sectionFirst, sectionSecond, sectionThird));
    }

    @Test
    void getSectionByName() {
        var section = mock(Section.class);

        when(repository.findByName("Kadry")).thenReturn(section);

        assertThat(service.getSectionByName("Kadry")).usingRecursiveComparison().isEqualTo(section);
    }

    @Test
    void addNewSection() {
        var section = mock(Section.class);

        when(repository.save(section)).thenReturn(section);

        assertThat(service.addNewSection(section)).usingRecursiveComparison().isEqualTo(section);
    }

    @Test
    void updateSection() {
        var sectionToUpdate = Section.builder()
                .id(1L)
                .name("Kadry")
                .workers(Collections.emptyList())
                .build();

        var sectionUpdated = Section.builder()
                .id(1L)
                .name("Kadry-Organizacyjne")
                .workers(Collections.emptyList())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(sectionToUpdate));
        when(repository.save(sectionUpdated)).thenReturn(sectionUpdated);

        assertThat(service.updateSection(sectionToUpdate)).usingRecursiveComparison().isEqualTo(sectionUpdated);
    }

    @Test
    void updateSectionShouldThrowException() {
        var sectionToUpdate = Section.builder()
                .id(1L)
                .name("Kadry")
                .workers(Collections.emptyList())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(IncorrectDataException.class)
                .isThrownBy(() ->  service.updateSection(sectionToUpdate))
                .withMessage("Nie ma takiego działu!!!");
    }

    @Test
    void deleteSection() {
        var section = mock(Section.class);

        service.deleteSection(section);

        verify(repository, times(1)).delete(section);
        assertThat(service.deleteSection(section)).usingRecursiveComparison().isEqualTo(section);
    }
}