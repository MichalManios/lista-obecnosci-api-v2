package pl.gov.listaobecnosci.sections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.gov.listaobecnosci.sections.entity.Section;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.gov.listaobecnosci.TestUtil.persistAll;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ISectionRepositoryTest {

    @Autowired
    ISectionRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    private Section sectionFirst;

    @BeforeEach
    void setUp() {
        sectionFirst = Section.builder()
                .name("Finanse")
                .workers(Collections.emptyList())
                .build();

        persistAll(entityManager, sectionFirst);
    }

    @Test
    void findByName() {
        assertThat(repository.findByName("Finanse")).usingRecursiveComparison().isEqualTo(sectionFirst);
    }

    @Test
    void findByNameShouldReturnNull() {
        assertThat(repository.findByName("Kadry")).usingRecursiveComparison().isEqualTo(null);
    }
}