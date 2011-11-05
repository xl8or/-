package com.novell.sasl.client;

import org.apache.harmony.javax.security.sasl.SaslException;

class TokenParser {

   private static final int STATE_DONE = 6;
   private static final int STATE_LOOKING_FOR_COMMA = 4;
   private static final int STATE_LOOKING_FOR_FIRST_TOKEN = 1;
   private static final int STATE_LOOKING_FOR_TOKEN = 2;
   private static final int STATE_PARSING_ERROR = 5;
   private static final int STATE_SCANNING_TOKEN = 3;
   private int m_curPos;
   private int m_scanStart;
   private int m_state;
   private String m_tokens;


   TokenParser(String var1) {
      this.m_tokens = var1;
      this.m_curPos = 0;
      this.m_scanStart = 0;
      this.m_state = 1;
   }

   boolean isValidTokenChar(char var1) {
      boolean var2;
      if((var1 < 0 || var1 > 32) && (var1 < 58 || var1 > 64) && (var1 < 91 || var1 > 93) && 44 != var1 && 37 != var1 && 40 != var1 && 41 != var1 && 123 != var1 && 125 != var1 && 127 != var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean isWhiteSpace(char var1) {
      boolean var2;
      if(9 != var1 && 10 != var1 && 13 != var1 && 32 != var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   String parseToken() throws SaslException {
      String var1;
      if(this.m_state == 6) {
         var1 = null;
      } else {
         var1 = null;

         while(true) {
            int var2 = this.m_curPos;
            int var3 = this.m_tokens.length();
            if(var2 >= var3 || var1 != null) {
               if(var1 == null) {
                  switch(this.m_state) {
                  case 1:
                  case 4:
                  default:
                     return var1;
                  case 2:
                     throw new SaslException("Trialing comma");
                  case 3:
                     String var24 = this.m_tokens;
                     int var25 = this.m_scanStart;
                     var1 = var24.substring(var25);
                     this.m_state = 6;
                  }
               }
               break;
            }

            String var4 = this.m_tokens;
            int var5 = this.m_curPos;
            char var6 = var4.charAt(var5);
            switch(this.m_state) {
            case 1:
            case 2:
               if(!this.isWhiteSpace(var6)) {
                  if(!this.isValidTokenChar(var6)) {
                     this.m_state = 5;
                     StringBuilder var9 = (new StringBuilder()).append("Invalid token character at position ");
                     int var10 = this.m_curPos;
                     String var11 = var9.append(var10).toString();
                     throw new SaslException(var11);
                  }

                  int var8 = this.m_curPos;
                  this.m_scanStart = var8;
                  this.m_state = 3;
               }
               break;
            case 3:
               if(!this.isValidTokenChar(var6)) {
                  if(this.isWhiteSpace(var6)) {
                     String var12 = this.m_tokens;
                     int var13 = this.m_scanStart;
                     int var14 = this.m_curPos;
                     var1 = var12.substring(var13, var14);
                     this.m_state = 4;
                  } else {
                     if(44 != var6) {
                        this.m_state = 5;
                        StringBuilder var18 = (new StringBuilder()).append("Invalid token character at position ");
                        int var19 = this.m_curPos;
                        String var20 = var18.append(var19).toString();
                        throw new SaslException(var20);
                     }

                     String var15 = this.m_tokens;
                     int var16 = this.m_scanStart;
                     int var17 = this.m_curPos;
                     var1 = var15.substring(var16, var17);
                     this.m_state = 2;
                  }
               }
               break;
            case 4:
               if(!this.isWhiteSpace(var6)) {
                  if(var6 != 44) {
                     this.m_state = 5;
                     StringBuilder var21 = (new StringBuilder()).append("Expected a comma, found \'").append(var6).append("\' at postion ");
                     int var22 = this.m_curPos;
                     String var23 = var21.append(var22).toString();
                     throw new SaslException(var23);
                  }

                  this.m_state = 2;
               }
            }

            int var7 = this.m_curPos + 1;
            this.m_curPos = var7;
         }
      }

      return var1;
   }
}
