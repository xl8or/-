package gnu.inet.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.security.sasl.SaslClient;

public class SaslInputStream extends FilterInputStream {

   private byte[] buf;
   private int pos;
   private final SaslClient sasl;


   public SaslInputStream(SaslClient var1, InputStream var2) {
      super(var2);
      this.sasl = var1;
   }

   public int read() throws IOException {
      int var4;
      if(this.buf != null) {
         byte[] var1 = this.buf;
         int var2 = this.pos;
         int var3 = var2 + 1;
         this.pos = var3;
         var4 = var1[var2];
         int var5 = this.pos;
         int var6 = this.buf.length;
         if(var5 == var6) {
            this.buf = null;
         }
      } else {
         var4 = super.read();
         if(var4 != -1) {
            byte[] var7 = new byte[1];
            byte[] var8 = this.sasl.unwrap(var7, 0, 1);
            byte var9 = var8[0];
            if(var8.length > 1) {
               int var10 = var8.length - 1;
               byte[] var11 = new byte[var10];
               this.buf = var11;
               byte[] var12 = this.buf;
               System.arraycopy(var8, 1, var12, 0, var10);
               this.pos = 0;
            }

            var4 = var9;
         }
      }

      return var4;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.buf != null) {
         var4 = this.buf.length;
         int var5 = this.pos;
         if(var4 - var5 <= var3) {
            byte[] var6 = this.buf;
            int var7 = this.pos;
            System.arraycopy(var6, var7, var1, var2, var4);
            this.buf = null;
         } else {
            byte[] var8 = this.buf;
            int var9 = this.pos;
            System.arraycopy(var8, var9, var1, var2, var3);
            int var10 = this.pos + var3;
            this.pos = var10;
            var4 = var3;
         }
      } else {
         var4 = super.read(var1, var2, var3);
         if(var4 != -1) {
            byte[] var11 = this.sasl.unwrap(var1, var2, var4);
            int var12 = var11.length;
            if(var12 > var3) {
               int var13 = var12 - var3;
               byte[] var14 = new byte[var13];
               this.buf = var14;
               System.arraycopy(var11, 0, var1, var2, var3);
               byte[] var15 = this.buf;
               System.arraycopy(var11, var3, var15, 0, var13);
               this.pos = 0;
               var4 = var3;
            } else {
               System.arraycopy(var11, 0, var1, var2, var12);

               int var17;
               for(int var18 = var12; var18 < var4; var17 = var18 + 1) {
                  int var16 = var2 + var12;
                  var1[var16] = 0;
               }

               var4 = var12;
            }
         }
      }

      return var4;
   }
}
