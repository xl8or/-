package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;

public class TBSCertList extends ASN1Encodable {

   X509Extensions crlExtensions;
   X509Name issuer;
   Time nextUpdate;
   ASN1Sequence revokedCertificates;
   ASN1Sequence seq;
   AlgorithmIdentifier signature;
   Time thisUpdate;
   DERInteger version;


   public TBSCertList(ASN1Sequence var1) {
      if(var1.size() >= 3 && var1.size() <= 7) {
         int var5 = 0;
         this.seq = var1;
         if(var1.getObjectAt(var5) instanceof DERInteger) {
            int var6 = var5 + 1;
            DERInteger var7 = DERInteger.getInstance(var1.getObjectAt(var5));
            this.version = var7;
            var5 = var6;
         } else {
            DERInteger var22 = new DERInteger(0);
            this.version = var22;
         }

         int var8 = var5 + 1;
         AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var5));
         this.signature = var9;
         int var10 = var8 + 1;
         X509Name var11 = X509Name.getInstance(var1.getObjectAt(var8));
         this.issuer = var11;
         int var12 = var10 + 1;
         Time var13 = Time.getInstance(var1.getObjectAt(var10));
         this.thisUpdate = var13;
         int var14 = var1.size();
         int var15;
         if(var12 < var14 && (var1.getObjectAt(var12) instanceof DERUTCTime || var1.getObjectAt(var12) instanceof DERGeneralizedTime || var1.getObjectAt(var12) instanceof Time)) {
            var15 = var12 + 1;
            Time var16 = Time.getInstance(var1.getObjectAt(var12));
            this.nextUpdate = var16;
         } else {
            var15 = var12;
         }

         int var17 = var1.size();
         if(var15 < var17 && !(var1.getObjectAt(var15) instanceof DERTaggedObject)) {
            int var18 = var15 + 1;
            ASN1Sequence var19 = ASN1Sequence.getInstance(var1.getObjectAt(var15));
            this.revokedCertificates = var19;
            var15 = var18;
         }

         int var20 = var1.size();
         if(var15 < var20) {
            if(var1.getObjectAt(var15) instanceof DERTaggedObject) {
               X509Extensions var21 = X509Extensions.getInstance(var1.getObjectAt(var15));
               this.crlExtensions = var21;
            }
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public static TBSCertList getInstance(Object var0) {
      TBSCertList var1;
      if(var0 instanceof TBSCertList) {
         var1 = (TBSCertList)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new TBSCertList(var2);
      }

      return var1;
   }

   public static TBSCertList getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public X509Extensions getExtensions() {
      return this.crlExtensions;
   }

   public X509Name getIssuer() {
      return this.issuer;
   }

   public Time getNextUpdate() {
      return this.nextUpdate;
   }

   public Enumeration getRevokedCertificateEnumeration() {
      Object var1;
      if(this.revokedCertificates == null) {
         var1 = new TBSCertList.EmptyEnumeration((TBSCertList.1)null);
      } else {
         Enumeration var2 = this.revokedCertificates.getObjects();
         var1 = new TBSCertList.RevokedCertificatesEnumeration(var2);
      }

      return (Enumeration)var1;
   }

   public TBSCertList.CRLEntry[] getRevokedCertificates() {
      TBSCertList.CRLEntry[] var1;
      if(this.revokedCertificates == null) {
         var1 = new TBSCertList.CRLEntry[0];
      } else {
         TBSCertList.CRLEntry[] var2 = new TBSCertList.CRLEntry[this.revokedCertificates.size()];
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               var1 = var2;
               break;
            }

            ASN1Sequence var5 = ASN1Sequence.getInstance(this.revokedCertificates.getObjectAt(var3));
            TBSCertList.CRLEntry var6 = new TBSCertList.CRLEntry(var5);
            var2[var3] = var6;
            ++var3;
         }
      }

      return var1;
   }

   public AlgorithmIdentifier getSignature() {
      return this.signature;
   }

   public Time getThisUpdate() {
      return this.thisUpdate;
   }

   public int getVersion() {
      return this.version.getValue().intValue() + 1;
   }

   public DERInteger getVersionNumber() {
      return this.version;
   }

   public DERObject toASN1Object() {
      return this.seq;
   }

   private class RevokedCertificatesEnumeration implements Enumeration {

      private final Enumeration en;


      RevokedCertificatesEnumeration(Enumeration var2) {
         this.en = var2;
      }

      public boolean hasMoreElements() {
         return this.en.hasMoreElements();
      }

      public Object nextElement() {
         TBSCertList var1 = TBSCertList.this;
         ASN1Sequence var2 = ASN1Sequence.getInstance(this.en.nextElement());
         return var1.new CRLEntry(var2);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   public class CRLEntry extends ASN1Encodable {

      X509Extensions crlEntryExtensions;
      Time revocationDate;
      ASN1Sequence seq;
      DERInteger userCertificate;


      public CRLEntry(ASN1Sequence var2) {
         if(var2.size() >= 2 && var2.size() <= 3) {
            this.seq = var2;
            DERInteger var6 = DERInteger.getInstance(var2.getObjectAt(0));
            this.userCertificate = var6;
            Time var7 = Time.getInstance(var2.getObjectAt(1));
            this.revocationDate = var7;
         } else {
            StringBuilder var3 = (new StringBuilder()).append("Bad sequence size: ");
            int var4 = var2.size();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }
      }

      public X509Extensions getExtensions() {
         if(this.crlEntryExtensions == null && this.seq.size() == 3) {
            X509Extensions var1 = X509Extensions.getInstance(this.seq.getObjectAt(2));
            this.crlEntryExtensions = var1;
         }

         return this.crlEntryExtensions;
      }

      public Time getRevocationDate() {
         return this.revocationDate;
      }

      public DERInteger getUserCertificate() {
         return this.userCertificate;
      }

      public DERObject toASN1Object() {
         return this.seq;
      }
   }

   private class EmptyEnumeration implements Enumeration {

      private EmptyEnumeration() {}

      // $FF: synthetic method
      EmptyEnumeration(TBSCertList.1 var2) {
         this();
      }

      public boolean hasMoreElements() {
         return false;
      }

      public Object nextElement() {
         return null;
      }
   }
}
