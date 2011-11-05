package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.util.Arrays;

public class DERBitString extends ASN1Object implements DERString {

   private static final char[] table = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   protected byte[] data;
   protected int padBits;


   protected DERBitString(byte var1, int var2) {
      byte[] var3 = new byte[1];
      this.data = var3;
      this.data[0] = var1;
      this.padBits = var2;
   }

   public DERBitString(DEREncodable var1) {
      try {
         byte[] var2 = var1.getDERObject().getEncoded("DER");
         this.data = var2;
         this.padBits = 0;
      } catch (IOException var7) {
         StringBuilder var4 = (new StringBuilder()).append("Error processing object : ");
         String var5 = var7.toString();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      }
   }

   public DERBitString(byte[] var1) {
      this(var1, 0);
   }

   public DERBitString(byte[] var1, int var2) {
      this.data = var1;
      this.padBits = var2;
   }

   protected static byte[] getBytes(int var0) {
      int var1 = 4;

      for(int var2 = 3; var2 >= 1; var2 += -1) {
         int var3 = var2 * 8;
         if((255 << var3 & var0) != 0) {
            break;
         }

         var1 += -1;
      }

      byte[] var4 = new byte[var1];

      for(int var5 = 0; var5 < var1; ++var5) {
         int var6 = var5 * 8;
         byte var7 = (byte)(var0 >> var6 & 255);
         var4[var5] = var7;
      }

      return var4;
   }

   public static DERBitString getInstance(Object var0) {
      DERBitString var1;
      if(var0 != null && !(var0 instanceof DERBitString)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            byte var3 = var2[0];
            byte[] var4 = new byte[var2.length - 1];
            int var5 = var2.length - 1;
            System.arraycopy(var2, 1, var4, 0, var5);
            var1 = new DERBitString(var4, var3);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var6 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var7 = var0.getClass().getName();
               String var8 = var6.append(var7).toString();
               throw new IllegalArgumentException(var8);
            }

            var1 = getInstance(((ASN1TaggedObject)var0).getObject());
         }
      } else {
         var1 = (DERBitString)var0;
      }

      return var1;
   }

   public static DERBitString getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   protected static int getPadBits(int var0) {
      int var1 = 0;

      for(int var2 = 3; var2 >= 0; var2 += -1) {
         if(var2 != 0) {
            int var3 = var2 * 8;
            if(var0 >> var3 != 0) {
               int var4 = var2 * 8;
               var1 = var0 >> var4 & 255;
               break;
            }
         } else if(var0 != 0) {
            var1 = var0 & 255;
            break;
         }
      }

      int var5;
      if(var1 == 0) {
         var5 = 7;
      } else {
         int var6 = 1;

         while(true) {
            var1 <<= 1;
            if((var1 & 255) == 0) {
               var5 = 8 - var6;
               break;
            }

            ++var6;
         }
      }

      return var5;
   }

   protected boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof DERBitString)) {
         var2 = false;
      } else {
         DERBitString var3 = (DERBitString)var1;
         int var4 = this.padBits;
         int var5 = var3.padBits;
         if(var4 == var5) {
            byte[] var6 = this.data;
            byte[] var7 = var3.data;
            if(Arrays.areEqual(var6, var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = new byte[this.getBytes().length + 1];
      byte var3 = (byte)this.getPadBits();
      var2[0] = var3;
      byte[] var4 = this.getBytes();
      int var5 = var2.length - 1;
      System.arraycopy(var4, 0, var2, 1, var5);
      var1.writeEncoded(3, var2);
   }

   public byte[] getBytes() {
      return this.data;
   }

   public int getPadBits() {
      return this.padBits;
   }

   public String getString() {
      StringBuffer var1 = new StringBuffer("#");
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ASN1OutputStream var3 = new ASN1OutputStream(var2);

      try {
         var3.writeObject(this);
      } catch (IOException var16) {
         throw new RuntimeException("internal error encoding BitString");
      }

      byte[] var4 = var2.toByteArray();
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 == var6) {
            return var1.toString();
         }

         char[] var7 = table;
         int var8 = var4[var5] >>> 4 & 15;
         char var9 = var7[var8];
         var1.append(var9);
         char[] var11 = table;
         int var12 = var4[var5] & 15;
         char var13 = var11[var12];
         var1.append(var13);
         ++var5;
      }
   }

   public int hashCode() {
      int var1 = this.padBits;
      int var2 = Arrays.hashCode(this.data);
      return var1 ^ var2;
   }

   public int intValue() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = this.data.length;
         if(var2 == var3 || var2 == 4) {
            return var1;
         }

         int var4 = this.data[var2] & 255;
         int var5 = var2 * 8;
         int var6 = var4 << var5;
         var1 |= var6;
         ++var2;
      }
   }

   public String toString() {
      return this.getString();
   }
}
