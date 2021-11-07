package lst.sigv.pt.exception;

import java.util.function.Supplier;

/**
 * Created by Afonseca on 24/10/2021.
 **/
public class FileNotFoundException extends RuntimeException implements Supplier<FileNotFoundException> {
    public FileNotFoundException (String message) {
        super(message);
    }

    @Override
    public FileNotFoundException get() {
        return this;
    }
}
