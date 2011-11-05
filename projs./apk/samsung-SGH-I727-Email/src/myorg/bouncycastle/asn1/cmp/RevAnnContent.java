package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIStatus;
import myorg.bouncycastle.asn1.crmf.CertId;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class RevAnnContent extends ASN1Encodable {

   private DERGeneralizedTime badSinceDate;
   private CertId certId;
   private X509Extensions crlDetails;
   private PKIStatus status;
   private DERGeneralizedTime willBeRevokedAt;


   private RevAnnContent(ASN1Sequence var1) {
      PKIStatus var2 = PKIStatus.getInstance(var1.getObjectAt(0));
      this.status = var2;
      CertId var3 = CertId.getInstance(var1.getObjectAt(1));
      this.certId = var3;
      DERGeneralizedTime var4 = DERGeneralizedTime.getInstance(var1.getObjectAt(2));
      this.willBeRevokedAt = var4;
      DERGeneralizedTime var5 = DERGeneralizedTime.getInstance(var1.getObjectAt(3));
      this.badSinceDate = var5;
      if(var1.size() > 4) {
         X509Extensions var6 = X509Extensions.getInstance(var1.getObjectAt(4));
         this.crlDetails = var6;
      }
   }

   public static RevAnnContent getInstance(Object var0) {
      RevAnnContent var1;
      if(var0 instanceof RevAnnContent) {
         var1 = (RevAnnContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RevAnnContent(var2);
      }

      return var1;
   }

   public DERGeneralizedTime getBadSinceDate() {
      return this.badSinceDate;
   }

   public CertId getCertId() {
      return this.certId;
   }

   public X509Extensions getCrlDetails() {
      return this.crlDetails;
   }

   public PKIStatus getStatus() {
      return this.status;
   }

   public DERGeneralizedTime getWillBeRevokedAt() {
      return this.willBeRevokedAt;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIStatus var2 = this.status;
      var1.add(var2);
      CertId var3 = this.certId;
      var1.add(var3);
      DERGeneralizedTime var4 = this.willBeRevokedAt;
      var1.add(var4);
      DERGeneralizedTime var5 = this.badSinceDate;
      var1.add(var5);
      if(this.crlDetails != null) {
         X509Extensions var6 = this.crlDetails;
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}
