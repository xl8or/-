package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;

public class ContentInfoParser {

   private ASN1TaggedObjectParser content;
   private DERObjectIdentifier contentType;


   public ContentInfoParser(ASN1SequenceParser var1) throws IOException {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.readObject();
      this.contentType = var2;
      ASN1TaggedObjectParser var3 = (ASN1TaggedObjectParser)var1.readObject();
      this.content = var3;
   }

   public DEREncodable getContent(int var1) throws IOException {
      DEREncodable var2;
      if(this.content != null) {
         var2 = this.content.getObjectParser(var1, (boolean)1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public DERObjectIdentifier getContentType() {
      return this.contentType;
   }
}
