package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.CRLReason;
import myorg.bouncycastle.asn1.x509.TBSCertList;
import myorg.bouncycastle.asn1.x509.Time;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;

public class V2TBSCertListGenerator {

   private Vector crlentries;
   X509Extensions extensions;
   X509Name issuer;
   Time nextUpdate;
   AlgorithmIdentifier signature;
   Time thisUpdate;
   DERInteger version;


   public V2TBSCertListGenerator() {
      DERInteger var1 = new DERInteger(1);
      this.version = var1;
      this.nextUpdate = null;
      this.extensions = null;
      this.crlentries = null;
   }

   public void addCRLEntry(ASN1Sequence var1) {
      if(this.crlentries == null) {
         Vector var2 = new Vector();
         this.crlentries = var2;
      }

      this.crlentries.addElement(var1);
   }

   public void addCRLEntry(DERInteger var1, DERUTCTime var2, int var3) {
      Time var4 = new Time(var2);
      this.addCRLEntry(var1, var4, var3);
   }

   public void addCRLEntry(DERInteger var1, Time var2, int var3) {
      this.addCRLEntry(var1, var2, var3, (DERGeneralizedTime)null);
   }

   public void addCRLEntry(DERInteger var1, Time var2, int var3, DERGeneralizedTime var4) {
      Vector var5 = new Vector();
      Vector var6 = new Vector();
      if(var3 != 0) {
         CRLReason var7 = new CRLReason(var3);

         try {
            DERObjectIdentifier var8 = X509Extensions.ReasonCode;
            var5.addElement(var8);
            byte[] var9 = var7.getEncoded();
            DEROctetString var10 = new DEROctetString(var9);
            X509Extension var11 = new X509Extension((boolean)0, var10);
            var6.addElement(var11);
         } catch (IOException var22) {
            String var18 = "error encoding reason: " + var22;
            throw new IllegalArgumentException(var18);
         }
      }

      if(var4 != null) {
         try {
            DERObjectIdentifier var12 = X509Extensions.InvalidityDate;
            var5.addElement(var12);
            byte[] var13 = var4.getEncoded();
            DEROctetString var14 = new DEROctetString(var13);
            X509Extension var15 = new X509Extension((boolean)0, var14);
            var6.addElement(var15);
         } catch (IOException var21) {
            String var20 = "error encoding invalidityDate: " + var21;
            throw new IllegalArgumentException(var20);
         }
      }

      if(var5.size() != 0) {
         X509Extensions var16 = new X509Extensions(var5, var6);
         this.addCRLEntry(var1, var2, var16);
      } else {
         this.addCRLEntry(var1, var2, (X509Extensions)null);
      }
   }

   public void addCRLEntry(DERInteger var1, Time var2, X509Extensions var3) {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var1);
      var4.add(var2);
      if(var3 != null) {
         var4.add(var3);
      }

      DERSequence var5 = new DERSequence(var4);
      this.addCRLEntry(var5);
   }

   public TBSCertList generateTBSCertList() {
      if(this.signature != null && this.issuer != null && this.thisUpdate != null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         DERInteger var2 = this.version;
         var1.add(var2);
         AlgorithmIdentifier var3 = this.signature;
         var1.add(var3);
         X509Name var4 = this.issuer;
         var1.add(var4);
         Time var5 = this.thisUpdate;
         var1.add(var5);
         if(this.nextUpdate != null) {
            Time var6 = this.nextUpdate;
            var1.add(var6);
         }

         if(this.crlentries != null) {
            ASN1EncodableVector var7 = new ASN1EncodableVector();
            Enumeration var8 = this.crlentries.elements();

            while(var8.hasMoreElements()) {
               ASN1Sequence var9 = (ASN1Sequence)var8.nextElement();
               var7.add(var9);
            }

            DERSequence var10 = new DERSequence(var7);
            var1.add(var10);
         }

         if(this.extensions != null) {
            X509Extensions var11 = this.extensions;
            DERTaggedObject var12 = new DERTaggedObject(0, var11);
            var1.add(var12);
         }

         DERSequence var13 = new DERSequence(var1);
         return new TBSCertList(var13);
      } else {
         throw new IllegalStateException("Not all mandatory fields set in V2 TBSCertList generator.");
      }
   }

   public void setExtensions(X509Extensions var1) {
      this.extensions = var1;
   }

   public void setIssuer(X509Name var1) {
      this.issuer = var1;
   }

   public void setNextUpdate(DERUTCTime var1) {
      Time var2 = new Time(var1);
      this.nextUpdate = var2;
   }

   public void setNextUpdate(Time var1) {
      this.nextUpdate = var1;
   }

   public void setSignature(AlgorithmIdentifier var1) {
      this.signature = var1;
   }

   public void setThisUpdate(DERUTCTime var1) {
      Time var2 = new Time(var1);
      this.thisUpdate = var2;
   }

   public void setThisUpdate(Time var1) {
      this.thisUpdate = var1;
   }
}
