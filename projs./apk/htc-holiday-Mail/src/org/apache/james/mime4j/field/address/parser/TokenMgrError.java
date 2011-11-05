package org.apache.james.mime4j.field.address.parser;


public class TokenMgrError extends Error {

   static final int INVALID_LEXICAL_STATE = 2;
   static final int LEXICAL_ERROR = 0;
   static final int LOOP_DETECTED = 3;
   static final int STATIC_LEXER_ERROR = 1;
   int errorCode;


   public TokenMgrError() {}

   public TokenMgrError(String var1, int var2) {
      super(var1);
      this.errorCode = var2;
   }

   public TokenMgrError(boolean var1, int var2, int var3, int var4, String var5, char var6, int var7) {
      String var8 = LexicalError(var1, var2, var3, var4, var5, var6);
      this(var8, var7);
   }

   protected static String LexicalError(boolean var0, int var1, int var2, int var3, String var4, char var5) {
      StringBuilder var6 = (new StringBuilder()).append("Lexical error at line ").append(var2).append(", column ").append(var3).append(".  Encountered: ");
      String var7;
      if(var0) {
         var7 = "<EOF> ";
      } else {
         StringBuilder var10 = (new StringBuilder()).append("\"");
         String var11 = addEscapes(String.valueOf(var5));
         var7 = var10.append(var11).append("\"").append(" (").append(var5).append("), ").toString();
      }

      StringBuilder var8 = var6.append(var7).append("after : \"");
      String var9 = addEscapes(var4);
      return var8.append(var9).append("\"").toString();
   }

   protected static final String addEscapes(String var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(true) {
         int var3 = var0.length();
         if(var2 >= var3) {
            return var1.toString();
         }

         switch(var0.charAt(var2)) {
         case 0:
            break;
         case 8:
            StringBuffer var14 = var1.append("\\b");
            break;
         case 9:
            StringBuffer var15 = var1.append("\\t");
            break;
         case 10:
            StringBuffer var16 = var1.append("\\n");
            break;
         case 12:
            StringBuffer var17 = var1.append("\\f");
            break;
         case 13:
            StringBuffer var18 = var1.append("\\r");
            break;
         case 34:
            StringBuffer var19 = var1.append("\\\"");
            break;
         case 39:
            StringBuffer var20 = var1.append("\\\'");
            break;
         case 92:
            StringBuffer var21 = var1.append("\\\\");
            break;
         default:
            char var4 = var0.charAt(var2);
            if(var4 >= 32 && var4 <= 126) {
               var1.append(var4);
            } else {
               StringBuilder var5 = (new StringBuilder()).append("0000");
               String var6 = Integer.toString(var4, 16);
               String var7 = var5.append(var6).toString();
               StringBuilder var8 = (new StringBuilder()).append("\\u");
               int var9 = var7.length() - 4;
               int var10 = var7.length();
               String var11 = var7.substring(var9, var10);
               String var12 = var8.append(var11).toString();
               var1.append(var12);
            }
         }

         ++var2;
      }
   }

   public String getMessage() {
      return super.getMessage();
   }
}
