package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;

public class ContentIdentifier extends ASN1Encodable {

   ASN1OctetString value;


   public ContentIdentifier(ASN1OctetString var1) {
      this.value = var1;
   }

   public ContentIdentifier(byte[] var1) {
      DEROctetString var2 = new DEROctetString(var1);
      this((ASN1OctetString)var2);
   }

   public static ContentIdentifier getInstance(Object var0) {
      ContentIdentifier var1;
      if(var0 != null && !(var0 instanceof ContentIdentifier)) {
         if(!(var0 instanceof ASN1OctetString)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'ContentIdentifier\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1OctetString var2 = (ASN1OctetString)var0;
         var1 = new ContentIdentifier(var2);
      } else {
         var1 = (ContentIdentifier)var0;
      }

      return var1;
   }

   public ASN1OctetString getValue() {
      return this.value;
   }

   public DERObject toASN1Object() {
      return this.value;
   }
}
