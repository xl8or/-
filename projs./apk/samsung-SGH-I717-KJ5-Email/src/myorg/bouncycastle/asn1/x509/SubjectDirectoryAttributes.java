package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.Attribute;

public class SubjectDirectoryAttributes extends ASN1Encodable {

   private Vector attributes;


   public SubjectDirectoryAttributes(Vector var1) {
      Vector var2 = new Vector();
      this.attributes = var2;
      Enumeration var3 = var1.elements();

      while(var3.hasMoreElements()) {
         Vector var4 = this.attributes;
         Object var5 = var3.nextElement();
         var4.addElement(var5);
      }

   }

   public SubjectDirectoryAttributes(ASN1Sequence var1) {
      Vector var2 = new Vector();
      this.attributes = var2;
      Enumeration var3 = var1.getObjects();

      while(var3.hasMoreElements()) {
         ASN1Sequence var4 = ASN1Sequence.getInstance(var3.nextElement());
         Vector var5 = this.attributes;
         Attribute var6 = new Attribute(var4);
         var5.addElement(var6);
      }

   }

   public static SubjectDirectoryAttributes getInstance(Object var0) {
      SubjectDirectoryAttributes var1;
      if(var0 != null && !(var0 instanceof SubjectDirectoryAttributes)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SubjectDirectoryAttributes(var2);
      } else {
         var1 = (SubjectDirectoryAttributes)var0;
      }

      return var1;
   }

   public Vector getAttributes() {
      return this.attributes;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Enumeration var2 = this.attributes.elements();

      while(var2.hasMoreElements()) {
         Attribute var3 = (Attribute)var2.nextElement();
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
