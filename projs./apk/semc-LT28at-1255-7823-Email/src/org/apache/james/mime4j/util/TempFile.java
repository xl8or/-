package org.apache.james.mime4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface TempFile {

   void delete();

   String getAbsolutePath();

   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   boolean isInMemory();

   long length();
}
