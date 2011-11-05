package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.DisplayText;

public class NoticeReference extends ASN1Encodable {

   private ASN1Sequence noticeNumbers;
   private DisplayText organization;


   public NoticeReference(int var1, String var2, ASN1Sequence var3) {
      DisplayText var4 = new DisplayText(var1, var2);
      this.organization = var4;
      this.noticeNumbers = var3;
   }

   public NoticeReference(String var1, Vector var2) {
      DisplayText var3 = new DisplayText(var1);
      this.organization = var3;
      Object var4 = var2.elementAt(0);
      ASN1EncodableVector var5 = new ASN1EncodableVector();
      if(var4 instanceof Integer) {
         Enumeration var6 = var2.elements();

         while(var6.hasMoreElements()) {
            int var7 = ((Integer)var6.nextElement()).intValue();
            DERInteger var8 = new DERInteger(var7);
            var5.add(var8);
         }
      }

      DERSequence var9 = new DERSequence(var5);
      this.noticeNumbers = var9;
   }

   public NoticeReference(String var1, ASN1Sequence var2) {
      DisplayText var3 = new DisplayText(var1);
      this.organization = var3;
      this.noticeNumbers = var2;
   }

   public NoticeReference(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         DisplayText var5 = DisplayText.getInstance(var1.getObjectAt(0));
         this.organization = var5;
         ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getObjectAt(1));
         this.noticeNumbers = var6;
      }
   }

   public static NoticeReference getInstance(Object var0) {
      NoticeReference var1;
      if(var0 instanceof NoticeReference) {
         var1 = (NoticeReference)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance.");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new NoticeReference(var2);
      }

      return var1;
   }

   public ASN1Sequence getNoticeNumbers() {
      return this.noticeNumbers;
   }

   public DisplayText getOrganization() {
      return this.organization;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DisplayText var2 = this.organization;
      var1.add(var2);
      ASN1Sequence var3 = this.noticeNumbers;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
