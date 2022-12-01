package pl.gov.listaobecnosci.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gov.listaobecnosci.functions.model.Function;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.dto.WorkerDTO;
import pl.gov.listaobecnosci.workers.dto.WorkerFlattened;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class IWorkerMapperTest {

    private IWorkerMapper mapper;

    private Worker workerFirst;

    private WorkerDTO workerFirstDTO;

    private WorkerFlattened workerFirstFlattened;

    private List<Worker> listWorkers;

    private List<WorkerFlattened> listWorkersFlattened;

    @BeforeEach
    void setUp() {
        mapper = new IWorkerMapperImpl();

        var sectionFirst = mock(Section.class);

        var sectionSecond = mock(Section.class);

        workerFirst = Worker.builder()
                .id(1L)
                .name("Michał")
                .surname("Mikołajczyk")
                .section(null)
                .function(Function.WORKER)
                .build();

        workerFirstDTO = WorkerDTO.builder()
                .id(1L)
                .name("Michał")
                .surname("Mikołajczyk")
                .section(null)
                .function(Function.WORKER)
                .build();

        var workerSecond = Worker.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .section(sectionFirst)
                .function(Function.WORKER)
                .build();

        var workerThird = Worker.builder()
                .id(3L)
                .name("Adam")
                .surname("Zieliński")
                .section(sectionSecond)
                .function(Function.OFFICER)
                .build();

        workerFirstFlattened = WorkerFlattened.builder()
                .id(1L)
                .name("Michał")
                .surname("Mikołajczyk")
                .function("Pracownik")
                .build();

        var workerSecondFlattened = WorkerFlattened.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .function("Pracownik")
                .build();

        var workerthirdFlattened = WorkerFlattened.builder()
                .id(3L)
                .name("Adam")
                .surname("Zieliński")
                .function("Funkcjonariusz")
                .build();

        listWorkers = List.of(workerFirst, workerSecond, workerThird);

        listWorkersFlattened = List.of(workerFirstFlattened, workerSecondFlattened, workerthirdFlattened);
    }

    @Test
    void mapToWorkerFlattened() {
        assertThat(mapper.mapToWorkerFlattened(workerFirst)).usingRecursiveComparison().isEqualTo(workerFirstFlattened);
    }

    @Test
    void mapToWorkersFlattened() {
        assertThat(mapper.mapToWorkersFlattened(listWorkers))
                .usingRecursiveComparison()
                .isEqualTo(listWorkersFlattened);
    }

    @Test
    void mapToWorker() {
        assertThat(mapper.mapToWorker(workerFirstDTO)).usingRecursiveComparison().isEqualTo(workerFirst);
    }

    @Test
    void mapToWorkerFromWorkerFlattened() {
        assertThat(mapper.mapToWorkerFromWorkerFlattened(workerFirstFlattened))
                .usingRecursiveComparison()
                .isEqualTo(workerFirst);
    }

    @Test
    void mapToWorkerDTO() {
        assertThat(mapper.mapToWorkerDTO(workerFirst)).usingRecursiveComparison().isEqualTo(workerFirst);
    }
}