package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.OIDTokenizer;

public class DERObjectIdentifier extends ASN1Object {

   String identifier;


   public DERObjectIdentifier(String var1) {
      if(!isValidIdentifier(var1)) {
         String var2 = "string " + var1 + " not an OID";
         throw new IllegalArgumentException(var2);
      } else {
         this.identifier = var1;
      }
   }

   DERObjectIdentifier(byte[] var1) {
      StringBuffer var2 = new StringBuffer();
      long var3 = 0L;
      BigInteger var5 = null;
      int var6 = 0;

      while(true) {
         int var7 = var1.length;
         if(var6 == var7) {
            String var22 = var2.toString();
            this.identifier = var22;
            return;
         }

         int var8 = var1[var6] & 255;
         if(var3 < 36028797018963968L) {
            long var9 = 128L * var3;
            long var11 = (long)(var8 & 127);
            var3 = var9 + var11;
            if((var8 & 128) == 0) {
               if(true) {
                  switch((int)var3 / 40) {
                  case 0:
                     StringBuffer var16 = var2.append('0');
                     break;
                  case 1:
                     StringBuffer var17 = var2.append('1');
                     var3 -= 40L;
                     break;
                  default:
                     StringBuffer var13 = var2.append('2');
                     var3 -= 80L;
                  }
               }

               StringBuffer var14 = var2.append('.');
               var2.append(var3);
               var3 = 0L;
            }
         } else {
            if(var5 == null) {
               var5 = BigInteger.valueOf(var3);
            }

            BigInteger var18 = var5.shiftLeft(7);
            BigInteger var19 = BigInteger.valueOf((long)(var8 & 127));
            var5 = var18.or(var19);
            if((var8 & 128) == 0) {
               StringBuffer var20 = var2.append('.');
               var2.append(var5);
               var5 = null;
               var3 = 0L;
            }
         }

         ++var6;
      }
   }

   public static DERObjectIdentifier getInstance(Object var0) {
      DERObjectIdentifier var1;
      if(var0 != null && !(var0 instanceof DERObjectIdentifier)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERObjectIdentifier(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).toString();
               throw new IllegalArgumentException(var5);
            }

            var1 = getInstance(((ASN1TaggedObject)var0).getObject());
         }
      } else {
         var1 = (DERObjectIdentifier)var0;
      }

      return var1;
   }

   public static DERObjectIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   private static boolean isValidIdentifier(String var0) {
      boolean var1;
      if(var0.length() >= 3 && var0.charAt(1) == 46) {
         char var2 = var0.charAt(0);
         if(var2 >= 48 && var2 <= 50) {
            boolean var3 = false;
            int var4 = var0.length() - 1;

            while(true) {
               if(var4 < 2) {
                  var1 = var3;
                  break;
               }

               char var5 = var0.charAt(var4);
               if(48 <= var5 && var5 <= 57) {
                  var3 = true;
               } else {
                  if(var5 != 46) {
                     var1 = false;
                     break;
                  }

                  if(!var3) {
                     var1 = false;
                     break;
                  }

                  var3 = false;
               }

               var4 += -1;
            }
         } else {
            var1 = false;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   private void writeField(OutputStream var1, long var2) throws IOException {
      if(var2 >= 128L) {
         if(var2 >= 16384L) {
            if(var2 >= 2097152L) {
               if(var2 >= 268435456L) {
                  if(var2 >= 34359738368L) {
                     if(var2 >= 4398046511104L) {
                        if(var2 >= 562949953421312L) {
                           if(var2 >= 72057594037927936L) {
                              int var4 = (int)(var2 >> 56) | 128;
                              var1.write(var4);
                           }

                           int var5 = (int)(var2 >> 49) | 128;
                           var1.write(var5);
                        }

                        int var6 = (int)(var2 >> 42) | 128;
                        var1.write(var6);
                     }

                     int var7 = (int)(var2 >> 35) | 128;
                     var1.write(var7);
                  }

                  int var8 = (int)(var2 >> 28) | 128;
                  var1.write(var8);
               }

               int var9 = (int)(var2 >> 21) | 128;
               var1.write(var9);
            }

            int var10 = (int)(var2 >> 14) | 128;
            var1.write(var10);
         }

         int var11 = (int)(var2 >> 7) | 128;
         var1.write(var11);
      }

      int var12 = (int)var2 & 127;
      var1.write(var12);
   }

   private void writeField(OutputStream var1, BigInteger var2) throws IOException {
      int var3 = (var2.bitLength() + 6) / 7;
      if(var3 == 0) {
         var1.write(0);
      } else {
         BigInteger var4 = var2;
         byte[] var5 = new byte[var3];

         for(int var6 = var3 - 1; var6 >= 0; var6 += -1) {
            byte var7 = (byte)(var4.intValue() & 127 | 128);
            var5[var6] = var7;
            var4 = var4.shiftRight(7);
         }

         int var8 = var3 - 1;
         byte var9 = (byte)(var5[var8] & 127);
         var5[var8] = var9;
         var1.write(var5);
      }
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERObjectIdentifier)) {
         var2 = 0;
      } else {
         String var3 = this.identifier;
         String var4 = ((DERObjectIdentifier)var1).identifier;
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      String var2 = this.identifier;
      OIDTokenizer var3 = new OIDTokenizer(var2);
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      DEROutputStream var5 = new DEROutputStream(var4);
      int var6 = Integer.parseInt(var3.nextToken()) * 40;
      int var7 = Integer.parseInt(var3.nextToken());
      long var8 = (long)(var6 + var7);
      this.writeField(var4, var8);

      while(var3.hasMoreTokens()) {
         String var10 = var3.nextToken();
         if(var10.length() < 18) {
            long var11 = Long.parseLong(var10);
            this.writeField(var4, var11);
         } else {
            BigInteger var13 = new BigInteger(var10);
            this.writeField(var4, var13);
         }
      }

      var5.close();
      byte[] var14 = var4.toByteArray();
      var1.writeEncoded(6, var14);
   }

   public String getId() {
      return this.identifier;
   }

   public int hashCode() {
      return this.identifier.hashCode();
   }

   public String toString() {
      return this.getId();
   }
}
