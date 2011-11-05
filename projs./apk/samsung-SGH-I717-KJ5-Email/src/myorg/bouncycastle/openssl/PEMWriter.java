package myorg.bouncycastle.openssl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.jce.PKCS10CertificationRequest;
import myorg.bouncycastle.openssl.PEMUtilities;
import myorg.bouncycastle.util.Strings;
import myorg.bouncycastle.util.encoders.Base64;
import myorg.bouncycastle.util.encoders.Hex;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509V2AttributeCertificate;

public class PEMWriter extends BufferedWriter {

   private String provider;


   public PEMWriter(Writer var1) {
      this(var1, "myBC");
   }

   public PEMWriter(Writer var1, String var2) {
      super(var1);
      this.provider = var2;
   }

   private void writeEncoded(byte[] var1) throws IOException {
      char[] var2 = new char[64];
      byte[] var3 = Base64.encode(var1);
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return;
         }

         int var6 = 0;

         while(true) {
            int var7 = var2.length;
            if(var6 != var7) {
               int var8 = var4 + var6;
               int var9 = var3.length;
               if(var8 < var9) {
                  int var11 = var4 + var6;
                  char var12 = (char)var3[var11];
                  var2[var6] = var12;
                  ++var6;
                  continue;
               }
            }

            this.write(var2, 0, var6);
            this.newLine();
            int var10 = var2.length;
            var4 += var10;
            break;
         }
      }
   }

   private void writeFooter(String var1) throws IOException {
      String var2 = "-----END " + var1 + "-----";
      this.write(var2);
      this.newLine();
   }

   private void writeHeader(String var1) throws IOException {
      String var2 = "-----BEGIN " + var1 + "-----";
      this.write(var2);
      this.newLine();
   }

   private void writeHexEncoded(byte[] var1) throws IOException {
      byte[] var2 = Hex.encode(var1);
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return;
         }

         char var5 = (char)var2[var3];
         this.write(var5);
         ++var3;
      }
   }

   public void writeObject(Object var1) throws IOException {
      String var2;
      byte[] var3;
      byte[] var4;
      if(var1 instanceof X509Certificate) {
         var2 = "CERTIFICATE";

         try {
            var3 = ((X509Certificate)var1).getEncoded();
         } catch (CertificateEncodingException var32) {
            StringBuilder var6 = (new StringBuilder()).append("Cannot encode object: ");
            String var7 = var32.toString();
            String var8 = var6.append(var7).toString();
            throw new IOException(var8);
         }

         var4 = var3;
      } else if(var1 instanceof X509CRL) {
         var2 = "X509 CRL";

         try {
            var3 = ((X509CRL)var1).getEncoded();
         } catch (CRLException var31) {
            StringBuilder var10 = (new StringBuilder()).append("Cannot encode object: ");
            String var11 = var31.toString();
            String var12 = var10.append(var11).toString();
            throw new IOException(var12);
         }

         var4 = var3;
      } else {
         if(var1 instanceof KeyPair) {
            PrivateKey var13 = ((KeyPair)var1).getPrivate();
            this.writeObject(var13);
            return;
         }

         if(var1 instanceof PrivateKey) {
            ASN1Sequence var14 = (ASN1Sequence)ASN1Object.fromByteArray(((Key)var1).getEncoded());
            PrivateKeyInfo var15 = new PrivateKeyInfo(var14);
            if(var1 instanceof RSAPrivateKey) {
               var2 = "RSA PRIVATE KEY";
               var4 = var15.getPrivateKey().getEncoded();
            } else if(var1 instanceof DSAPrivateKey) {
               var2 = "DSA PRIVATE KEY";
               DSAParameter var16 = DSAParameter.getInstance(var15.getAlgorithmId().getParameters());
               ASN1EncodableVector var17 = new ASN1EncodableVector();
               DERInteger var18 = new DERInteger(0);
               var17.add(var18);
               BigInteger var19 = var16.getP();
               DERInteger var20 = new DERInteger(var19);
               var17.add(var20);
               BigInteger var21 = var16.getQ();
               DERInteger var22 = new DERInteger(var21);
               var17.add(var22);
               BigInteger var23 = var16.getG();
               DERInteger var24 = new DERInteger(var23);
               var17.add(var24);
               BigInteger var25 = ((DSAPrivateKey)var1).getX();
               BigInteger var26 = var16.getG();
               BigInteger var27 = var16.getP();
               BigInteger var28 = var26.modPow(var25, var27);
               DERInteger var29 = new DERInteger(var28);
               var17.add(var29);
               DERInteger var30 = new DERInteger(var25);
               var17.add(var30);
               var4 = (new DERSequence(var17)).getEncoded();
            } else {
               if(!((PrivateKey)var1).getAlgorithm().equals("ECDSA")) {
                  throw new IOException("Cannot identify private key");
               }

               var2 = "EC PRIVATE KEY";
               var4 = var15.getPrivateKey().getEncoded();
            }
         } else if(var1 instanceof PublicKey) {
            var2 = "PUBLIC KEY";
            var4 = ((PublicKey)var1).getEncoded();
         } else if(var1 instanceof X509AttributeCertificate) {
            var2 = "ATTRIBUTE CERTIFICATE";
            var4 = ((X509V2AttributeCertificate)var1).getEncoded();
         } else if(var1 instanceof PKCS10CertificationRequest) {
            var2 = "CERTIFICATE REQUEST";
            var4 = ((PKCS10CertificationRequest)var1).getEncoded();
         } else {
            if(!(var1 instanceof ContentInfo)) {
               throw new IOException("unknown object passed - can\'t encode.");
            }

            var2 = "PKCS7";
            var4 = ((ContentInfo)var1).getEncoded();
         }
      }

      this.writeHeader(var2);
      this.writeEncoded(var4);
      this.writeFooter(var2);
   }

   public void writeObject(Object var1, String var2, char[] var3, SecureRandom var4) throws IOException {
      if(var1 instanceof KeyPair) {
         PrivateKey var5 = ((KeyPair)var1).getPrivate();
         this.writeObject(var5);
      } else {
         String var8 = null;
         byte[] var9 = null;
         if(var1 instanceof RSAPrivateCrtKey) {
            var8 = "RSA PRIVATE KEY";
            RSAPrivateCrtKey var10 = (RSAPrivateCrtKey)var1;
            BigInteger var11 = var10.getModulus();
            BigInteger var12 = var10.getPublicExponent();
            BigInteger var13 = var10.getPrivateExponent();
            BigInteger var14 = var10.getPrimeP();
            BigInteger var15 = var10.getPrimeQ();
            BigInteger var16 = var10.getPrimeExponentP();
            BigInteger var17 = var10.getPrimeExponentQ();
            BigInteger var18 = var10.getCrtCoefficient();
            var9 = (new RSAPrivateKeyStructure(var11, var12, var13, var14, var15, var16, var17, var18)).getEncoded();
         } else if(var1 instanceof DSAPrivateKey) {
            var8 = "DSA PRIVATE KEY";
            DSAPrivateKey var22 = (DSAPrivateKey)var1;
            DSAParams var23 = var22.getParams();
            ASN1EncodableVector var24 = new ASN1EncodableVector();
            DERInteger var25 = new DERInteger(0);
            var24.add(var25);
            BigInteger var28 = var23.getP();
            DERInteger var29 = new DERInteger(var28);
            var24.add(var29);
            BigInteger var32 = var23.getQ();
            DERInteger var33 = new DERInteger(var32);
            var24.add(var33);
            BigInteger var36 = var23.getG();
            DERInteger var37 = new DERInteger(var36);
            var24.add(var37);
            BigInteger var40 = var22.getX();
            BigInteger var41 = var23.getG();
            BigInteger var42 = var23.getP();
            BigInteger var46 = var41.modPow(var40, var42);
            DERInteger var47 = new DERInteger(var46);
            var24.add(var47);
            DERInteger var52 = new DERInteger(var40);
            var24.add(var52);
            DERSequence var57 = new DERSequence(var24);
            var9 = var57.getEncoded();
         } else if(var1 instanceof PrivateKey) {
            String var60 = ((PrivateKey)var1).getAlgorithm();
            if("ECDSA".equals(var60)) {
               var8 = "EC PRIVATE KEY";
               var9 = PrivateKeyInfo.getInstance(ASN1Object.fromByteArray(((PrivateKey)var1).getEncoded())).getPrivateKey().getEncoded();
            }
         }

         if(var8 != null && var9 != null) {
            String var61 = Strings.toUpperCase(var2);
            if(var61.equals("DESEDE")) {
               var61 = "DES-EDE3-CBC";
            }

            byte var62;
            if(var61.startsWith("AES-")) {
               var62 = 16;
            } else {
               var62 = 8;
            }

            byte[] var63 = new byte[var62];
            var4.nextBytes(var63);
            String var66 = this.provider;
            byte[] var68 = PEMUtilities.crypt((boolean)1, var66, var9, var3, var61, var63);
            this.writeHeader(var8);
            String var72 = "Proc-Type: 4,ENCRYPTED";
            this.write(var72);
            this.newLine();
            String var73 = "DEK-Info: " + var61 + ",";
            this.write(var73);
            this.writeHexEncoded(var63);
            this.newLine();
            this.newLine();
            this.writeEncoded(var68);
            this.writeFooter(var8);
         } else {
            StringBuilder var19 = (new StringBuilder()).append("Object type not supported: ");
            String var20 = var1.getClass().getName();
            String var21 = var19.append(var20).toString();
            throw new IllegalArgumentException(var21);
         }
      }
   }
}
