package org.apache.james.mime4j.field.contenttype.parser;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.james.mime4j.field.contenttype.parser.ContentTypeParserConstants;
import org.apache.james.mime4j.field.contenttype.parser.ContentTypeParserTokenManager;
import org.apache.james.mime4j.field.contenttype.parser.ParseException;
import org.apache.james.mime4j.field.contenttype.parser.SimpleCharStream;
import org.apache.james.mime4j.field.contenttype.parser.Token;

public class ContentTypeParser implements ContentTypeParserConstants {

   private static int[] jj_la1_0;
   private Vector jj_expentries;
   private int[] jj_expentry;
   private int jj_gen;
   SimpleCharStream jj_input_stream;
   private int jj_kind;
   private final int[] jj_la1;
   public Token jj_nt;
   private int jj_ntk;
   private ArrayList paramNames;
   private ArrayList paramValues;
   private String subtype;
   public Token token;
   public ContentTypeParserTokenManager token_source;
   private String type;


   static {
      jj_la1_0();
   }

   public ContentTypeParser(InputStream var1) {
      this(var1, (String)null);
   }

   public ContentTypeParser(InputStream var1, String var2) {
      ArrayList var3 = new ArrayList();
      this.paramNames = var3;
      ArrayList var4 = new ArrayList();
      this.paramValues = var4;
      int[] var5 = new int[3];
      this.jj_la1 = var5;
      Vector var6 = new Vector();
      this.jj_expentries = var6;
      this.jj_kind = -1;

      try {
         SimpleCharStream var7 = new SimpleCharStream(var1, var2, 1, 1);
         this.jj_input_stream = var7;
      } catch (UnsupportedEncodingException var13) {
         throw new RuntimeException(var13);
      }

      SimpleCharStream var8 = this.jj_input_stream;
      ContentTypeParserTokenManager var9 = new ContentTypeParserTokenManager(var8);
      this.token_source = var9;
      Token var10 = new Token();
      this.token = var10;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var11 = 0; var11 < 3; ++var11) {
         this.jj_la1[var11] = -1;
      }

   }

   public ContentTypeParser(Reader var1) {
      ArrayList var2 = new ArrayList();
      this.paramNames = var2;
      ArrayList var3 = new ArrayList();
      this.paramValues = var3;
      int[] var4 = new int[3];
      this.jj_la1 = var4;
      Vector var5 = new Vector();
      this.jj_expentries = var5;
      this.jj_kind = -1;
      SimpleCharStream var6 = new SimpleCharStream(var1, 1, 1);
      this.jj_input_stream = var6;
      SimpleCharStream var7 = this.jj_input_stream;
      ContentTypeParserTokenManager var8 = new ContentTypeParserTokenManager(var7);
      this.token_source = var8;
      Token var9 = new Token();
      this.token = var9;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var10 = 0; var10 < 3; ++var10) {
         this.jj_la1[var10] = -1;
      }

   }

   public ContentTypeParser(ContentTypeParserTokenManager var1) {
      ArrayList var2 = new ArrayList();
      this.paramNames = var2;
      ArrayList var3 = new ArrayList();
      this.paramValues = var3;
      int[] var4 = new int[3];
      this.jj_la1 = var4;
      Vector var5 = new Vector();
      this.jj_expentries = var5;
      this.jj_kind = -1;
      this.token_source = var1;
      Token var6 = new Token();
      this.token = var6;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var7 = 0; var7 < 3; ++var7) {
         this.jj_la1[var7] = -1;
      }

   }

   private final Token jj_consume_token(int var1) throws ParseException {
      Token var2 = this.token;
      if(var2.next != null) {
         Token var3 = this.token.next;
         this.token = var3;
      } else {
         Token var5 = this.token;
         Token var6 = this.token_source.getNextToken();
         var5.next = var6;
         this.token = var6;
      }

      this.jj_ntk = -1;
      if(this.token.kind == var1) {
         int var4 = this.jj_gen + 1;
         this.jj_gen = var4;
         return this.token;
      } else {
         this.token = var2;
         this.jj_kind = var1;
         throw this.generateParseException();
      }
   }

   private static void jj_la1_0() {
      jj_la1_0 = new int[]{2, 16, 2621440};
   }

   private final int jj_ntk() {
      Token var1 = this.token.next;
      this.jj_nt = var1;
      int var4;
      if(var1 == null) {
         Token var2 = this.token;
         Token var3 = this.token_source.getNextToken();
         var2.next = var3;
         var4 = var3.kind;
         this.jj_ntk = var4;
      } else {
         var4 = this.jj_nt.kind;
         this.jj_ntk = var4;
      }

      return var4;
   }

   public static void main(String[] var0) throws ParseException {
      try {
         while(true) {
            InputStream var1 = System.in;
            (new ContentTypeParser(var1)).parseLine();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   public void ReInit(InputStream var1) {
      this.ReInit(var1, (String)null);
   }

   public void ReInit(InputStream var1, String var2) {
      try {
         this.jj_input_stream.ReInit(var1, var2, 1, 1);
      } catch (UnsupportedEncodingException var8) {
         throw new RuntimeException(var8);
      }

      ContentTypeParserTokenManager var3 = this.token_source;
      SimpleCharStream var4 = this.jj_input_stream;
      var3.ReInit(var4);
      Token var5 = new Token();
      this.token = var5;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var6 = 0; var6 < 3; ++var6) {
         this.jj_la1[var6] = -1;
      }

   }

   public void ReInit(Reader var1) {
      this.jj_input_stream.ReInit(var1, 1, 1);
      ContentTypeParserTokenManager var2 = this.token_source;
      SimpleCharStream var3 = this.jj_input_stream;
      var2.ReInit(var3);
      Token var4 = new Token();
      this.token = var4;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var5 = 0; var5 < 3; ++var5) {
         this.jj_la1[var5] = -1;
      }

   }

   public void ReInit(ContentTypeParserTokenManager var1) {
      this.token_source = var1;
      Token var2 = new Token();
      this.token = var2;
      this.jj_ntk = -1;
      this.jj_gen = 0;

      for(int var3 = 0; var3 < 3; ++var3) {
         this.jj_la1[var3] = -1;
      }

   }

   public final void disable_tracing() {}

   public final void enable_tracing() {}

   public ParseException generateParseException() {
      this.jj_expentries.removeAllElements();
      boolean[] var1 = new boolean[24];

      for(int var2 = 0; var2 < 24; ++var2) {
         var1[var2] = false;
      }

      if(this.jj_kind >= 0) {
         int var3 = this.jj_kind;
         var1[var3] = true;
         this.jj_kind = -1;
      }

      for(int var4 = 0; var4 < 3; ++var4) {
         int var5 = this.jj_la1[var4];
         int var6 = this.jj_gen;
         if(var5 == var6) {
            for(int var7 = 0; var7 < 32; ++var7) {
               int var8 = jj_la1_0[var4];
               int var9 = 1 << var7;
               if((var8 & var9) != 0) {
                  var1[var7] = true;
               }
            }
         }
      }

      for(int var10 = 0; var10 < 24; ++var10) {
         if(var1[var10]) {
            int[] var11 = new int[1];
            this.jj_expentry = var11;
            this.jj_expentry[0] = var10;
            Vector var12 = this.jj_expentries;
            int[] var13 = this.jj_expentry;
            var12.addElement(var13);
         }
      }

      int[] var14 = new int[this.jj_expentries.size()];
      int var15 = 0;

      while(true) {
         int var16 = this.jj_expentries.size();
         if(var15 >= var16) {
            Token var18 = this.token;
            String[] var19 = tokenImage;
            return new ParseException(var18, (int[][])var14, var19);
         }

         int[] var17 = (int[])((int[])this.jj_expentries.elementAt(var15));
         var14[var15] = (int)var17;
         ++var15;
      }
   }

   public final Token getNextToken() {
      if(this.token.next != null) {
         Token var1 = this.token.next;
         this.token = var1;
      } else {
         Token var3 = this.token;
         Token var4 = this.token_source.getNextToken();
         var3.next = var4;
         this.token = var4;
      }

      this.jj_ntk = -1;
      int var2 = this.jj_gen + 1;
      this.jj_gen = var2;
      return this.token;
   }

   public ArrayList getParamNames() {
      return this.paramNames;
   }

   public ArrayList getParamValues() {
      return this.paramValues;
   }

   public String getSubType() {
      return this.subtype;
   }

   public final Token getToken(int var1) {
      Token var2 = this.token;
      int var3 = 0;

      Token var4;
      Token var5;
      for(var4 = var2; var3 < var1; var4 = var5) {
         if(var4.next != null) {
            var5 = var4.next;
         } else {
            var5 = this.token_source.getNextToken();
            var4.next = var5;
         }

         ++var3;
      }

      return var4;
   }

   public String getType() {
      return this.type;
   }

   public final void parameter() throws ParseException {
      Token var1 = this.jj_consume_token(21);
      Token var2 = this.jj_consume_token(5);
      String var3 = this.value();
      ArrayList var4 = this.paramNames;
      String var5 = var1.image;
      var4.add(var5);
      this.paramValues.add(var3);
   }

   public final void parse() throws ParseException {
      Token var1 = this.jj_consume_token(21);
      Token var2 = this.jj_consume_token(3);
      Token var3 = this.jj_consume_token(21);
      String var4 = var1.image;
      this.type = var4;
      String var5 = var3.image;
      this.subtype = var5;

      while(true) {
         int var6;
         if(this.jj_ntk == -1) {
            var6 = this.jj_ntk();
         } else {
            var6 = this.jj_ntk;
         }

         switch(var6) {
         case 4:
            Token var9 = this.jj_consume_token(4);
            this.parameter();
            break;
         default:
            int[] var7 = this.jj_la1;
            int var8 = this.jj_gen;
            var7[1] = var8;
            return;
         }
      }
   }

   public final void parseAll() throws ParseException {
      this.parse();
      Token var1 = this.jj_consume_token(0);
   }

   public final void parseLine() throws ParseException {
      this.parse();
      int var1;
      if(this.jj_ntk == -1) {
         var1 = this.jj_ntk();
      } else {
         var1 = this.jj_ntk;
      }

      switch(var1) {
      case 1:
         Token var5 = this.jj_consume_token(1);
         break;
      default:
         int[] var2 = this.jj_la1;
         int var3 = this.jj_gen;
         var2[0] = var3;
      }

      Token var4 = this.jj_consume_token(2);
   }

   public final String value() throws ParseException {
      int var1;
      if(this.jj_ntk == -1) {
         var1 = this.jj_ntk();
      } else {
         var1 = this.jj_ntk;
      }

      Token var5;
      switch(var1) {
      case 19:
         var5 = this.jj_consume_token(19);
         break;
      case 20:
      default:
         int[] var2 = this.jj_la1;
         int var3 = this.jj_gen;
         var2[2] = var3;
         Token var4 = this.jj_consume_token(-1);
         throw new ParseException();
      case 21:
         var5 = this.jj_consume_token(21);
      }

      return var5.image;
   }
}
