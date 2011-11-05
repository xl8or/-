package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import myorg.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X962Parameters;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ECPoint;
import myorg.bouncycastle.asn1.x9.X9IntegerConverter;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.jce.ECGOST3410NamedCurveTable;
import myorg.bouncycastle.jce.interfaces.ECPointEncoder;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.jce.provider.asymmetric.ec.EC5Util;
import myorg.bouncycastle.jce.provider.asymmetric.ec.ECUtil;
import myorg.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import myorg.bouncycastle.jce.spec.ECNamedCurveSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;

public class JCEECPublicKey implements ECPublicKey, myorg.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {

   private String algorithm = "EC";
   private ECParameterSpec ecSpec;
   private GOST3410PublicKeyAlgParameters gostParams;
   private myorg.bouncycastle.math.ec.ECPoint q;
   private boolean withCompression;


   public JCEECPublicKey(String var1, ECPublicKeySpec var2) {
      this.algorithm = var1;
      ECParameterSpec var3 = var2.getParams();
      this.ecSpec = var3;
      ECParameterSpec var4 = this.ecSpec;
      ECPoint var5 = var2.getW();
      myorg.bouncycastle.math.ec.ECPoint var6 = EC5Util.convertPoint(var4, var5, (boolean)0);
      this.q = var6;
   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2) {
      this.algorithm = var1;
      myorg.bouncycastle.math.ec.ECPoint var3 = var2.getQ();
      this.q = var3;
      this.ecSpec = null;
   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2, ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      myorg.bouncycastle.math.ec.ECPoint var5 = var2.getQ();
      this.q = var5;
      if(var3 == null) {
         ECCurve var6 = var4.getCurve();
         byte[] var7 = var4.getSeed();
         EllipticCurve var8 = EC5Util.convertCurve(var6, var7);
         ECParameterSpec var9 = this.createSpec(var8, var4);
         this.ecSpec = var9;
      } else {
         this.ecSpec = var3;
      }
   }

   public JCEECPublicKey(String var1, ECPublicKeyParameters var2, myorg.bouncycastle.jce.spec.ECParameterSpec var3) {
      ECDomainParameters var4 = var2.getParameters();
      this.algorithm = var1;
      myorg.bouncycastle.math.ec.ECPoint var5 = var2.getQ();
      this.q = var5;
      if(var3 == null) {
         ECCurve var6 = var4.getCurve();
         byte[] var7 = var4.getSeed();
         EllipticCurve var8 = EC5Util.convertCurve(var6, var7);
         ECParameterSpec var9 = this.createSpec(var8, var4);
         this.ecSpec = var9;
      } else {
         ECCurve var10 = var3.getCurve();
         byte[] var11 = var3.getSeed();
         ECParameterSpec var12 = EC5Util.convertSpec(EC5Util.convertCurve(var10, var11), var3);
         this.ecSpec = var12;
      }
   }

   public JCEECPublicKey(String var1, JCEECPublicKey var2) {
      this.algorithm = var1;
      myorg.bouncycastle.math.ec.ECPoint var3 = var2.q;
      this.q = var3;
      ECParameterSpec var4 = var2.ecSpec;
      this.ecSpec = var4;
      boolean var5 = var2.withCompression;
      this.withCompression = var5;
      GOST3410PublicKeyAlgParameters var6 = var2.gostParams;
      this.gostParams = var6;
   }

   public JCEECPublicKey(String var1, myorg.bouncycastle.jce.spec.ECPublicKeySpec var2) {
      this.algorithm = var1;
      myorg.bouncycastle.math.ec.ECPoint var3 = var2.getQ();
      this.q = var3;
      if(var2.getParams() != null) {
         ECCurve var4 = var2.getParams().getCurve();
         byte[] var5 = var2.getParams().getSeed();
         EllipticCurve var6 = EC5Util.convertCurve(var4, var5);
         myorg.bouncycastle.jce.spec.ECParameterSpec var7 = var2.getParams();
         ECParameterSpec var8 = EC5Util.convertSpec(var6, var7);
         this.ecSpec = var8;
      } else {
         if(this.q.getCurve() == null) {
            ECCurve var9 = ProviderUtil.getEcImplicitlyCa().getCurve();
            BigInteger var10 = this.q.getX().toBigInteger();
            BigInteger var11 = this.q.getY().toBigInteger();
            myorg.bouncycastle.math.ec.ECPoint var12 = var9.createPoint(var10, var11, (boolean)0);
            this.q = var12;
         }

         this.ecSpec = null;
      }
   }

   public JCEECPublicKey(ECPublicKey var1) {
      String var2 = var1.getAlgorithm();
      this.algorithm = var2;
      ECParameterSpec var3 = var1.getParams();
      this.ecSpec = var3;
      ECParameterSpec var4 = this.ecSpec;
      ECPoint var5 = var1.getW();
      myorg.bouncycastle.math.ec.ECPoint var6 = EC5Util.convertPoint(var4, var5, (boolean)0);
      this.q = var6;
   }

   JCEECPublicKey(SubjectPublicKeyInfo var1) {
      this.populateFromPubKeyInfo(var1);
   }

   private ECParameterSpec createSpec(EllipticCurve var1, ECDomainParameters var2) {
      BigInteger var3 = var2.getG().getX().toBigInteger();
      BigInteger var4 = var2.getG().getY().toBigInteger();
      ECPoint var5 = new ECPoint(var3, var4);
      BigInteger var6 = var2.getN();
      int var7 = var2.getH().intValue();
      return new ECParameterSpec(var1, var5, var6, var7);
   }

   private void extractBytes(byte[] var1, int var2, BigInteger var3) {
      byte[] var4 = var3.toByteArray();
      if(var4.length < 32) {
         byte[] var5 = new byte[32];
         int var6 = var5.length;
         int var7 = var4.length;
         int var8 = var6 - var7;
         int var9 = var4.length;
         System.arraycopy(var4, 0, var5, var8, var9);
      }

      for(int var10 = 0; var10 != 32; ++var10) {
         int var11 = var2 + var10;
         int var12 = var4.length - 1 - var10;
         byte var13 = var4[var12];
         var1[var11] = var13;
      }

   }

   private void populateFromPubKeyInfo(SubjectPublicKeyInfo var1) {
      DERObjectIdentifier var2 = var1.getAlgorithmId().getObjectId();
      DERObjectIdentifier var3 = CryptoProObjectIdentifiers.gostR3410_2001;
      if(var2.equals(var3)) {
         DERBitString var4 = var1.getPublicKeyData();
         String var5 = "ECGOST3410";
         this.algorithm = var5;

         ASN1OctetString var89;
         try {
            var89 = (ASN1OctetString)ASN1Object.fromByteArray(var4.getBytes());
         } catch (IOException var87) {
            throw new IllegalArgumentException("error recovering public key");
         }

         byte[] var7 = var89.getOctets();
         byte[] var8 = new byte[32];
         byte[] var9 = new byte[32];
         int var10 = 0;

         while(true) {
            int var11 = var8.length;
            if(var10 == var11) {
               int var15 = 0;

               while(true) {
                  int var16 = var9.length;
                  if(var15 == var16) {
                     GOST3410PublicKeyAlgParameters var19 = new GOST3410PublicKeyAlgParameters;
                     ASN1Sequence var20 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
                     var19.<init>(var20);
                     this.gostParams = var19;
                     ECNamedCurveParameterSpec var24 = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()));
                     if(var24 == null) {
                        return;
                     } else {
                        ECCurve var25 = var24.getCurve();
                        byte[] var26 = var24.getSeed();
                        EllipticCurve var27 = EC5Util.convertCurve(var25, var26);
                        BigInteger var28 = new BigInteger;
                        byte var30 = 1;
                        var28.<init>(var30, var8);
                        BigInteger var32 = new BigInteger;
                        byte var34 = 1;
                        var32.<init>(var34, var9);
                        myorg.bouncycastle.math.ec.ECPoint var36 = var25.createPoint(var28, var32, (boolean)0);
                        this.q = var36;
                        String var37 = ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet());
                        BigInteger var38 = var24.getG().getX().toBigInteger();
                        BigInteger var39 = var24.getG().getY().toBigInteger();
                        ECPoint var40 = new ECPoint(var38, var39);
                        BigInteger var41 = var24.getN();
                        BigInteger var42 = var24.getH();
                        ECNamedCurveSpec var43 = new ECNamedCurveSpec(var37, var27, var40, var41, var42);
                        this.ecSpec = var43;
                        return;
                     }
                  }

                  int var17 = 63 - var15;
                  byte var18 = var7[var17];
                  var9[var15] = var18;
                  ++var15;
               }
            }

            int var12 = 31 - var10;
            byte var13 = var7[var12];
            var8[var10] = var13;
            ++var10;
         }
      } else {
         X962Parameters var44 = new X962Parameters;
         DERObject var45 = (DERObject)var1.getAlgorithmId().getParameters();
         var44.<init>(var45);
         ECCurve var50;
         if(var44.isNamedCurve()) {
            DERObjectIdentifier var48 = (DERObjectIdentifier)var44.getParameters();
            X9ECParameters var49 = ECUtil.getNamedCurveByOid(var48);
            var50 = var49.getCurve();
            byte[] var51 = var49.getSeed();
            EllipticCurve var52 = EC5Util.convertCurve(var50, var51);
            String var53 = ECUtil.getCurveName(var48);
            BigInteger var54 = var49.getG().getX().toBigInteger();
            BigInteger var55 = var49.getG().getY().toBigInteger();
            ECPoint var56 = new ECPoint(var54, var55);
            BigInteger var57 = var49.getN();
            BigInteger var58 = var49.getH();
            ECNamedCurveSpec var59 = new ECNamedCurveSpec(var53, var52, var56, var57, var58);
            this.ecSpec = var59;
         } else if(var44.isImplicitlyCA()) {
            Object var75 = null;
            this.ecSpec = (ECParameterSpec)var75;
            var50 = ProviderUtil.getEcImplicitlyCa().getCurve();
         } else {
            ASN1Sequence var76 = (ASN1Sequence)var44.getParameters();
            X9ECParameters var77 = new X9ECParameters(var76);
            var50 = var77.getCurve();
            byte[] var78 = var77.getSeed();
            EllipticCurve var79 = EC5Util.convertCurve(var50, var78);
            BigInteger var80 = var77.getG().getX().toBigInteger();
            BigInteger var81 = var77.getG().getY().toBigInteger();
            ECPoint var82 = new ECPoint(var80, var81);
            BigInteger var83 = var77.getN();
            int var84 = var77.getH().intValue();
            ECParameterSpec var85 = new ECParameterSpec(var79, var82, var83, var84);
            this.ecSpec = var85;
         }

         byte[] var60 = var1.getPublicKeyData().getBytes();
         DEROctetString var6 = new DEROctetString(var60);
         if(var60[0] == 4) {
            byte var63 = var60[1];
            int var64 = var60.length - 2;
            if(var63 == var64 && (var60[2] == 2 || var60[2] == 3)) {
               int var65 = (new X9IntegerConverter()).getByteLength(var50);
               int var66 = var60.length - 3;
               if(var65 >= var66) {
                  try {
                     ASN1OctetString var69 = (ASN1OctetString)ASN1Object.fromByteArray(var60);
                  } catch (IOException var88) {
                     throw new IllegalArgumentException("error recovering public key");
                  }
               }
            }
         }

         X9ECPoint var70 = new X9ECPoint(var50, var6);
         myorg.bouncycastle.math.ec.ECPoint var74 = var70.getPoint();
         this.q = var74;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray((byte[])((byte[])var1.readObject())));
      this.populateFromPubKeyInfo(var2);
      String var3 = (String)var1.readObject();
      this.algorithm = var3;
      boolean var4 = var1.readBoolean();
      this.withCompression = var4;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      byte[] var2 = this.getEncoded();
      var1.writeObject(var2);
      String var3 = this.algorithm;
      var1.writeObject(var3);
      boolean var4 = this.withCompression;
      var1.writeBoolean(var4);
   }

   public myorg.bouncycastle.math.ec.ECPoint engineGetQ() {
      return this.q;
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
      if(!(var1 instanceof JCEECPublicKey)) {
         var2 = false;
      } else {
         JCEECPublicKey var3 = (JCEECPublicKey)var1;
         myorg.bouncycastle.math.ec.ECPoint var4 = this.engineGetQ();
         myorg.bouncycastle.math.ec.ECPoint var5 = var3.engineGetQ();
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

   public byte[] getEncoded() {
      SubjectPublicKeyInfo var9;
      if(this.algorithm.equals("ECGOST3410")) {
         Object var1;
         if(this.gostParams != null) {
            var1 = this.gostParams;
         } else if(this.ecSpec instanceof ECNamedCurveSpec) {
            DERObjectIdentifier var10 = ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)this.ecSpec).getName());
            DERObjectIdentifier var11 = CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet;
            var1 = new GOST3410PublicKeyAlgParameters(var10, var11);
         } else {
            ECCurve var12 = EC5Util.convertCurve(this.ecSpec.getCurve());
            ECPoint var13 = this.ecSpec.getGenerator();
            boolean var14 = this.withCompression;
            myorg.bouncycastle.math.ec.ECPoint var15 = EC5Util.convertPoint(var12, var13, var14);
            BigInteger var16 = this.ecSpec.getOrder();
            BigInteger var17 = BigInteger.valueOf((long)this.ecSpec.getCofactor());
            byte[] var18 = this.ecSpec.getCurve().getSeed();
            X9ECParameters var19 = new X9ECParameters(var12, var15, var16, var17, var18);
            var1 = new X962Parameters(var19);
         }

         BigInteger var2 = this.q.getX().toBigInteger();
         BigInteger var3 = this.q.getY().toBigInteger();
         byte[] var4 = new byte[64];
         this.extractBytes(var4, 0, var2);
         this.extractBytes(var4, 32, var3);
         DERObjectIdentifier var5 = CryptoProObjectIdentifiers.gostR3410_2001;
         DERObject var6 = ((ASN1Encodable)var1).getDERObject();
         AlgorithmIdentifier var7 = new AlgorithmIdentifier(var5, var6);
         DEROctetString var8 = new DEROctetString(var4);
         var9 = new SubjectPublicKeyInfo(var7, var8);
      } else {
         X962Parameters var40;
         if(this.ecSpec instanceof ECNamedCurveSpec) {
            DERObjectIdentifier var20 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
            var40 = new X962Parameters(var20);
         } else if(this.ecSpec == null) {
            DERNull var31 = DERNull.INSTANCE;
            var40 = new X962Parameters(var31);
         } else {
            ECCurve var32 = EC5Util.convertCurve(this.ecSpec.getCurve());
            ECPoint var33 = this.ecSpec.getGenerator();
            boolean var34 = this.withCompression;
            myorg.bouncycastle.math.ec.ECPoint var35 = EC5Util.convertPoint(var32, var33, var34);
            BigInteger var36 = this.ecSpec.getOrder();
            BigInteger var37 = BigInteger.valueOf((long)this.ecSpec.getCofactor());
            byte[] var38 = this.ecSpec.getCurve().getSeed();
            X9ECParameters var39 = new X9ECParameters(var32, var35, var36, var37, var38);
            var40 = new X962Parameters(var39);
         }

         ECCurve var21 = this.engineGetQ().getCurve();
         BigInteger var22 = this.getQ().getX().toBigInteger();
         BigInteger var23 = this.getQ().getY().toBigInteger();
         boolean var24 = this.withCompression;
         myorg.bouncycastle.math.ec.ECPoint var25 = var21.createPoint(var22, var23, var24);
         ASN1OctetString var26 = (ASN1OctetString)(new X9ECPoint(var25)).getDERObject();
         DERObjectIdentifier var27 = X9ObjectIdentifiers.id_ecPublicKey;
         DERObject var28 = var40.getDERObject();
         AlgorithmIdentifier var29 = new AlgorithmIdentifier(var27, var28);
         byte[] var30 = var26.getOctets();
         var9 = new SubjectPublicKeyInfo(var29, var30);
      }

      return var9.getDEREncoded();
   }

   public String getFormat() {
      return "X.509";
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

   public myorg.bouncycastle.math.ec.ECPoint getQ() {
      Object var3;
      if(this.ecSpec == null) {
         if(this.q instanceof myorg.bouncycastle.math.ec.ECPoint) {
            ECFieldElement var1 = this.q.getX();
            ECFieldElement var2 = this.q.getY();
            var3 = new myorg.bouncycastle.math.ec.ECPoint((ECCurve)null, var1, var2);
         } else {
            ECFieldElement var4 = this.q.getX();
            ECFieldElement var5 = this.q.getY();
            var3 = new myorg.bouncycastle.math.ec.ECPoint((ECCurve)null, var4, var5);
         }
      } else {
         var3 = this.q;
      }

      return (myorg.bouncycastle.math.ec.ECPoint)var3;
   }

   public ECPoint getW() {
      BigInteger var1 = this.q.getX().toBigInteger();
      BigInteger var2 = this.q.getY().toBigInteger();
      return new ECPoint(var1, var2);
   }

   public int hashCode() {
      int var1 = this.engineGetQ().hashCode();
      int var2 = this.engineGetSpec().hashCode();
      return var1 ^ var2;
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
      StringBuffer var3 = var1.append("EC Public Key").append(var2);
      StringBuffer var4 = var1.append("            X: ");
      String var5 = this.q.getX().toBigInteger().toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      StringBuffer var7 = var1.append("            Y: ");
      String var8 = this.q.getY().toBigInteger().toString(16);
      StringBuffer var9 = var7.append(var8).append(var2);
      return var1.toString();
   }
}
