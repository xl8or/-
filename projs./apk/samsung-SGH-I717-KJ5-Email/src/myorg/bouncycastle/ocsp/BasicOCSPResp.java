package myorg.bouncycastle.ocsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import myorg.bouncycastle.asn1.ocsp.ResponderID;
import myorg.bouncycastle.asn1.ocsp.ResponseData;
import myorg.bouncycastle.asn1.ocsp.SingleResponse;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPUtil;
import myorg.bouncycastle.ocsp.RespData;
import myorg.bouncycastle.ocsp.RespID;
import myorg.bouncycastle.ocsp.SingleResp;

public class BasicOCSPResp implements X509Extension {

   X509Certificate[] chain = null;
   ResponseData data;
   BasicOCSPResponse resp;


   public BasicOCSPResp(BasicOCSPResponse var1) {
      this.resp = var1;
      ResponseData var2 = var1.getTbsResponseData();
      this.data = var2;
   }

   private List getCertList(String var1) throws OCSPException, NoSuchProviderException {
      ArrayList var2 = new ArrayList();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      ASN1OutputStream var4 = new ASN1OutputStream(var3);

      CertificateFactory var5;
      try {
         var5 = OCSPUtil.createX509CertificateFactory(var1);
      } catch (CertificateException var19) {
         throw new OCSPException("can\'t get certificate factory.", var19);
      }

      CertificateFactory var6 = var5;
      ASN1Sequence var7 = this.resp.getCerts();
      if(var7 != null) {
         for(Enumeration var8 = var7.getObjects(); var8.hasMoreElements(); var3.reset()) {
            try {
               Object var9 = var8.nextElement();
               var4.writeObject(var9);
               byte[] var10 = var3.toByteArray();
               ByteArrayInputStream var11 = new ByteArrayInputStream(var10);
               Certificate var12 = var6.generateCertificate(var11);
               var2.add(var12);
            } catch (IOException var17) {
               throw new OCSPException("can\'t re-encode certificate!", var17);
            } catch (CertificateException var18) {
               throw new OCSPException("can\'t re-encode certificate!", var18);
            }
         }
      }

      return var2;
   }

   private Set getExtensionOIDs(boolean var1) {
      HashSet var2 = new HashSet();
      X509Extensions var3 = this.getResponseExtensions();
      if(var3 != null) {
         Enumeration var4 = var3.oids();

         while(var4.hasMoreElements()) {
            DERObjectIdentifier var5 = (DERObjectIdentifier)var4.nextElement();
            boolean var6 = var3.getExtension(var5).isCritical();
            if(var1 == var6) {
               String var7 = var5.getId();
               var2.add(var7);
            }
         }
      }

      return var2;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(!(var1 instanceof BasicOCSPResp)) {
         var2 = 0;
      } else {
         BasicOCSPResp var3 = (BasicOCSPResp)var1;
         BasicOCSPResponse var4 = this.resp;
         BasicOCSPResponse var5 = var3.resp;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public CertStore getCertificates(String var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, OCSPException {
      try {
         List var3 = this.getCertList(var2);
         CollectionCertStoreParameters var4 = new CollectionCertStoreParameters(var3);
         CertStore var5 = OCSPUtil.createCertStoreInstance(var1, var4, var2);
         return var5;
      } catch (InvalidAlgorithmParameterException var7) {
         throw new OCSPException("can\'t setup the CertStore", var7);
      }
   }

   public X509Certificate[] getCerts(String var1) throws OCSPException, NoSuchProviderException {
      List var2 = this.getCertList(var1);
      X509Certificate[] var3 = new X509Certificate[var2.size()];
      return (X509Certificate[])((X509Certificate[])var2.toArray(var3));
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getEncoded() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ASN1OutputStream var2 = new ASN1OutputStream(var1);
      BasicOCSPResponse var3 = this.resp;
      var2.writeObject(var3);
      return var1.toByteArray();
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.getResponseExtensions();
      byte[] var6;
      if(var2 != null) {
         DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
         myorg.bouncycastle.asn1.x509.X509Extension var4 = var2.getExtension(var3);
         if(var4 != null) {
            byte[] var5;
            try {
               var5 = var4.getValue().getEncoded("DER");
            } catch (Exception var11) {
               StringBuilder var8 = (new StringBuilder()).append("error encoding ");
               String var9 = var11.toString();
               String var10 = var8.append(var9).toString();
               throw new RuntimeException(var10);
            }

            var6 = var5;
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)0);
   }

   public Date getProducedAt() {
      try {
         Date var1 = this.data.getProducedAt().getDate();
         return var1;
      } catch (ParseException var6) {
         StringBuilder var3 = (new StringBuilder()).append("ParseException:");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new IllegalStateException(var5);
      }
   }

   public RespID getResponderId() {
      ResponderID var1 = this.data.getResponderID();
      return new RespID(var1);
   }

   public RespData getResponseData() {
      ResponseData var1 = this.resp.getTbsResponseData();
      return new RespData(var1);
   }

   public X509Extensions getResponseExtensions() {
      return this.data.getResponseExtensions();
   }

   public SingleResp[] getResponses() {
      ASN1Sequence var1 = this.data.getResponses();
      SingleResp[] var2 = new SingleResp[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return var2;
         }

         SingleResponse var5 = SingleResponse.getInstance(var1.getObjectAt(var3));
         SingleResp var6 = new SingleResp(var5);
         var2[var3] = var6;
         ++var3;
      }
   }

   public byte[] getSignature() {
      return this.resp.getSignature().getBytes();
   }

   public String getSignatureAlgName() {
      return OCSPUtil.getAlgorithmName(this.resp.getSignatureAlgorithm().getObjectId());
   }

   public String getSignatureAlgOID() {
      return this.resp.getSignatureAlgorithm().getObjectId().getId();
   }

   public byte[] getTBSResponseData() throws OCSPException {
      try {
         byte[] var1 = this.resp.getTbsResponseData().getEncoded();
         return var1;
      } catch (IOException var3) {
         throw new OCSPException("problem encoding tbsResponseData", var3);
      }
   }

   public int getVersion() {
      return this.data.getVersion().getValue().intValue() + 1;
   }

   public boolean hasUnsupportedCriticalExtension() {
      Set var1 = this.getCriticalExtensionOIDs();
      boolean var2;
      if(var1 != null && !var1.isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.resp.hashCode();
   }

   public boolean verify(PublicKey var1, String var2) throws OCSPException, NoSuchProviderException {
      try {
         Signature var3 = OCSPUtil.createSignatureInstance(this.getSignatureAlgName(), var2);
         var3.initVerify(var1);
         byte[] var4 = this.resp.getTbsResponseData().getEncoded("DER");
         var3.update(var4);
         byte[] var5 = this.getSignature();
         boolean var6 = var3.verify(var5);
         return var6;
      } catch (NoSuchProviderException var9) {
         throw var9;
      } catch (Exception var10) {
         String var8 = "exception processing sig: " + var10;
         throw new OCSPException(var8, var10);
      }
   }
}
