package myorg.bouncycastle.x509.examples;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import myorg.bouncycastle.asn1.misc.NetscapeCertType;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.jce.provider.BouncyCastleProvider;
import myorg.bouncycastle.x509.AttributeCertificateHolder;
import myorg.bouncycastle.x509.AttributeCertificateIssuer;
import myorg.bouncycastle.x509.X509Attribute;
import myorg.bouncycastle.x509.X509V1CertificateGenerator;
import myorg.bouncycastle.x509.X509V2AttributeCertificate;
import myorg.bouncycastle.x509.X509V2AttributeCertificateGenerator;
import myorg.bouncycastle.x509.X509V3CertificateGenerator;

public class AttrCertExample {

   static X509V1CertificateGenerator v1CertGen = new X509V1CertificateGenerator();
   static X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();


   public AttrCertExample() {}

   public static X509Certificate createAcIssuerCert(PublicKey var0, PrivateKey var1) throws Exception {
      X509V1CertificateGenerator var2 = v1CertGen;
      BigInteger var3 = BigInteger.valueOf(10L);
      var2.setSerialNumber(var3);
      X509V1CertificateGenerator var4 = v1CertGen;
      X509Principal var5 = new X509Principal("C=AU, O=The Legion of the Bouncy Castle, OU=Bouncy Primary Certificate");
      var4.setIssuerDN((X509Name)var5);
      X509V1CertificateGenerator var6 = v1CertGen;
      long var7 = System.currentTimeMillis() - 2592000000L;
      Date var9 = new Date(var7);
      var6.setNotBefore(var9);
      X509V1CertificateGenerator var10 = v1CertGen;
      long var11 = System.currentTimeMillis() + 2592000000L;
      Date var13 = new Date(var11);
      var10.setNotAfter(var13);
      X509V1CertificateGenerator var14 = v1CertGen;
      X509Principal var15 = new X509Principal("C=AU, O=The Legion of the Bouncy Castle, OU=Bouncy Primary Certificate");
      var14.setSubjectDN((X509Name)var15);
      v1CertGen.setPublicKey(var0);
      v1CertGen.setSignatureAlgorithm("SHA1WithRSAEncryption");
      X509Certificate var16 = v1CertGen.generateX509Certificate(var1);
      Date var17 = new Date();
      var16.checkValidity(var17);
      var16.verify(var0);
      return var16;
   }

   public static X509Certificate createClientCert(PublicKey var0, PrivateKey var1, PublicKey var2) throws Exception {
      Hashtable var3 = new Hashtable();
      Vector var4 = new Vector();
      DERObjectIdentifier var5 = X509Principal.C;
      var3.put(var5, "AU");
      DERObjectIdentifier var7 = X509Principal.O;
      var3.put(var7, "The Legion of the Bouncy Castle");
      DERObjectIdentifier var9 = X509Principal.L;
      var3.put(var9, "Melbourne");
      DERObjectIdentifier var11 = X509Principal.CN;
      var3.put(var11, "Eric H. Echidna");
      DERObjectIdentifier var13 = X509Principal.EmailAddress;
      var3.put(var13, "feedback-crypto@bouncycastle.org");
      DERObjectIdentifier var15 = X509Principal.C;
      var4.addElement(var15);
      DERObjectIdentifier var16 = X509Principal.O;
      var4.addElement(var16);
      DERObjectIdentifier var17 = X509Principal.L;
      var4.addElement(var17);
      DERObjectIdentifier var18 = X509Principal.CN;
      var4.addElement(var18);
      DERObjectIdentifier var19 = X509Principal.EmailAddress;
      var4.addElement(var19);
      v3CertGen.reset();
      X509V3CertificateGenerator var20 = v3CertGen;
      BigInteger var21 = BigInteger.valueOf(20L);
      var20.setSerialNumber(var21);
      X509V3CertificateGenerator var22 = v3CertGen;
      X509Principal var23 = new X509Principal("C=AU, O=The Legion of the Bouncy Castle, OU=Bouncy Primary Certificate");
      var22.setIssuerDN((X509Name)var23);
      X509V3CertificateGenerator var24 = v3CertGen;
      long var25 = System.currentTimeMillis() - 2592000000L;
      Date var27 = new Date(var25);
      var24.setNotBefore(var27);
      X509V3CertificateGenerator var28 = v3CertGen;
      long var29 = System.currentTimeMillis() + 2592000000L;
      Date var31 = new Date(var29);
      var28.setNotAfter(var31);
      X509V3CertificateGenerator var32 = v3CertGen;
      X509Principal var33 = new X509Principal(var4, var3);
      var32.setSubjectDN((X509Name)var33);
      v3CertGen.setPublicKey(var0);
      v3CertGen.setSignatureAlgorithm("SHA1WithRSAEncryption");
      X509V3CertificateGenerator var34 = v3CertGen;
      DERObjectIdentifier var35 = MiscObjectIdentifiers.netscapeCertType;
      NetscapeCertType var36 = new NetscapeCertType(48);
      var34.addExtension(var35, (boolean)0, (DEREncodable)var36);
      X509Certificate var37 = v3CertGen.generateX509Certificate(var1);
      Date var38 = new Date();
      var37.checkValidity(var38);
      var37.verify(var2);
      return var37;
   }

   public static void main(String[] var0) throws Exception {
      int var1 = Security.addProvider(new BouncyCastleProvider());
      BigInteger var2 = new BigInteger("b4a7e46170574f16a97082b22be58b6a2a629798419be12872a4bdba626cfae9900f76abfb12139dce5de56564fab2b6543165a040c606887420e33d91ed7ed7", 16);
      BigInteger var3 = new BigInteger("11", 16);
      RSAPublicKeySpec var4 = new RSAPublicKeySpec(var2, var3);
      BigInteger var5 = new BigInteger("b4a7e46170574f16a97082b22be58b6a2a629798419be12872a4bdba626cfae9900f76abfb12139dce5de56564fab2b6543165a040c606887420e33d91ed7ed7", 16);
      BigInteger var6 = new BigInteger("11", 16);
      BigInteger var7 = new BigInteger("9f66f6b05410cd503b2709e88115d55daced94d1a34d4e32bf824d0dde6028ae79c5f07b580f5dce240d7111f7ddb130a7945cd7d957d1920994da389f490c89", 16);
      BigInteger var8 = new BigInteger("c0a0758cdf14256f78d4708c86becdead1b50ad4ad6c5c703e2168fbf37884cb", 16);
      BigInteger var9 = new BigInteger("f01734d7960ea60070f1b06f2bb81bfac48ff192ae18451d5e56c734a5aab8a5", 16);
      BigInteger var10 = new BigInteger("b54bb9edff22051d9ee60f9351a48591b6500a319429c069a3e335a1d6171391", 16);
      BigInteger var11 = new BigInteger("d3d83daf2a0cecd3367ae6f8ae1aeb82e9ac2f816c6fc483533d8297dd7884cd", 16);
      BigInteger var12 = new BigInteger("b8f52fc6f38593dabb661d3f50f8897f8106eee68b1bce78a95b132b4e5b5d19", 16);
      RSAPrivateCrtKeySpec var13 = new RSAPrivateCrtKeySpec(var5, var6, var7, var8, var9, var10, var11, var12);
      BigInteger var14 = new BigInteger("b259d2d6e627a768c94be36164c2d9fc79d97aab9253140e5bf17751197731d6f7540d2509e7b9ffee0a70a6e26d56e92d2edd7f85aba85600b69089f35f6bdbf3c298e05842535d9f064e6b0391cb7d306e0a2d20c4dfb4e7b49a9640bdea26c10ad69c3f05007ce2513cee44cfe01998e62b6c3637d3fc0391079b26ee36d5", 16);
      BigInteger var15 = new BigInteger("11", 16);
      RSAPublicKeySpec var16 = new RSAPublicKeySpec(var14, var15);
      BigInteger var17 = new BigInteger("b259d2d6e627a768c94be36164c2d9fc79d97aab9253140e5bf17751197731d6f7540d2509e7b9ffee0a70a6e26d56e92d2edd7f85aba85600b69089f35f6bdbf3c298e05842535d9f064e6b0391cb7d306e0a2d20c4dfb4e7b49a9640bdea26c10ad69c3f05007ce2513cee44cfe01998e62b6c3637d3fc0391079b26ee36d5", 16);
      BigInteger var18 = new BigInteger("11", 16);
      BigInteger var19 = new BigInteger("92e08f83cc9920746989ca5034dcb384a094fb9c5a6288fcc4304424ab8f56388f72652d8fafc65a4b9020896f2cde297080f2a540e7b7ce5af0b3446e1258d1dd7f245cf54124b4c6e17da21b90a0ebd22605e6f45c9f136d7a13eaac1c0f7487de8bd6d924972408ebb58af71e76fd7b012a8d0e165f3ae2e5077a8648e619", 16);
      BigInteger var20 = new BigInteger("f75e80839b9b9379f1cf1128f321639757dba514642c206bbbd99f9a4846208b3e93fbbe5e0527cc59b1d4b929d9555853004c7c8b30ee6a213c3d1bb7415d03", 16);
      BigInteger var21 = new BigInteger("b892d9ebdbfc37e397256dd8a5d3123534d1f03726284743ddc6be3a709edb696fc40c7d902ed804c6eee730eee3d5b20bf6bd8d87a296813c87d3b3cc9d7947", 16);
      BigInteger var22 = new BigInteger("1d1a2d3ca8e52068b3094d501c9a842fec37f54db16e9a67070a8b3f53cc03d4257ad252a1a640eadd603724d7bf3737914b544ae332eedf4f34436cac25ceb5", 16);
      BigInteger var23 = new BigInteger("6c929e4e81672fef49d9c825163fec97c4b7ba7acb26c0824638ac22605d7201c94625770984f78a56e6e25904fe7db407099cad9b14588841b94f5ab498dded", 16);
      BigInteger var24 = new BigInteger("dae7651ee69ad1d081ec5e7188ae126f6004ff39556bde90e0b870962fa7b926d070686d8244fe5a9aa709a95686a104614834b0ada4b10f53197a5cb4c97339", 16);
      RSAPrivateCrtKeySpec var25 = new RSAPrivateCrtKeySpec(var17, var18, var19, var20, var21, var22, var23, var24);
      KeyFactory var26 = KeyFactory.getInstance("RSA", "myBC");
      PrivateKey var27 = var26.generatePrivate(var25);
      PublicKey var28 = var26.generatePublic(var16);
      var26.generatePrivate(var13);
      PublicKey var30 = var26.generatePublic(var4);
      X509Certificate var31 = createAcIssuerCert(var28, var27);
      X509Certificate var32 = createClientCert(var30, var27, var28);
      X509V2AttributeCertificateGenerator var33 = new X509V2AttributeCertificateGenerator();
      var33.reset();
      AttributeCertificateHolder var34 = new AttributeCertificateHolder(var32);
      var33.setHolder(var34);
      X500Principal var35 = var31.getSubjectX500Principal();
      AttributeCertificateIssuer var36 = new AttributeCertificateIssuer(var35);
      var33.setIssuer(var36);
      BigInteger var37 = new BigInteger("1");
      var33.setSerialNumber(var37);
      long var38 = System.currentTimeMillis() - 50000L;
      Date var40 = new Date(var38);
      var33.setNotBefore(var40);
      long var41 = System.currentTimeMillis() + 50000L;
      Date var43 = new Date(var41);
      var33.setNotAfter(var43);
      var33.setSignatureAlgorithm("SHA1WithRSAEncryption");
      GeneralName var44 = new GeneralName(1, "DAU123456789");
      ASN1EncodableVector var45 = new ASN1EncodableVector();
      var45.add(var44);
      DERSequence var46 = new DERSequence(var45);
      X509Attribute var47 = new X509Attribute("2.5.24.72", var46);
      var33.addAttribute(var47);
      X509V2AttributeCertificate var48 = (X509V2AttributeCertificate)var33.generateCertificate(var27, "myBC");
      AttributeCertificateHolder var49 = var48.getHolder();
      if(var49.match((Certificate)var32)) {
         if(var49.getEntityNames() != null) {
            PrintStream var50 = System.out;
            StringBuilder var51 = new StringBuilder();
            int var52 = var49.getEntityNames().length;
            String var53 = var51.append(var52).append(" entity names found").toString();
            var50.println(var53);
         }

         if(var49.getIssuer() != null) {
            PrintStream var54 = System.out;
            StringBuilder var55 = new StringBuilder();
            int var56 = var49.getIssuer().length;
            StringBuilder var57 = var55.append(var56).append(" issuer names found, serial number ");
            BigInteger var58 = var49.getSerialNumber();
            String var59 = var57.append(var58).toString();
            var54.println(var59);
         }

         System.out.println("Matches original client x509 cert");
      }

      AttributeCertificateIssuer var60 = var48.getIssuer();
      if(var60.match((Certificate)var31)) {
         if(var60.getPrincipals() != null) {
            PrintStream var61 = System.out;
            StringBuilder var62 = new StringBuilder();
            int var63 = var60.getPrincipals().length;
            String var64 = var62.append(var63).append(" entity names found").toString();
            var61.println(var64);
         }

         System.out.println("Matches original ca x509 cert");
      }

      PrintStream var65 = System.out;
      StringBuilder var66 = (new StringBuilder()).append("valid not before: ");
      Date var67 = var48.getNotBefore();
      String var68 = var66.append(var67).toString();
      var65.println(var68);
      PrintStream var69 = System.out;
      StringBuilder var70 = (new StringBuilder()).append("valid not before: ");
      Date var71 = var48.getNotAfter();
      String var72 = var70.append(var71).toString();
      var69.println(var72);

      try {
         var48.checkValidity();
         Date var73 = new Date();
         var48.checkValidity(var73);
      } catch (Exception var89) {
         System.out.println(var89);
      }

      try {
         var48.verify(var28, "myBC");
      } catch (Exception var88) {
         System.out.println(var88);
      }

      X509Attribute[] var74 = var48.getAttributes();
      PrintStream var75 = System.out;
      StringBuilder var76 = (new StringBuilder()).append("cert has ");
      int var77 = var74.length;
      String var78 = var76.append(var77).append(" attributes:").toString();
      var75.println(var78);
      int var79 = 0;

      while(true) {
         int var80 = var74.length;
         if(var79 >= var80) {
            return;
         }

         X509Attribute var81 = var74[var79];
         PrintStream var82 = System.out;
         StringBuilder var83 = (new StringBuilder()).append("OID: ");
         String var84 = var81.getOID();
         String var85 = var83.append(var84).toString();
         var82.println(var85);
         if(var81.getOID().equals("2.5.24.72")) {
            System.out.println("rolesyntax read from cert!");
         }

         ++var79;
      }
   }
}
