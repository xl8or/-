package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.V2Form;

public class AttCertIssuer extends ASN1Encodable implements ASN1Choice {

   DERObject choiceObj;
   ASN1Encodable obj;


   public AttCertIssuer(GeneralNames var1) {
      this.obj = var1;
      DERObject var2 = this.obj.getDERObject();
      this.choiceObj = var2;
   }

   public AttCertIssuer(V2Form var1) {
      this.obj = var1;
      ASN1Encodable var2 = this.obj;
      DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
      this.choiceObj = var3;
   }

   public static AttCertIssuer getInstance(Object var0) {
      AttCertIssuer var1;
      if(var0 instanceof AttCertIssuer) {
         var1 = (AttCertIssuer)var0;
      } else if(var0 instanceof V2Form) {
         V2Form var2 = V2Form.getInstance(var0);
         var1 = new AttCertIssuer(var2);
      } else if(var0 instanceof GeneralNames) {
         GeneralNames var3 = (GeneralNames)var0;
         var1 = new AttCertIssuer(var3);
      } else if(var0 instanceof ASN1TaggedObject) {
         V2Form var4 = V2Form.getInstance((ASN1TaggedObject)var0, (boolean)0);
         var1 = new AttCertIssuer(var4);
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var6 = (new StringBuilder()).append("unknown object in factory: ");
            String var7 = var0.getClass().getName();
            String var8 = var6.append(var7).toString();
            throw new IllegalArgumentException(var8);
         }

         GeneralNames var5 = GeneralNames.getInstance(var0);
         var1 = new AttCertIssuer(var5);
      }

      return var1;
   }

   public static AttCertIssuer getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public ASN1Encodable getIssuer() {
      return this.obj;
   }

   public DERObject toASN1Object() {
      return this.choiceObj;
   }
}
