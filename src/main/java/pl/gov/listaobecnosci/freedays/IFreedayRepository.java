package pl.gov.listaobecnosci.freedays;

import pl.gov.listaobecnosci.freedays.entity.Freeday;
import org.springframework.data.jpa.repository.JpaRepository;

interface IFreedayRepository extends JpaRepository<Freeday, Long> {
}
