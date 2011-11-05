package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.DHParameter;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;

public class JCEDHPublicKey implements DHPublicKey {

   static final long serialVersionUID = -216691575254424324L;
   private DHParameterSpec dhSpec;
   private BigInteger y;


   JCEDHPublicKey(BigInteger var1, DHParameterSpec var2) {
      this.y = var1;
      this.dhSpec = var2;
   }

   JCEDHPublicKey(DHPublicKey var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      DHParameterSpec var3 = var1.getParams();
      this.dhSpec = var3;
   }

   JCEDHPublicKey(DHPublicKeySpec var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getP();
      BigInteger var4 = var1.getG();
      DHParameterSpec var5 = new DHParameterSpec(var3, var4);
      this.dhSpec = var5;
   }

   JCEDHPublicKey(SubjectPublicKeyInfo var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      DHParameter var3 = new DHParameter(var2);

      DERInteger var4;
      try {
         var4 = (DERInteger)var1.getPublicKey();
      } catch (IOException var14) {
         throw new IllegalArgumentException("invalid info structure in DH public key");
      }

      BigInteger var5 = var4.getValue();
      this.y = var5;
      if(var3.getL() != null) {
         BigInteger var6 = var3.getP();
         BigInteger var7 = var3.getG();
         int var8 = var3.getL().intValue();
         DHParameterSpec var9 = new DHParameterSpec(var6, var7, var8);
         this.dhSpec = var9;
      } else {
         BigInteger var11 = var3.getP();
         BigInteger var12 = var3.getG();
         DHParameterSpec var13 = new DHParameterSpec(var11, var12);
         this.dhSpec = var13;
      }
   }

   JCEDHPublicKey(DHPublicKeyParameters var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getParameters().getP();
      BigInteger var4 = var1.getParameters().getG();
      int var5 = var1.getParameters().getL();
      DHParameterSpec var6 = new DHParameterSpec(var3, var4, var5);
      this.dhSpec = var6;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.y = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      int var5 = var1.readInt();
      DHParameterSpec var6 = new DHParameterSpec(var3, var4, var5);
      this.dhSpec = var6;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.getY();
      var1.writeObject(var2);
      BigInteger var3 = this.dhSpec.getP();
      var1.writeObject(var3);
      BigInteger var4 = this.dhSpec.getG();
      var1.writeObject(var4);
      int var5 = this.dhSpec.getL();
      var1.writeInt(var5);
   }

   public String getAlgorithm() {
      return "DH";
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = X9ObjectIdentifiers.dhpublicnumber;
      BigInteger var2 = this.dhSpec.getP();
      BigInteger var3 = this.dhSpec.getG();
      int var4 = this.dhSpec.getL();
      DERObject var5 = (new DHParameter(var2, var3, var4)).getDERObject();
      AlgorithmIdentifier var6 = new AlgorithmIdentifier(var1, var5);
      BigInteger var7 = this.y;
      DERInteger var8 = new DERInteger(var7);
      return (new SubjectPublicKeyInfo(var6, var8)).getDEREncoded();
   }

   public String getFormat() {
      return "X.509";
   }

   public DHParameterSpec getParams() {
      return this.dhSpec;
   }

   public BigInteger getY() {
      return this.y;
   }
}
