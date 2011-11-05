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
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.nist.NISTNamedCurves;
import myorg.bouncycastle.asn1.oiw.ElGamalParameter;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.DHParameter;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X962NamedCurves;
import myorg.bouncycastle.asn1.x9.X962Parameters;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ECPoint;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.DHParameters;
import myorg.bouncycastle.crypto.params.DHPublicKeyParameters;
import myorg.bouncycastle.crypto.params.DSAParameters;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;
import myorg.bouncycastle.crypto.params.ElGamalParameters;
import myorg.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import myorg.bouncycastle.crypto.params.RSAKeyParameters;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class PublicKeyFactory {

   public PublicKeyFactory() {}

   public static AsymmetricKeyParameter createKey(InputStream var0) throws IOException {
      return createKey(SubjectPublicKeyInfo.getInstance((new ASN1InputStream(var0)).readObject()));
   }

   public static AsymmetricKeyParameter createKey(SubjectPublicKeyInfo var0) throws IOException {
      AlgorithmIdentifier var1 = var0.getAlgorithmId();
      DERObjectIdentifier var2 = var1.getObjectId();
      DERObjectIdentifier var3 = PKCSObjectIdentifiers.rsaEncryption;
      Object var67;
      if(!var2.equals(var3)) {
         DERObjectIdentifier var4 = var1.getObjectId();
         DERObjectIdentifier var5 = X509ObjectIdentifiers.id_ea_rsa;
         if(!var4.equals(var5)) {
            DERObjectIdentifier var10 = var1.getObjectId();
            DERObjectIdentifier var11 = PKCSObjectIdentifiers.dhKeyAgreement;
            if(!var10.equals(var11)) {
               DERObjectIdentifier var12 = var1.getObjectId();
               DERObjectIdentifier var13 = X9ObjectIdentifiers.dhpublicnumber;
               if(!var12.equals(var13)) {
                  DERObjectIdentifier var23 = var1.getObjectId();
                  DERObjectIdentifier var24 = OIWObjectIdentifiers.elGamalAlgorithm;
                  if(var23.equals(var24)) {
                     ASN1Sequence var25 = (ASN1Sequence)var0.getAlgorithmId().getParameters();
                     ElGamalParameter var26 = new ElGamalParameter(var25);
                     BigInteger var27 = ((DERInteger)var0.getPublicKey()).getValue();
                     BigInteger var28 = var26.getP();
                     BigInteger var29 = var26.getG();
                     ElGamalParameters var30 = new ElGamalParameters(var28, var29);
                     var67 = new ElGamalPublicKeyParameters(var27, var30);
                  } else {
                     DERObjectIdentifier var31 = var1.getObjectId();
                     DERObjectIdentifier var32 = X9ObjectIdentifiers.id_dsa;
                     if(!var31.equals(var32)) {
                        DERObjectIdentifier var33 = var1.getObjectId();
                        DERObjectIdentifier var34 = OIWObjectIdentifiers.dsaWithSHA1;
                        if(!var33.equals(var34)) {
                           DERObjectIdentifier var44 = var1.getObjectId();
                           DERObjectIdentifier var45 = X9ObjectIdentifiers.id_ecPublicKey;
                           if(!var44.equals(var45)) {
                              throw new RuntimeException("algorithm identifier in key not recognised");
                           }

                           DERObject var46 = (DERObject)var0.getAlgorithmId().getParameters();
                           X962Parameters var47 = new X962Parameters(var46);
                           ECDomainParameters var68;
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
                              var68 = new ECDomainParameters(var51, var52, var53, var54, var55);
                           } else {
                              ASN1Sequence var60 = (ASN1Sequence)var47.getParameters();
                              X9ECParameters var61 = new X9ECParameters(var60);
                              ECCurve var62 = var61.getCurve();
                              ECPoint var63 = var61.getG();
                              BigInteger var64 = var61.getN();
                              BigInteger var65 = var61.getH();
                              byte[] var66 = var61.getSeed();
                              var68 = new ECDomainParameters(var62, var63, var64, var65, var66);
                           }

                           byte[] var56 = var0.getPublicKeyData().getBytes();
                           DEROctetString var57 = new DEROctetString(var56);
                           ECCurve var58 = var68.getCurve();
                           ECPoint var59 = (new X9ECPoint(var58, var57)).getPoint();
                           var67 = new ECPublicKeyParameters(var59, var68);
                           return (AsymmetricKeyParameter)var67;
                        }
                     }

                     DERInteger var35 = (DERInteger)var0.getPublicKey();
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
                     var67 = new DSAPublicKeyParameters(var43, var42);
                  }

                  return (AsymmetricKeyParameter)var67;
               }
            }

            ASN1Sequence var14 = (ASN1Sequence)var0.getAlgorithmId().getParameters();
            DHParameter var15 = new DHParameter(var14);
            DERInteger var16 = (DERInteger)var0.getPublicKey();
            BigInteger var17 = var15.getL();
            int var18;
            if(var17 == null) {
               var18 = 0;
            } else {
               var18 = var17.intValue();
            }

            BigInteger var19 = var15.getP();
            BigInteger var20 = var15.getG();
            DHParameters var21 = new DHParameters(var19, var20, (BigInteger)null, var18);
            BigInteger var22 = var16.getValue();
            var67 = new DHPublicKeyParameters(var22, var21);
            return (AsymmetricKeyParameter)var67;
         }
      }

      ASN1Sequence var6 = (ASN1Sequence)var0.getPublicKey();
      RSAPublicKeyStructure var7 = new RSAPublicKeyStructure(var6);
      BigInteger var8 = var7.getModulus();
      BigInteger var9 = var7.getPublicExponent();
      var67 = new RSAKeyParameters((boolean)0, var8, var9);
      return (AsymmetricKeyParameter)var67;
   }

   public static AsymmetricKeyParameter createKey(byte[] var0) throws IOException {
      return createKey(SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(var0)));
   }
}
