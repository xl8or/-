package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BEROctetStringGenerator;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.x509.CertificateList;
import myorg.bouncycastle.asn1.x509.TBSCertificateStructure;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.util.io.Streams;

public class CMSUtils {

   private static final Runtime RUNTIME = Runtime.getRuntime();


   public CMSUtils() {}

   static OutputStream createBEROctetOutputStream(OutputStream var0, int var1, boolean var2, int var3) throws IOException {
      BEROctetStringGenerator var4 = new BEROctetStringGenerator(var0, var1, var2);
      OutputStream var6;
      if(var3 != 0) {
         byte[] var5 = new byte[var3];
         var6 = var4.getOctetOutputStream(var5);
      } else {
         var6 = var4.getOctetOutputStream();
      }

      return var6;
   }

   static ASN1Set createBerSetFromList(List var0) {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         DEREncodable var3 = (DEREncodable)var2.next();
         var1.add(var3);
      }

      return new BERSet(var1);
   }

   static ASN1Set createDerSetFromList(List var0) {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         DEREncodable var3 = (DEREncodable)var2.next();
         var1.add(var3);
      }

      return new DERSet(var1);
   }

   static List getCRLsFromStore(CertStore var0) throws CertStoreException, CMSException {
      ArrayList var1 = new ArrayList();

      try {
         Iterator var2 = var0.getCRLs((CRLSelector)null).iterator();

         while(var2.hasNext()) {
            CertificateList var3 = CertificateList.getInstance(ASN1Object.fromByteArray(((X509CRL)var2.next()).getEncoded()));
            var1.add(var3);
         }

         return var1;
      } catch (IllegalArgumentException var8) {
         throw new CMSException("error processing crls", var8);
      } catch (IOException var9) {
         throw new CMSException("error processing crls", var9);
      } catch (CRLException var10) {
         throw new CMSException("error encoding crls", var10);
      }
   }

   static List getCertificatesFromStore(CertStore var0) throws CertStoreException, CMSException {
      ArrayList var1 = new ArrayList();

      try {
         Iterator var2 = var0.getCertificates((CertSelector)null).iterator();

         while(var2.hasNext()) {
            X509CertificateStructure var3 = X509CertificateStructure.getInstance(ASN1Object.fromByteArray(((X509Certificate)var2.next()).getEncoded()));
            var1.add(var3);
         }

         return var1;
      } catch (IllegalArgumentException var8) {
         throw new CMSException("error processing certs", var8);
      } catch (IOException var9) {
         throw new CMSException("error processing certs", var9);
      } catch (CertificateEncodingException var10) {
         throw new CMSException("error encoding certs", var10);
      }
   }

   static int getMaximumMemory() {
      long var0 = RUNTIME.maxMemory();
      int var2;
      if(var0 > 2147483647L) {
         var2 = Integer.MAX_VALUE;
      } else {
         var2 = (int)var0;
      }

      return var2;
   }

   public static Provider getProvider(String var0) throws NoSuchProviderException {
      Provider var2;
      if(var0 != null) {
         Provider var1 = Security.getProvider(var0);
         if(var1 == null) {
            String var3 = "provider " + var0 + " not found.";
            throw new NoSuchProviderException(var3);
         }

         var2 = var1;
      } else {
         var2 = null;
      }

      return var2;
   }

   static TBSCertificateStructure getTBSCertificateStructure(X509Certificate var0) throws CertificateEncodingException {
      try {
         TBSCertificateStructure var1 = TBSCertificateStructure.getInstance(ASN1Object.fromByteArray(var0.getTBSCertificate()));
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new CertificateEncodingException(var2);
      }
   }

   public static ContentInfo readContentInfo(InputStream var0) throws CMSException {
      int var1 = getMaximumMemory();
      return readContentInfo(new ASN1InputStream(var0, var1));
   }

   private static ContentInfo readContentInfo(ASN1InputStream var0) throws CMSException {
      try {
         ContentInfo var1 = ContentInfo.getInstance(var0.readObject());
         return var1;
      } catch (IOException var5) {
         throw new CMSException("IOException reading content.", var5);
      } catch (ClassCastException var6) {
         throw new CMSException("Malformed content.", var6);
      } catch (IllegalArgumentException var7) {
         throw new CMSException("Malformed content.", var7);
      }
   }

   static ContentInfo readContentInfo(byte[] var0) throws CMSException {
      return readContentInfo(new ASN1InputStream(var0));
   }

   public static byte[] streamToByteArray(InputStream var0) throws IOException {
      return Streams.readAll(var0);
   }

   public static byte[] streamToByteArray(InputStream var0, int var1) throws IOException {
      return Streams.readAllLimited(var0, var1);
   }
}
