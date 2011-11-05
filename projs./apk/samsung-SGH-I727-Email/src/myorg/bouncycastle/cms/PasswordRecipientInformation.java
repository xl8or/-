package myorg.bouncycastle.cms;

import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.PasswordRecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSPBEKey;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientId;
import myorg.bouncycastle.cms.RecipientInformation;

public class PasswordRecipientInformation extends RecipientInformation {

   private PasswordRecipientInfo info;


   public PasswordRecipientInformation(PasswordRecipientInfo var1, AlgorithmIdentifier var2, InputStream var3) {
      Object var7 = null;
      this(var1, var2, (AlgorithmIdentifier)null, (AlgorithmIdentifier)var7, var3);
   }

   public PasswordRecipientInformation(PasswordRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, InputStream var4) {
      this(var1, var2, var3, (AlgorithmIdentifier)null, var4);
   }

   PasswordRecipientInformation(PasswordRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, InputStream var5) {
      AlgorithmIdentifier var6 = var1.getKeyEncryptionAlgorithm();
      super(var2, var3, var4, var6, var5);
      this.info = var1;
      RecipientId var12 = new RecipientId();
      this.rid = var12;
   }

   public CMSTypedStream getContentStream(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContentStream(var1, var3);
   }

   public CMSTypedStream getContentStream(Key var1, Provider var2) throws CMSException {
      try {
         ASN1Sequence var3 = (ASN1Sequence)AlgorithmIdentifier.getInstance(this.info.getKeyEncryptionAlgorithm()).getParameters();
         byte[] var4 = this.info.getEncryptedKey().getOctets();
         String var5 = DERObjectIdentifier.getInstance(var3.getObjectAt(0)).getId();
         Cipher var6 = Cipher.getInstance(CMSEnvelopedHelper.INSTANCE.getRFC3211WrapperName(var5), var2);
         byte[] var7 = ASN1OctetString.getInstance(var3.getObjectAt(1)).getOctets();
         IvParameterSpec var8 = new IvParameterSpec(var7);
         byte[] var9 = ((CMSPBEKey)var1).getEncoded(var5);
         SecretKeySpec var10 = new SecretKeySpec(var9, var5);
         var6.init(4, var10, var8);
         String var11 = this.getActiveAlgID().getObjectId().getId();
         Key var12 = var6.unwrap(var4, var11, 3);
         CMSTypedStream var13 = this.getContentFromSessionKey(var12, var2);
         return var13;
      } catch (NoSuchAlgorithmException var18) {
         throw new CMSException("can\'t find algorithm.", var18);
      } catch (InvalidKeyException var19) {
         throw new CMSException("key invalid in message.", var19);
      } catch (NoSuchPaddingException var20) {
         throw new CMSException("required padding not supported.", var20);
      } catch (InvalidAlgorithmParameterException var21) {
         throw new CMSException("invalid iv.", var21);
      }
   }

   public String getKeyDerivationAlgOID() {
      String var1;
      if(this.info.getKeyDerivationAlgorithm() != null) {
         var1 = this.info.getKeyDerivationAlgorithm().getObjectId().getId();
      } else {
         var1 = null;
      }

      return var1;
   }

   public AlgorithmParameters getKeyDerivationAlgParameters(String var1) throws NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getKeyDerivationAlgParameters(var2);
   }

   public AlgorithmParameters getKeyDerivationAlgParameters(Provider var1) {
      AlgorithmParameters var3;
      AlgorithmParameters var5;
      label27: {
         try {
            if(this.info.getKeyDerivationAlgorithm() != null) {
               DEREncodable var2 = this.info.getKeyDerivationAlgorithm().getParameters();
               if(var2 != null) {
                  var3 = AlgorithmParameters.getInstance(this.info.getKeyDerivationAlgorithm().getObjectId().toString(), var1);
                  byte[] var4 = var2.getDERObject().getEncoded();
                  var3.init(var4);
                  break label27;
               }
            }
         } catch (Exception var8) {
            String var7 = "exception getting encryption parameters " + var8;
            throw new RuntimeException(var7);
         }

         var5 = null;
         return var5;
      }

      var5 = var3;
      return var5;
   }

   public byte[] getKeyDerivationAlgParams() {
      byte[] var2;
      byte[] var3;
      label27: {
         try {
            if(this.info.getKeyDerivationAlgorithm() != null) {
               DEREncodable var1 = this.info.getKeyDerivationAlgorithm().getParameters();
               if(var1 != null) {
                  var2 = var1.getDERObject().getEncoded();
                  break label27;
               }
            }
         } catch (Exception var6) {
            String var5 = "exception getting encryption parameters " + var6;
            throw new RuntimeException(var5);
         }

         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }
}
