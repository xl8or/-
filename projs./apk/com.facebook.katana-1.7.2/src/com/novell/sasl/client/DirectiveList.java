package com.novell.sasl.client;

import com.novell.sasl.client.ParsedDirective;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

class DirectiveList {

   private static final int STATE_LOOKING_FOR_COMMA = 6;
   private static final int STATE_LOOKING_FOR_DIRECTIVE = 2;
   private static final int STATE_LOOKING_FOR_EQUALS = 4;
   private static final int STATE_LOOKING_FOR_FIRST_DIRECTIVE = 1;
   private static final int STATE_LOOKING_FOR_VALUE = 5;
   private static final int STATE_NO_UTF8_SUPPORT = 9;
   private static final int STATE_SCANNING_NAME = 3;
   private static final int STATE_SCANNING_QUOTED_STRING_VALUE = 7;
   private static final int STATE_SCANNING_TOKEN_VALUE = 8;
   private String m_curName;
   private int m_curPos = 0;
   private ArrayList m_directiveList;
   private String m_directives;
   private int m_errorPos;
   private int m_scanStart;
   private int m_state = 1;


   DirectiveList(byte[] var1) {
      ArrayList var2 = new ArrayList(10);
      this.m_directiveList = var2;
      this.m_scanStart = 0;
      this.m_errorPos = -1;

      try {
         String var3 = new String(var1, "UTF-8");
         this.m_directives = var3;
      } catch (UnsupportedEncodingException var5) {
         this.m_state = 9;
      }
   }

   void addDirective(String var1, boolean var2) {
      String var6;
      if(!var2) {
         String var3 = this.m_directives;
         int var4 = this.m_scanStart;
         int var5 = this.m_curPos;
         var6 = var3.substring(var4, var5);
      } else {
         int var11 = this.m_curPos;
         int var12 = this.m_scanStart;
         int var13 = var11 - var12;
         StringBuffer var14 = new StringBuffer(var13);
         int var15 = 0;
         int var16 = this.m_scanStart;

         while(true) {
            int var17 = this.m_curPos;
            if(var16 >= var17) {
               var6 = new String(var14);
               break;
            }

            char var18 = this.m_directives.charAt(var16);
            if(92 == var18) {
               int var19 = var16 + 1;
            }

            char var20 = this.m_directives.charAt(var16);
            var14.setCharAt(var15, var20);
            ++var15;
            int var21 = var16 + 1;
         }
      }

      byte var7;
      if(this.m_state == 7) {
         var7 = 1;
      } else {
         var7 = 2;
      }

      ArrayList var8 = this.m_directiveList;
      ParsedDirective var9 = new ParsedDirective(var1, var6, var7);
      var8.add(var9);
   }

   Iterator getIterator() {
      return this.m_directiveList.iterator();
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

   void parseDirectives() throws SaslException {
      String var1 = "<no name>";
      if(this.m_state == 9) {
         throw new SaslException("No UTF-8 support on platform");
      } else {
         byte var2 = 0;
         char var3 = 0;

         while(true) {
            int var4 = this.m_curPos;
            int var5 = this.m_directives.length();
            if(var4 >= var5) {
               break;
            }

            String var6 = this.m_directives;
            int var7 = this.m_curPos;
            char var8 = var6.charAt(var7);
            switch(this.m_state) {
            case 1:
            case 2:
               if(!this.isWhiteSpace(var8)) {
                  if(!this.isValidTokenChar(var8)) {
                     int var10 = this.m_curPos;
                     this.m_errorPos = var10;
                     throw new SaslException("Parse error: Invalid name character");
                  }

                  int var9 = this.m_curPos;
                  this.m_scanStart = var9;
                  this.m_state = 3;
               }
               break;
            case 3:
               if(!this.isValidTokenChar(var8)) {
                  if(this.isWhiteSpace(var8)) {
                     String var11 = this.m_directives;
                     int var12 = this.m_scanStart;
                     int var13 = this.m_curPos;
                     var1 = var11.substring(var12, var13);
                     this.m_state = 4;
                  } else {
                     if(61 != var8) {
                        int var17 = this.m_curPos;
                        this.m_errorPos = var17;
                        throw new SaslException("Parse error: Invalid name character");
                     }

                     String var14 = this.m_directives;
                     int var15 = this.m_scanStart;
                     int var16 = this.m_curPos;
                     var1 = var14.substring(var15, var16);
                     this.m_state = 5;
                  }
               }
               break;
            case 4:
               if(!this.isWhiteSpace(var8)) {
                  if(61 != var8) {
                     int var18 = this.m_curPos;
                     this.m_errorPos = var18;
                     throw new SaslException("Parse error: Expected equals sign \'=\'.");
                  }

                  this.m_state = 5;
               }
               break;
            case 5:
               if(!this.isWhiteSpace(var8)) {
                  if(34 == var8) {
                     int var19 = this.m_curPos + 1;
                     this.m_scanStart = var19;
                     this.m_state = 7;
                  } else {
                     if(!this.isValidTokenChar(var8)) {
                        int var21 = this.m_curPos;
                        this.m_errorPos = var21;
                        throw new SaslException("Parse error: Unexpected character");
                     }

                     int var20 = this.m_curPos;
                     this.m_scanStart = var20;
                     this.m_state = 8;
                  }
               }
               break;
            case 6:
               if(!this.isWhiteSpace(var8)) {
                  if(var8 != 44) {
                     int var24 = this.m_curPos;
                     this.m_errorPos = var24;
                     throw new SaslException("Parse error: Expected a comma.");
                  }

                  this.m_state = 2;
               }
               break;
            case 7:
               if(92 == var8) {
                  var2 = 1;
               }

               if(34 == var8 && 92 != var3) {
                  this.addDirective(var1, (boolean)var2);
                  this.m_state = 6;
                  boolean var23 = false;
               }
               break;
            case 8:
               if(!this.isValidTokenChar(var8)) {
                  if(this.isWhiteSpace(var8)) {
                     this.addDirective(var1, (boolean)0);
                     this.m_state = 6;
                  } else {
                     if(44 != var8) {
                        int var22 = this.m_curPos;
                        this.m_errorPos = var22;
                        throw new SaslException("Parse error: Invalid value character");
                     }

                     this.addDirective(var1, (boolean)0);
                     this.m_state = 2;
                  }
               }
            }

            if(false) {
               break;
            }

            int var25 = this.m_curPos + 1;
            this.m_curPos = var25;
            var3 = var8;
         }

         if(true) {
            switch(this.m_state) {
            case 1:
            case 6:
            default:
               return;
            case 2:
               throw new SaslException("Parse error: Trailing comma.");
            case 3:
            case 4:
            case 5:
               throw new SaslException("Parse error: Missing value.");
            case 7:
               throw new SaslException("Parse error: Missing closing quote.");
            case 8:
               this.addDirective(var1, (boolean)0);
            }
         }
      }
   }
}
