package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import myorg.bouncycastle.asn1.ocsp.ResponseBytes;

public class OCSPResponse extends ASN1Encodable {

   ResponseBytes responseBytes;
   OCSPResponseStatus responseStatus;


   public OCSPResponse(ASN1Sequence var1) {
      DEREnumerated var2 = DEREnumerated.getInstance(var1.getObjectAt(0));
      OCSPResponseStatus var3 = new OCSPResponseStatus(var2);
      this.responseStatus = var3;
      if(var1.size() == 2) {
         ResponseBytes var4 = ResponseBytes.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)1);
         this.responseBytes = var4;
      }
   }

   public OCSPResponse(OCSPResponseStatus var1, ResponseBytes var2) {
      this.responseStatus = var1;
      this.responseBytes = var2;
   }

   public static OCSPResponse getInstance(Object var0) {
      OCSPResponse var1;
      if(var0 != null && !(var0 instanceof OCSPResponse)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OCSPResponse(var2);
      } else {
         var1 = (OCSPResponse)var0;
      }

      return var1;
   }

   public static OCSPResponse getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ResponseBytes getResponseBytes() {
      return this.responseBytes;
   }

   public OCSPResponseStatus getResponseStatus() {
      return this.responseStatus;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      OCSPResponseStatus var2 = this.responseStatus;
      var1.add(var2);
      if(this.responseBytes != null) {
         ResponseBytes var3 = this.responseBytes;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
