package org.apache.commons.httpclient.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.NameValuePair;

public class ParameterParser {

   private char[] chars = null;
   private int i1 = 0;
   private int i2 = 0;
   private int len = 0;
   private int pos = 0;


   public ParameterParser() {}

   private String getToken(boolean var1) {
      while(true) {
         int var2 = this.i1;
         int var3 = this.i2;
         if(var2 < var3) {
            char[] var4 = this.chars;
            int var5 = this.i1;
            if(Character.isWhitespace(var4[var5])) {
               int var6 = this.i1 + 1;
               this.i1 = var6;
               continue;
            }
         }

         while(true) {
            int var7 = this.i2;
            int var8 = this.i1;
            if(var7 <= var8) {
               break;
            }

            char[] var9 = this.chars;
            int var10 = this.i2 - 1;
            if(!Character.isWhitespace(var9[var10])) {
               break;
            }

            int var11 = this.i2 - 1;
            this.i2 = var11;
         }

         if(var1) {
            int var12 = this.i2;
            int var13 = this.i1;
            if(var12 - var13 >= 2) {
               char[] var14 = this.chars;
               int var15 = this.i1;
               if(var14[var15] == 34) {
                  char[] var16 = this.chars;
                  int var17 = this.i2 - 1;
                  if(var16[var17] == 34) {
                     int var18 = this.i1 + 1;
                     this.i1 = var18;
                     int var19 = this.i2 - 1;
                     this.i2 = var19;
                  }
               }
            }
         }

         String var20 = null;
         int var21 = this.i2;
         int var22 = this.i1;
         if(var21 >= var22) {
            char[] var23 = this.chars;
            int var24 = this.i1;
            int var25 = this.i2;
            int var26 = this.i1;
            int var27 = var25 - var26;
            var20 = new String(var23, var24, var27);
         }

         return var20;
      }
   }

   private boolean hasChar() {
      int var1 = this.pos;
      int var2 = this.len;
      boolean var3;
      if(var1 < var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean isOneOf(char var1, char[] var2) {
      boolean var3 = false;
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 >= var5) {
            break;
         }

         char var6 = var2[var4];
         if(var1 == var6) {
            var3 = true;
            break;
         }

         ++var4;
      }

      return var3;
   }

   private String parseQuotedToken(char[] var1) {
      int var2 = this.pos;
      this.i1 = var2;
      int var3 = this.pos;
      this.i2 = var3;
      boolean var4 = false;

      int var10;
      for(boolean var5 = false; this.hasChar(); this.pos = var10) {
         char[] var6 = this.chars;
         int var7 = this.pos;
         char var8 = var6[var7];
         if(!var4 && this.isOneOf(var8, var1)) {
            break;
         }

         if(!var5 && var8 == 34) {
            if(!var4) {
               var4 = true;
            } else {
               var4 = false;
            }
         }

         if(!var5 && var8 == 92) {
            var5 = true;
         } else {
            var5 = false;
         }

         int var9 = this.i2 + 1;
         this.i2 = var9;
         var10 = this.pos + 1;
      }

      return this.getToken((boolean)1);
   }

   private String parseToken(char[] var1) {
      int var2 = this.pos;
      this.i1 = var2;
      int var3 = this.pos;

      int var8;
      for(this.i2 = var3; this.hasChar(); this.pos = var8) {
         char[] var4 = this.chars;
         int var5 = this.pos;
         char var6 = var4[var5];
         if(this.isOneOf(var6, var1)) {
            break;
         }

         int var7 = this.i2 + 1;
         this.i2 = var7;
         var8 = this.pos + 1;
      }

      return this.getToken((boolean)0);
   }

   public List parse(String var1, char var2) {
      Object var3;
      if(var1 == null) {
         var3 = new ArrayList();
      } else {
         char[] var4 = var1.toCharArray();
         var3 = this.parse(var4, var2);
      }

      return (List)var3;
   }

   public List parse(char[] var1, char var2) {
      Object var3;
      if(var1 == null) {
         var3 = new ArrayList();
      } else {
         int var4 = var1.length;
         var3 = this.parse(var1, 0, var4, var2);
      }

      return (List)var3;
   }

   public List parse(char[] var1, int var2, int var3, char var4) {
      ArrayList var5;
      if(var1 == null) {
         var5 = new ArrayList();
      } else {
         ArrayList var6 = new ArrayList();
         this.chars = var1;
         this.pos = var2;
         this.len = var3;

         while(this.hasChar()) {
            char[] var7 = new char[]{'=', var4};
            String var8 = this.parseToken(var7);
            Object var9 = null;
            if(this.hasChar()) {
               int var10 = this.pos;
               if(var1[var10] == 61) {
                  int var11 = this.pos + 1;
                  this.pos = var11;
                  char[] var12 = new char[]{var4};
                  this.parseQuotedToken(var12);
               }
            }

            if(this.hasChar()) {
               int var14 = this.pos;
               if(var1[var14] == var4) {
                  int var15 = this.pos + 1;
                  this.pos = var15;
               }
            }

            if(var8 != null && (var8.length() != 0 || var9 != null)) {
               NameValuePair var16 = new NameValuePair(var8, (String)var9);
               var6.add(var16);
            }
         }

         var5 = var6;
      }

      return var5;
   }
}
