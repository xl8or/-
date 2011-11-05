package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.oiw.ElGamalParameter;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;
import myorg.bouncycastle.jce.spec.ElGamalPrivateKeySpec;

public class JCEElGamalPrivateKey implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier {

   static final long serialVersionUID = 4819350091141529678L;
   private PKCS12BagAttributeCarrierImpl attrCarrier;
   ElGamalParameterSpec elSpec;
   BigInteger x;


   protected JCEElGamalPrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   JCEElGamalPrivateKey(DHPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getParams().getP();
      BigInteger var5 = var1.getParams().getG();
      ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
      this.elSpec = var6;
   }

   JCEElGamalPrivateKey(DHPrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getG();
      ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
      this.elSpec = var6;
   }

   JCEElGamalPrivateKey(PrivateKeyInfo var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      ASN1Sequence var3 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      ElGamalParameter var4 = new ElGamalParameter(var3);
      BigInteger var5 = ((DERInteger)var1.getPrivateKey()).getValue();
      this.x = var5;
      BigInteger var6 = var4.getP();
      BigInteger var7 = var4.getG();
      ElGamalParameterSpec var8 = new ElGamalParameterSpec(var6, var7);
      this.elSpec = var8;
   }

   JCEElGamalPrivateKey(ElGamalPrivateKeyParameters var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getParameters().getP();
      BigInteger var5 = var1.getParameters().getG();
      ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
      this.elSpec = var6;
   }

   JCEElGamalPrivateKey(ElGamalPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      ElGamalParameterSpec var4 = var1.getParameters();
      this.elSpec = var4;
   }

   JCEElGamalPrivateKey(ElGamalPrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getParams().getP();
      BigInteger var5 = var1.getParams().getG();
      ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
      this.elSpec = var6;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.x = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      ElGamalParameterSpec var5 = new ElGamalParameterSpec(var3, var4);
      this.elSpec = var5;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.getX();
      var1.writeObject(var2);
      BigInteger var3 = this.elSpec.getP();
      var1.writeObject(var3);
      BigInteger var4 = this.elSpec.getG();
      var1.writeObject(var4);
   }

   public String getAlgorithm() {
      return "ElGamal";
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = OIWObjectIdentifiers.elGamalAlgorithm;
      BigInteger var2 = this.elSpec.getP();
      BigInteger var3 = this.elSpec.getG();
      DERObject var4 = (new ElGamalParameter(var2, var3)).getDERObject();
      AlgorithmIdentifier var5 = new AlgorithmIdentifier(var1, var4);
      BigInteger var6 = this.getX();
      DERInteger var7 = new DERInteger(var6);
      return (new PrivateKeyInfo(var5, var7)).getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public ElGamalParameterSpec getParameters() {
      return this.elSpec;
   }

   public DHParameterSpec getParams() {
      BigInteger var1 = this.elSpec.getP();
      BigInteger var2 = this.elSpec.getG();
      return new DHParameterSpec(var1, var2);
   }

   public BigInteger getX() {
      return this.x;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }
}
