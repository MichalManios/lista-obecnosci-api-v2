package pl.gov.listaobecnosci.security.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.gov.listaobecnosci.security.role.dto.RoleDTO;
import pl.gov.listaobecnosci.security.role.entity.Role;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    IRoleMapper INSTANCE = Mappers.getMapper(IRoleMapper.class);

    @Mapping(target = "user", ignore = true)
    Role mapToRole(RoleDTO RoleDTO);

    RoleDTO mapToRoleDTO(Role Role);

    List<RoleDTO> mapToRoleDTOs(List<Role> Roles);
}
