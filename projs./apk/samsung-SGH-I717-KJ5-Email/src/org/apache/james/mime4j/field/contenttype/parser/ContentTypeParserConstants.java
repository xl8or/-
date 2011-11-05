package org.apache.james.mime4j.field.contenttype.parser;


public interface ContentTypeParserConstants {

   int ANY = 23;
   int ATOKEN = 21;
   int COMMENT = 8;
   int DEFAULT = 0;
   int DIGITS = 20;
   int EOF = 0;
   int INCOMMENT = 1;
   int INQUOTEDSTRING = 3;
   int NESTED_COMMENT = 2;
   int QUOTEDPAIR = 22;
   int QUOTEDSTRING = 19;
   int WS = 6;
   String[] tokenImage;


   static {
      String[] var0 = new String[]{"<EOF>", "\"\\r\"", "\"\\n\"", "\"/\"", "\";\"", "\"=\"", "<WS>", "\"(\"", "\")\"", "<token of kind 9>", "\"(\"", "<token of kind 11>", "<token of kind 12>", "\"(\"", "\")\"", "<token of kind 15>", "\"\\\"\"", "<token of kind 17>", "<token of kind 18>", "\"\\\"\"", "<DIGITS>", "<ATOKEN>", "<QUOTEDPAIR>", "<ANY>"};
      tokenImage = var0;
   }
}
