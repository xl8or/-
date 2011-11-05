package com.htc.android.mail.mimemessage;

import android.text.Editable;
import android.widget.TextView;
import com.htc.android.mail.mimemessage.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class Utility {

   public Utility() {}

   public static final boolean arrayContains(Object[] var0, Object var1) {
      int var2 = 0;
      int var3 = var0.length;

      boolean var4;
      while(true) {
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(var0[var2].equals(var1)) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public static String base64Decode(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         Base64 var2 = new Base64();
         byte[] var3 = var0.getBytes();
         byte[] var4 = var2.decode(var3);
         var1 = new String(var4);
      }

      return var1;
   }

   public static String base64Encode(String var0) {
      String var1;
      if(var0 == null) {
         var1 = var0;
      } else {
         Base64 var2 = new Base64();
         byte[] var3 = var0.getBytes();
         byte[] var4 = var2.encode(var3);
         var1 = new String(var4);
      }

      return var1;
   }

   public static String combine(Object[] var0, char var1) {
      String var2;
      if(var0 == null) {
         var2 = null;
      } else {
         StringBuffer var3 = new StringBuffer();
         int var4 = 0;

         while(true) {
            int var5 = var0.length;
            if(var4 >= var5) {
               var2 = var3.toString();
               break;
            }

            String var6 = var0[var4].toString();
            var3.append(var6);
            int var8 = var0.length - 1;
            if(var4 < var8) {
               var3.append(var1);
            }

            ++var4;
         }
      }

      return var2;
   }

   public static String fastUrlDecode(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static String generateMessageId() {
      StringBuffer var0 = new StringBuffer();
      StringBuffer var1 = var0.append("<");

      for(int var2 = 0; var2 < 24; ++var2) {
         String var3 = Integer.toString((int)(Math.random() * 35.0D), 36);
         var0.append(var3);
      }

      StringBuffer var5 = var0.append(".");
      String var6 = Long.toString(System.currentTimeMillis());
      var0.append(var6);
      StringBuffer var8 = var0.append("@email.android.com>");
      return var0.toString();
   }

   public static String imapQuoted(String var0) {
      String var1 = var0.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
      return "\"" + var1 + "\"";
   }

   public static boolean isDateToday(Date var0) {
      Date var1 = new Date();
      int var2 = var0.getYear();
      int var3 = var1.getYear();
      boolean var8;
      if(var2 == var3) {
         int var4 = var0.getMonth();
         int var5 = var1.getMonth();
         if(var4 == var5) {
            int var6 = var0.getDate();
            int var7 = var1.getDate();
            if(var6 == var7) {
               var8 = true;
               return var8;
            }
         }
      }

      var8 = false;
      return var8;
   }

   public static String quoteString(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else if(!var0.matches("^\".*\"$")) {
         var1 = "\"" + var0 + "\"";
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static final String readInputStream(InputStream var0, String var1) throws IOException {
      InputStreamReader var2 = new InputStreamReader(var0, var1);
      StringBuffer var3 = new StringBuffer();
      char[] var4 = new char[512];

      while(true) {
         int var5 = var2.read(var4);
         if(var5 == -1) {
            return var3.toString();
         }

         var3.append(var4, 0, var5);
      }
   }

   public static boolean requiredFieldValid(Editable var0) {
      boolean var1;
      if(var0 != null && var0.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean requiredFieldValid(TextView var0) {
      boolean var1;
      if(var0.getText() != null && var0.getText().length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void setCompoundDrawablesAlpha(TextView var0, int var1) {}
}
