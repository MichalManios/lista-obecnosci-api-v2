package pl.gov.listaobecnosci.sections;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.gov.listaobecnosci.sections.dto.SectionDTO;
import pl.gov.listaobecnosci.sections.dto.SectionWithWorkersDTO;
import pl.gov.listaobecnosci.sections.entity.Section;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISectionMapper {

    ISectionMapper INSTANCE = Mappers.getMapper(ISectionMapper.class);

    @Mapping(target = "workers", ignore = true)
    Section mapToSection(SectionDTO sectionDTO);

    SectionDTO mapToSectionDTO(Section section);

    List<SectionDTO> mapToSectionDTOs(List<Section> sections);

    SectionWithWorkersDTO mapToSectionWithWorkersDTO(Section section);
}
