package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Provider;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import myorg.bouncycastle.asn1.cms.RecipientIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSTypedStream;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientId;
import myorg.bouncycastle.cms.RecipientInformation;

public class KeyTransRecipientInformation extends RecipientInformation {

   private KeyTransRecipientInfo info;


   public KeyTransRecipientInformation(KeyTransRecipientInfo var1, AlgorithmIdentifier var2, InputStream var3) {
      Object var7 = null;
      this(var1, var2, (AlgorithmIdentifier)null, (AlgorithmIdentifier)var7, var3);
   }

   public KeyTransRecipientInformation(KeyTransRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, InputStream var4) {
      this(var1, var2, var3, (AlgorithmIdentifier)null, var4);
   }

   KeyTransRecipientInformation(KeyTransRecipientInfo var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3, AlgorithmIdentifier var4, InputStream var5) {
      AlgorithmIdentifier var6 = var1.getKeyEncryptionAlgorithm();
      super(var2, var3, var4, var6, var5);
      this.info = var1;
      RecipientId var12 = new RecipientId();
      this.rid = var12;
      RecipientIdentifier var13 = var1.getRecipientIdentifier();

      try {
         if(var13.isTagged()) {
            ASN1OctetString var14 = ASN1OctetString.getInstance(var13.getId());
            RecipientId var15 = this.rid;
            byte[] var16 = var14.getOctets();
            var15.setSubjectKeyIdentifier(var16);
         } else {
            IssuerAndSerialNumber var17 = IssuerAndSerialNumber.getInstance(var13.getId());
            RecipientId var18 = this.rid;
            byte[] var19 = var17.getName().getEncoded();
            var18.setIssuer(var19);
            RecipientId var20 = this.rid;
            BigInteger var21 = var17.getSerialNumber().getValue();
            var20.setSerialNumber(var21);
         }
      } catch (IOException var23) {
         throw new IllegalArgumentException("invalid rid in KeyTransRecipientInformation");
      }
   }

   private String getExchangeEncryptionAlgorithmName(DERObjectIdentifier var1) {
      String var2;
      if(PKCSObjectIdentifiers.rsaEncryption.equals(var1)) {
         var2 = "RSA/ECB/PKCS1Padding";
      } else {
         var2 = var1.getId();
      }

      return var2;
   }

   public CMSTypedStream getContentStream(Key var1, String var2) throws CMSException, NoSuchProviderException {
      Provider var3 = CMSUtils.getProvider(var2);
      return this.getContentStream(var1, var3);
   }

   public CMSTypedStream getContentStream(Key var1, Provider var2) throws CMSException {
      Key var3 = this.getSessionKey(var1, var2);
      return this.getContentFromSessionKey(var3, var2);
   }

   protected Key getSessionKey(Key param1, Provider param2) throws CMSException {
      // $FF: Couldn't be decompiled
   }
}
