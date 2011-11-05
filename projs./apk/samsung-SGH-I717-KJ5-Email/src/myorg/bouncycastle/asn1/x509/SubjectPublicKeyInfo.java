package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class SubjectPublicKeyInfo extends ASN1Encodable {

   private AlgorithmIdentifier algId;
   private DERBitString keyData;


   public SubjectPublicKeyInfo(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var5.nextElement());
         this.algId = var6;
         DERBitString var7 = DERBitString.getInstance(var5.nextElement());
         this.keyData = var7;
      }
   }

   public SubjectPublicKeyInfo(AlgorithmIdentifier var1, DEREncodable var2) {
      DERBitString var3 = new DERBitString(var2);
      this.keyData = var3;
      this.algId = var1;
   }

   public SubjectPublicKeyInfo(AlgorithmIdentifier var1, byte[] var2) {
      DERBitString var3 = new DERBitString(var2);
      this.keyData = var3;
      this.algId = var1;
   }

   public static SubjectPublicKeyInfo getInstance(Object var0) {
      SubjectPublicKeyInfo var1;
      if(var0 instanceof SubjectPublicKeyInfo) {
         var1 = (SubjectPublicKeyInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SubjectPublicKeyInfo(var2);
      }

      return var1;
   }

   public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getAlgorithmId() {
      return this.algId;
   }

   public DERObject getPublicKey() throws IOException {
      byte[] var1 = this.keyData.getBytes();
      return (new ASN1InputStream(var1)).readObject();
   }

   public DERBitString getPublicKeyData() {
      return this.keyData;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.algId;
      var1.add(var2);
      DERBitString var3 = this.keyData;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
