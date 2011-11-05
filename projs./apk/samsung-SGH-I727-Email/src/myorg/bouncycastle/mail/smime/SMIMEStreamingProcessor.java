package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;

public interface SMIMEStreamingProcessor {

   void write(OutputStream var1) throws IOException;
}
