package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;

public class JCERSAPrivateKey implements RSAPrivateKey, PKCS12BagAttributeCarrier {

   private static BigInteger ZERO = BigInteger.valueOf(0L);
   static final long serialVersionUID = 5110188922551353628L;
   private PKCS12BagAttributeCarrierImpl attrCarrier;
   protected BigInteger modulus;
   protected BigInteger privateExponent;


   protected JCERSAPrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   JCERSAPrivateKey(RSAPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getModulus();
      this.modulus = var3;
      BigInteger var4 = var1.getPrivateExponent();
      this.privateExponent = var4;
   }

   JCERSAPrivateKey(RSAPrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getModulus();
      this.modulus = var3;
      BigInteger var4 = var1.getPrivateExponent();
      this.privateExponent = var4;
   }

   JCERSAPrivateKey(RSAKeyParameters var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getModulus();
      this.modulus = var3;
      BigInteger var4 = var1.getExponent();
      this.privateExponent = var4;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.modulus = var2;
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      this.attrCarrier.readObject(var1);
      BigInteger var4 = (BigInteger)var1.readObject();
      this.privateExponent = var4;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.modulus;
      var1.writeObject(var2);
      this.attrCarrier.writeObject(var1);
      BigInteger var3 = this.privateExponent;
      var1.writeObject(var3);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof RSAPrivateKey)) {
         var2 = false;
      } else if(var1 == this) {
         var2 = true;
      } else {
         RSAPrivateKey var3 = (RSAPrivateKey)var1;
         BigInteger var4 = this.getModulus();
         BigInteger var5 = var3.getModulus();
         if(var4.equals(var5)) {
            BigInteger var6 = this.getPrivateExponent();
            BigInteger var7 = var3.getPrivateExponent();
            if(var6.equals(var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAlgorithm() {
      return "RSA";
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public byte[] getEncoded() {
      DERObjectIdentifier var1 = PKCSObjectIdentifiers.rsaEncryption;
      DERNull var2 = new DERNull();
      AlgorithmIdentifier var3 = new AlgorithmIdentifier(var1, var2);
      BigInteger var4 = this.getModulus();
      BigInteger var5 = ZERO;
      BigInteger var6 = this.getPrivateExponent();
      BigInteger var7 = ZERO;
      BigInteger var8 = ZERO;
      BigInteger var9 = ZERO;
      BigInteger var10 = ZERO;
      BigInteger var11 = ZERO;
      DERObject var12 = (new RSAPrivateKeyStructure(var4, var5, var6, var7, var8, var9, var10, var11)).getDERObject();
      return (new PrivateKeyInfo(var3, var12)).getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public BigInteger getPrivateExponent() {
      return this.privateExponent;
   }

   public int hashCode() {
      int var1 = this.getModulus().hashCode();
      int var2 = this.getPrivateExponent().hashCode();
      return var1 ^ var2;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }
}
