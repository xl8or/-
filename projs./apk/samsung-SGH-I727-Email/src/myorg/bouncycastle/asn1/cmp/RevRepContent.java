package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;
import myorg.bouncycastle.asn1.crmf.CertId;
import myorg.bouncycastle.asn1.x509.CertificateList;

public class RevRepContent extends ASN1Encodable {

   private ASN1Sequence crls;
   private ASN1Sequence revCerts;
   private ASN1Sequence status;


   private RevRepContent(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      ASN1Sequence var3 = ASN1Sequence.getInstance(var2.nextElement());
      this.status = var3;

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var4 = ASN1TaggedObject.getInstance(var2.nextElement());
         if(var4.getTagNo() == 0) {
            ASN1Sequence var5 = ASN1Sequence.getInstance(var4, (boolean)1);
            this.revCerts = var5;
         } else {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var4, (boolean)1);
            this.crls = var6;
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, var2, var3);
         var1.add(var4);
      }
   }

   public static RevRepContent getInstance(Object var0) {
      RevRepContent var1;
      if(var0 instanceof RevRepContent) {
         var1 = (RevRepContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RevRepContent(var2);
      }

      return var1;
   }

   public CertificateList[] getCrls() {
      CertificateList[] var1;
      if(this.crls == null) {
         var1 = null;
      } else {
         CertificateList[] var2 = new CertificateList[this.crls.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            CertificateList var5 = CertificateList.getInstance(this.crls.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public CertId[] getRevCerts() {
      CertId[] var1;
      if(this.revCerts == null) {
         var1 = null;
      } else {
         CertId[] var2 = new CertId[this.revCerts.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            CertId var5 = CertId.getInstance(this.revCerts.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public PKIStatusInfo[] getStatus() {
      PKIStatusInfo[] var1 = new PKIStatusInfo[this.status.size()];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         PKIStatusInfo var4 = PKIStatusInfo.getInstance(this.status.getObjectAt(var2));
         var1[var2] = var4;
         ++var2;
      }
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1Sequence var2 = this.status;
      var1.add(var2);
      ASN1Sequence var3 = this.revCerts;
      this.addOptional(var1, 0, var3);
      ASN1Sequence var4 = this.crls;
      this.addOptional(var1, 1, var4);
      return new DERSequence(var1);
   }
}
