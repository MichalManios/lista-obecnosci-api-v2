package pl.gov.listaobecnosci.sections;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gov.listaobecnosci.sections.entity.Section;

interface ISectionRepository extends JpaRepository<Section, Long> {

    Section findByName(String name);
}
