package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.crmf.CertTemplate;
import myorg.bouncycastle.asn1.crmf.Controls;

public class CertRequest extends ASN1Encodable {

   private DERInteger certReqId;
   private CertTemplate certTemplate;
   private Controls controls;


   private CertRequest(ASN1Sequence var1) {
      DERInteger var2 = DERInteger.getInstance(var1.getObjectAt(0));
      this.certReqId = var2;
      CertTemplate var3 = CertTemplate.getInstance(var1.getObjectAt(1));
      this.certTemplate = var3;
      if(var1.size() > 2) {
         Controls var4 = Controls.getInstance(var1.getObjectAt(2));
         this.controls = var4;
      }
   }

   public static CertRequest getInstance(Object var0) {
      CertRequest var1;
      if(var0 instanceof CertRequest) {
         var1 = (CertRequest)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertRequest(var2);
      }

      return var1;
   }

   public DERInteger getCertReqId() {
      return this.certReqId;
   }

   public CertTemplate getCertTemplate() {
      return this.certTemplate;
   }

   public Controls getControls() {
      return this.controls;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.certReqId;
      var1.add(var2);
      CertTemplate var3 = this.certTemplate;
      var1.add(var3);
      if(this.controls != null) {
         Controls var4 = this.controls;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
