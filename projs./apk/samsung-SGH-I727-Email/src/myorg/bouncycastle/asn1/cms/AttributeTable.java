package myorg.bouncycastle.asn1.cms;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.Attribute;

public class AttributeTable {

   private Hashtable attributes;


   public AttributeTable(Hashtable var1) {
      Hashtable var2 = new Hashtable();
      this.attributes = var2;
      Hashtable var3 = this.copyTable(var1);
      this.attributes = var3;
   }

   public AttributeTable(ASN1Set var1) {
      Hashtable var2 = new Hashtable();
      this.attributes = var2;
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            return;
         }

         Attribute var5 = Attribute.getInstance(var1.getObjectAt(var3));
         DERObjectIdentifier var6 = var5.getAttrType();
         this.addAttribute(var6, var5);
         ++var3;
      }
   }

   public AttributeTable(DEREncodableVector var1) {
      Hashtable var2 = new Hashtable();
      this.attributes = var2;
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 == var4) {
            return;
         }

         Attribute var5 = Attribute.getInstance(var1.get(var3));
         DERObjectIdentifier var6 = var5.getAttrType();
         this.addAttribute(var6, var5);
         ++var3;
      }
   }

   private void addAttribute(DERObjectIdentifier var1, Attribute var2) {
      Object var3 = this.attributes.get(var1);
      if(var3 == null) {
         this.attributes.put(var1, var2);
      } else {
         Vector var5;
         if(var3 instanceof Attribute) {
            var5 = new Vector();
            var5.addElement(var3);
            var5.addElement(var2);
         } else {
            var5 = (Vector)var3;
            var5.addElement(var2);
         }

         this.attributes.put(var1, var5);
      }
   }

   private Hashtable copyTable(Hashtable var1) {
      Hashtable var2 = new Hashtable();
      Enumeration var3 = var1.keys();

      while(var3.hasMoreElements()) {
         Object var4 = var3.nextElement();
         Object var5 = var1.get(var4);
         var2.put(var4, var5);
      }

      return var2;
   }

   public Attribute get(DERObjectIdentifier var1) {
      Object var2 = this.attributes.get(var1);
      Attribute var3;
      if(var2 instanceof Vector) {
         var3 = (Attribute)((Vector)var2).elementAt(0);
      } else {
         var3 = (Attribute)var2;
      }

      return var3;
   }

   public ASN1EncodableVector getAll(DERObjectIdentifier var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      Object var3 = this.attributes.get(var1);
      if(var3 instanceof Vector) {
         Enumeration var4 = ((Vector)var3).elements();

         while(var4.hasMoreElements()) {
            Attribute var5 = (Attribute)var4.nextElement();
            var2.add(var5);
         }
      } else if(var3 != null) {
         Attribute var6 = (Attribute)var3;
         var2.add(var6);
      }

      return var2;
   }

   public ASN1EncodableVector toASN1EncodableVector() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Enumeration var2 = this.attributes.elements();

      while(var2.hasMoreElements()) {
         Object var3 = var2.nextElement();
         if(var3 instanceof Vector) {
            Enumeration var4 = ((Vector)var3).elements();

            while(var4.hasMoreElements()) {
               Attribute var5 = Attribute.getInstance(var4.nextElement());
               var1.add(var5);
            }
         } else {
            Attribute var6 = Attribute.getInstance(var3);
            var1.add(var6);
         }
      }

      return var1;
   }

   public Hashtable toHashtable() {
      Hashtable var1 = this.attributes;
      return this.copyTable(var1);
   }
}
