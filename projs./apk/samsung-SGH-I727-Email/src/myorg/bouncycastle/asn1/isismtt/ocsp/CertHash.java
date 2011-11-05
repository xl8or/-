package myorg.bouncycastle.asn1.isismtt.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertHash extends ASN1Encodable {

   private byte[] certificateHash;
   private AlgorithmIdentifier hashAlgorithm;


   private CertHash(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
         this.hashAlgorithm = var5;
         byte[] var6 = DEROctetString.getInstance(var1.getObjectAt(1)).getOctets();
         this.certificateHash = var6;
      }
   }

   public CertHash(AlgorithmIdentifier var1, byte[] var2) {
      this.hashAlgorithm = var1;
      byte[] var3 = new byte[var2.length];
      this.certificateHash = var3;
      byte[] var4 = this.certificateHash;
      int var5 = var2.length;
      System.arraycopy(var2, 0, var4, 0, var5);
   }

   public static CertHash getInstance(Object var0) {
      CertHash var1;
      if(var0 != null && !(var0 instanceof CertHash)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertHash(var2);
      } else {
         var1 = (CertHash)var0;
      }

      return var1;
   }

   public byte[] getCertificateHash() {
      return this.certificateHash;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      var1.add(var2);
      byte[] var3 = this.certificateHash;
      DEROctetString var4 = new DEROctetString(var3);
      var1.add(var4);
      return new DERSequence(var1);
   }
}
