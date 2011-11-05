package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.DisplayText;
import myorg.bouncycastle.asn1.x509.NoticeReference;

public class UserNotice extends ASN1Encodable {

   private DisplayText explicitText;
   private NoticeReference noticeRef;


   public UserNotice(ASN1Sequence var1) {
      if(var1.size() == 2) {
         NoticeReference var2 = NoticeReference.getInstance(var1.getObjectAt(0));
         this.noticeRef = var2;
         DisplayText var3 = DisplayText.getInstance(var1.getObjectAt(1));
         this.explicitText = var3;
      } else if(var1.size() == 1) {
         if(var1.getObjectAt(0).getDERObject() instanceof ASN1Sequence) {
            NoticeReference var4 = NoticeReference.getInstance(var1.getObjectAt(0));
            this.noticeRef = var4;
         } else {
            DisplayText var5 = DisplayText.getInstance(var1.getObjectAt(0));
            this.explicitText = var5;
         }
      } else {
         StringBuilder var6 = (new StringBuilder()).append("Bad sequence size: ");
         int var7 = var1.size();
         String var8 = var6.append(var7).toString();
         throw new IllegalArgumentException(var8);
      }
   }

   public UserNotice(NoticeReference var1, String var2) {
      this.noticeRef = var1;
      DisplayText var3 = new DisplayText(var2);
      this.explicitText = var3;
   }

   public UserNotice(NoticeReference var1, DisplayText var2) {
      this.noticeRef = var1;
      this.explicitText = var2;
   }

   public DisplayText getExplicitText() {
      return this.explicitText;
   }

   public NoticeReference getNoticeRef() {
      return this.noticeRef;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.noticeRef != null) {
         NoticeReference var2 = this.noticeRef;
         var1.add(var2);
      }

      if(this.explicitText != null) {
         DisplayText var3 = this.explicitText;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
