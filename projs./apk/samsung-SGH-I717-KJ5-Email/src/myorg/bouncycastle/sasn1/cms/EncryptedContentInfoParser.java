package myorg.bouncycastle.sasn1.cms;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.sasn1.Asn1Object;
import myorg.bouncycastle.sasn1.Asn1ObjectIdentifier;
import myorg.bouncycastle.sasn1.Asn1Sequence;
import myorg.bouncycastle.sasn1.Asn1TaggedObject;
import myorg.bouncycastle.sasn1.DerSequence;

public class EncryptedContentInfoParser {

   private AlgorithmIdentifier _contentEncryptionAlgorithm;
   private Asn1ObjectIdentifier _contentType;
   private Asn1TaggedObject _encryptedContent;


   public EncryptedContentInfoParser(Asn1Sequence var1) throws IOException {
      Asn1ObjectIdentifier var2 = (Asn1ObjectIdentifier)var1.readObject();
      this._contentType = var2;
      byte[] var3 = ((DerSequence)var1.readObject()).getEncoded();
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance((new ASN1InputStream(var3)).readObject());
      this._contentEncryptionAlgorithm = var4;
      Asn1TaggedObject var5 = (Asn1TaggedObject)var1.readObject();
      this._encryptedContent = var5;
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this._contentEncryptionAlgorithm;
   }

   public Asn1ObjectIdentifier getContentType() {
      return this._contentType;
   }

   public Asn1Object getEncryptedContent(int var1) throws IOException {
      return this._encryptedContent.getObject(var1, (boolean)0);
   }
}
