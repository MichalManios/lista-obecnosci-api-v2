package pl.gov.listaobecnosci.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.functions.model.Function;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    private WorkerService service;

    @Mock
    IWorkerRepository repository;

    @BeforeEach
    void setUp() {
        service = new WorkerService(repository);
    }

    @Test
    void getAllWorkersBySectionName() {
        var workerFirst = mock(Worker.class);
        var workerSecond = mock(Worker.class);
        var workerThird = mock(Worker.class);

        when(repository.findAllBySectionName("Kadry")).thenReturn(List.of(workerFirst, workerSecond, workerThird));

        assertThat(service.getAllWorkersBySectionName("Kadry"))
                .usingRecursiveComparison()
                .isEqualTo(List.of(workerFirst, workerSecond, workerThird));
    }

    @Test
    void addNewWorker() {
        var worker = mock(Worker.class);

        when(repository.save(worker)).thenReturn(worker);

        assertThat(service.addNewWorker(worker)).usingRecursiveComparison().isEqualTo(worker);
    }

    @Test
    void updateWorker() {
        var section = mock(Section.class);

        var workerToUpdate = Worker.builder()
                .id(1L)
                .name("Michał")
                .surname("Mikołajczyk")
                .section(section)
                .function(Function.WORKER)
                .build();

        var workerUpdated = Worker.builder()
                .id(1L)
                .name("Mikołaj")
                .surname("Mikołajczyk")
                .section(section)
                .function(Function.OFFICER)
                .build();

        when(repository.findById(workerToUpdate.getId())).thenReturn(Optional.of(workerToUpdate));
        when(repository.save(workerUpdated)).thenReturn(workerUpdated);

        assertThat(service.updateWorker(workerToUpdate)).usingRecursiveComparison().isEqualTo(workerUpdated);
    }

    @Test
    void updateWorkerShouldThrowException() {
        var workerToUpdate = mock(Worker.class);

        when(repository.findById(workerToUpdate.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IncorrectDataException.class)
                .isThrownBy(() ->  service.updateWorker(workerToUpdate))
                .withMessage("Nie ma takiego pracownika!!!");
    }

    @Test
    void deleteWorker() {
        var worker = mock(Worker.class);

        service.deleteWorker(worker);

        verify(repository, times(1)).delete(worker);
        assertThat(service.deleteWorker(worker)).usingRecursiveComparison().isEqualTo(worker);
    }
}