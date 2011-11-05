package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class DemuxOutputStream extends OutputStream {

   private InheritableThreadLocal m_streams;


   public DemuxOutputStream() {
      InheritableThreadLocal var1 = new InheritableThreadLocal();
      this.m_streams = var1;
   }

   private OutputStream getStream() {
      return (OutputStream)this.m_streams.get();
   }

   public OutputStream bindStream(OutputStream var1) {
      OutputStream var2 = this.getStream();
      this.m_streams.set(var1);
      return var2;
   }

   public void close() throws IOException {
      OutputStream var1 = this.getStream();
      if(var1 != null) {
         var1.close();
      }
   }

   public void flush() throws IOException {
      OutputStream var1 = this.getStream();
      if(var1 != null) {
         var1.flush();
      }
   }

   public void write(int var1) throws IOException {
      OutputStream var2 = this.getStream();
      if(var2 != null) {
         var2.write(var1);
      }
   }
}
