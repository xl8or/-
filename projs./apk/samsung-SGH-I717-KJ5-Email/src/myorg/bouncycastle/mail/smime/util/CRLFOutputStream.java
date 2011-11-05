package myorg.bouncycastle.mail.smime.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CRLFOutputStream extends FilterOutputStream {

   protected static byte[] newline = new byte[2];
   protected int lastb = -1;


   static {
      newline[0] = 13;
      newline[1] = 10;
   }

   public CRLFOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      if(var1 == 13) {
         OutputStream var2 = this.out;
         byte[] var3 = newline;
         var2.write(var3);
      } else if(var1 == 10) {
         if(this.lastb != 13) {
            OutputStream var4 = this.out;
            byte[] var5 = newline;
            var4.write(var5);
         }
      } else {
         this.out.write(var1);
      }

      this.lastb = var1;
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = var2;

      while(true) {
         int var5 = var2 + var3;
         if(var4 == var5) {
            return;
         }

         byte var6 = var1[var4];
         this.write(var6);
         ++var4;
      }
   }

   public void writeln() throws IOException {
      OutputStream var1 = super.out;
      byte[] var2 = newline;
      var1.write(var2);
   }
}
