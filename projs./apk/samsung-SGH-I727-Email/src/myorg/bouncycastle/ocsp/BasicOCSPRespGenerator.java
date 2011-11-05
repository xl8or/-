package myorg.bouncycastle.ocsp;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.ocsp.CertStatus;
import myorg.bouncycastle.asn1.ocsp.ResponderID;
import myorg.bouncycastle.asn1.ocsp.ResponseData;
import myorg.bouncycastle.asn1.ocsp.RevokedInfo;
import myorg.bouncycastle.asn1.ocsp.SingleResponse;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CRLReason;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.ocsp.BasicOCSPResp;
import myorg.bouncycastle.ocsp.CertificateID;
import myorg.bouncycastle.ocsp.CertificateStatus;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPUtil;
import myorg.bouncycastle.ocsp.RespID;
import myorg.bouncycastle.ocsp.RevokedStatus;
import myorg.bouncycastle.ocsp.UnknownStatus;

public class BasicOCSPRespGenerator {

   private List list;
   private RespID responderID;
   private X509Extensions responseExtensions;


   public BasicOCSPRespGenerator(PublicKey var1) throws OCSPException {
      ArrayList var2 = new ArrayList();
      this.list = var2;
      this.responseExtensions = null;
      RespID var3 = new RespID(var1);
      this.responderID = var3;
   }

   public BasicOCSPRespGenerator(RespID var1) {
      ArrayList var2 = new ArrayList();
      this.list = var2;
      this.responseExtensions = null;
      this.responderID = var1;
   }

   private BasicOCSPResp generateResponse(String var1, PrivateKey var2, X509Certificate[] var3, Date var4, String var5, SecureRandom var6) throws OCSPException, NoSuchProviderException {
      Iterator var7 = this.list.iterator();

      DERObjectIdentifier var8;
      try {
         var8 = OCSPUtil.getAlgorithmOID(var1);
      } catch (Exception var98) {
         throw new IllegalArgumentException("unknown signing algorithm specified");
      }

      ASN1EncodableVector var10 = new ASN1EncodableVector();

      while(var7.hasNext()) {
         try {
            SingleResponse var11 = ((BasicOCSPRespGenerator.ResponseObject)var7.next()).toResponse();
            var10.add(var11);
         } catch (Exception var97) {
            OCSPException var15 = new OCSPException;
            String var17 = "exception creating Request";
            var15.<init>(var17, var97);
            throw var15;
         }
      }

      ResponseData var20 = new ResponseData;
      ResponderID var21 = this.responderID.toASN1Object();
      DERGeneralizedTime var22 = new DERGeneralizedTime(var4);
      DERSequence var25 = new DERSequence(var10);
      X509Extensions var28 = this.responseExtensions;
      var20.<init>(var21, var22, var25, var28);

      Signature var36;
      try {
         var36 = OCSPUtil.createSignatureInstance(var1, var5);
         if(var6 != null) {
            var36.initSign(var2, var6);
         } else {
            var36.initSign(var2);
         }
      } catch (NoSuchProviderException var95) {
         throw var95;
      } catch (GeneralSecurityException var96) {
         OCSPException var63 = new OCSPException;
         StringBuilder var64 = (new StringBuilder()).append("exception creating signature: ");
         String var66 = var64.append(var96).toString();
         var63.<init>(var66, var96);
         throw var63;
      }

      DERBitString var45;
      try {
         String var41 = "DER";
         byte[] var42 = var20.getEncoded(var41);
         var36.update(var42);
         var45 = new DERBitString;
         byte[] var46 = var36.sign();
         var45.<init>(var46);
      } catch (Exception var94) {
         OCSPException var71 = new OCSPException;
         StringBuilder var72 = (new StringBuilder()).append("exception processing TBSRequest: ");
         String var74 = var72.append(var94).toString();
         var71.<init>(var74, var94);
         throw var71;
      }

      AlgorithmIdentifier var49 = OCSPUtil.getSigAlgID(var8);
      DERSequence var50 = null;
      if(var3 != null && var3.length > 0) {
         ASN1EncodableVector var51 = new ASN1EncodableVector();
         int var52 = 0;

         while(true) {
            try {
               int var53 = var3.length;
               if(var52 == var53) {
                  break;
               }

               X509CertificateStructure var56 = new X509CertificateStructure;
               ASN1Sequence var57 = (ASN1Sequence)ASN1Object.fromByteArray(var3[var52].getEncoded());
               var56.<init>(var57);
               var51.add(var56);
            } catch (IOException var99) {
               OCSPException var79 = new OCSPException;
               String var81 = "error processing certs";
               var79.<init>(var81, var99);
               throw var79;
            } catch (CertificateEncodingException var100) {
               OCSPException var84 = new OCSPException;
               String var86 = "error encoding certs";
               var84.<init>(var86, var100);
               throw var84;
            }

            ++var52;
         }

         var50 = new DERSequence(var51);
      }

      BasicOCSPResponse var88 = new BasicOCSPResponse(var20, var49, var45, var50);
      return new BasicOCSPResp(var88);
   }

   public void addResponse(CertificateID var1, CertificateStatus var2) {
      List var3 = this.list;
      Date var4 = new Date();
      Object var8 = null;
      BasicOCSPRespGenerator.ResponseObject var9 = new BasicOCSPRespGenerator.ResponseObject(var1, var2, var4, (Date)null, (X509Extensions)var8);
      var3.add(var9);
   }

   public void addResponse(CertificateID var1, CertificateStatus var2, Date var3, Date var4, X509Extensions var5) {
      List var6 = this.list;
      BasicOCSPRespGenerator.ResponseObject var13 = new BasicOCSPRespGenerator.ResponseObject(var1, var2, var3, var4, var5);
      var6.add(var13);
   }

   public void addResponse(CertificateID var1, CertificateStatus var2, Date var3, X509Extensions var4) {
      List var5 = this.list;
      Date var6 = new Date();
      BasicOCSPRespGenerator.ResponseObject var12 = new BasicOCSPRespGenerator.ResponseObject(var1, var2, var6, var3, var4);
      var5.add(var12);
   }

   public void addResponse(CertificateID var1, CertificateStatus var2, X509Extensions var3) {
      List var4 = this.list;
      Date var5 = new Date();
      BasicOCSPRespGenerator.ResponseObject var10 = new BasicOCSPRespGenerator.ResponseObject(var1, var2, var5, (Date)null, var3);
      var4.add(var10);
   }

   public BasicOCSPResp generate(String var1, PrivateKey var2, X509Certificate[] var3, Date var4, String var5) throws OCSPException, NoSuchProviderException, IllegalArgumentException {
      return this.generate(var1, var2, var3, var4, var5, (SecureRandom)null);
   }

   public BasicOCSPResp generate(String var1, PrivateKey var2, X509Certificate[] var3, Date var4, String var5, SecureRandom var6) throws OCSPException, NoSuchProviderException, IllegalArgumentException {
      if(var1 == null) {
         throw new IllegalArgumentException("no signing algorithm specified");
      } else {
         return this.generateResponse(var1, var2, var3, var4, var5, var6);
      }
   }

   public Iterator getSignatureAlgNames() {
      return OCSPUtil.getAlgNames();
   }

   public void setResponseExtensions(X509Extensions var1) {
      this.responseExtensions = var1;
   }

   private class ResponseObject {

      CertificateID certId;
      CertStatus certStatus;
      X509Extensions extensions;
      DERGeneralizedTime nextUpdate;
      DERGeneralizedTime thisUpdate;


      public ResponseObject(CertificateID var2, CertificateStatus var3, Date var4, Date var5, X509Extensions var6) {
         this.certId = var2;
         if(var3 == null) {
            CertStatus var7 = new CertStatus();
            this.certStatus = var7;
         } else if(var3 instanceof UnknownStatus) {
            DERNull var10 = new DERNull();
            CertStatus var11 = new CertStatus(2, var10);
            this.certStatus = var11;
         } else {
            RevokedStatus var12 = (RevokedStatus)var3;
            if(var12.hasRevocationReason()) {
               Date var13 = var12.getRevocationTime();
               DERGeneralizedTime var14 = new DERGeneralizedTime(var13);
               int var15 = var12.getRevocationReason();
               CRLReason var16 = new CRLReason(var15);
               RevokedInfo var17 = new RevokedInfo(var14, var16);
               CertStatus var18 = new CertStatus(var17);
               this.certStatus = var18;
            } else {
               Date var19 = var12.getRevocationTime();
               DERGeneralizedTime var20 = new DERGeneralizedTime(var19);
               RevokedInfo var21 = new RevokedInfo(var20, (CRLReason)null);
               CertStatus var22 = new CertStatus(var21);
               this.certStatus = var22;
            }
         }

         DERGeneralizedTime var8 = new DERGeneralizedTime(var4);
         this.thisUpdate = var8;
         if(var5 != null) {
            DERGeneralizedTime var9 = new DERGeneralizedTime(var5);
            this.nextUpdate = var9;
         } else {
            this.nextUpdate = null;
         }

         this.extensions = var6;
      }

      public SingleResponse toResponse() throws Exception {
         CertID var1 = this.certId.toASN1Object();
         CertStatus var2 = this.certStatus;
         DERGeneralizedTime var3 = this.thisUpdate;
         DERGeneralizedTime var4 = this.nextUpdate;
         X509Extensions var5 = this.extensions;
         return new SingleResponse(var1, var2, var3, var4, var5);
      }
   }
}
