package gnu.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RFC2822OutputStream extends FilterOutputStream {

   public static final int CR = 13;
   public static final int LF = 10;
   protected int count = 0;


   public RFC2822OutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      if(var1 != 13 && var1 != 10) {
         if(this.count > 998) {
            this.out.write(10);
            this.count = 0;
         }

         this.out.write(var1);
         int var2 = this.count + 1;
         this.count = var2;
      } else {
         this.out.write(var1);
         this.count = 0;
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = var3 + var2;
      int var5 = var2;

      int var6;
      for(var6 = var2; var5 < var4; ++var5) {
         int var7 = this.count + 1;
         this.count = var7;
         if(var1[var5] != 13 && var1[var5] != 10) {
            if(this.count > 998) {
               OutputStream var10 = this.out;
               int var11 = this.count;
               var10.write(var1, var6, var11);
               this.out.write(10);
               var6 = var5 + 1;
               this.count = 0;
            }
         } else {
            OutputStream var8 = this.out;
            int var9 = var5 + 1 - var6;
            var8.write(var1, var6, var9);
            var6 = var5 + 1;
            this.count = 0;
         }
      }

      int var12 = var5 - var6;
      if(var12 > 0) {
         this.out.write(var1, var6, var12);
      }
   }
}
