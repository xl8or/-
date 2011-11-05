package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class X509Extensions extends ASN1Encodable {

   public static final DERObjectIdentifier AuditIdentity = new DERObjectIdentifier("1.3.6.1.5.5.7.1.4");
   public static final DERObjectIdentifier AuthorityInfoAccess = new DERObjectIdentifier("1.3.6.1.5.5.7.1.1");
   public static final DERObjectIdentifier AuthorityKeyIdentifier = new DERObjectIdentifier("2.5.29.35");
   public static final DERObjectIdentifier BasicConstraints = new DERObjectIdentifier("2.5.29.19");
   public static final DERObjectIdentifier BiometricInfo = new DERObjectIdentifier("1.3.6.1.5.5.7.1.2");
   public static final DERObjectIdentifier CRLDistributionPoints = new DERObjectIdentifier("2.5.29.31");
   public static final DERObjectIdentifier CRLNumber = new DERObjectIdentifier("2.5.29.20");
   public static final DERObjectIdentifier CertificateIssuer = new DERObjectIdentifier("2.5.29.29");
   public static final DERObjectIdentifier CertificatePolicies = new DERObjectIdentifier("2.5.29.32");
   public static final DERObjectIdentifier DeltaCRLIndicator = new DERObjectIdentifier("2.5.29.27");
   public static final DERObjectIdentifier ExtendedKeyUsage = new DERObjectIdentifier("2.5.29.37");
   public static final DERObjectIdentifier FreshestCRL = new DERObjectIdentifier("2.5.29.46");
   public static final DERObjectIdentifier InhibitAnyPolicy = new DERObjectIdentifier("2.5.29.54");
   public static final DERObjectIdentifier InstructionCode = new DERObjectIdentifier("2.5.29.23");
   public static final DERObjectIdentifier InvalidityDate = new DERObjectIdentifier("2.5.29.24");
   public static final DERObjectIdentifier IssuerAlternativeName = new DERObjectIdentifier("2.5.29.18");
   public static final DERObjectIdentifier IssuingDistributionPoint = new DERObjectIdentifier("2.5.29.28");
   public static final DERObjectIdentifier KeyUsage = new DERObjectIdentifier("2.5.29.15");
   public static final DERObjectIdentifier LogoType = new DERObjectIdentifier("1.3.6.1.5.5.7.1.12");
   public static final DERObjectIdentifier NameConstraints = new DERObjectIdentifier("2.5.29.30");
   public static final DERObjectIdentifier NoRevAvail = new DERObjectIdentifier("2.5.29.56");
   public static final DERObjectIdentifier PolicyConstraints = new DERObjectIdentifier("2.5.29.36");
   public static final DERObjectIdentifier PolicyMappings = new DERObjectIdentifier("2.5.29.33");
   public static final DERObjectIdentifier PrivateKeyUsagePeriod = new DERObjectIdentifier("2.5.29.16");
   public static final DERObjectIdentifier QCStatements = new DERObjectIdentifier("1.3.6.1.5.5.7.1.3");
   public static final DERObjectIdentifier ReasonCode = new DERObjectIdentifier("2.5.29.21");
   public static final DERObjectIdentifier SubjectAlternativeName = new DERObjectIdentifier("2.5.29.17");
   public static final DERObjectIdentifier SubjectDirectoryAttributes = new DERObjectIdentifier("2.5.29.9");
   public static final DERObjectIdentifier SubjectInfoAccess = new DERObjectIdentifier("1.3.6.1.5.5.7.1.11");
   public static final DERObjectIdentifier SubjectKeyIdentifier = new DERObjectIdentifier("2.5.29.14");
   public static final DERObjectIdentifier TargetInformation = new DERObjectIdentifier("2.5.29.55");
   private Hashtable extensions;
   private Vector ordering;


   public X509Extensions(Hashtable var1) {
      this((Vector)null, var1);
   }

   public X509Extensions(Vector var1, Hashtable var2) {
      Hashtable var3 = new Hashtable();
      this.extensions = var3;
      Vector var4 = new Vector();
      this.ordering = var4;
      Enumeration var5;
      if(var1 == null) {
         var5 = var2.keys();
      } else {
         var5 = var1.elements();
      }

      while(var5.hasMoreElements()) {
         Vector var6 = this.ordering;
         Object var7 = var5.nextElement();
         var6.addElement(var7);
      }

      Enumeration var8 = this.ordering.elements();

      while(var8.hasMoreElements()) {
         DERObjectIdentifier var9 = (DERObjectIdentifier)var8.nextElement();
         X509Extension var10 = (X509Extension)var2.get(var9);
         this.extensions.put(var9, var10);
      }

   }

   public X509Extensions(Vector var1, Vector var2) {
      Hashtable var3 = new Hashtable();
      this.extensions = var3;
      Vector var4 = new Vector();
      this.ordering = var4;
      Enumeration var5 = var1.elements();

      while(var5.hasMoreElements()) {
         Vector var6 = this.ordering;
         Object var7 = var5.nextElement();
         var6.addElement(var7);
      }

      int var8 = 0;

      for(Enumeration var9 = this.ordering.elements(); var9.hasMoreElements(); ++var8) {
         DERObjectIdentifier var10 = (DERObjectIdentifier)var9.nextElement();
         X509Extension var11 = (X509Extension)var2.elementAt(var8);
         this.extensions.put(var10, var11);
      }

   }

   public X509Extensions(ASN1Sequence var1) {
      Hashtable var2 = new Hashtable();
      this.extensions = var2;
      Vector var3 = new Vector();
      this.ordering = var3;
      Enumeration var4 = var1.getObjects();

      while(var4.hasMoreElements()) {
         ASN1Sequence var5 = ASN1Sequence.getInstance(var4.nextElement());
         if(var5.size() == 3) {
            Hashtable var6 = this.extensions;
            DEREncodable var7 = var5.getObjectAt(0);
            DERBoolean var8 = DERBoolean.getInstance(var5.getObjectAt(1));
            ASN1OctetString var9 = ASN1OctetString.getInstance(var5.getObjectAt(2));
            X509Extension var10 = new X509Extension(var8, var9);
            var6.put(var7, var10);
         } else {
            if(var5.size() != 2) {
               StringBuilder var19 = (new StringBuilder()).append("Bad sequence size: ");
               int var20 = var5.size();
               String var21 = var19.append(var20).toString();
               throw new IllegalArgumentException(var21);
            }

            Hashtable var14 = this.extensions;
            DEREncodable var15 = var5.getObjectAt(0);
            ASN1OctetString var16 = ASN1OctetString.getInstance(var5.getObjectAt(1));
            X509Extension var17 = new X509Extension((boolean)0, var16);
            var14.put(var15, var17);
         }

         Vector var12 = this.ordering;
         DEREncodable var13 = var5.getObjectAt(0);
         var12.addElement(var13);
      }

   }

   public static X509Extensions getInstance(Object var0) {
      X509Extensions var1;
      if(var0 != null && !(var0 instanceof X509Extensions)) {
         if(var0 instanceof ASN1Sequence) {
            ASN1Sequence var2 = (ASN1Sequence)var0;
            var1 = new X509Extensions(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).toString();
               throw new IllegalArgumentException(var5);
            }

            var1 = getInstance(((ASN1TaggedObject)var0).getObject());
         }
      } else {
         var1 = (X509Extensions)var0;
      }

      return var1;
   }

   public static X509Extensions getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public boolean equivalent(X509Extensions var1) {
      int var2 = this.extensions.size();
      int var3 = var1.extensions.size();
      boolean var4;
      if(var2 != var3) {
         var4 = false;
      } else {
         Enumeration var5 = this.extensions.keys();

         while(true) {
            if(var5.hasMoreElements()) {
               Object var6 = var5.nextElement();
               Object var7 = this.extensions.get(var6);
               Object var8 = var1.extensions.get(var6);
               if(var7.equals(var8)) {
                  continue;
               }

               var4 = false;
               break;
            }

            var4 = true;
            break;
         }
      }

      return var4;
   }

   public X509Extension getExtension(DERObjectIdentifier var1) {
      return (X509Extension)this.extensions.get(var1);
   }

   public Enumeration oids() {
      return this.ordering.elements();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Enumeration var2 = this.ordering.elements();

      while(var2.hasMoreElements()) {
         DERObjectIdentifier var3 = (DERObjectIdentifier)var2.nextElement();
         X509Extension var4 = (X509Extension)this.extensions.get(var3);
         ASN1EncodableVector var5 = new ASN1EncodableVector();
         var5.add(var3);
         if(var4.isCritical()) {
            DERBoolean var6 = new DERBoolean((boolean)1);
            var5.add(var6);
         }

         ASN1OctetString var7 = var4.getValue();
         var5.add(var7);
         DERSequence var8 = new DERSequence(var5);
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}
