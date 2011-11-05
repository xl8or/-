package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class CertificatePolicies extends ASN1Encodable {

   static final DERObjectIdentifier anyPolicy = new DERObjectIdentifier("2.5.29.32.0");
   Vector policies;


   public CertificatePolicies(String var1) {
      DERObjectIdentifier var2 = new DERObjectIdentifier(var1);
      this(var2);
   }

   public CertificatePolicies(ASN1Sequence var1) {
      Vector var2 = new Vector();
      this.policies = var2;
      Enumeration var3 = var1.getObjects();

      while(var3.hasMoreElements()) {
         ASN1Sequence var4 = ASN1Sequence.getInstance(var3.nextElement());
         Vector var5 = this.policies;
         DEREncodable var6 = var4.getObjectAt(0);
         var5.addElement(var6);
      }

   }

   public CertificatePolicies(DERObjectIdentifier var1) {
      Vector var2 = new Vector();
      this.policies = var2;
      this.policies.addElement(var1);
   }

   public static CertificatePolicies getInstance(Object var0) {
      CertificatePolicies var1;
      if(var0 instanceof CertificatePolicies) {
         var1 = (CertificatePolicies)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertificatePolicies(var2);
      }

      return var1;
   }

   public static CertificatePolicies getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public void addPolicy(String var1) {
      Vector var2 = this.policies;
      DERObjectIdentifier var3 = new DERObjectIdentifier(var1);
      var2.addElement(var3);
   }

   public String getPolicy(int var1) {
      String var2;
      if(this.policies.size() > var1) {
         var2 = ((DERObjectIdentifier)this.policies.elementAt(var1)).getId();
      } else {
         var2 = null;
      }

      return var2;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      int var2 = 0;

      while(true) {
         int var3 = this.policies.size();
         if(var2 >= var3) {
            return new DERSequence(var1);
         }

         DERObjectIdentifier var4 = (DERObjectIdentifier)this.policies.elementAt(var2);
         DERSequence var5 = new DERSequence(var4);
         var1.add(var5);
         ++var2;
      }
   }

   public String toString() {
      String var1 = null;
      int var2 = 0;

      while(true) {
         int var3 = this.policies.size();
         if(var2 >= var3) {
            return "CertificatePolicies: " + var1;
         }

         if(var1 != null) {
            var1 = var1 + ", ";
         }

         StringBuilder var4 = (new StringBuilder()).append(var1);
         String var5 = ((DERObjectIdentifier)this.policies.elementAt(var2)).getId();
         var1 = var4.append(var5).toString();
         ++var2;
      }
   }
}
