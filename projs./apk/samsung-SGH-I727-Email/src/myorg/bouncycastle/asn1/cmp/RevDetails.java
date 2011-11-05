package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.crmf.CertTemplate;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class RevDetails extends ASN1Encodable {

   private CertTemplate certDetails;
   private X509Extensions crlEntryDetails;


   private RevDetails(ASN1Sequence var1) {
      CertTemplate var2 = CertTemplate.getInstance(var1.getObjectAt(0));
      this.certDetails = var2;
      if(var1.size() > 1) {
         X509Extensions var3 = X509Extensions.getInstance(var1.getObjectAt(1));
         this.crlEntryDetails = var3;
      }
   }

   public static RevDetails getInstance(Object var0) {
      RevDetails var1;
      if(var0 instanceof RevDetails) {
         var1 = (RevDetails)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RevDetails(var2);
      }

      return var1;
   }

   public CertTemplate getCertDetails() {
      return this.certDetails;
   }

   public X509Extensions getCrlEntryDetails() {
      return this.crlEntryDetails;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertTemplate var2 = this.certDetails;
      var1.add(var2);
      if(this.crlEntryDetails != null) {
         X509Extensions var3 = this.crlEntryDetails;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
