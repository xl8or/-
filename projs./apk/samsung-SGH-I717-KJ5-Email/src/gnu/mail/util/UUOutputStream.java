package gnu.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UUOutputStream extends FilterOutputStream {

   static final int MAX_LINE_LENGTH = 45;
   static final byte[] TABLE = new byte[]{(byte)96, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)62, (byte)63, (byte)64, (byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)91, (byte)92, (byte)93, (byte)94, (byte)95};
   boolean beginLineDone;
   String filename;
   byte[] line;
   int mode;


   public UUOutputStream(OutputStream var1) {
      this(var1, (String)null, 384);
   }

   public UUOutputStream(OutputStream var1, String var2) {
      this(var1, var2, 384);
   }

   public UUOutputStream(OutputStream var1, String var2, int var3) {
      super(var1);
      String var4;
      if(var2 == null) {
         var4 = "file";
      } else {
         var4 = var2;
      }

      this.filename = var4;
      this.mode = var3;
      byte[] var5 = new byte[0];
      this.line = var5;
      this.beginLineDone = (boolean)0;
   }

   static byte encode(byte var0) {
      int var1;
      if(var0 < 0) {
         var1 = var0 + 256;
      } else {
         var1 = var0;
      }

      byte[] var2 = TABLE;
      int var3 = var1 & 63;
      return var2[var3];
   }

   static byte encode(int var0) {
      return encode((byte)var0);
   }

   public void close() throws IOException {
      byte[] var1 = this.line;
      int var2 = this.line.length;
      this.flush(var1, 0, var2);
      OutputStream var3 = this.out;
      byte var4 = encode((int)0);
      var3.write(var4);
      this.out.write(10);
      OutputStream var5 = this.out;
      byte[] var6 = new byte[]{(byte)101, (byte)110, (byte)100, (byte)10};
      var5.write(var6);
      this.out.close();
   }

   void flush(byte[] var1, int var2, int var3) throws IOException {
      if(!this.beginLineDone) {
         this.writeBeginLine();
      }

      OutputStream var4 = this.out;
      byte var5 = encode((byte)var3);
      var4.write(var5);
      int var6 = var3;

      int var7;
      for(var7 = var2; var6 > 2; var7 += 3) {
         OutputStream var8 = this.out;
         byte var9 = encode(var1[var7] >> 2);
         var8.write(var9);
         OutputStream var10 = this.out;
         int var11 = var1[var7] << 4 & 48;
         int var12 = var7 + 1;
         int var13 = var1[var12] >> 4 & 15;
         byte var14 = encode(var11 | var13);
         var10.write(var14);
         OutputStream var15 = this.out;
         int var16 = var7 + 1;
         int var17 = var1[var16] << 2 & 60;
         int var18 = var7 + 2;
         int var19 = var1[var18] >> 6 & 3;
         byte var20 = encode(var17 | var19);
         var15.write(var20);
         OutputStream var21 = this.out;
         int var22 = var7 + 2;
         byte var23 = encode(var1[var22] & 63);
         var21.write(var23);
         var6 += -3;
      }

      if(var6 != 0) {
         byte var24 = var1[var7];
         byte var26;
         if(var6 != 1) {
            int var25 = var7 + 1;
            var26 = var1[var25];
         } else {
            var26 = 0;
         }

         OutputStream var27 = this.out;
         byte var28 = encode(var24 >> 2);
         var27.write(var28);
         OutputStream var29 = this.out;
         int var30 = var24 << 4 & 48;
         int var31 = var26 >> 4 & 15;
         byte var32 = encode(var30 | var31);
         var29.write(var32);
         if(var6 == 1) {
            OutputStream var33 = this.out;
            byte var34 = encode((int)0);
            var33.write(var34);
         } else {
            OutputStream var37 = this.out;
            byte var38 = encode(var26 << 2 & 60);
            var37.write(var38);
         }

         OutputStream var35 = this.out;
         byte var36 = encode((int)0);
         var35.write(var36);
      }

      this.out.write(10);
   }

   public void write(int var1) throws IOException {
      byte[] var2 = new byte[1];
      byte var3 = (byte)var1;
      var2[0] = var3;
      this.write(var2, 0, 1);
   }

   public void write(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.write(var1, 0, var2);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.line.length;
      int var5 = var3 - var2;
      byte[] var6 = new byte[var4 + var5];
      byte[] var7 = this.line;
      int var8 = this.line.length;
      System.arraycopy(var7, 0, var6, 0, var8);
      int var9 = this.line.length;
      int var10 = var3 - var2;
      System.arraycopy(var1, var2, var6, var9, var10);
      this.line = var6;

      int var11;
      for(var11 = 0; this.line.length - var11 > 45; var11 += 45) {
         byte[] var12 = this.line;
         this.flush(var12, var11, 45);
      }

      byte[] var13 = new byte[this.line.length - var11];
      byte[] var14 = this.line;
      int var15 = var13.length;
      System.arraycopy(var14, var11, var13, 0, var15);
      this.line = var13;
   }

   void writeBeginLine() throws IOException {
      StringBuilder var1 = (new StringBuilder()).append("begin ");
      String var2 = Integer.toString(this.mode, 8);
      StringBuilder var3 = var1.append(var2).append(" ");
      String var4 = this.filename;
      String var5 = var3.append(var4).append("\n").toString();
      OutputStream var6 = this.out;
      byte[] var7 = var5.getBytes("US-ASCII");
      var6.write(var7);
      this.beginLineDone = (boolean)1;
   }
}
