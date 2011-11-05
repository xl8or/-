package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.ess.ESSCertIDv2;
import myorg.bouncycastle.asn1.x509.PolicyInformation;

public class SigningCertificateV2 extends ASN1Encodable {

   ASN1Sequence certs;
   ASN1Sequence policies;


   public SigningCertificateV2(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         ASN1Sequence var5 = ASN1Sequence.getInstance(var1.getObjectAt(0));
         this.certs = var5;
         if(var1.size() > 1) {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getObjectAt(1));
            this.policies = var6;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public SigningCertificateV2(ESSCertIDv2[] var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            DERSequence var6 = new DERSequence(var2);
            this.certs = var6;
            return;
         }

         ESSCertIDv2 var5 = var1[var3];
         var2.add(var5);
         ++var3;
      }
   }

   public SigningCertificateV2(ESSCertIDv2[] var1, PolicyInformation[] var2) {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            DERSequence var7 = new DERSequence(var3);
            this.certs = var7;
            if(var2 == null) {
               return;
            } else {
               var3 = new ASN1EncodableVector();
               byte var12 = 0;

               while(true) {
                  int var8 = var2.length;
                  if(var12 >= var8) {
                     DERSequence var11 = new DERSequence(var3);
                     this.policies = var11;
                     return;
                  }

                  PolicyInformation var9 = var2[var12];
                  var3.add(var9);
                  int var10 = var12 + 1;
               }
            }
         }

         ESSCertIDv2 var6 = var1[var4];
         var3.add(var6);
         ++var4;
      }
   }

   public static SigningCertificateV2 getInstance(Object var0) {
      SigningCertificateV2 var1;
      if(var0 != null && !(var0 instanceof SigningCertificateV2)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SigningCertificateV2\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SigningCertificateV2(var2);
      } else {
         var1 = (SigningCertificateV2)var0;
      }

      return var1;
   }

   public ESSCertIDv2[] getCerts() {
      ESSCertIDv2[] var1 = new ESSCertIDv2[this.certs.size()];
      int var2 = 0;

      while(true) {
         int var3 = this.certs.size();
         if(var2 == var3) {
            return var1;
         }

         ESSCertIDv2 var4 = ESSCertIDv2.getInstance(this.certs.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }

   public PolicyInformation[] getPolicies() {
      PolicyInformation[] var1;
      if(this.policies == null) {
         var1 = null;
      } else {
         PolicyInformation[] var2 = new PolicyInformation[this.policies.size()];
         int var3 = 0;

         while(true) {
            int var4 = this.policies.size();
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            PolicyInformation var5 = PolicyInformation.getInstance(this.policies.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1Sequence var2 = this.certs;
      var1.add(var2);
      if(this.policies != null) {
         ASN1Sequence var3 = this.policies;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
