package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.KEKRecipientInfo;
import myorg.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import myorg.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import myorg.bouncycastle.asn1.cms.OtherRecipientInfo;
import myorg.bouncycastle.asn1.cms.PasswordRecipientInfo;

public class RecipientInfo extends ASN1Encodable implements ASN1Choice {

   DEREncodable info;


   public RecipientInfo(DERObject var1) {
      this.info = var1;
   }

   public RecipientInfo(KEKRecipientInfo var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 2, var1);
      this.info = var2;
   }

   public RecipientInfo(KeyAgreeRecipientInfo var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 1, var1);
      this.info = var2;
   }

   public RecipientInfo(KeyTransRecipientInfo var1) {
      this.info = var1;
   }

   public RecipientInfo(OtherRecipientInfo var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 4, var1);
      this.info = var2;
   }

   public RecipientInfo(PasswordRecipientInfo var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 3, var1);
      this.info = var2;
   }

   public static RecipientInfo getInstance(Object var0) {
      RecipientInfo var1;
      if(var0 != null && !(var0 instanceof RecipientInfo)) {
         if(var0 instanceof ASN1Sequence) {
            ASN1Sequence var2 = (ASN1Sequence)var0;
            var1 = new RecipientInfo(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var4 = (new StringBuilder()).append("unknown object in factory: ");
               String var5 = var0.getClass().getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }

            ASN1TaggedObject var3 = (ASN1TaggedObject)var0;
            var1 = new RecipientInfo(var3);
         }
      } else {
         var1 = (RecipientInfo)var0;
      }

      return var1;
   }

   private KEKRecipientInfo getKEKInfo(ASN1TaggedObject var1) {
      KEKRecipientInfo var2;
      if(var1.isExplicit()) {
         var2 = KEKRecipientInfo.getInstance(var1, (boolean)1);
      } else {
         var2 = KEKRecipientInfo.getInstance(var1, (boolean)0);
      }

      return var2;
   }

   public DEREncodable getInfo() {
      Object var2;
      if(this.info instanceof ASN1TaggedObject) {
         ASN1TaggedObject var1 = (ASN1TaggedObject)this.info;
         switch(var1.getTagNo()) {
         case 1:
            var2 = KeyAgreeRecipientInfo.getInstance(var1, (boolean)0);
            break;
         case 2:
            var2 = this.getKEKInfo(var1);
            break;
         case 3:
            var2 = PasswordRecipientInfo.getInstance(var1, (boolean)0);
            break;
         case 4:
            var2 = OtherRecipientInfo.getInstance(var1, (boolean)0);
            break;
         default:
            throw new IllegalStateException("unknown tag");
         }
      } else {
         var2 = KeyTransRecipientInfo.getInstance(this.info);
      }

      return (DEREncodable)var2;
   }

   public DERInteger getVersion() {
      DERInteger var2;
      if(this.info instanceof ASN1TaggedObject) {
         ASN1TaggedObject var1 = (ASN1TaggedObject)this.info;
         switch(var1.getTagNo()) {
         case 1:
            var2 = KeyAgreeRecipientInfo.getInstance(var1, (boolean)0).getVersion();
            break;
         case 2:
            var2 = this.getKEKInfo(var1).getVersion();
            break;
         case 3:
            var2 = PasswordRecipientInfo.getInstance(var1, (boolean)0).getVersion();
            break;
         case 4:
            var2 = new DERInteger(0);
            break;
         default:
            throw new IllegalStateException("unknown tag");
         }
      } else {
         var2 = KeyTransRecipientInfo.getInstance(this.info).getVersion();
      }

      return var2;
   }

   public boolean isTagged() {
      return this.info instanceof ASN1TaggedObject;
   }

   public DERObject toASN1Object() {
      return this.info.getDERObject();
   }
}
