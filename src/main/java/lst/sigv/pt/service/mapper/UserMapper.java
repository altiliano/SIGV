package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestUser;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 15/11/20
 */
@Mapper( componentModel = "spring")
public interface UserMapper {
    UserEntity restUserToUserEntity(RestUser user);
    RestUser userEntityToRestUser(UserEntity userEntity);
}
