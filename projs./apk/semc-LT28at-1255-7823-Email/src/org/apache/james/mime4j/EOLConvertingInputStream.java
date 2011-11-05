package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class EOLConvertingInputStream extends InputStream {

   public static final int CONVERT_BOTH = 3;
   public static final int CONVERT_CR = 1;
   public static final int CONVERT_LF = 2;
   private int flags;
   private PushbackInputStream in;
   private int previous;


   public EOLConvertingInputStream(InputStream var1) {
      this(var1, 3);
   }

   public EOLConvertingInputStream(InputStream var1, int var2) {
      this.in = null;
      this.previous = 0;
      this.flags = 3;
      PushbackInputStream var3 = new PushbackInputStream(var1, 2);
      this.in = var3;
      this.flags = var2;
   }

   public void close() throws IOException {
      this.in.close();
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      int var2;
      if(var1 == -1) {
         var2 = -1;
      } else {
         if((this.flags & 1) != 0 && var1 == 13) {
            int var3 = this.in.read();
            if(var3 != -1) {
               this.in.unread(var3);
            }

            if(var3 != 10) {
               this.in.unread(10);
            }
         } else if((this.flags & 2) != 0 && var1 == 10 && this.previous != 13) {
            var1 = 13;
            this.in.unread(10);
         }

         this.previous = var1;
         var2 = var1;
      }

      return var2;
   }
}
