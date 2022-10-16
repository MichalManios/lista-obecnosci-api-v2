package pl.gov.listaobecnosci.security.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static pl.gov.listaobecnosci.TestUtil.persistAll;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class IRoleRepositoryTest {

    @Autowired
    IRoleRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    private Role roleFirst;

    private User user;

    @BeforeEach
    void setUp() {
        roleFirst = Role.builder()
                .name("ADMINISTRATOR")
                .description("Administrator")
                .user(user)
                .build();

        user = User.builder()
                .name("Michał")
                .password("Haslo")
                .role(roleFirst)
                .build();

        persistAll(entityManager, roleFirst, user);
    }

    @Test
    void findRoleByUser() {
        assertThat(repository.findRoleByUser(user)).usingRecursiveComparison().isEqualTo(roleFirst);
    }

    @Test
    void findRoleByUserShouldReturnNull() {
        var user = mock(User.class);

        assertThat(repository.findRoleByUser(user)).usingRecursiveComparison().isEqualTo(null);
    }
}