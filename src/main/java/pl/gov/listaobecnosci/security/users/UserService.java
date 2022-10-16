package pl.gov.listaobecnosci.security.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class UserService implements IUserService {

    private final IUserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User addNewUser(User user) {
        return repository.save(user);
    }

    public User updateUser(User user) {
        var userToUpdate = getUserById(user.getId());
        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRole(user.getRole());
        return repository.save(userToUpdate);
    }

    public User deleteUser(User user) {
        repository.delete(user);
        return user;
    }

    private User getUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new IncorrectDataException("Nie ma takiego użytkownika!!!"));
    }
}
