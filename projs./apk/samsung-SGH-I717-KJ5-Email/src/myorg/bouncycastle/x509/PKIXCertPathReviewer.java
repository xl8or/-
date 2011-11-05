package myorg.bouncycastle.x509;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.x509.AccessDescription;
import myorg.bouncycastle.asn1.x509.AuthorityInformationAccess;
import myorg.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import myorg.bouncycastle.asn1.x509.BasicConstraints;
import myorg.bouncycastle.asn1.x509.CRLDistPoint;
import myorg.bouncycastle.asn1.x509.DistributionPoint;
import myorg.bouncycastle.asn1.x509.DistributionPointName;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.IssuingDistributionPoint;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.i18n.ErrorBundle;
import myorg.bouncycastle.i18n.LocaleString;
import myorg.bouncycastle.i18n.filter.TrustedInput;
import myorg.bouncycastle.i18n.filter.UntrustedInput;
import myorg.bouncycastle.i18n.filter.UntrustedUrlInput;
import myorg.bouncycastle.jce.provider.AnnotatedException;
import myorg.bouncycastle.jce.provider.CertPathValidatorUtilities;
import myorg.bouncycastle.x509.CertPathReviewerException;
import myorg.bouncycastle.x509.X509CRLStoreSelector;

public class PKIXCertPathReviewer extends CertPathValidatorUtilities {

   private static final String AUTH_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
   private static final String CRL_DIST_POINTS = X509Extensions.CRLDistributionPoints.getId();
   private static final String QC_STATEMENT = X509Extensions.QCStatements.getId();
   private static final String RESOURCE_NAME = "myorg.bouncycastle.x509.CertPathReviewerMessages";
   protected CertPath certPath;
   protected List certs;
   protected List[] errors;
   private boolean initialized;
   protected int n;
   protected List[] notifications;
   protected PKIXParameters pkixParams;
   protected PolicyNode policyTree;
   protected PublicKey subjectPublicKey;
   protected TrustAnchor trustAnchor;
   protected Date validDate;


   public PKIXCertPathReviewer() {}

   public PKIXCertPathReviewer(CertPath var1, PKIXParameters var2) throws CertPathReviewerException {
      this.init(var1, var2);
   }

   private String IPtoString(byte[] var1) {
      String var2;
      String var3;
      try {
         var2 = InetAddress.getByAddress(var1).getHostAddress();
      } catch (Exception var11) {
         StringBuffer var5 = new StringBuffer();
         int var6 = 0;

         while(true) {
            int var7 = var1.length;
            if(var6 == var7) {
               var3 = var5.toString();
               return var3;
            }

            String var8 = Integer.toHexString(var1[var6] & 255);
            var5.append(var8);
            StringBuffer var10 = var5.append(' ');
            ++var6;
         }
      }

      var3 = var2;
      return var3;
   }

   private void checkCriticalExtensions() {
      // $FF: Couldn't be decompiled
   }

   private void checkNameConstraints() {
      // $FF: Couldn't be decompiled
   }

   private void checkPathLength() {
      int var1 = this.n;
      int var2 = 0;

      for(int var3 = this.certs.size() - 1; var3 > 0; var3 += -1) {
         int var10000 = this.n - var3;
         X509Certificate var5 = (X509Certificate)this.certs.get(var3);
         if(!isSelfIssued(var5)) {
            if(var1 <= 0) {
               ErrorBundle var6 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.pathLenghtExtended");
               this.addError(var6);
            }

            var1 += -1;
            ++var2;
         }

         BasicConstraints var9;
         label31: {
            BasicConstraints var8;
            try {
               String var7 = BASIC_CONSTRAINTS;
               var8 = BasicConstraints.getInstance(getExtensionValue(var5, var7));
            } catch (AnnotatedException var18) {
               ErrorBundle var14 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.processLengthConstError");
               this.addError(var14, var3);
               var9 = null;
               break label31;
            }

            var9 = var8;
         }

         if(var9 != null) {
            BigInteger var10 = var9.getPathLenConstraint();
            if(var10 != null) {
               int var11 = var10.intValue();
               if(var11 < var1) {
                  ;
               }
            }
         }
      }

      Object[] var15 = new Object[1];
      Integer var16 = new Integer(var2);
      var15[0] = var16;
      ErrorBundle var17 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.totalPathLength", var15);
      this.addNotification(var17);
   }

   private void checkPolicy() {
      // $FF: Couldn't be decompiled
   }

   private void checkSignatures() {
      // $FF: Couldn't be decompiled
   }

   private X509CRL getCRL(String var1) throws CertPathReviewerException {
      X509CRL var2 = null;

      try {
         URL var3 = new URL(var1);
         if(var3.getProtocol().equals("http") || var3.getProtocol().equals("https")) {
            HttpURLConnection var4 = (HttpURLConnection)var3.openConnection();
            var4.setUseCaches((boolean)0);
            var4.setDoInput((boolean)1);
            var4.connect();
            if(var4.getResponseCode() != 200) {
               String var7 = var4.getResponseMessage();
               throw new Exception(var7);
            }

            CertificateFactory var5 = CertificateFactory.getInstance("X.509", "myBC");
            InputStream var6 = var4.getInputStream();
            var2 = (X509CRL)var5.generateCRL(var6);
         }

         return var2;
      } catch (Exception var14) {
         Object[] var9 = new Object[4];
         UntrustedInput var10 = new UntrustedInput(var1);
         var9[0] = var10;
         String var11 = var14.getMessage();
         var9[1] = var11;
         var9[2] = var14;
         String var12 = var14.getClass().getName();
         var9[3] = var12;
         ErrorBundle var13 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.loadCrlDistPointError", var9);
         throw new CertPathReviewerException(var13);
      }
   }

   private boolean processQcStatements(X509Certificate param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   protected void addError(ErrorBundle var1) {
      boolean var2 = this.errors[0].add(var1);
   }

   protected void addError(ErrorBundle var1, int var2) {
      if(var2 >= -1) {
         int var3 = this.n;
         if(var2 < var3) {
            List[] var4 = this.errors;
            int var5 = var2 + 1;
            boolean var6 = var4[var5].add(var1);
            return;
         }
      }

      throw new IndexOutOfBoundsException();
   }

   protected void addNotification(ErrorBundle var1) {
      boolean var2 = this.notifications[0].add(var1);
   }

   protected void addNotification(ErrorBundle var1, int var2) {
      if(var2 >= -1) {
         int var3 = this.n;
         if(var2 < var3) {
            List[] var4 = this.notifications;
            int var5 = var2 + 1;
            boolean var6 = var4[var5].add(var1);
            return;
         }
      }

      throw new IndexOutOfBoundsException();
   }

   protected void checkCRLs(PKIXParameters var1, X509Certificate var2, Date var3, X509Certificate var4, PublicKey var5, Vector var6, int var7) throws CertPathReviewerException {
      X509CRLStoreSelector var8 = new X509CRLStoreSelector();

      try {
         byte[] var9 = getEncodedIssuerPrincipal(var2).getEncoded();
         var8.addIssuerName(var9);
      } catch (IOException var406) {
         ErrorBundle var85 = new ErrorBundle;
         String var87 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
         String var88 = "CertPathReviewer.crlIssuerException";
         var85.<init>(var87, var88);
         CertPathReviewerException var89 = new CertPathReviewerException(var85, var406);
         throw var89;
      }

      var8.setCertificateChecking(var2);

      Iterator var18;
      Iterator var21;
      try {
         List var14 = var1.getCertStores();
         Collection var17 = findCRLs(var8, var14);
         var18 = var17.iterator();
         if(var17.isEmpty()) {
            X509CRLStoreSelector var19 = new X509CRLStoreSelector();
            List var20 = var1.getCertStores();
            var21 = findCRLs(var19, var20).iterator();

            X500Principal var23;
            ArrayList var22;
            boolean var26;
            for(var22 = new ArrayList(); var21.hasNext(); var26 = var22.add(var23)) {
               var23 = ((X509CRL)var21.next()).getIssuerX500Principal();
            }

            int var93 = var22.size();
            ErrorBundle var94 = new ErrorBundle;
            Object[] var95 = new Object[3];
            Collection var96 = var8.getIssuerNames();
            UntrustedInput var97 = new UntrustedInput(var96);
            var95[0] = var97;
            UntrustedInput var98 = new UntrustedInput(var22);
            var95[1] = var98;
            Integer var101 = new Integer(var93);
            var95[2] = var101;
            String var105 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var106 = "CertPathReviewer.noCrlInCertstore";
            var94.<init>(var105, var106, var95);
            this.addNotification(var94, var7);
         }
      } catch (AnnotatedException var408) {
         ErrorBundle var28 = new ErrorBundle;
         Object[] var29 = new Object[3];
         String var30 = var408.getCause().getMessage();
         var29[0] = var30;
         Throwable var31 = var408.getCause();
         var29[1] = var31;
         String var32 = var408.getCause().getClass().getName();
         var29[2] = var32;
         String var34 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
         String var35 = "CertPathReviewer.crlExtractionError";
         var28.<init>(var34, var35, var29);
         this.addError(var28, var7);
         var18 = (new ArrayList()).iterator();
      }

      boolean var40 = false;
      X509CRL var41 = null;

      while(var18.hasNext()) {
         var41 = (X509CRL)var18.next();
         if(var41.getNextUpdate() != null) {
            Date var42 = new Date();
            Date var43 = var41.getNextUpdate();
            if(!var42.before(var43)) {
               ErrorBundle var111 = new ErrorBundle;
               Object[] var112 = new Object[2];
               Date var113 = var41.getThisUpdate();
               TrustedInput var114 = new TrustedInput(var113);
               var112[0] = var114;
               Date var115 = var41.getNextUpdate();
               TrustedInput var116 = new TrustedInput(var115);
               var112[1] = var116;
               String var118 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var119 = "CertPathReviewer.localInvalidCRL";
               var111.<init>(var118, var119, var112);
               this.addNotification(var111, var7);
               continue;
            }
         }

         var40 = true;
         ErrorBundle var44 = new ErrorBundle;
         Object[] var45 = new Object[2];
         Date var46 = var41.getThisUpdate();
         TrustedInput var47 = new TrustedInput(var46);
         var45[0] = var47;
         Date var48 = var41.getNextUpdate();
         TrustedInput var49 = new TrustedInput(var48);
         var45[1] = var49;
         String var51 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
         String var52 = "CertPathReviewer.localValidCRL";
         var44.<init>(var51, var52, var45);
         this.addNotification(var44, var7);
         break;
      }

      if(!var40) {
         Iterator var57 = var6.iterator();

         while(var57.hasNext()) {
            X509CRL var61;
            try {
               String var58 = (String)var57.next();
               var61 = this.getCRL(var58);
               if(var61 == null) {
                  continue;
               }

               X500Principal var62 = var2.getIssuerX500Principal();
               X500Principal var63 = var61.getIssuerX500Principal();
               if(!var62.equals(var63)) {
                  ErrorBundle var64 = new ErrorBundle;
                  Object[] var65 = new Object[3];
                  String var66 = var61.getIssuerX500Principal().getName();
                  UntrustedInput var67 = new UntrustedInput(var66);
                  var65[0] = var67;
                  String var68 = var2.getIssuerX500Principal().getName();
                  UntrustedInput var69 = new UntrustedInput(var68);
                  var65[1] = var69;
                  UntrustedUrlInput var70 = new UntrustedUrlInput(var58);
                  var65[2] = var70;
                  String var74 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
                  String var75 = "CertPathReviewer.onlineCRLWrongCA";
                  var64.<init>(var74, var75, var65);
                  this.addNotification(var64, var7);
                  continue;
               }

               if(var61.getNextUpdate() != null) {
                  Date var124 = new Date();
                  Date var125 = var61.getNextUpdate();
                  if(!var124.before(var125)) {
                     ErrorBundle var152 = new ErrorBundle;
                     Object[] var153 = new Object[3];
                     Date var154 = var61.getThisUpdate();
                     TrustedInput var155 = new TrustedInput(var154);
                     var153[0] = var155;
                     Date var156 = var61.getNextUpdate();
                     TrustedInput var157 = new TrustedInput(var156);
                     var153[1] = var157;
                     UntrustedUrlInput var158 = new UntrustedUrlInput(var58);
                     var153[2] = var158;
                     String var162 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
                     String var163 = "CertPathReviewer.onlineInvalidCRL";
                     var152.<init>(var162, var163, var153);
                     this.addNotification(var152, var7);
                     continue;
                  }
               }

               var40 = true;
               ErrorBundle var126 = new ErrorBundle;
               Object[] var127 = new Object[3];
               Date var128 = var61.getThisUpdate();
               TrustedInput var129 = new TrustedInput(var128);
               var127[0] = var129;
               Date var130 = var61.getNextUpdate();
               TrustedInput var131 = new TrustedInput(var130);
               var127[1] = var131;
               UntrustedUrlInput var132 = new UntrustedUrlInput(var58);
               var127[2] = var132;
               String var136 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var137 = "CertPathReviewer.onlineValidCRL";
               var126.<init>(var136, var137, var127);
               this.addNotification(var126, var7);
            } catch (CertPathReviewerException var407) {
               ErrorBundle var80 = var407.getErrorMessage();
               this.addNotification(var80, var7);
               continue;
            }

            var41 = var61;
            break;
         }
      }

      if(var41 != null) {
         if(var4 != null) {
            boolean[] var142 = var4.getKeyUsage();
            if(var142 != null) {
               int var143 = var142.length;
               byte var144 = 7;
               if(var143 < var144 || !var142[6]) {
                  ErrorBundle var145 = new ErrorBundle;
                  String var147 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
                  String var148 = "CertPathReviewer.noCrlSigningPermited";
                  var145.<init>(var147, var148);
                  CertPathReviewerException var149 = new CertPathReviewerException(var145);
                  throw var149;
               }
            }
         }

         if(var5 == null) {
            ErrorBundle var210 = new ErrorBundle;
            String var212 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var213 = "CertPathReviewer.crlNoIssuerPublicKey";
            var210.<init>(var212, var213);
            CertPathReviewerException var214 = new CertPathReviewerException(var210);
            throw var214;
         }

         try {
            String var170 = "myBC";
            var41.verify(var5, var170);
         } catch (Exception var405) {
            ErrorBundle var202 = new ErrorBundle;
            String var204 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var205 = "CertPathReviewer.crlVerifyFailed";
            var202.<init>(var204, var205);
            CertPathReviewerException var206 = new CertPathReviewerException(var202, var405);
            throw var206;
         }

         BigInteger var171 = var2.getSerialNumber();
         X509CRLEntry var174 = var41.getRevokedCertificate(var171);
         if(var174 != null) {
            String var175 = null;
            if(var174.hasExtensions()) {
               DEREnumerated var179;
               try {
                  String var176 = X509Extensions.ReasonCode.getId();
                  var179 = DEREnumerated.getInstance(getExtensionValue(var174, var176));
               } catch (AnnotatedException var404) {
                  ErrorBundle var218 = new ErrorBundle;
                  String var220 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
                  String var221 = "CertPathReviewer.crlReasonExtError";
                  var218.<init>(var220, var221);
                  CertPathReviewerException var222 = new CertPathReviewerException(var218, var404);
                  throw var222;
               }

               if(var179 != null) {
                  String[] var181 = crlReasons;
                  int var182 = var179.getValue().intValue();
                  var175 = var181[var182];
               } else {
                  var175 = crlReasons[7];
               }
            }

            LocaleString var183 = new LocaleString;
            String var185 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            var183.<init>(var185, var175);
            Date var187 = var174.getRevocationDate();
            if(!var3.before(var187)) {
               ErrorBundle var190 = new ErrorBundle;
               Object[] var191 = new Object[2];
               Date var192 = var174.getRevocationDate();
               TrustedInput var193 = new TrustedInput(var192);
               var191[0] = var193;
               var191[1] = var183;
               String var195 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var196 = "CertPathReviewer.certRevoked";
               var190.<init>(var195, var196, var191);
               CertPathReviewerException var198 = new CertPathReviewerException(var190);
               throw var198;
            }

            ErrorBundle var226 = new ErrorBundle;
            Object[] var227 = new Object[2];
            Date var228 = var174.getRevocationDate();
            TrustedInput var229 = new TrustedInput(var228);
            var227[0] = var229;
            var227[1] = var183;
            String var231 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var232 = "CertPathReviewer.revokedAfterValidation";
            var226.<init>(var231, var232, var227);
            this.addNotification(var226, var7);
         } else {
            ErrorBundle var293 = new ErrorBundle;
            String var295 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var296 = "CertPathReviewer.notRevoked";
            var293.<init>(var295, var296);
            this.addNotification(var293, var7);
         }

         if(var41.getNextUpdate() != null) {
            Date var237 = var41.getNextUpdate();
            Date var238 = new Date();
            if(var237.before(var238)) {
               ErrorBundle var239 = new ErrorBundle;
               Object[] var240 = new Object[1];
               Date var241 = var41.getNextUpdate();
               TrustedInput var242 = new TrustedInput(var241);
               var240[0] = var242;
               String var244 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var245 = "CertPathReviewer.crlUpdateAvailable";
               var239.<init>(var244, var245, var240);
               this.addNotification(var239, var7);
            }
         }

         DERObject var253;
         try {
            String var250 = ISSUING_DISTRIBUTION_POINT;
            var253 = getExtensionValue(var41, var250);
         } catch (AnnotatedException var403) {
            ErrorBundle var301 = new ErrorBundle;
            String var303 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var304 = "CertPathReviewer.distrPtExtError";
            var301.<init>(var303, var304);
            CertPathReviewerException var305 = new CertPathReviewerException(var301);
            throw var305;
         }

         DERObject var254 = var253;

         DERObject var258;
         try {
            String var255 = DELTA_CRL_INDICATOR;
            var258 = getExtensionValue(var41, var255);
         } catch (AnnotatedException var402) {
            ErrorBundle var309 = new ErrorBundle;
            String var311 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
            String var312 = "CertPathReviewer.deltaCrlExtError";
            var309.<init>(var311, var312);
            CertPathReviewerException var313 = new CertPathReviewerException(var309);
            throw var313;
         }

         if(var258 != null) {
            X509CRLStoreSelector var260 = new X509CRLStoreSelector();

            try {
               byte[] var261 = getIssuerPrincipal(var41).getEncoded();
               var260.addIssuerName(var261);
            } catch (IOException var401) {
               ErrorBundle var317 = new ErrorBundle;
               String var319 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var320 = "CertPathReviewer.crlIssuerException";
               var317.<init>(var319, var320);
               CertPathReviewerException var321 = new CertPathReviewerException(var317, var401);
               throw var321;
            }

            BigInteger var264 = ((DERInteger)var258).getPositiveValue();
            var260.setMinCRLNumber(var264);

            try {
               String var267 = CRL_NUMBER;
               BigInteger var270 = ((DERInteger)getExtensionValue(var41, var267)).getPositiveValue();
               BigInteger var271 = BigInteger.valueOf(1L);
               BigInteger var272 = var270.subtract(var271);
               var260.setMaxCRLNumber(var272);
            } catch (AnnotatedException var400) {
               ErrorBundle var326 = new ErrorBundle;
               String var328 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var329 = "CertPathReviewer.crlNbrExtError";
               var326.<init>(var328, var329);
               CertPathReviewerException var330 = new CertPathReviewerException(var326, var400);
               throw var330;
            }

            boolean var275 = false;

            Iterator var279;
            try {
               List var276 = var1.getCertStores();
               var279 = findCRLs(var260, var276).iterator();
            } catch (AnnotatedException var399) {
               ErrorBundle var335 = new ErrorBundle;
               String var337 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var338 = "CertPathReviewer.crlExtractionError";
               var335.<init>(var337, var338);
               CertPathReviewerException var339 = new CertPathReviewerException(var335, var399);
               throw var339;
            }

            var21 = var279;

            while(var21.hasNext()) {
               X509CRL var280 = (X509CRL)var21.next();

               DERObject var284;
               try {
                  String var281 = ISSUING_DISTRIBUTION_POINT;
                  var284 = getExtensionValue(var280, var281);
               } catch (AnnotatedException var398) {
                  ErrorBundle var344 = new ErrorBundle;
                  String var346 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
                  String var347 = "CertPathReviewer.distrPtExtError";
                  var344.<init>(var346, var347);
                  CertPathReviewerException var348 = new CertPathReviewerException(var344, var398);
                  throw var348;
               }

               if(var254 == null) {
                  if(var284 == null) {
                     var275 = true;
                     break;
                  }
               } else if(var254.equals(var284)) {
                  var275 = true;
                  break;
               }
            }

            if(!var275) {
               ErrorBundle var286 = new ErrorBundle;
               String var288 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var289 = "CertPathReviewer.noBaseCRL";
               var286.<init>(var288, var289);
               CertPathReviewerException var290 = new CertPathReviewerException(var286);
               throw var290;
            }
         }

         if(var254 != null) {
            IssuingDistributionPoint var354 = IssuingDistributionPoint.getInstance(var254);

            BasicConstraints var358;
            try {
               String var355 = BASIC_CONSTRAINTS;
               var358 = BasicConstraints.getInstance(getExtensionValue(var2, var355));
            } catch (AnnotatedException var397) {
               ErrorBundle var368 = new ErrorBundle;
               String var370 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var371 = "CertPathReviewer.crlBCExtError";
               var368.<init>(var370, var371);
               CertPathReviewerException var372 = new CertPathReviewerException(var368, var397);
               throw var372;
            }

            if(var354.onlyContainsUserCerts() && var358 != null && var358.isCA()) {
               ErrorBundle var360 = new ErrorBundle;
               String var362 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var363 = "CertPathReviewer.crlOnlyUserCert";
               var360.<init>(var362, var363);
               CertPathReviewerException var364 = new CertPathReviewerException(var360);
               throw var364;
            }

            if(var354.onlyContainsCACerts() && (var358 == null || !var358.isCA())) {
               ErrorBundle var376 = new ErrorBundle;
               String var378 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var379 = "CertPathReviewer.crlOnlyCaCert";
               var376.<init>(var378, var379);
               CertPathReviewerException var380 = new CertPathReviewerException(var376);
               throw var380;
            }

            if(var354.onlyContainsAttributeCerts()) {
               ErrorBundle var383 = new ErrorBundle;
               String var385 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
               String var386 = "CertPathReviewer.crlOnlyAttrCert";
               var383.<init>(var385, var386);
               CertPathReviewerException var387 = new CertPathReviewerException(var383);
               throw var387;
            }
         }
      }

      if(!var40) {
         ErrorBundle var390 = new ErrorBundle;
         String var392 = "myorg.bouncycastle.x509.CertPathReviewerMessages";
         String var393 = "CertPathReviewer.noValidCrlFound";
         var390.<init>(var392, var393);
         CertPathReviewerException var394 = new CertPathReviewerException(var390);
         throw var394;
      }
   }

   protected void checkRevocation(PKIXParameters var1, X509Certificate var2, Date var3, X509Certificate var4, PublicKey var5, Vector var6, Vector var7, int var8) throws CertPathReviewerException {
      this.checkCRLs(var1, var2, var3, var4, var5, var6, var8);
   }

   protected void doChecks() {
      if(!this.initialized) {
         throw new IllegalStateException("Object not initialized. Call init() first.");
      } else if(this.notifications == null) {
         List[] var1 = new List[this.n + 1];
         this.notifications = var1;
         List[] var2 = new List[this.n + 1];
         this.errors = var2;
         int var3 = 0;

         while(true) {
            int var4 = this.notifications.length;
            if(var3 >= var4) {
               this.checkSignatures();
               this.checkNameConstraints();
               this.checkPathLength();
               this.checkPolicy();
               this.checkCriticalExtensions();
               return;
            }

            List[] var5 = this.notifications;
            ArrayList var6 = new ArrayList();
            var5[var3] = var6;
            List[] var7 = this.errors;
            ArrayList var8 = new ArrayList();
            var7[var3] = var8;
            ++var3;
         }
      }
   }

   protected Vector getCRLDistUrls(CRLDistPoint var1) {
      Vector var2 = new Vector();
      if(var1 != null) {
         DistributionPoint[] var3 = var1.getDistributionPoints();
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               break;
            }

            DistributionPointName var6 = var3[var4].getDistributionPoint();
            if(var6.getType() == 0) {
               GeneralName[] var7 = GeneralNames.getInstance(var6.getName()).getNames();
               int var8 = 0;

               while(true) {
                  int var9 = var7.length;
                  if(var8 >= var9) {
                     break;
                  }

                  if(var7[var8].getTagNo() == 6) {
                     String var10 = ((DERIA5String)var7[var8].getName()).getString();
                     var2.add(var10);
                  }

                  ++var8;
               }
            }

            ++var4;
         }
      }

      return var2;
   }

   public CertPath getCertPath() {
      return this.certPath;
   }

   public int getCertPathSize() {
      return this.n;
   }

   public List getErrors(int var1) {
      this.doChecks();
      List[] var2 = this.errors;
      int var3 = var1 + 1;
      return var2[var3];
   }

   public List[] getErrors() {
      this.doChecks();
      return this.errors;
   }

   public List getNotifications(int var1) {
      this.doChecks();
      List[] var2 = this.notifications;
      int var3 = var1 + 1;
      return var2[var3];
   }

   public List[] getNotifications() {
      this.doChecks();
      return this.notifications;
   }

   protected Vector getOCSPUrls(AuthorityInformationAccess var1) {
      Vector var2 = new Vector();
      if(var1 != null) {
         AccessDescription[] var3 = var1.getAccessDescriptions();
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               break;
            }

            DERObjectIdentifier var6 = var3[var4].getAccessMethod();
            DERObjectIdentifier var7 = AccessDescription.id_ad_ocsp;
            if(var6.equals(var7)) {
               GeneralName var8 = var3[var4].getAccessLocation();
               if(var8.getTagNo() == 6) {
                  String var9 = ((DERIA5String)var8.getName()).getString();
                  var2.add(var9);
               }
            }

            ++var4;
         }
      }

      return var2;
   }

   public PolicyNode getPolicyTree() {
      this.doChecks();
      return this.policyTree;
   }

   public PublicKey getSubjectPublicKey() {
      this.doChecks();
      return this.subjectPublicKey;
   }

   public TrustAnchor getTrustAnchor() {
      this.doChecks();
      return this.trustAnchor;
   }

   protected Collection getTrustAnchors(X509Certificate var1, Set var2) throws CertPathReviewerException {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();
      X509CertSelector var5 = new X509CertSelector();

      try {
         byte[] var6 = getEncodedIssuerPrincipal(var1).getEncoded();
         var5.setSubject(var6);
         String var7 = X509Extensions.AuthorityKeyIdentifier.getId();
         byte[] var8 = var1.getExtensionValue(var7);
         if(var8 != null) {
            AuthorityKeyIdentifier var9 = AuthorityKeyIdentifier.getInstance(ASN1Object.fromByteArray(((ASN1OctetString)ASN1Object.fromByteArray(var8)).getOctets()));
            BigInteger var10 = var9.getAuthorityCertSerialNumber();
            var5.setSerialNumber(var10);
            byte[] var11 = var9.getKeyIdentifier();
            if(var11 != null) {
               byte[] var12 = (new DEROctetString(var11)).getEncoded();
               var5.setSubjectKeyIdentifier(var12);
            }
         }
      } catch (IOException var22) {
         ErrorBundle var17 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.trustAnchorIssuerError");
         throw new CertPathReviewerException(var17);
      }

      while(var4.hasNext()) {
         TrustAnchor var13 = (TrustAnchor)var4.next();
         if(var13.getTrustedCert() != null) {
            X509Certificate var14 = var13.getTrustedCert();
            if(var5.match(var14)) {
               var3.add(var13);
            }
         } else if(var13.getCAName() != null && var13.getCAPublicKey() != null) {
            X500Principal var18 = getEncodedIssuerPrincipal(var1);
            String var19 = var13.getCAName();
            X500Principal var20 = new X500Principal(var19);
            if(var18.equals(var20)) {
               var3.add(var13);
            }
         }
      }

      return var3;
   }

   public void init(CertPath var1, PKIXParameters var2) throws CertPathReviewerException {
      if(this.initialized) {
         throw new IllegalStateException("object is already initialized!");
      } else {
         this.initialized = (boolean)1;
         if(var1 == null) {
            throw new NullPointerException("certPath was null");
         } else {
            this.certPath = var1;
            List var3 = var1.getCertificates();
            this.certs = var3;
            int var4 = this.certs.size();
            this.n = var4;
            if(this.certs.isEmpty()) {
               ErrorBundle var5 = new ErrorBundle("myorg.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.emptyCertPath");
               throw new CertPathReviewerException(var5);
            } else {
               PKIXParameters var6 = (PKIXParameters)var2.clone();
               this.pkixParams = var6;
               Date var7 = getValidDate(this.pkixParams);
               this.validDate = var7;
               this.notifications = null;
               this.errors = null;
               this.trustAnchor = null;
               this.subjectPublicKey = null;
               this.policyTree = null;
            }
         }
      }
   }

   public boolean isValidCertPath() {
      this.doChecks();
      boolean var1 = true;
      int var2 = 0;

      while(true) {
         int var3 = this.errors.length;
         if(var2 >= var3) {
            break;
         }

         if(!this.errors[var2].isEmpty()) {
            var1 = false;
            break;
         }

         ++var2;
      }

      return var1;
   }
}
