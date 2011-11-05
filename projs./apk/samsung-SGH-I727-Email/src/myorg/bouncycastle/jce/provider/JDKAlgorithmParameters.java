package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import myorg.bouncycastle.asn1.oiw.ElGamalParameter;
import myorg.bouncycastle.asn1.pkcs.DHParameter;
import myorg.bouncycastle.asn1.pkcs.PBKDF2Params;
import myorg.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.RC2CBCParameter;
import myorg.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import myorg.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.jce.provider.JCEDigestUtil;
import myorg.bouncycastle.jce.spec.ElGamalParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410ParameterSpec;
import myorg.bouncycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import myorg.bouncycastle.jce.spec.IESParameterSpec;
import myorg.bouncycastle.util.Arrays;

public abstract class JDKAlgorithmParameters extends AlgorithmParametersSpi {

   public JDKAlgorithmParameters() {}

   protected AlgorithmParameterSpec engineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
      if(var1 == null) {
         throw new NullPointerException("argument to getParameterSpec must not be null");
      } else {
         return this.localEngineGetParameterSpec(var1);
      }
   }

   protected boolean isASN1FormatString(String var1) {
      boolean var2;
      if(var1 != null && !var1.equals("ASN.1")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException;

   public static class PKCS12PBE extends JDKAlgorithmParameters {

      PKCS12PBEParams params;


      public PKCS12PBE() {}

      protected byte[] engineGetEncoded() {
         try {
            byte[] var1 = this.params.getEncoded("DER");
            return var1;
         } catch (IOException var6) {
            StringBuilder var3 = (new StringBuilder()).append("Oooops! ");
            String var4 = var6.toString();
            String var5 = var3.append(var4).toString();
            throw new RuntimeException(var5);
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(this.isASN1FormatString(var1)) {
            var2 = this.engineGetEncoded();
         } else {
            var2 = null;
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof PBEParameterSpec)) {
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
         } else {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            byte[] var3 = var2.getSalt();
            int var4 = var2.getIterationCount();
            PKCS12PBEParams var5 = new PKCS12PBEParams(var3, var4);
            this.params = var5;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         PKCS12PBEParams var2 = PKCS12PBEParams.getInstance(ASN1Object.fromByteArray(var1));
         this.params = var2;
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in PKCS12 PBE parameters object");
         }
      }

      protected String engineToString() {
         return "PKCS12 PBE Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == PBEParameterSpec.class) {
            byte[] var2 = this.params.getIV();
            int var3 = this.params.getIterations().intValue();
            return new PBEParameterSpec(var2, var3);
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
         }
      }
   }

   public static class DSA extends JDKAlgorithmParameters {

      DSAParameterSpec currentSpec;


      public DSA() {}

      protected byte[] engineGetEncoded() {
         BigInteger var1 = this.currentSpec.getP();
         BigInteger var2 = this.currentSpec.getQ();
         BigInteger var3 = this.currentSpec.getG();
         DSAParameter var4 = new DSAParameter(var1, var2, var3);

         try {
            byte[] var5 = var4.getEncoded("DER");
            return var5;
         } catch (IOException var7) {
            throw new RuntimeException("Error encoding DSAParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(this.isASN1FormatString(var1)) {
            var2 = this.engineGetEncoded();
         } else {
            var2 = null;
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof DSAParameterSpec)) {
            throw new InvalidParameterSpecException("DSAParameterSpec required to initialise a DSA algorithm parameters object");
         } else {
            DSAParameterSpec var2 = (DSAParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            DSAParameter var3 = new DSAParameter(var2);
            BigInteger var4 = var3.getP();
            BigInteger var5 = var3.getQ();
            BigInteger var6 = var3.getG();
            DSAParameterSpec var7 = new DSAParameterSpec(var4, var5, var6);
            this.currentSpec = var7;
         } catch (ClassCastException var10) {
            throw new IOException("Not a valid DSA Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var11) {
            throw new IOException("Not a valid DSA Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "DSA Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == DSAParameterSpec.class) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to DSA parameters object.");
         }
      }
   }

   public static class DH extends JDKAlgorithmParameters {

      DHParameterSpec currentSpec;


      public DH() {}

      protected byte[] engineGetEncoded() {
         BigInteger var1 = this.currentSpec.getP();
         BigInteger var2 = this.currentSpec.getG();
         int var3 = this.currentSpec.getL();
         DHParameter var4 = new DHParameter(var1, var2, var3);

         try {
            byte[] var5 = var4.getEncoded("DER");
            return var5;
         } catch (IOException var7) {
            throw new RuntimeException("Error encoding DHParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(this.isASN1FormatString(var1)) {
            var2 = this.engineGetEncoded();
         } else {
            var2 = null;
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
         } else {
            DHParameterSpec var2 = (DHParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            DHParameter var3 = new DHParameter(var2);
            if(var3.getL() != null) {
               BigInteger var4 = var3.getP();
               BigInteger var5 = var3.getG();
               int var6 = var3.getL().intValue();
               DHParameterSpec var7 = new DHParameterSpec(var4, var5, var6);
               this.currentSpec = var7;
            } else {
               BigInteger var8 = var3.getP();
               BigInteger var9 = var3.getG();
               DHParameterSpec var10 = new DHParameterSpec(var8, var9);
               this.currentSpec = var10;
            }
         } catch (ClassCastException var13) {
            throw new IOException("Not a valid DH Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var14) {
            throw new IOException("Not a valid DH Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            this.engineInit(var1);
         } else {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         }
      }

      protected String engineToString() {
         return "Diffie-Hellman Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == DHParameterSpec.class) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
         }
      }
   }

   public static class PBKDF2 extends JDKAlgorithmParameters {

      PBKDF2Params params;


      public PBKDF2() {}

      protected byte[] engineGetEncoded() {
         try {
            byte[] var1 = this.params.getEncoded("DER");
            return var1;
         } catch (IOException var6) {
            StringBuilder var3 = (new StringBuilder()).append("Oooops! ");
            String var4 = var6.toString();
            String var5 = var3.append(var4).toString();
            throw new RuntimeException(var5);
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(this.isASN1FormatString(var1)) {
            var2 = this.engineGetEncoded();
         } else {
            var2 = null;
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof PBEParameterSpec)) {
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PKCS12 PBE parameters algorithm parameters object");
         } else {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            byte[] var3 = var2.getSalt();
            int var4 = var2.getIterationCount();
            PBKDF2Params var5 = new PBKDF2Params(var3, var4);
            this.params = var5;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         PBKDF2Params var2 = PBKDF2Params.getInstance(ASN1Object.fromByteArray(var1));
         this.params = var2;
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in PWRIKEK parameters object");
         }
      }

      protected String engineToString() {
         return "PBKDF2 Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == PBEParameterSpec.class) {
            byte[] var2 = this.params.getSalt();
            int var3 = this.params.getIterationCount().intValue();
            return new PBEParameterSpec(var2, var3);
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PKCS12 PBE parameters object.");
         }
      }
   }

   public static class OAEP extends JDKAlgorithmParameters {

      OAEPParameterSpec currentSpec;


      public OAEP() {}

      protected byte[] engineGetEncoded() {
         DERObjectIdentifier var1 = JCEDigestUtil.getOID(this.currentSpec.getDigestAlgorithm());
         DERNull var2 = new DERNull();
         AlgorithmIdentifier var3 = new AlgorithmIdentifier(var1, var2);
         MGF1ParameterSpec var4 = (MGF1ParameterSpec)this.currentSpec.getMGFParameters();
         DERObjectIdentifier var5 = PKCSObjectIdentifiers.id_mgf1;
         DERObjectIdentifier var6 = JCEDigestUtil.getOID(var4.getDigestAlgorithm());
         DERNull var7 = new DERNull();
         AlgorithmIdentifier var8 = new AlgorithmIdentifier(var6, var7);
         AlgorithmIdentifier var9 = new AlgorithmIdentifier(var5, var8);
         PSpecified var10 = (PSpecified)this.currentSpec.getPSource();
         DERObjectIdentifier var11 = PKCSObjectIdentifiers.id_pSpecified;
         byte[] var12 = var10.getValue();
         DEROctetString var13 = new DEROctetString(var12);
         AlgorithmIdentifier var14 = new AlgorithmIdentifier(var11, var13);
         RSAESOAEPparams var15 = new RSAESOAEPparams(var3, var9, var14);

         try {
            byte[] var16 = var15.getEncoded("DER");
            return var16;
         } catch (IOException var18) {
            throw new RuntimeException("Error encoding OAEPParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(!this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509")) {
            var2 = null;
         } else {
            var2 = this.engineGetEncoded();
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof OAEPParameterSpec)) {
            throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
         } else {
            OAEPParameterSpec var2 = (OAEPParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            RSAESOAEPparams var3 = new RSAESOAEPparams(var2);
            String var4 = var3.getHashAlgorithm().getObjectId().getId();
            String var5 = var3.getMaskGenAlgorithm().getObjectId().getId();
            String var6 = AlgorithmIdentifier.getInstance(var3.getMaskGenAlgorithm().getParameters()).getObjectId().getId();
            MGF1ParameterSpec var7 = new MGF1ParameterSpec(var6);
            byte[] var8 = ASN1OctetString.getInstance(var3.getPSourceAlgorithm().getParameters()).getOctets();
            PSpecified var9 = new PSpecified(var8);
            OAEPParameterSpec var10 = new OAEPParameterSpec(var4, var5, var7, var9);
            this.currentSpec = var10;
         } catch (ClassCastException var13) {
            throw new IOException("Not a valid OAEP Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var14) {
            throw new IOException("Not a valid OAEP Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!var2.equalsIgnoreCase("X.509") && !var2.equalsIgnoreCase("ASN.1")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "OAEP Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == OAEPParameterSpec.class && this.currentSpec != null) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
         }
      }
   }

   public static class PSS extends JDKAlgorithmParameters {

      PSSParameterSpec currentSpec;


      public PSS() {}

      protected byte[] engineGetEncoded() throws IOException {
         PSSParameterSpec var1 = this.currentSpec;
         DERObjectIdentifier var2 = JCEDigestUtil.getOID(var1.getDigestAlgorithm());
         DERNull var3 = new DERNull();
         AlgorithmIdentifier var4 = new AlgorithmIdentifier(var2, var3);
         MGF1ParameterSpec var5 = (MGF1ParameterSpec)var1.getMGFParameters();
         DERObjectIdentifier var6 = PKCSObjectIdentifiers.id_mgf1;
         DERObjectIdentifier var7 = JCEDigestUtil.getOID(var5.getDigestAlgorithm());
         DERNull var8 = new DERNull();
         AlgorithmIdentifier var9 = new AlgorithmIdentifier(var7, var8);
         AlgorithmIdentifier var10 = new AlgorithmIdentifier(var6, var9);
         int var11 = var1.getSaltLength();
         DERInteger var12 = new DERInteger(var11);
         int var13 = var1.getTrailerField();
         DERInteger var14 = new DERInteger(var13);
         return (new RSASSAPSSparams(var4, var10, var12, var14)).getEncoded("DER");
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var2;
         if(!var1.equalsIgnoreCase("X.509") && !var1.equalsIgnoreCase("ASN.1")) {
            var2 = null;
         } else {
            var2 = this.engineGetEncoded();
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof PSSParameterSpec)) {
            throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
         } else {
            PSSParameterSpec var2 = (PSSParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            RSASSAPSSparams var3 = new RSASSAPSSparams(var2);
            String var4 = var3.getHashAlgorithm().getObjectId().getId();
            String var5 = var3.getMaskGenAlgorithm().getObjectId().getId();
            String var6 = AlgorithmIdentifier.getInstance(var3.getMaskGenAlgorithm().getParameters()).getObjectId().getId();
            MGF1ParameterSpec var7 = new MGF1ParameterSpec(var6);
            int var8 = var3.getSaltLength().getValue().intValue();
            int var9 = var3.getTrailerField().getValue().intValue();
            PSSParameterSpec var10 = new PSSParameterSpec(var4, var5, var7, var8, var9);
            this.currentSpec = var10;
         } catch (ClassCastException var13) {
            throw new IOException("Not a valid PSS Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var14) {
            throw new IOException("Not a valid PSS Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "PSS Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == PSSParameterSpec.class && this.currentSpec != null) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
         }
      }
   }

   public static class GOST3410 extends JDKAlgorithmParameters {

      GOST3410ParameterSpec currentSpec;


      public GOST3410() {}

      protected byte[] engineGetEncoded() {
         String var1 = this.currentSpec.getPublicKeyParamSetOID();
         DERObjectIdentifier var2 = new DERObjectIdentifier(var1);
         String var3 = this.currentSpec.getDigestParamSetOID();
         DERObjectIdentifier var4 = new DERObjectIdentifier(var3);
         String var5 = this.currentSpec.getEncryptionParamSetOID();
         DERObjectIdentifier var6 = new DERObjectIdentifier(var5);
         GOST3410PublicKeyAlgParameters var7 = new GOST3410PublicKeyAlgParameters(var2, var4, var6);

         try {
            byte[] var8 = var7.getEncoded("DER");
            return var8;
         } catch (IOException var10) {
            throw new RuntimeException("Error encoding GOST3410Parameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(!this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509")) {
            var2 = null;
         } else {
            var2 = this.engineGetEncoded();
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof GOST3410ParameterSpec)) {
            throw new InvalidParameterSpecException("GOST3410ParameterSpec required to initialise a GOST3410 algorithm parameters object");
         } else {
            GOST3410ParameterSpec var2 = (GOST3410ParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            GOST3410ParameterSpec var3 = GOST3410ParameterSpec.fromPublicKeyAlg(new GOST3410PublicKeyAlgParameters(var2));
            this.currentSpec = var3;
         } catch (ClassCastException var6) {
            throw new IOException("Not a valid GOST3410 Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var7) {
            throw new IOException("Not a valid GOST3410 Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "GOST3410 Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == GOST3410PublicKeyParameterSetSpec.class) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to GOST3410 parameters object.");
         }
      }
   }

   public static class IVAlgorithmParameters extends JDKAlgorithmParameters {

      private byte[] iv;


      public IVAlgorithmParameters() {}

      protected byte[] engineGetEncoded() throws IOException {
         return this.engineGetEncoded("ASN.1");
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var3;
         if(this.isASN1FormatString(var1)) {
            byte[] var2 = this.engineGetEncoded("RAW");
            var3 = (new DEROctetString(var2)).getEncoded();
         } else if(var1.equals("RAW")) {
            var3 = Arrays.clone(this.iv);
         } else {
            var3 = null;
         }

         return var3;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
         } else {
            byte[] var2 = ((IvParameterSpec)var1).getIV();
            this.iv = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         if(var1.length % 8 != 0 && var1[0] == 4) {
            byte var2 = var1[1];
            int var3 = var1.length - 2;
            if(var2 == var3) {
               var1 = ((ASN1OctetString)ASN1Object.fromByteArray(var1)).getOctets();
            }
         }

         byte[] var4 = Arrays.clone(var1);
         this.iv = var4;
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            try {
               byte[] var3 = ((ASN1OctetString)ASN1Object.fromByteArray(var1)).getOctets();
               this.engineInit(var3);
            } catch (Exception var6) {
               String var5 = "Exception decoding: " + var6;
               throw new IOException(var5);
            }
         } else if(var2.equals("RAW")) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in IV parameters object");
         }
      }

      protected String engineToString() {
         return "IV Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == IvParameterSpec.class) {
            byte[] var2 = this.iv;
            return new IvParameterSpec(var2);
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
         }
      }
   }

   public static class IES extends JDKAlgorithmParameters {

      IESParameterSpec currentSpec;


      public IES() {}

      protected byte[] engineGetEncoded() {
         try {
            ASN1EncodableVector var1 = new ASN1EncodableVector();
            byte[] var2 = this.currentSpec.getDerivationV();
            DEROctetString var3 = new DEROctetString(var2);
            var1.add(var3);
            byte[] var4 = this.currentSpec.getEncodingV();
            DEROctetString var5 = new DEROctetString(var4);
            var1.add(var5);
            int var6 = this.currentSpec.getMacKeySize();
            DERInteger var7 = new DERInteger(var6);
            var1.add(var7);
            byte[] var8 = (new DERSequence(var1)).getEncoded("DER");
            return var8;
         } catch (IOException var10) {
            throw new RuntimeException("Error encoding IESParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(!this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509")) {
            var2 = null;
         } else {
            var2 = this.engineGetEncoded();
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof IESParameterSpec)) {
            throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
         } else {
            IESParameterSpec var2 = (IESParameterSpec)var1;
            this.currentSpec = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            byte[] var3 = ((ASN1OctetString)var2.getObjectAt(0)).getOctets();
            byte[] var4 = ((ASN1OctetString)var2.getObjectAt(0)).getOctets();
            int var5 = ((DERInteger)var2.getObjectAt(0)).getValue().intValue();
            IESParameterSpec var6 = new IESParameterSpec(var3, var4, var5);
            this.currentSpec = var6;
         } catch (ClassCastException var9) {
            throw new IOException("Not a valid IES Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var10) {
            throw new IOException("Not a valid IES Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "IES Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == IESParameterSpec.class) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
         }
      }
   }

   public static class ElGamal extends JDKAlgorithmParameters {

      ElGamalParameterSpec currentSpec;


      public ElGamal() {}

      protected byte[] engineGetEncoded() {
         BigInteger var1 = this.currentSpec.getP();
         BigInteger var2 = this.currentSpec.getG();
         ElGamalParameter var3 = new ElGamalParameter(var1, var2);

         try {
            byte[] var4 = var3.getEncoded("DER");
            return var4;
         } catch (IOException var6) {
            throw new RuntimeException("Error encoding ElGamalParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         byte[] var2;
         if(!this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509")) {
            var2 = null;
         } else {
            var2 = this.engineGetEncoded();
         }

         return var2;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof ElGamalParameterSpec) && !(var1 instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a ElGamal algorithm parameters object");
         } else if(var1 instanceof ElGamalParameterSpec) {
            ElGamalParameterSpec var2 = (ElGamalParameterSpec)var1;
            this.currentSpec = var2;
         } else {
            DHParameterSpec var3 = (DHParameterSpec)var1;
            BigInteger var4 = var3.getP();
            BigInteger var5 = var3.getG();
            ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
            this.currentSpec = var6;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            ASN1Sequence var2 = (ASN1Sequence)ASN1Object.fromByteArray(var1);
            ElGamalParameter var3 = new ElGamalParameter(var2);
            BigInteger var4 = var3.getP();
            BigInteger var5 = var3.getG();
            ElGamalParameterSpec var6 = new ElGamalParameterSpec(var4, var5);
            this.currentSpec = var6;
         } catch (ClassCastException var9) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var10) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            String var3 = "Unknown parameter format " + var2;
            throw new IOException(var3);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "ElGamal Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         Object var2;
         if(var1 == ElGamalParameterSpec.class) {
            var2 = this.currentSpec;
         } else {
            if(var1 != DHParameterSpec.class) {
               throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
            }

            BigInteger var3 = this.currentSpec.getP();
            BigInteger var4 = this.currentSpec.getG();
            var2 = new DHParameterSpec(var3, var4);
         }

         return (AlgorithmParameterSpec)var2;
      }
   }

   public static class RC2AlgorithmParameters extends JDKAlgorithmParameters {

      private static final short[] ekb;
      private static final short[] table;
      private byte[] iv;
      private int parameterVersion = 58;


      static {
         ((Object[])null)[0] = (short)null;
         ((Object[])null)[1] = (short)null;
         ((Object[])null)[2] = (short)null;
         ((Object[])null)[3] = (short)null;
         ((Object[])null)[4] = (short)null;
         ((Object[])null)[5] = (short)null;
         ((Object[])null)[6] = (short)null;
         ((Object[])null)[7] = (short)null;
         ((Object[])null)[8] = (short)null;
         ((Object[])null)[9] = (short)null;
         ((Object[])null)[10] = (short)null;
         ((Object[])null)[11] = (short)null;
         ((Object[])null)[12] = (short)null;
         ((Object[])null)[13] = (short)null;
         ((Object[])null)[14] = (short)null;
         ((Object[])null)[15] = (short)null;
         ((Object[])null)[16] = (short)null;
         ((Object[])null)[17] = (short)null;
         ((Object[])null)[18] = (short)null;
         ((Object[])null)[19] = (short)null;
         ((Object[])null)[20] = (short)null;
         ((Object[])null)[21] = (short)null;
         ((Object[])null)[22] = (short)null;
         ((Object[])null)[23] = (short)null;
         ((Object[])null)[24] = (short)null;
         ((Object[])null)[25] = (short)null;
         ((Object[])null)[26] = (short)null;
         ((Object[])null)[27] = (short)null;
         ((Object[])null)[28] = (short)null;
         ((Object[])null)[29] = (short)null;
         ((Object[])null)[30] = (short)null;
         ((Object[])null)[31] = (short)null;
         ((Object[])null)[32] = (short)null;
         ((Object[])null)[33] = (short)null;
         ((Object[])null)[34] = (short)null;
         ((Object[])null)[35] = (short)null;
         ((Object[])null)[36] = (short)null;
         ((Object[])null)[37] = (short)null;
         ((Object[])null)[38] = (short)null;
         ((Object[])null)[39] = (short)null;
         ((Object[])null)[40] = (short)null;
         ((Object[])null)[41] = (short)null;
         ((Object[])null)[42] = (short)null;
         ((Object[])null)[43] = (short)null;
         ((Object[])null)[44] = (short)null;
         ((Object[])null)[45] = (short)null;
         ((Object[])null)[46] = (short)null;
         ((Object[])null)[47] = (short)null;
         ((Object[])null)[48] = (short)null;
         ((Object[])null)[49] = (short)null;
         ((Object[])null)[50] = (short)null;
         ((Object[])null)[51] = (short)null;
         ((Object[])null)[52] = (short)null;
         ((Object[])null)[53] = (short)null;
         ((Object[])null)[54] = (short)null;
         ((Object[])null)[55] = (short)null;
         ((Object[])null)[56] = (short)null;
         ((Object[])null)[57] = (short)null;
         ((Object[])null)[58] = (short)null;
         ((Object[])null)[59] = (short)null;
         ((Object[])null)[60] = (short)null;
         ((Object[])null)[61] = (short)null;
         ((Object[])null)[62] = (short)null;
         ((Object[])null)[63] = (short)null;
         ((Object[])null)[64] = (short)null;
         ((Object[])null)[65] = (short)null;
         ((Object[])null)[66] = (short)null;
         ((Object[])null)[67] = (short)null;
         ((Object[])null)[68] = (short)null;
         ((Object[])null)[69] = (short)null;
         ((Object[])null)[70] = (short)null;
         ((Object[])null)[71] = (short)null;
         ((Object[])null)[72] = (short)null;
         ((Object[])null)[73] = (short)null;
         ((Object[])null)[74] = (short)null;
         ((Object[])null)[75] = (short)null;
         ((Object[])null)[76] = (short)null;
         ((Object[])null)[77] = (short)null;
         ((Object[])null)[78] = (short)null;
         ((Object[])null)[79] = (short)null;
         ((Object[])null)[80] = (short)null;
         ((Object[])null)[81] = (short)null;
         ((Object[])null)[82] = (short)null;
         ((Object[])null)[83] = (short)null;
         ((Object[])null)[84] = (short)null;
         ((Object[])null)[85] = (short)null;
         ((Object[])null)[86] = (short)null;
         ((Object[])null)[87] = (short)null;
         ((Object[])null)[88] = (short)null;
         ((Object[])null)[89] = (short)null;
         ((Object[])null)[90] = (short)null;
         ((Object[])null)[91] = (short)null;
         ((Object[])null)[92] = (short)null;
         ((Object[])null)[93] = (short)null;
         ((Object[])null)[94] = (short)null;
         ((Object[])null)[95] = (short)null;
         ((Object[])null)[96] = (short)null;
         ((Object[])null)[97] = (short)null;
         ((Object[])null)[98] = (short)null;
         ((Object[])null)[99] = (short)null;
         ((Object[])null)[100] = (short)null;
         ((Object[])null)[101] = (short)null;
         ((Object[])null)[102] = (short)null;
         ((Object[])null)[103] = (short)null;
         ((Object[])null)[104] = (short)null;
         ((Object[])null)[105] = (short)null;
         ((Object[])null)[106] = (short)null;
         ((Object[])null)[107] = (short)null;
         ((Object[])null)[108] = (short)null;
         ((Object[])null)[109] = (short)null;
         ((Object[])null)[110] = (short)null;
         ((Object[])null)[111] = (short)null;
         ((Object[])null)[112] = (short)null;
         ((Object[])null)[113] = (short)null;
         ((Object[])null)[114] = (short)null;
         ((Object[])null)[115] = (short)null;
         ((Object[])null)[116] = (short)null;
         ((Object[])null)[117] = (short)null;
         ((Object[])null)[118] = (short)null;
         ((Object[])null)[119] = (short)null;
         ((Object[])null)[120] = (short)null;
         ((Object[])null)[121] = (short)null;
         ((Object[])null)[122] = (short)null;
         ((Object[])null)[123] = (short)null;
         ((Object[])null)[124] = (short)null;
         ((Object[])null)[125] = (short)null;
         ((Object[])null)[126] = (short)null;
         ((Object[])null)[127] = (short)null;
         ((Object[])null)[128] = (short)null;
         ((Object[])null)[129] = (short)null;
         ((Object[])null)[130] = (short)null;
         ((Object[])null)[131] = (short)null;
         ((Object[])null)[132] = (short)null;
         ((Object[])null)[133] = (short)null;
         ((Object[])null)[134] = (short)null;
         ((Object[])null)[135] = (short)null;
         ((Object[])null)[136] = (short)null;
         ((Object[])null)[137] = (short)null;
         ((Object[])null)[138] = (short)null;
         ((Object[])null)[139] = (short)null;
         ((Object[])null)[140] = (short)null;
         ((Object[])null)[141] = (short)null;
         ((Object[])null)[142] = (short)null;
         ((Object[])null)[143] = (short)null;
         ((Object[])null)[144] = (short)null;
         ((Object[])null)[145] = (short)null;
         ((Object[])null)[146] = (short)null;
         ((Object[])null)[147] = (short)null;
         ((Object[])null)[148] = (short)null;
         ((Object[])null)[149] = (short)null;
         ((Object[])null)[150] = (short)null;
         ((Object[])null)[151] = (short)null;
         ((Object[])null)[152] = (short)null;
         ((Object[])null)[153] = (short)null;
         ((Object[])null)[154] = (short)null;
         ((Object[])null)[155] = (short)null;
         ((Object[])null)[156] = (short)null;
         ((Object[])null)[157] = (short)null;
         ((Object[])null)[158] = (short)null;
         ((Object[])null)[159] = (short)null;
         ((Object[])null)[160] = (short)null;
         ((Object[])null)[161] = (short)null;
         ((Object[])null)[162] = (short)null;
         ((Object[])null)[163] = (short)null;
         ((Object[])null)[164] = (short)null;
         ((Object[])null)[165] = (short)null;
         ((Object[])null)[166] = (short)null;
         ((Object[])null)[167] = (short)null;
         ((Object[])null)[168] = (short)null;
         ((Object[])null)[169] = (short)null;
         ((Object[])null)[170] = (short)null;
         ((Object[])null)[171] = (short)null;
         ((Object[])null)[172] = (short)null;
         ((Object[])null)[173] = (short)null;
         ((Object[])null)[174] = (short)null;
         ((Object[])null)[175] = (short)null;
         ((Object[])null)[176] = (short)null;
         ((Object[])null)[177] = (short)null;
         ((Object[])null)[178] = (short)null;
         ((Object[])null)[179] = (short)null;
         ((Object[])null)[180] = (short)null;
         ((Object[])null)[181] = (short)null;
         ((Object[])null)[182] = (short)null;
         ((Object[])null)[183] = (short)null;
         ((Object[])null)[184] = (short)null;
         ((Object[])null)[185] = (short)null;
         ((Object[])null)[186] = (short)null;
         ((Object[])null)[187] = (short)null;
         ((Object[])null)[188] = (short)null;
         ((Object[])null)[189] = (short)null;
         ((Object[])null)[190] = (short)null;
         ((Object[])null)[191] = (short)null;
         ((Object[])null)[192] = (short)null;
         ((Object[])null)[193] = (short)null;
         ((Object[])null)[194] = (short)null;
         ((Object[])null)[195] = (short)null;
         ((Object[])null)[196] = (short)null;
         ((Object[])null)[197] = (short)null;
         ((Object[])null)[198] = (short)null;
         ((Object[])null)[199] = (short)null;
         ((Object[])null)[200] = (short)null;
         ((Object[])null)[201] = (short)null;
         ((Object[])null)[202] = (short)null;
         ((Object[])null)[203] = (short)null;
         ((Object[])null)[204] = (short)null;
         ((Object[])null)[205] = (short)null;
         ((Object[])null)[206] = (short)null;
         ((Object[])null)[207] = (short)null;
         ((Object[])null)[208] = (short)null;
         ((Object[])null)[209] = (short)null;
         ((Object[])null)[210] = (short)null;
         ((Object[])null)[211] = (short)null;
         ((Object[])null)[212] = (short)null;
         ((Object[])null)[213] = (short)null;
         ((Object[])null)[214] = (short)null;
         ((Object[])null)[215] = (short)null;
         ((Object[])null)[216] = (short)null;
         ((Object[])null)[217] = (short)null;
         ((Object[])null)[218] = (short)null;
         ((Object[])null)[219] = (short)null;
         ((Object[])null)[220] = (short)null;
         ((Object[])null)[221] = (short)null;
         ((Object[])null)[222] = (short)null;
         ((Object[])null)[223] = (short)null;
         ((Object[])null)[224] = (short)null;
         ((Object[])null)[225] = (short)null;
         ((Object[])null)[226] = (short)null;
         ((Object[])null)[227] = (short)null;
         ((Object[])null)[228] = (short)null;
         ((Object[])null)[229] = (short)null;
         ((Object[])null)[230] = (short)null;
         ((Object[])null)[231] = (short)null;
         ((Object[])null)[232] = (short)null;
         ((Object[])null)[233] = (short)null;
         ((Object[])null)[234] = (short)null;
         ((Object[])null)[235] = (short)null;
         ((Object[])null)[236] = (short)null;
         ((Object[])null)[237] = (short)null;
         ((Object[])null)[238] = (short)null;
         ((Object[])null)[239] = (short)null;
         ((Object[])null)[240] = (short)null;
         ((Object[])null)[241] = (short)null;
         ((Object[])null)[242] = (short)null;
         ((Object[])null)[243] = (short)null;
         ((Object[])null)[244] = (short)null;
         ((Object[])null)[245] = (short)null;
         ((Object[])null)[246] = (short)null;
         ((Object[])null)[247] = (short)null;
         ((Object[])null)[248] = (short)null;
         ((Object[])null)[249] = (short)null;
         ((Object[])null)[250] = (short)null;
         ((Object[])null)[251] = (short)null;
         ((Object[])null)[252] = (short)null;
         ((Object[])null)[253] = (short)null;
         ((Object[])null)[254] = (short)null;
         ((Object[])null)[255] = (short)null;
         table = null;
         ((Object[])null)[0] = (short)null;
         ((Object[])null)[1] = (short)null;
         ((Object[])null)[2] = (short)null;
         ((Object[])null)[3] = (short)null;
         ((Object[])null)[4] = (short)null;
         ((Object[])null)[5] = (short)null;
         ((Object[])null)[6] = (short)null;
         ((Object[])null)[7] = (short)null;
         ((Object[])null)[8] = (short)null;
         ((Object[])null)[9] = (short)null;
         ((Object[])null)[10] = (short)null;
         ((Object[])null)[11] = (short)null;
         ((Object[])null)[12] = (short)null;
         ((Object[])null)[13] = (short)null;
         ((Object[])null)[14] = (short)null;
         ((Object[])null)[15] = (short)null;
         ((Object[])null)[16] = (short)null;
         ((Object[])null)[17] = (short)null;
         ((Object[])null)[18] = (short)null;
         ((Object[])null)[19] = (short)null;
         ((Object[])null)[20] = (short)null;
         ((Object[])null)[21] = (short)null;
         ((Object[])null)[22] = (short)null;
         ((Object[])null)[23] = (short)null;
         ((Object[])null)[24] = (short)null;
         ((Object[])null)[25] = (short)null;
         ((Object[])null)[26] = (short)null;
         ((Object[])null)[27] = (short)null;
         ((Object[])null)[28] = (short)null;
         ((Object[])null)[29] = (short)null;
         ((Object[])null)[30] = (short)null;
         ((Object[])null)[31] = (short)null;
         ((Object[])null)[32] = (short)null;
         ((Object[])null)[33] = (short)null;
         ((Object[])null)[34] = (short)null;
         ((Object[])null)[35] = (short)null;
         ((Object[])null)[36] = (short)null;
         ((Object[])null)[37] = (short)null;
         ((Object[])null)[38] = (short)null;
         ((Object[])null)[39] = (short)null;
         ((Object[])null)[40] = (short)null;
         ((Object[])null)[41] = (short)null;
         ((Object[])null)[42] = (short)null;
         ((Object[])null)[43] = (short)null;
         ((Object[])null)[44] = (short)null;
         ((Object[])null)[45] = (short)null;
         ((Object[])null)[46] = (short)null;
         ((Object[])null)[47] = (short)null;
         ((Object[])null)[48] = (short)null;
         ((Object[])null)[49] = (short)null;
         ((Object[])null)[50] = (short)null;
         ((Object[])null)[51] = (short)null;
         ((Object[])null)[52] = (short)null;
         ((Object[])null)[53] = (short)null;
         ((Object[])null)[54] = (short)null;
         ((Object[])null)[55] = (short)null;
         ((Object[])null)[56] = (short)null;
         ((Object[])null)[57] = (short)null;
         ((Object[])null)[58] = (short)null;
         ((Object[])null)[59] = (short)null;
         ((Object[])null)[60] = (short)null;
         ((Object[])null)[61] = (short)null;
         ((Object[])null)[62] = (short)null;
         ((Object[])null)[63] = (short)null;
         ((Object[])null)[64] = (short)null;
         ((Object[])null)[65] = (short)null;
         ((Object[])null)[66] = (short)null;
         ((Object[])null)[67] = (short)null;
         ((Object[])null)[68] = (short)null;
         ((Object[])null)[69] = (short)null;
         ((Object[])null)[70] = (short)null;
         ((Object[])null)[71] = (short)null;
         ((Object[])null)[72] = (short)null;
         ((Object[])null)[73] = (short)null;
         ((Object[])null)[74] = (short)null;
         ((Object[])null)[75] = (short)null;
         ((Object[])null)[76] = (short)null;
         ((Object[])null)[77] = (short)null;
         ((Object[])null)[78] = (short)null;
         ((Object[])null)[79] = (short)null;
         ((Object[])null)[80] = (short)null;
         ((Object[])null)[81] = (short)null;
         ((Object[])null)[82] = (short)null;
         ((Object[])null)[83] = (short)null;
         ((Object[])null)[84] = (short)null;
         ((Object[])null)[85] = (short)null;
         ((Object[])null)[86] = (short)null;
         ((Object[])null)[87] = (short)null;
         ((Object[])null)[88] = (short)null;
         ((Object[])null)[89] = (short)null;
         ((Object[])null)[90] = (short)null;
         ((Object[])null)[91] = (short)null;
         ((Object[])null)[92] = (short)null;
         ((Object[])null)[93] = (short)null;
         ((Object[])null)[94] = (short)null;
         ((Object[])null)[95] = (short)null;
         ((Object[])null)[96] = (short)null;
         ((Object[])null)[97] = (short)null;
         ((Object[])null)[98] = (short)null;
         ((Object[])null)[99] = (short)null;
         ((Object[])null)[100] = (short)null;
         ((Object[])null)[101] = (short)null;
         ((Object[])null)[102] = (short)null;
         ((Object[])null)[103] = (short)null;
         ((Object[])null)[104] = (short)null;
         ((Object[])null)[105] = (short)null;
         ((Object[])null)[106] = (short)null;
         ((Object[])null)[107] = (short)null;
         ((Object[])null)[108] = (short)null;
         ((Object[])null)[109] = (short)null;
         ((Object[])null)[110] = (short)null;
         ((Object[])null)[111] = (short)null;
         ((Object[])null)[112] = (short)null;
         ((Object[])null)[113] = (short)null;
         ((Object[])null)[114] = (short)null;
         ((Object[])null)[115] = (short)null;
         ((Object[])null)[116] = (short)null;
         ((Object[])null)[117] = (short)null;
         ((Object[])null)[118] = (short)null;
         ((Object[])null)[119] = (short)null;
         ((Object[])null)[120] = (short)null;
         ((Object[])null)[121] = (short)null;
         ((Object[])null)[122] = (short)null;
         ((Object[])null)[123] = (short)null;
         ((Object[])null)[124] = (short)null;
         ((Object[])null)[125] = (short)null;
         ((Object[])null)[126] = (short)null;
         ((Object[])null)[127] = (short)null;
         ((Object[])null)[128] = (short)null;
         ((Object[])null)[129] = (short)null;
         ((Object[])null)[130] = (short)null;
         ((Object[])null)[131] = (short)null;
         ((Object[])null)[132] = (short)null;
         ((Object[])null)[133] = (short)null;
         ((Object[])null)[134] = (short)null;
         ((Object[])null)[135] = (short)null;
         ((Object[])null)[136] = (short)null;
         ((Object[])null)[137] = (short)null;
         ((Object[])null)[138] = (short)null;
         ((Object[])null)[139] = (short)null;
         ((Object[])null)[140] = (short)null;
         ((Object[])null)[141] = (short)null;
         ((Object[])null)[142] = (short)null;
         ((Object[])null)[143] = (short)null;
         ((Object[])null)[144] = (short)null;
         ((Object[])null)[145] = (short)null;
         ((Object[])null)[146] = (short)null;
         ((Object[])null)[147] = (short)null;
         ((Object[])null)[148] = (short)null;
         ((Object[])null)[149] = (short)null;
         ((Object[])null)[150] = (short)null;
         ((Object[])null)[151] = (short)null;
         ((Object[])null)[152] = (short)null;
         ((Object[])null)[153] = (short)null;
         ((Object[])null)[154] = (short)null;
         ((Object[])null)[155] = (short)null;
         ((Object[])null)[156] = (short)null;
         ((Object[])null)[157] = (short)null;
         ((Object[])null)[158] = (short)null;
         ((Object[])null)[159] = (short)null;
         ((Object[])null)[160] = (short)null;
         ((Object[])null)[161] = (short)null;
         ((Object[])null)[162] = (short)null;
         ((Object[])null)[163] = (short)null;
         ((Object[])null)[164] = (short)null;
         ((Object[])null)[165] = (short)null;
         ((Object[])null)[166] = (short)null;
         ((Object[])null)[167] = (short)null;
         ((Object[])null)[168] = (short)null;
         ((Object[])null)[169] = (short)null;
         ((Object[])null)[170] = (short)null;
         ((Object[])null)[171] = (short)null;
         ((Object[])null)[172] = (short)null;
         ((Object[])null)[173] = (short)null;
         ((Object[])null)[174] = (short)null;
         ((Object[])null)[175] = (short)null;
         ((Object[])null)[176] = (short)null;
         ((Object[])null)[177] = (short)null;
         ((Object[])null)[178] = (short)null;
         ((Object[])null)[179] = (short)null;
         ((Object[])null)[180] = (short)null;
         ((Object[])null)[181] = (short)null;
         ((Object[])null)[182] = (short)null;
         ((Object[])null)[183] = (short)null;
         ((Object[])null)[184] = (short)null;
         ((Object[])null)[185] = (short)null;
         ((Object[])null)[186] = (short)null;
         ((Object[])null)[187] = (short)null;
         ((Object[])null)[188] = (short)null;
         ((Object[])null)[189] = (short)null;
         ((Object[])null)[190] = (short)null;
         ((Object[])null)[191] = (short)null;
         ((Object[])null)[192] = (short)null;
         ((Object[])null)[193] = (short)null;
         ((Object[])null)[194] = (short)null;
         ((Object[])null)[195] = (short)null;
         ((Object[])null)[196] = (short)null;
         ((Object[])null)[197] = (short)null;
         ((Object[])null)[198] = (short)null;
         ((Object[])null)[199] = (short)null;
         ((Object[])null)[200] = (short)null;
         ((Object[])null)[201] = (short)null;
         ((Object[])null)[202] = (short)null;
         ((Object[])null)[203] = (short)null;
         ((Object[])null)[204] = (short)null;
         ((Object[])null)[205] = (short)null;
         ((Object[])null)[206] = (short)null;
         ((Object[])null)[207] = (short)null;
         ((Object[])null)[208] = (short)null;
         ((Object[])null)[209] = (short)null;
         ((Object[])null)[210] = (short)null;
         ((Object[])null)[211] = (short)null;
         ((Object[])null)[212] = (short)null;
         ((Object[])null)[213] = (short)null;
         ((Object[])null)[214] = (short)null;
         ((Object[])null)[215] = (short)null;
         ((Object[])null)[216] = (short)null;
         ((Object[])null)[217] = (short)null;
         ((Object[])null)[218] = (short)null;
         ((Object[])null)[219] = (short)null;
         ((Object[])null)[220] = (short)null;
         ((Object[])null)[221] = (short)null;
         ((Object[])null)[222] = (short)null;
         ((Object[])null)[223] = (short)null;
         ((Object[])null)[224] = (short)null;
         ((Object[])null)[225] = (short)null;
         ((Object[])null)[226] = (short)null;
         ((Object[])null)[227] = (short)null;
         ((Object[])null)[228] = (short)null;
         ((Object[])null)[229] = (short)null;
         ((Object[])null)[230] = (short)null;
         ((Object[])null)[231] = (short)null;
         ((Object[])null)[232] = (short)null;
         ((Object[])null)[233] = (short)null;
         ((Object[])null)[234] = (short)null;
         ((Object[])null)[235] = (short)null;
         ((Object[])null)[236] = (short)null;
         ((Object[])null)[237] = (short)null;
         ((Object[])null)[238] = (short)null;
         ((Object[])null)[239] = (short)null;
         ((Object[])null)[240] = (short)null;
         ((Object[])null)[241] = (short)null;
         ((Object[])null)[242] = (short)null;
         ((Object[])null)[243] = (short)null;
         ((Object[])null)[244] = (short)null;
         ((Object[])null)[245] = (short)null;
         ((Object[])null)[246] = (short)null;
         ((Object[])null)[247] = (short)null;
         ((Object[])null)[248] = (short)null;
         ((Object[])null)[249] = (short)null;
         ((Object[])null)[250] = (short)null;
         ((Object[])null)[251] = (short)null;
         ((Object[])null)[252] = (short)null;
         ((Object[])null)[253] = (short)null;
         ((Object[])null)[254] = (short)null;
         ((Object[])null)[255] = (short)null;
         ekb = null;
      }

      public RC2AlgorithmParameters() {}

      protected byte[] engineGetEncoded() {
         return Arrays.clone(this.iv);
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var3;
         if(this.isASN1FormatString(var1)) {
            if(this.parameterVersion == -1) {
               byte[] var2 = this.engineGetEncoded();
               var3 = (new RC2CBCParameter(var2)).getEncoded();
            } else {
               int var4 = this.parameterVersion;
               byte[] var5 = this.engineGetEncoded();
               var3 = (new RC2CBCParameter(var4, var5)).getEncoded();
            }
         } else if(var1.equals("RAW")) {
            var3 = this.engineGetEncoded();
         } else {
            var3 = null;
         }

         return var3;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(var1 instanceof IvParameterSpec) {
            byte[] var2 = ((IvParameterSpec)var1).getIV();
            this.iv = var2;
         } else if(var1 instanceof RC2ParameterSpec) {
            int var3 = ((RC2ParameterSpec)var1).getEffectiveKeyBits();
            if(var3 != -1) {
               if(var3 < 256) {
                  short var4 = table[var3];
                  this.parameterVersion = var4;
               } else {
                  this.parameterVersion = var3;
               }
            }

            byte[] var5 = ((RC2ParameterSpec)var1).getIV();
            this.iv = var5;
         } else {
            throw new InvalidParameterSpecException("IvParameterSpec or RC2ParameterSpec required to initialise a RC2 parameters algorithm parameters object");
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         byte[] var2 = Arrays.clone(var1);
         this.iv = var2;
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(this.isASN1FormatString(var2)) {
            RC2CBCParameter var3 = RC2CBCParameter.getInstance(ASN1Object.fromByteArray(var1));
            if(var3.getRC2ParameterVersion() != null) {
               int var4 = var3.getRC2ParameterVersion().intValue();
               this.parameterVersion = var4;
            }

            byte[] var5 = var3.getIV();
            this.iv = var5;
         } else if(var2.equals("RAW")) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in IV parameters object");
         }
      }

      protected String engineToString() {
         return "RC2 Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         Object var6;
         if(var1 == RC2ParameterSpec.class && this.parameterVersion != -1) {
            if(this.parameterVersion < 256) {
               short[] var2 = ekb;
               int var3 = this.parameterVersion;
               short var4 = var2[var3];
               byte[] var5 = this.iv;
               var6 = new RC2ParameterSpec(var4, var5);
            } else {
               int var7 = this.parameterVersion;
               byte[] var8 = this.iv;
               var6 = new RC2ParameterSpec(var7, var8);
            }
         } else {
            if(var1 != IvParameterSpec.class) {
               throw new InvalidParameterSpecException("unknown parameter spec passed to RC2 parameters object.");
            }

            byte[] var9 = this.iv;
            var6 = new IvParameterSpec(var9);
         }

         return (AlgorithmParameterSpec)var6;
      }
   }
}
