package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CompressedData extends ASN1Encodable {

   private AlgorithmIdentifier compressionAlgorithm;
   private ContentInfo encapContentInfo;
   private DERInteger version;


   public CompressedData(ASN1Sequence var1) {
      DERInteger var2 = (DERInteger)var1.getObjectAt(0);
      this.version = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
      this.compressionAlgorithm = var3;
      ContentInfo var4 = ContentInfo.getInstance(var1.getObjectAt(2));
      this.encapContentInfo = var4;
   }

   public CompressedData(AlgorithmIdentifier var1, ContentInfo var2) {
      DERInteger var3 = new DERInteger(0);
      this.version = var3;
      this.compressionAlgorithm = var1;
      this.encapContentInfo = var2;
   }

   public static CompressedData getInstance(Object var0) {
      CompressedData var1;
      if(var0 != null && !(var0 instanceof CompressedData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid CompressedData: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CompressedData(var2);
      } else {
         var1 = (CompressedData)var0;
      }

      return var1;
   }

   public static CompressedData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getCompressionAlgorithmIdentifier() {
      return this.compressionAlgorithm;
   }

   public ContentInfo getEncapContentInfo() {
      return this.encapContentInfo;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.compressionAlgorithm;
      var1.add(var3);
      ContentInfo var4 = this.encapContentInfo;
      var1.add(var4);
      return new BERSequence(var1);
   }
}
