package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERUTF8String;

public class ContentHints extends ASN1Encodable {

   private DERUTF8String contentDescription;
   private DERObjectIdentifier contentType;


   private ContentHints(ASN1Sequence var1) {
      DEREncodable var2 = var1.getObjectAt(0);
      if(var2.getDERObject() instanceof DERUTF8String) {
         DERUTF8String var3 = DERUTF8String.getInstance(var2);
         this.contentDescription = var3;
         DERObjectIdentifier var4 = DERObjectIdentifier.getInstance(var1.getObjectAt(1));
         this.contentType = var4;
      } else {
         DERObjectIdentifier var5 = DERObjectIdentifier.getInstance(var1.getObjectAt(0));
         this.contentType = var5;
      }
   }

   public ContentHints(DERObjectIdentifier var1) {
      this.contentType = var1;
      this.contentDescription = null;
   }

   public ContentHints(DERObjectIdentifier var1, DERUTF8String var2) {
      this.contentType = var1;
      this.contentDescription = var2;
   }

   public static ContentHints getInstance(Object var0) {
      ContentHints var1;
      if(var0 != null && !(var0 instanceof ContentHints)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'ContentHints\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ContentHints(var2);
      } else {
         var1 = (ContentHints)var0;
      }

      return var1;
   }

   public DERUTF8String getContentDescription() {
      return this.contentDescription;
   }

   public DERObjectIdentifier getContentType() {
      return this.contentType;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.contentDescription != null) {
         DERUTF8String var2 = this.contentDescription;
         var1.add(var2);
      }

      DERObjectIdentifier var3 = this.contentType;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
