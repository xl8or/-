package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class ResponseBytes extends ASN1Encodable {

   ASN1OctetString response;
   DERObjectIdentifier responseType;


   public ResponseBytes(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.responseType = var2;
      ASN1OctetString var3 = (ASN1OctetString)var1.getObjectAt(1);
      this.response = var3;
   }

   public ResponseBytes(DERObjectIdentifier var1, ASN1OctetString var2) {
      this.responseType = var1;
      this.response = var2;
   }

   public static ResponseBytes getInstance(Object var0) {
      ResponseBytes var1;
      if(var0 != null && !(var0 instanceof ResponseBytes)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ResponseBytes(var2);
      } else {
         var1 = (ResponseBytes)var0;
      }

      return var1;
   }

   public static ResponseBytes getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1OctetString getResponse() {
      return this.response;
   }

   public DERObjectIdentifier getResponseType() {
      return this.responseType;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.responseType;
      var1.add(var2);
      ASN1OctetString var3 = this.response;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
