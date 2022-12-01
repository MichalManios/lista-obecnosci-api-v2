package pl.gov.listaobecnosci.workers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.gov.listaobecnosci.functions.model.Function;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.dto.WorkerDTO;
import pl.gov.listaobecnosci.workers.dto.WorkerFlattened;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static pl.gov.listaobecnosci.TestUtil.APPLICATION_JSON_UTF8;
import static pl.gov.listaobecnosci.TestUtil.toJsonString;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest {

    @Mock
    private IWorkerService service;

    @Mock
    private IWorkerMapper mapper;

    private MockMvc mockMvc;

    private Worker workerFirst;

    private WorkerDTO workerDTOFirst;

    private List<Worker> listWorkers;

    private List<WorkerFlattened> listWorkersFlattened;

    private Section sectionFirst;

    private WorkerFlattened workerFlattened;

    @BeforeEach
    void setUp() {
        WorkerController controller = new WorkerController(service, mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        sectionFirst = Section.builder().name("Kadry").build();

        workerFirst = Worker.builder()
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

        workerFlattened = WorkerFlattened.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .function("Pracownik")
                .build();

        workerDTOFirst = WorkerDTO.builder()
                .id(1L)
                .name("Michał")
                .surname("Mikołajczyk")
                .section(null)
                .function(Function.WORKER)
                .build();

        listWorkers = List.of(workerSecond);

        listWorkersFlattened = List.of(workerFlattened);
    }

    @Test
    void getAllWorkersBySectionName() throws Exception {
        when(service.getAllWorkersBySectionName(sectionFirst.getName())).thenReturn(listWorkers);
        when(mapper.mapToWorkersFlattened(listWorkers)).thenReturn(listWorkersFlattened);

        mockMvc.perform(get("/api/workers/"+sectionFirst.getName()).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(2)))
                .andExpect(jsonPath("$[*].name").value(Matchers.containsInAnyOrder("Mariusz")))
                .andExpect(jsonPath("$[*].surname").value(Matchers.containsInAnyOrder("Kaleta")))
                .andExpect(jsonPath("$[*].function").value(Matchers.containsInAnyOrder("Pracownik")));
    }

    @Test
    void addWorker() throws Exception {
        when(mapper.mapToWorker(workerDTOFirst)).thenReturn(workerFirst);
        when(service.addNewWorker(workerFirst)).thenReturn(workerFirst);
        when(mapper.mapToWorkerDTO(workerFirst)).thenReturn(workerDTOFirst);

        mockMvc.perform(post("/api/workers")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(workerDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(workerDTOFirst)));

        verify(service).addNewWorker(workerFirst);
    }

    @Test
    void updateWorker() throws Exception {
        var workerToUpdate = Worker.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .section(sectionFirst)
                .function(Function.OFFICER)
                .build();

        var workerToUpdatedFlattened = WorkerFlattened.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .function("Funkcjonariusz")
                .build();

        when(mapper.mapToWorkerFromWorkerFlattened(workerToUpdatedFlattened)).thenReturn(workerToUpdate);
        when(service.updateWorker(workerToUpdate)).thenReturn(workerToUpdate);
        when(mapper.mapToWorkerFlattened(workerToUpdate)).thenReturn(workerToUpdatedFlattened);

        mockMvc.perform(put("/api/workers")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(workerToUpdatedFlattened)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(workerToUpdatedFlattened)));

        verify(service).updateWorker(workerToUpdate);
    }

    @Test
    void deleteWorker() throws Exception {
        when(mapper.mapToWorkerFromWorkerFlattened(workerFlattened)).thenReturn(workerFirst);
        when(service.deleteWorker(workerFirst)).thenReturn(workerFirst);

        mockMvc.perform(delete("/api/workers")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(workerFlattened)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(workerFlattened)));

        verify(service).deleteWorker(workerFirst);
    }
}