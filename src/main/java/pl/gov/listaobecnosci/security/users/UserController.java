package pl.gov.listaobecnosci.security.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gov.listaobecnosci.security.users.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    private final IUserMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        var userDTOs = mapper.mapToUserDTOs(service.getAllUsers());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        var user = mapper.mapToUser(userDTO);
        var newUserDTO = mapper.mapToUserDTO(service.addNewUser(user));
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        var userToUpdate = mapper.mapToUser(userDTO);
        var updatedUserDTO = mapper.mapToUserDTO(service.updateUser(userToUpdate));
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserDTO> deleteUser(@RequestBody UserDTO userDTO) {
        service.deleteUser(mapper.mapToUser(userDTO));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
