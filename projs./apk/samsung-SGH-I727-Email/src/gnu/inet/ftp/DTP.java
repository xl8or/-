package gnu.inet.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface DTP {

   boolean abort();

   void complete();

   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   void setTransferMode(int var1);

   void transferComplete();
}
