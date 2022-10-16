package pl.gov.listaobecnosci.workers;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

interface IWorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findAllBySectionName(String sectionName);
}
