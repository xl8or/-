package myorg.bouncycastle.ocsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.OCSPRequest;
import myorg.bouncycastle.asn1.ocsp.Request;
import myorg.bouncycastle.asn1.ocsp.TBSRequest;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPUtil;
import myorg.bouncycastle.ocsp.Req;

public class OCSPReq implements X509Extension {

   private OCSPRequest req;


   public OCSPReq(InputStream var1) throws IOException {
      ASN1InputStream var2 = new ASN1InputStream(var1);
      this(var2);
   }

   private OCSPReq(ASN1InputStream var1) throws IOException {
      try {
         OCSPRequest var2 = OCSPRequest.getInstance(var1.readObject());
         this.req = var2;
      } catch (IllegalArgumentException var11) {
         StringBuilder var4 = (new StringBuilder()).append("malformed request: ");
         String var5 = var11.getMessage();
         String var6 = var4.append(var5).toString();
         throw new IOException(var6);
      } catch (ClassCastException var12) {
         StringBuilder var8 = (new StringBuilder()).append("malformed request: ");
         String var9 = var12.getMessage();
         String var10 = var8.append(var9).toString();
         throw new IOException(var10);
      }
   }

   public OCSPReq(OCSPRequest var1) {
      this.req = var1;
   }

   public OCSPReq(byte[] var1) throws IOException {
      ASN1InputStream var2 = new ASN1InputStream(var1);
      this(var2);
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
      ASN1Sequence var7 = this.req.getOptionalSignature().getCerts();
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
      X509Extensions var3 = this.getRequestExtensions();
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

   public CertStore getCertificates(String var1, String var2) throws NoSuchAlgorithmException, NoSuchProviderException, OCSPException {
      CertStore var3;
      if(!this.isSigned()) {
         var3 = null;
      } else {
         CertStore var6;
         try {
            List var4 = this.getCertList(var2);
            CollectionCertStoreParameters var5 = new CollectionCertStoreParameters(var4);
            var6 = OCSPUtil.createCertStoreInstance(var1, var5, var2);
         } catch (InvalidAlgorithmParameterException var8) {
            throw new OCSPException("can\'t setup the CertStore", var8);
         }

         var3 = var6;
      }

      return var3;
   }

   public X509Certificate[] getCerts(String var1) throws OCSPException, NoSuchProviderException {
      X509Certificate[] var2;
      if(!this.isSigned()) {
         var2 = null;
      } else {
         List var3 = this.getCertList(var1);
         X509Certificate[] var4 = new X509Certificate[var3.size()];
         var2 = (X509Certificate[])((X509Certificate[])var3.toArray(var4));
      }

      return var2;
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs((boolean)1);
   }

   public byte[] getEncoded() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ASN1OutputStream var2 = new ASN1OutputStream(var1);
      OCSPRequest var3 = this.req;
      var2.writeObject(var3);
      return var1.toByteArray();
   }

   public byte[] getExtensionValue(String var1) {
      X509Extensions var2 = this.getRequestExtensions();
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

   public X509Extensions getRequestExtensions() {
      return X509Extensions.getInstance(this.req.getTbsRequest().getRequestExtensions());
   }

   public Req[] getRequestList() {
      ASN1Sequence var1 = this.req.getTbsRequest().getRequestList();
      Req[] var2 = new Req[var1.size()];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return var2;
         }

         Request var5 = Request.getInstance(var1.getObjectAt(var3));
         Req var6 = new Req(var5);
         var2[var3] = var6;
         ++var3;
      }
   }

   public GeneralName getRequestorName() {
      return GeneralName.getInstance(this.req.getTbsRequest().getRequestorName());
   }

   public byte[] getSignature() {
      byte[] var1;
      if(!this.isSigned()) {
         var1 = null;
      } else {
         var1 = this.req.getOptionalSignature().getSignature().getBytes();
      }

      return var1;
   }

   public String getSignatureAlgOID() {
      String var1;
      if(!this.isSigned()) {
         var1 = null;
      } else {
         var1 = this.req.getOptionalSignature().getSignatureAlgorithm().getObjectId().getId();
      }

      return var1;
   }

   public byte[] getTBSRequest() throws OCSPException {
      try {
         byte[] var1 = this.req.getTbsRequest().getEncoded();
         return var1;
      } catch (IOException var3) {
         throw new OCSPException("problem encoding tbsRequest", var3);
      }
   }

   public int getVersion() {
      return this.req.getTbsRequest().getVersion().getValue().intValue() + 1;
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

   public boolean isSigned() {
      boolean var1;
      if(this.req.getOptionalSignature() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean verify(PublicKey var1, String var2) throws OCSPException, NoSuchProviderException {
      if(!this.isSigned()) {
         throw new OCSPException("attempt to verify signature on unsigned object");
      } else {
         try {
            Signature var3 = OCSPUtil.createSignatureInstance(this.getSignatureAlgOID(), var2);
            var3.initVerify(var1);
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            ASN1OutputStream var5 = new ASN1OutputStream(var4);
            TBSRequest var6 = this.req.getTbsRequest();
            var5.writeObject(var6);
            byte[] var7 = var4.toByteArray();
            var3.update(var7);
            byte[] var8 = this.getSignature();
            boolean var9 = var3.verify(var8);
            return var9;
         } catch (NoSuchProviderException var12) {
            throw var12;
         } catch (Exception var13) {
            String var11 = "exception processing sig: " + var13;
            throw new OCSPException(var11, var13);
         }
      }
   }
}
