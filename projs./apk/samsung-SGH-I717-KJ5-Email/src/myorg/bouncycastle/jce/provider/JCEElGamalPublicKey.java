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
import myorg.bouncycastle.asn1.oiw.ElGamalParameter;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import myorg.bouncycastle.jce.interfaces.ElGamalPublicKey;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;
import myorg.bouncycastle.jce.spec.ElGamalPublicKeySpec;

public class JCEElGamalPublicKey implements ElGamalPublicKey, DHPublicKey {

   static final long serialVersionUID = 8712728417091216948L;
   private ElGamalParameterSpec elSpec;
   private BigInteger y;


   JCEElGamalPublicKey(BigInteger var1, ElGamalParameterSpec var2) {
      this.y = var1;
      this.elSpec = var2;
   }

   JCEElGamalPublicKey(DHPublicKey var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getParams().getP();
      BigInteger var4 = var1.getParams().getG();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   JCEElGamalPublicKey(DHPublicKeySpec var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getP();
      BigInteger var4 = var1.getG();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   JCEElGamalPublicKey(SubjectPublicKeyInfo var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      ElGamalParameter var3 = new ElGamalParameter(var2);

      DERInteger var4;
      try {
         var4 = (DERInteger)var1.getPublicKey();
      } catch (IOException var10) {
         throw new IllegalArgumentException("invalid info structure in DSA public key");
      }

      BigInteger var5 = var4.getValue();
      this.y = var5;
      BigInteger var6 = var3.getP();
      BigInteger var7 = var3.getG();
      ElGamalParameterSpec var8 = new ElGamalParameterSpec(var6, var7);
      this.elSpec = var8;
   }

   JCEElGamalPublicKey(ElGamalPublicKeyParameters var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getParameters().getP();
      BigInteger var4 = var1.getParameters().getG();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   JCEElGamalPublicKey(ElGamalPublicKey var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      ElGamalParameterSpec var3 = var1.getParameters();
      this.elSpec = var3;
   }

   JCEElGamalPublicKey(ElGamalPublicKeySpec var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getParams().getP();
      BigInteger var4 = var1.getParams().getG();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.y = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.getY();
      var1.writeObject(var2);
      BigInteger var3 = this.elSpec.getP();
      var1.writeObject(var3);
      BigInteger var4 = this.elSpec.getG();
      var1.writeObject(var4);
   }

   public String getAlgorithm() {
      return "ElGamal";
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = OIWObjectIdentifiers.elGamalAlgorithm;
      BigInteger var2 = this.elSpec.getP();
      BigInteger var3 = this.elSpec.getG();
      DERObject var4 = (new ElGamalParameter(var2, var3)).getDERObject();
      AlgorithmIdentifier var5 = new AlgorithmIdentifier(var1, var4);
      BigInteger var6 = this.y;
      DERInteger var7 = new DERInteger(var6);
      return (new SubjectPublicKeyInfo(var5, var7)).getDEREncoded();
   }

   public String getFormat() {
      return "X.509";
   }

   public ElGamalParameterSpec getParameters() {
      return this.elSpec;
   }

   public DHParameterSpec getParams() {
      BigInteger var1 = this.elSpec.getP();
      BigInteger var2 = this.elSpec.getG();
      return new DHParameterSpec(var1, var2);
   }

   public BigInteger getY() {
      return this.y;
   }
}
