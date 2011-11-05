package myorg.bouncycastle.openssl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.StringTokenizer;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import myorg.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.jce.ECNamedCurveTable;
import myorg.bouncycastle.jce.PKCS10CertificationRequest;
import myorg.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import myorg.bouncycastle.openssl.PEMException;
import myorg.bouncycastle.openssl.PEMUtilities;
import myorg.bouncycastle.openssl.PasswordException;
import myorg.bouncycastle.openssl.PasswordFinder;
import myorg.bouncycastle.util.encoders.Base64;
import myorg.bouncycastle.util.encoders.Hex;
import myorg.bouncycastle.x509.X509AttributeCertificate;
import myorg.bouncycastle.x509.X509V2AttributeCertificate;

public class PEMReader extends BufferedReader {

   private final PasswordFinder pFinder;
   private final String provider;


   public PEMReader(Reader var1) {
      this(var1, (PasswordFinder)null, "myBC");
   }

   public PEMReader(Reader var1, PasswordFinder var2) {
      this(var1, var2, "myBC");
   }

   public PEMReader(Reader var1, PasswordFinder var2, String var3) {
      super(var1);
      this.pFinder = var2;
      this.provider = var3;
   }

   private X509AttributeCertificate readAttributeCertificate(String var1) throws IOException {
      byte[] var2 = this.readBytes(var1);
      return new X509V2AttributeCertificate(var2);
   }

   private byte[] readBytes(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      while(true) {
         String var3 = this.readLine();
         if(var3 == null || var3.indexOf(var1) != -1) {
            if(var3 == null) {
               String var4 = var1 + " not found";
               throw new IOException(var4);
            } else {
               return Base64.decode(var2.toString());
            }
         }

         String var5 = var3.trim();
         var2.append(var5);
      }
   }

   private X509CRL readCRL(String var1) throws IOException {
      byte[] var2 = this.readBytes(var1);
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);

      try {
         String var4 = this.provider;
         X509CRL var10 = (X509CRL)CertificateFactory.getInstance("X.509", var4).generateCRL(var3);
         return var10;
      } catch (Exception var9) {
         StringBuilder var6 = (new StringBuilder()).append("problem parsing cert: ");
         String var7 = var9.toString();
         String var8 = var6.append(var7).toString();
         throw new PEMException(var8, var9);
      }
   }

   private X509Certificate readCertificate(String var1) throws IOException {
      byte[] var2 = this.readBytes(var1);
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);

      try {
         String var4 = this.provider;
         X509Certificate var10 = (X509Certificate)CertificateFactory.getInstance("X.509", var4).generateCertificate(var3);
         return var10;
      } catch (Exception var9) {
         StringBuilder var6 = (new StringBuilder()).append("problem parsing cert: ");
         String var7 = var9.toString();
         String var8 = var6.append(var7).toString();
         throw new PEMException(var8, var9);
      }
   }

   private PKCS10CertificationRequest readCertificateRequest(String var1) throws IOException {
      try {
         byte[] var2 = this.readBytes(var1);
         PKCS10CertificationRequest var3 = new PKCS10CertificationRequest(var2);
         return var3;
      } catch (Exception var8) {
         StringBuilder var5 = (new StringBuilder()).append("problem parsing certrequest: ");
         String var6 = var8.toString();
         String var7 = var5.append(var6).toString();
         throw new PEMException(var7, var8);
      }
   }

   private ECNamedCurveParameterSpec readECParameters(String var1) throws IOException {
      return ECNamedCurveTable.getParameterSpec(((DERObjectIdentifier)ASN1Object.fromByteArray(this.readBytes(var1))).getId());
   }

   private KeyPair readKeyPair(String var1, String var2) throws Exception {
      boolean var3 = false;
      String var4 = null;
      StringBuffer var5 = new StringBuffer();

      while(true) {
         String var6 = this.readLine();
         if(var6 == null) {
            break;
         }

         String var8 = "Proc-Type: 4,ENCRYPTED";
         if(var6.startsWith(var8)) {
            var3 = true;
         } else {
            String var10 = "DEK-Info:";
            if(var6.startsWith(var10)) {
               byte var12 = 10;
               var4 = var6.substring(var12);
            } else {
               if(var6.indexOf(var2) != -1) {
                  break;
               }

               String var16 = var6.trim();
               StringBuffer var19 = var5.append(var16);
            }
         }
      }

      byte[] var15 = Base64.decode(var5.toString());
      if(var3) {
         if(this.pFinder == null) {
            throw new PasswordException("No password finder specified, but a password is required");
         }

         char[] var20 = this.pFinder.getPassword();
         if(var20 == null) {
            throw new PasswordException("Password is null, but a password is required");
         }

         StringTokenizer var21 = new StringTokenizer;
         String var24 = ",";
         var21.<init>(var4, var24);
         String var25 = var21.nextToken();
         byte[] var26 = Hex.decode(var21.nextToken());
         String var27 = this.provider;
         var15 = PEMUtilities.crypt((boolean)0, var27, var15, var20, var25, var26);
      }

      ASN1Sequence var28 = (ASN1Sequence)ASN1Object.fromByteArray(var15);
      String var30 = "RSA";
      Object var55;
      Object var69;
      if(var1.equals(var30)) {
         byte var32 = 1;
         DERInteger var33 = (DERInteger)var28.getObjectAt(var32);
         byte var35 = 2;
         DERInteger var36 = (DERInteger)var28.getObjectAt(var35);
         byte var38 = 3;
         DERInteger var39 = (DERInteger)var28.getObjectAt(var38);
         byte var41 = 4;
         DERInteger var42 = (DERInteger)var28.getObjectAt(var41);
         byte var44 = 5;
         DERInteger var45 = (DERInteger)var28.getObjectAt(var44);
         byte var47 = 6;
         DERInteger var48 = (DERInteger)var28.getObjectAt(var47);
         byte var50 = 7;
         DERInteger var51 = (DERInteger)var28.getObjectAt(var50);
         byte var53 = 8;
         DERInteger var54 = (DERInteger)var28.getObjectAt(var53);
         var55 = new RSAPublicKeySpec;
         BigInteger var56 = var33.getValue();
         BigInteger var57 = var36.getValue();
         var55.<init>(var56, var57);
         BigInteger var61 = var33.getValue();
         BigInteger var62 = var36.getValue();
         BigInteger var63 = var39.getValue();
         BigInteger var64 = var42.getValue();
         BigInteger var65 = var45.getValue();
         BigInteger var66 = var48.getValue();
         BigInteger var67 = var51.getValue();
         BigInteger var68 = var54.getValue();
         var69 = new RSAPrivateCrtKeySpec(var61, var62, var63, var64, var65, var66, var67, var68);
      } else {
         String var81 = "ECDSA";
         if(var1.equals(var81)) {
            ECPrivateKeyStructure var82 = new ECPrivateKeyStructure(var28);
            AlgorithmIdentifier var85 = new AlgorithmIdentifier;
            DERObjectIdentifier var86 = X9ObjectIdentifiers.id_ecPublicKey;
            ASN1Object var87 = var82.getParameters();
            var85.<init>(var86, var87);
            PrivateKeyInfo var91 = new PrivateKeyInfo;
            DERObject var92 = var82.getDERObject();
            var91.<init>(var85, var92);
            SubjectPublicKeyInfo var96 = new SubjectPublicKeyInfo;
            byte[] var97 = var82.getPublicKey().getBytes();
            var96.<init>(var85, var97);
            byte[] var101 = var91.getEncoded();
            var69 = new PKCS8EncodedKeySpec(var101);
            var55 = new X509EncodedKeySpec;
            byte[] var102 = var96.getEncoded();
            var55.<init>(var102);
         } else {
            byte var106 = 1;
            DERInteger var107 = (DERInteger)var28.getObjectAt(var106);
            byte var109 = 2;
            DERInteger var110 = (DERInteger)var28.getObjectAt(var109);
            byte var112 = 3;
            DERInteger var113 = (DERInteger)var28.getObjectAt(var112);
            byte var115 = 4;
            DERInteger var116 = (DERInteger)var28.getObjectAt(var115);
            byte var118 = 5;
            BigInteger var119 = ((DERInteger)var28.getObjectAt(var118)).getValue();
            BigInteger var120 = var107.getValue();
            BigInteger var121 = var110.getValue();
            BigInteger var122 = var113.getValue();
            var69 = new DSAPrivateKeySpec(var119, var120, var121, var122);
            var55 = new DSAPublicKeySpec;
            BigInteger var123 = var116.getValue();
            BigInteger var124 = var107.getValue();
            BigInteger var125 = var110.getValue();
            BigInteger var126 = var113.getValue();
            var55.<init>(var123, var124, var125, var126);
         }
      }

      String var70 = this.provider;
      KeyFactory var73 = KeyFactory.getInstance(var1, var70);
      PublicKey var76 = var73.generatePublic((KeySpec)var55);
      PrivateKey var79 = var73.generatePrivate((KeySpec)var69);
      return new KeyPair(var76, var79);
   }

   private ContentInfo readPKCS7(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      while(true) {
         String var4 = this.readLine();
         if(var4 == null || var4.indexOf(var1) != -1) {
            if(var2.length() != 0) {
               throw new IOException("base64 data appears to be truncated");
            } else if(var4 == null) {
               String var11 = var1 + " not found";
               throw new IOException(var11);
            } else {
               try {
                  byte[] var12 = var3.toByteArray();
                  ContentInfo var13 = ContentInfo.getInstance((new ASN1InputStream(var12)).readObject());
                  return var13;
               } catch (Exception var18) {
                  StringBuilder var15 = (new StringBuilder()).append("problem parsing PKCS7 object: ");
                  String var16 = var18.toString();
                  String var17 = var15.append(var16).toString();
                  throw new PEMException(var17, var18);
               }
            }
         }

         String var5 = var4.trim().trim();
         var2.append(var5);
         int var7 = var2.length() / 4 * 4;
         int var8 = Base64.decode(var2.substring(0, var7), var3);
         int var9 = var2.length() / 4 * 4;
         var2.delete(0, var9);
      }
   }

   private PublicKey readPublicKey(String var1) throws IOException {
      byte[] var2 = this.readBytes(var1);
      X509EncodedKeySpec var3 = new X509EncodedKeySpec(var2);
      String[] var4 = new String[]{"DSA", "RSA"};
      int var5 = 0;

      PublicKey var10;
      while(true) {
         int var6 = var4.length;
         if(var5 < var6) {
            PublicKey var9;
            label21: {
               try {
                  String var7 = var4[var5];
                  String var8 = this.provider;
                  var9 = KeyFactory.getInstance(var7, var8).generatePublic(var3);
                  break label21;
               } catch (NoSuchAlgorithmException var17) {
                  ;
               } catch (InvalidKeySpecException var18) {
                  ;
               } catch (NoSuchProviderException var19) {
                  StringBuilder var12 = (new StringBuilder()).append("can\'t find provider ");
                  String var13 = this.provider;
                  String var14 = var12.append(var13).toString();
                  throw new RuntimeException(var14);
               }

               ++var5;
               continue;
            }

            var10 = var9;
            break;
         }

         var10 = null;
         break;
      }

      return var10;
   }

   private PublicKey readRSAPublicKey(String var1) throws IOException {
      byte[] var2 = this.readBytes(var1);
      ASN1Sequence var3 = (ASN1Sequence)(new ASN1InputStream(var2)).readObject();
      RSAPublicKeyStructure var4 = new RSAPublicKeyStructure(var3);
      BigInteger var5 = var4.getModulus();
      BigInteger var6 = var4.getPublicExponent();
      RSAPublicKeySpec var7 = new RSAPublicKeySpec(var5, var6);

      try {
         String var8 = this.provider;
         PublicKey var9 = KeyFactory.getInstance("RSA", var8).generatePublic(var7);
         return var9;
      } catch (NoSuchProviderException var18) {
         StringBuilder var11 = (new StringBuilder()).append("can\'t find provider ");
         String var12 = this.provider;
         String var13 = var11.append(var12).toString();
         throw new IOException(var13);
      } catch (Exception var19) {
         StringBuilder var15 = (new StringBuilder()).append("problem extracting key: ");
         String var16 = var19.toString();
         String var17 = var15.append(var16).toString();
         throw new PEMException(var17, var19);
      }
   }

   public Object readObject() throws IOException {
      while(true) {
         String var1 = this.readLine();
         Object var2;
         if(var1 != null) {
            if(var1.indexOf("-----BEGIN PUBLIC KEY") != -1) {
               var2 = this.readPublicKey("-----END PUBLIC KEY");
            } else if(var1.indexOf("-----BEGIN RSA PUBLIC KEY") != -1) {
               var2 = this.readRSAPublicKey("-----END RSA PUBLIC KEY");
            } else if(var1.indexOf("-----BEGIN CERTIFICATE REQUEST") != -1) {
               var2 = this.readCertificateRequest("-----END CERTIFICATE REQUEST");
            } else if(var1.indexOf("-----BEGIN NEW CERTIFICATE REQUEST") != -1) {
               var2 = this.readCertificateRequest("-----END NEW CERTIFICATE REQUEST");
            } else if(var1.indexOf("-----BEGIN CERTIFICATE") != -1) {
               var2 = this.readCertificate("-----END CERTIFICATE");
            } else if(var1.indexOf("-----BEGIN PKCS7") != -1) {
               var2 = this.readPKCS7("-----END PKCS7");
            } else if(var1.indexOf("-----BEGIN X509 CERTIFICATE") != -1) {
               var2 = this.readCertificate("-----END X509 CERTIFICATE");
            } else if(var1.indexOf("-----BEGIN X509 CRL") != -1) {
               var2 = this.readCRL("-----END X509 CRL");
            } else if(var1.indexOf("-----BEGIN ATTRIBUTE CERTIFICATE") != -1) {
               var2 = this.readAttributeCertificate("-----END ATTRIBUTE CERTIFICATE");
            } else {
               KeyPair var3;
               if(var1.indexOf("-----BEGIN RSA PRIVATE KEY") != -1) {
                  try {
                     var3 = this.readKeyPair("RSA", "-----END RSA PRIVATE KEY");
                  } catch (IOException var20) {
                     throw var20;
                  } catch (Exception var21) {
                     StringBuilder var5 = (new StringBuilder()).append("problem creating RSA private key: ");
                     String var6 = var21.toString();
                     String var7 = var5.append(var6).toString();
                     throw new PEMException(var7, var21);
                  }

                  var2 = var3;
               } else if(var1.indexOf("-----BEGIN DSA PRIVATE KEY") != -1) {
                  try {
                     var3 = this.readKeyPair("DSA", "-----END DSA PRIVATE KEY");
                  } catch (IOException var18) {
                     throw var18;
                  } catch (Exception var19) {
                     StringBuilder var9 = (new StringBuilder()).append("problem creating DSA private key: ");
                     String var10 = var19.toString();
                     String var11 = var9.append(var10).toString();
                     throw new PEMException(var11, var19);
                  }

                  var2 = var3;
               } else if(var1.indexOf("-----BEGIN EC PARAMETERS-----") != -1) {
                  var2 = this.readECParameters("-----END EC PARAMETERS-----");
               } else {
                  if(var1.indexOf("-----BEGIN EC PRIVATE KEY-----") == -1) {
                     continue;
                  }

                  try {
                     var3 = this.readKeyPair("ECDSA", "-----END EC PRIVATE KEY-----");
                  } catch (IOException var16) {
                     throw var16;
                  } catch (Exception var17) {
                     StringBuilder var13 = (new StringBuilder()).append("problem creating ECDSA private key: ");
                     String var14 = var17.toString();
                     String var15 = var13.append(var14).toString();
                     throw new PEMException(var15, var17);
                  }

                  var2 = var3;
               }
            }
         } else {
            var2 = null;
         }

         return var2;
      }
   }
}
