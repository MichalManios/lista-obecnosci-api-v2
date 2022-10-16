package pl.gov.listaobecnosci.freedays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.freedays.entity.Freeday;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreedayServiceTest {

    private FreedayService service;

    @Mock
    IFreedayRepository repository;

    @BeforeEach
    void setUp() {
        service = new FreedayService(repository);
    }

    @Test
    void getAllFreedays() {
        var freedayFirst = mock(Freeday.class);
        var freedaySecond = mock(Freeday.class);
        var freedayThird = mock(Freeday.class);

        when(repository.findAll()).thenReturn(List.of(freedayFirst, freedaySecond, freedayThird));

        assertThat(service.getAllFreedays())
                .usingRecursiveComparison()
                .isEqualTo(List.of(freedayFirst, freedaySecond, freedayThird));
    }

    @Test
    void addNewFreeday() {
        var freeday = mock(Freeday.class);

        when(repository.save(freeday)).thenReturn(freeday);

        assertThat(service.addNewFreeday(freeday)).usingRecursiveComparison().isEqualTo(freeday);
    }

    @Test
    void updateFreeday() {
        var freedayToUpdate = Freeday.builder()
                .id(1L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy Rok")
                .build();

        var freedayUpdated = Freeday.builder()
                .id(1L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy Rok nowy wpis")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(freedayToUpdate));
        when(repository.save(freedayUpdated)).thenReturn(freedayUpdated);

        assertThat(service.updateFreeday(freedayToUpdate)).usingRecursiveComparison().isEqualTo(freedayUpdated);
    }

    @Test
    void onUpdateFreedayThrowExceptionIfFreedayDoesNotExist() {
        var freedayToUpdate = Freeday.builder()
                .id(99L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy Rok")
                .build();

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(IncorrectDataException.class)
                .isThrownBy(() ->  service.updateFreeday(freedayToUpdate))
                .withMessage(("Nie ma takiego święta!!!"));
    }

    @Test
    void deleteFreeday() {
        var freeday = mock(Freeday.class);

        service.deleteFreeday(freeday);

        verify(repository, times(1)).delete(freeday);
        assertThat(service.deleteFreeday(freeday)).usingRecursiveComparison().isEqualTo(freeday);
    }
}