package pl.gov.listaobecnosci.security.users;

import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

interface IUserService {

    List<User> getAllUsers();

    User addNewUser(User user);

    User updateUser(User user);

    User deleteUser(User user);
}
