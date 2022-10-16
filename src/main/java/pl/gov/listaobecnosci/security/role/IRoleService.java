package pl.gov.listaobecnosci.security.role;

import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

public interface IRoleService {

    List<Role> getAllRoles();

    Role getRoleByUser(User user);

    Role addNewRole(Role section);

    Role updateRole(Role section);

    Role deleteRole (Role role);
}
