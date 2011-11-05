package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;

public class X509CertificateStructure extends ASN1Encodable implements X509ObjectIdentifiers, PKCSObjectIdentifiers {

   ASN1Sequence seq;
   DERBitString sig;
   AlgorithmIdentifier sigAlgId;
   TBSCertificateStructure tbsCert;


   public X509CertificateStructure(ASN1Sequence var1) {
      this.seq = var1;
      if(var1.size() == 3) {
         TBSCertificateStructure var2 = TBSCertificateStructure.getInstance(var1.getObjectAt(0));
         this.tbsCert = var2;
         AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
         this.sigAlgId = var3;
         DERBitString var4 = DERBitString.getInstance(var1.getObjectAt(2));
         this.sig = var4;
      } else {
         throw new IllegalArgumentException("sequence wrong size for a certificate");
      }
   }

   public static X509CertificateStructure getInstance(Object var0) {
      X509CertificateStructure var1;
      if(var0 instanceof X509CertificateStructure) {
         var1 = (X509CertificateStructure)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            if(var0 != null) {
               StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).toString();
               throw new IllegalArgumentException(var5);
            }

            throw new IllegalArgumentException("null object in factory");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new X509CertificateStructure(var2);
      }

      return var1;
   }

   public static X509CertificateStructure getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public Time getEndDate() {
      return this.tbsCert.getEndDate();
   }

   public X509Name getIssuer() {
      return this.tbsCert.getIssuer();
   }

   public DERInteger getSerialNumber() {
      return this.tbsCert.getSerialNumber();
   }

   public DERBitString getSignature() {
      return this.sig;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.sigAlgId;
   }

   public Time getStartDate() {
      return this.tbsCert.getStartDate();
   }

   public X509Name getSubject() {
      return this.tbsCert.getSubject();
   }

   public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
      return this.tbsCert.getSubjectPublicKeyInfo();
   }

   public TBSCertificateStructure getTBSCertificate() {
      return this.tbsCert;
   }

   public int getVersion() {
      return this.tbsCert.getVersion();
   }

   public DERObject toASN1Object() {
      return this.seq;
   }
}
