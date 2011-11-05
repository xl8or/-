package org.apache.james.mime4j.field.datetime.parser;


public class Token {

   public int beginColumn;
   public int beginLine;
   public int endColumn;
   public int endLine;
   public String image;
   public int kind;
   public Token next;
   public Token specialToken;


   public Token() {}

   public static final Token newToken(int var0) {
      return new Token();
   }

   public String toString() {
      return this.image;
   }
}
