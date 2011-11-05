package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfoParser {

   private AlgorithmIdentifier _contentEncryptionAlgorithm;
   private DERObjectIdentifier _contentType;
   private ASN1TaggedObjectParser _encryptedContent;


   public EncryptedContentInfoParser(ASN1SequenceParser var1) throws IOException {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.readObject();
      this._contentType = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.readObject().getDERObject());
      this._contentEncryptionAlgorithm = var3;
      ASN1TaggedObjectParser var4 = (ASN1TaggedObjectParser)var1.readObject();
      this._encryptedContent = var4;
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this._contentEncryptionAlgorithm;
   }

   public DERObjectIdentifier getContentType() {
      return this._contentType;
   }

   public DEREncodable getEncryptedContent(int var1) throws IOException {
      return this._encryptedContent.getObjectParser(var1, (boolean)0);
   }
}
