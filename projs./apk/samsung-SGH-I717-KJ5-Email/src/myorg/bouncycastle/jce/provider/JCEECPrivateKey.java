package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X962Parameters;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.jce.interfaces.ECPointEncoder;
import myorg.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import myorg.bouncycastle.jce.provider.JCEECPublicKey;
import myorg.bouncycastle.jce.provider.PKCS12BagAttributeCarrierImpl;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import myorg.bouncycastle.jce.spec.ECNamedCurveSpec;
import myorg.bouncycastle.math.ec.ECCurve;

public class JCEECPrivateKey implements ECPrivateKey, myorg.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {

   private String algorithm = "EC";
   private PKCS12BagAttributeCarrierImpl attrCarrier;
   private BigInteger d;
   private ECParameterSpec ecSpec;
   private DERBitString publicKey;
   private boolean withCompression;


   protected JCEECPrivateKey() {
      PKCS12BagAttributeCarrierImpl var1 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var1;
   }

   public JCEECPrivateKey(String var1, ECPrivateKeySpec var2) {
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      this.algorithm = var1;
      BigInteger var4 = var2.getS();
      this.d = var4;
      ECParameterSpec var5 = var2.getParams();
      this.ecSpec = var5;
   }

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2) {
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      this.algorithm = var1;
      BigInteger var4 = var2.getD();
      this.d = var4;
      this.ecSpec = null;
   }

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2, JCEECPublicKey var3, ECParameterSpec var4) {
      PKCS12BagAttributeCarrierImpl var5 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var5;
      ECDomainParameters var6 = var2.getParameters();
      this.algorithm = var1;
      BigInteger var7 = var2.getD();
      this.d = var7;
      if(var4 == null) {
         ECCurve var8 = var6.getCurve();
         byte[] var9 = var6.getSeed();
         EllipticCurve var10 = EC5Util.convertCurve(var8, var9);
         BigInteger var11 = var6.getG().getX().toBigInteger();
         BigInteger var12 = var6.getG().getY().toBigInteger();
         ECPoint var13 = new ECPoint(var11, var12);
         BigInteger var14 = var6.getN();
         int var15 = var6.getH().intValue();
         ECParameterSpec var16 = new ECParameterSpec(var10, var13, var14, var15);
         this.ecSpec = var16;
      } else {
         this.ecSpec = var4;
      }

      DERBitString var17 = this.getPublicKeyDetails(var3);
      this.publicKey = var17;
   }

   public JCEECPrivateKey(String var1, ECPrivateKeyParameters var2, JCEECPublicKey var3, myorg.bouncycastle.jce.spec.ECParameterSpec var4) {
      PKCS12BagAttributeCarrierImpl var5 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var5;
      ECDomainParameters var6 = var2.getParameters();
      this.algorithm = var1;
      BigInteger var7 = var2.getD();
      this.d = var7;
      if(var4 == null) {
         ECCurve var8 = var6.getCurve();
         byte[] var9 = var6.getSeed();
         EllipticCurve var10 = EC5Util.convertCurve(var8, var9);
         BigInteger var11 = var6.getG().getX().toBigInteger();
         BigInteger var12 = var6.getG().getY().toBigInteger();
         ECPoint var13 = new ECPoint(var11, var12);
         BigInteger var14 = var6.getN();
         int var15 = var6.getH().intValue();
         ECParameterSpec var16 = new ECParameterSpec(var10, var13, var14, var15);
         this.ecSpec = var16;
      } else {
         ECCurve var18 = var4.getCurve();
         byte[] var19 = var4.getSeed();
         EllipticCurve var20 = EC5Util.convertCurve(var18, var19);
         BigInteger var21 = var4.getG().getX().toBigInteger();
         BigInteger var22 = var4.getG().getY().toBigInteger();
         ECPoint var23 = new ECPoint(var21, var22);
         BigInteger var24 = var4.getN();
         int var25 = var4.getH().intValue();
         ECParameterSpec var26 = new ECParameterSpec(var20, var23, var24, var25);
         this.ecSpec = var26;
      }

      DERBitString var17 = this.getPublicKeyDetails(var3);
      this.publicKey = var17;
   }

   public JCEECPrivateKey(String var1, JCEECPrivateKey var2) {
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      this.algorithm = var1;
      BigInteger var4 = var2.d;
      this.d = var4;
      ECParameterSpec var5 = var2.ecSpec;
      this.ecSpec = var5;
      boolean var6 = var2.withCompression;
      this.withCompression = var6;
      PKCS12BagAttributeCarrierImpl var7 = var2.attrCarrier;
      this.attrCarrier = var7;
      DERBitString var8 = var2.publicKey;
      this.publicKey = var8;
   }

   public JCEECPrivateKey(String var1, myorg.bouncycastle.jce.spec.ECPrivateKeySpec var2) {
      PKCS12BagAttributeCarrierImpl var3 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var3;
      this.algorithm = var1;
      BigInteger var4 = var2.getD();
      this.d = var4;
      if(var2.getParams() != null) {
         ECCurve var5 = var2.getParams().getCurve();
         byte[] var6 = var2.getParams().getSeed();
         EllipticCurve var7 = EC5Util.convertCurve(var5, var6);
         myorg.bouncycastle.jce.spec.ECParameterSpec var8 = var2.getParams();
         ECParameterSpec var9 = EC5Util.convertSpec(var7, var8);
         this.ecSpec = var9;
      } else {
         this.ecSpec = null;
      }
   }

   public JCEECPrivateKey(ECPrivateKey var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      BigInteger var3 = var1.getS();
      this.d = var3;
      String var4 = var1.getAlgorithm();
      this.algorithm = var4;
      ECParameterSpec var5 = var1.getParams();
      this.ecSpec = var5;
   }

   JCEECPrivateKey(PrivateKeyInfo var1) {
      PKCS12BagAttributeCarrierImpl var2 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var2;
      this.populateFromPrivKeyInfo(var1);
   }

   private DERBitString getPublicKeyDetails(JCEECPublicKey var1) {
      DERBitString var2;
      DERBitString var3;
      try {
         var2 = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(var1.getEncoded())).getPublicKeyData();
      } catch (IOException var5) {
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   private void populateFromPrivKeyInfo(PrivateKeyInfo var1) {
      DERObject var2 = (DERObject)var1.getAlgorithmId().getParameters();
      X962Parameters var3 = new X962Parameters(var2);
      if(var3.isNamedCurve()) {
         DERObjectIdentifier var4 = (DERObjectIdentifier)var3.getParameters();
         X9ECParameters var5 = ECUtil.getNamedCurveByOid(var4);
         if(var5 == null) {
            ECDomainParameters var6 = ECGOST3410NamedCurves.getByOID(var4);
            ECCurve var7 = var6.getCurve();
            byte[] var8 = var6.getSeed();
            EllipticCurve var9 = EC5Util.convertCurve(var7, var8);
            String var10 = ECGOST3410NamedCurves.getName(var4);
            BigInteger var11 = var6.getG().getX().toBigInteger();
            BigInteger var12 = var6.getG().getY().toBigInteger();
            ECPoint var13 = new ECPoint(var11, var12);
            BigInteger var14 = var6.getN();
            BigInteger var15 = var6.getH();
            ECNamedCurveSpec var16 = new ECNamedCurveSpec(var10, var9, var13, var14, var15);
            this.ecSpec = var16;
         } else {
            ECCurve var18 = var5.getCurve();
            byte[] var19 = var5.getSeed();
            EllipticCurve var20 = EC5Util.convertCurve(var18, var19);
            String var21 = ECUtil.getCurveName(var4);
            BigInteger var22 = var5.getG().getX().toBigInteger();
            BigInteger var23 = var5.getG().getY().toBigInteger();
            ECPoint var24 = new ECPoint(var22, var23);
            BigInteger var25 = var5.getN();
            BigInteger var26 = var5.getH();
            ECNamedCurveSpec var27 = new ECNamedCurveSpec(var21, var20, var24, var25, var26);
            this.ecSpec = var27;
         }
      } else if(var3.isImplicitlyCA()) {
         this.ecSpec = null;
      } else {
         ASN1Sequence var28 = (ASN1Sequence)var3.getParameters();
         X9ECParameters var29 = new X9ECParameters(var28);
         ECCurve var30 = var29.getCurve();
         byte[] var31 = var29.getSeed();
         EllipticCurve var32 = EC5Util.convertCurve(var30, var31);
         BigInteger var33 = var29.getG().getX().toBigInteger();
         BigInteger var34 = var29.getG().getY().toBigInteger();
         ECPoint var35 = new ECPoint(var33, var34);
         BigInteger var36 = var29.getN();
         int var37 = var29.getH().intValue();
         ECParameterSpec var38 = new ECParameterSpec(var32, var35, var36, var37);
         this.ecSpec = var38;
      }

      if(var1.getPrivateKey() instanceof DERInteger) {
         BigInteger var17 = ((DERInteger)var1.getPrivateKey()).getValue();
         this.d = var17;
      } else {
         ASN1Sequence var39 = (ASN1Sequence)var1.getPrivateKey();
         ECPrivateKeyStructure var40 = new ECPrivateKeyStructure(var39);
         BigInteger var41 = var40.getKey();
         this.d = var41;
         DERBitString var42 = var40.getPublicKey();
         this.publicKey = var42;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      PrivateKeyInfo var2 = PrivateKeyInfo.getInstance(ASN1Object.fromByteArray((byte[])((byte[])var1.readObject())));
      this.populateFromPrivKeyInfo(var2);
      String var3 = (String)var1.readObject();
      this.algorithm = var3;
      boolean var4 = var1.readBoolean();
      this.withCompression = var4;
      PKCS12BagAttributeCarrierImpl var5 = new PKCS12BagAttributeCarrierImpl();
      this.attrCarrier = var5;
      this.attrCarrier.readObject(var1);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      byte[] var2 = this.getEncoded();
      var1.writeObject(var2);
      String var3 = this.algorithm;
      var1.writeObject(var3);
      boolean var4 = this.withCompression;
      var1.writeBoolean(var4);
      this.attrCarrier.writeObject(var1);
   }

   myorg.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
      myorg.bouncycastle.jce.spec.ECParameterSpec var3;
      if(this.ecSpec != null) {
         ECParameterSpec var1 = this.ecSpec;
         boolean var2 = this.withCompression;
         var3 = EC5Util.convertSpec(var1, var2);
      } else {
         var3 = ProviderUtil.getEcImplicitlyCa();
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof JCEECPrivateKey)) {
         var2 = false;
      } else {
         JCEECPrivateKey var3 = (JCEECPrivateKey)var1;
         BigInteger var4 = this.getD();
         BigInteger var5 = var3.getD();
         if(var4.equals(var5)) {
            myorg.bouncycastle.jce.spec.ECParameterSpec var6 = this.engineGetSpec();
            myorg.bouncycastle.jce.spec.ECParameterSpec var7 = var3.engineGetSpec();
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
      return this.algorithm;
   }

   public DEREncodable getBagAttribute(DERObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public BigInteger getD() {
      return this.d;
   }

   public byte[] getEncoded() {
      X962Parameters var2;
      if(this.ecSpec instanceof ECNamedCurveSpec) {
         DERObjectIdentifier var1 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
         var2 = new X962Parameters(var1);
      } else if(this.ecSpec == null) {
         DERNull var12 = DERNull.INSTANCE;
         var2 = new X962Parameters(var12);
      } else {
         ECCurve var13 = EC5Util.convertCurve(this.ecSpec.getCurve());
         ECPoint var14 = this.ecSpec.getGenerator();
         boolean var15 = this.withCompression;
         myorg.bouncycastle.math.ec.ECPoint var16 = EC5Util.convertPoint(var13, var14, var15);
         BigInteger var17 = this.ecSpec.getOrder();
         BigInteger var18 = BigInteger.valueOf((long)this.ecSpec.getCofactor());
         byte[] var19 = this.ecSpec.getCurve().getSeed();
         X9ECParameters var20 = new X9ECParameters(var13, var16, var17, var18, var19);
         var2 = new X962Parameters(var20);
      }

      ECPrivateKeyStructure var5;
      if(this.publicKey != null) {
         BigInteger var3 = this.getS();
         DERBitString var4 = this.publicKey;
         var5 = new ECPrivateKeyStructure(var3, var4, var2);
      } else {
         BigInteger var21 = this.getS();
         var5 = new ECPrivateKeyStructure(var21, var2);
      }

      String var6 = this.algorithm;
      PrivateKeyInfo var11;
      if("ECGOST3410".equals(var6)) {
         DERObjectIdentifier var7 = CryptoProObjectIdentifiers.gostR3410_2001;
         DERObject var8 = var2.getDERObject();
         AlgorithmIdentifier var9 = new AlgorithmIdentifier(var7, var8);
         DERObject var10 = var5.getDERObject();
         var11 = new PrivateKeyInfo(var9, var10);
      } else {
         DERObjectIdentifier var22 = X9ObjectIdentifiers.id_ecPublicKey;
         DERObject var23 = var2.getDERObject();
         AlgorithmIdentifier var24 = new AlgorithmIdentifier(var22, var23);
         DERObject var25 = var5.getDERObject();
         var11 = new PrivateKeyInfo(var24, var25);
      }

      return var11.getDEREncoded();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public myorg.bouncycastle.jce.spec.ECParameterSpec getParameters() {
      myorg.bouncycastle.jce.spec.ECParameterSpec var1;
      if(this.ecSpec == null) {
         var1 = null;
      } else {
         ECParameterSpec var2 = this.ecSpec;
         boolean var3 = this.withCompression;
         var1 = EC5Util.convertSpec(var2, var3);
      }

      return var1;
   }

   public ECParameterSpec getParams() {
      return this.ecSpec;
   }

   public BigInteger getS() {
      return this.d;
   }

   public int hashCode() {
      int var1 = this.getD().hashCode();
      int var2 = this.engineGetSpec().hashCode();
      return var1 ^ var2;
   }

   public void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }

   public void setPointFormat(String var1) {
      byte var2;
      if(!"UNCOMPRESSED".equalsIgnoreCase(var1)) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.withCompression = (boolean)var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("EC Private Key").append(var2);
      StringBuffer var4 = var1.append("             S: ");
      String var5 = this.d.toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      return var1.toString();
   }
}
