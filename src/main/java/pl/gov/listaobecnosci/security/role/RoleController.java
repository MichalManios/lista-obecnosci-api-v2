package pl.gov.listaobecnosci.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import pl.gov.listaobecnosci.security.role.dto.RoleDTO;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService service;

    private final IRoleMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        var roleDTOs = mapper.mapToRoleDTOs(service.getAllRoles());
        return new ResponseEntity<>(roleDTOs, HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<RoleDTO> getRoleByUser(@RequestBody User user) {
        return new ResponseEntity<>(mapper.mapToRoleDTO(service.getRoleByUser(user)),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO) {
        var newRole = mapper.mapToRole(roleDTO);
        var newRoleDTO = mapper.mapToRoleDTO(service.addNewRole(newRole));
        return new ResponseEntity<>(newRoleDTO, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) {
        var roleToUpdate = mapper.mapToRole(roleDTO);
        var updatedRoleDTO = mapper.mapToRoleDTO(service.updateRole(roleToUpdate));
        return new ResponseEntity<>(updatedRoleDTO, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<RoleDTO> deleteRole(@RequestBody RoleDTO roleDTO) {
        service.deleteRole(mapper.mapToRole(roleDTO));
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }
}
