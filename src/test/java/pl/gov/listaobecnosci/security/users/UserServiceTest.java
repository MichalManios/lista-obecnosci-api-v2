package pl.gov.listaobecnosci.security.users;

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
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    IUserRepository repository;

    @Test
    void shouldReturnAllUsers() {
        var userFirst = mock(User.class);
        var userSecond = mock(User.class);
        var userThird = mock(User.class);

        when(repository.findAll()).thenReturn(List.of(userFirst, userSecond, userThird));

        assertThat(service.getAllUsers())
                .usingRecursiveComparison()
                .isEqualTo(List.of(userFirst, userSecond, userThird));
    }

    @Test
    void shouldAddNewUser() {
        var user = mock(User.class);

        when(repository.save(user)).thenReturn(user);

        assertThat(service.addNewUser(user)).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void shouldUpdateUser() {
        var role = mock(Role.class);

        var userToUpdate = User.builder()
                .id(1L)
                .name("Michał")
                .password("Haslo1")
                .role(role)
                .build();

        var userUpdated = User.builder()
                .id(1L)
                .name("Mariusz")
                .password("Haslo11")
                .role(role)
                .build();

        when(repository.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        when(repository.save(userUpdated)).thenReturn(userUpdated);

        assertThat(service.updateUser(userToUpdate)).usingRecursiveComparison().isEqualTo(userUpdated);
    }

    @Test
    void shouldThrowExceptionWhenTryUpdateUserWhichDoesNotExist() {
        var userToUpdate = mock(User.class);

        when(repository.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IncorrectDataException.class)
                .isThrownBy(() ->  service.updateUser(userToUpdate))
                .withMessage("Nie ma takiego użytkownika!!!");
    }

    @Test
    void shouldDeleteUser() {
        var user = mock(User.class);

        service.deleteUser(user);

        verify(repository, times(1)).delete(user);
        assertThat(service.deleteUser(user)).usingRecursiveComparison().isEqualTo(user);
    }
}