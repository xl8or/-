package myorg.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.crypto.digests.SHA1Digest;

public class AuthorityKeyIdentifier extends ASN1Encodable {

   GeneralNames certissuer = null;
   DERInteger certserno = null;
   ASN1OctetString keyidentifier = null;


   public AuthorityKeyIdentifier(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = DERTaggedObject.getInstance(var2.nextElement());
         switch(var3.getTagNo()) {
         case 0:
            ASN1OctetString var4 = ASN1OctetString.getInstance(var3, (boolean)0);
            this.keyidentifier = var4;
            break;
         case 1:
            GeneralNames var5 = GeneralNames.getInstance(var3, (boolean)0);
            this.certissuer = var5;
            break;
         case 2:
            DERInteger var6 = DERInteger.getInstance(var3, (boolean)0);
            this.certserno = var6;
            break;
         default:
            throw new IllegalArgumentException("illegal tag");
         }
      }

   }

   public AuthorityKeyIdentifier(GeneralNames var1, BigInteger var2) {
      this.keyidentifier = null;
      GeneralNames var3 = GeneralNames.getInstance(var1.toASN1Object());
      this.certissuer = var3;
      DERInteger var4 = new DERInteger(var2);
      this.certserno = var4;
   }

   public AuthorityKeyIdentifier(SubjectPublicKeyInfo var1) {
      SHA1Digest var2 = new SHA1Digest();
      byte[] var3 = new byte[var2.getDigestSize()];
      byte[] var4 = var1.getPublicKeyData().getBytes();
      int var5 = var4.length;
      var2.update(var4, 0, var5);
      var2.doFinal(var3, 0);
      DEROctetString var7 = new DEROctetString(var3);
      this.keyidentifier = var7;
   }

   public AuthorityKeyIdentifier(SubjectPublicKeyInfo var1, GeneralNames var2, BigInteger var3) {
      SHA1Digest var4 = new SHA1Digest();
      byte[] var5 = new byte[var4.getDigestSize()];
      byte[] var6 = var1.getPublicKeyData().getBytes();
      int var7 = var6.length;
      var4.update(var6, 0, var7);
      var4.doFinal(var5, 0);
      DEROctetString var9 = new DEROctetString(var5);
      this.keyidentifier = var9;
      GeneralNames var10 = GeneralNames.getInstance(var2.toASN1Object());
      this.certissuer = var10;
      DERInteger var11 = new DERInteger(var3);
      this.certserno = var11;
   }

   public AuthorityKeyIdentifier(byte[] var1) {
      DEROctetString var2 = new DEROctetString(var1);
      this.keyidentifier = var2;
      this.certissuer = null;
      this.certserno = null;
   }

   public AuthorityKeyIdentifier(byte[] var1, GeneralNames var2, BigInteger var3) {
      DEROctetString var4 = new DEROctetString(var1);
      this.keyidentifier = var4;
      GeneralNames var5 = GeneralNames.getInstance(var2.toASN1Object());
      this.certissuer = var5;
      DERInteger var6 = new DERInteger(var3);
      this.certserno = var6;
   }

   public static AuthorityKeyIdentifier getInstance(Object var0) {
      AuthorityKeyIdentifier var1;
      if(var0 instanceof AuthorityKeyIdentifier) {
         var1 = (AuthorityKeyIdentifier)var0;
      } else if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AuthorityKeyIdentifier(var2);
      } else {
         if(!(var0 instanceof X509Extension)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
      }

      return var1;
   }

   public static AuthorityKeyIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public GeneralNames getAuthorityCertIssuer() {
      return this.certissuer;
   }

   public BigInteger getAuthorityCertSerialNumber() {
      BigInteger var1;
      if(this.certserno != null) {
         var1 = this.certserno.getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   public byte[] getKeyIdentifier() {
      byte[] var1;
      if(this.keyidentifier != null) {
         var1 = this.keyidentifier.getOctets();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.keyidentifier != null) {
         ASN1OctetString var2 = this.keyidentifier;
         DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
         var1.add(var3);
      }

      if(this.certissuer != null) {
         GeneralNames var4 = this.certissuer;
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      if(this.certserno != null) {
         DERInteger var6 = this.certserno;
         DERTaggedObject var7 = new DERTaggedObject((boolean)0, 2, var6);
         var1.add(var7);
      }

      return new DERSequence(var1);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("AuthorityKeyIdentifier: KeyID(");
      byte[] var2 = this.keyidentifier.getOctets();
      return var1.append(var2).append(")").toString();
   }
}
