package pl.gov.listaobecnosci.security.users;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.listaobecnosci.security.users.entity.User;

interface IUserRepository extends JpaRepository<User, Long> {
}
