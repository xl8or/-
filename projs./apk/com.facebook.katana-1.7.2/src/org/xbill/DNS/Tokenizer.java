package org.xbill.DNS;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.Address;
import org.xbill.DNS.Name;
import org.xbill.DNS.RelativeNameException;
import org.xbill.DNS.TTL;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base32;
import org.xbill.DNS.utils.base64;

public class Tokenizer {

   public static final int COMMENT = 5;
   public static final int EOF = 0;
   public static final int EOL = 1;
   public static final int IDENTIFIER = 3;
   public static final int QUOTED_STRING = 4;
   public static final int WHITESPACE = 2;
   private static String delim = " \t\n;()\"";
   private static String quotes = "\"";
   private Tokenizer.Token current;
   private String delimiters;
   private String filename;
   private PushbackInputStream is;
   private int line;
   private int multiline;
   private boolean quoting;
   private StringBuffer sb;
   private boolean ungottenToken;
   private boolean wantClose;


   public Tokenizer(File var1) throws FileNotFoundException {
      FileInputStream var2 = new FileInputStream(var1);
      this((InputStream)var2);
      this.wantClose = (boolean)1;
      String var3 = var1.getName();
      this.filename = var3;
   }

   public Tokenizer(InputStream var1) {
      Object var2;
      if(!(var1 instanceof BufferedInputStream)) {
         var2 = new BufferedInputStream(var1);
      } else {
         var2 = var1;
      }

      PushbackInputStream var3 = new PushbackInputStream((InputStream)var2, 2);
      this.is = var3;
      this.ungottenToken = (boolean)0;
      this.multiline = 0;
      this.quoting = (boolean)0;
      String var4 = delim;
      this.delimiters = var4;
      Tokenizer.Token var5 = new Tokenizer.Token((Tokenizer.1)null);
      this.current = var5;
      StringBuffer var6 = new StringBuffer();
      this.sb = var6;
      this.filename = "<none>";
      this.line = 1;
   }

   public Tokenizer(String var1) {
      byte[] var2 = var1.getBytes();
      ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
      this((InputStream)var3);
   }

   private String _getIdentifier(String var1) throws IOException {
      Tokenizer.Token var2 = this.get();
      if(var2.type != 3) {
         String var3 = "expected " + var1;
         throw this.exception(var3);
      } else {
         return var2.value;
      }
   }

   private void checkUnbalancedParens() throws TextParseException {
      if(this.multiline > 0) {
         throw this.exception("unbalanced parentheses");
      }
   }

   private int getChar() throws IOException {
      int var1 = this.is.read();
      if(var1 == 13) {
         int var2 = this.is.read();
         if(var2 != 10) {
            this.is.unread(var2);
         }

         var1 = 10;
      }

      if(var1 == 10) {
         int var3 = this.line + 1;
         this.line = var3;
      }

      return var1;
   }

   private String remainingStrings() throws IOException {
      StringBuffer var1 = null;

      while(true) {
         Tokenizer.Token var2 = this.get();
         if(!var2.isString()) {
            this.unget();
            String var5;
            if(var1 == null) {
               var5 = null;
            } else {
               var5 = var1.toString();
            }

            return var5;
         }

         if(var1 == null) {
            var1 = new StringBuffer();
         }

         String var3 = var2.value;
         var1.append(var3);
      }
   }

   private int skipWhitespace() throws IOException {
      int var1 = 0;

      while(true) {
         int var2 = this.getChar();
         if(var2 != 32 && var2 != 9 && (var2 != 10 || this.multiline <= 0)) {
            this.ungetChar(var2);
            return var1;
         }

         ++var1;
      }
   }

   private void ungetChar(int var1) throws IOException {
      if(var1 != -1) {
         this.is.unread(var1);
         if(var1 == 10) {
            int var2 = this.line - 1;
            this.line = var2;
         }
      }
   }

   public void close() {
      if(this.wantClose) {
         try {
            this.is.close();
         } catch (IOException var2) {
            ;
         }
      }
   }

   public TextParseException exception(String var1) {
      String var2 = this.filename;
      int var3 = this.line;
      return new Tokenizer.TokenizerException(var2, var3, var1);
   }

   protected void finalize() {
      this.close();
   }

   public Tokenizer.Token get() throws IOException {
      return this.get((boolean)0, (boolean)0);
   }

   public Tokenizer.Token get(boolean var1, boolean var2) throws IOException {
      Tokenizer.Token var3;
      if(this.ungottenToken) {
         this.ungottenToken = (boolean)0;
         if(this.current.type == 2) {
            if(var1) {
               var3 = this.current;
               return var3;
            }
         } else {
            if(this.current.type != 5) {
               if(this.current.type == 1) {
                  int var4 = this.line + 1;
                  this.line = var4;
               }

               var3 = this.current;
               return var3;
            }

            if(var2) {
               var3 = this.current;
               return var3;
            }
         }
      }

      if(this.skipWhitespace() > 0 && var1) {
         var3 = this.current.set(2, (StringBuffer)null);
         return var3;
      } else {
         byte var5 = 3;
         this.sb.setLength(0);

         while(true) {
            label90:
            while(true) {
               int var6 = this.getChar();
               if(var6 == -1 || this.delimiters.indexOf(var6) != -1) {
                  if(var6 == -1) {
                     if(this.quoting) {
                        throw this.exception("EOF in quoted string");
                     }

                     if(this.sb.length() == 0) {
                        var3 = this.current.set(0, (StringBuffer)null);
                     } else {
                        Tokenizer.Token var7 = this.current;
                        StringBuffer var8 = this.sb;
                        var3 = var7.set(var5, var8);
                     }

                     return var3;
                  }

                  if(this.sb.length() == 0 && var5 != 4) {
                     if(var6 == 40) {
                        int var9 = this.multiline + 1;
                        this.multiline = var9;
                        int var10 = this.skipWhitespace();
                        continue;
                     }

                     if(var6 == 41) {
                        if(this.multiline <= 0) {
                           throw this.exception("invalid close parenthesis");
                        }

                        int var11 = this.multiline - 1;
                        this.multiline = var11;
                        int var12 = this.skipWhitespace();
                        continue;
                     }

                     if(var6 == 34) {
                        if(!this.quoting) {
                           this.quoting = (boolean)1;
                           String var13 = quotes;
                           this.delimiters = var13;
                           var5 = 4;
                        } else {
                           this.quoting = (boolean)0;
                           String var14 = delim;
                           this.delimiters = var14;
                           int var15 = this.skipWhitespace();
                        }
                        continue;
                     }

                     if(var6 == 10) {
                        var3 = this.current.set(1, (StringBuffer)null);
                        return var3;
                     }

                     if(var6 != 59) {
                        throw new IllegalStateException();
                     }

                     while(true) {
                        int var16 = this.getChar();
                        if(var16 == 10 || var16 == -1) {
                           if(var2) {
                              this.ungetChar(var16);
                              Tokenizer.Token var17 = this.current;
                              StringBuffer var18 = this.sb;
                              var3 = var17.set(5, var18);
                              return var3;
                           }

                           if(var16 == -1 && var5 != 4) {
                              this.checkUnbalancedParens();
                              var3 = this.current.set(0, (StringBuffer)null);
                              return var3;
                           }

                           if(this.multiline > 0) {
                              int var22 = this.skipWhitespace();
                              this.sb.setLength(0);
                              continue label90;
                           }

                           var3 = this.current.set(1, (StringBuffer)null);
                           return var3;
                        }

                        StringBuffer var19 = this.sb;
                        char var20 = (char)var16;
                        var19.append(var20);
                     }
                  }

                  this.ungetChar(var6);
                  if(this.sb.length() == 0 && var5 != 4) {
                     this.checkUnbalancedParens();
                     var3 = this.current.set(0, (StringBuffer)null);
                     return var3;
                  }

                  Tokenizer.Token var27 = this.current;
                  StringBuffer var28 = this.sb;
                  var3 = var27.set(var5, var28);
                  return var3;
               } else {
                  if(var6 == 92) {
                     var6 = this.getChar();
                     if(var6 == -1) {
                        throw this.exception("unterminated escape sequence");
                     }

                     StringBuffer var23 = this.sb.append('\\');
                  } else if(this.quoting && var6 == 10) {
                     throw this.exception("newline in quoted string");
                  }

                  StringBuffer var24 = this.sb;
                  char var25 = (char)var6;
                  var24.append(var25);
               }
            }
         }
      }
   }

   public InetAddress getAddress(int var1) throws IOException {
      String var2 = this._getIdentifier("an address");

      try {
         InetAddress var3 = Address.getByAddress(var2, var1);
         return var3;
      } catch (UnknownHostException var5) {
         String var4 = var5.getMessage();
         throw this.exception(var4);
      }
   }

   public byte[] getBase32String(base32 var1) throws IOException {
      String var2 = this._getIdentifier("a base32 string");
      byte[] var3 = var1.fromString(var2);
      if(var3 == null) {
         throw this.exception("invalid base32 encoding");
      } else {
         return var3;
      }
   }

   public byte[] getBase64() throws IOException {
      return this.getBase64((boolean)0);
   }

   public byte[] getBase64(boolean var1) throws IOException {
      String var2 = this.remainingStrings();
      byte[] var3;
      if(var2 == null) {
         if(var1) {
            throw this.exception("expected base64 encoded string");
         }

         var3 = null;
      } else {
         var3 = base64.fromString(var2);
         if(var3 == null) {
            throw this.exception("invalid base64 encoding");
         }
      }

      return var3;
   }

   public void getEOL() throws IOException {
      Tokenizer.Token var1 = this.get();
      if(var1.type != 1) {
         if(var1.type != 0) {
            throw this.exception("expected EOL or EOF");
         }
      }
   }

   public byte[] getHex() throws IOException {
      return this.getHex((boolean)0);
   }

   public byte[] getHex(boolean var1) throws IOException {
      String var2 = this.remainingStrings();
      byte[] var3;
      if(var2 == null) {
         if(var1) {
            throw this.exception("expected hex encoded string");
         }

         var3 = null;
      } else {
         var3 = base16.fromString(var2);
         if(var3 == null) {
            throw this.exception("invalid hex encoding");
         }
      }

      return var3;
   }

   public byte[] getHexString() throws IOException {
      byte[] var1 = base16.fromString(this._getIdentifier("a hex string"));
      if(var1 == null) {
         throw this.exception("invalid hex encoding");
      } else {
         return var1;
      }
   }

   public String getIdentifier() throws IOException {
      return this._getIdentifier("an identifier");
   }

   public long getLong() throws IOException {
      String var1 = this._getIdentifier("an integer");
      if(!Character.isDigit(var1.charAt(0))) {
         throw this.exception("expected an integer");
      } else {
         try {
            long var2 = Long.parseLong(var1);
            return var2;
         } catch (NumberFormatException var5) {
            throw this.exception("expected an integer");
         }
      }
   }

   public Name getName(Name var1) throws IOException {
      String var2 = this._getIdentifier("a name");

      try {
         Name var5 = Name.fromString(var2, var1);
         if(!var5.isAbsolute()) {
            throw new RelativeNameException(var5);
         } else {
            return var5;
         }
      } catch (TextParseException var4) {
         String var3 = var4.getMessage();
         throw this.exception(var3);
      }
   }

   public String getString() throws IOException {
      Tokenizer.Token var1 = this.get();
      if(!var1.isString()) {
         throw this.exception("expected a string");
      } else {
         return var1.value;
      }
   }

   public long getTTL() throws IOException {
      String var1 = this._getIdentifier("a TTL value");

      try {
         long var2 = TTL.parseTTL(var1);
         return var2;
      } catch (NumberFormatException var5) {
         throw this.exception("expected a TTL value");
      }
   }

   public long getTTLLike() throws IOException {
      String var1 = this._getIdentifier("a TTL-like value");

      try {
         long var2 = TTL.parse(var1, (boolean)0);
         return var2;
      } catch (NumberFormatException var5) {
         throw this.exception("expected a TTL-like value");
      }
   }

   public int getUInt16() throws IOException {
      long var1 = this.getLong();
      if(var1 >= 0L && var1 <= 65535L) {
         return (int)var1;
      } else {
         throw this.exception("expected an 16 bit unsigned integer");
      }
   }

   public long getUInt32() throws IOException {
      long var1 = this.getLong();
      if(var1 >= 0L && var1 <= 4294967295L) {
         return var1;
      } else {
         throw this.exception("expected an 32 bit unsigned integer");
      }
   }

   public int getUInt8() throws IOException {
      long var1 = this.getLong();
      if(var1 >= 0L && var1 <= 255L) {
         return (int)var1;
      } else {
         throw this.exception("expected an 8 bit unsigned integer");
      }
   }

   public void unget() {
      if(this.ungottenToken) {
         throw new IllegalStateException("Cannot unget multiple tokens");
      } else {
         if(this.current.type == 1) {
            int var1 = this.line - 1;
            this.line = var1;
         }

         this.ungottenToken = (boolean)1;
      }
   }

   public static class Token {

      public int type;
      public String value;


      private Token() {
         this.type = -1;
         this.value = null;
      }

      // $FF: synthetic method
      Token(Tokenizer.1 var1) {
         this();
      }

      private Tokenizer.Token set(int var1, StringBuffer var2) {
         if(var1 < 0) {
            throw new IllegalArgumentException();
         } else {
            this.type = var1;
            String var3;
            if(var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toString();
            }

            this.value = var3;
            return this;
         }
      }

      public boolean isEOL() {
         boolean var1;
         if(this.type != 1 && this.type != 0) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public boolean isString() {
         boolean var1;
         if(this.type != 3 && this.type != 4) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public String toString() {
         String var1;
         switch(this.type) {
         case 0:
            var1 = "<eof>";
            break;
         case 1:
            var1 = "<eol>";
            break;
         case 2:
            var1 = "<whitespace>";
            break;
         case 3:
            StringBuilder var2 = (new StringBuilder()).append("<identifier: ");
            String var3 = this.value;
            var1 = var2.append(var3).append(">").toString();
            break;
         case 4:
            StringBuilder var4 = (new StringBuilder()).append("<quoted_string: ");
            String var5 = this.value;
            var1 = var4.append(var5).append(">").toString();
            break;
         case 5:
            StringBuilder var6 = (new StringBuilder()).append("<comment: ");
            String var7 = this.value;
            var1 = var6.append(var7).append(">").toString();
            break;
         default:
            var1 = "<unknown>";
         }

         return var1;
      }
   }

   static class TokenizerException extends TextParseException {

      String message;


      public TokenizerException(String var1, int var2, String var3) {
         String var4 = var1 + ":" + var2 + ": " + var3;
         super(var4);
         this.message = var3;
      }

      public String getBaseMessage() {
         return this.message;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
