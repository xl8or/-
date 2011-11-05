package com.google.android.common.base;

import com.google.android.common.base.CharEscaper;
import com.google.android.common.base.CharEscaperBuilder;
import com.google.android.common.base.Escaper;
import com.google.android.common.base.PercentEscaper;
import com.google.android.common.base.Preconditions;
import java.io.IOException;

public final class CharEscapers {

   private static final CharEscaper ASCII_HTML_ESCAPER = (new CharEscaperBuilder()).addEscape('\"', "&quot;").addEscape('\'', "&#39;").addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;").toEscaper();
   private static final Escaper CPP_URI_ESCAPER = new PercentEscaper("!()*-._~,/:", (boolean)1);
   private static final char[] HEX_DIGITS;
   private static final CharEscaper JAVASCRIPT_ESCAPER;
   private static final CharEscaper JAVA_CHAR_ESCAPER;
   private static final CharEscaper JAVA_STRING_ESCAPER;
   private static final CharEscaper JAVA_STRING_UNICODE_ESCAPER;
   private static final CharEscaper NULL_ESCAPER = new CharEscapers.1();
   private static final CharEscaper PYTHON_ESCAPER;
   private static final Escaper URI_ESCAPER = new PercentEscaper("-_.*", (boolean)1);
   private static final Escaper URI_ESCAPER_NO_PLUS = new PercentEscaper("-_.*", (boolean)0);
   private static final Escaper URI_PATH_ESCAPER = new PercentEscaper("-_.!~*\'()@:$&,;=", (boolean)0);
   private static final Escaper URI_QUERY_STRING_ESCAPER = new PercentEscaper("-_.!~*\'()@:$,;/?:", (boolean)0);
   private static final Escaper URI_QUERY_STRING_ESCAPER_WITH_PLUS = new PercentEscaper("-_.!~*\'()@:$,;/?:", (boolean)1);
   private static final CharEscaper XML_CONTENT_ESCAPER = newBasicXmlEscapeBuilder().toEscaper();
   private static final CharEscaper XML_ESCAPER = newBasicXmlEscapeBuilder().addEscape('\"', "&quot;").addEscape('\'', "&apos;").toEscaper();


   static {
      char[][] var0 = (new CharEscaperBuilder()).addEscape('\b', "\\b").addEscape('\f', "\\f").addEscape('\n', "\\n").addEscape('\r', "\\r").addEscape('\t', "\\t").addEscape('\"', "\\\"").addEscape('\\', "\\\\").toArray();
      JAVA_STRING_ESCAPER = new CharEscapers.JavaCharEscaper(var0);
      char[][] var1 = (new CharEscaperBuilder()).addEscape('\b', "\\b").addEscape('\f', "\\f").addEscape('\n', "\\n").addEscape('\r', "\\r").addEscape('\t', "\\t").addEscape('\'', "\\\'").addEscape('\"', "\\\"").addEscape('\\', "\\\\").toArray();
      JAVA_CHAR_ESCAPER = new CharEscapers.JavaCharEscaper(var1);
      JAVA_STRING_UNICODE_ESCAPER = new CharEscapers.2();
      PYTHON_ESCAPER = (new CharEscaperBuilder()).addEscape('\n', "\\n").addEscape('\r', "\\r").addEscape('\t', "\\t").addEscape('\\', "\\\\").addEscape('\"', "\\\"").addEscape('\'', "\\\'").toEscaper();
      char[][] var2 = (new CharEscaperBuilder()).addEscape('\'', "\\x27").addEscape('\"', "\\x22").addEscape('<', "\\x3c").addEscape('=', "\\x3d").addEscape('>', "\\x3e").addEscape('&', "\\x26").addEscape('\b', "\\b").addEscape('\t', "\\t").addEscape('\n', "\\n").addEscape('\f', "\\f").addEscape('\r', "\\r").addEscape('\\', "\\\\").toArray();
      JAVASCRIPT_ESCAPER = new CharEscapers.JavascriptCharEscaper(var2);
      HEX_DIGITS = "0123456789abcdef".toCharArray();
   }

   private CharEscapers() {}

   public static CharEscaper asciiHtmlEscaper() {
      return ASCII_HTML_ESCAPER;
   }

   public static Escaper cppUriEscaper() {
      return CPP_URI_ESCAPER;
   }

   public static CharEscaper fallThrough(CharEscaper var0, CharEscaper var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      Object var3 = Preconditions.checkNotNull(var1);
      return new CharEscapers.FallThroughCharEscaper(var0, var1);
   }

   public static CharEscaper htmlEscaper() {
      return CharEscapers.HtmlEscaperHolder.HTML_ESCAPER;
   }

   public static CharEscaper javaCharEscaper() {
      return JAVA_CHAR_ESCAPER;
   }

   public static CharEscaper javaStringEscaper() {
      return JAVA_STRING_ESCAPER;
   }

   public static CharEscaper javaStringUnicodeEscaper() {
      return JAVA_STRING_UNICODE_ESCAPER;
   }

   public static CharEscaper javascriptEscaper() {
      return JAVASCRIPT_ESCAPER;
   }

   private static CharEscaperBuilder newBasicXmlEscapeBuilder() {
      CharEscaperBuilder var0 = (new CharEscaperBuilder()).addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;");
      char[] var1 = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
      return var0.addEscapes(var1, "");
   }

   public static CharEscaper nullEscaper() {
      return NULL_ESCAPER;
   }

   public static CharEscaper pythonEscaper() {
      return PYTHON_ESCAPER;
   }

   public static Escaper uriEscaper() {
      return uriEscaper((boolean)1);
   }

   public static Escaper uriEscaper(boolean var0) {
      Escaper var1;
      if(var0) {
         var1 = URI_ESCAPER;
      } else {
         var1 = URI_ESCAPER_NO_PLUS;
      }

      return var1;
   }

   public static Escaper uriPathEscaper() {
      return URI_PATH_ESCAPER;
   }

   public static Escaper uriQueryStringEscaper() {
      return uriQueryStringEscaper((boolean)0);
   }

   public static Escaper uriQueryStringEscaper(boolean var0) {
      Escaper var1;
      if(var0) {
         var1 = URI_QUERY_STRING_ESCAPER_WITH_PLUS;
      } else {
         var1 = URI_QUERY_STRING_ESCAPER;
      }

      return var1;
   }

   public static CharEscaper xmlContentEscaper() {
      return XML_CONTENT_ESCAPER;
   }

   public static CharEscaper xmlEscaper() {
      return XML_ESCAPER;
   }

   private static class HtmlEscaperHolder {

      private static final CharEscaper HTML_ESCAPER;


      static {
         char[][] var0 = (new CharEscaperBuilder()).addEscape('\"', "&quot;").addEscape('\'', "&#39;").addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;").addEscape('\u00a0', "&nbsp;").addEscape('\u00a1', "&iexcl;").addEscape('\u00a2', "&cent;").addEscape('\u00a3', "&pound;").addEscape('\u00a4', "&curren;").addEscape('\u00a5', "&yen;").addEscape('\u00a6', "&brvbar;").addEscape('\u00a7', "&sect;").addEscape('\u00a8', "&uml;").addEscape('\u00a9', "&copy;").addEscape('\u00aa', "&ordf;").addEscape('\u00ab', "&laquo;").addEscape('\u00ac', "&not;").addEscape('\u00ad', "&shy;").addEscape('\u00ae', "&reg;").addEscape('\u00af', "&macr;").addEscape('\u00b0', "&deg;").addEscape('\u00b1', "&plusmn;").addEscape('\u00b2', "&sup2;").addEscape('\u00b3', "&sup3;").addEscape('\u00b4', "&acute;").addEscape('\u00b5', "&micro;").addEscape('\u00b6', "&para;").addEscape('\u00b7', "&middot;").addEscape('\u00b8', "&cedil;").addEscape('\u00b9', "&sup1;").addEscape('\u00ba', "&ordm;").addEscape('\u00bb', "&raquo;").addEscape('\u00bc', "&frac14;").addEscape('\u00bd', "&frac12;").addEscape('\u00be', "&frac34;").addEscape('\u00bf', "&iquest;").addEscape('\u00c0', "&Agrave;").addEscape('\u00c1', "&Aacute;").addEscape('\u00c2', "&Acirc;").addEscape('\u00c3', "&Atilde;").addEscape('\u00c4', "&Auml;").addEscape('\u00c5', "&Aring;").addEscape('\u00c6', "&AElig;").addEscape('\u00c7', "&Ccedil;").addEscape('\u00c8', "&Egrave;").addEscape('\u00c9', "&Eacute;").addEscape('\u00ca', "&Ecirc;").addEscape('\u00cb', "&Euml;").addEscape('\u00cc', "&Igrave;").addEscape('\u00cd', "&Iacute;").addEscape('\u00ce', "&Icirc;").addEscape('\u00cf', "&Iuml;").addEscape('\u00d0', "&ETH;").addEscape('\u00d1', "&Ntilde;").addEscape('\u00d2', "&Ograve;").addEscape('\u00d3', "&Oacute;").addEscape('\u00d4', "&Ocirc;").addEscape('\u00d5', "&Otilde;").addEscape('\u00d6', "&Ouml;").addEscape('\u00d7', "&times;").addEscape('\u00d8', "&Oslash;").addEscape('\u00d9', "&Ugrave;").addEscape('\u00da', "&Uacute;").addEscape('\u00db', "&Ucirc;").addEscape('\u00dc', "&Uuml;").addEscape('\u00dd', "&Yacute;").addEscape('\u00de', "&THORN;").addEscape('\u00df', "&szlig;").addEscape('\u00e0', "&agrave;").addEscape('\u00e1', "&aacute;").addEscape('\u00e2', "&acirc;").addEscape('\u00e3', "&atilde;").addEscape('\u00e4', "&auml;").addEscape('\u00e5', "&aring;").addEscape('\u00e6', "&aelig;").addEscape('\u00e7', "&ccedil;").addEscape('\u00e8', "&egrave;").addEscape('\u00e9', "&eacute;").addEscape('\u00ea', "&ecirc;").addEscape('\u00eb', "&euml;").addEscape('\u00ec', "&igrave;").addEscape('\u00ed', "&iacute;").addEscape('\u00ee', "&icirc;").addEscape('\u00ef', "&iuml;").addEscape('\u00f0', "&eth;").addEscape('\u00f1', "&ntilde;").addEscape('\u00f2', "&ograve;").addEscape('\u00f3', "&oacute;").addEscape('\u00f4', "&ocirc;").addEscape('\u00f5', "&otilde;").addEscape('\u00f6', "&ouml;").addEscape('\u00f7', "&divide;").addEscape('\u00f8', "&oslash;").addEscape('\u00f9', "&ugrave;").addEscape('\u00fa', "&uacute;").addEscape('\u00fb', "&ucirc;").addEscape('\u00fc', "&uuml;").addEscape('\u00fd', "&yacute;").addEscape('\u00fe', "&thorn;").addEscape('\u00ff', "&yuml;").addEscape('\u0152', "&OElig;").addEscape('\u0153', "&oelig;").addEscape('\u0160', "&Scaron;").addEscape('\u0161', "&scaron;").addEscape('\u0178', "&Yuml;").addEscape('\u0192', "&fnof;").addEscape('\u02c6', "&circ;").addEscape('\u02dc', "&tilde;").addEscape('\u0391', "&Alpha;").addEscape('\u0392', "&Beta;").addEscape('\u0393', "&Gamma;").addEscape('\u0394', "&Delta;").addEscape('\u0395', "&Epsilon;").addEscape('\u0396', "&Zeta;").addEscape('\u0397', "&Eta;").addEscape('\u0398', "&Theta;").addEscape('\u0399', "&Iota;").addEscape('\u039a', "&Kappa;").addEscape('\u039b', "&Lambda;").addEscape('\u039c', "&Mu;").addEscape('\u039d', "&Nu;").addEscape('\u039e', "&Xi;").addEscape('\u039f', "&Omicron;").addEscape('\u03a0', "&Pi;").addEscape('\u03a1', "&Rho;").addEscape('\u03a3', "&Sigma;").addEscape('\u03a4', "&Tau;").addEscape('\u03a5', "&Upsilon;").addEscape('\u03a6', "&Phi;").addEscape('\u03a7', "&Chi;").addEscape('\u03a8', "&Psi;").addEscape('\u03a9', "&Omega;").addEscape('\u03b1', "&alpha;").addEscape('\u03b2', "&beta;").addEscape('\u03b3', "&gamma;").addEscape('\u03b4', "&delta;").addEscape('\u03b5', "&epsilon;").addEscape('\u03b6', "&zeta;").addEscape('\u03b7', "&eta;").addEscape('\u03b8', "&theta;").addEscape('\u03b9', "&iota;").addEscape('\u03ba', "&kappa;").addEscape('\u03bb', "&lambda;").addEscape('\u03bc', "&mu;").addEscape('\u03bd', "&nu;").addEscape('\u03be', "&xi;").addEscape('\u03bf', "&omicron;").addEscape('\u03c0', "&pi;").addEscape('\u03c1', "&rho;").addEscape('\u03c2', "&sigmaf;").addEscape('\u03c3', "&sigma;").addEscape('\u03c4', "&tau;").addEscape('\u03c5', "&upsilon;").addEscape('\u03c6', "&phi;").addEscape('\u03c7', "&chi;").addEscape('\u03c8', "&psi;").addEscape('\u03c9', "&omega;").addEscape('\u03d1', "&thetasym;").addEscape('\u03d2', "&upsih;").addEscape('\u03d6', "&piv;").addEscape('\u2002', "&ensp;").addEscape('\u2003', "&emsp;").addEscape('\u2009', "&thinsp;").addEscape('\u200c', "&zwnj;").addEscape('\u200d', "&zwj;").addEscape('\u200e', "&lrm;").addEscape('\u200f', "&rlm;").addEscape('\u2013', "&ndash;").addEscape('\u2014', "&mdash;").addEscape('\u2018', "&lsquo;").addEscape('\u2019', "&rsquo;").addEscape('\u201a', "&sbquo;").addEscape('\u201c', "&ldquo;").addEscape('\u201d', "&rdquo;").addEscape('\u201e', "&bdquo;").addEscape('\u2020', "&dagger;").addEscape('\u2021', "&Dagger;").addEscape('\u2022', "&bull;").addEscape('\u2026', "&hellip;").addEscape('\u2030', "&permil;").addEscape('\u2032', "&prime;").addEscape('\u2033', "&Prime;").addEscape('\u2039', "&lsaquo;").addEscape('\u203a', "&rsaquo;").addEscape('\u203e', "&oline;").addEscape('\u2044', "&frasl;").addEscape('\u20ac', "&euro;").addEscape('\u2111', "&image;").addEscape('\u2118', "&weierp;").addEscape('\u211c', "&real;").addEscape('\u2122', "&trade;").addEscape('\u2135', "&alefsym;").addEscape('\u2190', "&larr;").addEscape('\u2191', "&uarr;").addEscape('\u2192', "&rarr;").addEscape('\u2193', "&darr;").addEscape('\u2194', "&harr;").addEscape('\u21b5', "&crarr;").addEscape('\u21d0', "&lArr;").addEscape('\u21d1', "&uArr;").addEscape('\u21d2', "&rArr;").addEscape('\u21d3', "&dArr;").addEscape('\u21d4', "&hArr;").addEscape('\u2200', "&forall;").addEscape('\u2202', "&part;").addEscape('\u2203', "&exist;").addEscape('\u2205', "&empty;").addEscape('\u2207', "&nabla;").addEscape('\u2208', "&isin;").addEscape('\u2209', "&notin;").addEscape('\u220b', "&ni;").addEscape('\u220f', "&prod;").addEscape('\u2211', "&sum;").addEscape('\u2212', "&minus;").addEscape('\u2217', "&lowast;").addEscape('\u221a', "&radic;").addEscape('\u221d', "&prop;").addEscape('\u221e', "&infin;").addEscape('\u2220', "&ang;").addEscape('\u2227', "&and;").addEscape('\u2228', "&or;").addEscape('\u2229', "&cap;").addEscape('\u222a', "&cup;").addEscape('\u222b', "&int;").addEscape('\u2234', "&there4;").addEscape('\u223c', "&sim;").addEscape('\u2245', "&cong;").addEscape('\u2248', "&asymp;").addEscape('\u2260', "&ne;").addEscape('\u2261', "&equiv;").addEscape('\u2264', "&le;").addEscape('\u2265', "&ge;").addEscape('\u2282', "&sub;").addEscape('\u2283', "&sup;").addEscape('\u2284', "&nsub;").addEscape('\u2286', "&sube;").addEscape('\u2287', "&supe;").addEscape('\u2295', "&oplus;").addEscape('\u2297', "&otimes;").addEscape('\u22a5', "&perp;").addEscape('\u22c5', "&sdot;").addEscape('\u2308', "&lceil;").addEscape('\u2309', "&rceil;").addEscape('\u230a', "&lfloor;").addEscape('\u230b', "&rfloor;").addEscape('\u2329', "&lang;").addEscape('\u232a', "&rang;").addEscape('\u25ca', "&loz;").addEscape('\u2660', "&spades;").addEscape('\u2663', "&clubs;").addEscape('\u2665', "&hearts;").addEscape('\u2666', "&diams;").toArray();
         HTML_ESCAPER = new CharEscapers.HtmlCharEscaper(var0);
      }

      private HtmlEscaperHolder() {}
   }

   private static class JavaCharEscaper extends CharEscapers.FastCharEscaper {

      public JavaCharEscaper(char[][] var1) {
         super(var1, ' ', '~');
      }

      protected char[] escape(char var1) {
         int var2 = this.replacementLength;
         char[] var3;
         if(var1 < var2) {
            var3 = this.replacements[var1];
            if(var3 != null) {
               return var3;
            }
         }

         if(this.safeMin <= var1) {
            char var4 = this.safeMax;
            if(var1 <= var4) {
               var3 = null;
               return var3;
            }
         }

         if(var1 <= 255) {
            var3 = new char[4];
            var3[0] = 92;
            char[] var5 = CharEscapers.HEX_DIGITS;
            int var6 = var1 & 7;
            char var7 = var5[var6];
            var3[3] = var7;
            char var8 = (char)(var1 >>> 3);
            char[] var9 = CharEscapers.HEX_DIGITS;
            int var10 = var8 & 7;
            char var11 = var9[var10];
            var3[2] = var11;
            char var12 = (char)(var8 >>> 3);
            char[] var13 = CharEscapers.HEX_DIGITS;
            int var14 = var12 & 7;
            char var15 = var13[var14];
            var3[1] = var15;
         } else {
            var3 = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
            char[] var16 = CharEscapers.HEX_DIGITS;
            int var17 = var1 & 15;
            char var18 = var16[var17];
            var3[5] = var18;
            char var19 = (char)(var1 >>> 4);
            char[] var20 = CharEscapers.HEX_DIGITS;
            int var21 = var19 & 15;
            char var22 = var20[var21];
            var3[4] = var22;
            char var23 = (char)(var19 >>> 4);
            char[] var24 = CharEscapers.HEX_DIGITS;
            int var25 = var23 & 15;
            char var26 = var24[var25];
            var3[3] = var26;
            char var27 = (char)(var23 >>> 4);
            char[] var28 = CharEscapers.HEX_DIGITS;
            int var29 = var27 & 15;
            char var30 = var28[var29];
            var3[2] = var30;
         }

         return var3;
      }
   }

   static class 2 extends CharEscaper {

      2() {}

      protected char[] escape(char var1) {
         char[] var2;
         if(var1 <= 127) {
            var2 = null;
         } else {
            var2 = new char[6];
            char[] var3 = CharEscapers.HEX_DIGITS;
            int var4 = var1 & 15;
            char var5 = var3[var4];
            var2[5] = var5;
            char var6 = (char)(var1 >>> 4);
            char[] var7 = CharEscapers.HEX_DIGITS;
            int var8 = var6 & 15;
            char var9 = var7[var8];
            var2[4] = var9;
            char var10 = (char)(var6 >>> 4);
            char[] var11 = CharEscapers.HEX_DIGITS;
            int var12 = var10 & 15;
            char var13 = var11[var12];
            var2[3] = var13;
            char var14 = (char)(var10 >>> 4);
            char[] var15 = CharEscapers.HEX_DIGITS;
            int var16 = var14 & 15;
            char var17 = var15[var16];
            var2[2] = var17;
            var2[1] = 117;
            var2[0] = 92;
         }

         return var2;
      }
   }

   private static class FallThroughCharEscaper extends CharEscaper {

      private final CharEscaper primary;
      private final CharEscaper secondary;


      public FallThroughCharEscaper(CharEscaper var1, CharEscaper var2) {
         this.primary = var1;
         this.secondary = var2;
      }

      protected char[] escape(char var1) {
         char[] var2 = this.primary.escape(var1);
         if(var2 == null) {
            var2 = this.secondary.escape(var1);
         }

         return var2;
      }
   }

   private abstract static class FastCharEscaper extends CharEscaper {

      protected final int replacementLength;
      protected final char[][] replacements;
      protected final char safeMax;
      protected final char safeMin;


      public FastCharEscaper(char[][] var1, char var2, char var3) {
         this.replacements = var1;
         int var4 = var1.length;
         this.replacementLength = var4;
         this.safeMin = var2;
         this.safeMax = var3;
      }

      public String escape(String var1) {
         int var2 = var1.length();
         int var3 = 0;

         while(var3 < var2) {
            char var4 = var1.charAt(var3);
            int var5 = this.replacementLength;
            if(var4 >= var5 || this.replacements[var4] == false) {
               char var6 = this.safeMin;
               if(var4 >= var6) {
                  char var7 = this.safeMax;
                  if(var4 <= var7) {
                     ++var3;
                     continue;
                  }
               }
            }

            var1 = this.escapeSlow(var1, var3);
            break;
         }

         return var1;
      }
   }

   private static class HtmlCharEscaper extends CharEscapers.FastCharEscaper {

      public HtmlCharEscaper(char[][] var1) {
         super(var1, '\u0000', '~');
      }

      protected char[] escape(char var1) {
         int var2 = this.replacementLength;
         char[] var3;
         if(var1 < var2) {
            var3 = this.replacements[var1];
            if(var3 != null) {
               return var3;
            }
         }

         char var4 = this.safeMax;
         if(var1 <= var4) {
            var3 = null;
         } else {
            int var5;
            if(var1 < 1000) {
               var5 = 4;
            } else if(var1 < 10000) {
               var5 = 5;
            } else {
               var5 = 6;
            }

            char[] var6 = new char[var5 + 2];
            var6[0] = 38;
            var6[1] = 35;
            int var7 = var5 + 1;
            var6[var7] = 59;

            for(int var8 = var1; var5 > 1; var5 += -1) {
               char[] var9 = CharEscapers.HEX_DIGITS;
               int var10 = var8 % 10;
               char var11 = var9[var10];
               var6[var5] = var11;
               var8 /= 10;
            }

            var3 = var6;
         }

         return var3;
      }
   }

   static class 1 extends CharEscaper {

      1() {}

      public Appendable escape(Appendable var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return new CharEscapers.1.1(var1);
      }

      public String escape(String var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         return var1;
      }

      protected char[] escape(char var1) {
         return null;
      }

      class 1 implements Appendable {

         // $FF: synthetic field
         final Appendable val$out;


         1(Appendable var2) {
            this.val$out = var2;
         }

         public Appendable append(char var1) throws IOException {
            this.val$out.append(var1);
            return this;
         }

         public Appendable append(CharSequence var1) throws IOException {
            Object var2 = Preconditions.checkNotNull(var1);
            this.val$out.append(var1);
            return this;
         }

         public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
            Object var4 = Preconditions.checkNotNull(var1);
            this.val$out.append(var1, var2, var3);
            return this;
         }
      }
   }

   private static class JavascriptCharEscaper extends CharEscapers.FastCharEscaper {

      public JavascriptCharEscaper(char[][] var1) {
         super(var1, ' ', '~');
      }

      protected char[] escape(char var1) {
         int var2 = this.replacementLength;
         char[] var3;
         if(var1 < var2) {
            var3 = this.replacements[var1];
            if(var3 != null) {
               return var3;
            }
         }

         if(this.safeMin <= var1) {
            char var4 = this.safeMax;
            if(var1 <= var4) {
               var3 = null;
               return var3;
            }
         }

         if(var1 < 256) {
            var3 = new char[4];
            char[] var5 = CharEscapers.HEX_DIGITS;
            int var6 = var1 & 15;
            char var7 = var5[var6];
            var3[3] = var7;
            char var8 = (char)(var1 >>> 4);
            char[] var9 = CharEscapers.HEX_DIGITS;
            int var10 = var8 & 15;
            char var11 = var9[var10];
            var3[2] = var11;
            var3[1] = 120;
            var3[0] = 92;
         } else {
            var3 = new char[6];
            char[] var12 = CharEscapers.HEX_DIGITS;
            int var13 = var1 & 15;
            char var14 = var12[var13];
            var3[5] = var14;
            char var15 = (char)(var1 >>> 4);
            char[] var16 = CharEscapers.HEX_DIGITS;
            int var17 = var15 & 15;
            char var18 = var16[var17];
            var3[4] = var18;
            char var19 = (char)(var15 >>> 4);
            char[] var20 = CharEscapers.HEX_DIGITS;
            int var21 = var19 & 15;
            char var22 = var20[var21];
            var3[3] = var22;
            char var23 = (char)(var19 >>> 4);
            char[] var24 = CharEscapers.HEX_DIGITS;
            int var25 = var23 & 15;
            char var26 = var24[var25];
            var3[2] = var26;
            var3[1] = 117;
            var3[0] = 92;
         }

         return var3;
      }
   }
}
