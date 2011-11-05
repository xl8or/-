package myorg.bouncycastle.sasn1.cms;

import java.io.IOException;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.Asn1ObjectIdentifier;
import myorg.bouncycastle.sasn1.Asn1Sequence;
import myorg.bouncycastle.sasn1.Asn1TaggedObject;

public class ContentInfoParser {

   private Asn1TaggedObject content;
   private Asn1ObjectIdentifier contentType;


   public ContentInfoParser(Asn1Sequence var1) throws IOException {
      Asn1ObjectIdentifier var2 = (Asn1ObjectIdentifier)var1.readObject();
      this.contentType = var2;
      Asn1TaggedObject var3 = (Asn1TaggedObject)var1.readObject();
      this.content = var3;
   }

   public Asn1Object getContent(int var1) throws IOException {
      Asn1Object var2;
      if(this.content != null) {
         var2 = this.content.getObject(var1, (boolean)1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public Asn1ObjectIdentifier getContentType() {
      return this.contentType;
   }
}
