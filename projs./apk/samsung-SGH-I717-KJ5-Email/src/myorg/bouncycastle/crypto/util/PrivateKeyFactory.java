package myorg.bouncycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTNamedCurves;
import myorg.bouncycastle.asn1.oiw.ElGamalParameter;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.DHParameter;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import myorg.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.asn1.x9.X962NamedCurves;
import myorg.bouncycastle.asn1.x9.X962Parameters;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class PrivateKeyFactory {

   public PrivateKeyFactory() {}

   public static AsymmetricKeyParameter createKey(InputStream var0) throws IOException {
      return createKey(PrivateKeyInfo.getInstance((new ASN1InputStream(var0)).readObject()));
   }

   public static AsymmetricKeyParameter createKey(PrivateKeyInfo var0) throws IOException {
      AlgorithmIdentifier var1 = var0.getAlgorithmId();
      DERObjectIdentifier var2 = var1.getObjectId();
      DERObjectIdentifier var3 = PKCSObjectIdentifiers.rsaEncryption;
      Object var65;
      if(var2.equals(var3)) {
         ASN1Sequence var4 = (ASN1Sequence)var0.getPrivateKey();
         RSAPrivateKeyStructure var5 = new RSAPrivateKeyStructure(var4);
         BigInteger var6 = var5.getModulus();
         BigInteger var7 = var5.getPublicExponent();
         BigInteger var8 = var5.getPrivateExponent();
         BigInteger var9 = var5.getPrime1();
         BigInteger var10 = var5.getPrime2();
         BigInteger var11 = var5.getExponent1();
         BigInteger var12 = var5.getExponent2();
         BigInteger var13 = var5.getCoefficient();
         var65 = new RSAPrivateCrtKeyParameters(var6, var7, var8, var9, var10, var11, var12, var13);
      } else {
         DERObjectIdentifier var14 = var1.getObjectId();
         DERObjectIdentifier var15 = PKCSObjectIdentifiers.dhKeyAgreement;
         if(var14.equals(var15)) {
            ASN1Sequence var16 = (ASN1Sequence)var0.getAlgorithmId().getParameters();
            DHParameter var17 = new DHParameter(var16);
            DERInteger var18 = (DERInteger)var0.getPrivateKey();
            BigInteger var19 = var17.getL();
            int var20;
            if(var19 == null) {
               var20 = 0;
            } else {
               var20 = var19.intValue();
            }

            BigInteger var21 = var17.getP();
            BigInteger var22 = var17.getG();
            DHParameters var23 = new DHParameters(var21, var22, (BigInteger)null, var20);
            BigInteger var24 = var18.getValue();
            var65 = new DHPrivateKeyParameters(var24, var23);
         } else {
            DERObjectIdentifier var25 = var1.getObjectId();
            DERObjectIdentifier var26 = OIWObjectIdentifiers.elGamalAlgorithm;
            if(var25.equals(var26)) {
               ASN1Sequence var27 = (ASN1Sequence)var0.getAlgorithmId().getParameters();
               ElGamalParameter var28 = new ElGamalParameter(var27);
               BigInteger var29 = ((DERInteger)var0.getPrivateKey()).getValue();
               BigInteger var30 = var28.getP();
               BigInteger var31 = var28.getG();
               ElGamalParameters var32 = new ElGamalParameters(var30, var31);
               var65 = new ElGamalPrivateKeyParameters(var29, var32);
            } else {
               DERObjectIdentifier var33 = var1.getObjectId();
               DERObjectIdentifier var34 = X9ObjectIdentifiers.id_dsa;
               if(var33.equals(var34)) {
                  DERInteger var35 = (DERInteger)var0.getPrivateKey();
                  DEREncodable var36 = var0.getAlgorithmId().getParameters();
                  Object var37 = null;
                  DSAParameters var42;
                  if(var36 != null) {
                     DSAParameter var38 = DSAParameter.getInstance(var36.getDERObject());
                     BigInteger var39 = var38.getP();
                     BigInteger var40 = var38.getQ();
                     BigInteger var41 = var38.getG();
                     var42 = new DSAParameters(var39, var40, var41);
                  } else {
                     var42 = (DSAParameters)var37;
                  }

                  BigInteger var43 = var35.getValue();
                  var65 = new DSAPrivateKeyParameters(var43, var42);
               } else {
                  DERObjectIdentifier var44 = var1.getObjectId();
                  DERObjectIdentifier var45 = X9ObjectIdentifiers.id_ecPublicKey;
                  if(!var44.equals(var45)) {
                     throw new RuntimeException("algorithm identifier in key not recognised");
                  }

                  DERObject var46 = (DERObject)var0.getAlgorithmId().getParameters();
                  X962Parameters var47 = new X962Parameters(var46);
                  ECDomainParameters var66;
                  if(var47.isNamedCurve()) {
                     DERObjectIdentifier var48 = (DERObjectIdentifier)var47.getParameters();
                     X9ECParameters var49 = X962NamedCurves.getByOID(var48);
                     X9ECParameters var50;
                     if(var49 == null && SECNamedCurves.getByOID(var48) == null && NISTNamedCurves.getByOID(var48) == null) {
                        var50 = TeleTrusTNamedCurves.getByOID(var48);
                     } else {
                        var50 = var49;
                     }

                     ECCurve var51 = var50.getCurve();
                     ECPoint var52 = var50.getG();
                     BigInteger var53 = var50.getN();
                     BigInteger var54 = var50.getH();
                     byte[] var55 = var50.getSeed();
                     var66 = new ECDomainParameters(var51, var52, var53, var54, var55);
                  } else {
                     ASN1Sequence var58 = (ASN1Sequence)var47.getParameters();
                     X9ECParameters var59 = new X9ECParameters(var58);
                     ECCurve var60 = var59.getCurve();
                     ECPoint var61 = var59.getG();
                     BigInteger var62 = var59.getN();
                     BigInteger var63 = var59.getH();
                     byte[] var64 = var59.getSeed();
                     var66 = new ECDomainParameters(var60, var61, var62, var63, var64);
                  }

                  ASN1Sequence var56 = (ASN1Sequence)var0.getPrivateKey();
                  BigInteger var57 = (new ECPrivateKeyStructure(var56)).getKey();
                  var65 = new ECPrivateKeyParameters(var57, var66);
               }
            }
         }
      }

      return (AsymmetricKeyParameter)var65;
   }

   public static AsymmetricKeyParameter createKey(byte[] var0) throws IOException {
      return createKey(PrivateKeyInfo.getInstance(ASN1Object.fromByteArray(var0)));
   }
}
