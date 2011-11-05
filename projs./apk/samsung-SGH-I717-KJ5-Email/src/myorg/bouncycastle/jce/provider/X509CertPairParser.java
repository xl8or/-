package myorg.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.x509.CertificatePair;
import myorg.bouncycastle.jce.provider.ProviderUtil;
import myorg.bouncycastle.x509.X509CertificatePair;
import myorg.bouncycastle.x509.X509StreamParserSpi;
import myorg.bouncycastle.x509.util.StreamParsingException;

public class X509CertPairParser extends X509StreamParserSpi {

   private InputStream currentStream = null;


   public X509CertPairParser() {}

   private X509CertificatePair readDERCrossCertificatePair(InputStream var1) throws IOException, CertificateParsingException {
      int var2 = ProviderUtil.getReadLimit(var1);
      CertificatePair var3 = CertificatePair.getInstance((ASN1Sequence)(new ASN1InputStream(var1, var2)).readObject());
      return new X509CertificatePair(var3);
   }

   public void engineInit(InputStream var1) {
      this.currentStream = var1;
      if(!this.currentStream.markSupported()) {
         InputStream var2 = this.currentStream;
         BufferedInputStream var3 = new BufferedInputStream(var2);
         this.currentStream = var3;
      }
   }

   public Object engineRead() throws StreamParsingException {
      // $FF: Couldn't be decompiled
   }

   public Collection engineReadAll() throws StreamParsingException {
      ArrayList var1 = new ArrayList();

      while(true) {
         X509CertificatePair var2 = (X509CertificatePair)this.engineRead();
         if(var2 == null) {
            return var1;
         }

         var1.add(var2);
      }
   }
}
