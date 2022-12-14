package pl.gov.listaobecnosci.workers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private WorkerController controller;

    private MockMvc mockMvc;

    private Worker workerFirst;

    private WorkerDTO workerDTOFirst;

    private List<Worker> listWorkers;

    private List<WorkerFlattened> listWorkersFlattened;

    private Section sectionFirst;

    private WorkerFlattened workerFlattened;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        sectionFirst = Section.builder().name("Kadry").build();

        workerFirst = Worker.builder()
                .id(1L)
                .name("Micha??")
                .surname("Miko??ajczyk")
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
                .function("SW - pracownik")
                .build();

        workerDTOFirst = WorkerDTO.builder()
                .id(1L)
                .name("Micha??")
                .surname("Miko??ajczyk")
                .section(null)
                .function(Function.WORKER)
                .build();

        listWorkers = List.of(workerSecond);

        listWorkersFlattened = List.of(workerFlattened);
    }

    @Test
    void shouldReturnListOfAllWorkersSearchedBySectionName() throws Exception {
        when(service.getAllWorkersBySectionName(sectionFirst.getName())).thenReturn(listWorkers);
        when(mapper.mapToWorkersFlattened(listWorkers)).thenReturn(listWorkersFlattened);

        mockMvc.perform(get("/api/workers/"+sectionFirst.getName()).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(2)))
                .andExpect(jsonPath("$[*].name").value(Matchers.containsInAnyOrder("Mariusz")))
                .andExpect(jsonPath("$[*].surname").value(Matchers.containsInAnyOrder("Kaleta")))
                .andExpect(jsonPath("$[*].function")
                        .value(Matchers.containsInAnyOrder("SW - pracownik")));
    }

    @Test
    void shouldAddNewWorker() throws Exception {
        when(mapper.mapToWorker(workerDTOFirst)).thenReturn(workerFirst);
        when(service.addNewWorker(workerFirst)).thenReturn(workerFirst);
        when(mapper.mapToWorkerFlattened(workerFirst)).thenReturn(workerFlattened);

        mockMvc.perform(post("/api/workers")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(workerDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(workerFlattened)));

        verify(service).addNewWorker(workerFirst);
    }

    @Test
    void shouldUpdateWorker() throws Exception {
        var workerToUpdate = Worker.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Kaleta")
                .section(sectionFirst)
                .function(Function.OFFICER)
                .build();

        var workerToUpdateDTO = WorkerDTO.builder()
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
                .function("SW - funkcjonariusz")
                .build();

        when(mapper.mapToWorker(workerToUpdateDTO)).thenReturn(workerToUpdate);
        when(service.updateWorker(workerToUpdate)).thenReturn(workerToUpdate);
        when(mapper.mapToWorkerFlattened(workerToUpdate)).thenReturn(workerToUpdatedFlattened);

        mockMvc.perform(put("/api/workers")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(workerToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(workerToUpdatedFlattened)));

        verify(service).updateWorker(workerToUpdate);
    }

    @Test
    void shouldDeleteWorker() throws Exception {
        when(service.deleteWorker(workerFirst.getId())).thenReturn(workerFirst);
        when(mapper.mapToWorkerFlattened(workerFirst)).thenReturn(workerFlattened);

        mockMvc.perform(delete("/api/workers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(workerFlattened)));

        verify(service).deleteWorker(workerFirst.getId());
    }
}