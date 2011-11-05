package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;

public class JDKDSAPrivateKey implements DSAPrivateKey, PKCS12BagAttributeCarrier {

   private static final long serialVersionUID = -4677259546958385734L;
   private PKCS12BagAttributeCarrierImpl attrCarrier;
   DSAParams dsaSpec;
   BigInteger x;


   protected JDKDSAPrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   JDKDSAPrivateKey(DSAPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      DSAParams var4 = var1.getParams();
      this.dsaSpec = var4;
   }

   JDKDSAPrivateKey(DSAPrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getQ();
      BigInteger var6 = var1.getG();
      DSAParameterSpec var7 = new DSAParameterSpec(var4, var5, var6);
      this.dsaSpec = var7;
   }

   JDKDSAPrivateKey(PrivateKeyInfo var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      ASN1Sequence var3 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      DSAParameter var4 = new DSAParameter(var3);
      BigInteger var5 = ((DERInteger)var1.getPrivateKey()).getValue();
      this.x = var5;
      BigInteger var6 = var4.getP();
      BigInteger var7 = var4.getQ();
      BigInteger var8 = var4.getG();
      DSAParameterSpec var9 = new DSAParameterSpec(var6, var7, var8);
      this.dsaSpec = var9;
   }

   JDKDSAPrivateKey(DSAPrivateKeyParameters var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getParameters().getP();
      BigInteger var5 = var1.getParameters().getQ();
      BigInteger var6 = var1.getParameters().getG();
      DSAParameterSpec var7 = new DSAParameterSpec(var4, var5, var6);
      this.dsaSpec = var7;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.x = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      BigInteger var5 = (BigInteger)var1.readObject();
      DSAParameterSpec var6 = new DSAParameterSpec(var3, var4, var5);
      this.dsaSpec = var6;
      PKCS12BagAttributeCarrierImpl var7 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var7;
      this.attrCarrier.readObject(var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.x;
      var1.writeObject(var2);
      BigInteger var3 = this.dsaSpec.getP();
      var1.writeObject(var3);
      BigInteger var4 = this.dsaSpec.getQ();
      var1.writeObject(var4);
      BigInteger var5 = this.dsaSpec.getG();
      var1.writeObject(var5);
      this.attrCarrier.writeObject(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DSAPrivateKey)) {
         var2 = false;
      } else {
         DSAPrivateKey var3 = (DSAPrivateKey)var1;
         BigInteger var4 = this.getX();
         BigInteger var5 = var3.getX();
         if(var4.equals(var5)) {
            BigInteger var6 = this.getParams().getG();
            BigInteger var7 = var3.getParams().getG();
            if(var6.equals(var7)) {
               BigInteger var8 = this.getParams().getP();
               BigInteger var9 = var3.getParams().getP();
               if(var8.equals(var9)) {
                  BigInteger var10 = this.getParams().getQ();
                  BigInteger var11 = var3.getParams().getQ();
                  if(var10.equals(var11)) {
                     var2 = true;
                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAlgorithm() {
      return "DSA";
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = X9ObjectIdentifiers.id_dsa;
      BigInteger var2 = this.dsaSpec.getP();
      BigInteger var3 = this.dsaSpec.getQ();
      BigInteger var4 = this.dsaSpec.getG();
      DERObject var5 = (new DSAParameter(var2, var3, var4)).getDERObject();
      AlgorithmIdentifier var6 = new AlgorithmIdentifier(var1, var5);
      BigInteger var7 = this.getX();
      DERInteger var8 = new DERInteger(var7);
      return (new PrivateKeyInfo(var6, var8)).getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public DSAParams getParams() {
      return this.dsaSpec;
   }

   public BigInteger getX() {
      return this.x;
   }

   public int hashCode() {
      int var1 = this.getX().hashCode();
      int var2 = this.getParams().getG().hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.getParams().getP().hashCode();
      int var5 = var3 ^ var4;
      int var6 = this.getParams().getQ().hashCode();
      return var5 ^ var6;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }
}
