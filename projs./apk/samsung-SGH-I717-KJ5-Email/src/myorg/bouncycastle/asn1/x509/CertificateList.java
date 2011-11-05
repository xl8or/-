package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Name;

public class CertificateList extends ASN1Encodable {

   DERBitString sig;
   AlgorithmIdentifier sigAlgId;
   TBSCertList tbsCertList;


   public CertificateList(ASN1Sequence var1) {
      if(var1.size() == 3) {
         TBSCertList var2 = TBSCertList.getInstance(var1.getObjectAt(0));
         this.tbsCertList = var2;
         AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
         this.sigAlgId = var3;
         DERBitString var4 = DERBitString.getInstance(var1.getObjectAt(2));
         this.sig = var4;
      } else {
         throw new IllegalArgumentException("sequence wrong size for CertificateList");
      }
   }

   public static CertificateList getInstance(Object var0) {
      CertificateList var1;
      if(var0 instanceof CertificateList) {
         var1 = (CertificateList)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertificateList(var2);
      }

      return var1;
   }

   public static CertificateList getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public X509Name getIssuer() {
      return this.tbsCertList.getIssuer();
   }

   public Time getNextUpdate() {
      return this.tbsCertList.getNextUpdate();
   }

   public Enumeration getRevokedCertificateEnumeration() {
      return this.tbsCertList.getRevokedCertificateEnumeration();
   }

   public TBSCertList.CRLEntry[] getRevokedCertificates() {
      return this.tbsCertList.getRevokedCertificates();
   }

   public DERBitString getSignature() {
      return this.sig;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.sigAlgId;
   }

   public TBSCertList getTBSCertList() {
      return this.tbsCertList;
   }

   public Time getThisUpdate() {
      return this.tbsCertList.getThisUpdate();
   }

   public int getVersion() {
      return this.tbsCertList.getVersion();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      TBSCertList var2 = this.tbsCertList;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.sigAlgId;
      var1.add(var3);
      DERBitString var4 = this.sig;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
