package org.apache.james.mime4j.field.datetime.parser;

import org.apache.james.mime4j.field.datetime.parser.Token;

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
            StringBuffer var17 = var2.append("\\b");
            break;
         case 9:
            StringBuffer var18 = var2.append("\\t");
            break;
         case 10:
            StringBuffer var19 = var2.append("\\n");
            break;
         case 12:
            StringBuffer var20 = var2.append("\\f");
            break;
         case 13:
            StringBuffer var21 = var2.append("\\r");
            break;
         case 34:
            StringBuffer var22 = var2.append("\\\"");
            break;
         case 39:
            StringBuffer var23 = var2.append("\\\'");
            break;
         case 92:
            StringBuffer var24 = var2.append("\\\\");
            break;
         default:
            char var5 = var1.charAt(var3);
            if(var5 >= 32 && var5 <= 126) {
               var2.append(var5);
            } else {
               StringBuffer var6 = new StringBuffer();
               StringBuffer var7 = var6.append("0000");
               String var8 = Integer.toString(var5, 16);
               var7.append(var8);
               StringBuffer var10 = var2.append("\\u");
               int var11 = var6.length() - 4;
               int var12 = var6.length();
               String var13 = var6.substring(var11, var12);
               var10.append(var13);
               int var15 = var6.length();
               var6.delete(0, var15);
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
               StringBuffer var18 = new StringBuffer("Encountered \"");
               Token var19 = this.currentToken.next;

               for(int var20 = 0; var20 < var3; ++var20) {
                  if(var20 != 0) {
                     StringBuffer var21 = var18.append(" ");
                  }

                  if(var19.kind == 0) {
                     String var22 = this.tokenImage[0];
                     var18.append(var22);
                     break;
                  }

                  String var37 = var19.image;
                  String var38 = this.add_escapes(var37);
                  var18.append(var38);
                  var19 = var19.next;
               }

               StringBuffer var24 = var18.append("\" at line ");
               int var25 = this.currentToken.next.beginLine;
               StringBuffer var26 = var24.append(var25).append(", column ");
               int var27 = this.currentToken.next.beginColumn;
               var26.append(var27);
               StringBuffer var29 = var18.append(".");
               String var30 = this.eol;
               var29.append(var30);
               if(this.expectedTokenSequences.length == 1) {
                  StringBuffer var32 = var18.append("Was expecting:");
                  String var33 = this.eol;
                  StringBuffer var34 = var32.append(var33).append("    ");
               } else {
                  StringBuffer var40 = var18.append("Was expecting one of:");
                  String var41 = this.eol;
                  StringBuffer var42 = var40.append(var41).append("    ");
               }

               String var35 = var2.toString();
               var18.append(var35);
               var1 = var18.toString();
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
