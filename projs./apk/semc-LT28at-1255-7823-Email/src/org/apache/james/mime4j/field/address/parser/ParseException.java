package org.apache.james.mime4j.field.address.parser;

import org.apache.james.mime4j.field.address.parser.Token;

public class ParseException extends Exception {

   public Token currentToken;
   protected String eol;
   public int[][] expectedTokenSequences;
   protected boolean specialConstructor;
   public String[] tokenImage;


   public ParseException() {
      String var1 = System.getProperty("line.separator", "\n");
      this.eol = var1;
      this.specialConstructor = (boolean)0;
   }

   public ParseException(String var1) {
      super(var1);
      String var2 = System.getProperty("line.separator", "\n");
      this.eol = var2;
      this.specialConstructor = (boolean)0;
   }

   public ParseException(Token var1, int[][] var2, String[] var3) {
      super("");
      String var4 = System.getProperty("line.separator", "\n");
      this.eol = var4;
      this.specialConstructor = (boolean)1;
      this.currentToken = var1;
      this.expectedTokenSequences = var2;
      this.tokenImage = var3;
   }

   protected String add_escapes(String var1) {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         int var4 = var1.length();
         if(var3 >= var4) {
            return var2.toString();
         }

         switch(var1.charAt(var3)) {
         case 0:
            break;
         case 8:
            StringBuffer var15 = var2.append("\\b");
            break;
         case 9:
            StringBuffer var16 = var2.append("\\t");
            break;
         case 10:
            StringBuffer var17 = var2.append("\\n");
            break;
         case 12:
            StringBuffer var18 = var2.append("\\f");
            break;
         case 13:
            StringBuffer var19 = var2.append("\\r");
            break;
         case 34:
            StringBuffer var20 = var2.append("\\\"");
            break;
         case 39:
            StringBuffer var21 = var2.append("\\\'");
            break;
         case 92:
            StringBuffer var22 = var2.append("\\\\");
            break;
         default:
            char var5 = var1.charAt(var3);
            if(var5 >= 32 && var5 <= 126) {
               var2.append(var5);
            } else {
               StringBuilder var6 = (new StringBuilder()).append("0000");
               String var7 = Integer.toString(var5, 16);
               String var8 = var6.append(var7).toString();
               StringBuilder var9 = (new StringBuilder()).append("\\u");
               int var10 = var8.length() - 4;
               int var11 = var8.length();
               String var12 = var8.substring(var10, var11);
               String var13 = var9.append(var12).toString();
               var2.append(var13);
            }
         }

         ++var3;
      }
   }

   public String getMessage() {
      String var1;
      if(!this.specialConstructor) {
         var1 = super.getMessage();
      } else {
         StringBuffer var2 = new StringBuffer();
         int var3 = 0;
         int var4 = 0;

         while(true) {
            int var5 = this.expectedTokenSequences.length;
            if(var4 >= var5) {
               String var18 = "Encountered \"";
               Token var19 = this.currentToken.next;

               for(int var20 = 0; var20 < var3; ++var20) {
                  if(var20 != 0) {
                     var18 = var18 + " ";
                  }

                  if(var19.kind == 0) {
                     StringBuilder var21 = (new StringBuilder()).append(var18);
                     String var22 = this.tokenImage[0];
                     var18 = var21.append(var22).toString();
                     break;
                  }

                  StringBuilder var36 = (new StringBuilder()).append(var18);
                  String var37 = var19.image;
                  String var38 = this.add_escapes(var37);
                  var18 = var36.append(var38).toString();
                  var19 = var19.next;
               }

               StringBuilder var23 = (new StringBuilder()).append(var18).append("\" at line ");
               int var24 = this.currentToken.next.beginLine;
               StringBuilder var25 = var23.append(var24).append(", column ");
               int var26 = this.currentToken.next.beginColumn;
               String var27 = var25.append(var26).toString();
               StringBuilder var28 = (new StringBuilder()).append(var27).append(".");
               String var29 = this.eol;
               String var30 = var28.append(var29).toString();
               String var33;
               if(this.expectedTokenSequences.length == 1) {
                  StringBuilder var31 = (new StringBuilder()).append(var30).append("Was expecting:");
                  String var32 = this.eol;
                  var33 = var31.append(var32).append("    ").toString();
               } else {
                  StringBuilder var39 = (new StringBuilder()).append(var30).append("Was expecting one of:");
                  String var40 = this.eol;
                  var33 = var39.append(var40).append("    ").toString();
               }

               StringBuilder var34 = (new StringBuilder()).append(var33);
               String var35 = var2.toString();
               var1 = var34.append(var35).toString();
               break;
            }

            int var6 = this.expectedTokenSequences[var4].length;
            if(var3 < var6) {
               var3 = this.expectedTokenSequences[var4].length;
            }

            int var7 = 0;

            while(true) {
               int var8 = this.expectedTokenSequences[var4].length;
               if(var7 >= var8) {
                  int[] var13 = this.expectedTokenSequences[var4];
                  int var14 = this.expectedTokenSequences[var4].length - 1;
                  if(var13[var14] != 0) {
                     StringBuffer var15 = var2.append("...");
                  }

                  String var16 = this.eol;
                  StringBuffer var17 = var2.append(var16).append("    ");
                  ++var4;
                  break;
               }

               String[] var9 = this.tokenImage;
               int var10 = this.expectedTokenSequences[var4][var7];
               String var11 = var9[var10];
               StringBuffer var12 = var2.append(var11).append(" ");
               ++var7;
            }
         }
      }

      return var1;
   }
}
