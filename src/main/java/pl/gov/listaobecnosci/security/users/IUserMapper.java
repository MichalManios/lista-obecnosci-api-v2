package pl.gov.listaobecnosci.security.users;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.gov.listaobecnosci.security.users.dto.UserDTO;
import pl.gov.listaobecnosci.security.users.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User mapToUser(UserDTO userDTO);

    UserDTO mapToUserDTO(User user);

    List<UserDTO> mapToUserDTOs(List<User> users);
}
