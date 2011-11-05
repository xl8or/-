package org.apache.commons.httpclient.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.util.ParameterParser;

public final class AuthChallengeParser {

   public AuthChallengeParser() {}

   public static Map extractParams(String var0) throws MalformedChallengeException {
      if(var0 == null) {
         throw new IllegalArgumentException("Challenge may not be null");
      } else {
         int var1 = var0.indexOf(32);
         if(var1 == -1) {
            String var2 = "Invalid challenge: " + var0;
            throw new MalformedChallengeException(var2);
         } else {
            HashMap var3 = new HashMap();
            ParameterParser var4 = new ParameterParser();
            int var5 = var1 + 1;
            int var6 = var0.length();
            String var7 = var0.substring(var5, var6);
            List var8 = var4.parse(var7, ',');
            int var9 = 0;

            while(true) {
               int var10 = var8.size();
               if(var9 >= var10) {
                  return var3;
               }

               NameValuePair var11 = (NameValuePair)var8.get(var9);
               String var12 = var11.getName().toLowerCase();
               String var13 = var11.getValue();
               var3.put(var12, var13);
               ++var9;
            }
         }
      }
   }

   public static String extractScheme(String var0) throws MalformedChallengeException {
      if(var0 == null) {
         throw new IllegalArgumentException("Challenge may not be null");
      } else {
         int var1 = var0.indexOf(32);
         String var2;
         if(var1 == -1) {
            var2 = var0;
         } else {
            var2 = var0.substring(0, var1);
         }

         if(var2.length() == 0) {
            String var3 = "Invalid challenge: " + var0;
            throw new MalformedChallengeException(var3);
         } else {
            return var2.toLowerCase();
         }
      }
   }

   public static Map parseChallenges(Header[] var0) throws MalformedChallengeException {
      if(var0 == null) {
         throw new IllegalArgumentException("Array of challenges may not be null");
      } else {
         int var1 = var0.length;
         HashMap var2 = new HashMap(var1);
         int var3 = 0;

         while(true) {
            int var4 = var0.length;
            if(var3 >= var4) {
               return var2;
            }

            String var5 = var0[var3].getValue();
            String var6 = extractScheme(var5);
            var2.put(var6, var5);
            ++var3;
         }
      }
   }
}
