package com.google.android.common.base;

import com.google.android.common.base.CharEscaper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CharEscaperBuilder {

   private final Map<Character, String> map;
   private int max = -1;


   public CharEscaperBuilder() {
      HashMap var1 = new HashMap();
      this.map = var1;
   }

   public CharEscaperBuilder addEscape(char var1, String var2) {
      Map var3 = this.map;
      Character var4 = Character.valueOf(var1);
      var3.put(var4, var2);
      int var6 = this.max;
      if(var1 > var6) {
         this.max = var1;
      }

      return this;
   }

   public CharEscaperBuilder addEscapes(char[] var1, String var2) {
      char[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         this.addEscape(var6, var2);
      }

      return this;
   }

   public char[][] toArray() {
      char[] var1 = new char[this.max + 1];

      char var4;
      char[] var5;
      for(Iterator var2 = this.map.entrySet().iterator(); var2.hasNext(); var1[var4] = (char)var5) {
         Entry var3 = (Entry)var2.next();
         var4 = ((Character)var3.getKey()).charValue();
         var5 = ((String)var3.getValue()).toCharArray();
      }

      return (char[][])var1;
   }

   public CharEscaper toEscaper() {
      char[][] var1 = this.toArray();
      return new CharEscaperBuilder.CharArrayDecorator(var1);
   }

   private static class CharArrayDecorator extends CharEscaper {

      private final int replaceLength;
      private final char[][] replacements;


      CharArrayDecorator(char[][] var1) {
         this.replacements = var1;
         int var2 = var1.length;
         this.replaceLength = var2;
      }

      public String escape(String var1) {
         int var2 = var1.length();

         for(int var3 = 0; var3 < var2; ++var3) {
            char var4 = var1.charAt(var3);
            int var5 = this.replacements.length;
            if(var4 < var5 && this.replacements[var4] != false) {
               var1 = this.escapeSlow(var1, var3);
               break;
            }
         }

         return var1;
      }

      protected char[] escape(char var1) {
         int var2 = this.replaceLength;
         char[] var3;
         if(var1 < var2) {
            var3 = this.replacements[var1];
         } else {
            var3 = null;
         }

         return var3;
      }
   }
}
