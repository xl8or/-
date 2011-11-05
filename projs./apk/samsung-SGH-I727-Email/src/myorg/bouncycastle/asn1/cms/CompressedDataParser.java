package myorg.bouncycastle.asn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CompressedDataParser {

   private AlgorithmIdentifier _compressionAlgorithm;
   private ContentInfoParser _encapContentInfo;
   private DERInteger _version;


   public CompressedDataParser(ASN1SequenceParser var1) throws IOException {
      DERInteger var2 = (DERInteger)var1.readObject();
      this._version = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.readObject().getDERObject());
      this._compressionAlgorithm = var3;
      ASN1SequenceParser var4 = (ASN1SequenceParser)var1.readObject();
      ContentInfoParser var5 = new ContentInfoParser(var4);
      this._encapContentInfo = var5;
   }

   public AlgorithmIdentifier getCompressionAlgorithmIdentifier() {
      return this._compressionAlgorithm;
   }

   public ContentInfoParser getEncapContentInfo() {
      return this._encapContentInfo;
   }

   public DERInteger getVersion() {
      return this._version;
   }
}
