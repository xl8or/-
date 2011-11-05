package myorg.bouncycastle.asn1.tsp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;
import myorg.bouncycastle.asn1.cms.ContentInfo;

public class TimeStampResp extends ASN1Encodable {

   PKIStatusInfo pkiStatusInfo;
   ContentInfo timeStampToken;


   public TimeStampResp(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      PKIStatusInfo var3 = PKIStatusInfo.getInstance(var2.nextElement());
      this.pkiStatusInfo = var3;
      if(var2.hasMoreElements()) {
         ContentInfo var4 = ContentInfo.getInstance(var2.nextElement());
         this.timeStampToken = var4;
      }
   }

   public TimeStampResp(PKIStatusInfo var1, ContentInfo var2) {
      this.pkiStatusInfo = var1;
      this.timeStampToken = var2;
   }

   public static TimeStampResp getInstance(Object var0) {
      TimeStampResp var1;
      if(var0 != null && !(var0 instanceof TimeStampResp)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'TimeStampResp\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TimeStampResp(var2);
      } else {
         var1 = (TimeStampResp)var0;
      }

      return var1;
   }

   public PKIStatusInfo getStatus() {
      return this.pkiStatusInfo;
   }

   public ContentInfo getTimeStampToken() {
      return this.timeStampToken;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIStatusInfo var2 = this.pkiStatusInfo;
      var1.add(var2);
      if(this.timeStampToken != null) {
         ContentInfo var3 = this.timeStampToken;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
