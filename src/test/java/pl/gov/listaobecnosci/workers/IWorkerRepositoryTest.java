package pl.gov.listaobecnosci.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.gov.listaobecnosci.functions.Function;
import pl.gov.listaobecnosci.sections.entity.Section;
import pl.gov.listaobecnosci.workers.entity.Worker;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.gov.listaobecnosci.TestUtil.persistAll;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class IWorkerRepositoryTest {

    @Autowired
    IWorkerRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    private Worker worker;

    @BeforeEach
    void setUp() {
        Section section = Section.builder()
                .name("Finanse")
                .build();

        worker = Worker.builder()
                .name("Michał")
                .surname("Mikołajczyk")
                .section(section)
                .function(Function.WORKER)
                .build();

        persistAll(entityManager, section, worker);
    }

    @Test
    void findAllBySectionName() {
        assertThat(repository.findAllBySectionName("Finanse")).usingRecursiveComparison().isEqualTo(List.of(worker));
    }

    @Test
    void findAllBySectionNameShouldReturnEmptyList() {
        assertThat(repository.findAllBySectionName("Kadry"))
                .usingRecursiveComparison()
                .isEqualTo(Collections.EMPTY_LIST);
    }
}