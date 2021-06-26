package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

/**
 * Created by Afonseca on 19/06/2021.
 **/
@Getter
@Setter
public class RestPageRequest  {
   private  int pageNumber;
   private  int pageSize = 10;

   public RestPageRequest(int pageNumber, int pageSize) {
      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
   }
}
