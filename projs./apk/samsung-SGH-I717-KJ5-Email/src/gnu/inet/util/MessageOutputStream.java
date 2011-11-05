package gnu.inet.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MessageOutputStream extends FilterOutputStream {

   public static final int END = 46;
   public static final int LF = 10;
   int[] last;


   public MessageOutputStream(OutputStream var1) {
      super(var1);
      int[] var2 = new int[]{10, 10};
      this.last = var2;
   }

   public void write(int var1) throws IOException {
      if(this.last[0] == 10 && this.last[1] == 46 && var1 == 10) {
         this.out.write(46);
      }

      this.out.write(var1);
      int[] var2 = this.last;
      int var3 = this.last[1];
      var2[0] = var3;
      this.last[1] = var1;
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = 0;
      int var5 = var3;

      byte[] var6;
      for(var6 = var1; var4 < var5; ++var4) {
         int var7 = var2 + var4;
         byte var8 = var6[var7];
         if(this.last[0] == 10 && this.last[1] == 46 && var8 == 10) {
            byte[] var9 = new byte[var6.length + 1];
            System.arraycopy(var6, var2, var9, var2, var4);
            int var10 = var2 + var4;
            var9[var10] = 46;
            int var11 = var2 + var4;
            int var12 = var2 + var4 + 1;
            int var13 = var5 - var4;
            System.arraycopy(var6, var11, var9, var12, var13);
            ++var4;
            ++var5;
            var6 = var9;
         }

         int[] var14 = this.last;
         int var15 = this.last[1];
         var14[0] = var15;
         this.last[1] = var8;
      }

      this.out.write(var6, var2, var5);
   }
}
