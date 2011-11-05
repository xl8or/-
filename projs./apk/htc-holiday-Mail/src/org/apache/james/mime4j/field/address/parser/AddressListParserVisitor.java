package org.apache.james.mime4j.field.address.parser;

import org.apache.james.mime4j.field.address.parser.ASTaddr_spec;
import org.apache.james.mime4j.field.address.parser.ASTaddress;
import org.apache.james.mime4j.field.address.parser.ASTaddress_list;
import org.apache.james.mime4j.field.address.parser.ASTangle_addr;
import org.apache.james.mime4j.field.address.parser.ASTdomain;
import org.apache.james.mime4j.field.address.parser.ASTgroup_body;
import org.apache.james.mime4j.field.address.parser.ASTlocal_part;
import org.apache.james.mime4j.field.address.parser.ASTmailbox;
import org.apache.james.mime4j.field.address.parser.ASTname_addr;
import org.apache.james.mime4j.field.address.parser.ASTphrase;
import org.apache.james.mime4j.field.address.parser.ASTroute;
import org.apache.james.mime4j.field.address.parser.SimpleNode;

public interface AddressListParserVisitor {

   Object visit(ASTaddr_spec var1, Object var2);

   Object visit(ASTaddress var1, Object var2);

   Object visit(ASTaddress_list var1, Object var2);

   Object visit(ASTangle_addr var1, Object var2);

   Object visit(ASTdomain var1, Object var2);

   Object visit(ASTgroup_body var1, Object var2);

   Object visit(ASTlocal_part var1, Object var2);

   Object visit(ASTmailbox var1, Object var2);

   Object visit(ASTname_addr var1, Object var2);

   Object visit(ASTphrase var1, Object var2);

   Object visit(ASTroute var1, Object var2);

   Object visit(SimpleNode var1, Object var2);
}
