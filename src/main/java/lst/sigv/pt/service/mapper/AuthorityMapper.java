package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.AuthorityEntity;
import lst.sigv.pt.model.api.RestAuthority;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 22/01/21
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    RestAuthority authorityEntityToRestAuthority(AuthorityEntity authorityEntity);
}
