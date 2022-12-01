package pl.gov.listaobecnosci.sections;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.gov.listaobecnosci.functions.model.Function;
import pl.gov.listaobecnosci.sections.dto.SectionDTO;
import pl.gov.listaobecnosci.sections.dto.SectionWithWorkersDTO;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.Collections;
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
class SectionControllerTest {

    @Mock
    private ISectionService service;

    @Mock
    private ISectionMapper mapper;

    private MockMvc mockMvc;

    private Worker workerFirst;

    private Worker workerThird;

    private Section sectionFirst;

    private Section sectionSecond;

    private Section sectionThird;

    private SectionDTO sectionDTOFirst;

    private SectionDTO sectionDTOSecond;

    private SectionDTO sectionDTOThird;

    @BeforeEach
    void setUp() {
        SectionController controller = new SectionController(service, mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        workerFirst = Worker.builder()
                .id(1L)
                .name("Michał")
                .surname("Michalak")
                .section(sectionFirst)
                .function(Function.WORKER)
                .build();

        Worker workerSecond = Worker.builder()
                .id(2L)
                .name("Mariusz")
                .surname("Muszalski")
                .section(sectionSecond)
                .function(Function.WORKER)
                .build();

        workerThird = Worker.builder()
                .id(3L)
                .name("Paweł")
                .surname("Stasiak")
                .section(sectionFirst)
                .function(Function.WORKER)
                .build();

        sectionFirst = Section.builder()
                .id(1L)
                .name("Kadry")
                .workers(List.of(workerFirst, workerThird))
                .build();

        sectionSecond = Section.builder()
                .id(2L)
                .name("Ochrona")
                .workers(List.of(workerSecond))
                .build();

        sectionThird = Section.builder()
                .id(3L)
                .name("Ewidencja")
                .workers(Collections.emptyList())
                .build();

        sectionDTOFirst = SectionDTO.builder()
                .id(1L)
                .name("Kadry")
                .build();

        sectionDTOSecond = SectionDTO.builder()
                .id(2L)
                .name("Ochrona")
                .build();

        sectionDTOThird = SectionDTO.builder()
                .id(3L)
                .name("Ewidencja")
                .build();
    }

    @Test
    void getAllSections() throws Exception {
        when(service.getAllSections()).thenReturn(List.of(sectionFirst, sectionSecond, sectionThird));
        when(mapper.mapToSectionDTOs(List.of(sectionFirst, sectionSecond, sectionThird)))
                .thenReturn(List.of(sectionDTOFirst, sectionDTOSecond, sectionDTOThird));

        mockMvc.perform(get("/api/sections").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].name")
                        .value(Matchers.containsInAnyOrder("Kadry", "Ochrona", "Ewidencja")));
    }

    @Test
    void getSectionByName() throws Exception {
        var sectionWithWorkers = SectionWithWorkersDTO.builder()
                .id(1L)
                .name("Kadry")
                .workers(Lists.newArrayList(workerFirst, workerThird))
                .build();

        when(service.getSectionByName("Kadry")).thenReturn(sectionFirst);
        when(mapper.mapToSectionWithWorkersDTO(sectionFirst)).thenReturn(sectionWithWorkers);

        mockMvc.perform(get("/api/sections/Kadry").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Kadry"));
    }

    @Test
    void addSection() throws Exception {
        when(mapper.mapToSection(sectionDTOFirst)).thenReturn(sectionFirst);
        when(service.addNewSection(sectionFirst)).thenReturn(sectionFirst);
        when(mapper.mapToSectionDTO(sectionFirst)).thenReturn(sectionDTOFirst);

        mockMvc.perform(post("/api/sections")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(sectionDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(sectionDTOFirst)));

        verify(service).addNewSection(sectionFirst);
    }

    @Test
    void updateSection() throws Exception {
        var sectionToUpdate = Section.builder()
                .id(2L)
                .name("Kierownictwo")
                .workers(Collections.emptyList())
                .build();

        var sectionToUpdateDTO = SectionDTO.builder()
                .id(2L)
                .name("Ewidencja")
                .build();

        when(mapper.mapToSection(sectionToUpdateDTO)).thenReturn(sectionToUpdate);
        when(service.updateSection(sectionToUpdate)).thenReturn(sectionToUpdate);
        when(mapper.mapToSectionDTO(sectionToUpdate)).thenReturn(sectionToUpdateDTO);

        mockMvc.perform(put("/api/sections")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(sectionToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(sectionToUpdateDTO)));

        verify(service).updateSection(sectionToUpdate);
    }

    @Test
    void deleteSection() throws Exception {
        when(mapper.mapToSection(sectionDTOFirst)).thenReturn(sectionFirst);
        when(service.deleteSection(sectionFirst)).thenReturn(sectionFirst);

        mockMvc.perform(delete("/api/sections")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(sectionDTOFirst)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(sectionDTOFirst)));

        verify(service).deleteSection(sectionFirst);
    }
}