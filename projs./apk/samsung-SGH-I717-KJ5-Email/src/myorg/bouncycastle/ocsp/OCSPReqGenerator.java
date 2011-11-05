package myorg.bouncycastle.ocsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.ocsp.OCSPRequest;
import myorg.bouncycastle.asn1.ocsp.Request;
import myorg.bouncycastle.asn1.ocsp.Signature;
import myorg.bouncycastle.asn1.ocsp.TBSRequest;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.ocsp.CertificateID;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPReq;
import myorg.bouncycastle.ocsp.OCSPUtil;

public class OCSPReqGenerator {

   private List list;
   private X509Extensions requestExtensions;
   private GeneralName requestorName;


   public OCSPReqGenerator() {
      ArrayList var1 = new ArrayList();
      this.list = var1;
      this.requestorName = null;
      this.requestExtensions = null;
   }

   private OCSPReq generateRequest(DERObjectIdentifier var1, PrivateKey var2, X509Certificate[] var3, String var4, SecureRandom var5) throws OCSPException, NoSuchProviderException {
      Iterator var6 = this.list.iterator();
      ASN1EncodableVector var7 = new ASN1EncodableVector();

      while(var6.hasNext()) {
         try {
            Request var8 = ((OCSPReqGenerator.RequestObject)var6.next()).toRequest();
            var7.add(var8);
         } catch (Exception var97) {
            OCSPException var12 = new OCSPException;
            String var14 = "exception creating Request";
            var12.<init>(var14, var97);
            throw var12;
         }
      }

      TBSRequest var16 = new TBSRequest;
      GeneralName var17 = this.requestorName;
      DERSequence var18 = new DERSequence(var7);
      X509Extensions var21 = this.requestExtensions;
      var16.<init>(var17, var18, var21);
      Signature var26 = null;
      if(var1 != null) {
         if(this.requestorName == null) {
            throw new OCSPException("requestorName must be specified if request is signed.");
         }

         java.security.Signature var29;
         try {
            String var27 = var1.getId();
            var29 = OCSPUtil.createSignatureInstance(var27, var4);
            if(var5 != null) {
               var29.initSign(var2, var5);
            } else {
               var29.initSign(var2);
            }
         } catch (NoSuchProviderException var95) {
            throw var95;
         } catch (GeneralSecurityException var96) {
            OCSPException var58 = new OCSPException;
            StringBuilder var59 = (new StringBuilder()).append("exception creating signature: ");
            String var61 = var59.append(var96).toString();
            var58.<init>(var61, var96);
            throw var58;
         }

         DERBitString var37;
         try {
            ByteArrayOutputStream var33 = new ByteArrayOutputStream();
            (new ASN1OutputStream(var33)).writeObject(var16);
            byte[] var34 = var33.toByteArray();
            var29.update(var34);
            var37 = new DERBitString;
            byte[] var38 = var29.sign();
            var37.<init>(var38);
         } catch (Exception var94) {
            OCSPException var66 = new OCSPException;
            StringBuilder var67 = (new StringBuilder()).append("exception processing TBSRequest: ");
            String var69 = var67.append(var94).toString();
            var66.<init>(var69, var94);
            throw var66;
         }

         AlgorithmIdentifier var41 = new AlgorithmIdentifier;
         DERNull var42 = new DERNull();
         var41.<init>(var1, var42);
         if(var3 != null && var3.length > 0) {
            ASN1EncodableVector var46 = new ASN1EncodableVector();
            int var47 = 0;

            while(true) {
               try {
                  int var48 = var3.length;
                  if(var47 == var48) {
                     break;
                  }

                  X509CertificateStructure var51 = new X509CertificateStructure;
                  ASN1Sequence var52 = (ASN1Sequence)ASN1Object.fromByteArray(var3[var47].getEncoded());
                  var51.<init>(var52);
                  var46.add(var51);
               } catch (IOException var98) {
                  OCSPException var74 = new OCSPException;
                  String var76 = "error processing certs";
                  var74.<init>(var76, var98);
                  throw var74;
               } catch (CertificateEncodingException var99) {
                  OCSPException var79 = new OCSPException;
                  String var81 = "error encoding certs";
                  var79.<init>(var81, var99);
                  throw var79;
               }

               ++var47;
            }

            var26 = new Signature;
            DERSequence var83 = new DERSequence(var46);
            var26.<init>(var41, var37, var83);
         } else {
            var26 = new Signature(var41, var37);
         }
      }

      OCSPRequest var90 = new OCSPRequest(var16, var26);
      return new OCSPReq(var90);
   }

   public void addRequest(CertificateID var1) {
      List var2 = this.list;
      OCSPReqGenerator.RequestObject var3 = new OCSPReqGenerator.RequestObject(var1, (X509Extensions)null);
      var2.add(var3);
   }

   public void addRequest(CertificateID var1, X509Extensions var2) {
      List var3 = this.list;
      OCSPReqGenerator.RequestObject var4 = new OCSPReqGenerator.RequestObject(var1, var2);
      var3.add(var4);
   }

   public OCSPReq generate() throws OCSPException {
      OCSPReqGenerator var1 = this;

      try {
         OCSPReq var2 = var1.generateRequest((DERObjectIdentifier)null, (PrivateKey)null, (X509Certificate[])null, (String)null, (SecureRandom)null);
         return var2;
      } catch (NoSuchProviderException var5) {
         String var4 = "no provider! - " + var5;
         throw new OCSPException(var4, var5);
      }
   }

   public OCSPReq generate(String var1, PrivateKey var2, X509Certificate[] var3, String var4) throws OCSPException, NoSuchProviderException, IllegalArgumentException {
      return this.generate(var1, var2, var3, var4, (SecureRandom)null);
   }

   public OCSPReq generate(String var1, PrivateKey var2, X509Certificate[] var3, String var4, SecureRandom var5) throws OCSPException, NoSuchProviderException, IllegalArgumentException {
      if(var1 == null) {
         throw new IllegalArgumentException("no signing algorithm specified");
      } else {
         try {
            DERObjectIdentifier var6 = OCSPUtil.getAlgorithmOID(var1);
            OCSPReq var12 = this.generateRequest(var6, var2, var3, var4, var5);
            return var12;
         } catch (IllegalArgumentException var15) {
            String var14 = "unknown signing algorithm specified: " + var1;
            throw new IllegalArgumentException(var14);
         }
      }
   }

   public Iterator getSignatureAlgNames() {
      return OCSPUtil.getAlgNames();
   }

   public void setRequestExtensions(X509Extensions var1) {
      this.requestExtensions = var1;
   }

   public void setRequestorName(X500Principal var1) {
      try {
         byte[] var2 = var1.getEncoded();
         X509Principal var3 = new X509Principal(var2);
         GeneralName var4 = new GeneralName(4, var3);
         this.requestorName = var4;
      } catch (IOException var7) {
         String var6 = "cannot encode principal: " + var7;
         throw new IllegalArgumentException(var6);
      }
   }

   public void setRequestorName(GeneralName var1) {
      this.requestorName = var1;
   }

   private class RequestObject {

      CertificateID certId;
      X509Extensions extensions;


      public RequestObject(CertificateID var2, X509Extensions var3) {
         this.certId = var2;
         this.extensions = var3;
      }

      public Request toRequest() throws Exception {
         CertID var1 = this.certId.toASN1Object();
         X509Extensions var2 = this.extensions;
         return new Request(var1, var2);
      }
   }
}
