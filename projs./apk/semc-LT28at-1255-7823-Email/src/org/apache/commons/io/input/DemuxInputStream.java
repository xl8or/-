package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class DemuxInputStream extends InputStream {

   private InheritableThreadLocal m_streams;


   public DemuxInputStream() {
      InheritableThreadLocal var1 = new InheritableThreadLocal();
      this.m_streams = var1;
   }

   private InputStream getStream() {
      return (InputStream)this.m_streams.get();
   }

   public InputStream bindStream(InputStream var1) {
      InputStream var2 = this.getStream();
      this.m_streams.set(var1);
      return var2;
   }

   public void close() throws IOException {
      InputStream var1 = this.getStream();
      if(var1 != null) {
         var1.close();
      }
   }

   public int read() throws IOException {
      InputStream var1 = this.getStream();
      int var2;
      if(var1 != null) {
         var2 = var1.read();
      } else {
         var2 = -1;
      }

      return var2;
   }
}
