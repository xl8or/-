package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface DataSource {

   String getContentType();

   InputStream getInputStream() throws IOException;

   String getName();

   OutputStream getOutputStream() throws IOException;
}
