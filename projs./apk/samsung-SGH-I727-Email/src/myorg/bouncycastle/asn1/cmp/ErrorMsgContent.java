package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIFreeText;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;

public class ErrorMsgContent extends ASN1Encodable {

   private DERInteger errorCode;
   private PKIFreeText errorDetails;
   private PKIStatusInfo pKIStatusInfo;


   private ErrorMsgContent(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      PKIStatusInfo var3 = PKIStatusInfo.getInstance(var2.nextElement());
      this.pKIStatusInfo = var3;

      while(var2.hasMoreElements()) {
         Object var4 = var2.nextElement();
         if(var4 instanceof DERInteger) {
            DERInteger var5 = DERInteger.getInstance(var4);
            this.errorCode = var5;
         } else {
            PKIFreeText var6 = PKIFreeText.getInstance(var4);
            this.errorDetails = var6;
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, ASN1Encodable var2) {
      if(var2 != null) {
         var1.add(var2);
      }
   }

   public static ErrorMsgContent getInstance(Object var0) {
      ErrorMsgContent var1;
      if(var0 instanceof ErrorMsgContent) {
         var1 = (ErrorMsgContent)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ErrorMsgContent(var2);
      }

      return var1;
   }

   public DERInteger getErrorCode() {
      return this.errorCode;
   }

   public PKIFreeText getErrorDetails() {
      return this.errorDetails;
   }

   public PKIStatusInfo getPKIStatusInfo() {
      return this.pKIStatusInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIStatusInfo var2 = this.pKIStatusInfo;
      var1.add(var2);
      DERInteger var3 = this.errorCode;
      this.addOptional(var1, var3);
      PKIFreeText var4 = this.errorDetails;
      this.addOptional(var1, var4);
      return new DERSequence(var1);
   }
}
