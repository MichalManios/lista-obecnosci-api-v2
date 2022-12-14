package pl.gov.listaobecnosci.security.role;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.gov.listaobecnosci.security.role.dto.RoleDTO;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
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
class RoleControllerTest {

    @Mock
    private IRoleService service;

    @Mock
    private IRoleMapper mapper;

    @InjectMocks
    private RoleController controller;

    private MockMvc mockMvc;

    private Role roleFirst;

    private RoleDTO roleDTOFirst;

    private List<Role> listRole;

    private List<RoleDTO> listRoleDTO;

    private User userFirst;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        userFirst = User.builder()
                .id(1L)
                .name("Micha??")
                .password("Haslo")
                .role(roleFirst)
                .build();

        var userSecond = mock(User.class);

        roleFirst = Role.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(userFirst)
                .build();

        Role roleSecond = Role.builder()
                .id(2L)
                .name("USER")
                .description("U??ytkownik")
                .user(userSecond)
                .build();

        var roleThird = Role.builder()
                .id(3L)
                .name("GUEST")
                .description("Go????")
                .user(userFirst)
                .build();

        roleDTOFirst = RoleDTO.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .build();

        var roleDTOSecond = RoleDTO.builder()
                .id(2L)
                .name("USER")
                .description("U??ytkownik")
                .build();

        var roleDTOThird = RoleDTO.builder()
                .id(3L)
                .name("GUEST")
                .description("Go????")
                .build();

        listRole = List.of(roleFirst, roleSecond, roleThird);
        listRoleDTO = List.of(roleDTOFirst, roleDTOSecond, roleDTOThird);
    }

    @Test
    void shouldReturnListOfAllRoles() throws Exception {
        when(service.getAllRoles()).thenReturn(listRole);
        when(mapper.mapToRoleDTOs(listRole)).thenReturn(listRoleDTO);

        mockMvc.perform(get("/api/roles").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].name")
                        .value(Matchers.containsInAnyOrder("ADMINISTRATOR", "USER", "GUEST")))
                .andExpect(jsonPath("$[*].description")
                        .value(Matchers.containsInAnyOrder("Administrator", "U??ytkownik", "Go????")));
    }

    @Test
    void shouldReturnRoleSearchedByUser() throws Exception {
        when(service.getRoleByUser(userFirst)).thenReturn(roleFirst);
        when(mapper.mapToRoleDTO(roleFirst)).thenReturn(roleDTOFirst);

        mockMvc.perform(get("/api/roles/users").accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(userFirst)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("ADMINISTRATOR"))
                .andExpect(jsonPath("description").value("Administrator"));
    }

    @Test
    void ShouldAddNewRole() throws Exception {
        when(mapper.mapToRole(roleDTOFirst)).thenReturn(roleFirst);
        when(service.addNewRole(roleFirst)).thenReturn(roleFirst);
        when(mapper.mapToRoleDTO(roleFirst)).thenReturn(roleDTOFirst);

        mockMvc.perform(post("/api/roles")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(roleDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(roleDTOFirst)));

        verify(service).addNewRole(roleFirst);
    }

    @Test
    void shouldUpdateRole() throws Exception {
        var user = mock(User.class);

        var roleToUpdate = Role.builder()
                .id(2L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(user)
                .build();

        var roleToUpdateDTO = RoleDTO.builder()
                .id(2L)
                .name("USER")
                .description("U??ytkownik")
                .build();

        when(mapper.mapToRole(roleToUpdateDTO)).thenReturn(roleToUpdate);
        when(service.updateRole(roleToUpdate)).thenReturn(roleToUpdate);
        when(mapper.mapToRoleDTO(roleToUpdate)).thenReturn(roleToUpdateDTO);

        mockMvc.perform(put("/api/roles")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(roleToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(roleToUpdateDTO)));

        verify(service).updateRole(roleToUpdate);
    }

    @Test
    void shouldDeleteRole() throws Exception {
        when(mapper.mapToRole(roleDTOFirst)).thenReturn(roleFirst);
        when(service.deleteRole(roleFirst)).thenReturn(roleFirst);

        mockMvc.perform(delete("/api/roles")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(roleDTOFirst)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(roleDTOFirst)));

        verify(service).deleteRole(roleFirst);
    }
}