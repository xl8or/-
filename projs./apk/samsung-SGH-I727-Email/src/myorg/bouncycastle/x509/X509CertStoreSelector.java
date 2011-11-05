package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.util.Selector;

public class X509CertStoreSelector extends X509CertSelector implements Selector {

   public X509CertStoreSelector() {}

   public static X509CertStoreSelector getInstance(X509CertSelector var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("cannot create from null selector");
      } else {
         X509CertStoreSelector var1 = new X509CertStoreSelector();
         byte[] var2 = var0.getAuthorityKeyIdentifier();
         var1.setAuthorityKeyIdentifier(var2);
         int var3 = var0.getBasicConstraints();
         var1.setBasicConstraints(var3);
         X509Certificate var4 = var0.getCertificate();
         var1.setCertificate(var4);
         Date var5 = var0.getCertificateValid();
         var1.setCertificateValid(var5);
         boolean var6 = var0.getMatchAllSubjectAltNames();
         var1.setMatchAllSubjectAltNames(var6);

         try {
            Collection var7 = var0.getPathToNames();
            var1.setPathToNames(var7);
            Set var8 = var0.getExtendedKeyUsage();
            var1.setExtendedKeyUsage(var8);
            byte[] var9 = var0.getNameConstraints();
            var1.setNameConstraints(var9);
            Set var10 = var0.getPolicy();
            var1.setPolicy(var10);
            String var11 = var0.getSubjectPublicKeyAlgID();
            var1.setSubjectPublicKeyAlgID(var11);
            Collection var12 = var0.getSubjectAlternativeNames();
            var1.setSubjectAlternativeNames(var12);
         } catch (IOException var22) {
            String var21 = "error in passed in selector: " + var22;
            throw new IllegalArgumentException(var21);
         }

         X500Principal var13 = var0.getIssuer();
         var1.setIssuer(var13);
         boolean[] var14 = var0.getKeyUsage();
         var1.setKeyUsage(var14);
         Date var15 = var0.getPrivateKeyValid();
         var1.setPrivateKeyValid(var15);
         BigInteger var16 = var0.getSerialNumber();
         var1.setSerialNumber(var16);
         X500Principal var17 = var0.getSubject();
         var1.setSubject(var17);
         byte[] var18 = var0.getSubjectKeyIdentifier();
         var1.setSubjectKeyIdentifier(var18);
         PublicKey var19 = var0.getSubjectPublicKey();
         var1.setSubjectPublicKey(var19);
         return var1;
      }
   }

   public Object clone() {
      return (X509CertStoreSelector)super.clone();
   }

   public boolean match(Object var1) {
      byte var2;
      if(!(var1 instanceof X509Certificate)) {
         var2 = 0;
      } else {
         X509Certificate var3 = (X509Certificate)var1;
         var2 = super.match(var3);
      }

      return (boolean)var2;
   }

   public boolean match(Certificate var1) {
      return this.match((Object)var1);
   }
}
