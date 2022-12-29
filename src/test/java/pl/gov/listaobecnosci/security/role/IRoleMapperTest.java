package pl.gov.listaobecnosci.security.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.gov.listaobecnosci.security.role.dto.RoleDTO;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class IRoleMapperTest {

    private IRoleMapper mapper;

    private Role roleFirst;

    private RoleDTO roleDTOFirst;

    private List<Role> listRole;

    private List<RoleDTO> listRoleDTO;

    @BeforeEach
    void setUp() {
        mapper = new IRoleMapperImpl();

        var userFirst = mock(User.class);

        var userSecond = mock(User.class);

        roleFirst = Role.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(null)
                .build();

        Role roleSecond = Role.builder()
                .id(2L)
                .name("USER")
                .description("Użytkownik")
                .user(userSecond)
                .build();

        var roleThird = Role.builder()
                .id(3L)
                .name("GUEST")
                .description("Gość")
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
                .description("Użytkownik")
                .build();

        var roleDTOThird = RoleDTO.builder()
                .id(3L)
                .name("GUEST")
                .description("Gość")
                .build();

        listRole = List.of(roleFirst, roleSecond, roleThird);
        listRoleDTO = List.of(roleDTOFirst, roleDTOSecond, roleDTOThird);
    }

    @Test
    void shouldMapToRole() {
        assertThat(mapper.mapToRole(roleDTOFirst)).usingRecursiveComparison().isEqualTo(roleFirst);
    }

    @Test
    void shouldMapToRoleDTO() {
        assertThat(mapper.mapToRoleDTO(roleFirst)).usingRecursiveComparison().isEqualTo(roleDTOFirst);
    }

    @Test
    void shouldMapToRoleDTOs() {
        assertThat(mapper.mapToRoleDTOs(listRole)).usingRecursiveComparison().isEqualTo(listRoleDTO);
    }
}