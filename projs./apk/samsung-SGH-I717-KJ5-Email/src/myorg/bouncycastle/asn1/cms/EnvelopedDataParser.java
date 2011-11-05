package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfoParser;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;

public class EnvelopedDataParser {

   private DEREncodable _nextObject;
   private boolean _originatorInfoCalled;
   private ASN1SequenceParser _seq;
   private DERInteger _version;


   public EnvelopedDataParser(ASN1SequenceParser var1) throws IOException {
      this._seq = var1;
      DERInteger var2 = (DERInteger)var1.readObject();
      this._version = var2;
   }

   public EncryptedContentInfoParser getEncryptedContentInfo() throws IOException {
      if(this._nextObject == null) {
         DEREncodable var1 = this._seq.readObject();
         this._nextObject = var1;
      }

      EncryptedContentInfoParser var3;
      if(this._nextObject != null) {
         ASN1SequenceParser var2 = (ASN1SequenceParser)this._nextObject;
         this._nextObject = null;
         var3 = new EncryptedContentInfoParser(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public OriginatorInfo getOriginatorInfo() throws IOException {
      this._originatorInfoCalled = (boolean)1;
      if(this._nextObject == null) {
         DEREncodable var1 = this._seq.readObject();
         this._nextObject = var1;
      }

      OriginatorInfo var3;
      if(this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0) {
         ASN1SequenceParser var2 = (ASN1SequenceParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(16, (boolean)0);
         this._nextObject = null;
         var3 = OriginatorInfo.getInstance(var2.getDERObject());
      } else {
         var3 = null;
      }

      return var3;
   }

   public ASN1SetParser getRecipientInfos() throws IOException {
      if(!this._originatorInfoCalled) {
         OriginatorInfo var1 = this.getOriginatorInfo();
      }

      if(this._nextObject == null) {
         DEREncodable var2 = this._seq.readObject();
         this._nextObject = var2;
      }

      ASN1SetParser var3 = (ASN1SetParser)this._nextObject;
      this._nextObject = null;
      return var3;
   }

   public ASN1SetParser getUnprotectedAttrs() throws IOException {
      if(this._nextObject == null) {
         DEREncodable var1 = this._seq.readObject();
         this._nextObject = var1;
      }

      ASN1SetParser var3;
      if(this._nextObject != null) {
         DEREncodable var2 = this._nextObject;
         this._nextObject = null;
         var3 = (ASN1SetParser)((ASN1TaggedObjectParser)var2).getObjectParser(17, (boolean)0);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DERInteger getVersion() {
      return this._version;
   }
}
