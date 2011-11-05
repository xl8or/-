package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERUTF8String;

public class PKIFreeText extends ASN1Encodable {

   ASN1Sequence strings;


   public PKIFreeText(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      do {
         if(!var2.hasMoreElements()) {
            this.strings = var1;
            return;
         }
      } while(var2.nextElement() instanceof DERUTF8String);

      throw new IllegalArgumentException("attempt to insert non UTF8 STRING into PKIFreeText");
   }

   public PKIFreeText(DERUTF8String var1) {
      DERSequence var2 = new DERSequence(var1);
      this.strings = var2;
   }

   public static PKIFreeText getInstance(Object var0) {
      PKIFreeText var1;
      if(var0 instanceof PKIFreeText) {
         var1 = (PKIFreeText)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKIFreeText(var2);
      }

      return var1;
   }

   public static PKIFreeText getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERUTF8String getStringAt(int var1) {
      return (DERUTF8String)this.strings.getObjectAt(var1);
   }

   public int size() {
      return this.strings.size();
   }

   public DERObject toASN1Object() {
      return this.strings;
   }
}
