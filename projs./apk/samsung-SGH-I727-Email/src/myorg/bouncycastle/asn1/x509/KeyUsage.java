package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class KeyUsage extends DERBitString {

   public static final int cRLSign = 2;
   public static final int dataEncipherment = 16;
   public static final int decipherOnly = 32768;
   public static final int digitalSignature = 128;
   public static final int encipherOnly = 1;
   public static final int keyAgreement = 8;
   public static final int keyCertSign = 4;
   public static final int keyEncipherment = 32;
   public static final int nonRepudiation = 64;


   public KeyUsage(int var1) {
      byte[] var2 = getBytes(var1);
      int var3 = getPadBits(var1);
      super(var2, var3);
   }

   public KeyUsage(DERBitString var1) {
      byte[] var2 = var1.getBytes();
      int var3 = var1.getPadBits();
      super(var2, var3);
   }

   public static DERBitString getInstance(Object var0) {
      KeyUsage var1;
      if(var0 instanceof KeyUsage) {
         var1 = (KeyUsage)var0;
      } else if(var0 instanceof X509Extension) {
         DERBitString var2 = DERBitString.getInstance(X509Extension.convertValueToObject((X509Extension)var0));
         var1 = new KeyUsage(var2);
      } else {
         DERBitString var3 = DERBitString.getInstance(var0);
         var1 = new KeyUsage(var3);
      }

      return var1;
   }

   public String toString() {
      String var3;
      if(this.data.length == 1) {
         StringBuilder var1 = (new StringBuilder()).append("KeyUsage: 0x");
         String var2 = Integer.toHexString(this.data[0] & 255);
         var3 = var1.append(var2).toString();
      } else {
         StringBuilder var4 = (new StringBuilder()).append("KeyUsage: 0x");
         int var5 = (this.data[1] & 255) << 8;
         int var6 = this.data[0] & 255;
         String var7 = Integer.toHexString(var5 | var6);
         var3 = var4.append(var7).toString();
      }

      return var3;
   }
}
