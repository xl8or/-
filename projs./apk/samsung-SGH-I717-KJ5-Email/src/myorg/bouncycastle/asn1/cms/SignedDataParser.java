package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;

public class SignedDataParser {

   private boolean _certsCalled;
   private boolean _crlsCalled;
   private Object _nextObject;
   private ASN1SequenceParser _seq;
   private DERInteger _version;


   private SignedDataParser(ASN1SequenceParser var1) throws IOException {
      this._seq = var1;
      DERInteger var2 = (DERInteger)var1.readObject();
      this._version = var2;
   }

   public static SignedDataParser getInstance(Object var0) throws IOException {
      SignedDataParser var2;
      if(var0 instanceof ASN1Sequence) {
         ASN1SequenceParser var1 = ((ASN1Sequence)var0).parser();
         var2 = new SignedDataParser(var1);
      } else {
         if(!(var0 instanceof ASN1SequenceParser)) {
            StringBuilder var4 = (new StringBuilder()).append("unknown object encountered: ");
            String var5 = var0.getClass().getName();
            String var6 = var4.append(var5).toString();
            throw new IOException(var6);
         }

         ASN1SequenceParser var3 = (ASN1SequenceParser)var0;
         var2 = new SignedDataParser(var3);
      }

      return var2;
   }

   public ASN1SetParser getCertificates() throws IOException {
      this._certsCalled = (boolean)1;
      DEREncodable var1 = this._seq.readObject();
      this._nextObject = var1;
      ASN1SetParser var3;
      if(this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0) {
         ASN1SetParser var2 = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, (boolean)0);
         this._nextObject = null;
         var3 = var2;
      } else {
         var3 = null;
      }

      return var3;
   }

   public ASN1SetParser getCrls() throws IOException {
      if(!this._certsCalled) {
         throw new IOException("getCerts() has not been called.");
      } else {
         this._crlsCalled = (boolean)1;
         if(this._nextObject == null) {
            DEREncodable var1 = this._seq.readObject();
            this._nextObject = var1;
         }

         ASN1SetParser var3;
         if(this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 1) {
            ASN1SetParser var2 = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, (boolean)0);
            this._nextObject = null;
            var3 = var2;
         } else {
            var3 = null;
         }

         return var3;
      }
   }

   public ASN1SetParser getDigestAlgorithms() throws IOException {
      DEREncodable var1 = this._seq.readObject();
      ASN1SetParser var2;
      if(var1 instanceof ASN1Set) {
         var2 = ((ASN1Set)var1).parser();
      } else {
         var2 = (ASN1SetParser)var1;
      }

      return var2;
   }

   public ContentInfoParser getEncapContentInfo() throws IOException {
      ASN1SequenceParser var1 = (ASN1SequenceParser)this._seq.readObject();
      return new ContentInfoParser(var1);
   }

   public ASN1SetParser getSignerInfos() throws IOException {
      if(this._certsCalled && this._crlsCalled) {
         if(this._nextObject == null) {
            DEREncodable var1 = this._seq.readObject();
            this._nextObject = var1;
         }

         return (ASN1SetParser)this._nextObject;
      } else {
         throw new IOException("getCerts() and/or getCrls() has not been called.");
      }
   }

   public DERInteger getVersion() {
      return this._version;
   }
}
