package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class AccessDescription extends ASN1Encodable {

   public static final DERObjectIdentifier id_ad_caIssuers = new DERObjectIdentifier("1.3.6.1.5.5.7.48.2");
   public static final DERObjectIdentifier id_ad_ocsp = new DERObjectIdentifier("1.3.6.1.5.5.7.48.1");
   GeneralName accessLocation = null;
   DERObjectIdentifier accessMethod = null;


   public AccessDescription(ASN1Sequence var1) {
      if(var1.size() != 2) {
         throw new IllegalArgumentException("wrong number of elements in sequence");
      } else {
         DERObjectIdentifier var2 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.accessMethod = var2;
         GeneralName var3 = GeneralName.getInstance(var1.getObjectAt(1));
         this.accessLocation = var3;
      }
   }

   public AccessDescription(DERObjectIdentifier var1, GeneralName var2) {
      this.accessMethod = var1;
      this.accessLocation = var2;
   }

   public static AccessDescription getInstance(Object var0) {
      AccessDescription var1;
      if(var0 instanceof AccessDescription) {
         var1 = (AccessDescription)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AccessDescription(var2);
      }

      return var1;
   }

   public GeneralName getAccessLocation() {
      return this.accessLocation;
   }

   public DERObjectIdentifier getAccessMethod() {
      return this.accessMethod;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.accessMethod;
      var1.add(var2);
      GeneralName var3 = this.accessLocation;
      var1.add(var3);
      return new DERSequence(var1);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("AccessDescription: Oid(");
      String var2 = this.accessMethod.getId();
      return var1.append(var2).append(")").toString();
   }
}
