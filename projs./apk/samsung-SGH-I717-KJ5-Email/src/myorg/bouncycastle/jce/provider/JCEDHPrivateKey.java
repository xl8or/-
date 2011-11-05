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
import myorg.bouncycastle.asn1.pkcs.DHParameter;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;

public class JCEDHPrivateKey implements DHPrivateKey, PKCS12BagAttributeCarrier {

   static final long serialVersionUID = 311058815616901812L;
   private PKCS12BagAttributeCarrier attrCarrier;
   DHParameterSpec dhSpec;
   BigInteger x;


   protected JCEDHPrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   JCEDHPrivateKey(DHPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      DHParameterSpec var4 = var1.getParams();
      this.dhSpec = var4;
   }

   JCEDHPrivateKey(DHPrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getG();
      DHParameterSpec var6 = new DHParameterSpec(var4, var5);
      this.dhSpec = var6;
   }

   JCEDHPrivateKey(PrivateKeyInfo var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      ASN1Sequence var3 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      DHParameter var4 = new DHParameter(var3);
      BigInteger var5 = ((DERInteger)var1.getPrivateKey()).getValue();
      this.x = var5;
      if(var4.getL() != null) {
         BigInteger var6 = var4.getP();
         BigInteger var7 = var4.getG();
         int var8 = var4.getL().intValue();
         DHParameterSpec var9 = new DHParameterSpec(var6, var7, var8);
         this.dhSpec = var9;
      } else {
         BigInteger var10 = var4.getP();
         BigInteger var11 = var4.getG();
         DHParameterSpec var12 = new DHParameterSpec(var10, var11);
         this.dhSpec = var12;
      }
   }

   JCEDHPrivateKey(DHPrivateKeyParameters var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getParameters().getP();
      BigInteger var5 = var1.getParameters().getG();
      int var6 = var1.getParameters().getL();
      DHParameterSpec var7 = new DHParameterSpec(var4, var5, var6);
      this.dhSpec = var7;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.x = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      int var5 = var1.readInt();
      DHParameterSpec var6 = new DHParameterSpec(var3, var4, var5);
      this.dhSpec = var6;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.getX();
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

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.dhKeyAgreement;
      BigInteger var2 = this.dhSpec.getP();
      BigInteger var3 = this.dhSpec.getG();
      int var4 = this.dhSpec.getL();
      DERObject var5 = (new DHParameter(var2, var3, var4)).getDERObject();
      AlgorithmIdentifier var6 = new AlgorithmIdentifier(var1, var5);
      BigInteger var7 = this.getX();
      DERInteger var8 = new DERInteger(var7);
      return (new PrivateKeyInfo(var6, var8)).getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public DHParameterSpec getParams() {
      return this.dhSpec;
   }

   public BigInteger getX() {
      return this.x;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }
}
