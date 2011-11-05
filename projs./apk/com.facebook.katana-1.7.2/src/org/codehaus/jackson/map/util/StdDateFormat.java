package org.codehaus.jackson.map.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StdDateFormat extends DateFormat {

   static final String[] ALL_FORMATS;
   static final SimpleDateFormat DATE_FORMAT_ISO8601;
   static final SimpleDateFormat DATE_FORMAT_ISO8601_Z;
   static final SimpleDateFormat DATE_FORMAT_RFC1123;
   static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd\'T\'HH:mm:ss.SSSZ";
   static final String DATE_FORMAT_STR_ISO8601_Z = "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'";
   static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
   public static final StdDateFormat instance;
   transient SimpleDateFormat _formatISO8601;
   transient SimpleDateFormat _formatISO8601_z;
   transient SimpleDateFormat _formatRFC1123;


   static {
      String[] var0 = new String[]{"yyyy-MM-dd\'T\'HH:mm:ss.SSSZ", "yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'", "EEE, dd MMM yyyy HH:mm:ss zzz"};
      ALL_FORMATS = var0;
      TimeZone var1 = TimeZone.getTimeZone("GMT");
      DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
      DATE_FORMAT_RFC1123.setTimeZone(var1);
      DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSSZ");
      DATE_FORMAT_ISO8601.setTimeZone(var1);
      DATE_FORMAT_ISO8601_Z = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'");
      DATE_FORMAT_ISO8601_Z.setTimeZone(var1);
      instance = new StdDateFormat();
   }

   public StdDateFormat() {}

   public static DateFormat getBlueprintISO8601Format() {
      return DATE_FORMAT_ISO8601;
   }

   public static DateFormat getBlueprintRFC1123Format() {
      return DATE_FORMAT_RFC1123;
   }

   public static DateFormat getISO8601Format(TimeZone var0) {
      SimpleDateFormat var1 = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
      var1.setTimeZone(var0);
      return var1;
   }

   public static DateFormat getRFC1123Format(TimeZone var0) {
      SimpleDateFormat var1 = (SimpleDateFormat)DATE_FORMAT_RFC1123.clone();
      var1.setTimeZone(var0);
      return var1;
   }

   public StdDateFormat clone() {
      return new StdDateFormat();
   }

   public StringBuffer format(Date var1, StringBuffer var2, FieldPosition var3) {
      if(this._formatISO8601 == null) {
         SimpleDateFormat var4 = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
         this._formatISO8601 = var4;
      }

      return this._formatISO8601.format(var1, var2, var3);
   }

   protected boolean looksLikeISO8601(String var1) {
      boolean var2;
      if(var1.length() >= 5 && Character.isDigit(var1.charAt(0)) && Character.isDigit(var1.charAt(3)) && var1.charAt(4) == 45) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Date parse(String var1) throws ParseException {
      String var2 = var1.trim();
      ParsePosition var3 = new ParsePosition(0);
      Date var4 = this.parse(var2, var3);
      if(var4 != null) {
         return var4;
      } else {
         StringBuilder var5 = new StringBuilder();
         String[] var6 = ALL_FORMATS;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String var9 = var6[var8];
            if(var5.length() > 0) {
               StringBuilder var10 = var5.append("\", \"");
            } else {
               StringBuilder var12 = var5.append('\"');
            }

            var5.append(var9);
         }

         StringBuilder var13 = var5.append('\"');
         Object[] var14 = new Object[]{var2, null};
         String var15 = var5.toString();
         var14[1] = var15;
         String var16 = String.format("Can not parse date \"%s\": not compatible with any of standard forms (%s)", var14);
         int var17 = var3.getErrorIndex();
         throw new ParseException(var16, var17);
      }
   }

   public Date parse(String var1, ParsePosition var2) {
      Date var3;
      if(this.looksLikeISO8601(var1)) {
         var3 = this.parseAsISO8601(var1, var2);
      } else {
         var3 = this.parseAsRFC1123(var1, var2);
      }

      return var3;
   }

   protected Date parseAsISO8601(String var1, ParsePosition var2) {
      int var3 = var1.length();
      int var4 = var3 - 1;
      SimpleDateFormat var5;
      String var6;
      if(var1.charAt(var4) == 90) {
         var5 = this._formatISO8601_z;
         if(var5 == null) {
            var5 = (SimpleDateFormat)DATE_FORMAT_ISO8601_Z.clone();
            this._formatISO8601_z = var5;
            var6 = var1;
         } else {
            var6 = var1;
         }
      } else {
         int var7 = var3 - 3;
         char var8 = var1.charAt(var7);
         if(var8 == 58) {
            StringBuilder var9 = new StringBuilder(var1);
            int var10 = var3 - 3;
            int var11 = var3 - 2;
            var9.delete(var10, var11);
            var6 = var9.toString();
         } else if(var8 != 43 && var8 != 45) {
            var6 = var1;
         } else {
            var6 = var1 + "00";
         }

         var5 = this._formatISO8601;
         if(this._formatISO8601 == null) {
            var5 = (SimpleDateFormat)DATE_FORMAT_ISO8601.clone();
            this._formatISO8601 = var5;
         }
      }

      return var5.parse(var6, var2);
   }

   protected Date parseAsRFC1123(String var1, ParsePosition var2) {
      if(this._formatRFC1123 == null) {
         SimpleDateFormat var3 = (SimpleDateFormat)DATE_FORMAT_RFC1123.clone();
         this._formatRFC1123 = var3;
      }

      return this._formatRFC1123.parse(var1, var2);
   }
}
