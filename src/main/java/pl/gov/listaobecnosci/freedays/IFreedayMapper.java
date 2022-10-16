package pl.gov.listaobecnosci.freedays;

import pl.gov.listaobecnosci.freedays.dto.FreedayDTO;
import pl.gov.listaobecnosci.freedays.entity.Freeday;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IFreedayMapper {

    IFreedayMapper INSTANCE = Mappers.getMapper(IFreedayMapper.class);

    Freeday mapToFreeday(FreedayDTO freedayDTO);

    FreedayDTO mapToFreedayDTO(Freeday freeday);

    List<FreedayDTO> mapToFreedayDTOs(List<Freeday> freedays);
}
