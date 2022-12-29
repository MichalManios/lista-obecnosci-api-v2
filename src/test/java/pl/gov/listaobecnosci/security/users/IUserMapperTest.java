package pl.gov.listaobecnosci.security.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.dto.UserDTO;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class IUserMapperTest {

    private IUserMapper mapper;

    private User userFirst;

    private UserDTO userDTOFirst;

    private List<User> listUsers;

    private List<UserDTO> listUsersDTO;

    @BeforeEach
    void setUp() {
        mapper = new IUserMapperImpl();

        var roleFirst = mock(Role.class);

        var roleSecond = mock(Role.class);

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

        listUsers = List.of(userFirst, userSecond, userThird);

        listUsersDTO = List.of(userDTOFirst, userDTOSecond, userDTOThird);
    }

    @Test
    void shouldMapToUser() {
        assertThat(mapper.mapToUser(userDTOFirst)).usingRecursiveComparison().isEqualTo(userFirst);
    }

    @Test
    void shouldMapToUserDTO() {
        assertThat(mapper.mapToUserDTO(userFirst)).usingRecursiveComparison().isEqualTo(userDTOFirst);
    }

    @Test
    void shouldMapToUserDTOs() {
        assertThat(mapper.mapToUserDTOs(listUsers)).usingRecursiveComparison().isEqualTo(listUsersDTO);
    }
}