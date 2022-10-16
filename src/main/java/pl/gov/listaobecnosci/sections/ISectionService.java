package pl.gov.listaobecnosci.sections;

import pl.gov.listaobecnosci.sections.entity.Section;

import java.util.List;

interface ISectionService {

    List<Section> getAllSections();

    Section getSectionByName(String name);

    Section addNewSection(Section section);

    Section updateSection(Section section);

    Section deleteSection(Section section);
}
