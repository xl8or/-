package myorg.bouncycastle.jce.netscape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class NetscapeCertRequest extends ASN1Encodable {

   String challenge;
   DERBitString content;
   AlgorithmIdentifier keyAlg;
   PublicKey pubkey;
   AlgorithmIdentifier sigAlg;
   byte[] sigBits;


   public NetscapeCertRequest(String var1, AlgorithmIdentifier var2, PublicKey var3) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
      this.challenge = var1;
      this.sigAlg = var2;
      this.pubkey = var3;
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      DERObject var5 = this.getKeySpec();
      var4.add(var5);
      DERIA5String var6 = new DERIA5String(var1);
      var4.add(var6);
      DERSequence var7 = new DERSequence(var4);
      DERBitString var8 = new DERBitString(var7);
      this.content = var8;
   }

   public NetscapeCertRequest(ASN1Sequence var1) {
      try {
         if(var1.size() != 3) {
            StringBuilder var2 = (new StringBuilder()).append("invalid SPKAC (size):");
            int var3 = var1.size();
            String var4 = var2.append(var3).toString();
            throw new IllegalArgumentException(var4);
         } else {
            ASN1Sequence var6 = (ASN1Sequence)var1.getObjectAt(1);
            AlgorithmIdentifier var7 = new AlgorithmIdentifier(var6);
            this.sigAlg = var7;
            byte[] var8 = ((DERBitString)var1.getObjectAt(2)).getBytes();
            this.sigBits = var8;
            ASN1Sequence var9 = (ASN1Sequence)var1.getObjectAt(0);
            if(var9.size() != 2) {
               StringBuilder var10 = (new StringBuilder()).append("invalid PKAC (len): ");
               int var11 = var9.size();
               String var12 = var10.append(var11).toString();
               throw new IllegalArgumentException(var12);
            } else {
               String var13 = ((DERIA5String)var9.getObjectAt(1)).getString();
               this.challenge = var13;
               DERBitString var14 = new DERBitString(var9);
               this.content = var14;
               ASN1Sequence var15 = (ASN1Sequence)var9.getObjectAt(0);
               SubjectPublicKeyInfo var16 = new SubjectPublicKeyInfo(var15);
               byte[] var17 = (new DERBitString(var16)).getBytes();
               X509EncodedKeySpec var18 = new X509EncodedKeySpec(var17);
               AlgorithmIdentifier var19 = var16.getAlgorithmId();
               this.keyAlg = var19;
               PublicKey var20 = KeyFactory.getInstance(this.keyAlg.getObjectId().getId(), "myBC").generatePublic(var18);
               this.pubkey = var20;
            }
         }
      } catch (Exception var21) {
         String var5 = var21.toString();
         throw new IllegalArgumentException(var5);
      }
   }

   public NetscapeCertRequest(byte[] var1) throws IOException {
      ASN1Sequence var2 = getReq(var1);
      this(var2);
   }

   private DERObject getKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      try {
         byte[] var2 = this.pubkey.getEncoded();
         var1.write(var2);
         var1.close();
         byte[] var3 = var1.toByteArray();
         ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
         DERObject var5 = (new ASN1InputStream(var4)).readObject();
         return var5;
      } catch (IOException var7) {
         String var6 = var7.getMessage();
         throw new InvalidKeySpecException(var6);
      }
   }

   private static ASN1Sequence getReq(byte[] var0) throws IOException {
      ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
      return ASN1Sequence.getInstance((new ASN1InputStream(var1)).readObject());
   }

   public String getChallenge() {
      return this.challenge;
   }

   public AlgorithmIdentifier getKeyAlgorithm() {
      return this.keyAlg;
   }

   public PublicKey getPublicKey() {
      return this.pubkey;
   }

   public AlgorithmIdentifier getSigningAlgorithm() {
      return this.sigAlg;
   }

   public void setChallenge(String var1) {
      this.challenge = var1;
   }

   public void setKeyAlgorithm(AlgorithmIdentifier var1) {
      this.keyAlg = var1;
   }

   public void setPublicKey(PublicKey var1) {
      this.pubkey = var1;
   }

   public void setSigningAlgorithm(AlgorithmIdentifier var1) {
      this.sigAlg = var1;
   }

   public void sign(PrivateKey var1) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
      this.sign(var1, (SecureRandom)null);
   }

   public void sign(PrivateKey var1, SecureRandom var2) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
      Signature var3 = Signature.getInstance(this.sigAlg.getObjectId().getId(), "myBC");
      if(var2 != null) {
         var3.initSign(var1, var2);
      } else {
         var3.initSign(var1);
      }

      ASN1EncodableVector var4 = new ASN1EncodableVector();
      DERObject var5 = this.getKeySpec();
      var4.add(var5);
      String var6 = this.challenge;
      DERIA5String var7 = new DERIA5String(var6);
      var4.add(var7);

      try {
         byte[] var8 = (new DERSequence(var4)).getEncoded("DER");
         var3.update(var8);
      } catch (IOException var11) {
         String var10 = var11.getMessage();
         throw new SignatureException(var10);
      }

      byte[] var9 = var3.sign();
      this.sigBits = var9;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1EncodableVector var2 = new ASN1EncodableVector();

      try {
         DERObject var3 = this.getKeySpec();
         var2.add(var3);
      } catch (Exception var11) {
         ;
      }

      String var4 = this.challenge;
      DERIA5String var5 = new DERIA5String(var4);
      var2.add(var5);
      DERSequence var6 = new DERSequence(var2);
      var1.add(var6);
      AlgorithmIdentifier var7 = this.sigAlg;
      var1.add(var7);
      byte[] var8 = this.sigBits;
      DERBitString var9 = new DERBitString(var8);
      var1.add(var9);
      return new DERSequence(var1);
   }

   public boolean verify(String var1) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
      String var2 = this.challenge;
      byte var3;
      if(!var1.equals(var2)) {
         var3 = 0;
      } else {
         Signature var4 = Signature.getInstance(this.sigAlg.getObjectId().getId(), "myBC");
         PublicKey var5 = this.pubkey;
         var4.initVerify(var5);
         byte[] var6 = this.content.getBytes();
         var4.update(var6);
         byte[] var7 = this.sigBits;
         var3 = var4.verify(var7);
      }

      return (boolean)var3;
   }
}
