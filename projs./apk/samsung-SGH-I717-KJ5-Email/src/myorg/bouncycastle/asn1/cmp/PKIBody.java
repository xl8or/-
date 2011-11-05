package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.CAKeyUpdAnnContent;
import myorg.bouncycastle.asn1.cmp.CMPCertificate;
import myorg.bouncycastle.asn1.cmp.CRLAnnContent;
import myorg.bouncycastle.asn1.cmp.CertConfirmContent;
import myorg.bouncycastle.asn1.cmp.CertRepMessage;
import myorg.bouncycastle.asn1.cmp.ErrorMsgContent;
import myorg.bouncycastle.asn1.cmp.GenMsgContent;
import myorg.bouncycastle.asn1.cmp.GenRepContent;
import myorg.bouncycastle.asn1.cmp.KeyRecRepContent;
import myorg.bouncycastle.asn1.cmp.PKIConfirmContent;
import myorg.bouncycastle.asn1.cmp.PKIMessages;
import myorg.bouncycastle.asn1.cmp.POPODecKeyChallContent;
import myorg.bouncycastle.asn1.cmp.POPODecKeyRespContent;
import myorg.bouncycastle.asn1.cmp.PollRepContent;
import myorg.bouncycastle.asn1.cmp.PollReqContent;
import myorg.bouncycastle.asn1.cmp.RevAnnContent;
import myorg.bouncycastle.asn1.cmp.RevRepContent;
import myorg.bouncycastle.asn1.cmp.RevReqContent;
import myorg.bouncycastle.asn1.crmf.CertReqMessages;
import myorg.bouncycastle.asn1.pkcs.CertificationRequest;

public class PKIBody extends ASN1Encodable implements ASN1Choice {

   private ASN1Encodable body;
   private int tagNo;


   private PKIBody(ASN1TaggedObject var1) {
      int var2 = var1.getTagNo();
      this.tagNo = var2;
      switch(var1.getTagNo()) {
      case 0:
         CertReqMessages var6 = CertReqMessages.getInstance(var1.getObject());
         this.body = var6;
         return;
      case 1:
         CertRepMessage var7 = CertRepMessage.getInstance(var1.getObject());
         this.body = var7;
         return;
      case 2:
         CertReqMessages var8 = CertReqMessages.getInstance(var1.getObject());
         this.body = var8;
         return;
      case 3:
         CertRepMessage var9 = CertRepMessage.getInstance(var1.getObject());
         this.body = var9;
         return;
      case 4:
         CertificationRequest var10 = CertificationRequest.getInstance(var1.getObject());
         this.body = var10;
         return;
      case 5:
         POPODecKeyChallContent var11 = POPODecKeyChallContent.getInstance(var1.getObject());
         this.body = var11;
         return;
      case 6:
         POPODecKeyRespContent var12 = POPODecKeyRespContent.getInstance(var1.getObject());
         this.body = var12;
         return;
      case 7:
         CertReqMessages var13 = CertReqMessages.getInstance(var1.getObject());
         this.body = var13;
         return;
      case 8:
         CertRepMessage var14 = CertRepMessage.getInstance(var1.getObject());
         this.body = var14;
         return;
      case 9:
         CertReqMessages var15 = CertReqMessages.getInstance(var1.getObject());
         this.body = var15;
         return;
      case 10:
         KeyRecRepContent var16 = KeyRecRepContent.getInstance(var1.getObject());
         this.body = var16;
         return;
      case 11:
         RevReqContent var17 = RevReqContent.getInstance(var1.getObject());
         this.body = var17;
         return;
      case 12:
         RevRepContent var18 = RevRepContent.getInstance(var1.getObject());
         this.body = var18;
         return;
      case 13:
         CertReqMessages var19 = CertReqMessages.getInstance(var1.getObject());
         this.body = var19;
         return;
      case 14:
         CertRepMessage var20 = CertRepMessage.getInstance(var1.getObject());
         this.body = var20;
         return;
      case 15:
         CAKeyUpdAnnContent var21 = CAKeyUpdAnnContent.getInstance(var1.getObject());
         this.body = var21;
         return;
      case 16:
         CMPCertificate var22 = CMPCertificate.getInstance(var1.getObject());
         this.body = var22;
         return;
      case 17:
         RevAnnContent var23 = RevAnnContent.getInstance(var1.getObject());
         this.body = var23;
         return;
      case 18:
         CRLAnnContent var24 = CRLAnnContent.getInstance(var1.getObject());
         this.body = var24;
         return;
      case 19:
         PKIConfirmContent var25 = PKIConfirmContent.getInstance(var1.getObject());
         this.body = var25;
         return;
      case 20:
         PKIMessages var26 = PKIMessages.getInstance(var1.getObject());
         this.body = var26;
         return;
      case 21:
         GenMsgContent var27 = GenMsgContent.getInstance(var1.getObject());
         this.body = var27;
         return;
      case 22:
         GenRepContent var28 = GenRepContent.getInstance(var1.getObject());
         this.body = var28;
         return;
      case 23:
         ErrorMsgContent var29 = ErrorMsgContent.getInstance(var1.getObject());
         this.body = var29;
         return;
      case 24:
         CertConfirmContent var30 = CertConfirmContent.getInstance(var1.getObject());
         this.body = var30;
         return;
      case 25:
         PollReqContent var31 = PollReqContent.getInstance(var1.getObject());
         this.body = var31;
         return;
      case 26:
         PollRepContent var32 = PollRepContent.getInstance(var1.getObject());
         this.body = var32;
         return;
      default:
         StringBuilder var3 = (new StringBuilder()).append("unknown tag number: ");
         int var4 = var1.getTagNo();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      }
   }

   public static PKIBody getInstance(Object var0) {
      PKIBody var1;
      if(var0 instanceof PKIBody) {
         var1 = (PKIBody)var0;
      } else {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new PKIBody(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      int var1 = this.tagNo;
      ASN1Encodable var2 = this.body;
      return new DERTaggedObject((boolean)1, var1, var2);
   }
}
