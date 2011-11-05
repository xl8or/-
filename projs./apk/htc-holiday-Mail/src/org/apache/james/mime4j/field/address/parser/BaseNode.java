package org.apache.james.mime4j.field.address.parser;

import org.apache.james.mime4j.field.address.parser.Node;
import org.apache.james.mime4j.field.address.parser.Token;

public abstract class BaseNode implements Node {

   public Token firstToken;
   public Token lastToken;


   public BaseNode() {}
}
