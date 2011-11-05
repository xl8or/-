package myorg.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.pkcs.EncryptedData;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedPrivateKeyInfo extends ASN1Encodable {

   private AlgorithmIdentifier algId;
   private ASN1OctetString data;


   public EncryptedPrivateKeyInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var2.nextElement());
      this.algId = var3;
      ASN1OctetString var4 = (ASN1OctetString)var2.nextElement();
      this.data = var4;
   }

   public EncryptedPrivateKeyInfo(AlgorithmIdentifier var1, byte[] var2) {
      this.algId = var1;
      DEROctetString var3 = new DEROctetString(var2);
      this.data = var3;
   }

   public static EncryptedPrivateKeyInfo getInstance(Object var0) {
      EncryptedPrivateKeyInfo var1;
      if(var0 instanceof EncryptedData) {
         var1 = (EncryptedPrivateKeyInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new EncryptedPrivateKeyInfo(var2);
      }

      return var1;
   }

   public byte[] getEncryptedData() {
      return this.data.getOctets();
   }

   public AlgorithmIdentifier getEncryptionAlgorithm() {
      return this.algId;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.algId;
      var1.add(var2);
      ASN1OctetString var3 = this.data;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
