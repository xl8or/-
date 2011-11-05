package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.Holder;
import myorg.bouncycastle.asn1.x509.IssuerSerial;
import myorg.bouncycastle.asn1.x509.ObjectDigestInfo;
import myorg.bouncycastle.jce.PrincipalUtil;
import myorg.bouncycastle.jce.X509Principal;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.X509Util;

public class AttributeCertificateHolder implements CertSelector, Selector {

   final Holder holder;


   public AttributeCertificateHolder(int var1, String var2, String var3, byte[] var4) {
      AlgorithmIdentifier var5 = new AlgorithmIdentifier(var2);
      byte[] var6 = Arrays.clone(var4);
      ObjectDigestInfo var7 = new ObjectDigestInfo(var1, var3, var5, var6);
      Holder var8 = new Holder(var7);
      this.holder = var8;
   }

   public AttributeCertificateHolder(X509Certificate var1) throws CertificateParsingException {
      X509Principal var2;
      try {
         var2 = PrincipalUtil.getIssuerX509Principal(var1);
      } catch (Exception var10) {
         String var9 = var10.getMessage();
         throw new CertificateParsingException(var9);
      }

      GeneralNames var4 = this.generateGeneralNames(var2);
      BigInteger var5 = var1.getSerialNumber();
      DERInteger var6 = new DERInteger(var5);
      IssuerSerial var7 = new IssuerSerial(var4, var6);
      Holder var8 = new Holder(var7);
      this.holder = var8;
   }

   public AttributeCertificateHolder(X500Principal var1) {
      X509Principal var2 = X509Util.convertPrincipal(var1);
      this(var2);
   }

   public AttributeCertificateHolder(X500Principal var1, BigInteger var2) {
      X509Principal var3 = X509Util.convertPrincipal(var1);
      this(var3, var2);
   }

   AttributeCertificateHolder(ASN1Sequence var1) {
      Holder var2 = Holder.getInstance(var1);
      this.holder = var2;
   }

   public AttributeCertificateHolder(X509Principal var1) {
      GeneralNames var2 = this.generateGeneralNames(var1);
      Holder var3 = new Holder(var2);
      this.holder = var3;
   }

   public AttributeCertificateHolder(X509Principal var1, BigInteger var2) {
      GeneralName var3 = new GeneralName(var1);
      DERSequence var4 = new DERSequence(var3);
      GeneralNames var5 = new GeneralNames(var4);
      DERInteger var6 = new DERInteger(var2);
      IssuerSerial var7 = new IssuerSerial(var5, var6);
      Holder var8 = new Holder(var7);
      this.holder = var8;
   }

   private GeneralNames generateGeneralNames(X509Principal var1) {
      GeneralName var2 = new GeneralName(var1);
      DERSequence var3 = new DERSequence(var2);
      return new GeneralNames(var3);
   }

   private Object[] getNames(GeneralName[] var1) {
      int var2 = var1.length;
      ArrayList var3 = new ArrayList(var2);
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 == var5) {
            Object[] var10 = new Object[var3.size()];
            return var3.toArray(var10);
         }

         if(var1[var4].getTagNo() == 4) {
            try {
               byte[] var6 = ((ASN1Encodable)var1[var4].getName()).getEncoded();
               X500Principal var7 = new X500Principal(var6);
               var3.add(var7);
            } catch (IOException var11) {
               throw new RuntimeException("badly formed Name object");
            }
         }

         ++var4;
      }
   }

   private Principal[] getPrincipals(GeneralNames var1) {
      GeneralName[] var2 = var1.getNames();
      Object[] var3 = this.getNames(var2);
      ArrayList var4 = new ArrayList();
      int var5 = 0;

      while(true) {
         int var6 = var3.length;
         if(var5 == var6) {
            Principal[] var9 = new Principal[var4.size()];
            return (Principal[])((Principal[])var4.toArray(var9));
         }

         if(var3[var5] instanceof Principal) {
            Object var7 = var3[var5];
            var4.add(var7);
         }

         ++var5;
      }
   }

   private boolean matchesDN(X509Principal var1, GeneralNames var2) {
      GeneralName[] var3 = var2.getNames();
      int var4 = 0;

      boolean var9;
      while(true) {
         int var5 = var3.length;
         if(var4 == var5) {
            var9 = false;
            break;
         }

         GeneralName var6 = var3[var4];
         if(var6.getTagNo() == 4) {
            label21: {
               boolean var8;
               try {
                  byte[] var7 = ((ASN1Encodable)var6.getName()).getEncoded();
                  var8 = (new X509Principal(var7)).equals(var1);
               } catch (IOException var11) {
                  break label21;
               }

               if(var8) {
                  var9 = true;
                  break;
               }
            }
         }

         ++var4;
      }

      return var9;
   }

   public Object clone() {
      ASN1Sequence var1 = (ASN1Sequence)this.holder.toASN1Object();
      return new AttributeCertificateHolder(var1);
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(!(var1 instanceof AttributeCertificateHolder)) {
         var2 = 0;
      } else {
         AttributeCertificateHolder var3 = (AttributeCertificateHolder)var1;
         Holder var4 = this.holder;
         Holder var5 = var3.holder;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public String getDigestAlgorithm() {
      if(this.holder.getObjectDigestInfo() != null) {
         String var1 = this.holder.getObjectDigestInfo().getDigestAlgorithm().getObjectId().getId();
      }

      return null;
   }

   public int getDigestedObjectType() {
      int var1;
      if(this.holder.getObjectDigestInfo() != null) {
         var1 = this.holder.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
      } else {
         var1 = -1;
      }

      return var1;
   }

   public Principal[] getEntityNames() {
      Principal[] var2;
      if(this.holder.getEntityName() != null) {
         GeneralNames var1 = this.holder.getEntityName();
         var2 = this.getPrincipals(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public Principal[] getIssuer() {
      Principal[] var2;
      if(this.holder.getBaseCertificateID() != null) {
         GeneralNames var1 = this.holder.getBaseCertificateID().getIssuer();
         var2 = this.getPrincipals(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public byte[] getObjectDigest() {
      if(this.holder.getObjectDigestInfo() != null) {
         byte[] var1 = this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
      }

      return null;
   }

   public String getOtherObjectTypeID() {
      if(this.holder.getObjectDigestInfo() != null) {
         String var1 = this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId();
      }

      return null;
   }

   public BigInteger getSerialNumber() {
      BigInteger var1;
      if(this.holder.getBaseCertificateID() != null) {
         var1 = this.holder.getBaseCertificateID().getSerial().getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   public int hashCode() {
      return this.holder.hashCode();
   }

   public boolean match(Object var1) {
      byte var2;
      if(!(var1 instanceof X509Certificate)) {
         var2 = 0;
      } else {
         Certificate var3 = (Certificate)var1;
         var2 = this.match(var3);
      }

      return (boolean)var2;
   }

   public boolean match(Certificate param1) {
      // $FF: Couldn't be decompiled
   }
}
