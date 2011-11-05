package myorg.bouncycastle.jce;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.x509.KeyUsage;

public class X509KeyUsage extends ASN1Encodable {

   public static final int cRLSign = 2;
   public static final int dataEncipherment = 16;
   public static final int decipherOnly = 32768;
   public static final int digitalSignature = 128;
   public static final int encipherOnly = 1;
   public static final int keyAgreement = 8;
   public static final int keyCertSign = 4;
   public static final int keyEncipherment = 32;
   public static final int nonRepudiation = 64;
   private int usage = 0;


   public X509KeyUsage(int var1) {
      this.usage = var1;
   }

   public DERObject toASN1Object() {
      int var1 = this.usage;
      return new KeyUsage(var1);
   }
}
