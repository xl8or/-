package gnu.mail.util;

import gnu.inet.util.LineInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UUInputStream extends FilterInputStream {

   boolean body;
   ByteArrayOutputStream pending;


   public UUInputStream(InputStream var1) {
      LineInputStream var2 = new LineInputStream(var1);
      super(var2);
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.pending = var3;
   }

   static int decode(byte var0) {
      int var1;
      if(var0 < 0) {
         var1 = var0 + 256;
      } else {
         var1 = var0;
      }

      return var1 - 32 & 63;
   }

   public int read() throws IOException {
      byte[] var1 = new byte[1];

      int var2;
      do {
         var2 = this.read(var1, 0, 1);
      } while(var2 == 0);

      int var3;
      if(var2 == -1) {
         var3 = var2;
      } else {
         var3 = var1[0];
      }

      return var3;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var10;
      if(this.pending.size() == 0) {
         LineInputStream var4 = (LineInputStream)this.in;
         if(!this.body) {
            String var5 = var4.readLine();
            if(var5 == null || !var5.startsWith("begin ")) {
               throw new IOException("No `begin\' line");
            }

            this.body = (boolean)1;
         }

         String var6;
         do {
            var6 = var4.readLine();
         } while("".equals(var6));

         if(var6 == null) {
            throw new EOFException();
         }

         byte[] var7 = var6.getBytes("US-ASCII");
         int var8 = decode(var7[0]);
         if(var8 <= 0) {
            this.body = (boolean)0;
            String var9 = var4.readLine();
            if(var9 == null || !var9.equals("end")) {
               throw new IOException("No `end\' line");
            }

            var10 = -1;
            return var10;
         }

         int var11 = 0 + 1;
         int var13 = var11;

         for(int var14 = var8; var14 > 0; var14 += -3) {
            if(var14 >= 1) {
               int var15 = decode(var7[var13]) << 2;
               int var16 = var13 + 1;
               int var17 = decode(var7[var16]) >> 4;
               int var18 = var15 | var17;
               this.pending.write(var18);
            }

            if(var14 >= 2) {
               int var19 = var13 + 1;
               int var20 = decode(var7[var19]) << 4;
               int var21 = var13 + 2;
               int var22 = decode(var7[var21]) >> 2;
               int var23 = var20 | var22;
               this.pending.write(var23);
            }

            if(var14 >= 3) {
               int var24 = var13 + 2;
               int var25 = decode(var7[var24]) << 6;
               int var26 = var13 + 3;
               int var27 = decode(var7[var26]);
               int var28 = var25 | var27;
               this.pending.write(var28);
            }

            var13 += 4;
         }
      }

      byte[] var29 = this.pending.toByteArray();
      int var32 = var29.length;
      this.pending.reset();
      if(var32 > var3) {
         System.arraycopy(var29, 0, var1, 0, var3);
         byte[] var30 = new byte[var32 - var3];
         int var31 = var30.length;
         System.arraycopy(var29, var3, var30, 0, var31);
         this.pending.write(var30);
         var10 = var3;
      } else {
         System.arraycopy(var29, 0, var1, 0, var32);
         var10 = var32;
      }

      return var10;
   }
}
