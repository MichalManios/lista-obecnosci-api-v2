package pl.gov.listaobecnosci.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.security.role.entity.Role;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService implements IRoleService{

    private final IRoleRepository repository;

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public Role getRoleByUser(User user) {
        return repository.findRoleByUser(user);
    }

    public Role addNewRole(Role role) {
        return repository.save(role);
    }

    public Role updateRole(Role role) {
        var roleToUpdate = getRoleById(role.getId());
        roleToUpdate.setName(role.getName());
        roleToUpdate.setDescription(role.getDescription());
        return repository.save(roleToUpdate);
    }

    public Role deleteRole(Role role) {
        repository.delete(role);
        return role;
    }

    private Role getRoleById(Long roleId) {
        return repository.findById(roleId)
                .orElseThrow(() -> new IncorrectDataException("Nie ma takiej roli!!!"));
    }
}
