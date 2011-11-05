package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIBody;
import myorg.bouncycastle.asn1.cmp.PKIHeader;

public class ProtectedPart extends ASN1Encodable {

   private PKIBody body;
   private PKIHeader header;


   private ProtectedPart(ASN1Sequence var1) {
      PKIHeader var2 = PKIHeader.getInstance(var1.getObjectAt(0));
      this.header = var2;
      PKIBody var3 = PKIBody.getInstance(var1.getObjectAt(1));
      this.body = var3;
   }

   public static ProtectedPart getInstance(Object var0) {
      ProtectedPart var1;
      if(var0 instanceof ProtectedPart) {
         var1 = (ProtectedPart)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ProtectedPart(var2);
      }

      return var1;
   }

   public PKIBody getBody() {
      return this.body;
   }

   public PKIHeader getHeader() {
      return this.header;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIHeader var2 = this.header;
      var1.add(var2);
      PKIBody var3 = this.body;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
