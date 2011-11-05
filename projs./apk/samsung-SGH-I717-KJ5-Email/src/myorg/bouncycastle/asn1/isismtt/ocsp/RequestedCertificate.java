package myorg.bouncycastle.asn1.isismtt.ocsp;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;

public class RequestedCertificate extends ASN1Encodable implements ASN1Choice {

   public static final int attributeCertificate = 1;
   public static final int certificate = 255;
   public static final int publicKeyCertificate;
   private byte[] attributeCert;
   private X509CertificateStructure cert;
   private byte[] publicKeyCert;


   public RequestedCertificate(int var1, byte[] var2) {
      DEROctetString var3 = new DEROctetString(var2);
      DERTaggedObject var4 = new DERTaggedObject(var1, var3);
      this((ASN1TaggedObject)var4);
   }

   private RequestedCertificate(ASN1TaggedObject var1) {
      if(var1.getTagNo() == 0) {
         byte[] var2 = ASN1OctetString.getInstance(var1, (boolean)1).getOctets();
         this.publicKeyCert = var2;
      } else if(var1.getTagNo() == 1) {
         byte[] var3 = ASN1OctetString.getInstance(var1, (boolean)1).getOctets();
         this.attributeCert = var3;
      } else {
         StringBuilder var4 = (new StringBuilder()).append("unknown tag number: ");
         int var5 = var1.getTagNo();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      }
   }

   public RequestedCertificate(X509CertificateStructure var1) {
      this.cert = var1;
   }

   public static RequestedCertificate getInstance(Object var0) {
      RequestedCertificate var1;
      if(var0 != null && !(var0 instanceof RequestedCertificate)) {
         if(var0 instanceof ASN1Sequence) {
            X509CertificateStructure var2 = X509CertificateStructure.getInstance(var0);
            var1 = new RequestedCertificate(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var4 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var5 = var0.getClass().getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }

            ASN1TaggedObject var3 = (ASN1TaggedObject)var0;
            var1 = new RequestedCertificate(var3);
         }
      } else {
         var1 = (RequestedCertificate)var0;
      }

      return var1;
   }

   public static RequestedCertificate getInstance(ASN1TaggedObject var0, boolean var1) {
      if(!var1) {
         throw new IllegalArgumentException("choice item must be explicitly tagged");
      } else {
         return getInstance(var0.getObject());
      }
   }

   public byte[] getCertificateBytes() {
      byte[] var2;
      if(this.cert != null) {
         byte[] var1;
         try {
            var1 = this.cert.getEncoded();
         } catch (IOException var5) {
            String var4 = "can\'t decode certificate: " + var5;
            throw new IllegalStateException(var4);
         }

         var2 = var1;
      } else if(this.publicKeyCert != null) {
         var2 = this.publicKeyCert;
      } else {
         var2 = this.attributeCert;
      }

      return var2;
   }

   public int getType() {
      byte var1;
      if(this.cert != null) {
         var1 = -1;
      } else if(this.publicKeyCert != null) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      Object var3;
      if(this.publicKeyCert != null) {
         byte[] var1 = this.publicKeyCert;
         DEROctetString var2 = new DEROctetString(var1);
         var3 = new DERTaggedObject(0, var2);
      } else if(this.attributeCert != null) {
         byte[] var4 = this.attributeCert;
         DEROctetString var5 = new DEROctetString(var4);
         var3 = new DERTaggedObject(1, var5);
      } else {
         var3 = this.cert.getDERObject();
      }

      return (DERObject)var3;
   }
}
