package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.CMPCertificate;
import myorg.bouncycastle.asn1.cmp.CertifiedKeyPair;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;

public class KeyRecRepContent extends ASN1Encodable {

   private ASN1Sequence caCerts;
   private ASN1Sequence keyPairHist;
   private CMPCertificate newSigCert;
   private PKIStatusInfo status;


   private KeyRecRepContent(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      PKIStatusInfo var3 = PKIStatusInfo.getInstance(var2.nextElement());
      this.status = var3;

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var4 = ASN1TaggedObject.getInstance(var2.nextElement());
         switch(var4.getTagNo()) {
         case 0:
            CMPCertificate var8 = CMPCertificate.getInstance(var4.getObject());
            this.newSigCert = var8;
            break;
         case 1:
            ASN1Sequence var9 = ASN1Sequence.getInstance(var4.getObject());
            this.caCerts = var9;
            break;
         case 2:
            ASN1Sequence var10 = ASN1Sequence.getInstance(var4.getObject());
            this.keyPairHist = var10;
            break;
         default:
            StringBuilder var5 = (new StringBuilder()).append("unknown tag number: ");
            int var6 = var4.getTagNo();
            String var7 = var5.append(var6).toString();
            throw new IllegalArgumentException(var7);
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, var2, var3);
         var1.add(var4);
      }
   }

   public static KeyRecRepContent getInstance(Object var0) {
      KeyRecRepContent var1;
      if(var0 instanceof KeyRecRepContent) {
         var1 = (KeyRecRepContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new KeyRecRepContent(var2);
      }

      return var1;
   }

   public CMPCertificate[] getCaCerts() {
      CMPCertificate[] var1;
      if(this.caCerts == null) {
         var1 = null;
      } else {
         CMPCertificate[] var2 = new CMPCertificate[this.caCerts.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            CMPCertificate var5 = CMPCertificate.getInstance(this.caCerts.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public CertifiedKeyPair[] getKeyPairHist() {
      CertifiedKeyPair[] var1;
      if(this.keyPairHist == null) {
         var1 = null;
      } else {
         CertifiedKeyPair[] var2 = new CertifiedKeyPair[this.keyPairHist.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 == var4) {
               var1 = var2;
               break;
            }

            CertifiedKeyPair var5 = CertifiedKeyPair.getInstance(this.keyPairHist.getObjectAt(var3));
            var2[var3] = var5;
            ++var3;
         }
      }

      return var1;
   }

   public CMPCertificate getNewSigCert() {
      return this.newSigCert;
   }

   public PKIStatusInfo getStatus() {
      return this.status;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIStatusInfo var2 = this.status;
      var1.add(var2);
      CMPCertificate var3 = this.newSigCert;
      this.addOptional(var1, 0, var3);
      ASN1Sequence var4 = this.caCerts;
      this.addOptional(var1, 1, var4);
      ASN1Sequence var5 = this.keyPairHist;
      this.addOptional(var1, 2, var5);
      return new DERSequence(var1);
   }
}
