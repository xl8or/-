package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.Signature;
import myorg.bouncycastle.asn1.ocsp.TBSRequest;

public class OCSPRequest extends ASN1Encodable {

   Signature optionalSignature;
   TBSRequest tbsRequest;


   public OCSPRequest(ASN1Sequence var1) {
      TBSRequest var2 = TBSRequest.getInstance(var1.getObjectAt(0));
      this.tbsRequest = var2;
      if(var1.size() == 2) {
         Signature var3 = Signature.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)1);
         this.optionalSignature = var3;
      }
   }

   public OCSPRequest(TBSRequest var1, Signature var2) {
      this.tbsRequest = var1;
      this.optionalSignature = var2;
   }

   public static OCSPRequest getInstance(Object var0) {
      OCSPRequest var1;
      if(var0 != null && !(var0 instanceof OCSPRequest)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OCSPRequest(var2);
      } else {
         var1 = (OCSPRequest)var0;
      }

      return var1;
   }

   public static OCSPRequest getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public Signature getOptionalSignature() {
      return this.optionalSignature;
   }

   public TBSRequest getTbsRequest() {
      return this.tbsRequest;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      TBSRequest var2 = this.tbsRequest;
      var1.add(var2);
      if(this.optionalSignature != null) {
         Signature var3 = this.optionalSignature;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
