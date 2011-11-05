package gnu.inet.ldap;

import gnu.inet.ldap.BERException;
import java.io.UnsupportedEncodingException;

public class BEREncoder {

   private byte[] buffer;
   private int offset;
   private int sequenceIndex;
   private int[] sequenceOffset;
   private boolean utf8;


   public BEREncoder(boolean var1) {
      this(var1, 1024);
   }

   public BEREncoder(boolean var1, int var2) {
      this.utf8 = var1;
      byte[] var3 = new byte[var2];
      this.buffer = var3;
      this.offset = 0;
      int[] var4 = new int[16];
      this.sequenceOffset = var4;
      this.sequenceIndex = 0;
   }

   private void allocate(int var1) {
      int var2 = this.buffer.length;
      int var3 = this.offset;
      if(var2 - var3 < var1) {
         int var4 = this.buffer.length;

         int var5;
         do {
            var4 *= 2;
            var5 = this.offset;
         } while(var4 - var5 < var1);

         byte[] var6 = new byte[var4];
         byte[] var7 = this.buffer;
         int var8 = this.offset;
         System.arraycopy(var7, 0, var6, 0, var8);
         this.buffer = var6;
      }
   }

   private void appendLength(int var1) throws BERException {
      if(var1 < 128) {
         byte[] var2 = this.buffer;
         int var3 = this.offset;
         int var4 = var3 + 1;
         this.offset = var4;
         byte var5 = (byte)var1;
         var2[var3] = var5;
      } else if(var1 < 256) {
         byte[] var6 = this.buffer;
         int var7 = this.offset;
         int var8 = var7 + 1;
         this.offset = var8;
         var6[var7] = (byte)'\uff81';
         byte[] var9 = this.buffer;
         int var10 = this.offset;
         int var11 = var10 + 1;
         this.offset = var11;
         byte var12 = (byte)var1;
         var9[var10] = var12;
      } else if(var1 < 65536) {
         byte[] var13 = this.buffer;
         int var14 = this.offset;
         int var15 = var14 + 1;
         this.offset = var15;
         var13[var14] = (byte)'\uff82';
         byte[] var16 = this.buffer;
         int var17 = this.offset;
         int var18 = var17 + 1;
         this.offset = var18;
         byte var19 = (byte)(var1 >> 8);
         var16[var17] = var19;
         byte[] var20 = this.buffer;
         int var21 = this.offset;
         int var22 = var21 + 1;
         this.offset = var22;
         byte var23 = (byte)(var1 & 255);
         var20[var21] = var23;
      } else if(var1 < 16777216) {
         byte[] var24 = this.buffer;
         int var25 = this.offset;
         int var26 = var25 + 1;
         this.offset = var26;
         var24[var25] = (byte)'\uff83';
         byte[] var27 = this.buffer;
         int var28 = this.offset;
         int var29 = var28 + 1;
         this.offset = var29;
         byte var30 = (byte)(var1 >> 16);
         var27[var28] = var30;
         byte[] var31 = this.buffer;
         int var32 = this.offset;
         int var33 = var32 + 1;
         this.offset = var33;
         byte var34 = (byte)(var1 >> 8);
         var31[var32] = var34;
         byte[] var35 = this.buffer;
         int var36 = this.offset;
         int var37 = var36 + 1;
         this.offset = var37;
         byte var38 = (byte)(var1 & 255);
         var35[var36] = var38;
      } else {
         String var39 = "Data too long: " + var1;
         throw new BERException(var39);
      }
   }

   static int indexOf(byte[] var0, byte var1, int var2) {
      int var3 = var2;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            var3 = -1;
            break;
         }

         if(var0[var3] == var1) {
            break;
         }

         if(var0[var3] == 41) {
            var3 = -1;
            break;
         }

         ++var3;
      }

      return var3;
   }

   static byte[] unencode(byte[] var0, int var1, int var2) throws BERException {
      byte[] var3 = new byte[var2 - var1];
      int var4 = indexOf(var0, (byte)92, var1);
      int var5 = 0;

      int var6;
      int var13;
      for(var6 = var1; var4 != -1; var5 = var13) {
         if(var4 + 3 > var2) {
            throw new BERException("Illegal filter value encoding");
         }

         int var7 = var4 - var6;
         System.arraycopy(var0, var6, var3, var5, var7);
         int var8 = var5 + var7;
         int var9 = var4 + 2;
         int var10 = Character.digit((char)var0[var9], 16);
         int var11 = var4 + 1;
         int var12 = Character.digit((char)var0[var11], 16) * 16 + var10;
         var13 = var8 + 1;
         byte var14 = (byte)var12;
         var3[var8] = var14;
         int var15 = var7 + 3 + var6;
         int var16 = indexOf(var0, (byte)92, var15);
         var6 = var15;
         var4 = var16;
      }

      int var17 = var2 - var6;
      System.arraycopy(var0, var6, var3, var5, var17);
      int var18 = var5 + var17;
      int var10000 = var17 + var6;
      int var20 = var3.length;
      if(var18 != var20) {
         byte[] var21 = new byte[var18];
         System.arraycopy(var3, 0, var21, 0, var18);
         var3 = var21;
      }

      return var3;
   }

   public void append(int var1) {
      this.append(var1, 2);
   }

   public void append(int var1, int var2) {
      int var3 = 4;

      int var4;
      for(var4 = var1; ((var4 & -8388608) == 0 || (var4 & -8388608) == -8388608) && var3 > 1; var4 <<= 8) {
         var3 += -1;
      }

      int var5 = var3 + 2;
      this.allocate(var5);
      byte[] var6 = this.buffer;
      int var7 = this.offset;
      int var8 = var7 + 1;
      this.offset = var8;
      byte var9 = (byte)var2;
      var6[var7] = var9;
      byte[] var10 = this.buffer;
      int var11 = this.offset;
      int var12 = var11 + 1;
      this.offset = var12;
      byte var13 = (byte)var3;

      for(var10[var11] = var13; var3 > 0; var3 += -1) {
         byte[] var14 = this.buffer;
         int var15 = this.offset;
         int var16 = var15 + 1;
         this.offset = var16;
         byte var17 = (byte)((-16777216 & var4) >> 24);
         var14[var15] = var17;
      }

   }

   public void append(String var1) throws BERException {
      this.append(var1, 12);
   }

   public void append(String var1, int var2) throws BERException {
      byte[] var3;
      if(var1 == null) {
         var3 = new byte[0];
      } else {
         String var13;
         if(this.utf8) {
            var13 = "UTF-8";
         } else {
            var13 = "ISO-8859-1";
         }

         byte[] var14;
         try {
            var14 = var1.getBytes(var13);
         } catch (UnsupportedEncodingException var17) {
            String var16 = "JVM does not support " + var13;
            throw new BERException(var16);
         }

         var3 = var14;
      }

      int var4 = var3.length;
      int var5 = var4 + 5;
      this.allocate(var5);
      byte[] var6 = this.buffer;
      int var7 = this.offset;
      int var8 = var7 + 1;
      this.offset = var8;
      byte var9 = (byte)var2;
      var6[var7] = var9;
      this.appendLength(var4);
      byte[] var10 = this.buffer;
      int var11 = this.offset;
      System.arraycopy(var3, 0, var10, var11, var4);
      int var12 = this.offset + var4;
      this.offset = var12;
   }

   public void append(boolean var1) {
      this.append(var1, 1);
   }

   public void append(boolean var1, int var2) {
      this.allocate(3);
      byte[] var3 = this.buffer;
      int var4 = this.offset;
      int var5 = var4 + 1;
      this.offset = var5;
      byte var6 = (byte)var2;
      var3[var4] = var6;
      byte[] var7 = this.buffer;
      int var8 = this.offset;
      int var9 = var8 + 1;
      this.offset = var9;
      var7[var8] = 1;
      byte[] var10 = this.buffer;
      int var11 = this.offset;
      int var12 = var11 + 1;
      this.offset = var12;
      byte var13;
      if(var1) {
         var13 = -1;
      } else {
         var13 = 0;
      }

      var10[var11] = var13;
   }

   public void append(byte[] var1) throws BERException {
      this.append(var1, 4);
   }

   public void append(byte[] var1, int var2) throws BERException {
      int var3;
      if(var1 == null) {
         var3 = 0;
      } else {
         var3 = var1.length;
      }

      this.append(var1, 0, var3, var2);
   }

   void append(byte[] var1, int var2, int var3, int var4) throws BERException {
      int var5 = var3 + 5;
      this.allocate(var5);
      byte[] var6 = this.buffer;
      int var7 = this.offset;
      int var8 = var7 + 1;
      this.offset = var8;
      byte var9 = (byte)var4;
      var6[var7] = var9;
      this.appendLength(var3);
      if(var3 > 0) {
         byte[] var10 = this.buffer;
         int var11 = this.offset;
         System.arraycopy(var1, var2, var10, var11, var3);
         int var12 = this.offset + var3;
         this.offset = var12;
      }
   }

   int appendFilter(byte[] var1, int var2) throws BERException {
      int var3 = 0;
      int var4 = var2;

      while(true) {
         int var5 = var1.length;
         int var6;
         if(var4 >= var5) {
            if(var3 != 0) {
               throw new BERException("Unbalanced parentheses");
            }

            var6 = var4;
            return var6;
         }

         switch(var1[var4]) {
         case 32:
            ++var4;
            break;
         case 33:
            int var9 = var4 + 1;
            var4 = this.appendFilterList(var1, var9, 162);
            break;
         case 34:
         case 35:
         case 36:
         case 37:
         case 39:
         case 42:
         case 43:
         default:
            var4 = this.appendFilterItem(var1, var4);
            break;
         case 38:
            int var7 = var4 + 1;
            var4 = this.appendFilterList(var1, var7, 160);
            break;
         case 40:
            ++var3;
            ++var4;
            break;
         case 41:
            var3 += -1;
            if(var3 != 0) {
               break;
            }

            var6 = var4 + 1;
            return var6;
         case 44:
            int var8 = var4 + 1;
            var4 = this.appendFilterList(var1, var8, 161);
         }
      }
   }

   public void appendFilter(String var1) throws BERException {
      if(var1 != null && var1.length() != 0) {
         String var2;
         if(this.utf8) {
            var2 = "UTF-8";
         } else {
            var2 = "ISO-8859-1";
         }

         byte[] var3;
         try {
            var3 = var1.getBytes(var2);
         } catch (UnsupportedEncodingException var8) {
            String var7 = "JVM does not support " + var2;
            throw new BERException(var7);
         }

         this.appendFilter(var3, 0);
      } else {
         throw new BERException("Empty filter expression");
      }
   }

   int appendFilterItem(byte[] var1, int var2) throws BERException {
      int var3 = indexOf(var1, (byte)61, var2);
      if(var3 == -1) {
         throw new BERException("Missing \'=\'");
      } else {
         boolean var4 = this.utf8;
         BEREncoder var5 = new BEREncoder(var4);
         int var6 = var3 - 1;
         short var8;
         int var9;
         int var26;
         switch(var1[var6]) {
         case 58:
            var9 = var3;
            break;
         case 60:
            var9 = var3 + -1;
            break;
         case 62:
            var9 = var3 + -1;
            break;
         case 126:
            var9 = var3 + -1;
            break;
         default:
            int var7 = var3 + 1;
            if(indexOf(var1, (byte)42, var7) == -1) {
               var8 = 163;
               var9 = var3;
            } else {
               int var13 = var3 + 1;
               int var14 = var1.length;
               if(var13 != var14) {
                  int var15 = var3 + 2;
                  if(var1[var15] != 41) {
                     boolean var17 = this.utf8;
                     BEREncoder var18 = new BEREncoder(var17);
                     var18.append(var1, var2, var3, 4);
                     int var19 = var3 + 1;
                     int var30 = indexOf(var1, (byte)41, var19);
                     if(var30 == -1) {
                        throw new BERException("No terminating \')\'");
                     }

                     boolean var20 = this.utf8;
                     BEREncoder var21 = new BEREncoder(var20);
                     int var22 = var3 + 1;
                     byte[] var23 = unencode(var1, var22, var30);
                     var21.append(var23);
                     byte[] var24 = var21.toByteArray();
                     var18.append(var24, 48);
                     byte[] var25 = var18.toByteArray();
                     this.append(var25, 164);
                     var26 = var30;
                     return var26;
                  }
               }

               var9 = var3 + -1;
               boolean var16 = true;
            }
         }

         int var10 = var9 - var2;
         var5.append(var1, var2, var10, 4);
         int var11 = var3 + 1;
         int var12 = indexOf(var1, (byte)41, var11);
         if(var12 == -1) {
            throw new BERException("No terminating \')\'");
         } else {
            if(var8 != 135) {
               int var27 = var3 + 1;
               byte[] var28 = unencode(var1, var27, var12);
               var5.append(var28);
            }

            byte[] var29 = var5.toByteArray();
            this.append(var29, var8);
            var26 = var12;
            return var26;
         }
      }
   }

   int appendFilterList(byte[] var1, int var2, int var3) throws BERException {
      boolean var4 = this.utf8;
      BEREncoder var5 = new BEREncoder(var4);
      int var6 = var2;

      while(true) {
         int var7 = var1.length;
         if(var6 >= var7 || var1[var6] != 40) {
            byte[] var8 = var5.toByteArray();
            this.append(var8, var3);
            return var6;
         }

         var6 = var5.appendFilter(var1, var6);
      }
   }

   public void appendNull() {
      this.allocate(2);
      byte[] var1 = this.buffer;
      int var2 = this.offset;
      int var3 = var2 + 1;
      this.offset = var3;
      var1[var2] = 5;
      byte[] var4 = this.buffer;
      int var5 = this.offset;
      int var6 = var5 + 1;
      this.offset = var6;
      var4[var5] = 0;
   }

   public void reset() {
      int var1 = 0;

      while(true) {
         int var2 = this.offset;
         if(var1 >= var2) {
            this.offset = 0;
            int var3 = 0;

            while(true) {
               int var4 = this.sequenceIndex;
               if(var3 >= var4) {
                  this.sequenceIndex = 0;
                  return;
               }

               this.sequenceOffset[var3] = 0;
               ++var3;
            }
         }

         this.buffer[var1] = 0;
         ++var1;
      }
   }

   public int size() {
      return this.offset;
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[this.offset];
      byte[] var2 = this.buffer;
      int var3 = this.offset;
      System.arraycopy(var2, 0, var1, 0, var3);
      return var1;
   }
}
