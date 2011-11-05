package org.apache.james.mime4j.field.address.parser;

import org.apache.james.mime4j.field.address.parser.AddressListParserVisitor;

public interface Node {

   Object jjtAccept(AddressListParserVisitor var1, Object var2);

   void jjtAddChild(Node var1, int var2);

   void jjtClose();

   Node jjtGetChild(int var1);

   int jjtGetNumChildren();

   Node jjtGetParent();

   void jjtOpen();

   void jjtSetParent(Node var1);
}
