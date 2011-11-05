package myorg.bouncycastle.asn1.x509.qualified;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.qualified.TypeOfBiometricData;

public class BiometricData extends ASN1Encodable {

   ASN1OctetString biometricDataHash;
   AlgorithmIdentifier hashAlgorithm;
   DERIA5String sourceDataUri;
   TypeOfBiometricData typeOfBiometricData;


   public BiometricData(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      TypeOfBiometricData var3 = TypeOfBiometricData.getInstance(var2.nextElement());
      this.typeOfBiometricData = var3;
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(var2.nextElement());
      this.hashAlgorithm = var4;
      ASN1OctetString var5 = ASN1OctetString.getInstance(var2.nextElement());
      this.biometricDataHash = var5;
      if(var2.hasMoreElements()) {
         DERIA5String var6 = DERIA5String.getInstance(var2.nextElement());
         this.sourceDataUri = var6;
      }
   }

   public BiometricData(TypeOfBiometricData var1, AlgorithmIdentifier var2, ASN1OctetString var3) {
      this.typeOfBiometricData = var1;
      this.hashAlgorithm = var2;
      this.biometricDataHash = var3;
      this.sourceDataUri = null;
   }

   public BiometricData(TypeOfBiometricData var1, AlgorithmIdentifier var2, ASN1OctetString var3, DERIA5String var4) {
      this.typeOfBiometricData = var1;
      this.hashAlgorithm = var2;
      this.biometricDataHash = var3;
      this.sourceDataUri = var4;
   }

   public static BiometricData getInstance(Object var0) {
      BiometricData var1;
      if(var0 != null && !(var0 instanceof BiometricData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new BiometricData(var2);
      } else {
         var1 = (BiometricData)var0;
      }

      return var1;
   }

   public ASN1OctetString getBiometricDataHash() {
      return this.biometricDataHash;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public DERIA5String getSourceDataUri() {
      return this.sourceDataUri;
   }

   public TypeOfBiometricData getTypeOfBiometricData() {
      return this.typeOfBiometricData;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      TypeOfBiometricData var2 = this.typeOfBiometricData;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.hashAlgorithm;
      var1.add(var3);
      ASN1OctetString var4 = this.biometricDataHash;
      var1.add(var4);
      if(this.sourceDataUri != null) {
         DERIA5String var5 = this.sourceDataUri;
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
