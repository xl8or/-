package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;

public class TBSCertificateStructure extends ASN1Encodable implements X509ObjectIdentifiers, PKCSObjectIdentifiers {

   Time endDate;
   X509Extensions extensions;
   X509Name issuer;
   DERBitString issuerUniqueId;
   ASN1Sequence seq;
   DERInteger serialNumber;
   AlgorithmIdentifier signature;
   Time startDate;
   X509Name subject;
   SubjectPublicKeyInfo subjectPublicKeyInfo;
   DERBitString subjectUniqueId;
   DERInteger version;


   public TBSCertificateStructure(ASN1Sequence var1) {
      byte var2 = 0;
      this.seq = var1;
      if(var1.getObjectAt(0) instanceof DERTaggedObject) {
         DERInteger var3 = DERInteger.getInstance(var1.getObjectAt(0));
         this.version = var3;
      } else {
         var2 = -1;
         DERInteger var23 = new DERInteger(0);
         this.version = var23;
      }

      int var4 = var2 + 1;
      DERInteger var5 = DERInteger.getInstance(var1.getObjectAt(var4));
      this.serialNumber = var5;
      int var6 = var2 + 2;
      AlgorithmIdentifier var7 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var6));
      this.signature = var7;
      int var8 = var2 + 3;
      X509Name var9 = X509Name.getInstance(var1.getObjectAt(var8));
      this.issuer = var9;
      int var10 = var2 + 4;
      ASN1Sequence var11 = (ASN1Sequence)var1.getObjectAt(var10);
      Time var12 = Time.getInstance(var11.getObjectAt(0));
      this.startDate = var12;
      Time var13 = Time.getInstance(var11.getObjectAt(1));
      this.endDate = var13;
      int var14 = var2 + 5;
      X509Name var15 = X509Name.getInstance(var1.getObjectAt(var14));
      this.subject = var15;
      int var16 = var2 + 6;
      SubjectPublicKeyInfo var17 = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(var16));
      this.subjectPublicKeyInfo = var17;
      int var18 = var1.size();
      int var19 = var2 + 6;

      for(int var20 = var18 - var19 - 1; var20 > 0; var20 += -1) {
         int var21 = var2 + 6 + var20;
         DERTaggedObject var22 = (DERTaggedObject)var1.getObjectAt(var21);
         switch(var22.getTagNo()) {
         case 1:
            DERBitString var24 = DERBitString.getInstance(var22, (boolean)0);
            this.issuerUniqueId = var24;
            break;
         case 2:
            DERBitString var25 = DERBitString.getInstance(var22, (boolean)0);
            this.subjectUniqueId = var25;
            break;
         case 3:
            X509Extensions var26 = X509Extensions.getInstance(var22);
            this.extensions = var26;
         }
      }

   }

   public static TBSCertificateStructure getInstance(Object var0) {
      TBSCertificateStructure var1;
      if(var0 instanceof TBSCertificateStructure) {
         var1 = (TBSCertificateStructure)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TBSCertificateStructure(var2);
      }

      return var1;
   }

   public static TBSCertificateStructure getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public Time getEndDate() {
      return this.endDate;
   }

   public X509Extensions getExtensions() {
      return this.extensions;
   }

   public X509Name getIssuer() {
      return this.issuer;
   }

   public DERBitString getIssuerUniqueId() {
      return this.issuerUniqueId;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public AlgorithmIdentifier getSignature() {
      return this.signature;
   }

   public Time getStartDate() {
      return this.startDate;
   }

   public X509Name getSubject() {
      return this.subject;
   }

   public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
      return this.subjectPublicKeyInfo;
   }

   public DERBitString getSubjectUniqueId() {
      return this.subjectUniqueId;
   }

   public int getVersion() {
      return this.version.getValue().intValue() + 1;
   }

   public DERInteger getVersionNumber() {
      return this.version;
   }

   public DERObject toASN1Object() {
      return this.seq;
   }
}
