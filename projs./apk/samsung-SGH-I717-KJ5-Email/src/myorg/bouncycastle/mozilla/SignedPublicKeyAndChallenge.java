package myorg.bouncycastle.mozilla;

import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.mozilla.PublicKeyAndChallenge;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class SignedPublicKeyAndChallenge extends ASN1Encodable {

   private PublicKeyAndChallenge pkac;
   private DERBitString signature;
   private AlgorithmIdentifier signatureAlgorithm;
   private ASN1Sequence spkacSeq;


   public SignedPublicKeyAndChallenge(byte[] var1) {
      ASN1Sequence var2 = toDERSequence(var1);
      this.spkacSeq = var2;
      PublicKeyAndChallenge var3 = PublicKeyAndChallenge.getInstance(this.spkacSeq.getObjectAt(0));
      this.pkac = var3;
      AlgorithmIdentifier var4 = AlgorithmIdentifier.getInstance(this.spkacSeq.getObjectAt(1));
      this.signatureAlgorithm = var4;
      DERBitString var5 = (DERBitString)this.spkacSeq.getObjectAt(2);
      this.signature = var5;
   }

   private static ASN1Sequence toDERSequence(byte[] var0) {
      try {
         ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
         ASN1Sequence var4 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
         return var4;
      } catch (Exception var3) {
         throw new IllegalArgumentException("badly encoded request");
      }
   }

   public PublicKey getPublicKey(String var1) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
      SubjectPublicKeyInfo var2 = this.pkac.getSubjectPublicKeyInfo();

      try {
         byte[] var3 = (new DERBitString(var2)).getBytes();
         X509EncodedKeySpec var4 = new X509EncodedKeySpec(var3);
         PublicKey var5 = KeyFactory.getInstance(var2.getAlgorithmId().getObjectId().getId(), var1).generatePublic(var4);
         return var5;
      } catch (InvalidKeySpecException var7) {
         throw new InvalidKeyException("error encoding public key");
      }
   }

   public PublicKeyAndChallenge getPublicKeyAndChallenge() {
      return this.pkac;
   }

   public DERObject toASN1Object() {
      return this.spkacSeq;
   }

   public boolean verify() throws NoSuchAlgorithmException, SignatureException, NoSuchProviderException, InvalidKeyException {
      return this.verify((String)null);
   }

   public boolean verify(String var1) throws NoSuchAlgorithmException, SignatureException, NoSuchProviderException, InvalidKeyException {
      Signature var2;
      if(var1 == null) {
         var2 = Signature.getInstance(this.signatureAlgorithm.getObjectId().getId());
      } else {
         var2 = Signature.getInstance(this.signatureAlgorithm.getObjectId().getId(), var1);
      }

      PublicKey var3 = this.getPublicKey(var1);
      var2.initVerify(var3);
      PublicKeyAndChallenge var4 = this.pkac;
      byte[] var5 = (new DERBitString(var4)).getBytes();
      var2.update(var5);
      byte[] var6 = this.signature.getBytes();
      return var2.verify(var6);
   }
}
