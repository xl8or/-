package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.CertifiedKeyPair;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;

public class CertResponse extends ASN1Encodable {

   private DERInteger certReqId;
   private CertifiedKeyPair certifiedKeyPair;
   private ASN1OctetString rspInfo;
   private PKIStatusInfo status;


   private CertResponse(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.certReqId = var2;
      PKIStatusInfo var3 = PKIStatusInfo.getInstance(var1.getObjectAt(1));
      this.status = var3;
      if(var1.size() >= 3) {
         if(var1.size() == 3) {
            DEREncodable var4 = var1.getObjectAt(2);
            if(var4 instanceof ASN1OctetString) {
               ASN1OctetString var5 = ASN1OctetString.getInstance(var4);
               this.rspInfo = var5;
            } else {
               CertifiedKeyPair var6 = CertifiedKeyPair.getInstance(var4);
               this.certifiedKeyPair = var6;
            }
         } else {
            CertifiedKeyPair var7 = CertifiedKeyPair.getInstance(var1.getObjectAt(2));
            this.certifiedKeyPair = var7;
            ASN1OctetString var8 = ASN1OctetString.getInstance(var1.getObjectAt(3));
            this.rspInfo = var8;
         }
      }
   }

   public static CertResponse getInstance(Object var0) {
      CertResponse var1;
      if(var0 instanceof CertResponse) {
         var1 = (CertResponse)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertResponse(var2);
      }

      return var1;
   }

   public DERInteger getCertReqId() {
      return this.certReqId;
   }

   public CertifiedKeyPair getCertifiedKeyPair() {
      return this.certifiedKeyPair;
   }

   public PKIStatusInfo getStatus() {
      return this.status;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.certReqId;
      var1.add(var2);
      PKIStatusInfo var3 = this.status;
      var1.add(var3);
      if(this.certifiedKeyPair != null) {
         CertifiedKeyPair var4 = this.certifiedKeyPair;
         var1.add(var4);
      }

      if(this.rspInfo != null) {
         ASN1OctetString var5 = this.rspInfo;
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
