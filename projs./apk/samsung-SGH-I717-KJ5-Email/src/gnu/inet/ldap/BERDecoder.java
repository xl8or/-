package gnu.inet.ldap;

import gnu.inet.ldap.BERException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class BERDecoder {

   private byte[] buffer;
   private boolean control;
   private int len;
   private int offset;
   private int type;
   private boolean utf8;


   public BERDecoder(byte[] var1, boolean var2) {
      this.buffer = var1;
      this.offset = 0;
      this.control = (boolean)1;
      this.utf8 = var2;
   }

   static int byteToInt(byte var0) {
      int var1;
      if(var0 < 0) {
         var1 = var0 + 256;
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static void debug(BERDecoder var0, int var1) throws BERException {
      for(int var2 = var0.parseType(); var2 != -1; var2 = var0.parseType()) {
         for(int var3 = 0; var3 < var1; ++var3) {
            System.out.print('\t');
         }

         switch(var2) {
         case 1:
            PrintStream var12 = System.out;
            StringBuilder var13 = (new StringBuilder()).append("BOOLEAN: ");
            boolean var14 = var0.parseBoolean();
            String var15 = var13.append(var14).toString();
            var12.println(var15);
            break;
         case 2:
            PrintStream var16 = System.out;
            StringBuilder var17 = (new StringBuilder()).append("INTEGER: ");
            int var18 = var0.parseInt();
            String var19 = var17.append(var18).toString();
            var16.println(var19);
            break;
         case 4:
            PrintStream var24 = System.out;
            StringBuilder var25 = (new StringBuilder()).append("OCTET-STRING: ");
            String var26 = toString(var0.parseOctetString());
            String var27 = var25.append(var26).toString();
            var24.println(var27);
            break;
         case 10:
            PrintStream var20 = System.out;
            StringBuilder var21 = (new StringBuilder()).append("ENUMERATED: ");
            int var22 = var0.parseInt();
            String var23 = var21.append(var22).toString();
            var20.println(var23);
            break;
         case 12:
            PrintStream var28 = System.out;
            StringBuilder var29 = (new StringBuilder()).append("STRING: \"");
            String var30 = var0.parseString();
            String var31 = var29.append(var30).append("\"").toString();
            var28.println(var31);
            break;
         default:
            PrintStream var4 = System.out;
            StringBuilder var5 = (new StringBuilder()).append("SEQUENCE ").append(var2).append("(0x");
            String var6 = Integer.toHexString(var2);
            StringBuilder var7 = var5.append(var6).append("): ");
            int var8 = var0.getLength();
            String var9 = var7.append(var8).toString();
            var4.println(var9);
            BERDecoder var10 = var0.parseSequence(var2);
            int var11 = var1 + 1;
            debug(var10, var11);
         }
      }

   }

   public static void main(String[] var0) {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();

         for(int var2 = System.in.read(); var2 != -1; var2 = System.in.read()) {
            var1.write(var2);
         }

         byte[] var3 = var1.toByteArray();
         debug(new BERDecoder(var3, (boolean)1), 0);
      } catch (Exception var6) {
         PrintStream var5 = System.err;
         var6.printStackTrace(var5);
      }
   }

   private static String toString(byte[] var0) {
      String var3;
      String var4;
      try {
         StringBuilder var1 = (new StringBuilder()).append("\"");
         String var2 = new String(var0, "UTF-8");
         var3 = var1.append(var2).append("\"").toString();
      } catch (UnsupportedEncodingException var6) {
         var4 = var0.toString();
         return var4;
      }

      var4 = var3;
      return var4;
   }

   public boolean available() {
      int var1 = this.offset;
      int var2 = this.buffer.length;
      boolean var3;
      if(var1 < var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   int getLength() {
      return this.len;
   }

   public boolean parseBoolean() throws BERException {
      if(this.control) {
         int var1 = this.parseType();
      }

      if(this.type != 1) {
         StringBuilder var2 = (new StringBuilder()).append("Unexpected type: ");
         int var3 = this.type;
         String var4 = var2.append(var3).toString();
         throw new BERException(var4);
      } else {
         byte[] var5 = this.buffer;
         int var6 = this.offset;
         int var7 = var6 + 1;
         this.offset = var7;
         byte var8 = var5[var6];
         this.control = (boolean)1;
         boolean var9;
         if(var8 != 0) {
            var9 = true;
         } else {
            var9 = false;
         }

         return var9;
      }
   }

   public int parseInt() throws BERException {
      if(this.control) {
         int var1 = this.parseType();
      }

      if(this.type != 2 && this.type != 10) {
         StringBuilder var2 = (new StringBuilder()).append("Unexpected type: ");
         int var3 = this.type;
         String var4 = var2.append(var3).toString();
         throw new BERException(var4);
      } else {
         byte[] var5 = this.buffer;
         int var6 = this.offset;
         int var7 = var6 + 1;
         this.offset = var7;
         byte var8 = var5[var6];
         int var9 = var8 & 127;
         int var10 = 1;

         while(true) {
            int var11 = this.len;
            if(var10 >= var11) {
               int var17;
               if((var8 & 128) != 0) {
                  var17 = -var9;
               } else {
                  var17 = var9;
               }

               this.control = (boolean)1;
               return var17;
            }

            int var12 = var9 << 8;
            byte[] var13 = this.buffer;
            int var14 = this.offset;
            int var15 = var14 + 1;
            this.offset = var15;
            int var16 = var13[var14] & 255;
            var9 = var12 | var16;
            ++var10;
         }
      }
   }

   public byte[] parseOctetString() throws BERException {
      if(this.control) {
         int var1 = this.parseType();
      }

      if(this.type != 4) {
         StringBuilder var2 = (new StringBuilder()).append("Unexpected type: ");
         int var3 = this.type;
         String var4 = var2.append(var3).toString();
         throw new BERException(var4);
      } else {
         byte[] var5 = new byte[this.len];
         byte[] var6 = this.buffer;
         int var7 = this.offset;
         int var8 = this.len;
         System.arraycopy(var6, var7, var5, 0, var8);
         int var9 = this.offset;
         int var10 = this.len;
         int var11 = var9 + var10;
         this.offset = var11;
         this.control = (boolean)1;
         return var5;
      }
   }

   public BERDecoder parseSequence() throws BERException {
      return this.parseSequence(16);
   }

   public BERDecoder parseSequence(int var1) throws BERException {
      if(this.control) {
         int var2 = this.parseType();
      }

      if(var1 != -1 && this.type != var1) {
         StringBuilder var3 = (new StringBuilder()).append("Unexpected type: ");
         int var4 = this.type;
         String var5 = var3.append(var4).toString();
         throw new BERException(var5);
      } else {
         byte[] var6 = new byte[this.len];
         byte[] var7 = this.buffer;
         int var8 = this.offset;
         int var9 = this.len;
         System.arraycopy(var7, var8, var6, 0, var9);
         int var10 = this.offset;
         int var11 = this.len;
         int var12 = var10 + var11;
         this.offset = var12;
         this.control = (boolean)1;
         boolean var13 = this.utf8;
         return new BERDecoder(var6, var13);
      }
   }

   public BERDecoder parseSet() throws BERException {
      return this.parseSet(17);
   }

   public BERDecoder parseSet(int var1) throws BERException {
      return this.parseSequence(var1);
   }

   public String parseString() throws BERException {
      String var1 = null;
      if(this.control) {
         int var2 = this.parseType();
      }

      String var3;
      if(this.len == 0) {
         this.control = (boolean)1;
         var3 = "";
      } else {
         if(this.type != 12 && this.type != 4) {
            StringBuilder var4 = (new StringBuilder()).append("Unexpected type: ");
            int var5 = this.type;
            String var6 = var4.append(var5).toString();
            throw new BERException(var6);
         }

         String var7;
         if(this.type == 12) {
            var7 = "UTF-8";
         } else {
            var7 = "ISO-8859-1";
         }

         try {
            byte[] var8 = this.buffer;
            int var9 = this.offset;
            int var10 = this.len;
            var1 = new String(var8, var9, var10, var7);
            int var11 = this.offset;
            int var12 = this.len;
            int var13 = var11 + var12;
            this.offset = var13;
            this.control = (boolean)1;
         } catch (UnsupportedEncodingException var16) {
            String var15 = "JVM does not support " + var7;
            throw new BERException(var15);
         }

         var3 = var1;
      }

      return var3;
   }

   public int parseType() throws BERException {
      int var1 = this.offset;
      int var2 = this.buffer.length;
      int var3;
      if(var1 >= var2) {
         var3 = -1;
      } else {
         byte[] var4 = this.buffer;
         int var5 = this.offset;
         int var6 = var5 + 1;
         this.offset = var6;
         int var7 = byteToInt(var4[var5]);
         this.type = var7;
         byte[] var8 = this.buffer;
         int var9 = this.offset;
         int var10 = var9 + 1;
         this.offset = var10;
         int var11 = byteToInt(var8[var9]);
         this.len = var11;
         if((this.len & 128) != 0) {
            int var12 = this.len - 128;
            if(var12 > 4) {
               String var13 = "Data too long: " + var12;
               throw new BERException(var13);
            }

            int var14 = this.buffer.length;
            int var15 = this.offset;
            if(var14 - var15 < var12) {
               throw new BERException("Insufficient data");
            }

            this.len = 0;

            for(int var16 = 0; var16 < var12; ++var16) {
               int var17 = this.len << 8;
               byte[] var18 = this.buffer;
               int var19 = this.offset;
               int var20 = var19 + 1;
               this.offset = var20;
               int var21 = byteToInt(var18[var19]);
               int var22 = var17 + var21;
               this.len = var22;
            }

            int var23 = this.buffer.length;
            int var24 = this.offset;
            int var25 = var23 - var24;
            int var26 = this.len;
            if(var25 < var26) {
               throw new BERException("Insufficient data");
            }
         }

         this.control = (boolean)0;
         var3 = this.type;
      }

      return var3;
   }

   public void skip() {
      int var1 = this.offset;
      int var2 = this.len;
      int var3 = var1 + var2;
      this.offset = var3;
      this.control = (boolean)1;
   }
}
