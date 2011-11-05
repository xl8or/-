package com.google.android.common.base;

import com.google.android.common.base.Platform;
import com.google.android.common.base.Preconditions;
import com.google.android.common.base.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class CharMatcher implements Predicate<Character> {

   public static final CharMatcher ANY;
   public static final CharMatcher ASCII;
   public static final CharMatcher BREAKING_WHITESPACE;
   private static final String BREAKING_WHITESPACE_CHARS = "\t\n\f\r     　";
   public static final CharMatcher DIGIT;
   public static final CharMatcher INVISIBLE;
   public static final CharMatcher JAVA_DIGIT;
   public static final CharMatcher JAVA_ISO_CONTROL;
   public static final CharMatcher JAVA_LETTER;
   public static final CharMatcher JAVA_LETTER_OR_DIGIT;
   public static final CharMatcher JAVA_LOWER_CASE;
   public static final CharMatcher JAVA_UPPER_CASE;
   public static final CharMatcher JAVA_WHITESPACE;
   @Deprecated
   public static final CharMatcher LEGACY_WHITESPACE;
   public static final CharMatcher NONE;
   private static final String NON_BREAKING_WHITESPACE_CHARS = " ᠎ ";
   public static final CharMatcher SINGLE_WIDTH;
   public static final CharMatcher WHITESPACE;


   static {
      CharMatcher var0 = anyOf("\t\n\f\r     　 ᠎ ");
      CharMatcher var1 = inRange('\u2000', '\u200a');
      WHITESPACE = var0.or(var1);
      CharMatcher var2 = anyOf("\t\n\f\r     　");
      CharMatcher var3 = inRange('\u2000', '\u2006');
      CharMatcher var4 = var2.or(var3);
      CharMatcher var5 = inRange('\u2008', '\u200a');
      BREAKING_WHITESPACE = var4.or(var5);
      ASCII = inRange('\u0000', '\u007f');
      CharMatcher var6 = inRange('0', '9');
      char[] var7 = "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         char var10 = var7[var9];
         char var11 = (char)(var10 + 9);
         CharMatcher var12 = inRange(var10, var11);
         var6 = var6.or(var12);
      }

      DIGIT = var6;
      CharMatcher var13 = inRange('\t', '\r');
      CharMatcher var14 = inRange('\u001c', ' ');
      CharMatcher var15 = var13.or(var14);
      CharMatcher var16 = is('\u1680');
      CharMatcher var17 = var15.or(var16);
      CharMatcher var18 = is('\u180e');
      CharMatcher var19 = var17.or(var18);
      CharMatcher var20 = inRange('\u2000', '\u2006');
      CharMatcher var21 = var19.or(var20);
      CharMatcher var22 = inRange('\u2008', '\u200b');
      CharMatcher var23 = var21.or(var22);
      CharMatcher var24 = inRange('\u2028', '\u2029');
      CharMatcher var25 = var23.or(var24);
      CharMatcher var26 = is('\u205f');
      CharMatcher var27 = var25.or(var26);
      CharMatcher var28 = is('\u3000');
      JAVA_WHITESPACE = var27.or(var28);
      JAVA_DIGIT = new CharMatcher.1();
      JAVA_LETTER = new CharMatcher.2();
      JAVA_LETTER_OR_DIGIT = new CharMatcher.3();
      JAVA_UPPER_CASE = new CharMatcher.4();
      JAVA_LOWER_CASE = new CharMatcher.5();
      CharMatcher var29 = inRange('\u0000', '\u001f');
      CharMatcher var30 = inRange('\u007f', '\u009f');
      JAVA_ISO_CONTROL = var29.or(var30);
      CharMatcher var31 = inRange('\u0000', ' ');
      CharMatcher var32 = inRange('\u007f', '\u00a0');
      CharMatcher var33 = var31.or(var32);
      CharMatcher var34 = is('\u00ad');
      CharMatcher var35 = var33.or(var34);
      CharMatcher var36 = inRange('\u0600', '\u0603');
      CharMatcher var37 = var35.or(var36);
      CharMatcher var38 = anyOf("۝܏ ឴឵᠎");
      CharMatcher var39 = var37.or(var38);
      CharMatcher var40 = inRange('\u2000', '\u200f');
      CharMatcher var41 = var39.or(var40);
      CharMatcher var42 = inRange('\u2028', '\u202f');
      CharMatcher var43 = var41.or(var42);
      CharMatcher var44 = inRange('\u205f', '\u2064');
      CharMatcher var45 = var43.or(var44);
      CharMatcher var46 = inRange('\u206a', '\u206f');
      CharMatcher var47 = var45.or(var46);
      CharMatcher var48 = is('\u3000');
      CharMatcher var49 = var47.or(var48);
      CharMatcher var50 = inRange('\ud800', '\uf8ff');
      CharMatcher var51 = var49.or(var50);
      CharMatcher var52 = anyOf("﻿￹￺￻");
      INVISIBLE = var51.or(var52);
      CharMatcher var53 = inRange('\u0000', '\u04f9');
      CharMatcher var54 = is('\u05be');
      CharMatcher var55 = var53.or(var54);
      CharMatcher var56 = inRange('\u05d0', '\u05ea');
      CharMatcher var57 = var55.or(var56);
      CharMatcher var58 = is('\u05f3');
      CharMatcher var59 = var57.or(var58);
      CharMatcher var60 = is('\u05f4');
      CharMatcher var61 = var59.or(var60);
      CharMatcher var62 = inRange('\u0600', '\u06ff');
      CharMatcher var63 = var61.or(var62);
      CharMatcher var64 = inRange('\u0750', '\u077f');
      CharMatcher var65 = var63.or(var64);
      CharMatcher var66 = inRange('\u0e00', '\u0e7f');
      CharMatcher var67 = var65.or(var66);
      CharMatcher var68 = inRange('\u1e00', '\u20af');
      CharMatcher var69 = var67.or(var68);
      CharMatcher var70 = inRange('\u2100', '\u213a');
      CharMatcher var71 = var69.or(var70);
      CharMatcher var72 = inRange('\ufb50', '\ufdff');
      CharMatcher var73 = var71.or(var72);
      CharMatcher var74 = inRange('\ufe70', '\ufeff');
      CharMatcher var75 = var73.or(var74);
      CharMatcher var76 = inRange('\uff61', '\uffdc');
      SINGLE_WIDTH = var75.or(var76);
      LEGACY_WHITESPACE = anyOf(" \r\n\t　   ").precomputed();
      ANY = new CharMatcher.6();
      NONE = new CharMatcher.7();
   }

   public CharMatcher() {}

   public static CharMatcher anyOf(CharSequence var0) {
      Object var2;
      switch(var0.length()) {
      case 0:
         var2 = NONE;
         break;
      case 1:
         var2 = is(var0.charAt(0));
         break;
      case 2:
         char var3 = var0.charAt(0);
         char var4 = var0.charAt(1);
         var2 = new CharMatcher.10(var3, var4);
         break;
      default:
         char[] var1 = var0.toString().toCharArray();
         Arrays.sort(var1);
         var2 = new CharMatcher.11(var1);
      }

      return (CharMatcher)var2;
   }

   public static CharMatcher forPredicate(Predicate<? super Character> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Object var2;
      if(var0 instanceof CharMatcher) {
         var2 = (CharMatcher)var0;
      } else {
         var2 = new CharMatcher.13(var0);
      }

      return (CharMatcher)var2;
   }

   public static CharMatcher inRange(char var0, char var1) {
      byte var2;
      if(var1 >= var0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      Preconditions.checkArgument((boolean)var2);
      return new CharMatcher.12(var0, var1);
   }

   public static CharMatcher is(char var0) {
      return new CharMatcher.8(var0);
   }

   public static CharMatcher isNot(char var0) {
      return new CharMatcher.9(var0);
   }

   public static CharMatcher noneOf(CharSequence var0) {
      return anyOf(var0).negate();
   }

   public CharMatcher and(CharMatcher var1) {
      CharMatcher[] var2 = new CharMatcher[]{this, null};
      CharMatcher var3 = (CharMatcher)Preconditions.checkNotNull(var1);
      var2[1] = var3;
      List var4 = Arrays.asList(var2);
      return new CharMatcher.And(var4);
   }

   public boolean apply(Character var1) {
      char var2 = var1.charValue();
      return this.matches(var2);
   }

   public String collapseFrom(CharSequence var1, char var2) {
      int var3 = this.indexIn(var1);
      String var4;
      if(var3 == -1) {
         var4 = var1.toString();
      } else {
         int var5 = var1.length();
         StringBuilder var6 = new StringBuilder(var5);
         CharSequence var7 = var1.subSequence(0, var3);
         StringBuilder var8 = var6.append(var7).append(var2);
         boolean var9 = true;
         int var10 = var3 + 1;

         while(true) {
            int var11 = var1.length();
            if(var10 >= var11) {
               var4 = var8.toString();
               break;
            }

            char var12 = var1.charAt(var10);
            Character var13 = Character.valueOf(var12);
            if(this.apply(var13)) {
               if(!var9) {
                  var8.append(var2);
                  var9 = true;
               }
            } else {
               var8.append(var12);
               var9 = false;
            }

            ++var10;
         }
      }

      return var4;
   }

   public int countIn(CharSequence var1) {
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var1.length();
         if(var3 >= var4) {
            return var2;
         }

         char var5 = var1.charAt(var3);
         if(this.matches(var5)) {
            ++var2;
         }

         ++var3;
      }
   }

   public int indexIn(CharSequence var1) {
      int var2 = var1.length();
      int var3 = 0;

      while(true) {
         if(var3 >= var2) {
            var3 = -1;
            break;
         }

         char var4 = var1.charAt(var3);
         if(this.matches(var4)) {
            break;
         }

         ++var3;
      }

      return var3;
   }

   public int indexIn(CharSequence var1, int var2) {
      int var3 = var1.length();
      Preconditions.checkPositionIndex(var2, var3);
      int var5 = var2;

      while(true) {
         if(var5 >= var3) {
            var5 = -1;
            break;
         }

         char var6 = var1.charAt(var5);
         if(this.matches(var6)) {
            break;
         }

         ++var5;
      }

      return var5;
   }

   public int lastIndexIn(CharSequence var1) {
      int var2 = var1.length() + -1;

      while(true) {
         if(var2 < 0) {
            var2 = -1;
            break;
         }

         char var3 = var1.charAt(var2);
         if(this.matches(var3)) {
            break;
         }

         var2 += -1;
      }

      return var2;
   }

   public abstract boolean matches(char var1);

   public boolean matchesAllOf(CharSequence var1) {
      int var2 = var1.length() + -1;

      boolean var4;
      while(true) {
         if(var2 < 0) {
            var4 = true;
            break;
         }

         char var3 = var1.charAt(var2);
         if(!this.matches(var3)) {
            var4 = false;
            break;
         }

         var2 += -1;
      }

      return var4;
   }

   public boolean matchesNoneOf(CharSequence var1) {
      boolean var2;
      if(this.indexIn(var1) == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public CharMatcher negate() {
      return new CharMatcher.14(this);
   }

   public CharMatcher or(CharMatcher var1) {
      CharMatcher[] var2 = new CharMatcher[]{this, null};
      CharMatcher var3 = (CharMatcher)Preconditions.checkNotNull(var1);
      var2[1] = var3;
      List var4 = Arrays.asList(var2);
      return new CharMatcher.Or(var4);
   }

   public CharMatcher precomputed() {
      return Platform.precomputeCharMatcher(this);
   }

   CharMatcher precomputedInternal() {
      CharMatcher.LookupTable var1 = new CharMatcher.LookupTable();
      this.setBits(var1);
      return new CharMatcher.15(var1);
   }

   public String removeFrom(CharSequence var1) {
      String var2 = var1.toString();
      int var3 = this.indexIn(var2);
      if(var3 != -1) {
         char[] var4 = var2.toCharArray();
         int var5 = 1;

         while(true) {
            ++var3;

            while(true) {
               int var6 = var4.length;
               if(var3 == var6) {
                  int var7 = var3 - var5;
                  var2 = new String(var4, 0, var7);
                  return var2;
               }

               char var8 = var4[var3];
               if(this.matches(var8)) {
                  ++var5;
                  break;
               }

               int var9 = var3 - var5;
               char var10 = var4[var3];
               var4[var9] = var10;
               ++var3;
            }
         }
      } else {
         return var2;
      }
   }

   public String replaceFrom(CharSequence var1, char var2) {
      String var3 = var1.toString();
      int var4 = this.indexIn(var3);
      if(var4 != -1) {
         char[] var5 = var3.toCharArray();
         var5[var4] = var2;
         int var6 = var4 + 1;

         while(true) {
            int var7 = var5.length;
            if(var6 >= var7) {
               var3 = new String(var5);
               break;
            }

            char var8 = var5[var6];
            if(this.matches(var8)) {
               var5[var6] = var2;
            }

            ++var6;
         }
      }

      return var3;
   }

   public String replaceFrom(CharSequence var1, CharSequence var2) {
      int var3 = var2.length();
      String var4;
      if(var3 == 0) {
         var4 = this.removeFrom(var1);
      } else if(var3 == 1) {
         char var5 = var2.charAt(0);
         var4 = this.replaceFrom(var1, var5);
      } else {
         var4 = var1.toString();
         int var6 = this.indexIn(var4);
         if(var6 != -1) {
            int var7 = var4.length();
            int var8 = (int)((double)var7 * 1.5D) + 16;
            StringBuilder var9 = new StringBuilder(var8);
            int var10 = 0;

            do {
               var9.append(var4, var10, var6);
               var9.append(var2);
               var10 = var6 + 1;
               var6 = this.indexIn(var4, var10);
            } while(var6 != -1);

            var9.append(var4, var10, var7);
            var4 = var9.toString();
         }
      }

      return var4;
   }

   public String retainFrom(CharSequence var1) {
      return this.negate().removeFrom(var1);
   }

   protected void setBits(CharMatcher.LookupTable var1) {
      char var2 = 0;

      while(true) {
         if(this.matches(var2)) {
            var1.set(var2);
         }

         char var3 = (char)(var2 + 1);
         if(var2 == '\uffff') {
            return;
         }

         var2 = var3;
      }
   }

   public String trimAndCollapseFrom(CharSequence var1, char var2) {
      int var3 = this.negate().indexIn(var1);
      String var4;
      if(var3 == -1) {
         var4 = "";
      } else {
         int var5 = var1.length();
         StringBuilder var6 = new StringBuilder(var5);
         boolean var7 = false;
         int var8 = var3;

         while(true) {
            int var9 = var1.length();
            if(var8 >= var9) {
               var4 = var6.toString();
               break;
            }

            char var10 = var1.charAt(var8);
            Character var11 = Character.valueOf(var10);
            if(this.apply(var11)) {
               var7 = true;
            } else {
               if(var7) {
                  var6.append(var2);
                  var7 = false;
               }

               var6.append(var10);
            }

            ++var8;
         }
      }

      return var4;
   }

   public String trimFrom(CharSequence var1) {
      int var2 = var1.length();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if(!this.matches(var4)) {
            break;
         }
      }

      int var5;
      for(var5 = var2 + -1; var5 > var3; var5 += -1) {
         char var6 = var1.charAt(var5);
         if(!this.matches(var6)) {
            break;
         }
      }

      int var7 = var5 + 1;
      return var1.subSequence(var3, var7).toString();
   }

   public String trimLeadingFrom(CharSequence var1) {
      int var2 = var1.length();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if(!this.matches(var4)) {
            break;
         }
      }

      return var1.subSequence(var3, var2).toString();
   }

   public String trimTrailingFrom(CharSequence var1) {
      int var2;
      for(var2 = var1.length() + -1; var2 >= 0; var2 += -1) {
         char var3 = var1.charAt(var2);
         if(!this.matches(var3)) {
            break;
         }
      }

      int var4 = var2 + 1;
      return var1.subSequence(0, var4).toString();
   }

   protected static class LookupTable {

      int[] data;


      protected LookupTable() {
         int[] var1 = new int[2048];
         this.data = var1;
      }

      boolean get(char var1) {
         byte var2 = 1;
         int[] var3 = this.data;
         int var4 = var1 >> 5;
         int var5 = var3[var4];
         int var6 = var2 << var1;
         if((var5 & var6) == 0) {
            var2 = 0;
         }

         return (boolean)var2;
      }

      void set(char var1) {
         int[] var2 = this.data;
         int var3 = var1 >> 5;
         int var4 = var2[var3];
         int var5 = 1 << var1;
         int var6 = var4 | var5;
         var2[var3] = var6;
      }
   }

   class 15 extends CharMatcher {

      // $FF: synthetic field
      final CharMatcher.LookupTable val$table;


      15(CharMatcher.LookupTable var2) {
         this.val$table = var2;
      }

      public boolean matches(char var1) {
         return this.val$table.get(var1);
      }

      public CharMatcher precomputed() {
         return this;
      }
   }

   class 14 extends CharMatcher {

      // $FF: synthetic field
      final CharMatcher val$original;


      14(CharMatcher var2) {
         this.val$original = var2;
      }

      public int countIn(CharSequence var1) {
         int var2 = var1.length();
         int var3 = this.val$original.countIn(var1);
         return var2 - var3;
      }

      public boolean matches(char var1) {
         boolean var2;
         if(!this.val$original.matches(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean matchesAllOf(CharSequence var1) {
         return this.val$original.matchesNoneOf(var1);
      }

      public boolean matchesNoneOf(CharSequence var1) {
         return this.val$original.matchesAllOf(var1);
      }

      public CharMatcher negate() {
         return this.val$original;
      }
   }

   static class 13 extends CharMatcher {

      // $FF: synthetic field
      final Predicate val$predicate;


      13(Predicate var1) {
         this.val$predicate = var1;
      }

      public boolean apply(Character var1) {
         Predicate var2 = this.val$predicate;
         Object var3 = Preconditions.checkNotNull(var1);
         return var2.apply(var3);
      }

      public boolean matches(char var1) {
         Predicate var2 = this.val$predicate;
         Character var3 = Character.valueOf(var1);
         return var2.apply(var3);
      }
   }

   static class 12 extends CharMatcher {

      // $FF: synthetic field
      final char val$endInclusive;
      // $FF: synthetic field
      final char val$startInclusive;


      12(char var1, char var2) {
         this.val$startInclusive = var1;
         this.val$endInclusive = var2;
      }

      public boolean matches(char var1) {
         boolean var3;
         if(this.val$startInclusive <= var1) {
            char var2 = this.val$endInclusive;
            if(var1 <= var2) {
               var3 = true;
               return var3;
            }
         }

         var3 = false;
         return var3;
      }

      public CharMatcher precomputed() {
         return this;
      }

      protected void setBits(CharMatcher.LookupTable var1) {
         char var2 = this.val$startInclusive;

         while(true) {
            var1.set(var2);
            char var3 = (char)(var2 + 1);
            char var4 = this.val$endInclusive;
            if(var2 == var4) {
               return;
            }

            var2 = var3;
         }
      }
   }

   static class 11 extends CharMatcher {

      // $FF: synthetic field
      final char[] val$chars;


      11(char[] var1) {
         this.val$chars = var1;
      }

      public boolean matches(char var1) {
         boolean var2;
         if(Arrays.binarySearch(this.val$chars, var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      protected void setBits(CharMatcher.LookupTable var1) {
         char[] var2 = this.val$chars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            var1.set(var5);
         }

      }
   }

   static class 10 extends CharMatcher {

      // $FF: synthetic field
      final char val$match1;
      // $FF: synthetic field
      final char val$match2;


      10(char var1, char var2) {
         this.val$match1 = var1;
         this.val$match2 = var2;
      }

      public boolean matches(char var1) {
         char var2 = this.val$match1;
         boolean var4;
         if(var1 != var2) {
            char var3 = this.val$match2;
            if(var1 != var3) {
               var4 = false;
               return var4;
            }
         }

         var4 = true;
         return var4;
      }

      public CharMatcher precomputed() {
         return this;
      }

      protected void setBits(CharMatcher.LookupTable var1) {
         char var2 = this.val$match1;
         var1.set(var2);
         char var3 = this.val$match2;
         var1.set(var3);
      }
   }

   private static class Or extends CharMatcher {

      List<CharMatcher> components;


      Or(List<CharMatcher> var1) {
         this.components = var1;
      }

      public boolean matches(char var1) {
         Iterator var2 = this.components.iterator();

         boolean var3;
         while(true) {
            if(var2.hasNext()) {
               if(!((CharMatcher)var2.next()).matches(var1)) {
                  continue;
               }

               var3 = true;
               break;
            }

            var3 = false;
            break;
         }

         return var3;
      }

      public CharMatcher or(CharMatcher var1) {
         List var2 = this.components;
         ArrayList var3 = new ArrayList(var2);
         Object var4 = Preconditions.checkNotNull(var1);
         var3.add(var4);
         return new CharMatcher.Or(var3);
      }

      protected void setBits(CharMatcher.LookupTable var1) {
         Iterator var2 = this.components.iterator();

         while(var2.hasNext()) {
            ((CharMatcher)var2.next()).setBits(var1);
         }

      }
   }

   private static class And extends CharMatcher {

      List<CharMatcher> components;


      And(List<CharMatcher> var1) {
         this.components = var1;
      }

      public CharMatcher and(CharMatcher var1) {
         List var2 = this.components;
         ArrayList var3 = new ArrayList(var2);
         Object var4 = Preconditions.checkNotNull(var1);
         var3.add(var4);
         return new CharMatcher.And(var3);
      }

      public boolean matches(char var1) {
         Iterator var2 = this.components.iterator();

         boolean var3;
         while(true) {
            if(var2.hasNext()) {
               if(((CharMatcher)var2.next()).matches(var1)) {
                  continue;
               }

               var3 = false;
               break;
            }

            var3 = true;
            break;
         }

         return var3;
      }
   }

   static class 1 extends CharMatcher {

      1() {}

      public boolean matches(char var1) {
         return Character.isDigit(var1);
      }
   }

   static class 2 extends CharMatcher {

      2() {}

      public boolean matches(char var1) {
         return Character.isLetter(var1);
      }
   }

   static class 3 extends CharMatcher {

      3() {}

      public boolean matches(char var1) {
         return Character.isLetterOrDigit(var1);
      }
   }

   static class 4 extends CharMatcher {

      4() {}

      public boolean matches(char var1) {
         return Character.isUpperCase(var1);
      }
   }

   static class 5 extends CharMatcher {

      5() {}

      public boolean matches(char var1) {
         return Character.isLowerCase(var1);
      }
   }

   static class 6 extends CharMatcher {

      6() {}

      public CharMatcher and(CharMatcher var1) {
         return (CharMatcher)Preconditions.checkNotNull(var1);
      }

      public String collapseFrom(CharSequence var1, char var2) {
         String var3;
         if(var1.length() == 0) {
            var3 = "";
         } else {
            var3 = String.valueOf(var2);
         }

         return var3;
      }

      public int countIn(CharSequence var1) {
         return var1.length();
      }

      public int indexIn(CharSequence var1) {
         byte var2;
         if(var1.length() == 0) {
            var2 = -1;
         } else {
            var2 = 0;
         }

         return var2;
      }

      public int indexIn(CharSequence var1, int var2) {
         int var3 = var1.length();
         Preconditions.checkPositionIndex(var2, var3);
         if(var2 == var3) {
            var2 = -1;
         }

         return var2;
      }

      public int lastIndexIn(CharSequence var1) {
         return var1.length() + -1;
      }

      public boolean matches(char var1) {
         return true;
      }

      public boolean matchesAllOf(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return true;
      }

      public boolean matchesNoneOf(CharSequence var1) {
         boolean var2;
         if(var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public CharMatcher negate() {
         return NONE;
      }

      public CharMatcher or(CharMatcher var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return this;
      }

      public CharMatcher precomputed() {
         return this;
      }

      public String removeFrom(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return "";
      }

      public String replaceFrom(CharSequence var1, char var2) {
         char[] var3 = new char[var1.length()];
         Arrays.fill(var3, var2);
         return new String(var3);
      }

      public String replaceFrom(CharSequence var1, CharSequence var2) {
         int var3 = var1.length();
         int var4 = var2.length();
         int var5 = var3 * var4;
         StringBuilder var6 = new StringBuilder(var5);
         int var7 = 0;

         while(true) {
            int var8 = var1.length();
            if(var7 >= var8) {
               return var6.toString();
            }

            var6.append(var2);
            ++var7;
         }
      }

      public String trimFrom(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return "";
      }
   }

   static class 7 extends CharMatcher {

      7() {}

      public CharMatcher and(CharMatcher var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return this;
      }

      public String collapseFrom(CharSequence var1, char var2) {
         return var1.toString();
      }

      public int countIn(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return 0;
      }

      public int indexIn(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return -1;
      }

      public int indexIn(CharSequence var1, int var2) {
         int var3 = var1.length();
         Preconditions.checkPositionIndex(var2, var3);
         return -1;
      }

      public int lastIndexIn(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return -1;
      }

      public boolean matches(char var1) {
         return false;
      }

      public boolean matchesAllOf(CharSequence var1) {
         boolean var2;
         if(var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean matchesNoneOf(CharSequence var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return true;
      }

      public CharMatcher negate() {
         return ANY;
      }

      public CharMatcher or(CharMatcher var1) {
         return (CharMatcher)Preconditions.checkNotNull(var1);
      }

      public CharMatcher precomputed() {
         return this;
      }

      public String removeFrom(CharSequence var1) {
         return var1.toString();
      }

      public String replaceFrom(CharSequence var1, char var2) {
         return var1.toString();
      }

      public String replaceFrom(CharSequence var1, CharSequence var2) {
         Object var3 = Preconditions.checkNotNull(var2);
         return var1.toString();
      }

      protected void setBits(CharMatcher.LookupTable var1) {}

      public String trimFrom(CharSequence var1) {
         return var1.toString();
      }
   }

   static class 8 extends CharMatcher {

      // $FF: synthetic field
      final char val$match;


      8(char var1) {
         this.val$match = var1;
      }

      public CharMatcher and(CharMatcher var1) {
         char var2 = ((CharMatcher.8)this).val$match;
         if(!var1.matches(var2)) {
            this = NONE;
         }

         return (CharMatcher)this;
      }

      public boolean matches(char var1) {
         char var2 = this.val$match;
         boolean var3;
         if(var1 == var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public CharMatcher negate() {
         return isNot(this.val$match);
      }

      public CharMatcher or(CharMatcher var1) {
         char var2 = this.val$match;
         if(!var1.matches(var2)) {
            var1 = super.or(var1);
         }

         return var1;
      }

      public CharMatcher precomputed() {
         return this;
      }

      public String replaceFrom(CharSequence var1, char var2) {
         String var3 = var1.toString();
         char var4 = this.val$match;
         return var3.replace(var4, var2);
      }

      protected void setBits(CharMatcher.LookupTable var1) {
         char var2 = this.val$match;
         var1.set(var2);
      }
   }

   static class 9 extends CharMatcher {

      // $FF: synthetic field
      final char val$match;


      9(char var1) {
         this.val$match = var1;
      }

      public CharMatcher and(CharMatcher var1) {
         char var2 = this.val$match;
         if(var1.matches(var2)) {
            var1 = super.and(var1);
         }

         return var1;
      }

      public boolean matches(char var1) {
         char var2 = this.val$match;
         boolean var3;
         if(var1 != var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public CharMatcher negate() {
         return is(this.val$match);
      }

      public CharMatcher or(CharMatcher var1) {
         char var2 = ((CharMatcher.9)this).val$match;
         if(var1.matches(var2)) {
            this = ANY;
         }

         return (CharMatcher)this;
      }
   }
}
