package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class ProxyWriter extends FilterWriter {

   public ProxyWriter(Writer var1) {
      super(var1);
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void write(int var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1) throws IOException {
      this.out.write(var1);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }

   public void write(char[] var1) throws IOException {
      this.out.write(var1);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
   }
}
