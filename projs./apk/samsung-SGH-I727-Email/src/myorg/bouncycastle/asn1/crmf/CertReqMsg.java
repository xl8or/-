package myorg.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.crmf.AttributeTypeAndValue;
import myorg.bouncycastle.asn1.crmf.CertRequest;
import myorg.bouncycastle.asn1.crmf.ProofOfPossession;

public class CertReqMsg extends ASN1Encodable {

   private CertRequest certReq;
   private ProofOfPossession pop;
   private ASN1Sequence regInfo;


   private CertReqMsg(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      CertRequest var3 = CertRequest.getInstance(var2.nextElement());
      this.certReq = var3;

      while(var2.hasMoreElements()) {
         Object var4 = var2.nextElement();
         if(var4 instanceof ASN1TaggedObject) {
            ProofOfPossession var5 = ProofOfPossession.getInstance(var4);
            this.pop = var5;
         } else {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var4);
            this.regInfo = var6;
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, ASN1Encodable var2) {
      if(var2 != null) {
         var1.add(var2);
      }
   }

   public static CertReqMsg getInstance(Object var0) {
      CertReqMsg var1;
      if(var0 instanceof CertReqMsg) {
         var1 = (CertReqMsg)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertReqMsg(var2);
      }

      return var1;
   }

   public CertRequest getCertReq() {
      return this.certReq;
   }

   public ProofOfPossession getPop() {
      return this.pop;
   }

   public AttributeTypeAndValue[] getRegInfo() {
      AttributeTypeAndValue[] var1;
      if(this.regInfo == null) {
         var1 = null;
      } else {
         AttributeTypeAndValue[] var2 = new AttributeTypeAndValue[this.regInfo.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            AttributeTypeAndValue var5 = AttributeTypeAndValue.getInstance(this.regInfo.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertRequest var2 = this.certReq;
      var1.add(var2);
      ProofOfPossession var3 = this.pop;
      this.addOptional(var1, var3);
      ASN1Sequence var4 = this.regInfo;
      this.addOptional(var1, var4);
      return new DERSequence(var1);
   }
}
