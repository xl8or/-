package gnu.inet.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class CRLFOutputStream extends FilterOutputStream {

   public static final int CR = 13;
   public static final byte[] CRLF = new byte[]{(byte)13, (byte)10};
   public static final int LF = 10;
   static final String US_ASCII = "US-ASCII";
   protected int last = -1;


   public CRLFOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      if(var1 == 13) {
         OutputStream var2 = this.out;
         byte[] var3 = CRLF;
         var2.write(var3);
      } else if(var1 == 10) {
         if(this.last != 13) {
            OutputStream var4 = this.out;
            byte[] var5 = CRLF;
            var4.write(var5);
         }
      } else {
         this.out.write(var1);
      }

      this.last = var1;
   }

   public void write(String var1) throws IOException {
      try {
         byte[] var2 = var1.getBytes("US-ASCII");
         int var3 = var2.length;
         this.write(var2, 0, var3);
      } catch (UnsupportedEncodingException var5) {
         throw new IOException("The US-ASCII encoding is not supported on this system");
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
         switch(var1[var5]) {
         case 10:
            if(this.last != 13) {
               OutputStream var12 = this.out;
               int var13 = var5 - var6;
               var12.write(var1, var6, var13);
               OutputStream var14 = this.out;
               byte[] var15 = CRLF;
               var14.write(var15, 0, 2);
            }

            var6 = var5 + 1;
         case 11:
         case 12:
         default:
            break;
         case 13:
            OutputStream var8 = this.out;
            int var9 = var5 - var6;
            var8.write(var1, var6, var9);
            OutputStream var10 = this.out;
            byte[] var11 = CRLF;
            var10.write(var11, 0, 2);
            var6 = var5 + 1;
         }

         byte var7 = var1[var5];
         this.last = var7;
      }

      if(var4 - var6 > 0) {
         OutputStream var16 = this.out;
         int var17 = var4 - var6;
         var16.write(var1, var6, var17);
      }
   }

   public void writeln() throws IOException {
      OutputStream var1 = this.out;
      byte[] var2 = CRLF;
      var1.write(var2, 0, 2);
   }
}
