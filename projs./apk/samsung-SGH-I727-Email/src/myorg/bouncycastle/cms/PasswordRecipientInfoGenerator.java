package myorg.bouncycastle.cms;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.PasswordRecipientInfo;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.RecipientInfoGenerator;

class PasswordRecipientInfoGenerator implements RecipientInfoGenerator {

   private AlgorithmIdentifier derivationAlg;
   private SecretKey wrapKey;


   PasswordRecipientInfoGenerator() {}

   public RecipientInfo generate(SecretKey var1, SecureRandom var2, Provider var3) throws GeneralSecurityException {
      CMSEnvelopedHelper var4 = CMSEnvelopedHelper.INSTANCE;
      String var5 = this.wrapKey.getAlgorithm();
      String var6 = var4.getRFC3211WrapperName(var5);
      Cipher var7 = var4.createAsymmetricCipher(var6, var3);
      SecretKey var8 = this.wrapKey;
      var7.init(3, var8, var2);
      byte[] var9 = var7.wrap(var1);
      DEROctetString var10 = new DEROctetString(var9);
      ASN1EncodableVector var11 = new ASN1EncodableVector();
      String var12 = this.wrapKey.getAlgorithm();
      DERObjectIdentifier var13 = new DERObjectIdentifier(var12);
      var11.add(var13);
      byte[] var14 = var7.getIV();
      DEROctetString var15 = new DEROctetString(var14);
      var11.add(var15);
      DERObjectIdentifier var16 = PKCSObjectIdentifiers.id_alg_PWRI_KEK;
      DERSequence var17 = new DERSequence(var11);
      AlgorithmIdentifier var18 = new AlgorithmIdentifier(var16, var17);
      AlgorithmIdentifier var19 = this.derivationAlg;
      PasswordRecipientInfo var20 = new PasswordRecipientInfo(var19, var18, var10);
      return new RecipientInfo(var20);
   }

   void setDerivationAlg(AlgorithmIdentifier var1) {
      this.derivationAlg = var1;
   }

   void setWrapKey(SecretKey var1) {
      this.wrapKey = var1;
   }
}
