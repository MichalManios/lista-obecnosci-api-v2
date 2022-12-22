package pl.gov.listaobecnosci.sections;

import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gov.listaobecnosci.sections.entity.Section;

import java.util.List;

@Service
@RequiredArgsConstructor
class SectionService implements ISectionService{

    private final ISectionRepository repository;

    public List<Section> getAllSections() {
        return repository.findAll();
    }

    public Section getSectionByName(String name) {
        return repository.findByName(name);
    }

    public Section addNewSection(Section section) {
        return repository.save(section);
    }

    public Section updateSection(Section section) {
        var sectionToUpdate = getSectionById(section.getId());
        sectionToUpdate.setName(section.getName());
        return repository.save(sectionToUpdate);
    }

    public Section deleteSection(Long sectionId) {
        var section = getSectionById(sectionId);
        repository.delete(section);
        return section;
    }

    private Section getSectionById(Long sectionId) {
        return repository.findById(sectionId)
                .orElseThrow(() -> new IncorrectDataException("Nie ma takiego działu!!!"));
    }
}
