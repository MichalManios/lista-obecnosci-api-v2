package pl.gov.listaobecnosci.security.role;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByUser(User user);
}
