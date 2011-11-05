package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;
import myorg.bouncycastle.asn1.cms.OriginatorInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedDataParser {

   private DEREncodable nextObject;
   private boolean originatorInfoCalled;
   private ASN1SequenceParser seq;
   private DERInteger version;


   public AuthenticatedDataParser(ASN1SequenceParser var1) throws IOException {
      this.seq = var1;
      DERInteger var2 = (DERInteger)var1.readObject();
      this.version = var2;
   }

   public ASN1SetParser getAuthAttrs() throws IOException {
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      ASN1SetParser var3;
      if(this.nextObject instanceof ASN1TaggedObjectParser) {
         DEREncodable var2 = this.nextObject;
         this.nextObject = null;
         var3 = (ASN1SetParser)((ASN1TaggedObjectParser)var2).getObjectParser(17, (boolean)0);
      } else {
         var3 = null;
      }

      return var3;
   }

   public ContentInfoParser getEnapsulatedContentInfo() throws IOException {
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      ContentInfoParser var3;
      if(this.nextObject != null) {
         ASN1SequenceParser var2 = (ASN1SequenceParser)this.nextObject;
         this.nextObject = null;
         var3 = new ContentInfoParser(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public ASN1OctetString getMac() throws IOException {
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      DEREncodable var2 = this.nextObject;
      this.nextObject = null;
      return ASN1OctetString.getInstance(var2.getDERObject());
   }

   public AlgorithmIdentifier getMacAlgorithm() throws IOException {
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      AlgorithmIdentifier var3;
      if(this.nextObject != null) {
         ASN1SequenceParser var2 = (ASN1SequenceParser)this.nextObject;
         this.nextObject = null;
         var3 = AlgorithmIdentifier.getInstance(var2.getDERObject());
      } else {
         var3 = null;
      }

      return var3;
   }

   public OriginatorInfo getOriginatorInfo() throws IOException {
      this.originatorInfoCalled = (boolean)1;
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      OriginatorInfo var3;
      if(this.nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this.nextObject).getTagNo() == 0) {
         ASN1SequenceParser var2 = (ASN1SequenceParser)((ASN1TaggedObjectParser)this.nextObject).getObjectParser(16, (boolean)0);
         this.nextObject = null;
         var3 = OriginatorInfo.getInstance(var2.getDERObject());
      } else {
         var3 = null;
      }

      return var3;
   }

   public ASN1SetParser getRecipientInfos() throws IOException {
      if(!this.originatorInfoCalled) {
         OriginatorInfo var1 = this.getOriginatorInfo();
      }

      if(this.nextObject == null) {
         DEREncodable var2 = this.seq.readObject();
         this.nextObject = var2;
      }

      ASN1SetParser var3 = (ASN1SetParser)this.nextObject;
      this.nextObject = null;
      return var3;
   }

   public ASN1SetParser getUnauthAttrs() throws IOException {
      if(this.nextObject == null) {
         DEREncodable var1 = this.seq.readObject();
         this.nextObject = var1;
      }

      ASN1SetParser var3;
      if(this.nextObject != null) {
         DEREncodable var2 = this.nextObject;
         this.nextObject = null;
         var3 = (ASN1SetParser)((ASN1TaggedObjectParser)var2).getObjectParser(17, (boolean)0);
      } else {
         var3 = null;
      }

      return var3;
   }

   public DERInteger getVersion() {
      return this.version;
   }
}
