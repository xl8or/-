package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.CMPCertificate;

public class CAKeyUpdAnnContent extends ASN1Encodable {

   private CMPCertificate newWithNew;
   private CMPCertificate newWithOld;
   private CMPCertificate oldWithNew;


   private CAKeyUpdAnnContent(ASN1Sequence var1) {
      CMPCertificate var2 = CMPCertificate.getInstance(var1.getObjectAt(0));
      this.oldWithNew = var2;
      CMPCertificate var3 = CMPCertificate.getInstance(var1.getObjectAt(1));
      this.newWithOld = var3;
      CMPCertificate var4 = CMPCertificate.getInstance(var1.getObjectAt(2));
      this.newWithNew = var4;
   }

   public static CAKeyUpdAnnContent getInstance(Object var0) {
      CAKeyUpdAnnContent var1;
      if(var0 instanceof CAKeyUpdAnnContent) {
         var1 = (CAKeyUpdAnnContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CAKeyUpdAnnContent(var2);
      }

      return var1;
   }

   public CMPCertificate getNewWithNew() {
      return this.newWithNew;
   }

   public CMPCertificate getNewWithOld() {
      return this.newWithOld;
   }

   public CMPCertificate getOldWithNew() {
      return this.oldWithNew;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CMPCertificate var2 = this.oldWithNew;
      var1.add(var2);
      CMPCertificate var3 = this.newWithOld;
      var1.add(var3);
      CMPCertificate var4 = this.newWithNew;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
