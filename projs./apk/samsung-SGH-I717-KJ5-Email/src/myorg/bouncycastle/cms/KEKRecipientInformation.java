package myorg.bouncycastle.cms;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import myorg.bouncycastle.asn1.cms.KEKIdentifier;
import myorg.bouncycastle.asn1.cms.KEKRecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientId;
import myorg.bouncycastle.cms.RecipientInformation;

public class KEKRecipientInformation extends RecipientInformation {

   private KEKRecipientInfo info;


   public KEKRecipientInformation(KEKRecipientInfo var1, AlgorithmIdentifier var2, InputStream var3) {
      Object var7 = null;
      this(var1, var2, (AlgorithmIdentifier)null, (AlgorithmIdentifier)var7, var3);
   }

   public KEKRecipientInformation(KEKRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, InputStream var4) {
      this(var1, var2, var3, (AlgorithmIdentifier)null, var4);
   }

   KEKRecipientInformation(KEKRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, InputStream var5) {
      AlgorithmIdentifier var6 = var1.getKeyEncryptionAlgorithm();
      super(var2, var3, var4, var6, var5);
      this.info = var1;
      RecipientId var12 = new RecipientId();
      this.rid = var12;
      KEKIdentifier var13 = var1.getKekid();
      RecipientId var14 = this.rid;
      byte[] var15 = var13.getKeyIdentifier().getOctets();
      var14.setKeyIdentifier(var15);
   }

   public CMSTypedStream getContentStream(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContentStream(var1, var3);
   }

   public CMSTypedStream getContentStream(Key var1, Provider var2) throws CMSException {
      try {
         byte[] var3 = this.info.getEncryptedKey().getOctets();
         Cipher var4 = Cipher.getInstance(this.keyEncAlg.getObjectId().getId(), var2);
         var4.init(4, var1);
         String var5 = this.getActiveAlgID().getObjectId().getId();
         Key var6 = var4.unwrap(var3, var5, 3);
         CMSTypedStream var7 = this.getContentFromSessionKey(var6, var2);
         return var7;
      } catch (NoSuchAlgorithmException var11) {
         throw new CMSException("can\'t find algorithm.", var11);
      } catch (InvalidKeyException var12) {
         throw new CMSException("key invalid in message.", var12);
      } catch (NoSuchPaddingException var13) {
         throw new CMSException("required padding not supported.", var13);
      }
   }
}
