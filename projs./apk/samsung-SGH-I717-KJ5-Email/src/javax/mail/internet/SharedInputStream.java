package javax.mail.internet;

import java.io.InputStream;

public interface SharedInputStream {

   long getPosition();

   InputStream newStream(long var1, long var3);
}
