package pl.gov.listaobecnosci.security.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService service;

    @Mock
    IRoleRepository repository;

    @Test
    void shouldReturnListOfAllRoles() {
        var roleFirst = mock(Role.class);
        var roleSecond = mock(Role.class);
        var roleThird = mock(Role.class);

        when(repository.findAll()).thenReturn(List.of(roleFirst, roleSecond, roleThird));

        assertThat(service.getAllRoles())
                .usingRecursiveComparison()
                .isEqualTo(List.of(roleFirst, roleSecond, roleThird));
    }

    @Test
    void shouldReturnRoleSearchedByUser() {
        var user = mock(User.class);
        var role = mock(Role.class);

        when(repository.findRoleByUser(user)).thenReturn(role);

        assertThat(service.getRoleByUser(user)).usingRecursiveComparison().isEqualTo(role);
    }

    @Test
    void shouldAddNewRole() {
        var roleToAdd = mock(Role.class);
        var role = mock(Role.class);

        when(repository.save(roleToAdd)).thenReturn(role);

        assertThat(service.addNewRole(roleToAdd)).usingRecursiveComparison().isEqualTo(role);
    }

    @Test
    void shouldUpdateRole() {
        var roleToUpdate = Role.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(null)
                .build();

        var roleUpdated = Role.builder()
                .id(1L)
                .name("USER")
                .description("Użytkownik")
                .user(null)
                .build();

        when(repository.findById(roleToUpdate.getId())).thenReturn(Optional.of(roleToUpdate));
        when(repository.save(roleUpdated)).thenReturn(roleUpdated);

        assertThat(service.updateRole(roleToUpdate)).usingRecursiveComparison().isEqualTo(roleUpdated);
    }

    @Test
    void shouldThrowExceptionWhenTryUpdateRoleWhichDoesNotExist() {
        var roleToUpdate = mock(Role.class);

        when(repository.findById(roleToUpdate.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IncorrectDataException.class)
                .isThrownBy(() ->  service.updateRole(roleToUpdate))
                .withMessage("Nie ma takiej roli!!!");
    }

    @Test
    void shouldDeleteRole() {
        var role = mock(Role.class);

        service.deleteRole(role);

        verify(repository, times(1)).delete(role);
        assertThat(service.deleteRole(role)).usingRecursiveComparison().isEqualTo(role);
    }
}