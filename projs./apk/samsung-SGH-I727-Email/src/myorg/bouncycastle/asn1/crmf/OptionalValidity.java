package myorg.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.Time;

public class OptionalValidity extends ASN1Encodable {

   private Time notAfter;
   private Time notBefore;


   private OptionalValidity(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         if(var3.getTagNo() == 0) {
            Time var4 = Time.getInstance(var3, (boolean)1);
            this.notBefore = var4;
         } else {
            Time var5 = Time.getInstance(var3, (boolean)1);
            this.notAfter = var5;
         }
      }

   }

   public static OptionalValidity getInstance(Object var0) {
      OptionalValidity var1;
      if(var0 instanceof OptionalValidity) {
         var1 = (OptionalValidity)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OptionalValidity(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.notBefore != null) {
         Time var2 = this.notBefore;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 0, var2);
         var1.add(var3);
      }

      if(this.notAfter != null) {
         Time var4 = this.notAfter;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
