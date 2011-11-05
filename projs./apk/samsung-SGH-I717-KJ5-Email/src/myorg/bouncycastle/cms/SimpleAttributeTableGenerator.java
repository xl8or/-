package myorg.bouncycastle.cms;

import java.util.Map;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.cms.CMSAttributeTableGenerator;

public class SimpleAttributeTableGenerator implements CMSAttributeTableGenerator {

   private final AttributeTable attributes;


   public SimpleAttributeTableGenerator(AttributeTable var1) {
      this.attributes = var1;
   }

   public AttributeTable getAttributes(Map var1) {
      return this.attributes;
   }
}
