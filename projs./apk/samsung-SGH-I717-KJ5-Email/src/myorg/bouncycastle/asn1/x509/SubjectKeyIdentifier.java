package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.crypto.digests.SHA1Digest;

public class SubjectKeyIdentifier extends ASN1Encodable {

   private byte[] keyidentifier;


   public SubjectKeyIdentifier(ASN1OctetString var1) {
      byte[] var2 = var1.getOctets();
      this.keyidentifier = var2;
   }

   public SubjectKeyIdentifier(SubjectPublicKeyInfo var1) {
      byte[] var2 = getDigest(var1);
      this.keyidentifier = var2;
   }

   public SubjectKeyIdentifier(byte[] var1) {
      this.keyidentifier = var1;
   }

   public static SubjectKeyIdentifier createSHA1KeyIdentifier(SubjectPublicKeyInfo var0) {
      return new SubjectKeyIdentifier(var0);
   }

   public static SubjectKeyIdentifier createTruncatedSHA1KeyIdentifier(SubjectPublicKeyInfo var0) {
      byte[] var1 = getDigest(var0);
      byte[] var2 = new byte[8];
      int var3 = var1.length - 8;
      int var4 = var2.length;
      System.arraycopy(var1, var3, var2, 0, var4);
      byte var5 = (byte)(var2[0] & 15);
      var2[0] = var5;
      byte var6 = (byte)(var2[0] | 64);
      var2[0] = var6;
      return new SubjectKeyIdentifier(var2);
   }

   private static byte[] getDigest(SubjectPublicKeyInfo var0) {
      SHA1Digest var1 = new SHA1Digest();
      byte[] var2 = new byte[var1.getDigestSize()];
      byte[] var3 = var0.getPublicKeyData().getBytes();
      int var4 = var3.length;
      var1.update(var3, 0, var4);
      var1.doFinal(var2, 0);
      return var2;
   }

   public static SubjectKeyIdentifier getInstance(Object var0) {
      SubjectKeyIdentifier var1;
      if(var0 instanceof SubjectKeyIdentifier) {
         var1 = (SubjectKeyIdentifier)var0;
      } else if(var0 instanceof SubjectPublicKeyInfo) {
         SubjectPublicKeyInfo var2 = (SubjectPublicKeyInfo)var0;
         var1 = new SubjectKeyIdentifier(var2);
      } else if(var0 instanceof ASN1OctetString) {
         ASN1OctetString var3 = (ASN1OctetString)var0;
         var1 = new SubjectKeyIdentifier(var3);
      } else {
         if(!(var0 instanceof X509Extension)) {
            StringBuilder var4 = (new StringBuilder()).append("Invalid SubjectKeyIdentifier: ");
            String var5 = var0.getClass().getName();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }

         var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
      }

      return var1;
   }

   public static SubjectKeyIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1OctetString.getInstance(var0, var1));
   }

   public byte[] getKeyIdentifier() {
      return this.keyidentifier;
   }

   public DERObject toASN1Object() {
      byte[] var1 = this.keyidentifier;
      return new DEROctetString(var1);
   }
}
