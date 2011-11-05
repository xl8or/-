package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DigestInfo extends ASN1Encodable {

   private AlgorithmIdentifier algId;
   private byte[] digest;


   public DigestInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var2.nextElement());
      this.algId = var3;
      byte[] var4 = ASN1OctetString.getInstance(var2.nextElement()).getOctets();
      this.digest = var4;
   }

   public DigestInfo(AlgorithmIdentifier var1, byte[] var2) {
      this.digest = var2;
      this.algId = var1;
   }

   public static DigestInfo getInstance(Object var0) {
      DigestInfo var1;
      if(var0 instanceof DigestInfo) {
         var1 = (DigestInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new DigestInfo(var2);
      }

      return var1;
   }

   public static DigestInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getAlgorithmId() {
      return this.algId;
   }

   public byte[] getDigest() {
      return this.digest;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.algId;
      var1.add(var2);
      byte[] var3 = this.digest;
      DEROctetString var4 = new DEROctetString(var3);
      var1.add(var4);
      return new DERSequence(var1);
   }
}
