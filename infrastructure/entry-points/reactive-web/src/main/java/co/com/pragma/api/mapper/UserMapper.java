package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CreateUserDTO;
import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toUser(CreateUserDTO dto);

}
