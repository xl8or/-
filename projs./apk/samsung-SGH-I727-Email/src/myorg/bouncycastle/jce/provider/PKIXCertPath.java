package myorg.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.pkcs.ContentInfo;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.SignedData;
import myorg.bouncycastle.openssl.PEMWriter;

public class PKIXCertPath extends CertPath {

   static final List certPathEncodings;
   private List certificates;


   static {
      ArrayList var0 = new ArrayList();
      boolean var1 = var0.add("PkiPath");
      boolean var2 = var0.add("PEM");
      boolean var3 = var0.add("PKCS7");
      certPathEncodings = Collections.unmodifiableList(var0);
   }

   PKIXCertPath(InputStream var1, String var2) throws CertificateException {
      super("X.509");

      label68: {
         IOException var4;
         label67: {
            NoSuchProviderException var15;
            label66: {
               CertificateFactory var10;
               BufferedInputStream var19;
               try {
                  if(var2.equalsIgnoreCase("PkiPath")) {
                     DERObject var3 = (new ASN1InputStream(var1)).readObject();
                     if(!(var3 instanceof ASN1Sequence)) {
                        throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                     }

                     Enumeration var8 = ((ASN1Sequence)var3).getObjects();
                     ArrayList var9 = new ArrayList();
                     this.certificates = var9;
                     var10 = CertificateFactory.getInstance("X.509", "myBC");

                     while(true) {
                        if(!var8.hasMoreElements()) {
                           break label68;
                        }

                        byte[] var11 = ((ASN1Encodable)var8.nextElement()).getEncoded("DER");
                        List var12 = this.certificates;
                        ByteArrayInputStream var13 = new ByteArrayInputStream(var11);
                        Certificate var14 = var10.generateCertificate(var13);
                        var12.add(0, var14);
                     }
                  }

                  if(!var2.equalsIgnoreCase("PKCS7") && !var2.equalsIgnoreCase("PEM")) {
                     String var27 = "unsupported encoding: " + var2;
                     throw new CertificateException(var27);
                  }

                  var19 = new BufferedInputStream(var1);
               } catch (IOException var31) {
                  var4 = var31;
                  break label67;
               } catch (NoSuchProviderException var32) {
                  var15 = var32;
                  break label66;
               }

               try {
                  ArrayList var20 = new ArrayList();
                  this.certificates = var20;
                  var10 = CertificateFactory.getInstance("X.509", "myBC");

                  while(true) {
                     Certificate var21 = var10.generateCertificate(var19);
                     if(var21 == null) {
                        break label68;
                     }

                     this.certificates.add(var21);
                  }
               } catch (IOException var29) {
                  var4 = var29;
                  break label67;
               } catch (NoSuchProviderException var30) {
                  var15 = var30;
               }
            }

            StringBuilder var16 = (new StringBuilder()).append("BouncyCastle provider not found while trying to get a CertificateFactory:\n");
            String var17 = var15.toString();
            String var18 = var16.append(var17).toString();
            throw new CertificateException(var18);
         }

         StringBuilder var5 = (new StringBuilder()).append("IOException throw while decoding CertPath:\n");
         String var6 = var4.toString();
         String var7 = var5.append(var6).toString();
         throw new CertificateException(var7);
      }

      List var25 = this.certificates;
      List var26 = this.sortCerts(var25);
      this.certificates = var26;
   }

   PKIXCertPath(List var1) {
      super("X.509");
      ArrayList var2 = new ArrayList(var1);
      List var3 = this.sortCerts(var2);
      this.certificates = var3;
   }

   private List sortCerts(List var1) {
      Object var2;
      if(var1.size() < 2) {
         var2 = var1;
      } else {
         X500Principal var3 = ((X509Certificate)var1.get(0)).getIssuerX500Principal();
         boolean var4 = true;
         int var5 = 1;

         while(true) {
            int var6 = var1.size();
            if(var5 == var6) {
               break;
            }

            X500Principal var7 = ((X509Certificate)var1.get(var5)).getSubjectX500Principal();
            if(!var3.equals(var7)) {
               var4 = false;
               break;
            }

            var3 = ((X509Certificate)var1.get(var5)).getIssuerX500Principal();
            ++var5;
         }

         if(var4) {
            var2 = var1;
         } else {
            int var8 = var1.size();
            ArrayList var9 = new ArrayList(var8);
            ArrayList var10 = new ArrayList(var1);
            byte var28 = 0;

            while(true) {
               int var11 = var1.size();
               if(var28 >= var11) {
                  if(var9.size() > 1) {
                     var2 = var10;
                     break;
                  } else {
                     var28 = 0;

                     while(true) {
                        int var20 = var9.size();
                        if(var28 == var20) {
                           if(var1.size() > 0) {
                              var2 = var10;
                           } else {
                              var2 = var9;
                           }

                           return (List)var2;
                        }

                        var3 = ((X509Certificate)var9.get(var28)).getIssuerX500Principal();
                        int var21 = 0;

                        while(true) {
                           int var22 = var1.size();
                           if(var21 >= var22) {
                              break;
                           }

                           X509Certificate var23 = (X509Certificate)var1.get(var21);
                           X500Principal var24 = var23.getSubjectX500Principal();
                           if(var3.equals(var24)) {
                              var9.add(var23);
                              var1.remove(var21);
                              break;
                           }

                           ++var21;
                        }

                        int var27 = var28 + 1;
                     }
                  }
               }

               X509Certificate var12 = (X509Certificate)var1.get(var28);
               boolean var13 = false;
               X500Principal var14 = var12.getSubjectX500Principal();
               int var15 = 0;

               while(true) {
                  int var16 = var1.size();
                  if(var15 == var16) {
                     break;
                  }

                  if(((X509Certificate)var1.get(var15)).getIssuerX500Principal().equals(var14)) {
                     var13 = true;
                     break;
                  }

                  ++var15;
               }

               if(!var13) {
                  var9.add(var12);
                  var1.remove(var28);
               }

               int var19 = var28 + 1;
            }
         }
      }

      return (List)var2;
   }

   private DERObject toASN1Object(X509Certificate var1) throws CertificateEncodingException {
      try {
         byte[] var2 = var1.getEncoded();
         DERObject var3 = (new ASN1InputStream(var2)).readObject();
         return var3;
      } catch (Exception var8) {
         StringBuilder var5 = (new StringBuilder()).append("Exception while encoding certificate: ");
         String var6 = var8.toString();
         String var7 = var5.append(var6).toString();
         throw new CertificateEncodingException(var7);
      }
   }

   private byte[] toDEREncoded(ASN1Encodable var1) throws CertificateEncodingException {
      try {
         byte[] var2 = var1.getEncoded("DER");
         return var2;
      } catch (IOException var5) {
         String var4 = "Exception thrown: " + var5;
         throw new CertificateEncodingException(var4);
      }
   }

   public List getCertificates() {
      List var1 = this.certificates;
      return Collections.unmodifiableList(new ArrayList(var1));
   }

   public byte[] getEncoded() throws CertificateEncodingException {
      Iterator var1 = this.getEncodings();
      byte[] var4;
      if(var1.hasNext()) {
         Object var2 = var1.next();
         if(var2 instanceof String) {
            String var3 = (String)var2;
            var4 = this.getEncoded(var3);
            return var4;
         }
      }

      var4 = null;
      return var4;
   }

   public byte[] getEncoded(String var1) throws CertificateEncodingException {
      ASN1EncodableVector var2;
      byte[] var9;
      if(var1.equalsIgnoreCase("PkiPath")) {
         var2 = new ASN1EncodableVector();
         List var3 = this.certificates;
         int var4 = this.certificates.size();
         ListIterator var5 = var3.listIterator(var4);

         while(var5.hasPrevious()) {
            X509Certificate var6 = (X509Certificate)var5.previous();
            DERObject var7 = this.toASN1Object(var6);
            var2.add(var7);
         }

         DERSequence var8 = new DERSequence(var2);
         var9 = this.toDEREncoded(var8);
      } else if(var1.equalsIgnoreCase("PKCS7")) {
         DERObjectIdentifier var10 = PKCSObjectIdentifiers.data;
         ContentInfo var11 = new ContentInfo(var10, (DEREncodable)null);
         var2 = new ASN1EncodableVector();
         int var12 = 0;

         while(true) {
            int var13 = this.certificates.size();
            if(var12 == var13) {
               DERInteger var16 = new DERInteger(1);
               DERSet var17 = new DERSet();
               DERSet var18 = new DERSet(var2);
               DERSet var19 = new DERSet();
               SignedData var20 = new SignedData(var16, var17, var11, var18, (ASN1Set)null, var19);
               DERObjectIdentifier var21 = PKCSObjectIdentifiers.signedData;
               ContentInfo var22 = new ContentInfo(var21, var20);
               var9 = this.toDEREncoded(var22);
               break;
            }

            X509Certificate var14 = (X509Certificate)this.certificates.get(var12);
            DERObject var15 = this.toASN1Object(var14);
            var2.add(var15);
            ++var12;
         }
      } else {
         if(!var1.equalsIgnoreCase("PEM")) {
            String var31 = "unsupported encoding: " + var1;
            throw new CertificateEncodingException(var31);
         }

         ByteArrayOutputStream var23 = new ByteArrayOutputStream();
         OutputStreamWriter var24 = new OutputStreamWriter(var23);
         PEMWriter var25 = new PEMWriter(var24);
         byte var43 = 0;

         while(true) {
            boolean var38 = false;

            try {
               var38 = true;
               int var26 = this.certificates.size();
               if(var43 == var26) {
                  var38 = false;
                  break;
               }

               Object var27 = this.certificates.get(var43);
               var25.writeObject(var27);
               var38 = false;
            } catch (Exception var41) {
               throw new CertificateEncodingException("can\'t encode certificate for PEM encoded path");
            } finally {
               if(var38) {
                  try {
                     var25.close();
                  } catch (IOException var39) {
                     ;
                  }

               }
            }

            int var28 = var43 + 1;
         }

         try {
            var25.close();
         } catch (IOException var40) {
            ;
         }

         var9 = var23.toByteArray();
      }

      return var9;
   }

   public Iterator getEncodings() {
      return certPathEncodings.iterator();
   }
}
