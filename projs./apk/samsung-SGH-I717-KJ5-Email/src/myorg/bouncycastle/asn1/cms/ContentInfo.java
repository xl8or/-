package myorg.bouncycastle.asn1.cms;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;

public class ContentInfo extends ASN1Encodable implements CMSObjectIdentifiers {

   private DEREncodable content;
   private DERObjectIdentifier contentType;


   public ContentInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERObjectIdentifier var3 = (DERObjectIdentifier)var2.nextElement();
      this.contentType = var3;
      if(var2.hasMoreElements()) {
         DERObject var4 = ((ASN1TaggedObject)var2.nextElement()).getObject();
         this.content = var4;
      }
   }

   public ContentInfo(DERObjectIdentifier var1, DEREncodable var2) {
      this.contentType = var1;
      this.content = var2;
   }

   public static ContentInfo getInstance(Object var0) {
      ContentInfo var1;
      if(var0 != null && !(var0 instanceof ContentInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ContentInfo(var2);
      } else {
         var1 = (ContentInfo)var0;
      }

      return var1;
   }

   public DEREncodable getContent() {
      return this.content;
   }

   public DERObjectIdentifier getContentType() {
      return this.contentType;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.contentType;
      var1.add(var2);
      if(this.content != null) {
         DEREncodable var3 = this.content;
         BERTaggedObject var4 = new BERTaggedObject(0, var3);
         var1.add(var4);
      }

      return new BERSequence(var1);
   }
}
