package myorg.bouncycastle.asn1.misc;

import myorg.bouncycastle.asn1.DERBitString;

public class NetscapeCertType extends DERBitString {

   public static final int objectSigning = 16;
   public static final int objectSigningCA = 1;
   public static final int reserved = 8;
   public static final int smime = 32;
   public static final int smimeCA = 2;
   public static final int sslCA = 4;
   public static final int sslClient = 128;
   public static final int sslServer = 64;


   public NetscapeCertType(int var1) {
      byte[] var2 = getBytes(var1);
      int var3 = getPadBits(var1);
      super(var2, var3);
   }

   public NetscapeCertType(DERBitString var1) {
      byte[] var2 = var1.getBytes();
      int var3 = var1.getPadBits();
      super(var2, var3);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("NetscapeCertType: 0x");
      String var2 = Integer.toHexString(this.data[0] & 255);
      return var1.append(var2).toString();
   }
}
