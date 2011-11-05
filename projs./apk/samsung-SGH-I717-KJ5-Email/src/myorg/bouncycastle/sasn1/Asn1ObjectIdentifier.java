package myorg.bouncycastle.sasn1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import myorg.bouncycastle.sasn1.DerObject;

public class Asn1ObjectIdentifier extends DerObject {

   private String _oid;


   Asn1ObjectIdentifier(int var1, byte[] var2) throws IOException {
      super(var1, 6, var2);
      StringBuffer var3 = new StringBuffer();
      long var4 = 0L;
      boolean var6 = true;
      BigInteger var7 = null;
      ByteArrayInputStream var8 = new ByteArrayInputStream(var2);

      while(true) {
         int var9 = var8.read();
         if(var9 < 0) {
            String var23 = var3.toString();
            this._oid = var23;
            return;
         }

         if(var4 < 36028797018963968L) {
            long var10 = 128L * var4;
            long var12 = (long)(var9 & 127);
            var4 = var10 + var12;
            if((var9 & 128) == 0) {
               if(var6) {
                  switch((int)var4 / 40) {
                  case 0:
                     StringBuffer var17 = var3.append('0');
                     break;
                  case 1:
                     StringBuffer var18 = var3.append('1');
                     var4 -= 40L;
                     break;
                  default:
                     StringBuffer var14 = var3.append('2');
                     var4 -= 80L;
                  }

                  var6 = false;
               }

               StringBuffer var15 = var3.append('.');
               var3.append(var4);
               var4 = 0L;
            }
         } else {
            if(var7 == null) {
               var7 = BigInteger.valueOf(var4);
            }

            BigInteger var19 = var7.shiftLeft(7);
            BigInteger var20 = BigInteger.valueOf((long)(var9 & 127));
            var7 = var19.or(var20);
            if((var9 & 128) == 0) {
               StringBuffer var21 = var3.append('.');
               var3.append(var7);
               var7 = null;
               var4 = 0L;
            }
         }
      }
   }

   public Asn1ObjectIdentifier(String var1) throws IllegalArgumentException {
      byte[] var2 = toByteArray(var1);
      super(0, 6, var2);
      this._oid = var1;
   }

   private static byte[] toByteArray(String var0) throws IllegalArgumentException {
      Asn1ObjectIdentifier.OIDTokenizer var1 = new Asn1ObjectIdentifier.OIDTokenizer(var0);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      try {
         int var3 = Integer.parseInt(var1.nextToken()) * 40;
         int var4 = Integer.parseInt(var1.nextToken());
         long var5 = (long)(var3 + var4);
         writeField(var2, var5);

         while(var1.hasMoreTokens()) {
            String var7 = var1.nextToken();
            if(var7.length() < 18) {
               long var8 = Long.parseLong(var7);
               writeField(var2, var8);
            } else {
               BigInteger var14 = new BigInteger(var7);
               writeField(var2, var14);
            }
         }
      } catch (NumberFormatException var19) {
         StringBuilder var11 = (new StringBuilder()).append("exception parsing field value: ");
         String var12 = var19.getMessage();
         String var13 = var11.append(var12).toString();
         throw new IllegalArgumentException(var13);
      } catch (IOException var20) {
         StringBuilder var16 = (new StringBuilder()).append("exception converting to bytes: ");
         String var17 = var20.getMessage();
         String var18 = var16.append(var17).toString();
         throw new IllegalArgumentException(var18);
      }

      return var2.toByteArray();
   }

   private static void writeField(OutputStream var0, long var1) throws IOException {
      if(var1 >= 128L) {
         if(var1 >= 16384L) {
            if(var1 >= 2097152L) {
               if(var1 >= 268435456L) {
                  if(var1 >= 34359738368L) {
                     if(var1 >= 4398046511104L) {
                        if(var1 >= 562949953421312L) {
                           if(var1 >= 72057594037927936L) {
                              int var3 = (int)(var1 >> 56) | 128;
                              var0.write(var3);
                           }

                           int var4 = (int)(var1 >> 49) | 128;
                           var0.write(var4);
                        }

                        int var5 = (int)(var1 >> 42) | 128;
                        var0.write(var5);
                     }

                     int var6 = (int)(var1 >> 35) | 128;
                     var0.write(var6);
                  }

                  int var7 = (int)(var1 >> 28) | 128;
                  var0.write(var7);
               }

               int var8 = (int)(var1 >> 21) | 128;
               var0.write(var8);
            }

            int var9 = (int)(var1 >> 14) | 128;
            var0.write(var9);
         }

         int var10 = (int)(var1 >> 7) | 128;
         var0.write(var10);
      }

      int var11 = (int)var1 & 127;
      var0.write(var11);
   }

   private static void writeField(OutputStream var0, BigInteger var1) throws IOException {
      int var2 = (var1.bitLength() + 6) / 7;
      if(var2 == 0) {
         var0.write(0);
      } else {
         BigInteger var3 = var1;
         byte[] var4 = new byte[var2];

         for(int var5 = var2 - 1; var5 >= 0; var5 += -1) {
            byte var6 = (byte)(var3.intValue() & 127 | 128);
            var4[var5] = var6;
            var3 = var3.shiftRight(7);
         }

         int var7 = var2 - 1;
         byte var8 = (byte)(var4[var7] & 127);
         var4[var7] = var8;
         var0.write(var4);
      }
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof Asn1ObjectIdentifier)) {
         var2 = 0;
      } else {
         String var3 = this._oid;
         String var4 = ((Asn1ObjectIdentifier)var1)._oid;
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }

   public int hashCode() {
      return this._oid.hashCode();
   }

   public String toString() {
      return this._oid;
   }

   private static class OIDTokenizer {

      private int index;
      private String oid;


      public OIDTokenizer(String var1) {
         this.oid = var1;
         this.index = 0;
      }

      public boolean hasMoreTokens() {
         boolean var1;
         if(this.index != -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public String nextToken() {
         String var1;
         if(this.index == -1) {
            var1 = null;
         } else {
            String var2 = this.oid;
            int var3 = this.index;
            int var4 = var2.indexOf(46, var3);
            if(var4 == -1) {
               String var5 = this.oid;
               int var6 = this.index;
               String var7 = var5.substring(var6);
               this.index = -1;
               var1 = var7;
            } else {
               String var8 = this.oid;
               int var9 = this.index;
               String var10 = var8.substring(var9, var4);
               int var11 = var4 + 1;
               this.index = var11;
               var1 = var10;
            }
         }

         return var1;
      }
   }
}
