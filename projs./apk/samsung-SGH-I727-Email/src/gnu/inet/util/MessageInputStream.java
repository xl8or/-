package gnu.inet.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MessageInputStream extends FilterInputStream {

   public static final int END = 46;
   public static final int LF = 10;
   protected int buf1 = -1;
   protected int buf2 = -1;
   protected boolean eof = 0;
   protected int markBuf1;
   protected int markBuf2;


   public MessageInputStream(InputStream var1) {
      super(var1);
   }

   public void mark(int var1) {
      this.in.mark(var1);
      int var2 = this.buf1;
      this.markBuf1 = var2;
      int var3 = this.buf2;
      this.markBuf2 = var3;
   }

   public boolean markSupported() {
      return this.in.markSupported();
   }

   public int read() throws IOException {
      int var1;
      if(this.eof) {
         var1 = -1;
      } else {
         if(this.buf1 != -1) {
            var1 = this.buf1;
            int var2 = this.buf2;
            this.buf1 = var2;
            this.buf2 = -1;
         } else {
            var1 = super.read();
         }

         if(var1 == 10) {
            if(this.buf1 == -1) {
               int var3 = super.read();
               this.buf1 = var3;
               if(this.buf1 == 46) {
                  int var4 = super.read();
                  this.buf2 = var4;
                  if(this.buf2 == 10) {
                     this.eof = (boolean)1;
                  }
               }
            } else if(this.buf1 == 46) {
               if(this.buf2 == -1) {
                  int var5 = super.read();
                  this.buf2 = var5;
                  if(this.buf2 == 10) {
                     this.eof = (boolean)1;
                  }
               } else if(this.buf2 == 10) {
                  this.eof = (boolean)1;
               }
            }
         }
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.eof) {
         var4 = -1;
      } else {
         int var5 = var2 + var3;
         int var6 = var2;

         while(true) {
            if(var6 >= var5) {
               var4 = var3;
               break;
            }

            int var7 = this.read();
            if(var7 == -1) {
               var4 = var6 - var2;
               break;
            }

            byte var8 = (byte)var7;
            var1[var6] = var8;
            ++var6;
         }
      }

      return var4;
   }

   public void reset() throws IOException {
      this.in.reset();
      int var1 = this.markBuf1;
      this.buf1 = var1;
      int var2 = this.markBuf2;
      this.buf2 = var2;
   }
}
