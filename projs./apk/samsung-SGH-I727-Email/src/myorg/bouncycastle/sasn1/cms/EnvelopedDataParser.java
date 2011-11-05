package myorg.bouncycastle.sasn1.cms;

import java.io.IOException;
import myorg.bouncycastle.sasn1.Asn1Integer;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.Asn1Sequence;
import myorg.bouncycastle.sasn1.Asn1Set;
import myorg.bouncycastle.sasn1.Asn1TaggedObject;
import myorg.bouncycastle.sasn1.cms.EncryptedContentInfoParser;

public class EnvelopedDataParser {

   private Asn1Object _nextObject;
   private Asn1Sequence _seq;
   private Asn1Integer _version;


   public EnvelopedDataParser(Asn1Sequence var1) throws IOException {
      this._seq = var1;
      Asn1Integer var2 = (Asn1Integer)var1.readObject();
      this._version = var2;
   }

   public Asn1Set getCertificates() throws IOException {
      Asn1Object var1 = this._seq.readObject();
      this._nextObject = var1;
      Asn1Set var3;
      if(this._nextObject instanceof Asn1TaggedObject && ((Asn1TaggedObject)this._nextObject).getTagNumber() == 0) {
         Asn1Set var2 = (Asn1Set)((Asn1TaggedObject)this._nextObject).getObject(17, (boolean)0);
         this._nextObject = null;
         var3 = var2;
      } else {
         var3 = null;
      }

      return var3;
   }

   public Asn1Set getCrls() throws IOException {
      if(this._nextObject == null) {
         Asn1Object var1 = this._seq.readObject();
         this._nextObject = var1;
      }

      Asn1Set var3;
      if(this._nextObject instanceof Asn1TaggedObject && ((Asn1TaggedObject)this._nextObject).getTagNumber() == 1) {
         Asn1Set var2 = (Asn1Set)((Asn1TaggedObject)this._nextObject).getObject(17, (boolean)0);
         this._nextObject = null;
         var3 = var2;
      } else {
         var3 = null;
      }

      return var3;
   }

   public EncryptedContentInfoParser getEncryptedContentInfo() throws IOException {
      Asn1Sequence var1 = (Asn1Sequence)this._seq.readObject();
      return new EncryptedContentInfoParser(var1);
   }

   public Asn1Set getRecipientInfos() throws IOException {
      return (Asn1Set)this._seq.readObject();
   }

   public Asn1Set getUnprotectedAttrs() throws IOException {
      Asn1Object var1 = this._seq.readObject();
      Asn1Set var2;
      if(var1 != null) {
         var2 = (Asn1Set)((Asn1TaggedObject)var1).getObject(17, (boolean)0);
      } else {
         var2 = null;
      }

      return var2;
   }

   public Asn1Integer getVersion() {
      return this._version;
   }
}
