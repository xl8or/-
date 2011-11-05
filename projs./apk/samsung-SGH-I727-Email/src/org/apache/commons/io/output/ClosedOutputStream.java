package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class ClosedOutputStream extends OutputStream {

   public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();


   public ClosedOutputStream() {}

   public void write(int var1) throws IOException {
      String var2 = "write(" + var1 + ") failed: stream is closed";
      throw new IOException(var2);
   }
}
