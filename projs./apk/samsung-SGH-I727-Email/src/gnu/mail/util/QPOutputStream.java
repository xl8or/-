package gnu.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QPOutputStream extends FilterOutputStream {

   private static final char[] hex = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private int bytesPerLine;
   private int count;
   private boolean gotCR;
   private boolean gotSpace;


   public QPOutputStream(OutputStream var1) {
      this(var1, 76);
   }

   public QPOutputStream(OutputStream var1, int var2) {
      super(var1);
      this.bytesPerLine = var2;
      this.count = 0;
      this.gotSpace = (boolean)0;
      this.gotCR = (boolean)0;
   }

   private void outputCRLF() throws IOException {
      this.out.write(13);
      this.out.write(10);
      this.count = 0;
   }

   public void close() throws IOException {
      this.out.close();
   }

   public void flush() throws IOException {
      if(this.gotSpace) {
         this.output(32, (boolean)0);
         this.gotSpace = (boolean)0;
      }

      this.out.flush();
   }

   protected void output(int var1, boolean var2) throws IOException {
      if(var2) {
         int var3 = this.count + 3;
         this.count = var3;
         int var4 = this.bytesPerLine;
         if(var3 > var4) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 3;
         }

         this.out.write(61);
         OutputStream var5 = this.out;
         char[] var6 = hex;
         int var7 = var1 >> 4;
         char var8 = var6[var7];
         var5.write(var8);
         OutputStream var9 = this.out;
         char[] var10 = hex;
         int var11 = var1 & 15;
         char var12 = var10[var11];
         var9.write(var12);
      } else {
         int var13 = this.count + 1;
         this.count = var13;
         int var14 = this.bytesPerLine;
         if(var13 > var14) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 1;
         }

         this.out.write(var1);
      }
   }

   public void write(int var1) throws IOException {
      int var2 = var1 & 255;
      if(this.gotSpace) {
         if(var2 != 10 && var2 != 13) {
            this.output(32, (boolean)0);
         } else {
            this.output(32, (boolean)1);
         }

         this.gotSpace = (boolean)0;
      }

      if(var2 == 32) {
         this.gotSpace = (boolean)1;
      } else if(var2 == 13) {
         this.gotCR = (boolean)1;
         this.outputCRLF();
      } else if(var2 == 10) {
         if(this.gotCR) {
            this.gotCR = (boolean)0;
         } else {
            this.outputCRLF();
         }
      } else if(var2 >= 32 && var2 < 127 && var2 != 61) {
         this.output(var2, (boolean)0);
      } else {
         this.output(var2, (boolean)1);
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = var2; var4 < var3; ++var4) {
         byte var5 = var1[var4];
         this.write(var5);
      }

   }
}
