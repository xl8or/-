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
import myorg.bouncycastle.asn1.cms.AuthenticatedDataParser;
import myorg.bouncycastle.asn1.cms.RecipientInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSContentInfoParser;
import myorg.bouncycastle.cms.CMSEnvelopedHelper;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;
import myorg.bouncycastle.cms.RecipientInformationStore;
import myorg.bouncycastle.util.Arrays;

public class CMSAuthenticatedDataParser extends CMSContentInfoParser {

   RecipientInformationStore _recipientInfoStore;
   private boolean authAttrNotRead;
   private AttributeTable authAttrs;
   AuthenticatedDataParser authData;
   private byte[] mac;
   private AlgorithmIdentifier macAlg;
   private boolean unauthAttrNotRead;
   private AttributeTable unauthAttrs;


   public CMSAuthenticatedDataParser(InputStream var1) throws CMSException, IOException {
      super(var1);
      this.authAttrNotRead = (boolean)1;
      ASN1SequenceParser var2 = (ASN1SequenceParser)this._contentInfo.getContent(16);
      AuthenticatedDataParser var3 = new AuthenticatedDataParser(var2);
      this.authData = var3;
      ASN1SetParser var4 = this.authData.getRecipientInfos();
      ArrayList var5 = new ArrayList();

      while(true) {
         DEREncodable var6 = var4.readObject();
         if(var6 == null) {
            AlgorithmIdentifier var9 = this.authData.getMacAlgorithm();
            this.macAlg = var9;
            InputStream var10 = ((ASN1OctetStringParser)this.authData.getEnapsulatedContentInfo().getContent(4)).getOctetStream();
            Iterator var11 = var5.iterator();
            AlgorithmIdentifier var12 = this.macAlg;
            List var13 = CMSEnvelopedHelper.readRecipientInfos(var11, var10, (AlgorithmIdentifier)null, var12, (AlgorithmIdentifier)null);
            RecipientInformationStore var14 = new RecipientInformationStore(var13);
            this._recipientInfoStore = var14;
            return;
         }

         RecipientInfo var7 = RecipientInfo.getInstance(var6.getDERObject());
         var5.add(var7);
      }
   }

   public CMSAuthenticatedDataParser(byte[] var1) throws CMSException, IOException {
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

   public AttributeTable getAuthAttrs() throws IOException {
      if(this.authAttrs == null && this.authAttrNotRead) {
         ASN1SetParser var1 = this.authData.getAuthAttrs();
         this.authAttrNotRead = (boolean)0;
         if(var1 != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();

            while(true) {
               DEREncodable var3 = var1.readObject();
               if(var3 == null) {
                  DERSet var5 = new DERSet(var2);
                  AttributeTable var6 = new AttributeTable(var5);
                  this.authAttrs = var6;
                  break;
               }

               DERObject var4 = ((ASN1SequenceParser)var3).getDERObject();
               var2.add(var4);
            }
         }
      }

      return this.authAttrs;
   }

   public byte[] getMac() throws IOException {
      if(this.mac == null) {
         AttributeTable var1 = this.getAuthAttrs();
         byte[] var2 = this.authData.getMac().getOctets();
         this.mac = var2;
      }

      return Arrays.clone(this.mac);
   }

   public String getMacAlgOID() {
      return this.macAlg.getObjectId().toString();
   }

   public byte[] getMacAlgParams() {
      try {
         DEREncodable var1 = this.macAlg.getParameters();
         byte[] var2 = this.encodeObj(var1);
         return var2;
      } catch (Exception var5) {
         String var4 = "exception getting encryption parameters " + var5;
         throw new RuntimeException(var4);
      }
   }

   public AlgorithmParameters getMacAlgorithmParameters(String var1) throws CMSException, NoSuchProviderException {
      Provider var2 = CMSUtils.getProvider(var1);
      return this.getMacAlgorithmParameters(var2);
   }

   public AlgorithmParameters getMacAlgorithmParameters(Provider var1) throws CMSException {
      CMSEnvelopedHelper var2 = CMSEnvelopedHelper.INSTANCE;
      String var3 = this.getMacAlgOID();
      byte[] var4 = this.getMacAlgParams();
      return var2.getEncryptionAlgorithmParameters(var3, var4, var1);
   }

   public RecipientInformationStore getRecipientInfos() {
      return this._recipientInfoStore;
   }

   public AttributeTable getUnauthAttrs() throws IOException {
      if(this.unauthAttrs == null && this.unauthAttrNotRead) {
         ASN1SetParser var1 = this.authData.getUnauthAttrs();
         this.unauthAttrNotRead = (boolean)0;
         if(var1 != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();

            while(true) {
               DEREncodable var3 = var1.readObject();
               if(var3 == null) {
                  DERSet var5 = new DERSet(var2);
                  AttributeTable var6 = new AttributeTable(var5);
                  this.unauthAttrs = var6;
                  break;
               }

               DERObject var4 = ((ASN1SequenceParser)var3).getDERObject();
               var2.add(var4);
            }
         }
      }

      return this.unauthAttrs;
   }
}
