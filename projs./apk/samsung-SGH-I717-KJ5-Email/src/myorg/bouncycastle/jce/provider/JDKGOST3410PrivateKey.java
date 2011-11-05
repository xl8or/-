package myorg.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import myorg.bouncycastle.jce.interfaces.GOST3410Params;
import myorg.bouncycastle.jce.interfaces.GOST3410PrivateKey;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;
import myorg.bouncycastle.jce.spec.GOST3410ParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410PrivateKeySpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class JDKGOST3410PrivateKey implements GOST3410PrivateKey, PKCS12BagAttributeCarrier {

   private PKCS12BagAttributeCarrier attrCarrier;
   GOST3410Params gost3410Spec;
   BigInteger x;


   protected JDKGOST3410PrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   JDKGOST3410PrivateKey(PrivateKeyInfo var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      ASN1Sequence var3 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
      GOST3410PublicKeyAlgParameters var4 = new GOST3410PublicKeyAlgParameters(var3);
      byte[] var5 = ((DEROctetString)var1.getPrivateKey()).getOctets();
      byte[] var6 = new byte[var5.length];
      int var7 = 0;

      while(true) {
         int var8 = var5.length;
         if(var7 == var8) {
            BigInteger var11 = new BigInteger(1, var6);
            this.x = var11;
            GOST3410ParameterSpec var12 = GOST3410ParameterSpec.fromPublicKeyAlg(var4);
            this.gost3410Spec = var12;
            return;
         }

         int var9 = var5.length - 1 - var7;
         byte var10 = var5[var9];
         var6[var7] = var10;
         ++var7;
      }
   }

   JDKGOST3410PrivateKey(GOST3410PrivateKeyParameters var1, GOST3410ParameterSpec var2) {
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      BigInteger var4 = var1.getX();
      this.x = var4;
      this.gost3410Spec = var2;
      if(var2 == null) {
         throw new IllegalArgumentException("spec is null");
      }
   }

   JDKGOST3410PrivateKey(GOST3410PrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      GOST3410Params var4 = var1.getParameters();
      this.gost3410Spec = var4;
   }

   JDKGOST3410PrivateKey(GOST3410PrivateKeySpec var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getX();
      this.x = var3;
      BigInteger var4 = var1.getP();
      BigInteger var5 = var1.getQ();
      BigInteger var6 = var1.getA();
      GOST3410PublicKeyParameterSetSpec var7 = new GOST3410PublicKeyParameterSetSpec(var4, var5, var6);
      GOST3410ParameterSpec var8 = new GOST3410ParameterSpec(var7);
      this.gost3410Spec = var8;
   }

   public String getAlgorithm() {
      return "GOST3410";
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public byte[] getEncoded() {
      byte[] var1 = this.getX().toByteArray();
      byte[] var2;
      if(var1[0] == 0) {
         var2 = new byte[var1.length - 1];
      } else {
         var2 = new byte[var1.length];
      }

      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            PrivateKeyInfo var15;
            if(this.gost3410Spec instanceof GOST3410ParameterSpec) {
               DERObjectIdentifier var7 = CryptoProObjectIdentifiers.gostR3410_94;
               String var8 = this.gost3410Spec.getPublicKeyParamSetOID();
               DERObjectIdentifier var9 = new DERObjectIdentifier(var8);
               String var10 = this.gost3410Spec.getDigestParamSetOID();
               DERObjectIdentifier var11 = new DERObjectIdentifier(var10);
               DERObject var12 = (new GOST3410PublicKeyAlgParameters(var9, var11)).getDERObject();
               AlgorithmIdentifier var13 = new AlgorithmIdentifier(var7, var12);
               DEROctetString var14 = new DEROctetString(var2);
               var15 = new PrivateKeyInfo(var13, var14);
            } else {
               DERObjectIdentifier var16 = CryptoProObjectIdentifiers.gostR3410_94;
               AlgorithmIdentifier var17 = new AlgorithmIdentifier(var16);
               DEROctetString var18 = new DEROctetString(var2);
               var15 = new PrivateKeyInfo(var17, var18);
            }

            return var15.getDEREncoded();
         }

         int var5 = var1.length - 1 - var3;
         byte var6 = var1[var5];
         var2[var3] = var6;
         ++var3;
      }
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public GOST3410Params getParameters() {
      return this.gost3410Spec;
   }

   public BigInteger getX() {
      return this.x;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }
}
