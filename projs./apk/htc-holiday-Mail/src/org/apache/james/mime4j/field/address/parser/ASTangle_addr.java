package org.apache.james.mime4j.field.address.parser;

import org.apache.james.mime4j.field.address.parser.AddressListParser;
import org.apache.james.mime4j.field.address.parser.AddressListParserVisitor;
import org.apache.james.mime4j.field.address.parser.SimpleNode;

public class ASTangle_addr extends SimpleNode {

   public ASTangle_addr(int var1) {
      super(var1);
   }

   public ASTangle_addr(AddressListParser var1, int var2) {
      super(var1, var2);
   }

   public Object jjtAccept(AddressListParserVisitor var1, Object var2) {
      return var1.visit(this, var2);
   }
}
