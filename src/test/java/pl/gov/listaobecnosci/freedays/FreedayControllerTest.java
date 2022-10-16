package pl.gov.listaobecnosci.freedays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.gov.listaobecnosci.freedays.dto.FreedayDTO;
import pl.gov.listaobecnosci.freedays.entity.Freeday;

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
class FreedayControllerTest {

    @Mock
    private IFreedayService service;

    @Mock
    private IFreedayMapper mapper;

    private MockMvc mockMvc;

    private Freeday freedayFirst;

    private Freeday freedaySecond;

    private Freeday freedayThird;

    private FreedayDTO freedayDTOFirst;

    private FreedayDTO freedayDTOSecond;

    private FreedayDTO freedayDTOThird;

    @BeforeEach
    void setUp() {
        FreedayController controller = new FreedayController(service, mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        freedayFirst = Freeday.builder()
                .id(1L)
                .day(1)
                .month(1)
                .nameOfHoliday("Nowy rok")
                .build();

        freedaySecond = Freeday.builder()
                .id(2L)
                .day(3)
                .month(5)
                .nameOfHoliday("3-go maja")
                .build();

        freedayThird = Freeday.builder()
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

        freedayDTOSecond = FreedayDTO.builder()
                .id(2L)
                .day(3)
                .month(5)
                .nameOfHoliday("3-go maja")
                .build();

        freedayDTOThird = FreedayDTO.builder()
                .id(3L)
                .day(25)
                .month(12)
                .nameOfHoliday("Boże Narodzenie")
                .build();
    }

    @Test
    void getAllFreedays() throws Exception {
        when(service.getAllFreedays()).thenReturn(List.of(freedayFirst, freedaySecond, freedayThird));
        when(mapper.mapToFreedayDTOs(List.of(freedayFirst, freedaySecond, freedayThird)))
                .thenReturn(List.of(freedayDTOFirst, freedayDTOSecond, freedayDTOThird));

        mockMvc.perform(get("/api/freedays").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].day").value(Matchers.containsInAnyOrder(1, 3, 25)))
                .andExpect(jsonPath("$[*].month").value(Matchers.containsInAnyOrder(1, 5, 12)))
                .andExpect(jsonPath("$[*].nameOfHoliday")
                        .value(Matchers.containsInAnyOrder("Nowy rok", "3-go maja", "Boże Narodzenie")));
    }

    @Test
    void addFreeday() throws Exception {
        when(mapper.mapToFreeday(freedayDTOFirst)).thenReturn(freedayFirst);
        when(service.addNewFreeday(freedayFirst)).thenReturn(freedayFirst);
        when(mapper.mapToFreedayDTO(freedayFirst)).thenReturn(freedayDTOFirst);

        mockMvc.perform(post("/api/freedays")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(freedayDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(freedayDTOFirst)));

        verify(service).addNewFreeday(freedayFirst);
    }

    @Test
    void updateFreeday() throws Exception {
        var freedayToUpdate = Freeday.builder()
                .id(3L)
                .day(26)
                .month(12)
                .nameOfHoliday("2-gi dzień Bożego Narodzenia")
                .build();

        var freedayToUpdateDTO = FreedayDTO.builder()
                .id(3L)
                .day(26)
                .month(12)
                .nameOfHoliday("2-gi dzień Bożego Narodzenia")
                .build();

        when(mapper.mapToFreeday(freedayToUpdateDTO)).thenReturn(freedayToUpdate);
        when(service.updateFreeday(freedayToUpdate)).thenReturn(freedayToUpdate);
        when(mapper.mapToFreedayDTO(freedayToUpdate)).thenReturn(freedayToUpdateDTO);

        mockMvc.perform(put("/api/freedays")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(freedayToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(freedayToUpdateDTO)));

        verify(service).updateFreeday(freedayToUpdate);
    }

    @Test
    void deleteFreeday() throws Exception {
        when(mapper.mapToFreeday(freedayDTOFirst)).thenReturn(freedayFirst);
        when(service.deleteFreeday(freedayFirst)).thenReturn(freedayFirst);

        mockMvc.perform(delete("/api/freedays")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(freedayDTOFirst)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(freedayDTOFirst)));

        verify(service).deleteFreeday(freedayFirst);
    }
}