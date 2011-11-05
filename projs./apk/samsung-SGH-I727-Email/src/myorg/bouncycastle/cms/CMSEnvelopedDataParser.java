package myorg.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1SetParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.EncryptedContentInfoParser;
import myorg.bouncycastle.asn1.cms.EnvelopedDataParser;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSContentInfoParser;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInformationStore;

public class CMSEnvelopedDataParser extends CMSContentInfoParser {

   private boolean _attrNotRead;
   private AlgorithmIdentifier _encAlg;
   EnvelopedDataParser _envelopedData;
   RecipientInformationStore _recipientInfoStore;
   private AttributeTable _unprotectedAttributes;


   public CMSEnvelopedDataParser(InputStream var1) throws CMSException, IOException {
      super(var1);
      this._attrNotRead = (boolean)1;
      ASN1SequenceParser var2 = (ASN1SequenceParser)this._contentInfo.getContent(16);
      EnvelopedDataParser var3 = new EnvelopedDataParser(var2);
      this._envelopedData = var3;
      ASN1SetParser var4 = this._envelopedData.getRecipientInfos();
      ArrayList var5 = new ArrayList();

      while(true) {
         DEREncodable var6 = var4.readObject();
         if(var6 == null) {
            EncryptedContentInfoParser var9 = this._envelopedData.getEncryptedContentInfo();
            AlgorithmIdentifier var10 = var9.getContentEncryptionAlgorithm();
            this._encAlg = var10;
            InputStream var11 = ((ASN1OctetStringParser)var9.getEncryptedContent(4)).getOctetStream();
            Iterator var12 = var5.iterator();
            AlgorithmIdentifier var13 = this._encAlg;
            List var14 = CMSEnvelopedHelper.readRecipientInfos(var12, var11, var13, (AlgorithmIdentifier)null, (AlgorithmIdentifier)null);
            RecipientInformationStore var15 = new RecipientInformationStore(var14);
            this._recipientInfoStore = var15;
            return;
         }

         RecipientInfo var7 = RecipientInfo.getInstance(var6.getDERObject());
         var5.add(var7);
      }
   }

   public CMSEnvelopedDataParser(byte[] var1) throws CMSException, IOException {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      this((InputStream)var2);
   }

   private byte[] encodeObj(DEREncodable var1) throws IOException {
      byte[] var2;
      if(var1 != null) {
         var2 = var1.getDERObject().getEncoded();
      } else {
         var2 = null;
      }

      return var2;
   }

   public String getEncryptionAlgOID() {
      return this._encAlg.getObjectId().toString();
   }

   public byte[] getEncryptionAlgParams() {
      try {
         DEREncodable var1 = this._encAlg.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmParameters getEncryptionAlgorithmParameters(String var1) throws CMSException, NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getEncryptionAlgorithmParameters(var2);
   }

   public AlgorithmParameters getEncryptionAlgorithmParameters(Provider var1) throws CMSException {
      CMSEnvelopedHelper var2 = CMSEnvelopedHelper.INSTANCE;
      String var3 = this.getEncryptionAlgOID();
      byte[] var4 = this.getEncryptionAlgParams();
      return var2.getEncryptionAlgorithmParameters(var3, var4, var1);
   }

   public RecipientInformationStore getRecipientInfos() {
      return this._recipientInfoStore;
   }

   public AttributeTable getUnprotectedAttributes() throws IOException {
      if(this._unprotectedAttributes == null && this._attrNotRead) {
         ASN1SetParser var1 = this._envelopedData.getUnprotectedAttrs();
         this._attrNotRead = (boolean)0;
         if(var1 != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();

            while(true) {
               DEREncodable var3 = var1.readObject();
               if(var3 == null) {
                  DERSet var5 = new DERSet(var2);
                  AttributeTable var6 = new AttributeTable(var5);
                  this._unprotectedAttributes = var6;
                  break;
               }

               DERObject var4 = ((ASN1SequenceParser)var3).getDERObject();
               var2.add(var4);
            }
         }
      }

      return this._unprotectedAttributes;
   }
}
