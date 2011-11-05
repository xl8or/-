package myorg.bouncycastle.cms;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.asn1.cms.CMSAttributes;
import myorg.bouncycastle.asn1.cms.Time;
import myorg.bouncycastle.cms.CMSAttributeTableGenerator;

public class DefaultSignedAttributeTableGenerator implements CMSAttributeTableGenerator {

   private final Hashtable table;


   public DefaultSignedAttributeTableGenerator() {
      Hashtable var1 = new Hashtable();
      this.table = var1;
   }

   public DefaultSignedAttributeTableGenerator(AttributeTable var1) {
      if(var1 != null) {
         Hashtable var2 = var1.toHashtable();
         this.table = var2;
      } else {
         Hashtable var3 = new Hashtable();
         this.table = var3;
      }
   }

   protected Hashtable createStandardAttributeTable(Map var1) {
      Hashtable var2 = (Hashtable)this.table.clone();
      DERObjectIdentifier var3 = CMSAttributes.contentType;
      if(!var2.containsKey(var3)) {
         DERObjectIdentifier var4 = (DERObjectIdentifier)var1.get("contentType");
         DERObjectIdentifier var5 = CMSAttributes.contentType;
         DERSet var6 = new DERSet(var4);
         Attribute var7 = new Attribute(var5, var6);
         DERObjectIdentifier var8 = var7.getAttrType();
         var2.put(var8, var7);
      }

      DERObjectIdentifier var10 = CMSAttributes.signingTime;
      if(!var2.containsKey(var10)) {
         Date var11 = new Date();
         DERObjectIdentifier var12 = CMSAttributes.signingTime;
         Time var13 = new Time(var11);
         DERSet var14 = new DERSet(var13);
         Attribute var15 = new Attribute(var12, var14);
         DERObjectIdentifier var16 = var15.getAttrType();
         var2.put(var16, var15);
      }

      DERObjectIdentifier var18 = CMSAttributes.messageDigest;
      if(!var2.containsKey(var18)) {
         byte[] var19 = (byte[])((byte[])var1.get("digest"));
         DERObjectIdentifier var20 = CMSAttributes.messageDigest;
         DEROctetString var21 = new DEROctetString(var19);
         DERSet var22 = new DERSet(var21);
         Attribute var23 = new Attribute(var20, var22);
         DERObjectIdentifier var24 = var23.getAttrType();
         var2.put(var24, var23);
      }

      return var2;
   }

   public AttributeTable getAttributes(Map var1) {
      Hashtable var2 = this.createStandardAttributeTable(var1);
      return new AttributeTable(var2);
   }
}
