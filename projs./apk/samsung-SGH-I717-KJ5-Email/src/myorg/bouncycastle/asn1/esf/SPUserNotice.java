package myorg.bouncycastle.asn1.esf;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.DisplayText;
import myorg.bouncycastle.asn1.x509.NoticeReference;

public class SPUserNotice {

   private DisplayText explicitText;
   private NoticeReference noticeRef;


   public SPUserNotice(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         DEREncodable var3 = (DEREncodable)var2.nextElement();
         if(var3 instanceof NoticeReference) {
            NoticeReference var4 = NoticeReference.getInstance(var3);
            this.noticeRef = var4;
         } else {
            if(!(var3 instanceof DisplayText)) {
               throw new IllegalArgumentException("Invalid element in \'SPUserNotice\'.");
            }

            DisplayText var5 = DisplayText.getInstance(var3);
            this.explicitText = var5;
         }
      }

   }

   public SPUserNotice(NoticeReference var1, DisplayText var2) {
      this.noticeRef = var1;
      this.explicitText = var2;
   }

   public static SPUserNotice getInstance(Object var0) {
      SPUserNotice var1;
      if(var0 != null && !(var0 instanceof SPUserNotice)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'SPUserNotice\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SPUserNotice(var2);
      } else {
         var1 = (SPUserNotice)var0;
      }

      return var1;
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
