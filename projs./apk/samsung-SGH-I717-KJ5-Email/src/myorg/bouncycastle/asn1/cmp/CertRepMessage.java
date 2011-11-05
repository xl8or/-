package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.CMPCertificate;
import myorg.bouncycastle.asn1.cmp.CertResponse;

public class CertRepMessage extends ASN1Encodable {

   private ASN1Sequence caPubs;
   private ASN1Sequence response;


   private CertRepMessage(ASN1Sequence var1) {
      int var2 = 0;
      if(var1.size() > 1) {
         int var3 = 0 + 1;
         ASN1Sequence var4 = ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(0), (boolean)1);
         this.caPubs = var4;
         var2 = var3;
      }

      ASN1Sequence var5 = ASN1Sequence.getInstance(var1.getObjectAt(var2));
      this.response = var5;
   }

   public static CertRepMessage getInstance(Object var0) {
      CertRepMessage var1;
      if(var0 instanceof CertRepMessage) {
         var1 = (CertRepMessage)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertRepMessage(var2);
      }

      return var1;
   }

   public CMPCertificate[] getCaPubs() {
      CMPCertificate[] var1;
      if(this.caPubs == null) {
         var1 = null;
      } else {
         CMPCertificate[] var2 = new CMPCertificate[this.caPubs.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            CMPCertificate var5 = CMPCertificate.getInstance(this.caPubs.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public CertResponse[] getResponse() {
      CertResponse[] var1 = new CertResponse[this.response.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         CertResponse var4 = CertResponse.getInstance(this.response.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.caPubs != null) {
         ASN1Sequence var2 = this.caPubs;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 1, var2);
         var1.add(var3);
      }

      ASN1Sequence var4 = this.response;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
