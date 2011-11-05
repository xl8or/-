package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.KeyPurposeId;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class ExtendedKeyUsage extends ASN1Encodable {

   ASN1Sequence seq;
   Hashtable usageTable;


   public ExtendedKeyUsage(Vector var1) {
      Hashtable var2 = new Hashtable();
      this.usageTable = var2;
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      Enumeration var4 = var1.elements();

      while(var4.hasMoreElements()) {
         DERObject var5 = (DERObject)var4.nextElement();
         var3.add(var5);
         this.usageTable.put(var5, var5);
      }

      DERSequence var7 = new DERSequence(var3);
      this.seq = var7;
   }

   public ExtendedKeyUsage(ASN1Sequence var1) {
      Hashtable var2 = new Hashtable();
      this.usageTable = var2;
      this.seq = var1;
      Enumeration var3 = var1.getObjects();

      while(var3.hasMoreElements()) {
         Object var4 = var3.nextElement();
         if(!(var4 instanceof DERObjectIdentifier)) {
            throw new IllegalArgumentException("Only DERObjectIdentifiers allowed in ExtendedKeyUsage.");
         }

         this.usageTable.put(var4, var4);
      }

   }

   public ExtendedKeyUsage(KeyPurposeId var1) {
      Hashtable var2 = new Hashtable();
      this.usageTable = var2;
      DERSequence var3 = new DERSequence(var1);
      this.seq = var3;
      this.usageTable.put(var1, var1);
   }

   public static ExtendedKeyUsage getInstance(Object var0) {
      ExtendedKeyUsage var1;
      if(var0 instanceof ExtendedKeyUsage) {
         var1 = (ExtendedKeyUsage)var0;
      } else if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ExtendedKeyUsage(var2);
      } else {
         if(!(var0 instanceof X509Extension)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid ExtendedKeyUsage: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
      }

      return var1;
   }

   public static ExtendedKeyUsage getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public Vector getUsages() {
      Vector var1 = new Vector();
      Enumeration var2 = this.usageTable.elements();

      while(var2.hasMoreElements()) {
         Object var3 = var2.nextElement();
         var1.addElement(var3);
      }

      return var1;
   }

   public boolean hasKeyPurposeId(KeyPurposeId var1) {
      boolean var2;
      if(this.usageTable.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int size() {
      return this.usageTable.size();
   }

   public DERObject toASN1Object() {
      return this.seq;
   }
}
