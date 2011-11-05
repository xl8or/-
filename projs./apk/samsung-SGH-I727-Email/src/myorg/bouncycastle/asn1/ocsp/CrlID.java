package myorg.bouncycastle.asn1.ocsp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class CrlID extends ASN1Encodable {

   DERInteger crlNum;
   DERGeneralizedTime crlTime;
   DERIA5String crlUrl;


   public CrlID(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         switch(var3.getTagNo()) {
         case 0:
            DERIA5String var7 = DERIA5String.getInstance(var3, (boolean)1);
            this.crlUrl = var7;
            break;
         case 1:
            DERInteger var8 = DERInteger.getInstance(var3, (boolean)1);
            this.crlNum = var8;
            break;
         case 2:
            DERGeneralizedTime var9 = DERGeneralizedTime.getInstance(var3, (boolean)1);
            this.crlTime = var9;
            break;
         default:
            StringBuilder var4 = (new StringBuilder()).append("unknown tag number: ");
            int var5 = var3.getTagNo();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }
      }

   }

   public DERInteger getCrlNum() {
      return this.crlNum;
   }

   public DERGeneralizedTime getCrlTime() {
      return this.crlTime;
   }

   public DERIA5String getCrlUrl() {
      return this.crlUrl;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.crlUrl != null) {
         DERIA5String var2 = this.crlUrl;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 0, var2);
         var1.add(var3);
      }

      if(this.crlNum != null) {
         DERInteger var4 = this.crlNum;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 1, var4);
         var1.add(var5);
      }

      if(this.crlTime != null) {
         DERGeneralizedTime var6 = this.crlTime;
         DERTaggedObject var7 = new DERTaggedObject((boolean)1, 2, var6);
         var1.add(var7);
      }

      return new DERSequence(var1);
   }
}
