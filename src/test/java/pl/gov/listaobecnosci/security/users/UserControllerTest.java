package pl.gov.listaobecnosci.security.users;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.dto.UserDTO;
import pl.gov.listaobecnosci.security.users.entity.User;

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
class UserControllerTest {

    @Mock
    private IUserService service;

    @Mock
    private IUserMapper mapper;

    private MockMvc mockMvc;

    private User userFirst;

    private UserDTO userDTOFirst;

    private List<User> listUsers;

    private List<UserDTO> listUsersDTO;

    private Role roleFirst;

    private Role roleSecond;

    @BeforeEach
    void setUp() {
        UserController controller = new UserController(service, mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        userFirst = User.builder()
                .id(1L)
                .name("Michał")
                .password("Haslo1")
                .role(roleFirst)
                .build();

        var userSecond = User.builder()
                .id(2L)
                .name("Mariusz")
                .password("Haslo11")
                .role(roleFirst)
                .build();

        var userThird = User.builder()
                .id(3L)
                .name("Kamil")
                .password("Haslo111")
                .role(roleSecond)
                .build();

        userDTOFirst = UserDTO.builder()
                .id(1L)
                .name("Michał")
                .password("Haslo1")
                .role(roleFirst)
                .build();

        var userDTOSecond = UserDTO.builder()
                .id(2L)
                .name("Mariusz")
                .password("Haslo11")
                .role(roleFirst)
                .build();

        var userDTOThird = UserDTO.builder()
                .id(3L)
                .name("Kamil")
                .password("Haslo111")
                .role(roleSecond)
                .build();

        roleFirst = Role.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(userFirst)
                .build();

        roleSecond = Role.builder()
                .id(2L)
                .name("USER")
                .description("Użytkownik")
                .user(userSecond)
                .build();

        listUsers = List.of(userFirst, userSecond, userThird);

        listUsersDTO = List.of(userDTOFirst, userDTOSecond, userDTOThird);
    }

    @Test
    void getAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(listUsers);
        when(mapper.mapToUserDTOs(listUsers)).thenReturn(listUsersDTO);

        mockMvc.perform(get("/api/users").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].name")
                        .value(Matchers.containsInAnyOrder("Michał", "Mariusz", "Kamil")))
                .andExpect(jsonPath("$[*].password")
                        .value(Matchers.containsInAnyOrder("Haslo1", "Haslo11", "Haslo111")));
    }

    @Test
    void addUser() throws Exception {
        when(mapper.mapToUser(userDTOFirst)).thenReturn(userFirst);
        when(service.addNewUser(userFirst)).thenReturn(userFirst);
        when(mapper.mapToUserDTO(userFirst)).thenReturn(userDTOFirst);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(userDTOFirst)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJsonString(userDTOFirst)));

        verify(service).addNewUser(userFirst);
    }

    @Test
    void updateUser() throws Exception {
        var userToUpdate = User.builder()
                .id(2L)
                .name("Mariusz")
                .password("Haslo11")
                .role(roleFirst)
                .build();

        var userToUpdateDTO = UserDTO.builder()
                .id(2L)
                .name("Matias")
                .password("Haslo111")
                .role(roleFirst)
                .build();

        when(mapper.mapToUser(userToUpdateDTO)).thenReturn(userToUpdate);
        when(service.updateUser(userToUpdate)).thenReturn(userToUpdate);
        when(mapper.mapToUserDTO(userToUpdate)).thenReturn(userToUpdateDTO);

        mockMvc.perform(put("/api/users")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(userToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(userToUpdateDTO)));

        verify(service).updateUser(userToUpdate);
    }

    @Test
    void deleteUser() throws Exception {
        when(mapper.mapToUser(userDTOFirst)).thenReturn(userFirst);
        when(service.deleteUser(userFirst)).thenReturn(userFirst);

        mockMvc.perform(delete("/api/users")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(userDTOFirst)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(userDTOFirst)));

        verify(service).deleteUser(userFirst);
    }
}