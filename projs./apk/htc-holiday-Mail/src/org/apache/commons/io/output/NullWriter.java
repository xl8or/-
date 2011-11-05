package org.apache.commons.io.output;

import java.io.Writer;

public class NullWriter extends Writer {

   public static final NullWriter NULL_WRITER = new NullWriter();


   public NullWriter() {}

   public void close() {}

   public void flush() {}

   public void write(int var1) {}

   public void write(String var1) {}

   public void write(String var1, int var2, int var3) {}

   public void write(char[] var1) {}

   public void write(char[] var1, int var2, int var3) {}
}
