package gnu.inet.util;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CRLFInputStream extends FilterInputStream {

   public static final int CR = 13;
   public static final int LF = 10;
   private boolean doReset;


   public CRLFInputStream(InputStream var1) {
      Object var2;
      if(var1.markSupported()) {
         var2 = var1;
      } else {
         var2 = new BufferedInputStream(var1);
      }

      super((InputStream)var2);
   }

   private int indexOfCRLF(byte[] var1, int var2, int var3) throws IOException {
      this.doReset = (boolean)0;
      int var4 = var3 - 1;
      int var5 = var2;

      int var7;
      while(true) {
         if(var5 >= var3) {
            var7 = -1;
            break;
         }

         if(var1[var5] == 13) {
            int var6;
            if(var5 == var4) {
               var6 = this.in.read();
               this.doReset = (boolean)1;
            } else {
               int var8 = var5 + 1;
               var6 = var1[var8];
            }

            if(var6 == 10) {
               this.doReset = (boolean)1;
               var7 = var5;
               break;
            }
         }

         ++var5;
      }

      return var7;
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if(var1 == 13) {
         this.in.mark(1);
         int var2 = this.in.read();
         if(var2 == 10) {
            var1 = var2;
         } else {
            this.in.reset();
         }
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      InputStream var4 = this.in;
      int var5 = var3 + 1;
      var4.mark(var5);
      int var6 = this.in.read(var1, var2, var3);
      if(var6 > 0) {
         int var7 = this.indexOfCRLF(var1, var2, var6);
         if(this.doReset) {
            this.in.reset();
            if(var7 != -1) {
               InputStream var8 = this.in;
               int var9 = var7 + 1;
               var6 = var8.read(var1, var2, var9);
               int var10 = this.in.read();
               var1[var7] = 10;
            } else {
               var6 = this.in.read(var1, var2, var3);
            }
         }
      }

      return var6;
   }
}
