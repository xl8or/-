package myorg.bouncycastle.sasn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.sasn1.Asn1Integer;
import myorg.bouncycastle.sasn1.Asn1Sequence;
import myorg.bouncycastle.sasn1.DerSequence;
import myorg.bouncycastle.sasn1.cms.ContentInfoParser;

public class CompressedDataParser {

   private AlgorithmIdentifier _compressionAlgorithm;
   private ContentInfoParser _encapContentInfo;
   private Asn1Integer _version;


   public CompressedDataParser(Asn1Sequence var1) throws IOException {
      Asn1Integer var2 = (Asn1Integer)var1.readObject();
      this._version = var2;
      byte[] var3 = ((DerSequence)var1.readObject()).getEncoded();
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance((new ASN1InputStream(var3)).readObject());
      this._compressionAlgorithm = var4;
      Asn1Sequence var5 = (Asn1Sequence)var1.readObject();
      ContentInfoParser var6 = new ContentInfoParser(var5);
      this._encapContentInfo = var6;
   }

   public AlgorithmIdentifier getCompressionAlgorithmIdentifier() {
      return this._compressionAlgorithm;
   }

   public ContentInfoParser getEncapContentInfo() {
      return this._encapContentInfo;
   }

   public Asn1Integer getVersion() {
      return this._version;
   }
}
