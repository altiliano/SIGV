package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Afonseca on 19/06/2021.
 **/
@Setter
@Getter
public class RestPageResult<T> extends PageImpl<T> {


    public RestPageResult(List<T> content) {
        super(content);
    }
}
