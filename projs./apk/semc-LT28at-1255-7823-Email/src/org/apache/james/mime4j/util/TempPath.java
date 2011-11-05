package org.apache.james.mime4j.util;

import java.io.IOException;
import org.apache.james.mime4j.util.TempFile;

public interface TempPath {

   TempFile createTempFile() throws IOException;

   TempFile createTempFile(String var1, String var2) throws IOException;

   TempFile createTempFile(String var1, String var2, boolean var3) throws IOException;

   TempPath createTempPath() throws IOException;

   TempPath createTempPath(String var1) throws IOException;

   void delete();

   String getAbsolutePath();
}
